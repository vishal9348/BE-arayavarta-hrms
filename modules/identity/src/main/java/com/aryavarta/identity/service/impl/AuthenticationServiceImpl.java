package com.aryavarta.identity.service.impl;


import com.aryavarta.identity.entity.Session;
import com.aryavarta.identity.entity.enums.SessionStatus;
import com.aryavarta.identity.exception.AuthenticationServiceException;
import com.aryavarta.identity.record.auth.LoginRequest;
import com.aryavarta.identity.record.auth.LoginResponse;
import com.aryavarta.identity.record.auth.LogoutRequest;
import com.aryavarta.identity.record.auth.RefreshTokenRequest;
import com.aryavarta.identity.repository.SessionRepository;
import com.aryavarta.identity.security.authentication.CustomUserDetails;
import com.aryavarta.identity.security.authentication.CustomUserDetailsService;
import com.aryavarta.identity.security.authentication.TenantUsernamePasswordAuthenticationToken;
import com.aryavarta.identity.security.jwt.JwtService;
import com.aryavarta.identity.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SessionRepository sessionRepository;
    private final CustomUserDetailsService userDetailsService;

    public LoginResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(new TenantUsernamePasswordAuthenticationToken(request.tenantId(), request.username(), request.password()));
        var userDetails = (com.aryavarta.identity.security.authentication.CustomUserDetails) authentication.getPrincipal();
        UUID tenantId = userDetails.tenantId();
        UUID userId = userDetails.userId();
        UUID employeeId = userDetails.employeeId();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).filter(authority -> authority.startsWith("ROLE_")).toList();
        List<String> permissions = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).filter(authority -> !authority.startsWith("ROLE_")).toList();
        String accessToken = jwtService.generateAccessToken(userId, tenantId, employeeId, userDetails.getUsername(), roles, permissions);
        String refreshToken = jwtService.generateRefreshToken(userId, tenantId, userDetails.getUsername());
        persistRefreshSession(tenantId, userId, refreshToken);
        OffsetDateTime expiresAt = OffsetDateTime.ofInstant(jwtService.getExpiration(accessToken), ZoneOffset.UTC);
        long expiresIn = jwtService.getExpiration(accessToken).getEpochSecond() - java.time.Instant.now().getEpochSecond();

        return new LoginResponse(accessToken, refreshToken, "Bearer", expiresIn, expiresAt, tenantId, userId, employeeId, userDetails.getUsername(), userDetails.email(), roles, permissions);
    }

    public LoginResponse refresh(RefreshTokenRequest request) {

        String refreshToken = request.refreshToken();

        validateRefreshToken(refreshToken);

        UUID tokenTenantId = jwtService.getTenantId(refreshToken);

        if (!request.tenantId().equals(tokenTenantId)) {
            throw new AuthenticationServiceException("TENANT_MISMATCH", "Tenant does not match refresh token");
        }

        UUID userId = jwtService.getUserId(refreshToken);

        String tokenHash = hashToken(refreshToken);

        Session session = sessionRepository.findByTenantIdAndTokenHash(tokenTenantId, tokenHash).orElseThrow(() -> new AuthenticationServiceException("INVALID_REFRESH_TOKEN", "Refresh session not found"));

        validateSession(session);

        if (!userId.equals(session.getUserId())) {
            throw new AuthenticationServiceException("INVALID_REFRESH_TOKEN", "Refresh session does not belong to user");
        }

        /*
         * Reload the user from the database using BOTH tenant ID
         * and user identity.
         *
         * This ensures that the refreshed access token reflects
         * the user's current account state and authorities.
         */
        CustomUserDetails userDetails = userDetailsService.loadUserByTenantAndUsername(tokenTenantId, jwtService.getUsername(refreshToken));

        /*
         * Verify that the user from the database is the same
         * user represented by the refresh token.
         */
        if (!userId.equals(userDetails.userId())) {
            throw new AuthenticationServiceException("INVALID_REFRESH_TOKEN", "Refresh token user does not match current user");
        }

        /*
         * Verify that the tenant from the database matches
         * the tenant encoded in the refresh token.
         */
        if (!tokenTenantId.equals(userDetails.tenantId())) {
            throw new AuthenticationServiceException("TENANT_MISMATCH", "User does not belong to the requested tenant");
        }

        /*
         * Account status is checked again here.
         *
         * A user who was active when the refresh token was issued
         * may have subsequently been disabled or locked.
         */
        if (!userDetails.isEnabled()) {
            throw new AuthenticationServiceException("USER_INACTIVE", "User account is inactive");
        }

        if (!userDetails.isAccountNonLocked()) {
            throw new AuthenticationServiceException("ACCOUNT_LOCKED", "User account is locked");
        }

        /*
         * Extract CURRENT authorities from the database-backed
         * CustomUserDetails.
         */
        List<String> roles = userDetails.getAuthorities().stream().map(authority -> authority.getAuthority()).filter(authority -> authority.startsWith("ROLE_")).toList();

        List<String> permissions = userDetails.getAuthorities().stream().map(authority -> authority.getAuthority()).filter(authority -> !authority.startsWith("ROLE_")).toList();

        /*
         * Rotate refresh token:
         *
         * Old refresh token -> REVOKED
         * New refresh token -> ACTIVE
         */
        session.setStatus(SessionStatus.REVOKED);

        session.setLastAccessedAt(OffsetDateTime.now(ZoneOffset.UTC));

        sessionRepository.save(session);

        String newAccessToken = jwtService.generateAccessToken(userDetails.userId(), userDetails.tenantId(), userDetails.employeeId(), userDetails.getUsername(), roles, permissions);

        String newRefreshToken = jwtService.generateRefreshToken(userDetails.userId(), userDetails.tenantId(), userDetails.getUsername());

        persistRefreshSession(userDetails.tenantId(), userDetails.userId(), newRefreshToken);

        OffsetDateTime expiresAt = OffsetDateTime.ofInstant(jwtService.getExpiration(newAccessToken), ZoneOffset.UTC);

        long expiresIn = jwtService.getExpiration(newAccessToken).getEpochSecond() - java.time.Instant.now().getEpochSecond();

        return new LoginResponse(newAccessToken, newRefreshToken, "Bearer", expiresIn, expiresAt, userDetails.tenantId(), userDetails.userId(), userDetails.employeeId(), userDetails.getUsername(), userDetails.email(), roles, permissions);
    }

    public void logout(LogoutRequest request) {
        String tokenHash = hashToken(request.refreshToken());
        sessionRepository.findByTenantIdAndTokenHash(request.tenantId(), tokenHash).ifPresent(session -> {
            if (!session.getTenantId().equals(request.tenantId())) {
                return;
            }
            session.setStatus(SessionStatus.REVOKED);
            session.setLastAccessedAt(OffsetDateTime.now(ZoneOffset.UTC));
            sessionRepository.save(session);
        });
    }

    private void validateRefreshToken(String refreshToken) {
        if (!jwtService.isValid(refreshToken)) {
            throw new AuthenticationServiceException("INVALID_REFRESH_TOKEN", "Refresh token is invalid or expired");
        }
        if (!jwtService.isRefreshToken(refreshToken)) {
            throw new AuthenticationServiceException("INVALID_REFRESH_TOKEN", "Invalid token type");
        }
    }

    private void validateSession(Session session) {

        if (session.getStatus() != SessionStatus.ACTIVE) {
            throw new AuthenticationServiceException("SESSION_REVOKED", "Refresh session is no longer active");
        }

        if (session.getExpiresAt().isBefore(OffsetDateTime.now(ZoneOffset.UTC))) {
            session.setStatus(SessionStatus.EXPIRED);
            sessionRepository.save(session);
            throw new AuthenticationServiceException("REFRESH_TOKEN_EXPIRED", "Refresh session has expired");
        }
    }

    private void persistRefreshSession(UUID tenantId, UUID userId, String refreshToken) {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime expiresAt = OffsetDateTime.ofInstant(jwtService.getExpiration(refreshToken), ZoneOffset.UTC);
        Session session = Session.builder().tenantId(tenantId).userId(userId).tokenHash(hashToken(refreshToken)).status(SessionStatus.ACTIVE).expiresAt(expiresAt).lastAccessedAt(now).build();
        sessionRepository.save(session);
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 algorithm is not available", exception);
        }
    }
}

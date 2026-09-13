package com.aryavarta.identity.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JwtService {

    private static final String TOKEN_TYPE_CLAIM = "token_type";
    private static final String TENANT_ID_CLAIM = "tenant_id";
    private static final String USER_ID_CLAIM = "user_id";
    private static final String EMPLOYEE_ID_CLAIM = "employee_id";
    private static final String ROLES_CLAIM = "roles";
    private static final String PERMISSIONS_CLAIM = "permissions";

    private final JwtProperties properties;
    private final SecretKey signingKey;

    public JwtService(JwtProperties properties) {
        this.properties = properties;
        this.signingKey = Keys.hmacShaKeyFor(properties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(UUID userId, UUID tenantId, UUID employeeId, String username, List<String> roles, List<String> permissions) {
        return generateToken(JwtTokenType.ACCESS, userId, tenantId, employeeId, username, roles, permissions, properties.getAccessTokenExpiration());
    }

    public String generateRefreshToken(UUID userId, UUID tenantId, String username) {
        return generateToken(JwtTokenType.REFRESH, userId, tenantId, null, username, List.of(), List.of(), properties.getRefreshTokenExpiration());
    }

    private String generateToken(JwtTokenType tokenType, UUID userId, UUID tenantId, UUID employeeId, String username, List<String> roles, List<String> permissions, Duration expiration) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(expiration);

        var builder = Jwts.builder().issuer(properties.getIssuer()).audience().add(properties.getAudience()).and().subject(userId.toString()).issuedAt(Date.from(issuedAt)).expiration(Date.from(expiresAt)).claim(TOKEN_TYPE_CLAIM, tokenType.name()).claim(TENANT_ID_CLAIM, tenantId.toString()).claim(USER_ID_CLAIM, userId.toString()).claim("username", username);

        if (employeeId != null) {
            builder.claim(EMPLOYEE_ID_CLAIM, employeeId.toString());
        }

        if (!roles.isEmpty()) {
            builder.claim(ROLES_CLAIM, roles);
        }

        if (!permissions.isEmpty()) {
            builder.claim(PERMISSIONS_CLAIM, permissions);
        }

        return builder.signWith(signingKey).compact();
    }

    public boolean isValid(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isAccessToken(String token) {
        return JwtTokenType.ACCESS.name().equals(getClaim(token, TOKEN_TYPE_CLAIM, String.class));
    }

    public boolean isRefreshToken(String token) {
        return JwtTokenType.REFRESH.name().equals(getClaim(token, TOKEN_TYPE_CLAIM, String.class));
    }

    public UUID getUserId(String token) {
        return UUID.fromString(getRequiredClaim(token, USER_ID_CLAIM, String.class));
    }

    public UUID getTenantId(String token) {
        return UUID.fromString(getRequiredClaim(token, TENANT_ID_CLAIM, String.class));
    }

    public UUID getEmployeeId(String token) {
        String employeeId = getClaim(token, EMPLOYEE_ID_CLAIM, String.class);

        return employeeId == null ? null : UUID.fromString(employeeId);
    }

    public String getUsername(String token) {
        return getRequiredClaim(token, "username", String.class);
    }

    public List<String> getRoles(String token) {
        return getStringListClaim(token, ROLES_CLAIM);
    }

    public List<String> getPermissions(String token) {
        return getStringListClaim(token, PERMISSIONS_CLAIM);
    }

    public Instant getExpiration(String token) {
        Claims claims = parseToken(token).getPayload();

        return claims.getExpiration().toInstant();
    }

    public String getTokenId(String token) {
        return parseToken(token).getPayload().getId();
    }

    private <T> T getClaim(String token, String claimName, Class<T> type) {
        return parseToken(token).getPayload().get(claimName, type);
    }

    private <T> T getRequiredClaim(String token, String claimName, Class<T> type) {
        T value = getClaim(token, claimName, type);

        if (value == null) {
            throw new IllegalArgumentException("Required JWT claim is missing: " + claimName);
        }

        return value;
    }

    private List<String> getStringListClaim(String token, String claimName) {
        Object value = parseToken(token).getPayload().get(claimName);

        if (value == null) {
            return List.of();
        }

        if (!(value instanceof List<?> list)) {
            throw new IllegalArgumentException("Invalid JWT claim: " + claimName);
        }

        return list.stream().map(String::valueOf).toList();
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser().verifyWith(signingKey).requireIssuer(properties.getIssuer()).requireAudience(properties.getAudience()).build().parseSignedClaims(token);
    }
}
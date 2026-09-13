package com.aryavarta.identity.service.impl;

import com.aryavarta.identity.entity.Permission;

import com.aryavarta.identity.entity.platform.PlatformUser;
import com.aryavarta.identity.entity.platform.PlatformUserRole;
import com.aryavarta.identity.entity.platform.PlatformUserStatus;
import com.aryavarta.identity.record.platform.PlatformLoginRequest;
import com.aryavarta.identity.record.platform.PlatformLoginResponse;
import com.aryavarta.identity.repository.*;
import com.aryavarta.identity.security.jwt.JwtProperties;
import com.aryavarta.identity.security.jwt.JwtService;
import com.aryavarta.identity.service.PlatformAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlatformAuthenticationServiceImpl implements PlatformAuthenticationService {

    private static final String SUPER_ADMIN_ROLE = "PLATFORM_SUPER_ADMIN";
    private static final String TENANT_CREATE_PERMISSION = "tenant:create";
    private final JwtProperties properties;

    private final PlatformUserRepository platformUserRepository;
    private final PlatformUserRoleRepository platformUserRoleRepository;
    private final PlatformRoleRepository platformRoleRepository;
    private final PlatformRolePermissionRepository platformRolePermissionRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional(readOnly = true)
    public PlatformLoginResponse login(PlatformLoginRequest request) {

        String username = request.username().trim();

        PlatformUser platformUser = platformUserRepository.findByUsername(username).orElseThrow(() -> new BadCredentialsException("Invalid platform credentials"));

        validateUser(platformUser, request.password());

        List<String> roles = loadRoles(platformUser.getId());
        List<String> permissions = loadPermissions(platformUser.getId());

        /*
         * PLATFORM_SUPER_ADMIN must always have tenant:create.
         *
         * We don't trust the client to provide either role or permission.
         */
        validatePlatformAdministrator(roles, permissions);

        String accessToken = jwtService.generateAccessToken(platformUser.getId(), null, null, platformUser.getUsername(), roles, permissions);

        String refreshToken = jwtService.generateRefreshToken(
                platformUser.getId(),
                null,
                platformUser.getUsername()
        );

//        Instant expiresAt = jwtService.getExpiration(accessToken);
        OffsetDateTime expiresAt =
                jwtService.getExpiration(accessToken)
                        .atOffset(ZoneOffset.UTC);

        long expiresIn = Math.max(0, expiresAt.toEpochSecond() - OffsetDateTime.now().toEpochSecond());

        return new PlatformLoginResponse(accessToken, refreshToken, "Bearer", expiresIn, expiresAt, platformUser.getId(), platformUser.getUsername(), platformUser.getEmail(), roles, permissions);
    }

    private void validateUser(PlatformUser platformUser, String rawPassword) {

        if (platformUser.getStatus() != PlatformUserStatus.ACTIVE) {
            throw new BadCredentialsException("Platform account is not active");
        }

        if (!passwordEncoder.matches(rawPassword, platformUser.getPasswordHash())) {

            throw new BadCredentialsException("Invalid platform credentials");
        }
    }

    private List<String> loadRoles(UUID userId) {

        List<PlatformUserRole> userRoles = platformUserRoleRepository.findAllByUserId(userId);

        List<String> roles = new ArrayList<>();

        for (PlatformUserRole userRole : userRoles) {

            platformRoleRepository.findById(userRole.getRoleId()).ifPresent(role -> roles.add(role.getName()));
        }

        return roles;
    }

    private List<String> loadPermissions(UUID userId) {

        List<PlatformUserRole> userRoles = platformUserRoleRepository.findAllByUserId(userId);

        List<String> permissions = new ArrayList<>();

        for (PlatformUserRole userRole : userRoles) {

            List<UUID> permissionIds = platformRolePermissionRepository.findAllByRoleId(userRole.getRoleId()).stream().map(platformRolePermission -> platformRolePermission.getPermissionId()).toList();

            for (UUID permissionId : permissionIds) {

                permissionRepository.findById(permissionId).map(Permission::getCode).ifPresent(permissions::add);
            }
        }

        return permissions.stream().distinct().sorted().toList();
    }

    private void validatePlatformAdministrator(List<String> roles, List<String> permissions) {

        if (!roles.contains(SUPER_ADMIN_ROLE)) {
            throw new BadCredentialsException("Platform user does not have required platform role");
        }

        if (!permissions.contains(TENANT_CREATE_PERMISSION)) {
            throw new BadCredentialsException("Platform user does not have required platform permission");
        }
    }
}

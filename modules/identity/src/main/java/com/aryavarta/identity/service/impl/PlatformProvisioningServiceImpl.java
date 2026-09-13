package com.aryavarta.identity.service.impl;


import com.aryavarta.identity.entity.platform.*;
import com.aryavarta.identity.record.platform.CreatePlatformAdminRequest;
import com.aryavarta.identity.record.platform.PlatformUserResponse;

import com.aryavarta.identity.entity.Permission;
import com.aryavarta.identity.repository.*;
import com.aryavarta.identity.service.PlatformProvisioningService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlatformProvisioningServiceImpl implements PlatformProvisioningService {

    private static final String SUPER_ADMIN_ROLE = "PLATFORM_SUPER_ADMIN";
    private static final String TENANT_CREATE_PERMISSION = "tenant:create";

    private final PlatformUserRepository platformUserRepository;
    private final PlatformRoleRepository platformRoleRepository;
    private final PlatformUserRoleRepository platformUserRoleRepository;
    private final PlatformRolePermissionRepository platformRolePermissionRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public PlatformUserResponse createPlatformAdmin(CreatePlatformAdminRequest request) {
        if (platformUserRepository.count() > 0) {
            throw new IllegalStateException("Platform bootstrap has already been completed");
        }

        String username = request.username().trim();
        String email = request.email().trim().toLowerCase();

        if (platformUserRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Platform username already exists: " + username);
        }

        if (platformUserRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Platform email already exists: " + email);
        }

        Permission permission = permissionRepository.findByCode(TENANT_CREATE_PERMISSION).orElseGet(() -> permissionRepository.save(Permission.builder().code(TENANT_CREATE_PERMISSION).name("Create Tenant").description("Allows creation of new tenants").build()));

        PlatformRole role = platformRoleRepository.findByName(SUPER_ADMIN_ROLE).orElseGet(() -> platformRoleRepository.save(PlatformRole.builder().name(SUPER_ADMIN_ROLE).description("Full platform administrator").systemFlag(true).build()));

        if (!platformRolePermissionRepository.existsByRoleIdAndPermissionId(role.getId(), permission.getId())) {

            platformRolePermissionRepository.save(PlatformRolePermission.builder().roleId(role.getId()).permissionId(permission.getId()).build());
        }

        PlatformUser platformUser = PlatformUser.builder().username(username).email(email).passwordHash(passwordEncoder.encode(request.password())).status(PlatformUserStatus.ACTIVE).build();

        platformUser = platformUserRepository.save(platformUser);

        platformUserRoleRepository.save(PlatformUserRole.builder().userId(platformUser.getId()).roleId(role.getId()).build());

        return new PlatformUserResponse(platformUser.getId(), platformUser.getUsername(), platformUser.getEmail(), platformUser.getStatus(), platformUser.getCreatedAt());
    }
}
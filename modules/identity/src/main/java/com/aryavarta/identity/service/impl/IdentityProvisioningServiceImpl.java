package com.aryavarta.identity.service.impl;

import com.aryavarta.identity.entity.Role;
import com.aryavarta.identity.entity.UserAccount;
import com.aryavarta.identity.entity.enums.UserAccountStatus;
import com.aryavarta.identity.entity.enums.AuthProvider;
import com.aryavarta.identity.entity.UserRole;
import com.aryavarta.identity.exception.TenantProvisioningErrorCode;
import com.aryavarta.identity.exception.TenantProvisioningException;
import com.aryavarta.identity.record.ProvisionedAdministrator;
import com.aryavarta.identity.repository.RoleRepository;
import com.aryavarta.identity.repository.UserAccountRepository;
import com.aryavarta.identity.repository.UserRoleRepository;
import com.aryavarta.identity.service.IdentityProvisioningService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IdentityProvisioningServiceImpl implements IdentityProvisioningService {

    private static final String TENANT_ADMIN_ROLE = "TENANT_ADMIN";

    private final UserAccountRepository userAccountRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ProvisionedAdministrator provisionTenantAdministrator(UUID tenantId, String username, String email, String password, String firstName, String lastName) {

        if (userAccountRepository.existsByTenantIdAndUsername(tenantId, username)) {
            throw new TenantProvisioningException(TenantProvisioningErrorCode.ADMIN_USERNAME_ALREADY_EXISTS, "Administrator username already exists");
        }

        if (userAccountRepository.existsByTenantIdAndEmail(tenantId, email)) {
            throw new TenantProvisioningException(TenantProvisioningErrorCode.ADMIN_EMAIL_ALREADY_EXISTS, "Administrator email already exists");
        }

        Role tenantAdminRole = roleRepository.findByTenantIdAndName(tenantId, TENANT_ADMIN_ROLE).orElseThrow(() -> new TenantProvisioningException(TenantProvisioningErrorCode.TENANT_ADMIN_ROLE_NOT_FOUND, "TENANT_ADMIN role was not provisioned"));

        UserAccount user = UserAccount.builder().tenantId(tenantId).username(username).email(email).passwordHash(passwordEncoder.encode(password)).status(UserAccountStatus.ACTIVE).authProvider(AuthProvider.LOCAL).build();

        UserAccount savedUser = userAccountRepository.save(user);

        UserRole userRole = UserRole.builder().tenantId(tenantId).userId(savedUser.getId()).roleId(tenantAdminRole.getId()).build();

        userRoleRepository.save(userRole);

        return new ProvisionedAdministrator(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    @Override
    @Transactional
    public void provisionTenantDefaults(UUID tenantId) {

        Role role = roleRepository.findByTenantIdAndName(tenantId, "TENANT_ADMIN").orElseGet(() -> roleRepository.save(Role.builder().tenantId(tenantId).name("TENANT_ADMIN").description("Tenant administrator").systemFlag(true).build()));

        // Assign the platform-defined tenant-admin permissions here.
        //
        // Example:
        // tenant:read
        // tenant:update
        // user:create
        // user:read
        // user:update
        // role:create
        // role:read
        // role:update
        // etc.
    }
}

package com.aryavarta.identity.service;

import com.aryavarta.identity.record.ProvisionedAdministrator;

import java.util.UUID;

public interface IdentityProvisioningService {

    ProvisionedAdministrator provisionTenantAdministrator(
            UUID tenantId,
            String username,
            String email,
            String password,
            String firstName,
            String lastName
    );

    void provisionTenantDefaults(UUID tenantId);
}
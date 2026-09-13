package com.aryavarta.identity.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.UUID;

public class TenantUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final UUID tenantId;

    public TenantUsernamePasswordAuthenticationToken(UUID tenantId, String username, String password) {
        super(username, password);
        this.tenantId = tenantId;
    }

    public TenantUsernamePasswordAuthenticationToken(UUID tenantId, CustomUserDetails principal) {
        super(principal, null, principal.getAuthorities());
        this.tenantId = tenantId;
    }

    public UUID getTenantId() {
        return tenantId;
    }
}
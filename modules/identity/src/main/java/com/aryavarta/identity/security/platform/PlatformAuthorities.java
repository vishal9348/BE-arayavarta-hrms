package com.aryavarta.identity.security.platform;

import java.util.Set;

public final class PlatformAuthorities {

    public static final String SUPER_ADMIN = "ROLE_PLATFORM_SUPER_ADMIN";
    public static final String TENANT_CREATE = "tenant:create";

    private PlatformAuthorities() {
    }

    public static boolean canCreateTenant(Set<String> authorities) {
        return authorities.contains(SUPER_ADMIN)
                && authorities.contains(TENANT_CREATE);
    }
}
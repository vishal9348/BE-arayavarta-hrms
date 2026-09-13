package com.aryavarta.identity.security.tenant;

import java.util.UUID;

public final class TenantSecurityContext {

    private static final ThreadLocal<UUID> TENANT_CONTEXT = new ThreadLocal<>();

    private TenantSecurityContext() {
    }

    public static void setTenantId(UUID tenantId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        TENANT_CONTEXT.set(tenantId);
    }

    public static UUID getTenantId() {
        UUID tenantId = TENANT_CONTEXT.get();

        if (tenantId == null) {
            throw new IllegalStateException("Tenant context is not available");
        }

        return tenantId;
    }

    public static UUID getTenantIdOrNull() {
        return TENANT_CONTEXT.get();
    }

    public static boolean hasTenant() {
        return TENANT_CONTEXT.get() != null;
    }

    public static void clear() {
        TENANT_CONTEXT.remove();
    }
}
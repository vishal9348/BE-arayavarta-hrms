package com.aryavarta.identity.record.identityprovider;

import com.aryavarta.identity.entity.enums.IdentityProviderType;

import java.time.OffsetDateTime;
import java.util.UUID;

public record IdentityProviderResponse(
        UUID id,
        UUID tenantId,
        String name,
        IdentityProviderType type,
        String issuerUrl,
        String clientId,
        boolean active,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Long version
) {
}
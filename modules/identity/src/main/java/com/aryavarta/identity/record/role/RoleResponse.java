package com.aryavarta.identity.record.role;

import java.time.OffsetDateTime;
import java.util.UUID;

public record RoleResponse(
        UUID id,
        UUID tenantId,
        String name,
        String description,
        boolean systemFlag,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Long version
) {
}
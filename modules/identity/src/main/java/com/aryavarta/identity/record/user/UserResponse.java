package com.aryavarta.identity.record.user;

import com.aryavarta.identity.entity.enums.AuthProvider;
import com.aryavarta.identity.entity.enums.UserAccountStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        UUID tenantId,
        String username,
        String email,
        UserAccountStatus status,
        AuthProvider authProvider,
        UUID employeeId,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Long version
) {
}
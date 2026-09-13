package com.aryavarta.identity.record.mfa;

import com.aryavarta.identity.entity.enums.MfaStatus;
import com.aryavarta.identity.entity.enums.MfaType;

import java.time.OffsetDateTime;
import java.util.UUID;

public record MfaResponse(
        UUID id,
        UUID userId,
        MfaType type,
        MfaStatus status,
        boolean primary,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
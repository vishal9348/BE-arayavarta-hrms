package com.aryavarta.identity.record.session;

import com.aryavarta.identity.entity.enums.SessionStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record SessionResponse(
        UUID id,
        UUID userId,
        SessionStatus status,
        String ipAddress,
        String userAgent,
        OffsetDateTime createdAt,
        OffsetDateTime expiresAt,
        OffsetDateTime lastAccessedAt
) {
}
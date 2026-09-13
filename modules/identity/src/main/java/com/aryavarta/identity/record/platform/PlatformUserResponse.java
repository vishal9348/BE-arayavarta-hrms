package com.aryavarta.identity.record.platform;

import com.aryavarta.identity.entity.platform.PlatformUserStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record PlatformUserResponse(
        UUID id,
        String username,
        String email,
        PlatformUserStatus status,
        OffsetDateTime createdAt
) {
}

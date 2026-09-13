package com.aryavarta.identity.record.platform;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record PlatformLoginResponse(

        String accessToken,

        String refreshToken,

        String tokenType,

        long expiresIn,

        OffsetDateTime expiresAt,

        UUID userId,

        String username,

        String email,

        List<String> roles,

        List<String> permissions
) {
}

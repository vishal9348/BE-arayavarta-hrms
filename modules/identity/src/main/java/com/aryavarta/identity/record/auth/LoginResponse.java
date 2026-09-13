package com.aryavarta.identity.record.auth;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresIn,
        UUID userId,
        UUID employeeId,
        String username,
        String email,
        List<String> roles,
        List<String> permissions,
        OffsetDateTime expiresAt
) {
}
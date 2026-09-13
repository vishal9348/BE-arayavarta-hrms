package com.aryavarta.identity.security.jwt;

import java.util.UUID;

public record JwtPrincipal(
        UUID userId,
        UUID tenantId,
        UUID employeeId,
        String username
) {
}
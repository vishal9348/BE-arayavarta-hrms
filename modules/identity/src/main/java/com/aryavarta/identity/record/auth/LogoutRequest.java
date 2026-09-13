package com.aryavarta.identity.record.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LogoutRequest(

        @NotNull(message = "Tenant ID is required")
        UUID tenantId,

        @NotBlank(message = "Refresh token is required")
        String refreshToken
) {
}
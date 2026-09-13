package com.aryavarta.identity.record.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record LoginRequest(

        @NotNull(message = "Tenant ID is required")
        UUID tenantId,

        @NotBlank(message = "Username is required")
        @Size(max = 100, message = "Username must not exceed 100 characters")
        String username,

        @NotBlank(message = "Password is required")
        @Size(max = 200, message = "Password must not exceed 200 characters")
        String password
) {
}
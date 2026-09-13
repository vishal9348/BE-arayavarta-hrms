package com.aryavarta.identity.record.identityprovider;

import com.aryavarta.identity.entity.enums.IdentityProviderType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateIdentityProviderRequest(

        @NotBlank
        @Size(max = 100)
        String name,

        @NotNull
        IdentityProviderType type,

        @NotBlank
        @Size(max = 500)
        String issuerUrl,

        @Size(max = 500)
        String clientId,

        @Size(max = 1000)
        String clientSecret,

        boolean active
) {
}
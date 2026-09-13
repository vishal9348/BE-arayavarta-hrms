package com.aryavarta.identity.record.identityprovider;

import jakarta.validation.constraints.Size;

public record UpdateIdentityProviderRequest(

        @Size(max = 100)
        String name,

        @Size(max = 500)
        String issuerUrl,

        @Size(max = 500)
        String clientId,

        @Size(max = 1000)
        String clientSecret,

        Boolean active
) {
}
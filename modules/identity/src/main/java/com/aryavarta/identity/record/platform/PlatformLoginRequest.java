package com.aryavarta.identity.record.platform;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PlatformLoginRequest(

        @NotBlank
        @Size(max = 100)
        String username,

        @NotBlank
        @Size(min = 12, max = 128)
        String password
) {
}


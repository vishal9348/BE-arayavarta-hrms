package com.aryavarta.identity.record.mfa;

import jakarta.validation.constraints.NotBlank;

public record VerifyMfaRequest(

        @NotBlank
        String code
) {
}
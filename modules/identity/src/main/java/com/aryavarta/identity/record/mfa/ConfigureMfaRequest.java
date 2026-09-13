package com.aryavarta.identity.record.mfa;

import com.aryavarta.identity.entity.enums.MfaType;
import jakarta.validation.constraints.NotNull;

public record ConfigureMfaRequest(

        @NotNull
        MfaType type
) {
}
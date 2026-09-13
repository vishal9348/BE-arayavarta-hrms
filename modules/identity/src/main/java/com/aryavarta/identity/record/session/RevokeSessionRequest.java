package com.aryavarta.identity.record.session;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RevokeSessionRequest(

        @NotNull
        UUID sessionId
) {
}
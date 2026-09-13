package com.aryavarta.identity.record.auth;

import java.time.OffsetDateTime;
import java.util.List;

public record AuthErrorResponse(

        OffsetDateTime timestamp,

        int status,

        AuthErrorCode code,

        String message,

        String path,

        List<FieldError> fieldErrors) {

    public record FieldError(String field, String message) {
    }

    public static AuthErrorResponse of(int status, AuthErrorCode code, String message, String path) {
        return new AuthErrorResponse(OffsetDateTime.now(), status, code, message, path, List.of());
    }
}
package com.aryavarta.identity.exception;

import lombok.Getter;

@Getter
public class AuthenticationServiceException
        extends RuntimeException {

    private final String code;

    public AuthenticationServiceException(
            String code,
            String message
    ) {
        super(message);
        this.code = code;
    }
}

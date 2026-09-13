package com.aryavarta.identity.service;

import com.aryavarta.identity.security.authentication.CustomUserDetails;

import java.time.Instant;

public record AuthenticationResult(
        CustomUserDetails user,
        String accessToken,
        String refreshToken,
        Instant accessTokenExpiresAt
) {
}
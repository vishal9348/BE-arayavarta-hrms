package com.aryavarta.identity.service;

import com.aryavarta.identity.record.auth.LoginRequest;
import com.aryavarta.identity.record.auth.LoginResponse;
import com.aryavarta.identity.record.auth.LogoutRequest;
import com.aryavarta.identity.record.auth.RefreshTokenRequest;

public interface AuthenticationService {
    LoginResponse login(LoginRequest request);

    LoginResponse refresh(RefreshTokenRequest request);

    void logout(LogoutRequest request);
}

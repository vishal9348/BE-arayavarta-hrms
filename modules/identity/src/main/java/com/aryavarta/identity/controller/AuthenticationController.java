package com.aryavarta.identity.controller;

import com.aryavarta.identity.record.auth.LoginRequest;
import com.aryavarta.identity.record.auth.LoginResponse;
import com.aryavarta.identity.record.auth.LogoutRequest;
import com.aryavarta.identity.record.auth.RefreshTokenRequest;
import com.aryavarta.identity.record.platform.PlatformLoginRequest;
import com.aryavarta.identity.record.platform.PlatformLoginResponse;
import com.aryavarta.identity.service.AuthenticationService;
import com.aryavarta.identity.service.PlatformAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final PlatformAuthenticationService platformAuthenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        LoginResponse response = authenticationService.login(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/platform-login")
    public ResponseEntity<PlatformLoginResponse> loginPlatformUser(@Valid @RequestBody PlatformLoginRequest request) {
        PlatformLoginResponse response = platformAuthenticationService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {

        LoginResponse response = authenticationService.refresh(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@Valid @RequestBody LogoutRequest request) {

        authenticationService.logout(request);
    }
}
package com.aryavarta.identity.controller;

import com.aryavarta.identity.record.platform.CreatePlatformAdminRequest;
import com.aryavarta.identity.record.platform.PlatformUserResponse;
import com.aryavarta.identity.service.PlatformProvisioningService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/platform/bootstrap")
@RequiredArgsConstructor
public class PlatformProvisioningController {

    private final PlatformProvisioningService platformProvisioningService;

    @PostMapping
    public ResponseEntity<PlatformUserResponse> createPlatformAdmin(@Valid @RequestBody CreatePlatformAdminRequest request) {

        PlatformUserResponse response = platformProvisioningService.createPlatformAdmin(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
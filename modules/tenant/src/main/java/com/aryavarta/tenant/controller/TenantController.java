package com.aryavarta.tenant.controller;

import com.aryavarta.tenant.record.CreateTenantRequest;
import com.aryavarta.tenant.record.TenantProvisioningResponse;
import com.aryavarta.tenant.service.TenantProvisioningService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantProvisioningService tenantProvisioningService;

    @PostMapping
    @PreAuthorize("hasRole('PLATFORM_SUPER_ADMIN') and hasAuthority('tenant:create')")
    public ResponseEntity<TenantProvisioningResponse> createTenant(@Valid @RequestBody CreateTenantRequest request) {
        TenantProvisioningResponse response = tenantProvisioningService.provision(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
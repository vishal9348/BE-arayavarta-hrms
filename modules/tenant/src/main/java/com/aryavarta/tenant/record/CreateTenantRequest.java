package com.aryavarta.tenant.record;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateTenantRequest(

        // Tenant
        @NotBlank
        @Size(max = 100)
        String code,

        @NotBlank
        @Size(max = 200)
        String name,

        @NotNull
        UUID planId,

        // Legal Entity
        @NotBlank
        @Size(max = 100)
        String legalEntityName,

        @NotBlank
        @Size(max = 50)
        String legalEntityCode,

        @NotBlank
        @Size(max = 300)
        String legalName,

        @NotBlank
        @Size(max = 100)
        String registrationNumber,

        @Size(max = 100)
        String taxIdentificationNumber,

        @NotBlank
        @Size(min = 2, max = 2)
        String countryCode,

        @Size(max = 20)
        String stateCode,

        @Size(max = 255)
        String addressLine1,

        @Size(max = 255)
        String addressLine2,

        @Size(max = 100)
        String city,

        @Size(max = 20)
        String postalCode,

        @Email
        @Size(max = 255)
        String contactEmail,

        @Size(max = 30)
        String contactPhone,

        // Initial Tenant Administrator
        @NotBlank
        @Size(max = 100)
        String adminUsername,

        @NotBlank
        @Email
        @Size(max = 255)
        String adminEmail,

        @NotBlank
        @Size(min = 12, max = 128)
        String adminPassword,

        @NotBlank
        @Size(max = 100)
        String adminFirstName,

        @Size(max = 100)
        String adminLastName,

        // Optional tenant settings
        @Valid
        TenantProvisioningSettings settings
) {
}
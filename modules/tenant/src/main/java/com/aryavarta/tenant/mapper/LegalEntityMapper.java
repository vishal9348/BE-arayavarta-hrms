package com.aryavarta.tenant.mapper;

import com.aryavarta.tenant.entity.LegalEntity;
import com.aryavarta.tenant.record.CreateTenantRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LegalEntityMapper {

    public LegalEntity toEntity(CreateTenantRequest request, UUID tenantId) {

        return LegalEntity.builder()
                .tenantId(tenantId)
                .code(request.legalEntityCode().trim())
                .name(request.legalEntityName().trim())
                .legalName(request.legalName().trim())
                .registrationNumber(request.registrationNumber().trim())
                .taxIdentificationNumber(trimToNull(request.taxIdentificationNumber()))
                .countryCode(request.countryCode().trim().toUpperCase())
                .stateCode(trimToNull(request.stateCode()))
                .addressLine1(trimToNull(request.addressLine1()))
                .addressLine2(trimToNull(request.addressLine2()))
                .city(trimToNull(request.city()))
                .postalCode(trimToNull(request.postalCode()))
                .contactEmail(trimToNull(request.contactEmail()))
                .contactPhone(trimToNull(request.contactPhone()))
                .active(true)
                .build();
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
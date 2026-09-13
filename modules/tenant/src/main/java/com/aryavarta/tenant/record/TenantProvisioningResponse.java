package com.aryavarta.tenant.record;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record TenantProvisioningResponse(

        UUID tenantId,

        String tenantCode,

        String tenantName,

        UUID planId,

        String planName,

        UUID legalEntityId,

        UUID administratorUserId,

        String administratorUsername,

        String administratorEmail,

        List<String> enabledFeatures,

        OffsetDateTime createdAt
) {
}
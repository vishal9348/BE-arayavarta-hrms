package com.aryavarta.tenant.record;

import jakarta.validation.constraints.Size;

public record TenantProvisioningSettings(

        @Size(max = 100)
        String timezone,

        @Size(max = 20)
        String locale,

        @Size(max = 10)
        String currency,

        @Size(max = 20)
        String fiscalYearStart,

        @Size(max = 200)
        String dateFormat
) {
}
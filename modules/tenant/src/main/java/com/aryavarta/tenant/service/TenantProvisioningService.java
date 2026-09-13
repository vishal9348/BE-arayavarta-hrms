package com.aryavarta.tenant.service;

import com.aryavarta.tenant.record.CreateTenantRequest;
import com.aryavarta.tenant.record.TenantProvisioningResponse;

public interface TenantProvisioningService {
    TenantProvisioningResponse provision(CreateTenantRequest request);
}

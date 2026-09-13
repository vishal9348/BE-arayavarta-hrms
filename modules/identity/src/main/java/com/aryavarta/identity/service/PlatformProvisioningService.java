package com.aryavarta.identity.service;

import com.aryavarta.identity.record.platform.CreatePlatformAdminRequest;
import com.aryavarta.identity.record.platform.PlatformUserResponse;

public interface PlatformProvisioningService {
    PlatformUserResponse createPlatformAdmin(CreatePlatformAdminRequest request);
}

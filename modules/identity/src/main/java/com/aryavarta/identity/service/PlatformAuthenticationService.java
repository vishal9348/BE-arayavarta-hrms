package com.aryavarta.identity.service;

import com.aryavarta.identity.record.platform.PlatformLoginRequest;
import com.aryavarta.identity.record.platform.PlatformLoginResponse;

public interface PlatformAuthenticationService {

    PlatformLoginResponse login(PlatformLoginRequest request);
}

package com.aryavarta.identity.exception;


public class TenantProvisioningException extends RuntimeException {

    private final TenantProvisioningErrorCode code;

    public TenantProvisioningException(
            TenantProvisioningErrorCode code,
            String message
    ) {
        super(message);
        this.code = code;
    }

    public TenantProvisioningErrorCode getCode() {
        return code;
    }
}

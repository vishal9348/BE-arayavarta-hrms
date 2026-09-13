package com.aryavarta.identity.mapper;

import com.aryavarta.identity.entity.IdentityProvider;
import com.aryavarta.identity.record.identityprovider.IdentityProviderResponse;
import org.springframework.stereotype.Component;

@Component
public class IdentityProviderMapper {

    public IdentityProviderResponse toResponse(IdentityProvider entity) {
        return new IdentityProviderResponse(
                entity.getId(),
                entity.getTenantId(),
                entity.getName(),
                entity.getType(),
                entity.getIssuerUrl(),
                entity.getClientId(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getVersion()
        );
    }
}
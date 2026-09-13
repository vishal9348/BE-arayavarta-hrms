package com.aryavarta.identity.mapper;

import com.aryavarta.identity.entity.MfaConfiguration;
import com.aryavarta.identity.record.mfa.MfaResponse;
import org.springframework.stereotype.Component;

@Component
public class MfaConfigurationMapper {

    public MfaResponse toResponse(MfaConfiguration entity) {
        return new MfaResponse(
                entity.getId(),
                entity.getUserId(),
                entity.getType(),
                entity.getStatus(),
                entity.isPrimary(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
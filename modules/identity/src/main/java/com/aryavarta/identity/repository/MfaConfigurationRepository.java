package com.aryavarta.identity.repository;

import com.aryavarta.identity.entity.MfaConfiguration;
import com.aryavarta.identity.entity.enums.MfaStatus;
import com.aryavarta.identity.entity.enums.MfaType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MfaConfigurationRepository extends JpaRepository<MfaConfiguration, UUID> {

    Optional<MfaConfiguration> findByIdAndTenantId(UUID id, UUID tenantId);

    List<MfaConfiguration> findAllByTenantIdAndUserId(UUID tenantId, UUID userId);

    List<MfaConfiguration> findAllByTenantIdAndUserIdAndStatus(UUID tenantId, UUID userId, MfaStatus status);

    Optional<MfaConfiguration> findByTenantIdAndUserIdAndType(UUID tenantId, UUID userId, MfaType type);

    boolean existsByTenantIdAndUserIdAndType(UUID tenantId, UUID userId, MfaType type);

    void deleteAllByTenantIdAndUserId(UUID tenantId, UUID userId);
}
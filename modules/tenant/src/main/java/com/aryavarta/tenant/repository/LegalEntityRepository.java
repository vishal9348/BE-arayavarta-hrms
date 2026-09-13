package com.aryavarta.tenant.repository;

import com.aryavarta.tenant.entity.LegalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LegalEntityRepository extends JpaRepository<LegalEntity, UUID> {

    Optional<LegalEntity> findByIdAndTenantId(UUID id, UUID tenantId);

    Optional<LegalEntity> findByTenantIdAndCode(UUID tenantId, String code);

    boolean existsByTenantIdAndCode(UUID tenantId, String code);
}
package com.aryavarta.tenant.repository;

import com.aryavarta.tenant.entity.TenantSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TenantSettingRepository extends JpaRepository<TenantSetting, UUID> {

    Optional<TenantSetting> findByIdAndTenantId(UUID id, UUID tenantId);

    Optional<TenantSetting> findByTenantIdAndKey(UUID tenantId, String key);

    boolean existsByTenantIdAndKey(UUID tenantId, String key);
}
package com.aryavarta.identity.repository;

import com.aryavarta.identity.entity.IdentityProvider;
import com.aryavarta.identity.entity.enums.IdentityProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IdentityProviderRepository extends JpaRepository<IdentityProvider, UUID> {

    Optional<IdentityProvider> findByIdAndTenantId(UUID id, UUID tenantId);

    Optional<IdentityProvider> findByTenantIdAndName(UUID tenantId, String name);

    List<IdentityProvider> findAllByTenantId(UUID tenantId);

    List<IdentityProvider> findAllByTenantIdAndType(UUID tenantId, IdentityProviderType type);

    List<IdentityProvider> findAllByTenantIdAndActive(UUID tenantId, boolean active);

    boolean existsByTenantIdAndName(UUID tenantId, String name);
}
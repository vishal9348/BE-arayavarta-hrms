package com.aryavarta.identity.repository;

import com.aryavarta.identity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByIdAndTenantId(UUID id, UUID tenantId);

    Optional<Role> findByTenantIdAndName(UUID tenantId, String name);

    List<Role> findAllByTenantId(UUID tenantId);

    List<Role> findAllByTenantIdAndSystemFlag(UUID tenantId, boolean systemFlag);

    boolean existsByTenantIdAndName(UUID tenantId, String name);
}
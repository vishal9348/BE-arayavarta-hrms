package com.aryavarta.identity.repository;

import com.aryavarta.identity.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

    Optional<UserRole> findByIdAndTenantId(UUID id, UUID tenantId);

    List<UserRole> findAllByTenantIdAndUserId(UUID tenantId, UUID userId);

    List<UserRole> findAllByTenantIdAndRoleId(UUID tenantId, UUID roleId);

    Optional<UserRole> findByTenantIdAndUserIdAndRoleId(UUID tenantId, UUID userId, UUID roleId);

    boolean existsByTenantIdAndUserIdAndRoleId(UUID tenantId, UUID userId, UUID roleId);

    void deleteByTenantIdAndUserIdAndRoleId(UUID tenantId, UUID userId, UUID roleId);

    void deleteAllByTenantIdAndUserId(UUID tenantId, UUID userId);
}
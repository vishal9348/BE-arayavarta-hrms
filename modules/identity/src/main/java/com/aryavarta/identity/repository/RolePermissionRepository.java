package com.aryavarta.identity.repository;

import com.aryavarta.identity.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RolePermissionRepository extends JpaRepository<RolePermission, UUID> {

    Optional<RolePermission> findByIdAndTenantId(UUID id, UUID tenantId);

    List<RolePermission> findAllByTenantIdAndRoleId(UUID tenantId, UUID roleId);

    List<RolePermission> findAllByTenantIdAndPermissionId(UUID tenantId, UUID permissionId);

    Optional<RolePermission> findByTenantIdAndRoleIdAndPermissionId(UUID tenantId, UUID roleId, UUID permissionId);

    boolean existsByTenantIdAndRoleIdAndPermissionId(UUID tenantId, UUID roleId, UUID permissionId);

    void deleteByTenantIdAndRoleIdAndPermissionId(UUID tenantId, UUID roleId, UUID permissionId);

    void deleteAllByTenantIdAndRoleId(UUID tenantId, UUID roleId);
}
package com.aryavarta.identity.repository;

import com.aryavarta.identity.entity.platform.PlatformRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlatformRolePermissionRepository extends JpaRepository<PlatformRolePermission, UUID> {

    Optional<PlatformRolePermission> findByIdAndRoleId(UUID id, UUID roleId);

    List<PlatformRolePermission> findAllByRoleId(UUID roleId);

    List<PlatformRolePermission> findAllByPermissionId(UUID permissionId);

    Optional<PlatformRolePermission> findByRoleIdAndPermissionId(UUID roleId, UUID permissionId);

    boolean existsByRoleIdAndPermissionId(UUID roleId, UUID permissionId);

    void deleteByRoleIdAndPermissionId(UUID roleId, UUID permissionId);

    void deleteAllByRoleId(UUID roleId);
}

package com.aryavarta.identity.repository;

import com.aryavarta.identity.entity.platform.PlatformUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlatformUserRoleRepository extends JpaRepository<PlatformUserRole, UUID> {

    Optional<PlatformUserRole> findByIdAndUserId(UUID id, UUID userId);

    List<PlatformUserRole> findAllByUserId(UUID userId);

    List<PlatformUserRole> findAllByRoleId(UUID roleId);

    Optional<PlatformUserRole> findByUserIdAndRoleId(UUID userId, UUID roleId);

    boolean existsByUserIdAndRoleId(UUID userId, UUID roleId);

    void deleteByUserIdAndRoleId(UUID userId, UUID roleId);

    void deleteAllByUserId(UUID userId);
}
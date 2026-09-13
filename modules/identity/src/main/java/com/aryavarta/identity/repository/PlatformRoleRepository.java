package com.aryavarta.identity.repository;

import com.aryavarta.identity.entity.platform.PlatformRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlatformRoleRepository extends JpaRepository<PlatformRole, UUID> {

    Optional<PlatformRole> findByName(String name);

    List<PlatformRole> findAllBySystemFlag(boolean systemFlag);

    boolean existsByName(String name);
}

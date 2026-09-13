package com.aryavarta.identity.repository;


import com.aryavarta.identity.entity.platform.PlatformUser;
import com.aryavarta.identity.entity.platform.PlatformUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlatformUserRepository extends JpaRepository<PlatformUser, UUID> {

    Optional<PlatformUser> findByUsername(String username);

    Optional<PlatformUser> findByEmail(String email);

    Optional<PlatformUser> findByIdAndStatus(UUID id, PlatformUserStatus status);

    List<PlatformUser> findAllByStatus(PlatformUserStatus status);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsAnyBy();
}
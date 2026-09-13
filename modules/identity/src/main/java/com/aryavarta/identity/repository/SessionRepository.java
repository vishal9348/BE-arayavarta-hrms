package com.aryavarta.identity.repository;

import com.aryavarta.identity.entity.Session;
import com.aryavarta.identity.entity.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {

    Optional<Session> findByIdAndTenantId(UUID id, UUID tenantId);

    Optional<Session> findByTenantIdAndTokenHash(UUID tenantId, String tokenHash);

    List<Session> findAllByTenantIdAndUserId(UUID tenantId, UUID userId);

    List<Session> findAllByTenantIdAndUserIdAndStatus(UUID tenantId, UUID userId, SessionStatus status);

    List<Session> findAllByTenantIdAndStatus(UUID tenantId, SessionStatus status);

    List<Session> findAllByTenantIdAndExpiresAtBefore(UUID tenantId, OffsetDateTime dateTime);

    boolean existsByTenantIdAndTokenHash(UUID tenantId, String tokenHash);

    void deleteAllByTenantIdAndUserId(UUID tenantId, UUID userId);
}
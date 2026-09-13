package com.aryavarta.identity.repository;

import com.aryavarta.identity.entity.AccessReview;
import com.aryavarta.identity.entity.enums.AccessReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccessReviewRepository extends JpaRepository<AccessReview, UUID> {

    Optional<AccessReview> findByIdAndTenantId(UUID id, UUID tenantId);

    List<AccessReview> findAllByTenantId(UUID tenantId);

    List<AccessReview> findAllByTenantIdAndStatus(UUID tenantId, AccessReviewStatus status);

    List<AccessReview> findAllByTenantIdAndReviewerUserId(UUID tenantId, UUID reviewerUserId);

    List<AccessReview> findAllByTenantIdAndDueAtBefore(UUID tenantId, OffsetDateTime dateTime);

    boolean existsByTenantIdAndName(UUID tenantId, String name);
}
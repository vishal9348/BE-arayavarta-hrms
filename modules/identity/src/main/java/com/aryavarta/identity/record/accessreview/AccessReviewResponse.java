package com.aryavarta.identity.record.accessreview;

import com.aryavarta.identity.entity.enums.AccessReviewStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AccessReviewResponse(
        UUID id,
        UUID tenantId,
        String name,
        UUID reviewerUserId,
        AccessReviewStatus status,
        OffsetDateTime dueAt,
        OffsetDateTime completedAt,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Long version
) {
}
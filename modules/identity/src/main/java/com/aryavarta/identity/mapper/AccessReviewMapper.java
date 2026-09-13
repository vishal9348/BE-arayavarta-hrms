package com.aryavarta.identity.mapper;

import com.aryavarta.identity.entity.AccessReview;
import com.aryavarta.identity.record.accessreview.AccessReviewResponse;
import org.springframework.stereotype.Component;

@Component
public class AccessReviewMapper {

    public AccessReviewResponse toResponse(AccessReview entity) {
        return new AccessReviewResponse(
                entity.getId(),
                entity.getTenantId(),
                entity.getName(),
                entity.getReviewerUserId(),
                entity.getStatus(),
                entity.getDueAt(),
                entity.getCompletedAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getVersion()
        );
    }
}
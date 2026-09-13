package com.aryavarta.identity.entity;

import com.aryavarta.identity.entity.enums.AccessReviewStatus;
import com.aryavarta.infrastructure.persistence.entity.TenantAwareEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "access_review", indexes = {@Index(name = "idx_access_review_tenant_id", columnList = "tenant_id"),
        @Index(name = "idx_access_review_subject_user_id", columnList = "subject_user_id"),
        @Index(name = "idx_access_review_reviewer_user_id", columnList = "reviewer_user_id"),
        @Index(name = "idx_access_review_status", columnList = "status"),
        @Index(name = "idx_access_review_due_at", columnList = "due_at")})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AccessReview extends TenantAwareEntity {

    /**
     * User whose access is being reviewed.
     */
    @Column(name = "subject_user_id", nullable = false, updatable = false)
    private UUID subjectUserId;

    /**
     * User responsible for performing the access review.
     */
    @Column(name = "reviewer_user_id", nullable = false, updatable = false)
    private UUID reviewerUserId;

    /**
     * Optional role being reviewed.
     * Null when the review covers the user's complete access.
     */
    @Column(name = "role_id")
    private UUID roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private AccessReviewStatus status;

    @Column(name = "reason", length = 500)
    private String reason;

    @Column(name = "due_at", nullable = false)
    private OffsetDateTime dueAt;

    @Column(name = "reviewed_at")
    private OffsetDateTime reviewedAt;

    /**
     * Reviewer decision or additional review comments.
     */
    @Column(name = "comments", length = 2000)
    private String comments;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "completed_at")
    private OffsetDateTime completedAt;
}

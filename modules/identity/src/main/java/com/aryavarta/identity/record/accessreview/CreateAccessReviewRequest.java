package com.aryavarta.identity.record.accessreview;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateAccessReviewRequest(

        @NotBlank
        @Size(max = 200)
        String name,

        @NotNull
        UUID reviewerUserId,

        @NotNull
        OffsetDateTime dueAt
) {
}
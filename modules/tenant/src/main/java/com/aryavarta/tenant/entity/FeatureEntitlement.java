package com.aryavarta.tenant.entity;

import com.aryavarta.infrastructure.persistence.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(
        name = "feature_entitlement",
        indexes = {
                @Index(
                        name = "idx_feature_entitlement_plan_id",
                        columnList = "plan_id"
                ),
                @Index(
                        name = "idx_feature_entitlement_feature_code",
                        columnList = "feature_code"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_feature_entitlement_plan_feature",
                        columnNames = {
                                "plan_id",
                                "feature_code"
                        }
                )
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FeatureEntitlement extends BaseEntity {

    /**
     * Reference to the subscription plan.
     */
    @Column(
            name = "plan_id",
            nullable = false,
            updatable = false
    )
    private UUID planId;

    /**
     * Unique feature identifier, for example:
     * EMPLOYEE_MANAGEMENT
     * PAYROLL
     * ATTENDANCE
     * RECRUITMENT
     * PERFORMANCE
     */
    @Column(
            name = "feature_code",
            nullable = false,
            length = 100
    )
    private String featureCode;

    @Column(
            name = "enabled",
            nullable = false
    )
    private boolean enabled;

    /**
     * Optional numeric limit associated with the feature.
     * Example: maximum number of employees or users.
     */
    @Column(
            name = "limit_value"
    )
    private Long limitValue;

    /**
     * Optional textual unit for the limit.
     * Example: EMPLOYEES, USERS, GB, TRANSACTIONS.
     */
    @Column(
            name = "limit_unit",
            length = 50
    )
    private String limitUnit;

    @Column(
            name = "description",
            length = 500
    )
    private String description;
}

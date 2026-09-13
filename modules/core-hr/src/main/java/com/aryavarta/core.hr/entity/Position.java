package com.aryavarta.core.hr.entity;

import com.aryavarta.core.hr.entity.enums.PositionStatus;
import com.aryavarta.infrastructure.persistence.entity.TenantAwareEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
        name = "position",
        indexes = {
                @Index(
                        name = "idx_position_tenant_id",
                        columnList = "tenant_id"
                ),
                @Index(
                        name = "idx_position_tenant_code",
                        columnList = "tenant_id, code"
                ),
                @Index(
                        name = "idx_position_org_unit_id",
                        columnList = "org_unit_id"
                ),
                @Index(
                        name = "idx_position_job_family_id",
                        columnList = "job_family_id"
                ),
                @Index(
                        name = "idx_position_grade_id",
                        columnList = "grade_id"
                ),
                @Index(
                        name = "idx_position_status",
                        columnList = "tenant_id, status"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_position_tenant_code",
                        columnNames = {
                                "tenant_id",
                                "code"
                        }
                )
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Position extends TenantAwareEntity {

    @Column(
            name = "code",
            nullable = false,
            length = 50
    )
    private String code;

    @Column(
            name = "title",
            nullable = false,
            length = 200
    )
    private String title;

    /**
     * Organizational unit that owns this position.
     */
    @Column(
            name = "org_unit_id",
            nullable = false
    )
    private UUID orgUnitId;

    /**
     * Job family associated with this position.
     */
    @Column(
            name = "job_family_id"
    )
    private UUID jobFamilyId;

    /**
     * Grade associated with this position.
     */
    @Column(
            name = "grade_id"
    )
    private UUID gradeId;

    @Column(
            name = "description",
            length = 1000
    )
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 30
    )
    private PositionStatus status;

    @Column(
            name = "budgeted_headcount",
            nullable = false
    )
    private Integer budgetedHeadcount;

    @Column(
            name = "filled_headcount",
            nullable = false
    )
    private Integer filledHeadcount;

    @Column(
            name = "minimum_salary",
            precision = 18,
            scale = 2
    )
    private BigDecimal minimumSalary;

    @Column(
            name = "maximum_salary",
            precision = 18,
            scale = 2
    )
    private BigDecimal maximumSalary;

    @Column(
            name = "active",
            nullable = false
    )
    private boolean active;
}

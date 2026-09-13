package com.aryavarta.core.hr.entity;

import com.aryavarta.infrastructure.persistence.entity.TenantAwareEntity;
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

import java.math.BigDecimal;

@Entity
@Table(
        name = "grade",
        indexes = {
                @Index(
                        name = "idx_grade_tenant_id",
                        columnList = "tenant_id"
                ),
                @Index(
                        name = "idx_grade_tenant_code",
                        columnList = "tenant_id, code"
                ),
                @Index(
                        name = "idx_grade_level",
                        columnList = "tenant_id, level"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_grade_tenant_code",
                        columnNames = {
                                "tenant_id",
                                "code"
                        }
                ),
                @UniqueConstraint(
                        name = "uk_grade_tenant_level",
                        columnNames = {
                                "tenant_id",
                                "level"
                        }
                )
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Grade extends TenantAwareEntity {

    @Column(
            name = "code",
            nullable = false,
            length = 50
    )
    private String code;

    @Column(
            name = "name",
            nullable = false,
            length = 150
    )
    private String name;

    @Column(
            name = "level",
            nullable = false
    )
    private Integer level;

    @Column(
            name = "description",
            length = 500
    )
    private String description;

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
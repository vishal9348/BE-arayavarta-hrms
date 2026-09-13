package com.aryavarta.tenant.entity;

import com.aryavarta.infrastructure.persistence.entity.BaseEntity;
import com.aryavarta.tenant.entity.enums.BillingCycle;
import com.aryavarta.tenant.entity.enums.PlanStatus;
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

@Entity
@Table(
        name = "plan",
        indexes = {
                @Index(
                        name = "idx_plan_code",
                        columnList = "code"
                ),
                @Index(
                        name = "idx_plan_status",
                        columnList = "status"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_plan_code",
                        columnNames = "code"
                )
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Plan extends BaseEntity {

    @Column(
            name = "code",
            nullable = false,
            length = 50
    )
    private String code;

    @Column(
            name = "name",
            nullable = false,
            length = 100
    )
    private String name;

    @Column(
            name = "description",
            length = 500
    )
    private String description;

    @Column(
            name = "price",
            nullable = false,
            precision = 18,
            scale = 2
    )
    private BigDecimal price;

    @Column(
            name = "currency",
            nullable = false,
            length = 3
    )
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "billing_cycle",
            nullable = false,
            length = 30
    )
    private BillingCycle billingCycle;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 30
    )
    private PlanStatus status;
}
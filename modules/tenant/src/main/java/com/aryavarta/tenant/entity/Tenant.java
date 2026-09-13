package com.aryavarta.tenant.entity;

import com.aryavarta.infrastructure.persistence.entity.BaseEntity;
import com.aryavarta.tenant.entity.enums.TenantStatus;
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

@Entity
@Table(
        name = "tenant",
        indexes = {
                @Index(
                        name = "idx_tenant_code",
                        columnList = "code"
                ),
                @Index(
                        name = "idx_tenant_status",
                        columnList = "status"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_tenant_code",
                        columnNames = "code"
                )
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Tenant extends BaseEntity {

    @Column(
            name = "code",
            nullable = false,
            length = 50
    )
    private String code;

    @Column(
            name = "name",
            nullable = false,
            length = 200
    )
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 30
    )
    private TenantStatus status;

    @Column(
            name = "timezone",
            nullable = false,
            length = 50
    )
    private String timezone;

    @Column(
            name = "locale",
            nullable = false,
            length = 20
    )
    private String locale;

    @Column(
            name = "currency",
            nullable = false,
            length = 3
    )
    private String currency;

    /**
     * Subscription plan associated with the tenant.
     */
    @Column(
            name = "plan_id"
    )
    private java.util.UUID planId;
}
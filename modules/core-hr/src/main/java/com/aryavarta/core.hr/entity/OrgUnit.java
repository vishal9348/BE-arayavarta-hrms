package com.aryavarta.core.hr.entity;

import com.aryavarta.core.hr.entity.enums.OrgUnitType;
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

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "org_unit",
        indexes = {
                @Index(
                        name = "idx_org_unit_tenant_id",
                        columnList = "tenant_id"
                ),
                @Index(
                        name = "idx_org_unit_tenant_code",
                        columnList = "tenant_id, code"
                ),
                @Index(
                        name = "idx_org_unit_parent_id",
                        columnList = "parent_id"
                ),
                @Index(
                        name = "idx_org_unit_type",
                        columnList = "tenant_id, type"
                ),
                @Index(
                        name = "idx_org_unit_effective_from",
                        columnList = "tenant_id, effective_from"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_org_unit_tenant_code",
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
public class OrgUnit extends TenantAwareEntity {

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
            name = "type",
            nullable = false,
            length = 30
    )
    private OrgUnitType type;

    /**
     * Parent organizational unit.
     * <p>
     * Kept as UUID to avoid a recursive JPA relationship and to keep
     * organizational hierarchy management explicit.
     */
    @Column(
            name = "parent_id"
    )
    private UUID parentId;

    /**
     * Optional head/owner of the organizational unit.
     */
    @Column(
            name = "head_employee_id"
    )
    private UUID headEmployeeId;

    @Column(
            name = "description",
            length = 500
    )
    private String description;

    @Column(
            name = "effective_from",
            nullable = false
    )
    private LocalDate effectiveFrom;

    @Column(
            name = "effective_to"
    )
    private LocalDate effectiveTo;

    @Column(
            name = "active",
            nullable = false
    )
    private boolean active;
}

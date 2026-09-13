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

@Entity
@Table(
        name = "job_family",
        indexes = {
                @Index(
                        name = "idx_job_family_tenant_id",
                        columnList = "tenant_id"
                ),
                @Index(
                        name = "idx_job_family_tenant_code",
                        columnList = "tenant_id, code"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_job_family_tenant_code",
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
public class JobFamily extends TenantAwareEntity {

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
            name = "description",
            length = 500
    )
    private String description;

    @Column(
            name = "active",
            nullable = false
    )
    private boolean active;
}

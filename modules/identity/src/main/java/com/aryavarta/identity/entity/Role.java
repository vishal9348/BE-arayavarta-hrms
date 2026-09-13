package com.aryavarta.identity.entity;

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
        name = "role",
        indexes = {
                @Index(
                        name = "idx_role_tenant_id",
                        columnList = "tenant_id"
                ),
                @Index(
                        name = "idx_role_tenant_name",
                        columnList = "tenant_id, name"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_role_tenant_name",
                        columnNames = {
                                "tenant_id",
                                "name"
                        }
                )
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends TenantAwareEntity {

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
            name = "system_flag",
            nullable = false
    )
    private boolean systemFlag;
}
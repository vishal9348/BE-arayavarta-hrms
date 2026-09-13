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

import java.util.UUID;

@Entity
@Table(
        name = "role_permission",
        indexes = {
                @Index(
                        name = "idx_role_permission_tenant_id",
                        columnList = "tenant_id"
                ),
                @Index(
                        name = "idx_role_permission_role_id",
                        columnList = "role_id"
                ),
                @Index(
                        name = "idx_role_permission_permission_id",
                        columnList = "permission_id"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_role_permission_role_permission",
                        columnNames = {
                                "tenant_id",
                                "role_id",
                                "permission_id"
                        }
                )
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission extends TenantAwareEntity {

    @Column(
            name = "role_id",
            nullable = false,
            updatable = false
    )
    private UUID roleId;

    @Column(
            name = "permission_id",
            nullable = false,
            updatable = false
    )
    private UUID permissionId;
}
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
        name = "user_role",
        indexes = {
                @Index(
                        name = "idx_user_role_tenant_id",
                        columnList = "tenant_id"
                ),
                @Index(
                        name = "idx_user_role_user_id",
                        columnList = "user_id"
                ),
                @Index(
                        name = "idx_user_role_role_id",
                        columnList = "role_id"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_role_user_role",
                        columnNames = {
                                "tenant_id",
                                "user_id",
                                "role_id"
                        }
                )
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserRole extends TenantAwareEntity {

    @Column(
            name = "user_id",
            nullable = false,
            updatable = false
    )
    private UUID userId;

    @Column(
            name = "role_id",
            nullable = false,
            updatable = false
    )
    private UUID roleId;
}
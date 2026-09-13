package com.aryavarta.identity.entity;

import com.aryavarta.identity.entity.enums.AuthProvider;
import com.aryavarta.identity.entity.enums.UserAccountStatus;
import com.aryavarta.infrastructure.persistence.entity.TenantAwareEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(
        name = "user_account",
        indexes = {
                @Index(
                        name = "idx_user_account_tenant_id",
                        columnList = "tenant_id"
                ),
                @Index(
                        name = "idx_user_account_tenant_username",
                        columnList = "tenant_id, username"
                ),
                @Index(
                        name = "idx_user_account_tenant_email",
                        columnList = "tenant_id, email"
                ),
                @Index(
                        name = "idx_user_account_employee_id",
                        columnList = "employee_id"
                )
        },
        uniqueConstraints = {
                @jakarta.persistence.UniqueConstraint(
                        name = "uk_user_account_tenant_username",
                        columnNames = {
                                "tenant_id",
                                "username"
                        }
                ),
                @jakarta.persistence.UniqueConstraint(
                        name = "uk_user_account_tenant_email",
                        columnNames = {
                                "tenant_id",
                                "email"
                        }
                )
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount extends TenantAwareEntity {

    @Column(
            name = "username",
            nullable = false,
            length = 100
    )
    private String username;

    @Column(
            name = "email",
            nullable = false,
            length = 255
    )
    private String email;

    @Column(
            name = "password_hash",
            length = 255
    )
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 30
    )
    private UserAccountStatus status;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "auth_provider",
            nullable = false,
            length = 30
    )
    private AuthProvider authProvider;

    /**
     * Reference to the employee associated with this user account.
     * <p>
     * Kept as UUID instead of a JPA relationship because Employee
     * belongs to the Core HR module.
     */
    @Column(
            name = "employee_id"
    )
    private UUID employeeId;
}
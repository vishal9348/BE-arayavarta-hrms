package com.aryavarta.identity.entity;

import com.aryavarta.identity.entity.enums.MfaStatus;
import com.aryavarta.identity.entity.enums.MfaType;
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

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "mfa_configuration", indexes = {@Index(name = "idx_mfa_configuration_tenant_id", columnList = "tenant_id"),
        @Index(name = "idx_mfa_configuration_user_id", columnList = "user_id"),
        @Index(name = "idx_mfa_configuration_status", columnList = "status")},
        uniqueConstraints = {@UniqueConstraint(name = "uk_mfa_configuration_user_type", columnNames = {"tenant_id", "user_id", "type"})})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MfaConfiguration extends TenantAwareEntity {

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 30)
    private MfaType type;

    /**
     * Encrypted secret associated with the MFA configuration.
     * <p>
     * The value must never contain a plaintext MFA secret in logs,
     * API responses, or audit records.
     */
    @Column(name = "secret", length = 1024)
    private String secret;

    /**
     * Encrypted recovery codes.
     */
    @Column(name = "recovery_codes", columnDefinition = "TEXT")
    private String recoveryCodes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private MfaStatus status;

    @Column(name = "verified_at")
    private OffsetDateTime verifiedAt;

    @Column(name = "last_used_at")
    private OffsetDateTime lastUsedAt;

    @Column(name = "is_primary", nullable = false)
    private boolean primary;
}
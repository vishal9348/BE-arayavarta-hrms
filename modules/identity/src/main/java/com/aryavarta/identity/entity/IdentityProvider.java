package com.aryavarta.identity.entity;

import com.aryavarta.identity.entity.enums.IdentityProviderType;
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

@Entity
@Table(name = "identity_provider", indexes = {@Index(name = "idx_identity_provider_tenant_id", columnList = "tenant_id"), @Index(name = "idx_identity_provider_type", columnList = "type"), @Index(name = "idx_identity_provider_status", columnList = "status")}, uniqueConstraints = {@UniqueConstraint(name = "uk_identity_provider_tenant_name", columnNames = {"tenant_id", "name"})})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class IdentityProvider extends TenantAwareEntity {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 30)
    private IdentityProviderType type;

    @Column(name = "issuer", length = 500)
    private String issuer;

    @Column(name = "client_id", length = 255)
    private String clientId;

    /**
     * Encrypted client secret.
     * Never expose this value through API responses or logs.
     */
    @Column(name = "client_secret", length = 1024)
    private String clientSecret;

    @Column(name = "authorization_url", length = 1000)
    private String authorizationUrl;

    @Column(name = "token_url", length = 1000)
    private String tokenUrl;

    @Column(name = "user_info_url", length = 1000)
    private String userInfoUrl;

    @Column(name = "jwks_url", length = 1000)
    private String jwksUrl;

    @Column(name = "metadata_url", length = 1000)
    private String metadataUrl;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "issuer_url", nullable = false, length = 500)
    private String issuerUrl;

    @Column(name = "active", nullable = false)
    private boolean active;
}

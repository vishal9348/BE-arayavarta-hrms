package com.aryavarta.identity.entity;

import com.aryavarta.identity.entity.enums.SessionStatus;
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

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "session",
        indexes = {
                @Index(
                        name = "idx_session_tenant_id",
                        columnList = "tenant_id"
                ),
                @Index(
                        name = "idx_session_user_id",
                        columnList = "user_id"
                ),
                @Index(
                        name = "idx_session_token_hash",
                        columnList = "token_hash"
                ),
                @Index(
                        name = "idx_session_expires_at",
                        columnList = "expires_at"
                ),
                @Index(
                        name = "idx_session_status",
                        columnList = "status"
                )
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Session extends TenantAwareEntity {

    @Column(
            name = "user_id",
            nullable = false,
            updatable = false
    )
    private UUID userId;

    /**
     * Store only the hash of the session token.
     * Never persist the raw authentication/session token.
     */
    @Column(
            name = "token_hash",
            nullable = false,
            unique = true,
            length = 255
    )
    private String tokenHash;

    @Column(
            name = "ip_address",
            length = 45
    )
    private String ipAddress;

    @Column(
            name = "user_agent",
            length = 1000
    )
    private String userAgent;

    @Column(
            name = "device_id",
            length = 255
    )
    private String deviceId;

    @Column(
            name = "created_at_client",
            updatable = false
    )
    private OffsetDateTime createdAtClient;

    @Column(
            name = "last_accessed_at"
    )
    private OffsetDateTime lastAccessedAt;

    @Column(
            name = "expires_at",
            nullable = false
    )
    private OffsetDateTime expiresAt;

    @Column(
            name = "revoked_at"
    )
    private OffsetDateTime revokedAt;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 30
    )
    private SessionStatus status;
}
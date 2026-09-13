package com.aryavarta.identity.entity.platform;

import com.aryavarta.infrastructure.persistence.entity.BaseEntity;
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
@Table(name = "platform_user_role", indexes = {@Index(name = "idx_platform_user_role_user_id", columnList = "user_id"),
        @Index(name = "idx_platform_user_role_role_id", columnList = "role_id")},
        uniqueConstraints = {@UniqueConstraint(name = "uk_platform_user_role_user_role", columnNames = {"user_id", "role_id"})})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformUserRole extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "role_id", nullable = false)
    private UUID roleId;
}
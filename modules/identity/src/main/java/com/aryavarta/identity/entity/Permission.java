package com.aryavarta.identity.entity;

import com.aryavarta.infrastructure.persistence.entity.BaseEntity;
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
@Table(name = "permission", indexes = {@Index(name = "idx_permission_code", columnList = "code"),
        @Index(name = "idx_permission_resource_action", columnList = "resource, action")},
        uniqueConstraints = {@UniqueConstraint(name = "uk_permission_code", columnNames = "code")})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends BaseEntity {

    @Column(name = "code", nullable = false, updatable = false, length = 150)
    private String code;

    @Column(name = "resource", nullable = false, length = 100)
    private String resource;

    @Column(name = "action", nullable = false, length = 50)
    private String action;

    @Column(name = "description", length = 500)
    private String description;
    @Column(name = "name", nullable = false, length = 200)
    private String name;
}
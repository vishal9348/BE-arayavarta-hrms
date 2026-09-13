package com.aryavarta.tenant.entity;

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
@Table(name = "tenant_setting", indexes = {@Index(name = "idx_tenant_setting_tenant_id", columnList = "tenant_id"), @Index(name = "idx_tenant_setting_key", columnList = "tenant_id, setting_key")}, uniqueConstraints = {@UniqueConstraint(name = "uk_tenant_setting_tenant_key", columnNames = {"tenant_id", "setting_key"})})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TenantSetting extends TenantAwareEntity {

    @Column(name = "setting_key", nullable = false, length = 150)
    private String settingKey;

    @Column(name = "setting_value", columnDefinition = "TEXT")
    private String settingValue;

    @Column(name = "data_type", nullable = false, length = 30)
    private String dataType;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "encrypted", nullable = false)
    private boolean encrypted;
}

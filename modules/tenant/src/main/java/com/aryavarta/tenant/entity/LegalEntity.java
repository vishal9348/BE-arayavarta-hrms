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
@Table(name = "legal_entity", indexes = {@Index(name = "idx_legal_entity_tenant_id", columnList = "tenant_id"), @Index(name = "idx_legal_entity_tenant_name", columnList = "tenant_id, name"), @Index(name = "idx_legal_entity_registration_number", columnList = "registration_number")}, uniqueConstraints = {@UniqueConstraint(name = "uk_legal_entity_tenant_code", columnNames = {"tenant_id", "code"}), @UniqueConstraint(name = "uk_legal_entity_tenant_registration_number", columnNames = {"tenant_id", "registration_number"})})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LegalEntity extends TenantAwareEntity {

    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "legal_name", nullable = false, length = 300)
    private String legalName;

    @Column(name = "registration_number", nullable = false, length = 100)
    private String registrationNumber;

    @Column(name = "tax_identification_number", length = 100)
    private String taxIdentificationNumber;

    @Column(name = "country_code", nullable = false, length = 2)
    private String countryCode;

    @Column(name = "state_code", length = 20)
    private String stateCode;

    @Column(name = "address_line1", length = 255)
    private String addressLine1;

    @Column(name = "address_line2", length = 255)
    private String addressLine2;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "contact_email", length = 255)
    private String contactEmail;

    @Column(name = "contact_phone", length = 30)
    private String contactPhone;

    @Column(name = "active", nullable = false)
    private boolean active;
}
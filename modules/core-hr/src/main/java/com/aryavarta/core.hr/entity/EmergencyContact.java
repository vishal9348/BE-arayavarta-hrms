package com.aryavarta.core.hr.entity;

import com.aryavarta.core.hr.entity.enums.EmergencyContactRelationship;
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
        name = "emergency_contact",
        indexes = {
                @Index(
                        name = "idx_emergency_contact_tenant_id",
                        columnList = "tenant_id"
                ),
                @Index(
                        name = "idx_emergency_contact_employee_id",
                        columnList = "tenant_id, employee_id"
                ),
                @Index(
                        name = "idx_emergency_contact_priority",
                        columnList = "tenant_id, employee_id, priority"
                )
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyContact extends TenantAwareEntity {

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "middle_name", length = 100)
    private String middleName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "relationship", nullable = false, length = 30)
    private EmergencyContactRelationship relationship;

    @Column(name = "phone_number", nullable = false, length = 30)
    private String phoneNumber;

    @Column(name = "alternate_phone_number", length = 30)
    private String alternatePhoneNumber;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "address_line1", length = 255)
    private String addressLine1;

    @Column(name = "address_line2", length = 255)
    private String addressLine2;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "country_code", length = 10)
    private String countryCode;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Column(name = "active", nullable = false)
    private boolean active;
}

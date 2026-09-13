package com.aryavarta.core.hr.entity;

import com.aryavarta.core.hr.entity.enums.EmployeeStatus;
import com.aryavarta.core.hr.entity.enums.Gender;
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

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "employee",
        indexes = {
                @Index(
                        name = "idx_employee_tenant_id",
                        columnList = "tenant_id"
                ),
                @Index(
                        name = "idx_employee_employee_code",
                        columnList = "tenant_id, employee_code"
                ),
                @Index(
                        name = "idx_employee_email",
                        columnList = "tenant_id, email"
                ),
                @Index(
                        name = "idx_employee_status",
                        columnList = "tenant_id, status"
                ),
                @Index(
                        name = "idx_employee_org_unit_id",
                        columnList = "org_unit_id"
                ),
                @Index(
                        name = "idx_employee_position_id",
                        columnList = "position_id"
                ),
                @Index(
                        name = "idx_employee_manager_id",
                        columnList = "manager_id"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_employee_tenant_employee_code",
                        columnNames = {
                                "tenant_id",
                                "employee_code"
                        }
                ),
                @UniqueConstraint(
                        name = "uk_employee_tenant_email",
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
public class Employee extends TenantAwareEntity {

    @Column(
            name = "employee_code",
            nullable = false,
            length = 50
    )
    private String employeeCode;

    @Column(
            name = "first_name",
            nullable = false,
            length = 100
    )
    private String firstName;

    @Column(
            name = "middle_name",
            length = 100
    )
    private String middleName;

    @Column(
            name = "last_name",
            nullable = false,
            length = 100
    )
    private String lastName;

    @Column(
            name = "display_name",
            length = 250
    )
    private String displayName;

    @Column(
            name = "email",
            nullable = false,
            length = 255
    )
    private String email;

    @Column(
            name = "personal_email",
            length = 255
    )
    private String personalEmail;

    @Column(
            name = "phone_number",
            length = 30
    )
    private String phoneNumber;

    @Column(
            name = "date_of_birth"
    )
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "gender",
            length = 30
    )
    private Gender gender;

    @Column(
            name = "hire_date",
            nullable = false
    )
    private LocalDate hireDate;

    @Column(
            name = "termination_date"
    )
    private LocalDate terminationDate;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 30
    )
    private EmployeeStatus status;

    /**
     * Reference to the employee's organizational unit.
     */
    @Column(
            name = "org_unit_id"
    )
    private UUID orgUnitId;

    /**
     * Reference to the employee's position.
     */
    @Column(
            name = "position_id"
    )
    private UUID positionId;

    /**
     * Reference to the employee's reporting manager.
     * Self-reference is maintained as UUID to keep the aggregate boundary simple.
     */
    @Column(
            name = "manager_id"
    )
    private UUID managerId;

    /**
     * Reference to the employee's primary work location.
     */
    @Column(
            name = "location_id"
    )
    private UUID locationId;

    /**
     * Reference to the legal entity employing the employee.
     */
    @Column(
            name = "legal_entity_id"
    )
    private UUID legalEntityId;
}

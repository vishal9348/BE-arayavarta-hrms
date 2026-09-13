package com.aryavarta.core.hr.entity;

import com.aryavarta.infrastructure.persistence.entity.TenantAwareEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "experience",
        indexes = {
                @Index(
                        name = "idx_experience_tenant_id",
                        columnList = "tenant_id"
                ),
                @Index(
                        name = "idx_experience_employee_id",
                        columnList = "tenant_id, employee_id"
                ),
                @Index(
                        name = "idx_experience_start_date",
                        columnList = "tenant_id, start_date"
                ),
                @Index(
                        name = "idx_experience_end_date",
                        columnList = "tenant_id, end_date"
                )
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Experience extends TenantAwareEntity {

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "company_name", nullable = false, length = 255)
    private String companyName;

    @Column(name = "job_title", nullable = false, length = 200)
    private String jobTitle;

    @Column(name = "employment_type", length = 50)
    private String employmentType;

    @Column(name = "industry", length = 150)
    private String industry;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "currently_working", nullable = false)
    private boolean currentlyWorking;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "reason_for_leaving", length = 1000)
    private String reasonForLeaving;

    @Column(name = "last_drawn_salary", precision = 18, scale = 2)
    private java.math.BigDecimal lastDrawnSalary;

    @Column(name = "currency_code", length = 10)
    private String currencyCode;

    @Column(name = "reference_name", length = 200)
    private String referenceName;

    @Column(name = "reference_phone", length = 30)
    private String referencePhone;

    @Column(name = "reference_email", length = 255)
    private String referenceEmail;

    @Column(name = "active", nullable = false)
    private boolean active;
}

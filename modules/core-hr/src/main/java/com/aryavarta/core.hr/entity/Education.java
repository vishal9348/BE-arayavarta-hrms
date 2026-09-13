package com.aryavarta.core.hr.entity;

import com.aryavarta.core.hr.entity.enums.EducationLevel;
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

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "education",
        indexes = {
                @Index(
                        name = "idx_education_tenant_id",
                        columnList = "tenant_id"
                ),
                @Index(
                        name = "idx_education_employee_id",
                        columnList = "tenant_id, employee_id"
                ),
                @Index(
                        name = "idx_education_level",
                        columnList = "tenant_id, education_level"
                )
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Education extends TenantAwareEntity {

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_level", nullable = false, length = 50)
    private EducationLevel educationLevel;

    @Column(name = "degree_name", nullable = false, length = 200)
    private String degreeName;

    @Column(name = "field_of_study", length = 200)
    private String fieldOfStudy;

    @Column(name = "institution_name", nullable = false, length = 255)
    private String institutionName;

    @Column(name = "institution_location", length = 255)
    private String institutionLocation;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "graduation_year")
    private Integer graduationYear;

    @Column(name = "grade_or_percentage", length = 50)
    private String gradeOrPercentage;

    @Column(name = "specialization", length = 200)
    private String specialization;

    @Column(name = "certificate_number", length = 100)
    private String certificateNumber;

    @Column(name = "is_highest_qualification", nullable = false)
    private boolean highestQualification;

    @Column(name = "active", nullable = false)
    private boolean active;
}

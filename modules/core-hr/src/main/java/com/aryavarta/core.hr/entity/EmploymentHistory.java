package com.aryavarta.core.hr.entity;

import com.aryavarta.core.hr.entity.enums.EmploymentEventType;
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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(
        name = "employment_history",
        indexes = {
                @Index(
                        name = "idx_employment_history_tenant_id",
                        columnList = "tenant_id"
                ),
                @Index(
                        name = "idx_employment_history_employee_id",
                        columnList = "tenant_id, employee_id"
                ),
                @Index(
                        name = "idx_employment_history_event_type",
                        columnList = "tenant_id, event_type"
                ),
                @Index(
                        name = "idx_employment_history_effective_from",
                        columnList = "tenant_id, effective_from"
                )
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentHistory extends TenantAwareEntity {

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 50)
    private EmploymentEventType eventType;

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "source_reference", length = 200)
    private String sourceReference;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload", columnDefinition = "jsonb")
    private Map<String, Object> payload;
}
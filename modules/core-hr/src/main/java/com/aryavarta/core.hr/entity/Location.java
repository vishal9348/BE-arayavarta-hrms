package com.aryavarta.core.hr.entity;

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
@Table(
        name = "location",
        indexes = {
                @Index(
                        name = "idx_location_tenant_id",
                        columnList = "tenant_id"
                ),
                @Index(
                        name = "idx_location_tenant_code",
                        columnList = "tenant_id, code"
                ),
                @Index(
                        name = "idx_location_country_code",
                        columnList = "country_code"
                ),
                @Index(
                        name = "idx_location_city",
                        columnList = "city"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_location_tenant_code",
                        columnNames = {
                                "tenant_id",
                                "code"
                        }
                )
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Location extends TenantAwareEntity {

    @Column(
            name = "code",
            nullable = false,
            length = 50
    )
    private String code;

    @Column(
            name = "name",
            nullable = false,
            length = 200
    )
    private String name;

    @Column(
            name = "description",
            length = 500
    )
    private String description;

    @Column(
            name = "address_line1",
            length = 255
    )
    private String addressLine1;

    @Column(
            name = "address_line2",
            length = 255
    )
    private String addressLine2;

    @Column(
            name = "city",
            length = 100
    )
    private String city;

    @Column(
            name = "state",
            length = 100
    )
    private String state;

    @Column(
            name = "country_code",
            nullable = false,
            length = 2
    )
    private String countryCode;

    @Column(
            name = "postal_code",
            length = 20
    )
    private String postalCode;

    @Column(
            name = "timezone",
            length = 50
    )
    private String timezone;

    @Column(
            name = "active",
            nullable = false
    )
    private boolean active;

    @Column(
            name = "remote_enabled",
            nullable = false
    )
    private boolean remoteEnabled;
}

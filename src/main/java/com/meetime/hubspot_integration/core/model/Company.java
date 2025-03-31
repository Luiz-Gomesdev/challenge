package com.meetime.hubspot_integration.core.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "companies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private String domain;

    @Column(name = "hubspot_company_id")
    private String hubspotCompanyId;

    private String industry;

    private Integer employeeCount;

    public Company(String name, String domain, String hubspotCompanyId, String industry, Integer employeeCount) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Company name cannot be blank");
        }
        this.name = name;
        this.domain = domain;
        this.hubspotCompanyId = hubspotCompanyId;
        this.industry = industry;
        this.employeeCount = employeeCount;
    }
}

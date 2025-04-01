package com.meetime.hubspot_integration.core.model;

import com.meetime.hubspot_integration.core.model.valueobjects.PhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.*;

@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "hubspot_id", unique = true)
    private String hubspotId;

    @Column(name = "first_name", nullable = false)
    @NotBlank
    @Size(max = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank
    @Size(max = 100)
    private String lastName;

    @Column(nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 255)
    private String email;

    @Embedded
    private PhoneNumber phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ElementCollection
    @CollectionTable(name = "contact_tags", joinColumns = @JoinColumn(name = "contact_id"))
    @Column(name = "tag")
    @Builder.Default
    private List<String> tags = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    @Builder.Default
    private Date updatedAt = new Date();

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private ContactStatus status = ContactStatus.NEW;

    @PrePersist
    @PreUpdate
    private void prePersist() {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (this.status == null) {
            this.status = ContactStatus.NEW;  // Default status for new contacts
        }

        updatedAt = new Date();
    }

    public void updateStatus(ContactStatus newStatus) {
        if (this.status != newStatus) {
            this.status = newStatus;
        }
    }

    public Map<String, Object> toHubSpotProperties() {
        Map<String, Object> properties = new LinkedHashMap<>();
        properties.put("email", email);
        properties.put("firstname", firstName);
        properties.put("lastname", lastName);

        Optional.ofNullable(phone)
                .ifPresent(p -> properties.put("phone", p.formattedValue()));

        Optional.ofNullable(company)
                .map(Company::getName)
                .ifPresent(name -> properties.put("company", name));

        if (!tags.isEmpty()) {
            properties.put("tags", String.join(";", tags));
        }

        return properties;
    }

    public enum ContactStatus {
        NEW, ACTIVE, INACTIVE, ARCHIVED
    }
}
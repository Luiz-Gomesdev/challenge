package com.meetime.hubspot_integration.core.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

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

    @Column(nullable = false, unique = true)
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    @Size(max = 255, message = "Email must be at most 255 characters")
    private String email;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "First name cannot be blank")
    @Size(max = 100, message = "First name must be at most 100 characters")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 100, message = "Last name must be at most 100 characters")
    private String lastName;

    @Column
    @Size(max = 255, message = "Website must be at most 255 characters")
    private String website;

    @Column
    @Size(max = 255, message = "Company name must be at most 255 characters")
    private String company;

    @Column
    @Size(max = 20, message = "Phone number must be at most 20 characters")
    private String phone;
}
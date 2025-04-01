package com.meetime.hubspot_integration.infrastructure.adapters.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contacts")
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column
    private String website;

    @Column
    private String company;

    @Column
    private String phone;
}
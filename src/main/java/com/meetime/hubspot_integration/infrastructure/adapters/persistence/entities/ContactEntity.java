package com.meetime.hubspot_integration.infrastructure.adapters.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactEntity {

    private static final String HUBSPOT_ID_COLUMN = "hubspot_id";
    private static final String EMAIL_COLUMN = "email";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LAST_NAME_COLUMN = "last_name";
    private static final String PHONE_COLUMN = "phone";
    private static final String TAGS_COLUMN = "tags";
    private static final String CREATED_AT_COLUMN = "created_at";
    private static final String UPDATED_AT_COLUMN = "updated_at";
    private static final String STATUS_COLUMN = "status";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = HUBSPOT_ID_COLUMN, unique = true)
    private String hubspotId;

    @Column(name = EMAIL_COLUMN, nullable = false, unique = true)
    private String email;

    @Column(name = FIRST_NAME_COLUMN, nullable = false)
    private String firstName;

    @Column(name = LAST_NAME_COLUMN, nullable = false)
    private String lastName;

    @Column(name = PHONE_COLUMN)
    private String phone;

    @Column(name = TAGS_COLUMN)
    private String tags;

    @Column(name = CREATED_AT_COLUMN)
    private Date createdAt;

    @Column(name = UPDATED_AT_COLUMN)
    private Date updatedAt;

    @Column(name = STATUS_COLUMN)
    private String status;
}

package com.meetime.hubspot_integration.infrastructure.adapters.persistence.repository;

import com.meetime.hubspot_integration.core.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {
    Optional<Contact> findByHubspotId(String hubspotId);
    boolean existsByEmail(String email);
    boolean existsByHubspotId(String hubspotId);
}
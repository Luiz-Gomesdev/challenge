package com.meetime.hubspot_integration.application.ports.outbound;

import com.meetime.hubspot_integration.core.model.Contact;

import java.util.Optional;
import java.util.UUID;

public interface ContactRepositoryPort {
    Contact save(Contact contact);
    Optional<Contact> findById(UUID id);
    Optional<Contact> findByHubspotId(String hubspotId);
    void delete(UUID id);
    boolean existsByEmail(String email);
}

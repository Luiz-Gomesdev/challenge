package com.meetime.hubspot_integration.application.ports;

import com.meetime.hubspot_integration.domain.models.Contact;
import java.util.Optional;

public interface ContactRepositoryPort {
    Contact save(Contact contact);
    Optional<Contact> findById(String id);
    boolean existsByEmail(String email);
}
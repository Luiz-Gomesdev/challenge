package com.meetime.hubspot_integration.infrastructure.adapters.persistence.repository;

import com.meetime.hubspot_integration.core.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
}
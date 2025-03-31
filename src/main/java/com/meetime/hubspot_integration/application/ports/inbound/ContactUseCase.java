package com.meetime.hubspot_integration.application.ports.inbound;

import com.meetime.hubspot_integration.core.model.Company;
import com.meetime.hubspot_integration.core.model.valueobjects.PhoneNumber;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ContactUseCase {
    ResponseEntity<String> createContact(String hubspotId, String firstName, String lastName, String email, PhoneNumber phone, Company company, List<String> tags, String status) throws Exception;
}
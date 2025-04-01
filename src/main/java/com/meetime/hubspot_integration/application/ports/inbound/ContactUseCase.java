package com.meetime.hubspot_integration.application.ports.inbound;

import com.meetime.hubspot_integration.application.dto.ContactDTO;
import org.springframework.http.ResponseEntity;

public interface ContactUseCase {
    ResponseEntity<String> createContact(ContactDTO contactDTO) throws Exception;
}
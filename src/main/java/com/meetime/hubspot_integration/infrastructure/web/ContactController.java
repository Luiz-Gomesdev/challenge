package com.meetime.hubspot_integration.infrastructure.web;

import com.meetime.hubspot_integration.application.dto.ContactDTO;
import com.meetime.hubspot_integration.application.ports.inbound.ContactUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hubspot")
@RequiredArgsConstructor
public class ContactController {

    private final ContactUseCase contactUseCase;

    @PostMapping("/new-contact")
    public ResponseEntity<String> create(@Valid @RequestBody ContactDTO contactDTO) throws Exception {
        return contactUseCase.createContact(
                contactDTO.getHubspotId(),
                contactDTO.getFirstName(),
                contactDTO.getLastName(),
                contactDTO.getEmail(),
                contactDTO.getPhone(),
                contactDTO.getCompany(),
                contactDTO.getTags(),
                contactDTO.getStatus()
        );
    }
}
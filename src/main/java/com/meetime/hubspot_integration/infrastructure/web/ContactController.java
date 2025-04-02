package com.meetime.hubspot_integration.infrastructure.web;

import com.meetime.hubspot_integration.application.dto.ContactDTO;
import com.meetime.hubspot_integration.application.ports.inbound.ContactUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ContactController.BASE_PATH)
@RequiredArgsConstructor
public class ContactController {

    public static final String BASE_PATH = "/api/hubspot";
    private static final String NEW_CONTACT_PATH = "/new-contact";

    private final ContactUseCase contactUseCase;

    @PostMapping(NEW_CONTACT_PATH)
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

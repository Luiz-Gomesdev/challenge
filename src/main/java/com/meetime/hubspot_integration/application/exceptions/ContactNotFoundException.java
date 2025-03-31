package com.meetime.hubspot_integration.application.exceptions;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ContactNotFoundException extends DomainEntityNotFoundException {

    public ContactNotFoundException(UUID contactId) {
        super("Contact", "id", contactId.toString());
    }

    public ContactNotFoundException(String hubspotId) {
        super("Contact", "hubspotId", hubspotId);
    }

    public ContactNotFoundException(String fieldName, Object fieldValue) {
        super("Contact", fieldName, fieldValue.toString());
    }
}

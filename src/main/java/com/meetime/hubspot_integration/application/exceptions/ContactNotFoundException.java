package com.meetime.hubspot_integration.application.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContactNotFoundException extends DomainEntityNotFoundException {

    public ContactNotFoundException(String hubspotId) {
        super("Contact", "hubspotId", hubspotId);
    }
}

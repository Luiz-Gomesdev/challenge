package com.meetime.hubspot_integration.application.exceptions;

public class ContactCreationException extends RuntimeException {
    public ContactCreationException(String message) {
        super(message);
    }
}

package com.meetime.hubspot_integration.application.exceptions;

import lombok.Getter;

@Getter
public class DomainValidationException extends RuntimeException {
    private final String details;

    public DomainValidationException(String message, String details) {
        super(message);
        this.details = details;
    }
}

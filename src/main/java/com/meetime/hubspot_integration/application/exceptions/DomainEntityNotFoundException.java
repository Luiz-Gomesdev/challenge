package com.meetime.hubspot_integration.application.exceptions;

import lombok.Getter;

@Getter
public class DomainEntityNotFoundException extends RuntimeException {
    private final String entityType;
    private final String fieldName;
    private final String fieldValue;

    protected DomainEntityNotFoundException() {
        this.entityType = "Entity";
        this.fieldName = "Unknown";
        this.fieldValue = "N/A";
    }

    public DomainEntityNotFoundException(String entityType, String fieldName, String fieldValue) {
        super("%s not found with %s: %s".formatted(entityType, fieldName, fieldValue));
        this.entityType = entityType;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}

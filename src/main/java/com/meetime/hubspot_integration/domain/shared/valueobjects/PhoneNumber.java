package com.meetime.hubspot_integration.domain.shared.valueobjects;

public record PhoneNumber(String value) {
    public PhoneNumber {
        if (value != null && !value.matches("^\\+?[1-9]\\d{1,14}$")) {
            throw new IllegalArgumentException("Phone must follow E.164 format");
        }
    }
}
package com.meetime.hubspot_integration.domain.shared.valueobjects;

import lombok.NonNull;

public record Email(@NonNull String value) {
    public Email {
        if (!value.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
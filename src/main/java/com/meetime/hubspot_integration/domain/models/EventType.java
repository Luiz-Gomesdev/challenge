package com.meetime.hubspot_integration.domain.models;

public enum EventType {
    CONTACT_CREATED("contact.created"),
    CONTACT_UPDATED("contact.updated"),
    COMPANY_CREATED("company.created");

    private final String webhookValue;

    EventType(String webhookValue) {
        this.webhookValue = webhookValue;
    }

    public static EventType fromWebhookValue(String value) {
        for (EventType type : values()) {
            if (type.webhookValue.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown event type: " + value);
    }
}
package com.meetime.hubspot_integration.core.events;

import java.util.Date;
import java.util.UUID;

public record WebhookReceivedEvent(
        UUID eventId,
        String payload,
        String signature,
        Date timestamp,
        String source
) implements DomainEvent {

    public WebhookReceivedEvent(String payload, String signature) {
        this(
                UUID.randomUUID(),
                payload,
                signature,
                new Date(),
                "HUBSPOT_WEBHOOK"
        );
    }

    @Override
    public String eventType() {
        return "WEBHOOK_RECEIVED";
    }
}
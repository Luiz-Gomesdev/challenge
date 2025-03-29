package com.meetime.hubspot_integration.domain.events;

import com.meetime.hubspot_integration.domain.models.EventType;
import java.time.Instant;
import java.util.Map;

public record WebhookReceivedEvent(
        EventType eventType,
        Map<String, Object> payload,
        Instant occurredAt
) implements DomainEvent {
    public WebhookReceivedEvent(EventType eventType, Map<String, Object> payload) {
        this(eventType, payload, Instant.now());
    }
}
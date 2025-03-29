package com.meetime.hubspot_integration.domain.events;

import java.time.Instant;

public interface DomainEvent {
    Instant occurredAt();
}
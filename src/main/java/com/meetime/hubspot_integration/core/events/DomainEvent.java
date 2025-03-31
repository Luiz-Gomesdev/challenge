package com.meetime.hubspot_integration.core.events;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public interface DomainEvent {

    UUID eventId();

    String eventType();

    Date timestamp();

    default String source() {
        return "SYSTEM";
    }

    default String toLogString() {
        return String.format("[%s][%s] %s",
                eventType(),
                eventId().toString().substring(0, 8),
                timestamp()
        );
    }
}

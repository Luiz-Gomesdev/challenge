package com.meetime.hubspot_integration.application.ports.outbound;

import com.meetime.hubspot_integration.core.events.DomainEvent;

public interface EventPublisherPort {

    void publish(DomainEvent event);

    default void publishAll(Iterable<? extends DomainEvent> events) {
        events.forEach(this::publish);
    }
}

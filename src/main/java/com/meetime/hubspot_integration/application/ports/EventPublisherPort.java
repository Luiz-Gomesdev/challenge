package com.meetime.hubspot_integration.application.ports;

import com.meetime.hubspot_integration.domain.events.DomainEvent;

public interface EventPublisherPort {
    void publish(DomainEvent event);
}
package com.meetime.hubspot_integration.core.events;

import com.meetime.hubspot_integration.core.model.Contact;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record ContactCreatedEvent(
        UUID eventId,
        Contact contact,
        Date timestamp,
        String source,
        String triggeredBy
) implements DomainEvent {

    public ContactCreatedEvent {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }
        eventId = eventId != null ? eventId : UUID.randomUUID();
        timestamp = timestamp != null ? timestamp : new Date();
        source = source != null && !source.isBlank() ? source : "INTERNAL";
        triggeredBy = triggeredBy != null && !triggeredBy.isBlank() ? triggeredBy : "SYSTEM";
    }

    public static ContactCreatedEvent from(Contact contact, String source) {
        return new ContactCreatedEvent(null, contact, null, source, null);
    }

    public static ContactCreatedEvent create(UUID eventId,
                                             Contact contact,
                                             Date timestamp,
                                             String source,
                                             String triggeredBy) {
        return new ContactCreatedEvent(eventId, contact, timestamp, source, triggeredBy);
    }

    @Override
    public String eventType() {
        return "CONTACT_CREATED";
    }

    @Override
    public String toString() {
        return String.format(
                "ContactCreatedEvent[%s] - Type: %s, Source: %s, Contact: %s",
                eventId,
                eventType(),
                source,
                contact.getEmail()
        );
    }

    public String getContactEmail() {
        return contact.getEmail();
    }
}
package com.meetime.hubspot_integration.domain.events;

import com.meetime.hubspot_integration.domain.models.OAuthToken;
import java.time.Instant;

public record OAuthTokenRefreshedEvent(
        OAuthToken newToken,
        OAuthToken oldToken,  // Pode ser null no primeiro token
        Instant occurredAt
) implements DomainEvent {
    public OAuthTokenRefreshedEvent(OAuthToken newToken, OAuthToken oldToken) {
        this(newToken, oldToken, Instant.now());
    }
}
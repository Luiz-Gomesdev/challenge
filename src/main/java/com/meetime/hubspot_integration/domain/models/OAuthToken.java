package com.meetime.hubspot_integration.domain.models;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class OAuthToken {
    private final String accessToken;
    private final String refreshToken;
    private final Instant expiresAt;
    private final String tokenType;

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public boolean isValid() {
        return accessToken != null && !isExpired();
    }
}
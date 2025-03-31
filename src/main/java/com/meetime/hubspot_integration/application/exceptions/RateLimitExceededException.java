package com.meetime.hubspot_integration.application.exceptions;

import lombok.Getter;
import lombok.Builder;

import java.time.Duration;

@Getter
@Builder
public class RateLimitExceededException extends RuntimeException {
    private final Duration retryAfter;

    public RateLimitExceededException(Duration retryAfter) {
        super("HubSpot rate limit exceeded. Please retry after %d seconds".formatted(retryAfter.toSeconds()));
        this.retryAfter = retryAfter;
    }
}

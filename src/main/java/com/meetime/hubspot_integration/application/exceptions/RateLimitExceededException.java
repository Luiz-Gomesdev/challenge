package com.meetime.hubspot_integration.application.exceptions;

import java.time.Duration;

public class RateLimitExceededException extends HubSpotIntegrationException {

    private final Duration retryAfter;

    public RateLimitExceededException(Duration retryAfter) {
        super("Rate limit exceeded. Try again after " + retryAfter.toSeconds() + " seconds");
        this.retryAfter = retryAfter;
    }

    public Duration getRetryAfter() {
        return retryAfter;
    }
}
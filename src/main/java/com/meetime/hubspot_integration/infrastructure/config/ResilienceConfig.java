package com.meetime.hubspot_integration.infrastructure.config;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    private static final int MAX_ATTEMPTS = 3;
    private static final long WAIT_DURATION_SECONDS = 1;

    @Bean
    public Retry hubspotRetry() {
        return Retry.of("hubspotApiRetry", RetryConfig.custom()
                .maxAttempts(MAX_ATTEMPTS)
                .waitDuration(Duration.ofSeconds(WAIT_DURATION_SECONDS))
                .build());
    }
}

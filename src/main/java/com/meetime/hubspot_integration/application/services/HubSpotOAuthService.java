package com.meetime.hubspot_integration.application.services;

import com.meetime.hubspot_integration.application.ports.EventPublisherPort;
import com.meetime.hubspot_integration.application.ports.HubSpotClientPort;
import com.meetime.hubspot_integration.application.ports.OAuthServicePort;
import com.meetime.hubspot_integration.domain.events.OAuthTokenRefreshedEvent;
import com.meetime.hubspot_integration.domain.models.OAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class HubSpotOAuthService implements OAuthServicePort {

    private final HubSpotClientPort hubSpotClient;
    private final EventPublisherPort eventPublisher;
    private OAuthToken currentToken;

    @Override
    public synchronized OAuthToken getCurrentToken() {
        if (!isValidToken(currentToken)) {
            refreshToken();
        }
        return currentToken;
    }

    @Override
    public OAuthToken exchangeCode(String authorizationCode) {
        Objects.requireNonNull(authorizationCode, "Authorization code cannot be null");

        OAuthToken oldToken = this.currentToken;
        this.currentToken = hubSpotClient.exchangeCodeForToken(authorizationCode);

        log.info("New OAuth token obtained via authorization code");
        eventPublisher.publish(new OAuthTokenRefreshedEvent(currentToken, oldToken));

        return currentToken;
    }

    @Override
    public boolean isValidToken(OAuthToken token) {
        return token != null && !token.isExpired();
    }

    private void refreshToken() {
        OAuthToken oldToken = this.currentToken;

        try {
            this.currentToken = hubSpotClient.refreshToken(oldToken);
            log.info("OAuth token refreshed successfully");
            eventPublisher.publish(new OAuthTokenRefreshedEvent(currentToken, oldToken));
        } catch (Exception e) {
            log.error("Failed to refresh OAuth token", e);
            this.currentToken = null;
            throw e;
        }
    }
}
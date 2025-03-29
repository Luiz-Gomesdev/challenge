package com.meetime.hubspot_integration.application.ports;

import com.meetime.hubspot_integration.domain.models.OAuthToken;

public interface OAuthServicePort {
    OAuthToken getCurrentToken();
    OAuthToken exchangeCode(String authorizationCode);
    boolean isValidToken(OAuthToken token);
}
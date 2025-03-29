package com.meetime.hubspot_integration.application.exceptions;

public class OAuthException extends HubSpotIntegrationException {

    public OAuthException(String error, String errorDescription) {
        super(String.format("OAuth error: %s - %s", error, errorDescription));
    }
}
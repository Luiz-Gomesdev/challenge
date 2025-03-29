package com.meetime.hubspot_integration.application.ports;

import com.meetime.hubspot_integration.domain.models.Contact;
import com.meetime.hubspot_integration.domain.models.OAuthToken;

import java.util.Optional;

public interface HubSpotClientPort {
    Contact createContact(Contact contact, OAuthToken token);
    Optional<Contact> findContactById(String id, OAuthToken token);
    OAuthToken refreshToken(OAuthToken expiredToken);
    boolean validateWebhookSignature(String payload, String signature);
    OAuthToken exchangeCodeForToken(String authorizationCode);

}
package com.meetime.hubspot_integration.application.ports.outbound;

import com.meetime.hubspot_integration.application.exceptions.HubSpotIntegrationException;
import com.meetime.hubspot_integration.core.model.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class HubSpotClient implements HubSpotClientPort {

    private static final String CREATE_CONTACT_URL = "/contacts/v1/contact";
    private static final String UPDATE_CONTACT_URL = "/contacts/v1/contact/vid/:vid";
    private static final String DELETE_CONTACT_URL = "/contacts/v1/contact/vid/:vid";

    @Value("${hubspot.api.url}")
    private String hubSpotApiUrl;

    private final RestTemplate restTemplate;

    @Override
    public Contact createContact(Contact contact) throws HubSpotIntegrationException {
        try {
            ResponseEntity<Contact> response = restTemplate.exchange(
                    hubSpotApiUrl + CREATE_CONTACT_URL,
                    HttpMethod.POST,
                    null,  // Passar o corpo com os dados do contato
                    Contact.class
            );
            return response.getBody();
        } catch (Exception e) {
            throw new HubSpotIntegrationException("Error creating contact in HubSpot", e);
        }
    }

    @Override
    public void updateContact(Contact contact) throws HubSpotIntegrationException {
        try {
            String url = hubSpotApiUrl + UPDATE_CONTACT_URL.replace(":vid", contact.getHubspotId());
            restTemplate.exchange(url, HttpMethod.PUT, null, Void.class);
        } catch (Exception e) {
            throw new HubSpotIntegrationException("Error updating contact in HubSpot", e);
        }
    }

    @Override
    public void deleteContact(String hubSpotId) throws HubSpotIntegrationException {
        try {
            String url = hubSpotApiUrl + DELETE_CONTACT_URL.replace(":vid", hubSpotId);
            restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        } catch (Exception e) {
            throw new HubSpotIntegrationException("Error deleting contact in HubSpot", e);
        }
    }
}

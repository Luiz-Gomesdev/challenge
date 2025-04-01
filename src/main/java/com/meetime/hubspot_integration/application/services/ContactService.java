package com.meetime.hubspot_integration.application.services;

import com.meetime.hubspot_integration.application.exceptions.ContactNotFoundException;
import com.meetime.hubspot_integration.application.ports.inbound.ContactUseCase;
import com.meetime.hubspot_integration.core.model.Company;
import com.meetime.hubspot_integration.core.model.OAuthToken;
import com.meetime.hubspot_integration.core.model.valueobjects.PhoneNumber;
import com.meetime.hubspot_integration.infrastructure.adapters.persistence.repository.ContactRepository;
import com.meetime.hubspot_integration.infrastructure.adapters.persistence.repository.OAuthTokenRepository;
import com.meetime.hubspot_integration.infrastructure.config.AuthConfig;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ContactService implements ContactUseCase {

    private final AuthConfig authorizationConfig;
    private final ContactRepository contactRepository;
    private final OAuthService authService;
    private final OAuthTokenRepository oAuthTokenRepository;
    private final String HUBSPOT_CREATE_CONTACT_URL = "https://api.hubapi.com/crm/v3/objects/contacts";
    private final String TOKEN_URL = "https://api.hubapi.com/oauth/v1/token";

    public ContactService(AuthConfig authorizationConfig, ContactRepository contactRepository, OAuthService authService, OAuthTokenRepository oAuthTokenRepository) {
        this.authorizationConfig = authorizationConfig;
        this.contactRepository = contactRepository;
        this.authService = authService;
        this.oAuthTokenRepository = oAuthTokenRepository;
    }

    @Override
    public ResponseEntity<String> createContact(String hubspotId, String firstName, String lastName,
                                                String email, PhoneNumber phone, Company company, List<String> tags,
                                                String status) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String clientId = authorizationConfig.getClientId();

        Optional<OAuthToken> userToken = oAuthTokenRepository.findTopByClientIdOrderByExpiresInDesc(UUID.fromString(clientId));

        if (userToken.isEmpty()) {
            throw new ContactNotFoundException("INVALID USER!");
        }

        OAuthToken token = userToken.get();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token.getAccessToken());
        headers.set("Content-Type", "application/json");

        Map<String, Object> properties = new HashMap<>();
        properties.put("email", email);
        properties.put("firstname", firstName);
        properties.put("lastname", lastName);
        properties.put("phone", phone != null ? phone.formattedValue() : null);
        properties.put("company", company != null ? company.getName() : null);
        properties.put("tags", tags != null ? String.join(";", tags) : null);
        properties.put("status", status);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("properties", properties);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                HUBSPOT_CREATE_CONTACT_URL,
                HttpMethod.POST,
                request,
                String.class);

        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            String novoToken = authService.refreshAccessToken(token.getRefreshToken());
            headers.setBearerAuth(novoToken);
            request = new HttpEntity<>(requestBody, headers);

            response = restTemplate.exchange(HUBSPOT_CREATE_CONTACT_URL, HttpMethod.POST, request, String.class);
        }

        return response;
    }
}
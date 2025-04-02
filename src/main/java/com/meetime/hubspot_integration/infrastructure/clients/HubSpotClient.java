package com.meetime.hubspot_integration.infrastructure.clients;

import com.meetime.hubspot_integration.application.exceptions.OAuthException;
import com.meetime.hubspot_integration.application.services.OAuthService;
import com.meetime.hubspot_integration.core.model.Company;
import com.meetime.hubspot_integration.core.model.OAuthToken;
import com.meetime.hubspot_integration.core.model.valueobjects.PhoneNumber;
import com.meetime.hubspot_integration.infrastructure.config.AuthConfig;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HubSpotClient {

    private static final String HUBSPOT_CREATE_CONTACT_URL = "https://api.hubapi.com/crm/v3/objects/contacts";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    private final AuthConfig authConfig;  // Agora a variável é final
    private final RestTemplate restTemplate;
    private final OAuthService authService;

    @Autowired
    public HubSpotClient(RestTemplate restTemplate, OAuthService authService, AuthConfig authConfig) {
        this.restTemplate = restTemplate;
        this.authService = authService;
        this.authConfig = authConfig;  // Inicializando authConfig no construtor
    }

    public ResponseEntity<String> createContact(OAuthToken token, String firstName, String lastName,
                                                String email, PhoneNumber phone, Company company, List<String> tags,
                                                String status) throws Exception {
        HttpHeaders headers = createHeaders(token.getAccessToken());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("properties", buildProperties(firstName, lastName, email, phone, company, tags, status));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(HUBSPOT_CREATE_CONTACT_URL, HttpMethod.POST, request, String.class);

        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return retryWithNewToken(token, requestBody);
        }

        return response;
    }

    private ResponseEntity<String> retryWithNewToken(OAuthToken token, Map<String, Object> requestBody) throws Exception {
        String newToken = authService.refreshAccessToken(token.getRefreshToken()); // Passando apenas o refreshToken

        if (newToken == null) {
            throw new OAuthException("Failed to refresh access token", "The refresh token could not be renewed.");
        }

        HttpHeaders newHeaders = createHeaders(newToken);
        HttpEntity<Map<String, Object>> newRequest = new HttpEntity<>(requestBody, newHeaders);

        return restTemplate.exchange(HUBSPOT_CREATE_CONTACT_URL, HttpMethod.POST, newRequest, String.class);
    }



    private HttpHeaders createHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, BEARER + accessToken);
        headers.set(CONTENT_TYPE, APPLICATION_JSON);
        return headers;
    }

    private Map<String, Object> buildProperties(String firstName, String lastName, String email,
                                                PhoneNumber phone, Company company, List<String> tags,
                                                String status) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("email", email);
        properties.put("firstname", firstName);
        properties.put("lastname", lastName);
        properties.put("phone", phone != null ? phone.formattedValue() : null);
        properties.put("company", company != null ? company.getName() : null);
        properties.put("tags", tags != null ? String.join(";", tags) : null);
        properties.put("status", status);
        return properties;
    }
}
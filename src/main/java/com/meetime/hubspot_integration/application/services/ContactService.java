package com.meetime.hubspot_integration.application.services;

import com.meetime.hubspot_integration.application.dto.ContactDTO;
import com.meetime.hubspot_integration.application.exceptions.ContactNotFoundException;
import com.meetime.hubspot_integration.application.ports.inbound.ContactUseCase;
import com.meetime.hubspot_integration.core.model.OAuthToken;
import com.meetime.hubspot_integration.infrastructure.adapters.persistence.repository.ContactRepository;
import com.meetime.hubspot_integration.infrastructure.adapters.persistence.repository.OAuthTokenRepository;
import com.meetime.hubspot_integration.infrastructure.config.AuthConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService implements ContactUseCase {

    private final AuthConfig authorizationConfig;
    private final ContactRepository contactRepository;
    private final OAuthService authService;
    private final OAuthTokenRepository oAuthTokenRepository;
    private static final String HUBSPOT_CREATE_CONTACT_URL = "https://api.hubapi.com/crm/v3/objects/contacts";
    private static final String TOKEN_URL = "https://api.hubapi.com/oauth/v1/token";

    @Override
    public ResponseEntity<String> createContact(ContactDTO contactDTO) throws Exception {
        log.info("CREATING A NEW CONTACT!");
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
        properties.put("email", contactDTO.getEmail());
        properties.put("firstname", contactDTO.getFirstName());
        properties.put("lastname", contactDTO.getLastName());
        properties.put("phone", contactDTO.getPhone());
        properties.put("company", contactDTO.getCompany());
        properties.put("website", contactDTO.getWebsite());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("properties", properties);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                HUBSPOT_CREATE_CONTACT_URL,
                HttpMethod.POST,
                request,
                String.class);

        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            log.info("TOKEN EXPIRED!");
            String newToken = authService.refreshAccessToken(token.getRefreshToken());
            headers.setBearerAuth(newToken);
            request = new HttpEntity<>(requestBody, headers);

            response = restTemplate.exchange(HUBSPOT_CREATE_CONTACT_URL, HttpMethod.POST, request, String.class);
        }

        log.info("NEW CONTACT CREATED!");
        return response;
    }
}
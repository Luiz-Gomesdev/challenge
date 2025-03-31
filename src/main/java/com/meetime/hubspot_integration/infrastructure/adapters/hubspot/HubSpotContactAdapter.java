//package com.meetime.hubspot_integration.infrastructure.adapters.hubspot;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.meetime.hubspot_integration.application.exceptions.*;
//import com.meetime.hubspot_integration.core.model.*;
//import com.meetime.hubspot_integration.application.ports.outbound.HubSpotClientPort;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.http.*;
//import org.springframework.retry.annotation.Backoff;
//import org.springframework.retry.annotation.Retryable;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.util.*;
//
//@Slf4j
//@Component
//@Primary
//@RequiredArgsConstructor
//public class HubSpotContactAdapter implements HubSpotClientPort {
//
//    private static final String CONTACTS_ENDPOINT = "/crm/v3/objects/contacts";
//    private static final String OAUTH_TOKEN_URL = "/oauth/v1/token";
//    private static final String REVOKE_TOKEN_URL = "/oauth/v1/token/revoke";
//    private static final String HMAC_SHA256 = "HmacSHA256";
//
//    private final WebClient webClient;
//    private final ObjectMapper objectMapper;
//
//    @Value("${hubspot.client-id}")
//    private String clientId;
//
//    @Value("${hubspot.client-secret}")
//    private String clientSecret;
//
//    @Value("${hubspot.redirect-uri}")
//    private String redirectUri;
//
//    @Value("${hubspot.auth-url}")
//    private String authUrl;
//
//    @Value("${hubspot.webhook-secret}")
//    private String webhookSecret;
//
//
//    @Override
//    public Contact createContact(Contact contact) throws HubSpotIntegrationException {
//        return null;
//    }
//
//    @Override
//    public void updateContact(Contact contact) throws HubSpotIntegrationException {
//
//    }
//
//    @Override
//    public void deleteContact(String hubSpotId) throws HubSpotIntegrationException {
//
//    }
//}

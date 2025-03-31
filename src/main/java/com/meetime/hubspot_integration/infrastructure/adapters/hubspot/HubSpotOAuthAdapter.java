//package com.meetime.hubspot_integration.infrastructure.adapters.hubspot;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.meetime.hubspot_integration.application.exceptions.HubSpotIntegrationException;
//import com.meetime.hubspot_integration.application.exceptions.WebhookValidationException;
//import com.meetime.hubspot_integration.application.ports.outbound.HubSpotClientPort;
//import com.meetime.hubspot_integration.core.model.Contact;
//import com.meetime.hubspot_integration.core.model.OAuthToken;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//@Slf4j
//@Component
//@Qualifier("hubSpotOAuthAdapter")
//@RequiredArgsConstructor
//public class HubSpotOAuthAdapter implements HubSpotClientPort {
//    private static final String HMAC_SHA256 = "HmacSHA256";
//    private static final String SIGNATURE_REGEX = "^[0-9a-f]{64}$";
//    private static final String TOKEN_URL = "/oauth/v1/token";
//    private static final String REVOKE_URL = "/oauth/v1/token/revoke";
//    private static final String GRANT_TYPE_AUTH_CODE = "authorization_code";
//    private static final String GRANT_TYPE_REFRESH = "refresh_token";
//    private static final String AUTHORIZATION_URL_TEMPLATE = "%s?client_id=%s&redirect_uri=%s&scope=contacts%%20content%%20automation";
//
//    private final WebClient webClient;
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
//    @Value("${hubspot.token-url}")
//    private String tokenUrl;
//
//    @Value("${hubspot.webhook-secret}")
//    private String webhookSecret;
//
//    @Override
//    public String generateAuthorizationUrl() {
//        return String.format(AUTHORIZATION_URL_TEMPLATE,
//                authUrl,
//                clientId,
//                URLEncoder.encode(redirectUri, StandardCharsets.UTF_8));
//    }
//
//    public OAuthToken exchangeAuthorizationCode(String code, String redirectUri) throws HubSpotIntegrationException {
//        try {
//            return webClient.post()
//                    .uri(tokenUrl + TOKEN_URL)
//                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                    .bodyValue(buildTokenRequest(GRANT_TYPE_AUTH_CODE)
//                            .param("code", code)
//                            .param("redirect_uri", redirectUri)
//                            .build())
//                    .retrieve()
//                    .bodyToMono(OAuthToken.class)
//                    .block();
//        } catch (Exception e) {
//            throw new HubSpotIntegrationException(
//                    "Failed to exchange authorization code: " + e.getMessage(),
//                    e);
//        }
//    }
//
//    @Override
//    public OAuthToken refreshAccessToken(String refreshToken) throws HubSpotIntegrationException {
//        try {
//            return webClient.post()
//                    .uri(tokenUrl + TOKEN_URL)
//                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                    .bodyValue(buildTokenRequest(GRANT_TYPE_REFRESH)
//                            .param("refresh_token", refreshToken)
//                            .build())
//                    .retrieve()
//                    .bodyToMono(OAuthToken.class)
//                    .block();
//        } catch (Exception e) {
//            throw new HubSpotIntegrationException(
//                    "Failed to refresh access token: " + e.getMessage(),
//                    e);
//        }
//    }
//
//    @Override
//    public void revokeToken(String accessToken) throws HubSpotIntegrationException {
//        try {
//            webClient.post()
//                    .uri(tokenUrl + REVOKE_URL)
//                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                    .bodyValue("token=" + accessToken)
//                    .retrieve()
//                    .toBodilessEntity();
//        } catch (Exception e) {
//            throw new HubSpotIntegrationException(
//                    "Failed to revoke token: " + e.getMessage(),
//                    e);
//        }
//    }
//
//    @Override
//    public void validateWebhookSignature(String payload, String signature) throws WebhookValidationException {
//        if (payload == null || payload.isEmpty()) {
//            throw WebhookValidationException.malformedPayload(payload);
//        }
//
//        if (signature == null || signature.isEmpty()) {
//            throw WebhookValidationException.missingHeader("X-HubSpot-Signature-v3");
//        }
//
//        if (!signature.matches(SIGNATURE_REGEX)) {
//            throw WebhookValidationException.invalidSignature(signature, "SHA256_HEX_64_CHARS");
//        }
//
//        String computedSignature = calculateHmacSignature(payload, webhookSecret);
//
//        if (!computedSignature.equals(signature)) {
//            throw WebhookValidationException.invalidSignature(signature, computedSignature);
//        }
//    }
//
//    @Override
//    public void processWebhookPayload(String payload) throws HubSpotIntegrationException {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(payload);
//
//            JsonNode subscriptionTypeNode = rootNode.path("subscriptionType");
//            if (!subscriptionTypeNode.asText().equals("contact.creation")) {
//                throw HubSpotIntegrationException.fromResponse(
//                        400,
//                        "UNSUPPORTED_EVENT_TYPE",
//                        "Unsupported event type: " + subscriptionTypeNode.asText()
//                );
//            }
//
//            JsonNode objectsNode = rootNode.path("objectIds");
//            if (objectsNode.isArray()) {
//                for (JsonNode contactIdNode : objectsNode) {
//                    String contactId = contactIdNode.asText();
//                    processContactCreationEvent(contactId);
//                }
//            }
//
//        } catch (JsonProcessingException e) {
//            throw new HubSpotIntegrationException(
//                    "Failed to process webhook payload: invalid JSON format",
//                    e,
//                    400,
//                    "INVALID_JSON"
//            );
//        } catch (HubSpotIntegrationException e) {
//            throw e; // Re-throws specific exceptions
//        } catch (Exception e) {
//            throw new HubSpotIntegrationException(
//                    "Unexpected error while processing webhook",
//                    e,
//                    500,
//                    "INTERNAL_ERROR"
//            );
//        }
//    }
//
//    private void processContactCreationEvent(String contactId) throws HubSpotIntegrationException {
//        try {
//            Contact contact = findContactById(contactId)
//                    .orElseThrow(() -> HubSpotIntegrationException.fromResponse(
//                            404,
//                            "CONTACT_NOT_FOUND",
//                            "Contact not found in HubSpot: " + contactId
//                    ));
//
//            log.info("New contact processed via webhook: ID={}, Name={} {}, Email={}",
//                    contact.getHubspotId(),
//                    contact.getFirstName(),
//                    contact.getLastName(),
//                    contact.getEmail());
//
//        } catch (HubSpotIntegrationException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new HubSpotIntegrationException(
//                    "Failed to process contact creation: " + contactId,
//                    e,
//                    500,
//                    "PROCESSING_ERROR"
//            );
//        }
//    }
//
//
//
//    @Override
//    public Contact updateContact(Contact contact) throws HubSpotIntegrationException {
//        try {
//            return webClient.patch()
//                    .uri("/crm/v3/objects/contacts/" + contact.getHubspotId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .bodyValue(Map.of("properties", contact.toHubSpotProperties()))
//                    .retrieve()
//                    .bodyToMono(Contact.class)
//                    .block();
//        } catch (Exception e) {
//            throw new HubSpotIntegrationException(
//                    "Failed to update contact in HubSpot: " + e.getMessage(),
//                    e);
//        }
//    }
//
//    @Override
//    public void deleteContact(String hubspotId) throws HubSpotIntegrationException {
//        try {
//            webClient.delete()
//                    .uri("/crm/v3/objects/contacts/" + hubspotId)
//                    .retrieve()
//                    .toBodilessEntity()
//                    .block();
//        } catch (Exception e) {
//            throw new HubSpotIntegrationException(
//                    "Failed to delete contact in HubSpot: " + e.getMessage(),
//                    e);
//        }
//    }
//
//
//
//    private String calculateHmacSignature(String payload, String secret) {
//        try {
//            SecretKeySpec secretKey = new SecretKeySpec(
//                    secret.getBytes(StandardCharsets.UTF_8),
//                    HMAC_SHA256
//            );
//
//            Mac mac = Mac.getInstance(HMAC_SHA256);
//            mac.init(secretKey);
//            byte[] hmacBytes = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
//            return bytesToHex(hmacBytes);
//        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
//            throw new RuntimeException("Failed to compute HMAC signature", e);
//        }
//    }
//
//    private String bytesToHex(byte[] bytes) {
//        StringBuilder hexString = new StringBuilder();
//        for (byte b : bytes) {
//            String hex = Integer.toHexString(0xff & b);
//            if (hex.length() == 1) {
//                hexString.append('0');
//            }
//            hexString.append(hex);
//        }
//        return hexString.toString();
//    }
//
//    private TokenRequestBuilder buildTokenRequest(String grantType) {
//        return new TokenRequestBuilder()
//                .param("grant_type", grantType)
//                .param("client_id", clientId)
//                .param("client_secret", clientSecret);
//    }
//
//    private static class TokenRequestBuilder {
//        private final StringBuilder body = new StringBuilder();
//
//        public TokenRequestBuilder param(String key, String value) {
//            if (body.length() > 0) {
//                body.append("&");
//            }
//            body.append(key).append("=").append(value);
//            return this;
//        }
//
//        public String build() {
//            return body.toString();
//        }
//    }
//}

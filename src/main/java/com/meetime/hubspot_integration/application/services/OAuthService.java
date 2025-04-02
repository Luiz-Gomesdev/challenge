package com.meetime.hubspot_integration.application.services;

import com.meetime.hubspot_integration.application.dto.OAuthTokenDTO;
import com.meetime.hubspot_integration.application.exceptions.OAuthException;
import com.meetime.hubspot_integration.application.ports.inbound.OAuthUseCase;
import com.meetime.hubspot_integration.core.model.OAuthToken;
import com.meetime.hubspot_integration.infrastructure.adapters.persistence.mappers.OAuthTokenMapper;
import com.meetime.hubspot_integration.infrastructure.adapters.persistence.repository.OAuthTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Slf4j
@Service
public class OAuthService implements OAuthUseCase {

    private final String AUTH_URL;
    private final String CLIENT_ID;
    private final String CLIENT_SECRET;
    private final String REDIRECT_URI;
    private final String TOKEN_URL = "https://api.hubapi.com/oauth/v1/token";

    private final OAuthTokenRepository tokenRepository;
    private final RestTemplate restTemplate;

    public OAuthService(
            @Value("${hubspot.auth-url}") String authUrl,
            @Value("${hubspot.client-id}") String clientId,
            @Value("${hubspot.client-secret}") String clientSecret,
            @Value("${hubspot.redirect-uri}") String redirectUri,
            OAuthTokenRepository tokenRepository,
            RestTemplate restTemplate) {
        this.AUTH_URL = authUrl;
        this.CLIENT_ID = clientId;
        this.CLIENT_SECRET = clientSecret;
        this.REDIRECT_URI = redirectUri;
        this.tokenRepository = tokenRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public RedirectView authorize() {
        System.out.println("STARTING REDIRECT!");
        String REDIRECT_URL = AUTH_URL +
                "?client_id=" +
                CLIENT_ID +
                "&redirect_uri=" +
                REDIRECT_URI +
                "&scope=crm.objects.contacts.write%20crm.objects.contacts.read" +
                "&response_type=code";

        return new RedirectView(REDIRECT_URL);
    }

    @Override
    public ResponseEntity<Map<String, Object>> callback(String authorizationCode) throws Exception {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("client_id", CLIENT_ID);
        requestBody.add("client_secret", CLIENT_SECRET);
        requestBody.add("redirect_uri", REDIRECT_URI);
        requestBody.add("code", authorizationCode);

        ResponseEntity<String> response = requestToken(requestBody);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            OAuthTokenDTO responseBody = OAuthTokenMapper.fromJsonToDto(response.getBody());
            OAuthToken token = OAuthToken.builder()
                    .accessToken(responseBody.getAccessToken())
                    .refreshToken(responseBody.getRefreshToken())
                    .expiresIn(responseBody.getExpiresIn())
                    .tokenType(responseBody.getTokenType())
                    .scope(responseBody.getScope())
                    .build();
            tokenRepository.save(token);
            return buildResponse("ACCESS TOKEN RECEIVED!", HttpStatus.OK);
        }
        throw new OAuthException("failed_to_receive_access_token", "FAILED TO RECEIVE ACCESS TOKEN!");
    }

    @Override
    public String refreshAccessToken(String refreshToken) throws Exception {
        Optional<OAuthToken> tokenOptional = tokenRepository.findTopByClientIdOrderByExpiresInDesc(UUID.fromString(CLIENT_ID));
        OAuthToken token = tokenOptional.orElseThrow(() -> new OAuthException("Token_not_found", "Token not found"));

        if (!token.isExpired()) {
            return token.getAccessToken();
        }

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "refresh_token");
        requestBody.add("client_id", CLIENT_ID);
        requestBody.add("client_secret", CLIENT_SECRET);
        requestBody.add("refresh_token", refreshToken);

        ResponseEntity<String> response = requestToken(requestBody);
        return processTokenRefreshResponse(response, token);
    }

    private ResponseEntity<String> requestToken(MultiValueMap<String, String> requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(TOKEN_URL, HttpMethod.POST, request, String.class);
    }

    private String processTokenRefreshResponse(ResponseEntity<String> response, OAuthToken token) throws Exception {
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            OAuthTokenDTO responseBody = OAuthTokenMapper.fromJsonToDto(response.getBody());
            token.setAccessToken(responseBody.getAccessToken());
            token.setRefreshToken(responseBody.getRefreshToken());
            token.setExpiresIn(responseBody.getExpiresIn());
            tokenRepository.save(token);
            return responseBody.getAccessToken();
        }
        throw new OAuthException("failed_to_refresh_access_token", "FAILED TO REFRESH ACCESS TOKEN!");
    }

    private ResponseEntity<Map<String, Object>> buildResponse(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", new Date());
        response.put("status", status.value());
        response.put("message", message);
        return new ResponseEntity<>(response, status);
    }
}
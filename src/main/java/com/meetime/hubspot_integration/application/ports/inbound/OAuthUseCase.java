package com.meetime.hubspot_integration.application.ports.inbound;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

public interface OAuthUseCase {
    RedirectView authorize();
    ResponseEntity<Map<String, Object>> callback(String authorizationCode) throws Exception;
    String refreshAccessToken(String refreshToken) throws Exception;
}
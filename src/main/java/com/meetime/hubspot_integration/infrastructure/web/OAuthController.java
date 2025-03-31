package com.meetime.hubspot_integration.infrastructure.web;

import com.meetime.hubspot_integration.application.ports.inbound.OAuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthUseCase authUseCase;

    @GetMapping("/authorize")
    public RedirectView authorize() {
        return authUseCase.authorize();
    }

    @GetMapping("/callback")
    public ResponseEntity<Map<String, Object>> callback(@RequestParam("code") String authorizationCode) throws Exception {
        return authUseCase.callback(authorizationCode);
    }
}

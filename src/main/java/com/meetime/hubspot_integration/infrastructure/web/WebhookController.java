package com.meetime.hubspot_integration.infrastructure.web;

import com.meetime.hubspot_integration.application.ports.inbound.WebhookUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/hubspot")
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookUseCase webhookUseCase;

    @PostMapping("/webhook")
    public void receberWebhook(@RequestBody String payload) throws Exception {
        log.info("Received webhook payload: {}", payload);

        webhookUseCase.processWebHookEvents(payload);
    }
}

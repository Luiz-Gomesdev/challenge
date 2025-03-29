package com.meetime.hubspot_integration.application.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetime.hubspot_integration.application.exceptions.WebhookProcessingException;
import com.meetime.hubspot_integration.application.ports.EventPublisherPort;
import com.meetime.hubspot_integration.application.ports.HubSpotClientPort;
import com.meetime.hubspot_integration.domain.events.WebhookReceivedEvent;
import com.meetime.hubspot_integration.domain.models.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebhookHandlerService {

    private final HubSpotClientPort hubSpotClient;
    private final EventPublisherPort eventPublisher;
    private final ObjectMapper objectMapper;

    public void handleWebhook(String payload, String signature, EventType eventType) {
        try {
            // Convertendo o payload JSON para Map<String, Object>
            Map<String, Object> payloadMap = objectMapper.readValue(
                    payload,
                    new TypeReference<Map<String, Object>>() {}
            );

            if (!hubSpotClient.validateWebhookSignature(payload, signature)) {
                throw new SecurityException("Invalid webhook signature");
            }

            eventPublisher.publish(new WebhookReceivedEvent(eventType, payloadMap));
            log.info("Webhook processado: {}", eventType);

        } catch (Exception e) {
            log.error("Falha ao processar webhook", e);
            throw new WebhookProcessingException("Failed to process webhook", e);
        }
    }
}
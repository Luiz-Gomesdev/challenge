package com.meetime.hubspot_integration.application.services;

import com.meetime.hubspot_integration.application.dto.WebhookEventDTO;
import com.meetime.hubspot_integration.application.ports.inbound.WebhookUseCase;
import com.meetime.hubspot_integration.core.model.WebhookEvent;
import com.meetime.hubspot_integration.infrastructure.adapters.persistence.mappers.WebhookEventMapper;
import com.meetime.hubspot_integration.infrastructure.adapters.persistence.repository.WebhookRepository;
import com.meetime.hubspot_integration.infrastructure.config.AuthConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WebhookService implements WebhookUseCase {

    private final String TOKEN_URL = "https://api.hubapi.com/oauth/v1/token";
    private final String HUBSPOT_CREATE_CONTACT_URL = "https://api.hubapi.com/crm/v3/objects/contacts";

    private final WebhookRepository webhookRepository;
    private final AuthConfig authConfig;

    public WebhookService(WebhookRepository webhookRepository, AuthConfig authConfig) {
        this.webhookRepository = webhookRepository;
        this.authConfig = authConfig;
    }

    public void processWebHookEvents(String payload) throws Exception {
        WebhookEvent webhookEvent = new WebhookEvent();
        WebhookEventDTO contactEventPayload = WebhookEventMapper.webhookEventConverter(payload);

        webhookEvent.setEventId(contactEventPayload.getEventId());
        webhookEvent.setSubscriptionId(contactEventPayload.getSubscriptionId());
        webhookEvent.setPortalId(contactEventPayload.getPortalId());
        webhookEvent.setAppId(contactEventPayload.getAppId());
        webhookEvent.setOccurredAt(contactEventPayload.getOccurredAt());
        webhookEvent.setSubscriptionType(contactEventPayload.getSubscriptionType());
        webhookEvent.setAttemptNumber(contactEventPayload.getAttemptNumber());
        webhookEvent.setObjectId(contactEventPayload.getObjectId());
        webhookEvent.setChangeFlag(contactEventPayload.getChangeFlag());
        webhookEvent.setChangeSource(contactEventPayload.getChangeSource());
        webhookEvent.setSourceId(contactEventPayload.getSourceId());

        webhookRepository.save(webhookEvent);
        log.info("Webhook event saved: {}", webhookEvent);
    }
}
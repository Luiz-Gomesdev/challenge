package com.meetime.hubspot_integration.application.ports.inbound;

public interface WebhookUseCase {
    void processWebHookEvents(String payload) throws Exception;
}
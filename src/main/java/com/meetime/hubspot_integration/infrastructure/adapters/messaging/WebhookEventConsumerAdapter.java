//package com.meetime.hubspot_integration.infrastructure.adapters.messaging;
//
//import com.meetime.hubspot_integration.application.ports.inbound.WebhookUseCase;
//import com.meetime.hubspot_integration.core.events.WebhookReceivedEvent;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class WebhookEventConsumerAdapter {
//
//    private final WebhookUseCase webhookUseCase;
//
//    @KafkaListener(topics = "${kafka.topics.webhook-events}")
//    public void consume(@Payload WebhookReceivedEvent event) {
//        try {
//            log.debug("Received webhook event ID: {}", event.eventId());
//            webhookUseCase.handleWebhookEvent(event);
//            log.info("Webhook {} processed successfully", event.eventId());
//        } catch (Exception e) {
//            log.error("Failed to process webhook {}", event.eventId(), e);
//        }
//    }
//}

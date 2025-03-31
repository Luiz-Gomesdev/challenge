//package com.meetime.hubspot_integration.infrastructure.adapters.messaging;
//
//import com.meetime.hubspot_integration.application.ports.outbound.EventPublisherPort;
//import com.meetime.hubspot_integration.core.events.DomainEvent;
//import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class KafkaEventPublisherAdapter implements EventPublisherPort {
//
//    private static final String TOPIC_NAME = "domain-events";
//
//    private final KafkaTemplate<String, DomainEvent> kafkaTemplate;
//
//    @Override
//    public void publish(DomainEvent event) {
//        kafkaTemplate.send(TOPIC_NAME, event.eventType(), event);
//    }
//}

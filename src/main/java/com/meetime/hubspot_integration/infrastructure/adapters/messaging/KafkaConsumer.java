package com.meetime.hubspot_integration.infrastructure.adapters.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "meetime_topic_name", groupId = "hubspot-integration-group", concurrency = "3")
    public void listen(String message) {
        try {
            logger.info("Received message: {}", message);
        } catch (Exception e) {
            logger.error("Error processing message: {}", message, e);
        }
    }
}

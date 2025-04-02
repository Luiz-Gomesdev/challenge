package com.meetime.hubspot_integration.infrastructure.adapters.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        CompletableFuture<SendResult<String, String>> completableFuture = kafkaTemplate.send(topic, message);
        ListenableFuture<SendResult<String, String>> future = toListenableFuture(completableFuture);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Message successfully sent to topic {}. Partition: {}, Offset: {}",
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("Failed to send message to topic", ex);
            }
        });
    }

    private <T> ListenableFuture<T> toListenableFuture(CompletableFuture<T> completableFuture) {
        SettableListenableFuture<T> listenableFuture = new SettableListenableFuture<>();
        completableFuture.whenComplete((result, ex) -> {
            if (ex != null) {
                listenableFuture.setException(ex);
            } else {
                listenableFuture.set(result);
            }
        });
        return listenableFuture;
    }
}
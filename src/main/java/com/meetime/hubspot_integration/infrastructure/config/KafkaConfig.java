//package com.meetime.hubspot_integration.infrastructure.config;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.TopicBuilder;
//
//@Configuration
//public class KafkaConfig {
//
//    public static final String DOMAIN_EVENTS_TOPIC = "domain-events";
//    public static final String WEBHOOK_EVENTS_TOPIC = "webhook-events";
//    private static final int PARTITIONS = 3;
//    private static final int REPLICAS = 1;
//    private static final String RETENTION_MS_DOMAIN = "604800000"; // 7 dias
//    private static final String RETENTION_MS_WEBHOOK = "86400000"; // 1 dia
//
//    @Bean
//    public NewTopic domainEventsTopic() {
//        return TopicBuilder.name(DOMAIN_EVENTS_TOPIC)
//                .partitions(PARTITIONS)
//                .replicas(REPLICAS)
//                .config("retention.ms", RETENTION_MS_DOMAIN)
//                .build();
//    }
//
//    @Bean
//    public NewTopic webhookEventsTopic() {
//        return TopicBuilder.name(WEBHOOK_EVENTS_TOPIC)
//                .partitions(PARTITIONS)
//                .replicas(REPLICAS)
//                .config("retention.ms", RETENTION_MS_WEBHOOK)
//                .build();
//    }
//}

package com.meetime.hubspot_integration.application.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class WebhookEventDTO {
    private UUID eventId;
    private UUID subscriptionId;
    private UUID portalId;
    private UUID appId;
    private Long occurredAt;
    private String subscriptionType;
    private Long attemptNumber;
    private UUID objectId;
    private String changeFlag;
    private String changeSource;
    private UUID sourceId;
}

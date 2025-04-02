package com.meetime.hubspot_integration.core.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "webhook_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Webhook {

    @Id
    @Column(name = "event_id")
    private UUID eventId;

    private String clientId;

    @Column(name = "subscription_id")
    private UUID subscriptionId;

    @Column(name = "portal_id")
    private UUID portalId;

    @Column(name = "app_id")
    private UUID appId;

    @Column(name = "occurred_at")
    private Long occurredAt;

    @Column(name = "subscription_type")
    private String subscriptionType;

    @Column(name = "attempt_number")
    private Long attemptNumber;

    @Column(name = "object_id")
    private UUID objectId;

    @Column(name = "change_flag")
    private String changeFlag;

    @Column(name = "change_source")
    private String changeSource;

    @Column(name = "source_id")
    private UUID sourceId;
}

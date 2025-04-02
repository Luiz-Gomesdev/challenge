package com.meetime.hubspot_integration.infrastructure.adapters.persistence.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetime.hubspot_integration.application.dto.WebhookEventDTO;

import java.util.List;

public class WebhookEventMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<WebhookEventDTO> webhookEventConverter(String json) throws Exception {
        return objectMapper.readValue(json, new TypeReference<List<WebhookEventDTO>>() {});
    }
}
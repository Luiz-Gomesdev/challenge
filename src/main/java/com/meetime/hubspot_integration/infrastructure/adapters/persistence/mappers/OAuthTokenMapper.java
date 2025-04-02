package com.meetime.hubspot_integration.infrastructure.adapters.persistence.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetime.hubspot_integration.application.dto.OAuthTokenDTO;

public class OAuthTokenMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private OAuthTokenMapper() {}

    public static OAuthTokenDTO fromJsonToDto(String json) {
        try {
            return objectMapper.readValue(json, OAuthTokenDTO.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to convert JSON to OAuthTokenDTO", e);
        }
    }

    public static String toJson(OAuthTokenDTO dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to convert OAuthTokenDTO to JSON", e);
        }
    }
}
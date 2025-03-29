package com.meetime.hubspot_integration.application.exceptions;

public class WebhookProcessingException extends RuntimeException {
    public WebhookProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
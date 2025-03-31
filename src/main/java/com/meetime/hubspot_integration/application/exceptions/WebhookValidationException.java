package com.meetime.hubspot_integration.application.exceptions;

import lombok.Getter;

@Getter
public class WebhookValidationException extends RuntimeException {
    private final String errorType;
    private final String validationDetails;
    private final String additionalDetails;

    public WebhookValidationException(String errorType, String validationDetails, String additionalDetails) {
        super("Erro Webhook [%s]: %s - %s".formatted(errorType, validationDetails, additionalDetails));
        this.errorType = errorType;
        this.validationDetails = validationDetails;
        this.additionalDetails = additionalDetails;
    }

    public static WebhookValidationException invalidSignature(String receivedSig, String expectedSig) {
        return new WebhookValidationException(
                "INVALID_SIGNATURE",
                "Received: %s | Expected: %s".formatted(receivedSig, expectedSig),
                "Signature mismatch"
        );
    }

    public static WebhookValidationException missingHeader(String headerName) {
        return new WebhookValidationException("MISSING_HEADER", "Missing header: " + headerName, "No signature header provided");
    }

    public static WebhookValidationException malformedPayload(String payload) {
        return new WebhookValidationException(
                "MALFORMED_PAYLOAD",
                "Payload: %s".formatted(payload != null ? payload.substring(0, Math.min(payload.length(), 100)) : "null"),
                "Malformed payload"
        );
    }
}

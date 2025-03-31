package com.meetime.hubspot_integration.application.exceptions;

import lombok.Getter;

@Getter
public class SyncFailedException extends RuntimeException {

    public SyncFailedException(String message) {
        super(message);
    }

    public SyncFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SyncFailedException(Throwable cause) {
        super(cause);
    }
}
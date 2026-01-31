package com.cyber.portal.sharedResources.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PortalException extends RuntimeException {
    private final HttpStatus status;

    public PortalException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}

package com.cyber.portal.sharedResources.exception;

import com.cyber.portal.sharedResources.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PortalException.class)
    public ResponseEntity<ApiResponse<Void>> handlePortalException(PortalException ex) {
        return new ResponseEntity<>(
                ApiResponse.of(ex.getStatus(), ex.getMessage(), null),
                ex.getStatus()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        return new ResponseEntity<>(
                ApiResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

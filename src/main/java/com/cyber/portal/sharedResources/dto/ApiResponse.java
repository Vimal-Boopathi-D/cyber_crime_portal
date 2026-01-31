package com.cyber.portal.sharedResources.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private int statusCode;
    private String message;
    private T data;

    public static <T> ApiResponse<T> of(HttpStatus status, String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.statusCode = status.value();
        response.message = message;
        response.data = data;
        return response;
    }
}

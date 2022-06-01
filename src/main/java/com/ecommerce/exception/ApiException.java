package com.ecommerce.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    private final ZonedDateTime timestamp;
    private final Integer status;
    private final HttpStatus error;
    private final String message;


    public ApiException(String message, HttpStatus error, Integer statusCode, ZonedDateTime timestamp) {
        this.message = message;
        this.error = error;
        this.status = statusCode;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getError() {
        return error;
    }

    public Integer getStatus() {
        return status;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}

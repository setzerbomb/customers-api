package com.customers.api.common.exceptions;

import org.springframework.http.HttpStatus;

public class ApiError extends Exception {

    private HttpStatus code;
    private String description;

    public ApiError(String message, HttpStatus code) {
        super(message);

        this.code = code;
        this.description = message;
    }
}

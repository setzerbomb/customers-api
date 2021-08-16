package com.customers.api.common.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
@Setter
public class ApiErrorException extends ResponseStatusException {

    private String code;
    private String description;

    public ApiErrorException(HttpStatus status, String reason, String code) {
        super(status, reason);

        this.code = code;
        this.description = reason;
    }
}

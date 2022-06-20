package com.mapandre.domain.models.errors;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundError extends DefaultApiError{

    private final String message;

    public ResourceNotFoundError(HttpStatus httpStatus, String exception, String message) {
        super(httpStatus, exception);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

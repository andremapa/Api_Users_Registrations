package com.mapandre.domain.models.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public abstract class DefaultApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;
    private final String exception;
    private final HttpStatus httpStatus;

    protected DefaultApiError(HttpStatus httpStatus, String exception) {
        this.timestamp = LocalDateTime.now();
        this.exception = exception;
        this.httpStatus = httpStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getException() {
        return exception;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

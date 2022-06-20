package com.mapandre.handlers;

import com.mapandre.domain.models.errors.JsonFieldsNotValidError;
import com.mapandre.domain.models.errors.DateFormatError;
import com.mapandre.domain.models.errors.ResourceNotFoundError;
import com.mapandre.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler{

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception){
        ResourceNotFoundError resourceNotFoundError = new ResourceNotFoundError(
                HttpStatus.NOT_FOUND, exception.getClass().getName(),
                exception.getMessage()
        );
        return new ResponseEntity<>(resourceNotFoundError, resourceNotFoundError.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        JsonFieldsNotValidError jsonFieldsNotValidError = new JsonFieldsNotValidError(
                HttpStatus.BAD_REQUEST, exception.getClass().getName(), exception.getFieldErrors()
        );
        return new ResponseEntity<>(jsonFieldsNotValidError, jsonFieldsNotValidError.getHttpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception){
        DateFormatError dateFormatError = new DateFormatError(
                HttpStatus.BAD_REQUEST,  exception.getClass().getName(), "Invalid Date Format! Use -> 'dd-MM-yyyy'"
        );
        return new ResponseEntity<>(dateFormatError, dateFormatError.getHttpStatus());
    }
}

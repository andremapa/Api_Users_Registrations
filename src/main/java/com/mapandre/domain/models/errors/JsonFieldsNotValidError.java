package com.mapandre.domain.models.errors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonFieldsNotValidError extends DefaultApiError{

    private final Map<String,String> fieldsWithError;

    public JsonFieldsNotValidError(HttpStatus httpStatus, String exception, List<FieldError> fieldErrorList) {
        super(httpStatus, exception);
        this.fieldsWithError = mappingFieldsWithInvalidInformation(fieldErrorList);
    }

    private Map<String, String> mappingFieldsWithInvalidInformation(List<FieldError> fieldErrorList){
        Map<String, String> mapFieldsAndMessages = new HashMap<>();
        fieldErrorList.forEach(field -> mapFieldsAndMessages.put(field.getField(), field.getDefaultMessage()));
        return mapFieldsAndMessages;
    }

    public Map<String, String> getFieldsWithError() {
        return fieldsWithError;
    }
}

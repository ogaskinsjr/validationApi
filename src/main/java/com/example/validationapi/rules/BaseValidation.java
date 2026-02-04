package com.example.validationapi.rules;

import com.example.validationapi.models.ValidationRequest;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseValidation {

    protected String errorMessage;

    public BaseValidation(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public abstract void validate(ValidationRequest request) throws IllegalArgumentException;

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getSuccessMessage() {
        return "Validation passed for rule: " + this.getClass().getSimpleName();
    }
}
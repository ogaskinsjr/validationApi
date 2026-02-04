package com.example.validationapi.rules;

import org.springframework.stereotype.Component;

import com.example.validationapi.models.ValidationRequest;

@Component
public abstract class BaseValidation {

    protected String errorMessage;

    public BaseValidation(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void severityHandler() {
        // Default implementation (can be overridden by subclasses)
    }

    public abstract void validate(ValidationRequest request) throws IllegalArgumentException;

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getSuccessMessage() {
        return "Validation passed for rule: " + this.getClass().getSimpleName();
    }


}
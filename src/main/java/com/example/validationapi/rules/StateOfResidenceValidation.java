package com.example.validationapi.rules;

import com.example.validationapi.models.StateList;
import com.example.validationapi.models.ValidationRequest;
import org.springframework.stereotype.Component;

@Component
public class StateOfResidenceValidation extends BaseValidation {

    public StateOfResidenceValidation() {
        super("StateOfResidenceValidation");
    }

    @Override
    public void validate(ValidationRequest request) throws IllegalArgumentException {
        if (request == null) throw new IllegalArgumentException("Request cannot be null.");

        String raw = request.getStateOfResidence();

        if (raw == null || raw.trim().isEmpty()) {
            throw new IllegalArgumentException("State of residence errors: stateOfResidence is required (e.g., OH, CA, NY).");
        }

        String normalized = raw.trim().toUpperCase();

        try {
            StateList.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("State of residence errors: invalid state code '" + raw + "'.");
        }
    }
}

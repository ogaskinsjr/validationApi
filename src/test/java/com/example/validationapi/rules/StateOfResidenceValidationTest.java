package com.example.validationapi.rules;

import com.example.validationapi.models.ValidationRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StateOfResidenceValidationTest {

    @Test
    public void nullRequest_throws() {
        StateOfResidenceValidation v = new StateOfResidenceValidation();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> v.validate(null));
        assertTrue(ex.getMessage().contains("Request cannot be null."));
    }

    @Test
    public void emptyState_throws() {
        StateOfResidenceValidation v = new StateOfResidenceValidation();
        ValidationRequest r = new ValidationRequest();
        r.setStateOfResidence("  ");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> v.validate(r));
        assertTrue(ex.getMessage().contains("stateOfResidence is required"));
    }

    @Test
    public void invalidCode_throws() {
        StateOfResidenceValidation v = new StateOfResidenceValidation();
        ValidationRequest r = new ValidationRequest();
        r.setStateOfResidence("XX");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> v.validate(r));
        assertTrue(ex.getMessage().contains("invalid state code"));
    }

    @Test
    public void validLowerCase_passes() {
        StateOfResidenceValidation v = new StateOfResidenceValidation();
        ValidationRequest r = new ValidationRequest();
        r.setStateOfResidence(" ca ");
        assertDoesNotThrow(() -> v.validate(r));
    }
}

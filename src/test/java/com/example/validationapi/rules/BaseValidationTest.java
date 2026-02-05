package com.example.validationapi.rules;

import com.example.validationapi.models.ValidationRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BaseValidationTest {

    @Test
    public void getSuccessMessage_containsClassName() {
        BaseValidation b = new BaseValidation("err") {
            @Override
            public void validate(ValidationRequest request) { }
        };
        assertTrue(b.getSuccessMessage().startsWith("Validation passed for rule:"));
    }

    @Test
    public void getErrorMessage_returnsProvided() {
        BaseValidation b = new BaseValidation("myError") {
            @Override
            public void validate(ValidationRequest request) { }
        };
        assertEquals("myError", b.getErrorMessage());
    }

    @Test
    public void severityHandler_default_noop() {
        BaseValidation b = new BaseValidation("myError") {
            @Override
            public void validate(ValidationRequest request) { }
        };
        // should not throw
        b.severityHandler();
    }
}

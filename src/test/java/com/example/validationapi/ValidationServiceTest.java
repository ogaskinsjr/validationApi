package com.example.validationapi;

import com.example.validationapi.models.ValidationRequest;
import com.example.validationapi.models.ValidationResponse;
import com.example.validationapi.rules.BaseValidation;
import com.example.validationapi.service.ValidationService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationServiceTest {

    @Test
    public void noRules_returnsBadRequestMessage() {
        ValidationService svc = new ValidationService(new ArrayList<>());
        ResponseEntity<ValidationResponse> resp = svc.validateRequest(new ValidationRequest());
        assertEquals(400, resp.getStatusCodeValue());
        assertFalse(resp.getBody().isValid());
        assertTrue(resp.getBody().getMessages().contains("No validation rules applied."));
    }

    @Test
    public void passingRule_returnsOkWithSuccess() {
        BaseValidation pass = new BaseValidation("ok") {
            @Override
            public void validate(ValidationRequest request) { }
        };

        List<BaseValidation> rules = new ArrayList<>();
        rules.add(pass);

        ValidationService svc = new ValidationService(rules);
        ResponseEntity<ValidationResponse> resp = svc.validateRequest(new ValidationRequest());
        assertEquals(200, resp.getStatusCodeValue());
        assertTrue(resp.getBody().isValid());
        assertFalse(resp.getBody().getMessages().isEmpty());
    }

    @Test
    public void failingRule_formatsErrorsWithMarker() {
        BaseValidation fail = new BaseValidation("fail") {
            @Override
            public void validate(ValidationRequest request) {
                throw new IllegalArgumentException("Some context errors: a, b, c");
            }
        };

        ValidationService svc = new ValidationService(List.of(fail));
        ResponseEntity<ValidationResponse> resp = svc.validateRequest(new ValidationRequest());
        assertEquals(200, resp.getStatusCodeValue());
        // overall valid should be false because rule threw
        assertFalse(resp.getBody().isValid());
        // should contain split error lines
        assertTrue(resp.getBody().getMessages().stream().anyMatch(m -> m.contains("Some context errors: a")));
        assertTrue(resp.getBody().getMessages().stream().anyMatch(m -> m.contains("[ERROR] b")));
    }

    @Test
    public void failingRule_formatsErrorsWithoutMarker() {
        BaseValidation fail = new BaseValidation("fail") {
            @Override
            public void validate(ValidationRequest request) {
                throw new IllegalArgumentException("plain failure message");
            }
        };

        ValidationService svc = new ValidationService(List.of(fail));
        ResponseEntity<ValidationResponse> resp = svc.validateRequest(new ValidationRequest());
        assertEquals(200, resp.getStatusCodeValue());
        assertFalse(resp.getBody().isValid());
        assertTrue(resp.getBody().getMessages().stream().anyMatch(m -> m.contains("plain failure message")));
    }
}

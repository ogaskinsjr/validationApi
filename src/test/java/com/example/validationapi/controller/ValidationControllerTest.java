package com.example.validationapi.controller;

import com.example.validationapi.models.ValidationRequest;
import com.example.validationapi.models.ValidationResponse;
import com.example.validationapi.service.ValidationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationControllerTest {

    @Test
    public void validateInput_delegatesToService() {
        ValidationService mockSvc = Mockito.mock(ValidationService.class);
        ValidationController controller = new ValidationController();
        // inject via field (no setter) - use reflection
        try {
            java.lang.reflect.Field f = controller.getClass().getDeclaredField("validationService");
            f.setAccessible(true);
            f.set(controller, mockSvc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ValidationRequest req = new ValidationRequest();
        ValidationResponse respBody = new ValidationResponse(true, List.of("ok"));
        ResponseEntity<ValidationResponse> respEntity = ResponseEntity.ok(respBody);

        Mockito.when(mockSvc.validateRequest(Mockito.any())).thenReturn(respEntity);

        ResponseEntity<ValidationResponse> out = controller.validateInput(req);
        assertEquals(200, out.getStatusCodeValue());
        assertTrue(out.getBody().isValid());
    }

    @Test
    public void handleDeserializationError_returnsBadRequest() {
        ValidationController controller = new ValidationController();
        org.springframework.http.converter.HttpMessageNotReadableException ex =
                new org.springframework.http.converter.HttpMessageNotReadableException("bad json");

        ResponseEntity<ValidationResponse> out = controller.handleDeserializationError(ex);
        assertEquals(400, out.getStatusCodeValue());
        assertFalse(out.getBody().isValid());
        assertTrue(out.getBody().getMessages().get(0).contains("Invalid request"));
    }
}

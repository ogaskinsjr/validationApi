package com.example.validationapi.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.validationapi.models.ValidationRequest;
import com.example.validationapi.service.ValidationService;
import com.example.validationapi.models.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;
import java.util.List;

@RestController
public class ValidationController {

    @Autowired
    ValidationService validationService;

    @PostMapping("/validate")
    public ResponseEntity<ValidationResponse> validateInput(@RequestBody ValidationRequest validationRequest) {
        return validationService.validateRequest(validationRequest);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ValidationResponse> handleDeserializationError(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(new ValidationResponse(false, List.of("Invalid request, please check submission details: " + e.getMessage())));
    }
    
    
}
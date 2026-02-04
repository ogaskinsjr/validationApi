package com.example.validationapi.service;

import org.springframework.stereotype.Service;
import com.example.validationapi.models.ValidationRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import com.example.validationapi.rules.BaseValidation;
import java.util.ArrayList;
import com.example.validationapi.models.ValidationResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ValidationService {

    private final List<BaseValidation> rules;

    public ValidationService(List<BaseValidation> rules) {
        this.rules = rules;
    }

    public ResponseEntity<ValidationResponse> validateRequest(ValidationRequest request) {
        log.info("[INFO] Starting validation for request: {}", request);

        List<String> messages = new ArrayList<>();
        boolean hasError = false;

        for (BaseValidation rule : rules) {
            try {
                rule.validate(request);
                log.info("[INFO] Validation passed for rule: {}", rule.getClass().getSimpleName());
                messages.add("[SUCCESS] " + rule.getSuccessMessage());
            } catch (IllegalArgumentException e) {
                log.error("[ERROR] Validation failed for rule: {}: {}", rule.getClass().getSimpleName(), e.getMessage());
                messages.addAll(formatErrorMessages(e.getMessage()));
                hasError = true;
            }
        }

        if (messages.isEmpty()) {
            return ResponseEntity.badRequest()
                .body(new ValidationResponse(false, List.of("No validation rules applied.")));
        }

        log.info("Validation completed. Overall valid: {}", !hasError);
        return ResponseEntity.ok(new ValidationResponse(!hasError, messages));
    }

    private List<String> formatErrorMessages(String raw) {
        List<String> out = new ArrayList<>();
        if (raw == null || raw.trim().isEmpty()) {
            out.add("[ERROR] Validation failed.");
            return out;
        }

        // If your rules follow: "<Some context> errors: a, b, c"
        String marker = " errors: ";
        int idx = raw.indexOf(marker);

        if (idx >= 0) {
            String prefix = raw.substring(0, idx + marker.length()); // includes " errors: "
            String rest = raw.substring(idx + marker.length()).trim(); // "a, b, c"

            String[] parts = rest.split(",\\s*");
            for (int i = 0; i < parts.length; i++) {
                String msg = parts[i].trim();
                if (msg.isEmpty()) continue;

                if (i == 0) {
                    out.add("[ERROR] " + prefix + msg);
                } else {
                    out.add("[ERROR] " + msg);
                }
            }
            return out;
        }

        // fallback: no "errors:" marker -> keep as single line
        out.add("[ERROR] " + raw);
        return out;
    }
}

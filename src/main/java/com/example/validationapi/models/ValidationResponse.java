package com.example.validationapi.models;

import java.util.List;

public class ValidationResponse {
    private boolean valid;
    private List<String> messages;

    public ValidationResponse(boolean valid, List<String> messages) {
        this.valid = valid;
        this.messages = messages;
    }

    public boolean isValid() { return valid; }
    public List<String> getMessages() { return messages; }
}

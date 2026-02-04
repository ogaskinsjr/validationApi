package com.example.validationapi.rules;

import org.springframework.stereotype.Component;
import com.example.validationapi.models.MaritalType;
import com.example.validationapi.models.SpouseInfo;

import com.example.validationapi.models.ValidationRequest;

@Component
public class MarriageValidation extends BaseValidation {

    public MarriageValidation() {
        super("MarriageValidation");
    }

    @Override
    public void validate(ValidationRequest request) throws IllegalArgumentException {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null.");
        }

        String raw = request.getMaritalStatus();

        if (raw == null || raw.trim().isEmpty()) {
            throw new IllegalArgumentException("Marriage errors: maritalStatus is required.");
        }

        MaritalType type;
        try {
            // enum values are lowercase, so normalize to lowercase
            type = MaritalType.valueOf(raw.trim().toLowerCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Marriage errors: invalid maritalStatus '" + raw + "'.");
        }

        if (type == MaritalType.married) {
            SpouseInfo spouseInfo = request.getSpouseInfo();
            
            if(spouseInfo == null) {
                throw new IllegalArgumentException("Marriage errors: married selected but spouseInfo is missing.");
            }

            String spouseSsn = spouseInfo.getSpouseSSN();
            String spouseName = spouseInfo.getFirstName() + " " + spouseInfo.getLastName();

            

            if (spouseSsn == null || spouseSsn.trim().isEmpty()) {
                throw new IllegalArgumentException("Marriage errors: married selected but spouseSSN is missing.");
            }
            if(spouseName == null || spouseName.trim().isEmpty()) {
                throw new IllegalArgumentException("Marriage errors: married selected but spouseName is missing.");
            }

            if (!spouseSsn.matches("\\d{9}")) {
                throw new IllegalArgumentException("Marriage errors: spouseSSN must be exactly 9 digits.");
            }
        }
    }
}

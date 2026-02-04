package com.example.validationapi.rules;

import com.example.validationapi.models.HouseholdStatus;
import com.example.validationapi.models.ValidationRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class HouseholdValidation extends BaseValidation {

    public HouseholdValidation() {
        super("HouseholdValidation");
    }

    @Override
    public String getErrorMessage() {
        return "Invalid household status information. " + errorMessage;
    }

    @Override
    public void validate(ValidationRequest request) throws IllegalArgumentException {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null.");
        }

        HouseholdStatus household = request.getHousehold();
        if (household == null) {
            throw new IllegalArgumentException("Household status errors: householdStatus is required.");
        }

        ArrayList<String> errors = new ArrayList<>();

        int numberInHousehold = household.getNumberInHousehold();
        int numberInCollege = household.getNumberInCollege();
        
        if (numberInHousehold < 0) {
            errors.add("household.numberInHousehold cannot be less than 0.");
        }

        if (numberInCollege < 0) {
            errors.add("household.numberInCollege cannot be less than 0.");
        }
        
        if (numberInCollege > numberInHousehold) {
            errors.add("household.numberInCollege cannot be greater than householdStatus.numberInHousehold.");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(
                "Household status errors: " + String.join(", ", errors)
            );
        }
    }
}

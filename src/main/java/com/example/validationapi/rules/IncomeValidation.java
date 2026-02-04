package com.example.validationapi.rules;

import com.example.validationapi.models.IncomeModel;
import com.example.validationapi.models.ValidationRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class IncomeValidation extends BaseValidation {

    public IncomeValidation() {
        super("IncomeValidation");
    }

    @Override
    public void validate(ValidationRequest request) throws IllegalArgumentException {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null.");
        }

        IncomeModel income = request.getIncome();
        if (income == null) {
            throw new IllegalArgumentException("Income errors: income is required.");
        }

        ArrayList<String> errors = new ArrayList<>();

        // studentIncome
        if (income.getStudentIncome() == null) {
            errors.add("income.studentIncome is required.");
        } else if (income.getStudentIncome() < 0) {
            errors.add("income.studentIncome cannot be less than 0.");
        }

        // parentIncome (base rule: only validate if present; dependency rule will enforce required-ness)
        if (income.getParentIncome() != null && income.getParentIncome() < 0) {
            errors.add("income.parentIncome cannot be less than 0.");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Income errors: " + String.join(", ", errors));
        }
    }
}

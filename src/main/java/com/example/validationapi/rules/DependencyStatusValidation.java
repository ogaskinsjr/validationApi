package com.example.validationapi.rules;

import com.example.validationapi.models.DependencyType;
import com.example.validationapi.models.IncomeModel;
import com.example.validationapi.models.ValidationRequest;
import org.springframework.stereotype.Component;

@Component
public class DependencyStatusValidation extends BaseValidation {

    public DependencyStatusValidation() {
        super("DependencyStatusValidation");
    }

    @Override
    public void validate(ValidationRequest request) throws IllegalArgumentException {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null.");
        }

        String raw = request.getDependencyStatus();
        if (raw == null || raw.trim().isEmpty()) {
            throw new IllegalArgumentException("Dependency errors: dependencyStatus is required.");
        }

        DependencyType type;
        try {
            type = DependencyType.valueOf(raw.trim().toLowerCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Dependency errors: invalid dependencyStatus '" + raw + "'.");
        }

        IncomeModel income = request.getIncome();

        /* ───────── DEPENDENT RULES ───────── */
        if (type == DependencyType.dependent) {

            if (income == null) {
                throw new IllegalArgumentException("Dependency errors: dependent selected but income object is missing.");
            }

            if (income.getParentIncome() == null) {
                throw new IllegalArgumentException("Dependency errors: dependent selected but parentIncome is missing.");
            }

            if (income.getParentIncome() < 0) {
                throw new IllegalArgumentException("Dependency errors: parentIncome cannot be less than 0.");
            }
        }

        /* ───────── INDEPENDENT RULES ───────── */
        if (type == DependencyType.independent) {
            
            if (income != null && income.getParentIncome() != null) {
                throw new IllegalArgumentException(
                        "Dependency errors: parentIncome must not be provided when dependencyStatus is independent."
                );
            }

            if (income != null && income.getStudentIncome() != null && income.getStudentIncome() < 0) {
                throw new IllegalArgumentException("Dependency errors: studentIncome cannot be less than 0.");
            }
        }
    }
}

package com.example.validationapi.rules;

import com.example.validationapi.models.StudentInfo;
import com.example.validationapi.models.ValidationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

@Component
public class StudentInfoValidation extends BaseValidation {

    private final int minAge;
    private final int maxAge;

    public StudentInfoValidation(
            @Value("${validation.age.min}") int minAge,
            @Value("${validation.age.max}") int maxAge
    ) {
        super("StudentInfoValidation");
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    @Override
    public String getErrorMessage() {
        return "Student information is incomplete or invalid. " + errorMessage;
    }

    @Override
    public void validate(ValidationRequest request) throws IllegalArgumentException {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null.");
        }

        StudentInfo studentInfo = request.getStudentInfo();
        if (studentInfo == null) {
            throw new IllegalArgumentException("Student information errors: studentInfo cannot be null.");
        }

        ArrayList<String> errors = new ArrayList<>();

        if (isNullOrEmpty(studentInfo.getFirstName())) {
            errors.add("Field cannot be null or empty: studentInfo.firstName");
        }
        if (isNullOrEmpty(studentInfo.getLastName())) {
            errors.add("Field cannot be null or empty: studentInfo.lastName");
        }

        // dateOfBirth is LocalDate now
        if (studentInfo.getDateOfBirth() == null) {
            errors.add("Field cannot be null: studentInfo.dateOfBirth");
        }

        if (isNullOrEmpty(studentInfo.getSsn())) {
            errors.add("Field cannot be null or empty: studentInfo.ssn");
        }

        // Age check derived from DOB
        if (studentInfo.getDateOfBirth() != null) {
            int age = Period.between(studentInfo.getDateOfBirth(), LocalDate.now()).getYears();
            if (age < minAge || age > maxAge) {
                errors.add("Age must be between " + minAge + " and " + maxAge + ".");
            }
        }

        // SSN validation (use getSsn(), not getSSN())
        if (studentInfo.getSsn() != null && studentInfo.getSsn().length() != 9) {
            errors.add("SSN must be exactly 9 digits.");
        } else if (studentInfo.getSsn() != null && !studentInfo.getSsn().matches("\\d{9}")) {
            errors.add("SSN must contain only digits.");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Student information errors: " + String.join(", ", errors));
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}

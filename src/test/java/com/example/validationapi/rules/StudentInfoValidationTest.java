package com.example.validationapi.rules;

import com.example.validationapi.models.StudentInfo;
import com.example.validationapi.models.ValidationRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class StudentInfoValidationTest {

    @Test
    public void nullRequest_throws() {
        StudentInfoValidation v = new StudentInfoValidation(16, 120);
        assertThrows(IllegalArgumentException.class, () -> v.validate(null));
    }

    @Test
    public void missingFields_throws() {
        StudentInfoValidation v = new StudentInfoValidation(16, 120);
        ValidationRequest r = new ValidationRequest();
        r.setStudentInfo(new StudentInfo());
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> v.validate(r));
        assertTrue(ex.getMessage().contains("Student information errors"));
    }

    @Test
    public void invalidAge_throws() {
        StudentInfoValidation v = new StudentInfoValidation(18, 25);
        ValidationRequest r = new ValidationRequest();
        StudentInfo s = new StudentInfo();
        s.setFirstName("A");
        s.setLastName("B");
        s.setSsn("123456789");
        s.setDateOfBirth(LocalDate.now().minusYears(10));
        r.setStudentInfo(s);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> v.validate(r));
        assertTrue(ex.getMessage().contains("Age must be between"));
    }

    @Test
    public void invalidSsn_throws() {
        StudentInfoValidation v = new StudentInfoValidation(16, 120);
        ValidationRequest r = new ValidationRequest();
        StudentInfo s = new StudentInfo();
        s.setFirstName("A");
        s.setLastName("B");
        s.setSsn("abcdefghi");
        s.setDateOfBirth(LocalDate.now().minusYears(20));
        r.setStudentInfo(s);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> v.validate(r));
        assertTrue(ex.getMessage().contains("SSN must contain only digits") || ex.getMessage().contains("SSN must be exactly 9 digits"));
    }

    @Test
    public void severityHandler_runs() {
        StudentInfoValidation v = new StudentInfoValidation(16, 120);
        // just call to increase coverage
        v.severityHandler();
    }

    @Test
    public void getErrorMessage_overridden() {
        StudentInfoValidation v = new StudentInfoValidation(16, 120);
        assertTrue(v.getErrorMessage().contains("Student information"));
    }
}

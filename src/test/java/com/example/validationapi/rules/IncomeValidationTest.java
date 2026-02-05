package com.example.validationapi.rules;

import com.example.validationapi.models.IncomeModel;
import com.example.validationapi.models.ValidationRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IncomeValidationTest {

    @Test
    public void nullRequest_throws() {
        IncomeValidation v = new IncomeValidation();
        assertThrows(IllegalArgumentException.class, () -> v.validate(null));
    }

    @Test
    public void nullIncome_throws() {
        IncomeValidation v = new IncomeValidation();
        ValidationRequest r = new ValidationRequest();
        assertThrows(IllegalArgumentException.class, () -> v.validate(r));
    }

    @Test
    public void missingStudentIncome_reports() {
        IncomeValidation v = new IncomeValidation();
        ValidationRequest r = new ValidationRequest();
        IncomeModel im = new IncomeModel();
        r.setIncome(im);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> v.validate(r));
        assertTrue(ex.getMessage().contains("income.studentIncome is required"));
    }

    @Test
    public void negativeParentIncome_reports() {
        IncomeValidation v = new IncomeValidation();
        ValidationRequest r = new ValidationRequest();
        IncomeModel im = new IncomeModel();
        im.setStudentIncome(0);
        im.setParentIncome(-10);
        r.setIncome(im);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> v.validate(r));
        assertTrue(ex.getMessage().contains("income.parentIncome cannot be less than 0"));
    }

    @Test
    public void validIncome_passes() {
        IncomeValidation v = new IncomeValidation();
        ValidationRequest r = new ValidationRequest();
        IncomeModel im = new IncomeModel();
        im.setStudentIncome(100);
        im.setParentIncome(200);
        r.setIncome(im);
        assertDoesNotThrow(() -> v.validate(r));
    }

    @Test
    public void negativeStudentIncome_reports() {
        IncomeValidation v = new IncomeValidation();
        ValidationRequest r = new ValidationRequest();
        IncomeModel im = new IncomeModel();
        im.setStudentIncome(-1);
        im.setParentIncome(0);
        r.setIncome(im);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> v.validate(r));
        assertTrue(ex.getMessage().contains("income.studentIncome cannot be less than 0"));
    }
}

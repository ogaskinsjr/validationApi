package com.example.validationapi.rules;

import com.example.validationapi.models.IncomeModel;
import com.example.validationapi.models.ValidationRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DependencyStatusValidationTest {

    @Test
    public void nullRequest_throws() {
        DependencyStatusValidation v = new DependencyStatusValidation();
        assertThrows(IllegalArgumentException.class, () -> v.validate(null));
    }

    @Test
    public void missingDependencyStatus_throws() {
        DependencyStatusValidation v = new DependencyStatusValidation();
        ValidationRequest r = new ValidationRequest();
        assertThrows(IllegalArgumentException.class, () -> v.validate(r));
    }

    @Test
    public void dependentMissingIncome_throws() {
        DependencyStatusValidation v = new DependencyStatusValidation();
        ValidationRequest r = new ValidationRequest();
        r.setDependencyStatus("dependent");
        assertThrows(IllegalArgumentException.class, () -> v.validate(r));
    }

    @Test
    public void independentWithParentIncome_throws() {
        DependencyStatusValidation v = new DependencyStatusValidation();
        ValidationRequest r = new ValidationRequest();
        r.setDependencyStatus("independent");
        IncomeModel im = new IncomeModel();
        im.setParentIncome(1000);
        r.setIncome(im);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> v.validate(r));
        assertTrue(ex.getMessage().contains("parentIncome must not be provided"));
    }

    @Test
    public void independentNegativeStudentIncome_throws() {
        DependencyStatusValidation v = new DependencyStatusValidation();
        ValidationRequest r = new ValidationRequest();
        r.setDependencyStatus("independent");
        IncomeModel im = new IncomeModel();
        im.setStudentIncome(-5);
        r.setIncome(im);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> v.validate(r));
        assertTrue(ex.getMessage().contains("studentIncome cannot be less than 0"));
    }

    @Test
    public void dependentNegativeParentIncome_throws() {
        DependencyStatusValidation v = new DependencyStatusValidation();
        ValidationRequest r = new ValidationRequest();
        r.setDependencyStatus("dependent");
        IncomeModel im = new IncomeModel();
        im.setParentIncome(-50);
        r.setIncome(im);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> v.validate(r));
        assertTrue(ex.getMessage().contains("parentIncome cannot be less than 0"));
    }
}

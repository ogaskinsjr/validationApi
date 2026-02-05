package com.example.validationapi.rules;

import com.example.validationapi.models.HouseholdStatus;
import com.example.validationapi.models.ValidationRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HouseholdValidationTest {

    @Test
    public void nullRequest_throws() {
        HouseholdValidation v = new HouseholdValidation();
        assertThrows(IllegalArgumentException.class, () -> v.validate(null));
    }

    @Test
    public void nullHousehold_throws() {
        HouseholdValidation v = new HouseholdValidation();
        ValidationRequest r = new ValidationRequest();
        assertThrows(IllegalArgumentException.class, () -> v.validate(r));
    }

    @Test
    public void invalidNumbers_throwsMultipleErrors() {
        HouseholdValidation v = new HouseholdValidation();
        ValidationRequest r = new ValidationRequest();
        HouseholdStatus hs = new HouseholdStatus();
        hs.setNumberInHousehold(-1);
        hs.setNumberInCollege(5);
        r.setHousehold(hs);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> v.validate(r));
        assertTrue(ex.getMessage().contains("Household status errors"));
    }

    @Test
    public void validHousehold_passes() {
        HouseholdValidation v = new HouseholdValidation();
        ValidationRequest r = new ValidationRequest();
        HouseholdStatus hs = new HouseholdStatus();
        hs.setNumberInHousehold(3);
        hs.setNumberInCollege(1);
        r.setHousehold(hs);
        assertDoesNotThrow(() -> v.validate(r));
    }

    @Test
    public void getErrorMessage_overridden() {
        HouseholdValidation v = new HouseholdValidation();
        assertTrue(v.getErrorMessage().contains("Invalid household status"));
    }
}

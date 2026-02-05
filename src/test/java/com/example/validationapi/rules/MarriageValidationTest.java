package com.example.validationapi.rules;

import com.example.validationapi.models.SpouseInfo;
import com.example.validationapi.models.ValidationRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MarriageValidationTest {

    @Test
    public void missingMaritalStatus_throws() {
        MarriageValidation v = new MarriageValidation();
        ValidationRequest r = new ValidationRequest();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> v.validate(r));
        assertTrue(ex.getMessage().contains("maritalStatus is required"));
    }

    @Test
    public void marriedMissingSpouseInfo_throws() {
        MarriageValidation v = new MarriageValidation();
        ValidationRequest r = new ValidationRequest();
        r.setMaritalStatus("married");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> v.validate(r));
        assertTrue(ex.getMessage().contains("spouseInfo is missing"));
    }

    @Test
    public void marriedInvalidSpouseSSN_throws() {
        MarriageValidation v = new MarriageValidation();
        ValidationRequest r = new ValidationRequest();
        r.setMaritalStatus("married");
        SpouseInfo s = new SpouseInfo();
        s.setFirstName("A");
        s.setLastName("B");
        s.setSpouseSSN("123");
        r.setSpouseInfo(s);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> v.validate(r));
        assertTrue(ex.getMessage().contains("spouseSSN must be exactly 9 digits"));
    }

    @Test
    public void marriedValid_passes() {
        MarriageValidation v = new MarriageValidation();
        ValidationRequest r = new ValidationRequest();
        r.setMaritalStatus("married");
        SpouseInfo s = new SpouseInfo();
        s.setFirstName("A");
        s.setLastName("B");
        s.setSpouseSSN("123456789");
        r.setSpouseInfo(s);
        assertDoesNotThrow(() -> v.validate(r));
    }

    @Test
    public void invalidMaritalStatus_throws() {
        MarriageValidation v = new MarriageValidation();
        ValidationRequest r = new ValidationRequest();
        r.setMaritalStatus("weird");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> v.validate(r));
        assertTrue(ex.getMessage().contains("invalid maritalStatus"));
    }
}

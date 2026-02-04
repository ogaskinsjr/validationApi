package com.example.validationapi.models;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;

@Data
public class SpouseInfo {
    private String firstName;
    private String lastName;
    private String spouseSSN;
}
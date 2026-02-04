package com.example.validationapi.models;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;

@Data
public class StudentInfo {
    private String firstName;
    private String lastName;
    private String ssn;
    private LocalDate dateOfBirth;
}
package com.example.validationapi.models;

import java.util.UUID;
import lombok.Data;

@Data
public class ValidationRequest {
    private UUID requestId;
    private StudentInfo studentInfo;
    private String dependencyStatus;
    private String maritalStatus;
    private String spouseSSN;
    private String stateOfResidence;
    private HouseholdStatus household;
    private IncomeModel income;
}
# Validation API - Spring Boot Application

This is a basic Spring Boot application setup.

## Prerequisites

- Java 17 or higher
- Maven 3.6+ (or Gradle)

## Setup

1. Ensure you have Java and Maven installed.

2. To build the application:
   ```
   mvn clean install
   ```

3. To run the application:
   ```
   mvn spring-boot:run
   ```

The application will start on http://localhost:8080

## API Endpoints
At http://localhost:8080/validate POST, you can send your request for validation.

A good request like so will return this
Good Request:
{
  "studentInfo": {
    "firstName": "Jane",
    "lastName": "Smith",
    "ssn": "123456789",
    "dateOfBirth": "2003-05-15"
  },
  "dependencyStatus": "dependent",
  "maritalStatus": "single",
  "household": {
    "numberInHousehold": 4,
    "numberInCollege": 1
  },
  "income": {
    "studentIncome": 5000,
    "parentIncome": 65000
  },
  "stateOfResidence": "CA"
}

Response:
{
    "valid": true,
    "messages": [
        "[SUCCESS] Validation passed for rule: DependencyStatusValidation",
        "[SUCCESS] Validation passed for rule: HouseholdValidation",
        "[SUCCESS] Validation passed for rule: IncomeValidation",
        "[SUCCESS] Validation passed for rule: MarriageValidation",
        "[SUCCESS] Validation passed for rule: StateOfResidenceValidation",
        "[SUCCESS] Validation passed for rule: StudentInfoValidation"
    ]
}


BadRequest - 400
{
    "valid": false,
    "messages": [
        "[ERROR] Dependency errors: dependent selected but parentIncome is missing.",
        "[ERROR] Household status errors: household.numberInCollege cannot be greater than householdStatus.numberInHousehold.",
        "[ERROR] Income errors: income.studentIncome cannot be less than 0.",
        "[ERROR] Marriage errors: married selected but spouseInfo is missing.",
        "[ERROR] State of residence errors: invalid state code 'XX'.",
        "[ERROR] Student information errors: Age must be between 14 and 120.",
        "[ERROR] SSN must be exactly 9 digits."
    ]
}

## Project Structure

- `src/main/java/com/example/validationapi/ValidationApplication.java` - Main application class
- `src/main/resources/application.properties` - Application configuration
- `pom.xml` - Maven configuration
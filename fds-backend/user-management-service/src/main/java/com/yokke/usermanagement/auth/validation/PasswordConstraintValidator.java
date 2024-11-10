package com.yokke.usermanagement.auth.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        // Define regex patterns
        String specialCharPattern = "[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]";
        String upperCasePattern = "[A-Z]";
        String lowerCasePattern = "[a-z]";
        String digitPattern = "\\d";

        // Check all conditions
        boolean isAtLeast12 = password.length() >= 12;
        boolean hasSpecialChar = password.matches(".*" + specialCharPattern + ".*");
        boolean hasUpperCase = password.matches(".*" + upperCasePattern + ".*");
        boolean hasLowerCase = password.matches(".*" + lowerCasePattern + ".*");
        boolean hasDigit = password.matches(".*" + digitPattern + ".*");

        // For debugging
        System.out.println("Password length check: " + isAtLeast12);
        System.out.println("Special character check: " + hasSpecialChar);
        System.out.println("Uppercase check: " + hasUpperCase);
        System.out.println("Lowercase check: " + hasLowerCase);
        System.out.println("Digit check: " + hasDigit);

        return isAtLeast12 &&
                hasSpecialChar &&
                hasUpperCase &&
                hasLowerCase &&
                hasDigit;
    }
}
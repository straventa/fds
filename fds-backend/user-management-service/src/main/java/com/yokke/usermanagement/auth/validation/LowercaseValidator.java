package com.yokke.usermanagement.auth.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LowercaseValidator implements ConstraintValidator<Lowercase, String> {

    @Override
    public void initialize(Lowercase lowercase) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values can be handled separately with @NotNull if needed
        }
        return value.equals(value.toLowerCase());
    }
}
package com.yokke.usermanagement.auth.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    private String passwordField;
    private String confirmPasswordField;

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        this.passwordField = constraintAnnotation.passwordField();
        this.confirmPasswordField = constraintAnnotation.confirmPasswordField();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        BeanWrapperImpl wrapper = new BeanWrapperImpl(obj);
        String password = (String) wrapper.getPropertyValue(passwordField);
        String confirmPassword = (String) wrapper.getPropertyValue(confirmPasswordField);

        return password != null && password.equals(confirmPassword);
    }
}
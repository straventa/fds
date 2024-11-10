package com.yokke.usermanagement.auth.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LowercaseValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Lowercase {

    String message() default "The field must be in lowercase.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
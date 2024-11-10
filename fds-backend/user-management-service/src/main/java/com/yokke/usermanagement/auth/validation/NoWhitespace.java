package com.yokke.usermanagement.auth.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoWhitespaceValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoWhitespace {

    String message() default "The field must not contain leading or trailing whitespace.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
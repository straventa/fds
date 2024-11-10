package com.yokke.usermanagement.transactions.fds_transactions.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConditionalNotNullValidator.class)
public @interface ConditionalNotNull {
    String message() default "Field cannot be null when {dependsOn} is {expectedValues}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String dependsOn();

    String[] expectedValues();
}

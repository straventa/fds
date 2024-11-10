package com.yokke.usermanagement.transactions.fds_transactions.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class ConditionalNotNullValidator implements ConstraintValidator<ConditionalNotNull, Object> {
    private String dependsOn;
    private String[] expectedValues;
    private String message;

    @Override
    public void initialize(ConditionalNotNull annotation) {
        this.dependsOn = annotation.dependsOn();
        this.expectedValues = annotation.expectedValues();
        this.message = annotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object parent = getParentObject(context);
        if (parent == null) {
            return true;
        }

        try {
            BeanWrapperImpl wrapper = new BeanWrapperImpl(parent);
            Object dependsOnValue = wrapper.getPropertyValue(dependsOn);

            if (dependsOnValue != null) {
                for (String expectedValue : expectedValues) {
                    if (expectedValue.equals(dependsOnValue.toString())) {
                        return value != null;
                    }
                }
            }
        } catch (Exception e) {
            // Log error if needed
            e.printStackTrace();
        }
        return true;
    }

    private Object getParentObject(ConstraintValidatorContext context) {
        try {
            return context.unwrap(Class.class);
        } catch (Exception first) {
            try {
                jakarta.validation.Path path = context.unwrap(jakarta.validation.Path.class);
                if (path != null) {
                    for (jakarta.validation.Path.Node node : path) {
                        Object bean = node.toString();
                        if (bean != null) {
                            return bean;
                        }
                    }
                }
            } catch (Exception second) {
                // Fall through
            }
        }
        return null;
    }
}
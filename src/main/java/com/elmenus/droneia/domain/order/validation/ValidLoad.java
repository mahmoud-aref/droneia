package com.elmenus.droneia.domain.order.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DroneMedicationValidator.class)
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLoad {
    String message() default "Invalid load";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}

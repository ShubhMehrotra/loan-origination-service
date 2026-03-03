package com.shubh.loan.origination.validation;

import jakarta.validation.Payload;
import jakarta.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.shubh.loan.origination.validation.ValidMonthlyIncomeValidator;

@Constraint(validatedBy = ValidMonthlyIncomeValidator.class)
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)

public @interface ValidMonthlyIncome {
    String message() default "Monthly income must be greater than 0";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

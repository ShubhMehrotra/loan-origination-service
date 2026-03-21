package com.shubh.loan.origination.validation;

import jakarta.validation.*;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LoanAmountValidator.class)
public @interface LoanAmountValidation {
    String message() default "Loan amount must be between 10,000 and 50,00,000";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

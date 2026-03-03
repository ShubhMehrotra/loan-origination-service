package com.shubh.loan.origination.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class ValidMonthlyIncomeValidator implements ConstraintValidator<ValidMonthlyIncome, BigDecimal> {

    @Override
    public void initialize(ValidMonthlyIncome constraintAnnotation) {
        // no initialization required
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        // Treat null as valid so that @NotNull can be used when presence is required.
        if (value == null) {
            return true;
        }
        return value.compareTo(BigDecimal.ZERO) > 0;
    }
}


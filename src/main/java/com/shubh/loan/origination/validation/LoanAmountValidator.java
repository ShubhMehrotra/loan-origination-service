package com.shubh.loan.origination.validation;

import jakarta.validation.*;
import java.math.*;

public class LoanAmountValidator implements ConstraintValidator<LoanAmountValidation, BigDecimal> {

    @Override
    public boolean isValid(BigDecimal amount, ConstraintValidatorContext context) {
        return amount != null &&
                amount.compareTo(BigDecimal.valueOf(10_000)) >= 0 &&
                amount.compareTo(BigDecimal.valueOf(50_00_000)) <= 0;
    }
}

package com.shubh.loan.origination.validation;

import jakarta.validation.*;

public class CreditScoreValidator implements ConstraintValidator<ValidCreditScore, Integer> {
    @Override
    public boolean isValid(Integer score, ConstraintValidatorContext context) {
        return score != null && score >= 300 && score <= 900;
    }
}

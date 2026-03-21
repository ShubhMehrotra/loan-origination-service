package com.shubh.loan.origination.validation;

import jakarta.validation.*;

public class TenureValidator implements ConstraintValidator<ValidTenure,Integer> {
    @Override
    public boolean isValid(Integer tenure, ConstraintValidatorContext context) {
        if (tenure == null) return false;

        return tenure >= 6 && tenure <= 360;
    }
}

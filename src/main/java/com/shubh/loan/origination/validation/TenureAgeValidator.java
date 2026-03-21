package com.shubh.loan.origination.validation;

import com.shubh.loan.origination.model.dto.*;
import jakarta.validation.*;

public class TenureAgeValidator implements ConstraintValidator<ValidAgeTenure, LoanApplicationRequest> {
    @Override
    public boolean isValid(LoanApplicationRequest req, ConstraintValidatorContext context) {

        if (req == null || req.applicant() == null || req.loan() == null) {
            return true;
        }
        int age = req.applicant().age();
        int tenureYears = req.loan().tenureMonths() / 12;

        return (age + tenureYears) <= 65;
    }

}

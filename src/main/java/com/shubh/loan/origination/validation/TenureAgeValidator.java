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
        int tenureMonths = req.loan().tenureMonths();
        int tenureYears = tenureMonths / 12;

        boolean valid = true;

        context.disableDefaultConstraintViolation();

        if (age < 21 || age > 60) {
            context.buildConstraintViolationWithTemplate("Age must be between 21 and 60")
                    .addPropertyNode("applicant")
                    .addPropertyNode("age")
                    .addConstraintViolation();
            valid = false;
        }

        if ((age + tenureYears) > 65) {
            context.buildConstraintViolationWithTemplate("Age + tenure (in years) must not exceed 65")
                    .addPropertyNode("loan")
                    .addPropertyNode("tenureMonths")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }

}

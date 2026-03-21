package com.shubh.loan.origination.model.dto;

import com.shubh.loan.origination.model.constants.*;
import com.shubh.loan.origination.validation.*;
import jakarta.validation.constraints.*;
import java.math.*;

public record ApplicantDTO(


        int age,
        @ValidCreditScore
        int creditScore,
        @Positive
        BigDecimal monthlyIncome,
        EmploymentType employmentType


    ) {}


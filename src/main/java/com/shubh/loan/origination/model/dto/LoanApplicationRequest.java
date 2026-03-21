package com.shubh.loan.origination.model.dto;


import com.shubh.loan.origination.validation.*;

@ValidAgeTenure
public record LoanApplicationRequest(@Valid ApplicantDTO applicant,
public record LoanApplicationRequest(@Valid ApplicantDTO applicant,
         @Valid  LoanDTO loan) {}

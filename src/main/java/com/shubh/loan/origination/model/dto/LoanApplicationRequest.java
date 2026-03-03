package com.shubh.loan.origination.model;


import com.shubh.loan.origination.model.constants.LoanPurpose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanApplicationRequest {

    private Applicant applicant;
    private Double loanAmount;
    private Integer loanTermMonths;
    private LoanPurpose loanPurpose;

}


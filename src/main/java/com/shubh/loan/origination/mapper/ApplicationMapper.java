package com.shubh.loan.origination.mapper;

import com.shubh.loan.origination.model.*;
import com.shubh.loan.origination.model.dto.*;
import org.springframework.stereotype.*;

@Component
public class ApplicationMapper {
    public Application toEntity(LoanApplicationRequest req) {

        ApplicantDTO a = req.applicant();
        LoanDTO l = req.loan();

        Application app = new Application();
        Applicant applicant= new Applicant();
        Loan loan = new Loan();
        applicant.setAge(a.age());
        applicant.setCreditScore(a.creditScore());
        applicant.setMonthlyIncome(a.monthlyIncome());
        applicant.setEmploymentType(a.employmentType());
        app.setApplicant(applicant);
        loan.setAmount(l.amount());
        loan.setTenureMonths(l.tenureMonths());
        loan.setPurpose(l.purpose());
        app.setLoan(loan);


        return app;
    }
}

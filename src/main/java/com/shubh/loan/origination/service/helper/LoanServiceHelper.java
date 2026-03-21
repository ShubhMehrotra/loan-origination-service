package com.shubh.loan.origination.service.helper;

import com.shubh.loan.origination.model.constants.*;
import com.shubh.loan.origination.model.dto.*;
import java.math.*;

public class LoanServiceHelper {

    public BigDecimal calculateInterest(LoanApplicationRequest req) {

        int creditScore = req.applicant().creditScore();

        if (creditScore >= 750) return BigDecimal.valueOf(8.5);
        if (creditScore >= 650) return BigDecimal.valueOf(10.5);
        return BigDecimal.valueOf(13.5);
    }

    public BigDecimal calculateEMI(BigDecimal principal,
                                    BigDecimal rate,
                                    int tenureMonths) {

        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);

        double r = monthlyRate.doubleValue();
        double p = principal.doubleValue();
        int n = tenureMonths;

        double emi = (p * r * Math.pow(1 + r, n)) /
                (Math.pow(1 + r, n) - 1);

        return BigDecimal.valueOf(emi).setScale(2, RoundingMode.HALF_UP);
    }

    public String calculateRisk(LoanApplicationRequest req) {

        int score = req.applicant().creditScore();

        if (score >= 750) return "LOW";
        if (score >= 650) return "MEDIUM";
        return "HIGH";
    }

    public String approve(LoanApplicationRequest req, BigDecimal emi) {

        int age = req.applicant().age();
        int tenureYears = req.loan().tenureMonths() / 12;

        BigDecimal income = req.applicant().monthlyIncome();
        BigDecimal loanAmount = req.loan().amount();

        // 1. Age + tenure rule
        if (age + tenureYears > 65) {
            return "REJECTED";
        }

        // 2. EMI affordability
        BigDecimal maxAllowed = income.multiply(BigDecimal.valueOf(0.5));
        if (emi.compareTo(maxAllowed) > 0) {
            return "REJECTED";
        }

        // 3. Credit score
        if (req.applicant().creditScore() < 600) {
            return "REJECTED";
        }

        if (income.compareTo(BigDecimal.valueOf(20000)) < 0) {
            return "REJECTED";
        }

        if (loanAmount.compareTo(income.multiply(BigDecimal.valueOf(20))) > 0) {
            return "REJECTED";
        }

        if (req.applicant().employmentType() == EmploymentType.UNEMPLOYED) {
            return "REJECTED";
        }

        return "APPROVED";
    }


}

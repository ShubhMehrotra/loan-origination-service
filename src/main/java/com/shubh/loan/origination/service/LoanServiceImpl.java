package com.shubh.loan.origination.service;

import com.shubh.loan.origination.mapper.ApplicationMapper;
import com.shubh.loan.origination.model.constants.*;
import com.shubh.loan.origination.model.dto.*;
import com.shubh.loan.origination.repository.ApplicationRepository;
import com.shubh.loan.origination.service.helper.LoanServiceHelper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class LoanServiceImpl implements LoanService {

    private final ApplicationRepository applicationRepository;
    private final LoanServiceHelper loanServiceHelper;

    public LoanServiceImpl(ApplicationRepository applicationRepository,
                           LoanServiceHelper loanServiceHelper) {
        this.applicationRepository = applicationRepository;
        this.loanServiceHelper = loanServiceHelper;
    }

    @Override
    public LoanResponse process(LoanApplicationRequest request) {

        log.info("Processing loan application for applicant");
        var applicationEntity = ApplicationMapper.toEntity(request);
        log.info("Calculating interest rate based on credit score and loan purpose");
        BigDecimal interest = loanServiceHelper.calculateInterest(request);
        log.info("Calculated interest rate: {}", interest);
        log.info("Calculating EMI based on amount, interest and tenure");
        BigDecimal emi = loanServiceHelper.calculateEMI(
                request.loan().amount(),
                interest,
                request.loan().tenureMonths()
        );
        log.info("Calculated EMI: {}", emi);
        log.info("Calculating risk band based on credit score");

        String risk = loanServiceHelper.calculateRisk(request);
        log.info("Calculated risk band: {}", risk);
        ApplicationStatus status = loanServiceHelper.approve(request, emi);

        applicationEntity.setInterestRate(interest);
        applicationEntity.setEmi(emi);
        applicationEntity.setRiskBand(RiskBand.valueOf(risk));
        applicationEntity.setStatus(String.valueOf(status));

        var saved = applicationRepository.save(applicationEntity);

        log.info("Completed processing. Status: {}", status);

        Offer offer = null;
        if (status == ApplicationStatus.APPROVED) {
            BigDecimal totalPayable = emi.multiply(BigDecimal.valueOf(request.loan().tenureMonths()));
            offer = new Offer(interest, request.loan().tenureMonths(), emi, totalPayable);
        }

        return new LoanResponse(
                saved.getApplicationId(),
                String.valueOf(status),
                risk,
                offer
        );
    }
}
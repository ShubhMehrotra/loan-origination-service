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
    private final ApplicationMapper mapper;

    public LoanServiceImpl(ApplicationRepository applicationRepository,
                           LoanServiceHelper loanServiceHelper,
                           ApplicationMapper mapper) {
        this.applicationRepository = applicationRepository;
        this.loanServiceHelper = loanServiceHelper;
        this.mapper = mapper;
    }

    @Override
    public LoanResponse process(LoanApplicationRequest request) {

        log.info("Processing loan application for applicant");
        var applicationEntity = mapper.toEntity(request);
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
        String status = loanServiceHelper.approve(request, emi);

        applicationEntity.setInterestRate(interest);
        applicationEntity.setEmi(emi);
        applicationEntity.setRiskBand(RiskBand.valueOf(risk));
        applicationEntity.setStatus(status);

        var saved = applicationRepository.save(applicationEntity);

        log.info("Completed processing. Status: {}", status);

        return new LoanResponse(
                saved.getApplicationId(),
                status,
                emi,
                interest,
                risk
        );
    }
}
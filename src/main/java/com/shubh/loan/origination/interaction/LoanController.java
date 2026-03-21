package com.shubh.loan.origination.interaction;

import com.shubh.loan.origination.model.*;
import com.shubh.loan.origination.model.dto.*;
import com.shubh.loan.origination.service.LoanService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/loans")
@Slf4j
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<LoanResponse>> applyLoan(
            @Valid @RequestBody LoanApplicationRequest request) {

        log.info("Received loan application request");

        LoanResponse response = loanService.process(request);

        ApiResponse<LoanResponse> apiResponse = new ApiResponse<>(
                "Loan processed successfully",
                response,
                LocalDateTime.now()
        );

        log.info("Loan processed successfully with status: {}", response.status());

        return ResponseEntity.ok(apiResponse);
    }
}
package com.shubh.loan.origination.model.dto;

import java.math.*;
import java.util.*;

public record LoanResponse(
        UUID applicationId,
        String status,
        BigDecimal emi,
        BigDecimal interestRate,
        String riskBand
) {}

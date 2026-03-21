package com.shubh.loan.origination.model.dto;

import java.math.BigDecimal;
import java.util.*;

public record Offer(
        BigDecimal interestRate,
        int tenureMonths,
        BigDecimal emi,
        BigDecimal totalPayable
) {}


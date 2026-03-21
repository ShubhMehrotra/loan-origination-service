package com.shubh.loan.origination.model.dto;

import java.util.*;

public record Offer(
        int tenureMonths,
        BigDecimal totalPayable


package com.shubh.loan.origination.model.dto;

import com.shubh.loan.origination.model.constants.*;
import com.shubh.loan.origination.validation.*;
import java.math.*;

public record LoanDTO (BigDecimal amount,
        @ValidTenure
        int tenureMonths,
        LoanPurpose purpose){}

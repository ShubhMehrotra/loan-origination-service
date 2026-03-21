package com.shubh.loan.origination.model.dto;

import java.util.*;

public record LoanResponse(
        String status,
        Offer offer

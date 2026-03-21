package com.shubh.loan.origination.service;

import com.shubh.loan.origination.model.dto.*;

public interface LoanService {

    LoanResponse process(LoanApplicationRequest request);
}

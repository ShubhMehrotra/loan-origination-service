package com.shubh.loan.origination.model;

import com.shubh.loan.origination.model.constants.*;
import jakarta.persistence.*;
import java.math.*;
import java.util.*;
import lombok.*;

@Entity
@Table(name = "applications")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    private UUID applicationId;
    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;
    private Loan loan;

    private RiskBand riskBand;
    private BigDecimal interestRate;
    private BigDecimal emi;

    private String status;
    private List<RejectionReason> rejectionReasons;


}

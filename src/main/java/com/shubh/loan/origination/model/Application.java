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
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID applicationId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @Enumerated(EnumType.STRING)
    private RiskBand riskBand;

    private BigDecimal interestRate;

    private BigDecimal emi;

    private String status;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<RejectionReason> rejectionReasons;
}

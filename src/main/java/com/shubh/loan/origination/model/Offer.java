package com.shubh.loan.origination.model;

import jakarta.persistence.*;
import java.math.*;
import java.util.*;
import lombok.*;

@Entity
@Table(name = "offers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private BigDecimal interestRate;
    private int tenureMonths;
    private BigDecimal emi;
    private BigDecimal totalPayable;
}

package com.shubh.loan.origination.model;

import com.shubh.loan.origination.model.constants.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "applicants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private Integer age;
    private BigDecimal monthlyIncome;
    private EmploymentType employmentType;
    private Integer creditScore;


}

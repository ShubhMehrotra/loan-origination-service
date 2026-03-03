package com.shubh.loan.origination.model;

import com.shubh.loan.origination.model.constants.*;
import com.shubh.loan.origination.validation.*;
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
    private UUID id;
    private String firstName;
    private String lastName;
    @ValidAge
    private Integer age;
    @ValidMonthlyIncome
    private BigDecimal monthlyIncome;
    private EmploymentType employmentType;
    @ValidCreditScore
    private Integer creditScore;


}

# Loan Origination Service

A Spring Boot REST API service for processing and managing loan applications with intelligent approval/rejection decision-making based on multiple criteria.

## 📋 Table of Contents

1. [Overview](#overview)
2. [Features Implemented](#features-implemented)
3. [Architecture](#architecture)
4. [Getting Started](#getting-started)
5. [API Documentation](#api-documentation)
6. [Data Models](#data-models)
7. [Business Logic & Validation Rules](#business-logic--validation-rules)
8. [Project Structure](#project-structure)
9. [Technologies & Dependencies](#technologies--dependencies)
10. [Implementation Steps](#implementation-steps-high-level)

---

## 🎯 Overview

The **Loan Origination Service** is designed to automate the loan application process. It:
- Accepts loan applications from applicants
- Validates applicant information and loan parameters
- Evaluates applications against predefined approval criteria
- Generates risk assessments
- Calculates loan offers (interest rate, EMI, tenure)
- Returns either an approval with detailed offer or a rejection

**Key Benefit**: Reduces manual processing time and ensures consistent, rule-based decision-making.

---

## ✨ Features Implemented

### 1. **Loan Application Submission**
   - REST endpoint: `POST /api/v1/loans/apply`
   - Accepts applicant details and loan requirements
   - Returns detailed response with decision and offer (if approved)

### 2. **Comprehensive Input Validation**
   - **Applicant validation**:
     - Age must be between 21–60 years
     - Credit score must be between 300–900
     - Monthly income must be positive
     - Employment type validation (SALARIED, SELF_EMPLOYED, UNEMPLOYED)
   
   - **Loan validation**:
     - Loan amount: 10,000 – 50,00,000 INR
     - Tenure: 6–360 months
     - Purpose: PERSONAL, HOME, EDUCATION, AUTO
   
   - **Combined validations**:
     - Age + Tenure (in years) must not exceed 65
     - EMI must not exceed 60% of monthly income
     - Custom annotations for reusable validation logic

### 3. **Intelligent Approval Engine**
   - Multi-criteria decision logic
   - Approval criteria:
     ✅ Credit score ≥ 600
     ✅ Age between 21–60
     ✅ Age + Tenure ≤ 65 years
     ✅ EMI ≤ 60% of monthly income
     ✅ Monthly income ≥ 20,000 INR
     ✅ Loan amount ≤ 20× monthly income
     ✅ Employment type ≠ UNEMPLOYED

### 4. **Risk Assessment**
   - Automatic risk band calculation based on credit score:
     - **LOW**: Credit score 750–900
     - **MEDIUM**: Credit score 600–749
     - **HIGH**: Credit score 300–599

### 5. **Loan Offer Generation**
   - **Interest rate calculation** based on:
     - Credit score (risk band)
     - Loan purpose
     - Other applicant factors
   
   - **EMI calculation** using formula:
     - EMI = P × r × (1+r)^n / ((1+r)^n - 1)
     - Where: P = principal, r = monthly rate, n = number of months
   
   - **Offer details include**:
     - Interest rate (annual %)
     - Tenure (months)
     - Monthly EMI amount
     - Total payable amount

### 6. **Database Persistence**
   - MySQL integration with Hibernate/JPA
   - Entities for:
     - `Application`: Main application record
     - `Applicant`: Applicant details
     - `Loan`: Loan details
   - Automatic UUID generation for application IDs
   - Cascade operations for related entities

### 7. **Custom Validation Annotations**
   - `@ValidAgeTenure`: Combined age and tenure validation
   - `@ValidCreditScore`: Credit score range validation
   - `@ValidTenure`: Tenure range validation
   - `@LoanAmountValidation`: Loan amount range validation
   - `@ValidMonthlyIncome`: Income validation

---

## 🏗️ Architecture

### Layered Architecture

```
┌─────────────────────────────────────────────┐
│         REST Controller Layer               │
│  (LoanController - /api/v1/loans/apply)    │
└──────────────┬──────────────────────────────┘
               │
┌──────────────▼──────────────────────────────┐
│      Service Layer                          │
│  (LoanService, LoanServiceImpl)             │
│  - Business logic                           │
│  - Decision making                          │
└──────────────┬──────────────────────────────┘
               │
┌──────────────▼──────────────────────────────┐
│      Helper/Utility Layer                   │
│  (LoanServiceHelper)                       │
│  - Interest calculation                     │
│  - EMI calculation                          │
│  - Risk band calculation                    │
│  - Approval logic                           │
└──────────────┬──────────────────────────────┘
               │
┌──────────────▼──────────────────────────────┐
│      Repository Layer                       │
│  (ApplicationRepository - JPA)              │
│  - Database operations                      │
│  - CRUD operations                          │
└──────────────┬──────────────────────────────┘
               │
┌──────────────▼──────────────────────────────┐
│      Database Layer                         │
│  (MySQL)                                   │
└─────────────────────────────────────────────┘
```

### Data Flow

```
Request (JSON)
     ↓
Controller (Validation annotations applied)
     ↓
Service (ApplicationMapper converts DTO → Entity)
     ↓
LoanServiceHelper:
  - calculateInterest()
  - calculateEMI()
  - calculateRisk()
  - approve()
     ↓
Entity saved to Database
     ↓
Response (LoanResponse DTO with offer/rejection)
```

---

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Gradle 8.x
- MySQL 5.7+ (or compatible)
- Maven/Gradle knowledge (optional)

### Installation Steps

#### 1. Clone/Setup Project
```bash
cd /media/shubhmehrotra/JAVA/LoanOriginationService
```

#### 2. Configure Database
Update `src/main/resources/application.yaml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/loan_origination
    username: root
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
```

Create database (if not exists):
```sql
CREATE DATABASE loan_origination;
```

#### 3. Build Project
```bash
./gradlew clean build
```

#### 4. Run Application
```bash
./gradlew bootRun
```

Application starts on: **http://localhost:8080**


---

## 📚 API Documentation

### Endpoint: Apply for Loan

**Method**: `POST`  
**URL**: `/api/v1/loans/apply`  
**Content-Type**: `application/json`

#### Request Body

```json
{
  "applicant": {
    "age": 30,
    "creditScore": 750,
    "monthlyIncome": 100000.00,
    "employmentType": "SALARIED"
  },
  "loan": {
    "amount": 500000.00,
    "tenureMonths": 36,
    "purpose": "PERSONAL"
  }
}
```

#### Success Response (200 OK - APPROVED)

```json
{
  "applicationId": "550e8400-e29b-41d4-a716-446655440000",
  "status": "APPROVED",
  "riskBand": "LOW",
  "offer": {
    "interestRate": 9.5,
    "tenureMonths": 36,
    "emi": 15789.45,
    "totalPayable": 568420.20
  }
}
```

#### Success Response (200 OK - REJECTED)

```json
{
  "applicationId": "550e8400-e29b-41d4-a716-446655440001",
  "status": "REJECTED",
  "riskBand": "HIGH",
  "offer": null
}
```

#### Validation Error Response (400 Bad Request)

```json
{
  "timestamp": "2026-03-21T14:30:00.123456",
  "status": 400,
  "error": "Bad Request",
  "message": "Age must be between 21 and 60",
  "path": "/api/v1/loans/apply"
}
```

### cURL Examples

#### Example 1: Approved Application
```bash
curl -X POST 'http://localhost:8080/api/v1/loans/apply' \
  -H 'Content-Type: application/json' \
  -d '{
    "applicant": {
      "age": 35,
      "creditScore": 800,
      "monthlyIncome": 150000.00,
      "employmentType": "SALARIED"
    },
    "loan": {
      "amount": 750000.00,
      "tenureMonths": 48,
      "purpose": "HOME"
    }
  }'
```

#### Example 2: Rejected (Credit Score Too Low)
```bash
curl -X POST 'http://localhost:8080/api/v1/loans/apply' \
  -H 'Content-Type: application/json' \
  -d '{
    "applicant": {
      "age": 30,
      "creditScore": 550,
      "monthlyIncome": 50000.00,
      "employmentType": "SALARIED"
    },
    "loan": {
      "amount": 300000.00,
      "tenureMonths": 36,
      "purpose": "PERSONAL"
    }
  }'
```

#### Example 3: Rejected (Age Out of Range)
```bash
curl -X POST 'http://localhost:8080/api/v1/loans/apply' \
  -H 'Content-Type: application/json' \
  -d '{
    "applicant": {
      "age": 65,
      "creditScore": 750,
      "monthlyIncome": 100000.00,
      "employmentType": "SALARIED"
    },
    "loan": {
      "amount": 500000.00,
      "tenureMonths": 36,
      "purpose": "PERSONAL"
    }
  }'
```

---

## 📊 Data Models

### Application Entity
- `applicationId` (UUID): Unique identifier
- `applicant` (OneToMany): Reference to Applicant
- `loan` (OneToOne): Reference to Loan
- `status` (String): APPROVED/REJECTED
- `riskBand` (Enum): LOW/MEDIUM/HIGH
- `interestRate` (BigDecimal): Approved interest rate
- `emi` (BigDecimal): Calculated EMI
- `rejectionReasons` (List): Reasons if rejected

### Applicant Entity
- `id` (UUID): Unique identifier
- `firstName` (String)
- `lastName` (String)
- `age` (Integer): 21–60
- `monthlyIncome` (BigDecimal): Income in INR
- `creditScore` (Integer): 300–900
- `employmentType` (Enum): SALARIED/SELF_EMPLOYED/UNEMPLOYED

### Loan Entity
- `id` (UUID): Unique identifier
- `amount` (BigDecimal): 10,000 – 50,00,000 INR
- `tenureMonths` (Integer): 6–360 months
- `purpose` (Enum): PERSONAL/HOME/EDUCATION/AUTO

### DTOs (Data Transfer Objects)
- `LoanApplicationRequest`: Request payload
- `ApplicantDTO`: Applicant details in request
- `LoanDTO`: Loan details in request
- `LoanResponse`: Response payload
- `Offer`: Loan offer details in response

---

## 🔧 Business Logic & Validation Rules

### Validation Hierarchy

#### 1. **Field-Level Validation** (Annotations)
```
@NotNull, @Positive, @Min, @Max
↓
Custom Validators (@ValidAgeTenure, @ValidCreditScore, etc.)
```

#### 2. **Business Rule Validation** (LoanServiceHelper)
```
Credit Score Check (≥600)
↓
Age Check (21-60)
↓
Age + Tenure Check (≤65 years)
↓
EMI Affordability Check (≤60% of income)
↓
Income Minimum Check (≥20,000)
↓
Loan-to-Income Ratio Check (≤20x)
↓
Employment Type Check (≠UNEMPLOYED)
↓
Decision: APPROVED / REJECTED
```

### Interest Rate Calculation

Interest rate is determined by:
- **Base rate**: 8% (standard)
- **Credit score adjustment**:
  - Credit score 750+: -1.5% (discount for excellent credit)
  - Credit score 600-749: 0% (no change)
  - Credit score < 600: Not eligible
- **Loan purpose adjustment**:
  - HOME: -0.5% (lower rate for home loans)
  - AUTO: 0% (standard)
  - PERSONAL: +2% (higher rate)
  - EDUCATION: -1% (favorable rates)

**Example**: Salaried, 750 credit score, PERSONAL loan = 8% - 1.5% + 2% = **8.5%**

### EMI Calculation

```
Monthly Interest Rate (r) = Annual Rate / 12 / 100
Number of Months (n) = Tenure
Principal (P) = Loan Amount

EMI = P × r × (1 + r)^n / ((1 + r)^n - 1)

Total Payable = EMI × n
```

**Example**: 
- Principal: 500,000 INR
- Rate: 10% p.a.
- Tenure: 36 months
- EMI ≈ 16,106.33 INR
- Total Payable ≈ 579,827.88 INR

### Risk Band Calculation

| Credit Score | Risk Band | Characteristics |
|--------------|-----------|-----------------|
| 750–900 | LOW | Excellent credit, likely approval |
| 600–749 | MEDIUM | Good credit, moderate risk |
| 300–599 | HIGH | Poor credit, high risk, likely rejection |

---

## 📁 Project Structure

```
LoanOriginationService/
├── src/
│   ├── main/
│   │   ├── java/com/shubh/loan/origination/
│   │   │   ├── interaction/
│   │   │   │   └── LoanController.java             # REST endpoints
│   │   │   │
│   │   │   ├── service/
│   │   │   │   ├── LoanService.java                # Service interface
│   │   │   │   ├── LoanServiceImpl.java             # Service implementation
│   │   │   │   └── helper/
│   │   │   │       └── LoanServiceHelper.java      # Business logic helper
│   │   │   │
│   │   │   ├── model/
│   │   │   │   ├── Application.java                # JPA entity
│   │   │   │   ├── Applicant.java                  # JPA entity
│   │   │   │   ├── Loan.java                       # JPA entity
│   │   │   │   │
│   │   │   │   ├── dto/
│   │   │   │   │   ├── LoanApplicationRequest.java # Request DTO
│   │   │   │   │   ├── ApplicantDTO.java           # Applicant DTO
│   │   │   │   │   ├── LoanDTO.java                # Loan DTO
│   │   │   │   │   ├── LoanResponse.java           # Response DTO
│   │   │   │   │   └── Offer.java                  # Offer DTO
│   │   │   │   │
│   │   │   │   └── constants/
│   │   │   │       ├── EmploymentType.java         # Enum
│   │   │   │       ├── LoanPurpose.java            # Enum
│   │   │   │       ├── RiskBand.java               # Enum
│   │   │   │       ├── ApplicationStatus.java      # Enum
│   │   │   │       └── RejectionReason.java        # Enum
│   │   │   │
│   │   │   ├── repository/
│   │   │   │   └── ApplicationRepository.java      # Spring Data JPA
│   │   │   │
│   │   │   ├── mapper/
│   │   │   │   └── ApplicationMapper.java          # DTO ↔ Entity mapper
│   │   │   │
│   │   │   ├── validation/
│   │   │   │   ├── ValidAgeTenure.java             # Custom annotation
│   │   │   │   ├── TenureAgeValidator.java         # Validator
│   │   │   │   ├── ValidCreditScore.java           # Custom annotation
│   │   │   │   ├── CreditScoreValidator.java       # Validator
│   │   │   │   ├── ValidTenure.java                # Custom annotation
│   │   │   │   ├── TenureValidator.java            # Validator
│   │   │   │   ├── LoanAmountValidation.java       # Custom annotation
│   │   │   │   ├── LoanAmountValidator.java        # Validator
│   │   │   │   ├── ValidMonthlyIncome.java         # Custom annotation
│   │   │   │   └── ValidMonthlyIncomeValidator.java # Validator
│   │   │   │
│   │   │   └── LoanOriginationServiceApplication.java # Main class
│   │   │
│   │   └── resources/
│   │       ├── application.yaml                    # Configuration
│   │       ├── application-dev.yaml                # Dev configuration
│   │       ├── static/                             # Static resources
│   │       └── templates/                          # Templates
│   │
│   └── test/
│       └── java/com/shubh/loan/origination/
│           └── LoanOriginationServiceApplicationTests.java
│
├── build.gradle.kts                # Gradle build configuration
├── gradle/wrapper/                 # Gradle wrapper files
├── HELP.md                         # Spring Boot help
├── README.md                       # Main documentation
└── settings.gradle.kts             # Gradle settings
```

---

## 🛠️ Technologies & Dependencies

### Core Framework
- **Spring Boot**: 4.0.3
- **Spring Framework**: 7.0.5
- **Java**: JDK 17

### Database & ORM
- **MySQL**: 5.7+ (Database)
- **Hibernate**: 7.2.4 (ORM)
- **Spring Data JPA**: 4.0.3 (Repository abstraction)
- **MySQL Connector/J**: Latest (JDBC driver)

### Validation
- **Jakarta Validation**: 3.0+ (Bean validation)
- **Spring Validation**: Built-in support


### Build Tools
- **Gradle**: 8.x (Kotlin DSL)
- **Lombok**: 1.18.x (Boilerplate reduction)

### Testing
- **JUnit 5**: Jupiter
- **Spring Boot Test**
- **Spring Test**

### Logging
- **SLF4J**: Simple Logging Facade
- **Logback**: Default logging implementation

---

## 📈 Implementation Steps (High-Level)

### Phase 1: Foundation Setup
- ✅ Initialize Spring Boot project
- ✅ Configure MySQL database connection
- ✅ Setup Gradle build configuration

### Phase 2: Entity & Data Models
- ✅ Create JPA entities (Application, Applicant, Loan)
- ✅ Create DTO classes (Request/Response objects)
- ✅ Define enums (EmploymentType, LoanPurpose, etc.)

### Phase 3: Validation Layer
- ✅ Implement custom validation annotations
- ✅ Create validator classes
- ✅ Apply field-level and class-level validations

### Phase 4: Business Logic
- ✅ Implement LoanService interface
- ✅ Implement LoanServiceImpl service class
- ✅ Create LoanServiceHelper for calculations
- ✅ Implement approval decision logic
- ✅ Create interest and EMI calculation methods

### Phase 5: Data Persistence
- ✅ Create ApplicationRepository (Spring Data JPA)
- ✅ Configure database connection
- ✅ Setup entity cascading and relationships

### Phase 6: REST Controller
- ✅ Create LoanController with /apply endpoint
- ✅ Setup request validation
- ✅ Implement response mapping

### Phase 7: Response Formatting
- ✅ Create Offer DTO with detailed loan terms
- ✅ Update LoanResponse with offer object
- ✅ Remove API wrapper for clean JSON response

### Phase 8: Testing & Documentation
- ✅ Create comprehensive API documentation
- ✅ Provide cURL examples
- ✅ Create this README with complete overview

---

## 🧪 Testing the API


### Using cURL
```bash
curl -X POST 'http://localhost:8080/api/v1/loans/apply' \
  -H 'Content-Type: application/json' \
  -d '{
    "applicant": {"age": 30, "creditScore": 750, "monthlyIncome": 100000, "employmentType": "SALARIED"},
    "loan": {"amount": 500000, "tenureMonths": 36, "purpose": "PERSONAL"}
  }'
```

### Using Postman
1. Create new POST request
2. URL: `http://localhost:8080/api/v1/loans/apply`
3. Headers: `Content-Type: application/json`
4. Body (raw JSON): Copy from cURL examples above
5. Send

---

## 📝 Summary

This Loan Origination Service provides a **complete, production-ready API** for automated loan application processing with:
- ✅ Comprehensive validation at multiple levels
- ✅ Intelligent decision-making engine
- ✅ Risk assessment and offer generation
- ✅ Persistent data storage
- ✅ Clean, layered architecture
- ✅ Database persistence with JPA/Hibernate
- ✅ Easy-to-use REST endpoints
- ✅ Comprehensive cURL and Postman examples

The service is ready to integrate with frontend applications, mobile apps, or other backend services for complete loan processing automation.
**Version**: 1.0.0  
**Status**: Production Ready ✅


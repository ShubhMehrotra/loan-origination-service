package com.shubh.loan.origination.validation;

import jakarta.validation.*;
import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.PARAMETER})
@Constraint(validatedBy = TenureAgeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAgeTenure{
    String message() default "Age + tenure must not exceed 65";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
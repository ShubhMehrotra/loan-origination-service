package com.shubh.loan.origination.validation;

import jakarta.validation.*;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TenureValidator.class)
@Documented
public @interface ValidTenure {
    String message() default "Invalid tenure. Tenure must be between 6 and 360 months.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

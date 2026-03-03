package com.shubh.loan.origination.validation;

import jakarta.validation.*;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAge {
    String message() default "Age must be between 21 and 60";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
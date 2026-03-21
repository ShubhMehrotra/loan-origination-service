package com.shubh.loan.origination.model;


import java.time.*;

public record ApiResponse<T>(
            String message,
            T data,
            LocalDateTime timestamp
    ) {}


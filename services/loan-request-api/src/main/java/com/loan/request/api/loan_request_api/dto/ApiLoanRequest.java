package com.loan.request.api.loan_request_api.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ApiLoanRequest {
    String account;
    BigDecimal amount;
}

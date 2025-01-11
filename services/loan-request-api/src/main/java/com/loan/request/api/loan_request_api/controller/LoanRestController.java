package com.loan.request.api.loan_request_api.controller;

import com.loan.request.api.loan_request_api.dto.ApiLoanRequest;
import com.loan.request.api.loan_request_api.service.LoanSender;
import com.loan.request.api.loan_request_api.topology.LoanRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import space.gavinklfong.demo.finance.schema.LoanRequest;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/loans")
public class LoanRestController {

    private final LoanSender loanSender;

    @PostMapping
    public ApiLoanRequest submitLoanRequest(@RequestBody ApiLoanRequest apiLoanRequest) {
        LoanRequest loanRequest = LoanRequest.newBuilder()
                .setAccount(apiLoanRequest.getAccount())
                .setAmount(apiLoanRequest.getAmount())
                .setRequestId(UUID.randomUUID().toString())
                .setTimestamp(Instant.now())
                .build();
        loanSender.send(loanRequest);
        return apiLoanRequest;
    }

}
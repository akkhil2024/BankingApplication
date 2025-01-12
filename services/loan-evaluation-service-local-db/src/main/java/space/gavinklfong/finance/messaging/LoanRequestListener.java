package space.gavinklfong.finance.messaging;

import com.loan.request.api.loan_request_api.topology.Account;
import com.loan.request.api.loan_request_api.topology.LoanRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


import com.loan.request.api.loan_request_api.topology.LoanResponse;
//import com.loan.request.api.loan_request_api.topology.LoanRequest;
import space.gavinklfong.finance.service.LoanEvaluationService;
import space.gavinklfong.finance.service.LoanResultSender;


@Slf4j
@RequiredArgsConstructor
@Component
public class LoanRequestListener {
    private final LoanEvaluationService loanEvaluationService;
    private final LoanResultSender loanResultSender;

   @KafkaListener(id = "loan-request-handler", topics = "loan-requests", batch = "1")
    public void handleLoanRequestEvent(ConsumerRecord<Account, LoanRequest> data)  {
        log.info("Loan request received: key={}, value={}", data.key(), data.value());
        LoanResponse response = loanEvaluationService.evaluate(data.value());
        log.info("Sending loan evaluation result: {}", response);
        loanResultSender.send(response);
    }
}


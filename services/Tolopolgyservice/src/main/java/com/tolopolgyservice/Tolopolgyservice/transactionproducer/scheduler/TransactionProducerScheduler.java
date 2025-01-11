package com.tolopolgyservice.Tolopolgyservice.transactionproducer.scheduler;

import com.tolopolgyservice.Tolopolgyservice.model.Transaction;
import com.tolopolgyservice.Tolopolgyservice.transactionproducer.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.Iterator;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionProducerScheduler {
    private static final String CSV_FILE = "/";
    private static final Iterator<Transaction> TRANSACTIONS = TransactionLoader.loadTransactions(CSV_FILE).iterator();

    private final TransactionService transactionService;

    @Scheduled(fixedRateString = "${transaction-producer.fixed-rate}")
    public void scheduleFixedRateTask() {
        System.out.println(" SCHEDULER STARTED........");
        if (TRANSACTIONS.hasNext()) {
            Transaction transaction = TRANSACTIONS.next();
            transactionService.sendTransaction(transaction);
            log.info("Transaction sent: {}", transaction);
        }
    }
}

package com.loan.request.api.loan_request_api.service;

import com.loan.request.api.loan_request_api.topology.Account;
import com.loan.request.api.loan_request_api.topology.LoanRequest;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;





@Component
public class LoanSender {

    private final String topic;
    private final KafkaProducer<Account, LoanRequest> kafkaProducer;

    public LoanSender(@Value("${loan.input.topic}") String topic, KafkaProducer<Account, LoanRequest> kafkaProducer) {
        this.topic = topic;
        this.kafkaProducer = kafkaProducer;
    }

    @SneakyThrows
    public void send(LoanRequest request) {
        ProducerRecord<Account, LoanRequest> producerRecord = new ProducerRecord<>(topic, buildKey(request), request);
        System.out.println(" SEDNNG MESSAGE TO THE TOPIC :====> " + topic);
        kafkaProducer.send(producerRecord).get();
    }

    private Account buildKey(LoanRequest loanRequest) {
        return Account.newBuilder()
                .setAccount(loanRequest.getAccount())
                .build();
    }
}

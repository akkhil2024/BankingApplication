package com.tolopolgyservice.Tolopolgyservice.transactionproducer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tolopolgyservice.Tolopolgyservice.model.Transaction;
import com.tolopolgyservice.Tolopolgyservice.model.TransactionKey;
import com.tolopolgyservice.Tolopolgyservice.transactionproducer.service.TransactionService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class AppConfig {

    @Bean
    public TransactionService transactionProducer(@Value("${transaction-producer.topic}") String topic,
                                                  KafkaProducer<TransactionKey,  Transaction> kafkaProducer) {
        return new TransactionService(topic, kafkaProducer);
    }

    /*@Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }*/

}

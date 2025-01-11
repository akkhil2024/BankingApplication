
package com.tolopolgyservice.Tolopolgyservice.transactionproducer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tolopolgyservice.Tolopolgyservice.model.Transaction;
import com.tolopolgyservice.Tolopolgyservice.model.TransactionKey;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;


@Slf4j
@RequiredArgsConstructor
public class TransactionService {

    private final String topic;

    private final KafkaProducer<TransactionKey, Transaction> transactionKafkaProducer;

    @SneakyThrows
    public void sendTransaction(Transaction transaction) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        //  ProducerRecord<TransactionKey, Transaction> producerRecord = new ProducerRecord<>(topic, buildKey(transaction), transactionJson);


        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Register module for LocalDateTime
        String transactionJson = mapper.writeValueAsString(transaction);
        ProducerRecord<TransactionKey, Transaction> producerRecord = new ProducerRecord<>(topic, buildKey(transaction),transaction);

        //
        transactionKafkaProducer.send(producerRecord).get();
        System.out.println(" Messagd sent to Topic,,,,,,,, "+ topic+ "at "+ System.currentTimeMillis());
    }

    private TransactionKey buildKey(Transaction transaction) {
        return TransactionKey.builder()
                .fromAccount(transaction.getFromAccount())
                .toAccount(transaction.getToAccount())
                .build();
    }
}

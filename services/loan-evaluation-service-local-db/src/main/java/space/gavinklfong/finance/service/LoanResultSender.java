package space.gavinklfong.finance.service;


import com.loan.request.api.loan_request_api.topology.Account;
import com.loan.request.api.loan_request_api.topology.LoanResponse;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import space.gavinklfong.finance.config.KafkaConfig;


@Component
public class LoanResultSender {

    private  String topic;

    //@Autowired
    private final  KafkaProducer<Account, LoanResponse> kafkaProducer;

    public LoanResultSender(@Value("${loan.output.topic}") String topic, KafkaProducer<Account, LoanResponse> kafkaProducer) {
        this.topic = topic;
        this.kafkaProducer = kafkaProducer;
    }

    @SneakyThrows
    public void send(LoanResponse response) {
        ProducerRecord<Account, LoanResponse> producerRecord = new ProducerRecord<>(topic, buildKey(response), response);
        KafkaProducer<Account,LoanResponse> kafkaProducer
                = new KafkaConfig().customKafkaProducer();
        kafkaProducer.send(producerRecord).get();
    }

    private Account buildKey(LoanResponse loanResponse) {
        return Account.newBuilder()
                .setAccount(loanResponse.getAccount())
                .build();
    }
}

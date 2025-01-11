package com.tolopolgyservice.Tolopolgyservice;

import com.tolopolgyservice.Tolopolgyservice.model.Transaction;
import com.tolopolgyservice.Tolopolgyservice.model.TransactionKey;
import com.tolopolgyservice.Tolopolgyservice.topology.*;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Base64;
import java.util.Properties;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.compare.ComparableUtils.is;

@SpringBootApplication
@Slf4j
public class TolopolgyserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TolopolgyserviceApplication.class, args);
		System.out.println(" Inside Topology Service...");
		Properties properties =new Properties();

		properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		// build topology

		//1. transaction topology
		KafkaStreams kafkaStreams = buildTransactionTopology(properties);
		Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));


		//2.acoountbalanceTopology
		KafkaStreams KafkaStreamsForBalance = buildAccountBalanceTopology(properties);
		Runtime.getRuntime().addShutdownHook(new Thread(KafkaStreamsForBalance::close));


		//3; Loan Evaluation Topology
		KafkaStreams KafkaStreamsForLoanEvaluation = buildLoanEvaluationTopology(properties);
		Runtime.getRuntime().addShutdownHook(new Thread(KafkaStreamsForLoanEvaluation::close));

		System.out.println("All topologies instatioted.......................");
	}

	private static KafkaStreams buildTransactionTopology(Properties properties){
		properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "transaction-filter");
		Topology topology =TransactionFilterTopology.build();
		log.info(" --------------------Transaction filter  topology ----------------");
		log.info("Toloplogy : {}" ,topology.describe());

		KafkaStreams kafkaStreams =new KafkaStreams(topology,properties);
		kafkaStreams.start();

		return kafkaStreams;
	}


	private static KafkaStreams buildAccountBalanceTopology(Properties properties){
		final String STATE_STORE = "account-balance-store";
		properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "account-balance-calculation");
		StreamsBuilder builder = new StreamsBuilder();


		StoreBuilder<KeyValueStore<String,String>> accountBalanceStoreBuilder =
		Stores.keyValueStoreBuilder(
				Stores.persistentKeyValueStore(STATE_STORE),
				Serdes.String(),
				Serdes.String()
		);

	   builder.addStateStore(accountBalanceStoreBuilder);

		KStream<TransactionKey, Transaction> source = builder.stream("transactions",
						Consumed.with(TransactionSerdes.transactionKey(), TransactionSerdes.transaction()))
				.peek((key, value) -> log.info("input - key: {}, value: {}", key, value), Named.as("log-input"));

              source.process(AccountBalanceCalculator::new,Named.as("account-balance-calculator"),STATE_STORE)
					  .flatMapValues(v -> v)
					  .selectKey((key,value) -> buildKey(value.getAccount()))
					  .to("account-balances", Produced.with(
							  TransactionSerdes.accountKey(),TransactionSerdes.accountBalance()
					  ));

		Topology topology = builder.build();

		KafkaStreams kafkaStreams = new KafkaStreams(topology, properties);
		kafkaStreams.start();

		log.info(" --------------------Account Balance topology ----------------");
		log.info("Topology: {}", topology.describe());

		return kafkaStreams;
	}


	private static Account buildKey(String account) {
		return Account.newBuilder()
				.setAccount(account)
				.build();
	}



	private static KafkaStreams buildLoanEvaluationTopology(Properties properties){

		final String SCHEMA_REGISTRY_URL = "http://localhost:8081";


		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "loan-evaluation-topology");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, GenericAvroSerde.class);
		props.put("schema.registry.url", SCHEMA_REGISTRY_URL);

		SchemaRegistryClient schemaRegistryClient = new CachedSchemaRegistryClient(SCHEMA_REGISTRY_URL, 10);
		SerdeFactory serdeFactory = new SerdeFactory(SCHEMA_REGISTRY_URL, schemaRegistryClient);

		Topology topology = loanEvaluationTopologyBuilder(serdeFactory);
		log.info("Topology: {}", topology.describe());

		KafkaStreams kafkaStreams = new KafkaStreams(topology, props);
		kafkaStreams.start();

		//Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
		return kafkaStreams;
	}

	private static Topology loanEvaluationTopologyBuilder(SerdeFactory serdeFactory) {

		Serde<Account> accountKeySerde = serdeFactory.getSerde(true);
		Serde<AccountBalance> accountBalanceSerde = serdeFactory.getSerde(false);
		Serde<LoanRequest> loanRequestSerde = serdeFactory.getSerde(false);
		Serde<LoanResponse> loanResponseSerde = serdeFactory.getSerde(false);


		// consume request from Customer who is a Loan Seeker
		StreamsBuilder builder =new StreamsBuilder();

		KStream<Account, LoanRequest> loanRequests =
                   builder.stream("loan-requests",Consumed.with(
accountKeySerde,loanRequestSerde
				   )).peek(
						   (key,value) ->log.info("input - key: {}, value: {}", key, value), Named.as("loan-request-input"));

		System.out.println(" About to save to KTable.**************");
		// Materializes the Incoming record to Database Table
		KTable<Account,AccountBalance>accountBalanceTable =
										builder.stream("account-balances",
												Consumed.with(accountKeySerde,accountBalanceSerde))
												.toTable(Materialized.<Account,AccountBalance,KeyValueStore<Bytes,byte[]>>as("account-balances-table")
												.withKeySerde(accountKeySerde)
														.withValueSerde(accountBalanceSerde));

		System.out.println(" Data saved to Database.....**************");
		loanRequests.leftJoin(
               accountBalanceTable,TolopolgyserviceApplication::evaluate)
				.peek((key, value) -> log.info("output - key: {}, value: {}", key, value), Named.as("log-output"))
				.to("loan-evaluation-results",
						Produced.with(accountKeySerde, loanResponseSerde));

		Topology topology = builder.build();
		log.info(" --------------------Loan Evaluation topology ----------------");
		log.info("Topology: {}", topology.describe());
		return  topology;


	}


	private static LoanResponse evaluate(LoanRequest loanRequest, AccountBalance accountBalance) {
		EvaluationResult result = nonNull(accountBalance)?
				evaluate(loanRequest, accountBalance.getAmount()):
				EvaluationResult.REJECTED;

		return LoanResponse.newBuilder()
				.setRequestId(loanRequest.getRequestId())
				.setAccount(loanRequest.getAccount())
				.setAmount(loanRequest.getAmount())
				.setResult(result)
				.setTimestamp(Instant.now())
				.build();
	}

	private static EvaluationResult evaluate(LoanRequest loanRequest, BigDecimal balance) {
		if (is(balance)
				.greaterThanOrEqualTo(loanRequest.getAmount().multiply(BigDecimal.valueOf(3)))) {
			return EvaluationResult.APPROVED;
		} else if (is(balance)
				.greaterThanOrEqualTo(loanRequest.getAmount().multiply(BigDecimal.valueOf(2)))) {
			return EvaluationResult.REVIEW_NEEDED;
		} else {
			return EvaluationResult.REJECTED;
		}
	}
}

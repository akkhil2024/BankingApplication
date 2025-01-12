package com.tolopolgyservice.Tolopolgyservice;

import com.tolopolgyservice.Tolopolgyservice.model.TransactionType;
import com.tolopolgyservice.Tolopolgyservice.topology.TransactionSerdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;

/*
  Filter topology
 */
public class TransactionFilterTopology {

    public static Topology build(){
        StreamsBuilder streamsBuilder
                 =new StreamsBuilder();

        streamsBuilder
                .stream("transactions",
                        Consumed.with(
                                TransactionSerdes.transactionKey(),TransactionSerdes.transaction())
                        ).filter((key,value) -> value.getType().equals(TransactionType.TRANSFER))
                .to("transfer-transactions");

        return  streamsBuilder.build();

    }
}

/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.loan.request.api.loan_request_api.topology;

@org.apache.avro.specific.AvroGenerated
public interface AppProtocol {
  public static final org.apache.avro.Protocol PROTOCOL = org.apache.avro.Protocol.parse("{\"protocol\":\"AppProtocol\",\"namespace\":\"com.loan.request.api.loan_request_api.topology\",\"types\":[{\"type\":\"record\",\"name\":\"LoanRequest\",\"fields\":[{\"name\":\"requestId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"account\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"amount\",\"type\":{\"type\":\"bytes\",\"logicalType\":\"decimal\",\"precision\":14,\"scale\":2}},{\"name\":\"timestamp\",\"type\":{\"type\":\"long\",\"logicalType\":\"timestamp-millis\"}}]}],\"messages\":{}}");

  @org.apache.avro.specific.AvroGenerated
  public interface Callback extends AppProtocol {
    public static final org.apache.avro.Protocol PROTOCOL = com.loan.request.api.loan_request_api.topology.AppProtocol.PROTOCOL;
  }
}
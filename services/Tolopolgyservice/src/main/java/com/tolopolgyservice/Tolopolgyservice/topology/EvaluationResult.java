/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.tolopolgyservice.Tolopolgyservice.topology;
@org.apache.avro.specific.AvroGenerated
public enum EvaluationResult implements org.apache.avro.generic.GenericEnumSymbol<EvaluationResult> {
  APPROVED, REJECTED, REVIEW_NEEDED  ;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"EvaluationResult\",\"namespace\":\"com.tolopolgyservice.Tolopolgyservice.topology\",\"symbols\":[\"APPROVED\",\"REJECTED\",\"REVIEW_NEEDED\"]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
}
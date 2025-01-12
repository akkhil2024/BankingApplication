/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package space.gavinklfong.demo.finance.schema;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class LoanRequest extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 3502079365357179075L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"LoanRequest\",\"namespace\":\"space.gavinklfong.demo.finance.schema\",\"fields\":[{\"name\":\"requestId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"account\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"amount\",\"type\":{\"type\":\"bytes\",\"logicalType\":\"decimal\",\"precision\":14,\"scale\":2}},{\"name\":\"timestamp\",\"type\":{\"type\":\"long\",\"logicalType\":\"timestamp-millis\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();
  static {
    MODEL$.addLogicalTypeConversion(new org.apache.avro.data.TimeConversions.TimestampMillisConversion());
    MODEL$.addLogicalTypeConversion(new org.apache.avro.Conversions.DecimalConversion());
  }

  private static final BinaryMessageEncoder<LoanRequest> ENCODER =
      new BinaryMessageEncoder<LoanRequest>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<LoanRequest> DECODER =
      new BinaryMessageDecoder<LoanRequest>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<LoanRequest> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<LoanRequest> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<LoanRequest> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<LoanRequest>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this LoanRequest to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a LoanRequest from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a LoanRequest instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static LoanRequest fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.String requestId;
  private java.lang.String account;
  private java.math.BigDecimal amount;
  private java.time.Instant timestamp;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public LoanRequest() {}

  /**
   * All-args constructor.
   * @param requestId The new value for requestId
   * @param account The new value for account
   * @param amount The new value for amount
   * @param timestamp The new value for timestamp
   */
  public LoanRequest(java.lang.String requestId, java.lang.String account, java.math.BigDecimal amount, java.time.Instant timestamp) {
    this.requestId = requestId;
    this.account = account;
    this.amount = amount;
    this.timestamp = timestamp.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return requestId;
    case 1: return account;
    case 2: return amount;
    case 3: return timestamp;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  private static final org.apache.avro.Conversion<?>[] conversions =
      new org.apache.avro.Conversion<?>[] {
      null,
      null,
      new org.apache.avro.Conversions.DecimalConversion(),
      new org.apache.avro.data.TimeConversions.TimestampMillisConversion(),
      null
  };

  @Override
  public org.apache.avro.Conversion<?> getConversion(int field) {
    return conversions[field];
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: requestId = value$ != null ? value$.toString() : null; break;
    case 1: account = value$ != null ? value$.toString() : null; break;
    case 2: amount = (java.math.BigDecimal)value$; break;
    case 3: timestamp = (java.time.Instant)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'requestId' field.
   * @return The value of the 'requestId' field.
   */
  public java.lang.String getRequestId() {
    return requestId;
  }


  /**
   * Sets the value of the 'requestId' field.
   * @param value the value to set.
   */
  public void setRequestId(java.lang.String value) {
    this.requestId = value;
  }

  /**
   * Gets the value of the 'account' field.
   * @return The value of the 'account' field.
   */
  public java.lang.String getAccount() {
    return account;
  }


  /**
   * Sets the value of the 'account' field.
   * @param value the value to set.
   */
  public void setAccount(java.lang.String value) {
    this.account = value;
  }

  /**
   * Gets the value of the 'amount' field.
   * @return The value of the 'amount' field.
   */
  public java.math.BigDecimal getAmount() {
    return amount;
  }


  /**
   * Sets the value of the 'amount' field.
   * @param value the value to set.
   */
  public void setAmount(java.math.BigDecimal value) {
    this.amount = value;
  }

  /**
   * Gets the value of the 'timestamp' field.
   * @return The value of the 'timestamp' field.
   */
  public java.time.Instant getTimestamp() {
    return timestamp;
  }


  /**
   * Sets the value of the 'timestamp' field.
   * @param value the value to set.
   */
  public void setTimestamp(java.time.Instant value) {
    this.timestamp = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  /**
   * Creates a new LoanRequest RecordBuilder.
   * @return A new LoanRequest RecordBuilder
   */
  public static space.gavinklfong.demo.finance.schema.LoanRequest.Builder newBuilder() {
    return new space.gavinklfong.demo.finance.schema.LoanRequest.Builder();
  }

  /**
   * Creates a new LoanRequest RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new LoanRequest RecordBuilder
   */
  public static space.gavinklfong.demo.finance.schema.LoanRequest.Builder newBuilder(space.gavinklfong.demo.finance.schema.LoanRequest.Builder other) {
    if (other == null) {
      return new space.gavinklfong.demo.finance.schema.LoanRequest.Builder();
    } else {
      return new space.gavinklfong.demo.finance.schema.LoanRequest.Builder(other);
    }
  }

  /**
   * Creates a new LoanRequest RecordBuilder by copying an existing LoanRequest instance.
   * @param other The existing instance to copy.
   * @return A new LoanRequest RecordBuilder
   */
  public static space.gavinklfong.demo.finance.schema.LoanRequest.Builder newBuilder(space.gavinklfong.demo.finance.schema.LoanRequest other) {
    if (other == null) {
      return new space.gavinklfong.demo.finance.schema.LoanRequest.Builder();
    } else {
      return new space.gavinklfong.demo.finance.schema.LoanRequest.Builder(other);
    }
  }

  /**
   * RecordBuilder for LoanRequest instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<LoanRequest>
    implements org.apache.avro.data.RecordBuilder<LoanRequest> {

    private java.lang.String requestId;
    private java.lang.String account;
    private java.math.BigDecimal amount;
    private java.time.Instant timestamp;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(space.gavinklfong.demo.finance.schema.LoanRequest.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.requestId)) {
        this.requestId = data().deepCopy(fields()[0].schema(), other.requestId);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.account)) {
        this.account = data().deepCopy(fields()[1].schema(), other.account);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.amount)) {
        this.amount = data().deepCopy(fields()[2].schema(), other.amount);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[3].schema(), other.timestamp);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
    }

    /**
     * Creates a Builder by copying an existing LoanRequest instance
     * @param other The existing instance to copy.
     */
    private Builder(space.gavinklfong.demo.finance.schema.LoanRequest other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.requestId)) {
        this.requestId = data().deepCopy(fields()[0].schema(), other.requestId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.account)) {
        this.account = data().deepCopy(fields()[1].schema(), other.account);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.amount)) {
        this.amount = data().deepCopy(fields()[2].schema(), other.amount);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[3].schema(), other.timestamp);
        fieldSetFlags()[3] = true;
      }
    }

    /**
      * Gets the value of the 'requestId' field.
      * @return The value.
      */
    public java.lang.String getRequestId() {
      return requestId;
    }


    /**
      * Sets the value of the 'requestId' field.
      * @param value The value of 'requestId'.
      * @return This builder.
      */
    public space.gavinklfong.demo.finance.schema.LoanRequest.Builder setRequestId(java.lang.String value) {
      validate(fields()[0], value);
      this.requestId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'requestId' field has been set.
      * @return True if the 'requestId' field has been set, false otherwise.
      */
    public boolean hasRequestId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'requestId' field.
      * @return This builder.
      */
    public space.gavinklfong.demo.finance.schema.LoanRequest.Builder clearRequestId() {
      requestId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'account' field.
      * @return The value.
      */
    public java.lang.String getAccount() {
      return account;
    }


    /**
      * Sets the value of the 'account' field.
      * @param value The value of 'account'.
      * @return This builder.
      */
    public space.gavinklfong.demo.finance.schema.LoanRequest.Builder setAccount(java.lang.String value) {
      validate(fields()[1], value);
      this.account = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'account' field has been set.
      * @return True if the 'account' field has been set, false otherwise.
      */
    public boolean hasAccount() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'account' field.
      * @return This builder.
      */
    public space.gavinklfong.demo.finance.schema.LoanRequest.Builder clearAccount() {
      account = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'amount' field.
      * @return The value.
      */
    public java.math.BigDecimal getAmount() {
      return amount;
    }


    /**
      * Sets the value of the 'amount' field.
      * @param value The value of 'amount'.
      * @return This builder.
      */
    public space.gavinklfong.demo.finance.schema.LoanRequest.Builder setAmount(java.math.BigDecimal value) {
      validate(fields()[2], value);
      this.amount = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'amount' field has been set.
      * @return True if the 'amount' field has been set, false otherwise.
      */
    public boolean hasAmount() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'amount' field.
      * @return This builder.
      */
    public space.gavinklfong.demo.finance.schema.LoanRequest.Builder clearAmount() {
      amount = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'timestamp' field.
      * @return The value.
      */
    public java.time.Instant getTimestamp() {
      return timestamp;
    }


    /**
      * Sets the value of the 'timestamp' field.
      * @param value The value of 'timestamp'.
      * @return This builder.
      */
    public space.gavinklfong.demo.finance.schema.LoanRequest.Builder setTimestamp(java.time.Instant value) {
      validate(fields()[3], value);
      this.timestamp = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'timestamp' field has been set.
      * @return True if the 'timestamp' field has been set, false otherwise.
      */
    public boolean hasTimestamp() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'timestamp' field.
      * @return This builder.
      */
    public space.gavinklfong.demo.finance.schema.LoanRequest.Builder clearTimestamp() {
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public LoanRequest build() {
      try {
        LoanRequest record = new LoanRequest();
        record.requestId = fieldSetFlags()[0] ? this.requestId : (java.lang.String) defaultValue(fields()[0]);
        record.account = fieldSetFlags()[1] ? this.account : (java.lang.String) defaultValue(fields()[1]);
        record.amount = fieldSetFlags()[2] ? this.amount : (java.math.BigDecimal) defaultValue(fields()[2]);
        record.timestamp = fieldSetFlags()[3] ? this.timestamp : (java.time.Instant) defaultValue(fields()[3]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<LoanRequest>
    WRITER$ = (org.apache.avro.io.DatumWriter<LoanRequest>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<LoanRequest>
    READER$ = (org.apache.avro.io.DatumReader<LoanRequest>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}











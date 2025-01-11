/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.tolopolgyservice.Tolopolgyservice.topology;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class Account extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 3448836173800144479L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Account\",\"namespace\":\"com.tolopolgyservice.Tolopolgyservice.topology\",\"fields\":[{\"name\":\"account\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Account> ENCODER =
      new BinaryMessageEncoder<Account>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Account> DECODER =
      new BinaryMessageDecoder<Account>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<Account> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<Account> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<Account> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Account>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this Account to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a Account from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a Account instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static Account fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.String account;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Account() {}

  /**
   * All-args constructor.
   * @param account The new value for account
   */
  public Account(java.lang.String account) {
    this.account = account;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return account;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: account = value$ != null ? value$.toString() : null; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
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
   * Creates a new Account RecordBuilder.
   * @return A new Account RecordBuilder
   */
  public static com.tolopolgyservice.Tolopolgyservice.topology.Account.Builder newBuilder() {
    return new com.tolopolgyservice.Tolopolgyservice.topology.Account.Builder();
  }

  /**
   * Creates a new Account RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Account RecordBuilder
   */
  public static com.tolopolgyservice.Tolopolgyservice.topology.Account.Builder newBuilder(com.tolopolgyservice.Tolopolgyservice.topology.Account.Builder other) {
    if (other == null) {
      return new com.tolopolgyservice.Tolopolgyservice.topology.Account.Builder();
    } else {
      return new com.tolopolgyservice.Tolopolgyservice.topology.Account.Builder(other);
    }
  }

  /**
   * Creates a new Account RecordBuilder by copying an existing Account instance.
   * @param other The existing instance to copy.
   * @return A new Account RecordBuilder
   */
  public static com.tolopolgyservice.Tolopolgyservice.topology.Account.Builder newBuilder(com.tolopolgyservice.Tolopolgyservice.topology.Account other) {
    if (other == null) {
      return new com.tolopolgyservice.Tolopolgyservice.topology.Account.Builder();
    } else {
      return new com.tolopolgyservice.Tolopolgyservice.topology.Account.Builder(other);
    }
  }

  /**
   * RecordBuilder for Account instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Account>
    implements org.apache.avro.data.RecordBuilder<Account> {

    private java.lang.String account;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.tolopolgyservice.Tolopolgyservice.topology.Account.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.account)) {
        this.account = data().deepCopy(fields()[0].schema(), other.account);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
    }

    /**
     * Creates a Builder by copying an existing Account instance
     * @param other The existing instance to copy.
     */
    private Builder(com.tolopolgyservice.Tolopolgyservice.topology.Account other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.account)) {
        this.account = data().deepCopy(fields()[0].schema(), other.account);
        fieldSetFlags()[0] = true;
      }
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
    public com.tolopolgyservice.Tolopolgyservice.topology.Account.Builder setAccount(java.lang.String value) {
      validate(fields()[0], value);
      this.account = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'account' field has been set.
      * @return True if the 'account' field has been set, false otherwise.
      */
    public boolean hasAccount() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'account' field.
      * @return This builder.
      */
    public com.tolopolgyservice.Tolopolgyservice.topology.Account.Builder clearAccount() {
      account = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Account build() {
      try {
        Account record = new Account();
        record.account = fieldSetFlags()[0] ? this.account : (java.lang.String) defaultValue(fields()[0]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Account>
    WRITER$ = (org.apache.avro.io.DatumWriter<Account>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Account>
    READER$ = (org.apache.avro.io.DatumReader<Account>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.account);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.account = in.readString();

    } else {
      for (int i = 0; i < 1; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.account = in.readString();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}











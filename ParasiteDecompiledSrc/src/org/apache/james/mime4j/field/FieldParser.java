package org.apache.james.mime4j.field;

import org.apache.james.mime4j.util.ByteSequence;

public abstract interface FieldParser
{
  public abstract ParsedField parse(String paramString1, String paramString2, ByteSequence paramByteSequence);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.FieldParser
 * JD-Core Version:    0.7.0.1
 */
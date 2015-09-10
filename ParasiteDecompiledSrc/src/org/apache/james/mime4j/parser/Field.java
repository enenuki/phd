package org.apache.james.mime4j.parser;

import org.apache.james.mime4j.util.ByteSequence;

public abstract interface Field
{
  public abstract String getName();
  
  public abstract String getBody();
  
  public abstract ByteSequence getRaw();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.parser.Field
 * JD-Core Version:    0.7.0.1
 */
package org.apache.james.mime4j.descriptor;

import org.apache.james.mime4j.parser.Field;

public abstract interface MutableBodyDescriptor
  extends BodyDescriptor
{
  public abstract void addField(Field paramField);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.descriptor.MutableBodyDescriptor
 * JD-Core Version:    0.7.0.1
 */
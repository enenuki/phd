package org.apache.james.mime4j.field;

import org.apache.james.mime4j.parser.Field;

public abstract interface ParsedField
  extends Field
{
  public abstract boolean isValidField();
  
  public abstract ParseException getParseException();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.ParsedField
 * JD-Core Version:    0.7.0.1
 */
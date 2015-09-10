package org.apache.james.mime4j.message;

import java.io.IOException;
import java.io.Reader;

public abstract class TextBody
  extends SingleBody
{
  public abstract String getMimeCharset();
  
  public abstract Reader getReader()
    throws IOException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.message.TextBody
 * JD-Core Version:    0.7.0.1
 */
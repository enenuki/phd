package org.apache.james.mime4j.message;

import java.io.IOException;
import java.io.InputStream;

public abstract class BinaryBody
  extends SingleBody
{
  public abstract InputStream getInputStream()
    throws IOException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.message.BinaryBody
 * JD-Core Version:    0.7.0.1
 */
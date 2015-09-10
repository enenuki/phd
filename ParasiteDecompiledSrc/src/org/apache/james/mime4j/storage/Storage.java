package org.apache.james.mime4j.storage;

import java.io.IOException;
import java.io.InputStream;

public abstract interface Storage
{
  public abstract InputStream getInputStream()
    throws IOException;
  
  public abstract void delete();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.storage.Storage
 * JD-Core Version:    0.7.0.1
 */
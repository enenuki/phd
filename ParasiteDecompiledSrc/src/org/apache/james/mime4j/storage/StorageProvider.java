package org.apache.james.mime4j.storage;

import java.io.IOException;
import java.io.InputStream;

public abstract interface StorageProvider
{
  public abstract Storage store(InputStream paramInputStream)
    throws IOException;
  
  public abstract StorageOutputStream createStorageOutputStream()
    throws IOException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.storage.StorageProvider
 * JD-Core Version:    0.7.0.1
 */
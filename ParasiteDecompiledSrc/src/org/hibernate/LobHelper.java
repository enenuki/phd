package org.hibernate;

import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;

public abstract interface LobHelper
{
  public abstract Blob createBlob(byte[] paramArrayOfByte);
  
  public abstract Blob createBlob(InputStream paramInputStream, long paramLong);
  
  public abstract Clob createClob(String paramString);
  
  public abstract Clob createClob(Reader paramReader, long paramLong);
  
  public abstract NClob createNClob(String paramString);
  
  public abstract NClob createNClob(Reader paramReader, long paramLong);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.LobHelper
 * JD-Core Version:    0.7.0.1
 */
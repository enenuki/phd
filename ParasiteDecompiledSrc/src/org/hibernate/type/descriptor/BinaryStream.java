package org.hibernate.type.descriptor;

import java.io.InputStream;

public abstract interface BinaryStream
{
  public abstract InputStream getInputStream();
  
  public abstract byte[] getBytes();
  
  public abstract int getLength();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.BinaryStream
 * JD-Core Version:    0.7.0.1
 */
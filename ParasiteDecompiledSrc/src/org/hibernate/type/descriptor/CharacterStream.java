package org.hibernate.type.descriptor;

import java.io.Reader;

public abstract interface CharacterStream
{
  public abstract Reader getReader();
  
  public abstract int getLength();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.CharacterStream
 * JD-Core Version:    0.7.0.1
 */
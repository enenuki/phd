package org.hibernate.internal.util.xml;

import java.io.Serializable;

public abstract interface Origin
  extends Serializable
{
  public abstract String getType();
  
  public abstract String getName();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.xml.Origin
 * JD-Core Version:    0.7.0.1
 */
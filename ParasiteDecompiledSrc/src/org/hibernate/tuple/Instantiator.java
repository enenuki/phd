package org.hibernate.tuple;

import java.io.Serializable;

public abstract interface Instantiator
  extends Serializable
{
  public abstract Object instantiate(Serializable paramSerializable);
  
  public abstract Object instantiate();
  
  public abstract boolean isInstance(Object paramObject);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.Instantiator
 * JD-Core Version:    0.7.0.1
 */
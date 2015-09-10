package org.hibernate.proxy;

import java.io.Serializable;

public abstract interface HibernateProxy
  extends Serializable
{
  public abstract Object writeReplace();
  
  public abstract LazyInitializer getHibernateLazyInitializer();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.proxy.HibernateProxy
 * JD-Core Version:    0.7.0.1
 */
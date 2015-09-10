package org.hibernate.event.spi;

import java.io.Serializable;
import org.hibernate.HibernateException;

public abstract interface EvictEventListener
  extends Serializable
{
  public abstract void onEvict(EvictEvent paramEvictEvent)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.EvictEventListener
 * JD-Core Version:    0.7.0.1
 */
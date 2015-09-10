package org.hibernate.event.spi;

import java.io.Serializable;
import java.util.Map;
import org.hibernate.HibernateException;

public abstract interface PersistEventListener
  extends Serializable
{
  public abstract void onPersist(PersistEvent paramPersistEvent)
    throws HibernateException;
  
  public abstract void onPersist(PersistEvent paramPersistEvent, Map paramMap)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PersistEventListener
 * JD-Core Version:    0.7.0.1
 */
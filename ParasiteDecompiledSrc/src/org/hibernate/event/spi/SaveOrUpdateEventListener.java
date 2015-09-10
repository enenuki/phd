package org.hibernate.event.spi;

import java.io.Serializable;
import org.hibernate.HibernateException;

public abstract interface SaveOrUpdateEventListener
  extends Serializable
{
  public abstract void onSaveOrUpdate(SaveOrUpdateEvent paramSaveOrUpdateEvent)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.SaveOrUpdateEventListener
 * JD-Core Version:    0.7.0.1
 */
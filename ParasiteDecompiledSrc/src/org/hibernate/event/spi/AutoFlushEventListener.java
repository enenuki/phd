package org.hibernate.event.spi;

import java.io.Serializable;
import org.hibernate.HibernateException;

public abstract interface AutoFlushEventListener
  extends Serializable
{
  public abstract void onAutoFlush(AutoFlushEvent paramAutoFlushEvent)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.AutoFlushEventListener
 * JD-Core Version:    0.7.0.1
 */
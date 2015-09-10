package org.hibernate.event.spi;

import java.io.Serializable;
import org.hibernate.HibernateException;

public abstract interface FlushEventListener
  extends Serializable
{
  public abstract void onFlush(FlushEvent paramFlushEvent)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.FlushEventListener
 * JD-Core Version:    0.7.0.1
 */
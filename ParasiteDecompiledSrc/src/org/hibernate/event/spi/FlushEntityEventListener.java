package org.hibernate.event.spi;

import java.io.Serializable;
import org.hibernate.HibernateException;

public abstract interface FlushEntityEventListener
  extends Serializable
{
  public abstract void onFlushEntity(FlushEntityEvent paramFlushEntityEvent)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.FlushEntityEventListener
 * JD-Core Version:    0.7.0.1
 */
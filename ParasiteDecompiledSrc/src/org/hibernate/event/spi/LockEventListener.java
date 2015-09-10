package org.hibernate.event.spi;

import java.io.Serializable;
import org.hibernate.HibernateException;

public abstract interface LockEventListener
  extends Serializable
{
  public abstract void onLock(LockEvent paramLockEvent)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.LockEventListener
 * JD-Core Version:    0.7.0.1
 */
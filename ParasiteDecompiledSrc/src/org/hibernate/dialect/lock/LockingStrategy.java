package org.hibernate.dialect.lock;

import java.io.Serializable;
import org.hibernate.StaleObjectStateException;
import org.hibernate.engine.spi.SessionImplementor;

public abstract interface LockingStrategy
{
  public abstract void lock(Serializable paramSerializable, Object paramObject1, Object paramObject2, int paramInt, SessionImplementor paramSessionImplementor)
    throws StaleObjectStateException, LockingStrategyException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.lock.LockingStrategy
 * JD-Core Version:    0.7.0.1
 */
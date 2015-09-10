package org.hibernate;

import javax.transaction.Synchronization;
import org.hibernate.engine.transaction.spi.LocalStatus;

public abstract interface Transaction
{
  public abstract boolean isInitiator();
  
  public abstract void begin();
  
  public abstract void commit();
  
  public abstract void rollback();
  
  public abstract LocalStatus getLocalStatus();
  
  public abstract boolean isActive();
  
  public abstract boolean isParticipating();
  
  public abstract boolean wasCommitted();
  
  public abstract boolean wasRolledBack();
  
  public abstract void registerSynchronization(Synchronization paramSynchronization)
    throws HibernateException;
  
  public abstract void setTimeout(int paramInt);
  
  public abstract int getTimeout();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.Transaction
 * JD-Core Version:    0.7.0.1
 */
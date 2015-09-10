package org.hibernate.engine.transaction.spi;

import java.io.Serializable;
import javax.transaction.Synchronization;

public abstract interface SynchronizationRegistry
  extends Serializable
{
  public abstract void registerSynchronization(Synchronization paramSynchronization);
  
  public abstract void notifySynchronizationsBeforeTransactionCompletion();
  
  public abstract void notifySynchronizationsAfterTransactionCompletion(int paramInt);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.spi.SynchronizationRegistry
 * JD-Core Version:    0.7.0.1
 */
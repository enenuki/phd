package org.hibernate.engine.transaction.synchronization.spi;

import java.io.Serializable;
import org.hibernate.engine.transaction.spi.TransactionCoordinator;

public abstract interface ManagedFlushChecker
  extends Serializable
{
  public abstract boolean shouldDoManagedFlush(TransactionCoordinator paramTransactionCoordinator, int paramInt);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.synchronization.spi.ManagedFlushChecker
 * JD-Core Version:    0.7.0.1
 */
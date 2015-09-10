package org.hibernate.engine.transaction.synchronization.spi;

import org.hibernate.engine.transaction.spi.TransactionCoordinator;

public abstract interface AfterCompletionAction
{
  public abstract void doAction(TransactionCoordinator paramTransactionCoordinator, int paramInt);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.synchronization.spi.AfterCompletionAction
 * JD-Core Version:    0.7.0.1
 */
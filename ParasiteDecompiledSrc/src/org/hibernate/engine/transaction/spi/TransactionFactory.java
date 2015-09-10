package org.hibernate.engine.transaction.spi;

import org.hibernate.ConnectionReleaseMode;
import org.hibernate.service.Service;

public abstract interface TransactionFactory<T extends TransactionImplementor>
  extends Service
{
  public abstract T createTransaction(TransactionCoordinator paramTransactionCoordinator);
  
  public abstract boolean canBeDriver();
  
  public abstract boolean compatibleWithJtaSynchronization();
  
  public abstract boolean isJoinableJtaTransaction(TransactionCoordinator paramTransactionCoordinator, T paramT);
  
  public abstract ConnectionReleaseMode getDefaultReleaseMode();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.spi.TransactionFactory
 * JD-Core Version:    0.7.0.1
 */
package org.hibernate.engine.transaction.spi;

public abstract interface TransactionObserver
{
  public abstract void afterBegin(TransactionImplementor paramTransactionImplementor);
  
  public abstract void beforeCompletion(TransactionImplementor paramTransactionImplementor);
  
  public abstract void afterCompletion(boolean paramBoolean, TransactionImplementor paramTransactionImplementor);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.spi.TransactionObserver
 * JD-Core Version:    0.7.0.1
 */
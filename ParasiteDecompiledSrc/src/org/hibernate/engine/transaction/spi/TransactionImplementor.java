package org.hibernate.engine.transaction.spi;

import org.hibernate.Transaction;

public abstract interface TransactionImplementor
  extends Transaction
{
  public abstract IsolationDelegate createIsolationDelegate();
  
  public abstract JoinStatus getJoinStatus();
  
  public abstract void markForJoin();
  
  public abstract void join();
  
  public abstract void resetJoinStatus();
  
  public abstract void markRollbackOnly();
  
  public abstract void invalidate();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.spi.TransactionImplementor
 * JD-Core Version:    0.7.0.1
 */
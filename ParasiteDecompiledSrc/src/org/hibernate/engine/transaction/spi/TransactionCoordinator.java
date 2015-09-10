package org.hibernate.engine.transaction.spi;

import java.io.Serializable;
import java.sql.Connection;
import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
import org.hibernate.engine.transaction.synchronization.spi.SynchronizationCallbackCoordinator;

public abstract interface TransactionCoordinator
  extends Serializable
{
  public abstract TransactionContext getTransactionContext();
  
  public abstract JdbcCoordinator getJdbcCoordinator();
  
  public abstract TransactionImplementor getTransaction();
  
  public abstract SynchronizationRegistry getSynchronizationRegistry();
  
  public abstract void addObserver(TransactionObserver paramTransactionObserver);
  
  public abstract boolean isTransactionJoinable();
  
  public abstract boolean isTransactionJoined();
  
  public abstract void resetJoinStatus();
  
  public abstract boolean isTransactionInProgress();
  
  public abstract void pulse();
  
  public abstract Connection close();
  
  public abstract void afterNonTransactionalQuery(boolean paramBoolean);
  
  public abstract void setRollbackOnly();
  
  public abstract SynchronizationCallbackCoordinator getSynchronizationCallbackCoordinator();
  
  public abstract boolean isSynchronizationRegistered();
  
  public abstract boolean takeOwnership();
  
  public abstract void afterTransaction(TransactionImplementor paramTransactionImplementor, int paramInt);
  
  public abstract void sendAfterTransactionBeginNotifications(TransactionImplementor paramTransactionImplementor);
  
  public abstract void sendBeforeTransactionCompletionNotifications(TransactionImplementor paramTransactionImplementor);
  
  public abstract void sendAfterTransactionCompletionNotifications(TransactionImplementor paramTransactionImplementor, int paramInt);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.spi.TransactionCoordinator
 * JD-Core Version:    0.7.0.1
 */
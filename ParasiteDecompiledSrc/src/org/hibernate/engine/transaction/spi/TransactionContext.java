package org.hibernate.engine.transaction.spi;

import java.io.Serializable;
import org.hibernate.ConnectionReleaseMode;
import org.hibernate.engine.jdbc.spi.JdbcConnectionAccess;

public abstract interface TransactionContext
  extends Serializable
{
  public abstract TransactionEnvironment getTransactionEnvironment();
  
  public abstract ConnectionReleaseMode getConnectionReleaseMode();
  
  public abstract boolean shouldAutoJoinTransaction();
  
  public abstract boolean isAutoCloseSessionEnabled();
  
  public abstract boolean isClosed();
  
  public abstract boolean isFlushModeNever();
  
  public abstract boolean isFlushBeforeCompletionEnabled();
  
  public abstract void managedFlush();
  
  public abstract boolean shouldAutoClose();
  
  public abstract void managedClose();
  
  public abstract void afterTransactionBegin(TransactionImplementor paramTransactionImplementor);
  
  public abstract void beforeTransactionCompletion(TransactionImplementor paramTransactionImplementor);
  
  public abstract void afterTransactionCompletion(TransactionImplementor paramTransactionImplementor, boolean paramBoolean);
  
  public abstract String onPrepareStatement(String paramString);
  
  public abstract JdbcConnectionAccess getJdbcConnectionAccess();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.spi.TransactionContext
 * JD-Core Version:    0.7.0.1
 */
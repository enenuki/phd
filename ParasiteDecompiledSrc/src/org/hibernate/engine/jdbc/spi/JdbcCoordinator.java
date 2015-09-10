package org.hibernate.engine.jdbc.spi;

import java.io.Serializable;
import java.sql.Connection;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.hibernate.engine.jdbc.batch.spi.BatchKey;
import org.hibernate.engine.transaction.spi.TransactionCoordinator;
import org.hibernate.jdbc.WorkExecutorVisitable;

public abstract interface JdbcCoordinator
  extends Serializable
{
  public abstract TransactionCoordinator getTransactionCoordinator();
  
  public abstract LogicalConnectionImplementor getLogicalConnection();
  
  public abstract Batch getBatch(BatchKey paramBatchKey);
  
  public abstract void executeBatch();
  
  public abstract void abortBatch();
  
  public abstract StatementPreparer getStatementPreparer();
  
  public abstract void flushBeginning();
  
  public abstract void flushEnding();
  
  public abstract Connection close();
  
  public abstract void afterTransaction();
  
  public abstract <T> T coordinateWork(WorkExecutorVisitable<T> paramWorkExecutorVisitable);
  
  public abstract void cancelLastQuery();
  
  public abstract void setTransactionTimeOut(int paramInt);
  
  public abstract int determineRemainingTransactionTimeOutPeriod();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.spi.JdbcCoordinator
 * JD-Core Version:    0.7.0.1
 */
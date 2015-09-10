package org.hibernate.service.jta.platform.spi;

import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import org.hibernate.service.Service;

public abstract interface JtaPlatform
  extends Service
{
  public abstract TransactionManager retrieveTransactionManager();
  
  public abstract UserTransaction retrieveUserTransaction();
  
  public abstract Object getTransactionIdentifier(Transaction paramTransaction);
  
  public abstract boolean canRegisterSynchronization();
  
  public abstract void registerSynchronization(Synchronization paramSynchronization);
  
  public abstract int getCurrentStatus()
    throws SystemException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.spi.JtaPlatform
 * JD-Core Version:    0.7.0.1
 */
package org.hibernate.transaction;

import java.util.Properties;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import org.hibernate.HibernateException;

public abstract interface TransactionManagerLookup
{
  public abstract TransactionManager getTransactionManager(Properties paramProperties)
    throws HibernateException;
  
  public abstract String getUserTransactionName();
  
  public abstract Object getTransactionIdentifier(Transaction paramTransaction);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.transaction.TransactionManagerLookup
 * JD-Core Version:    0.7.0.1
 */
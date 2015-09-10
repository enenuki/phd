package org.hibernate.action.spi;

import java.io.Serializable;
import org.hibernate.HibernateException;

public abstract interface Executable
{
  public abstract Serializable[] getPropertySpaces();
  
  public abstract void beforeExecutions()
    throws HibernateException;
  
  public abstract void execute()
    throws HibernateException;
  
  public abstract AfterTransactionCompletionProcess getAfterTransactionCompletionProcess();
  
  public abstract BeforeTransactionCompletionProcess getBeforeTransactionCompletionProcess();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.action.spi.Executable
 * JD-Core Version:    0.7.0.1
 */
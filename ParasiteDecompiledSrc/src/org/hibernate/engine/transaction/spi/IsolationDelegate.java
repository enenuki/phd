package org.hibernate.engine.transaction.spi;

import org.hibernate.HibernateException;
import org.hibernate.jdbc.WorkExecutorVisitable;

public abstract interface IsolationDelegate
{
  public abstract <T> T delegateWork(WorkExecutorVisitable<T> paramWorkExecutorVisitable, boolean paramBoolean)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.spi.IsolationDelegate
 * JD-Core Version:    0.7.0.1
 */
package org.hibernate.action.spi;

import org.hibernate.engine.spi.SessionImplementor;

public abstract interface BeforeTransactionCompletionProcess
{
  public abstract void doBeforeTransactionCompletion(SessionImplementor paramSessionImplementor);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.action.spi.BeforeTransactionCompletionProcess
 * JD-Core Version:    0.7.0.1
 */
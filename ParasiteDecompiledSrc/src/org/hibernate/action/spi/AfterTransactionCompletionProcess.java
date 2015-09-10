package org.hibernate.action.spi;

import org.hibernate.engine.spi.SessionImplementor;

public abstract interface AfterTransactionCompletionProcess
{
  public abstract void doAfterTransactionCompletion(boolean paramBoolean, SessionImplementor paramSessionImplementor);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.action.spi.AfterTransactionCompletionProcess
 * JD-Core Version:    0.7.0.1
 */
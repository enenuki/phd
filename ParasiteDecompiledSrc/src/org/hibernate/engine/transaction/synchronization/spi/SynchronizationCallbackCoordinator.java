package org.hibernate.engine.transaction.synchronization.spi;

import javax.transaction.Synchronization;

public abstract interface SynchronizationCallbackCoordinator
  extends Synchronization
{
  public abstract void setManagedFlushChecker(ManagedFlushChecker paramManagedFlushChecker);
  
  public abstract void setAfterCompletionAction(AfterCompletionAction paramAfterCompletionAction);
  
  public abstract void setExceptionMapper(ExceptionMapper paramExceptionMapper);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.synchronization.spi.SynchronizationCallbackCoordinator
 * JD-Core Version:    0.7.0.1
 */
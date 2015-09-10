package javax.transaction;

import javax.transaction.xa.XAResource;

public abstract interface Transaction
{
  public abstract void commit()
    throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, SystemException;
  
  public abstract void rollback()
    throws IllegalStateException, SystemException;
  
  public abstract void setRollbackOnly()
    throws IllegalStateException, SystemException;
  
  public abstract int getStatus()
    throws SystemException;
  
  public abstract boolean enlistResource(XAResource paramXAResource)
    throws RollbackException, IllegalStateException, SystemException;
  
  public abstract boolean delistResource(XAResource paramXAResource, int paramInt)
    throws IllegalStateException, SystemException;
  
  public abstract void registerSynchronization(Synchronization paramSynchronization)
    throws RollbackException, IllegalStateException, SystemException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.transaction.Transaction
 * JD-Core Version:    0.7.0.1
 */
package javax.transaction;

public abstract interface TransactionManager
{
  public abstract void begin()
    throws NotSupportedException, SystemException;
  
  public abstract void commit()
    throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException;
  
  public abstract void rollback()
    throws IllegalStateException, SecurityException, SystemException;
  
  public abstract void setRollbackOnly()
    throws IllegalStateException, SystemException;
  
  public abstract int getStatus()
    throws SystemException;
  
  public abstract Transaction getTransaction()
    throws SystemException;
  
  public abstract void setTransactionTimeout(int paramInt)
    throws SystemException;
  
  public abstract Transaction suspend()
    throws SystemException;
  
  public abstract void resume(Transaction paramTransaction)
    throws InvalidTransactionException, IllegalStateException, SystemException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.transaction.TransactionManager
 * JD-Core Version:    0.7.0.1
 */
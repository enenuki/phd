package javax.transaction;

public abstract interface TransactionSynchronizationRegistry
{
  public abstract Object getTransactionKey();
  
  public abstract int getTransactionStatus();
  
  public abstract boolean getRollbackOnly()
    throws IllegalStateException;
  
  public abstract void setRollbackOnly()
    throws IllegalStateException;
  
  public abstract void registerInterposedSynchronization(Synchronization paramSynchronization)
    throws IllegalStateException;
  
  public abstract Object getResource(Object paramObject)
    throws IllegalStateException;
  
  public abstract void putResource(Object paramObject1, Object paramObject2)
    throws IllegalStateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.transaction.TransactionSynchronizationRegistry
 * JD-Core Version:    0.7.0.1
 */
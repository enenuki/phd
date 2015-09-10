package javax.transaction;

public abstract interface Synchronization
{
  public abstract void beforeCompletion();
  
  public abstract void afterCompletion(int paramInt);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.transaction.Synchronization
 * JD-Core Version:    0.7.0.1
 */
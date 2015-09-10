package javax.persistence;

public abstract interface Cache
{
  public abstract boolean contains(Class paramClass, Object paramObject);
  
  public abstract void evict(Class paramClass, Object paramObject);
  
  public abstract void evict(Class paramClass);
  
  public abstract void evictAll();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.Cache
 * JD-Core Version:    0.7.0.1
 */
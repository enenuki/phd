package javax.persistence;

public abstract interface PersistenceUnitUtil
  extends PersistenceUtil
{
  public abstract boolean isLoaded(Object paramObject, String paramString);
  
  public abstract boolean isLoaded(Object paramObject);
  
  public abstract Object getIdentifier(Object paramObject);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.PersistenceUnitUtil
 * JD-Core Version:    0.7.0.1
 */
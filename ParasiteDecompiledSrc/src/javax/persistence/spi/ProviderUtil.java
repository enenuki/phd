package javax.persistence.spi;

public abstract interface ProviderUtil
{
  public abstract LoadState isLoadedWithoutReference(Object paramObject, String paramString);
  
  public abstract LoadState isLoadedWithReference(Object paramObject, String paramString);
  
  public abstract LoadState isLoaded(Object paramObject);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.spi.ProviderUtil
 * JD-Core Version:    0.7.0.1
 */
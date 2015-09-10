package javax.persistence.spi;

import java.util.List;

public abstract interface PersistenceProviderResolver
{
  public abstract List<PersistenceProvider> getPersistenceProviders();
  
  public abstract void clearCachedProviders();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.spi.PersistenceProviderResolver
 * JD-Core Version:    0.7.0.1
 */
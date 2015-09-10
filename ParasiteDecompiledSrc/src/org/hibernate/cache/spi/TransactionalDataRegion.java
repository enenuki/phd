package org.hibernate.cache.spi;

public abstract interface TransactionalDataRegion
  extends Region
{
  public abstract boolean isTransactionAware();
  
  public abstract CacheDataDescription getCacheDataDescription();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.TransactionalDataRegion
 * JD-Core Version:    0.7.0.1
 */
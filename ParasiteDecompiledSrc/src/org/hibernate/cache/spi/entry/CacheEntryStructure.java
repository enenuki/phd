package org.hibernate.cache.spi.entry;

import org.hibernate.engine.spi.SessionFactoryImplementor;

public abstract interface CacheEntryStructure
{
  public abstract Object structure(Object paramObject);
  
  public abstract Object destructure(Object paramObject, SessionFactoryImplementor paramSessionFactoryImplementor);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.entry.CacheEntryStructure
 * JD-Core Version:    0.7.0.1
 */
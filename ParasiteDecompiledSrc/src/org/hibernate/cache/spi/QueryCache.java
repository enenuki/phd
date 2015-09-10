package org.hibernate.cache.spi;

import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.cache.CacheException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.Type;

public abstract interface QueryCache
{
  public abstract void clear()
    throws CacheException;
  
  public abstract boolean put(QueryKey paramQueryKey, Type[] paramArrayOfType, List paramList, boolean paramBoolean, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract List get(QueryKey paramQueryKey, Type[] paramArrayOfType, boolean paramBoolean, Set paramSet, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract void destroy();
  
  public abstract QueryResultsRegion getRegion();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.QueryCache
 * JD-Core Version:    0.7.0.1
 */
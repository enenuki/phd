package org.hibernate.cache.spi;

import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Settings;

public abstract interface QueryCacheFactory
{
  public abstract QueryCache getQueryCache(String paramString, UpdateTimestampsCache paramUpdateTimestampsCache, Settings paramSettings, Properties paramProperties)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.QueryCacheFactory
 * JD-Core Version:    0.7.0.1
 */
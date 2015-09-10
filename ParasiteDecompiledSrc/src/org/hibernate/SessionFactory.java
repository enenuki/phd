package org.hibernate;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;
import java.util.Set;
import javax.naming.Referenceable;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.proxy.EntityNotFoundDelegate;
import org.hibernate.stat.Statistics;

public abstract interface SessionFactory
  extends Referenceable, Serializable
{
  public abstract SessionFactoryOptions getSessionFactoryOptions();
  
  public abstract SessionBuilder withOptions();
  
  public abstract Session openSession()
    throws HibernateException;
  
  public abstract Session getCurrentSession()
    throws HibernateException;
  
  public abstract StatelessSessionBuilder withStatelessOptions();
  
  public abstract StatelessSession openStatelessSession();
  
  public abstract StatelessSession openStatelessSession(Connection paramConnection);
  
  public abstract ClassMetadata getClassMetadata(Class paramClass);
  
  public abstract ClassMetadata getClassMetadata(String paramString);
  
  public abstract CollectionMetadata getCollectionMetadata(String paramString);
  
  public abstract Map<String, ClassMetadata> getAllClassMetadata();
  
  public abstract Map getAllCollectionMetadata();
  
  public abstract Statistics getStatistics();
  
  public abstract void close()
    throws HibernateException;
  
  public abstract boolean isClosed();
  
  public abstract Cache getCache();
  
  @Deprecated
  public abstract void evict(Class paramClass)
    throws HibernateException;
  
  @Deprecated
  public abstract void evict(Class paramClass, Serializable paramSerializable)
    throws HibernateException;
  
  @Deprecated
  public abstract void evictEntity(String paramString)
    throws HibernateException;
  
  @Deprecated
  public abstract void evictEntity(String paramString, Serializable paramSerializable)
    throws HibernateException;
  
  @Deprecated
  public abstract void evictCollection(String paramString)
    throws HibernateException;
  
  @Deprecated
  public abstract void evictCollection(String paramString, Serializable paramSerializable)
    throws HibernateException;
  
  @Deprecated
  public abstract void evictQueries(String paramString)
    throws HibernateException;
  
  @Deprecated
  public abstract void evictQueries()
    throws HibernateException;
  
  public abstract Set getDefinedFilterNames();
  
  public abstract FilterDefinition getFilterDefinition(String paramString)
    throws HibernateException;
  
  public abstract boolean containsFetchProfileDefinition(String paramString);
  
  public abstract TypeHelper getTypeHelper();
  
  public static abstract interface SessionFactoryOptions
  {
    public abstract Interceptor getInterceptor();
    
    public abstract EntityNotFoundDelegate getEntityNotFoundDelegate();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.SessionFactory
 * JD-Core Version:    0.7.0.1
 */
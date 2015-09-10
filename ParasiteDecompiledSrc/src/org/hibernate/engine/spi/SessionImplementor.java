package org.hibernate.engine.spi;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.cache.spi.CacheKey;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.jdbc.LobCreationContext;
import org.hibernate.engine.jdbc.spi.JdbcConnectionAccess;
import org.hibernate.engine.query.spi.sql.NativeSQLQuerySpecification;
import org.hibernate.engine.transaction.spi.TransactionCoordinator;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.loader.custom.CustomQuery;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.Type;

public abstract interface SessionImplementor
  extends Serializable, LobCreationContext
{
  public abstract String getTenantIdentifier();
  
  public abstract JdbcConnectionAccess getJdbcConnectionAccess();
  
  public abstract EntityKey generateEntityKey(Serializable paramSerializable, EntityPersister paramEntityPersister);
  
  public abstract CacheKey generateCacheKey(Serializable paramSerializable, Type paramType, String paramString);
  
  public abstract Interceptor getInterceptor();
  
  public abstract void setAutoClear(boolean paramBoolean);
  
  public abstract void disableTransactionAutoJoin();
  
  public abstract boolean isTransactionInProgress();
  
  public abstract void initializeCollection(PersistentCollection paramPersistentCollection, boolean paramBoolean)
    throws HibernateException;
  
  public abstract Object internalLoad(String paramString, Serializable paramSerializable, boolean paramBoolean1, boolean paramBoolean2)
    throws HibernateException;
  
  public abstract Object immediateLoad(String paramString, Serializable paramSerializable)
    throws HibernateException;
  
  public abstract long getTimestamp();
  
  public abstract SessionFactoryImplementor getFactory();
  
  public abstract List list(String paramString, QueryParameters paramQueryParameters)
    throws HibernateException;
  
  public abstract Iterator iterate(String paramString, QueryParameters paramQueryParameters)
    throws HibernateException;
  
  public abstract ScrollableResults scroll(String paramString, QueryParameters paramQueryParameters)
    throws HibernateException;
  
  public abstract ScrollableResults scroll(CriteriaImpl paramCriteriaImpl, ScrollMode paramScrollMode);
  
  public abstract List list(CriteriaImpl paramCriteriaImpl);
  
  public abstract List listFilter(Object paramObject, String paramString, QueryParameters paramQueryParameters)
    throws HibernateException;
  
  public abstract Iterator iterateFilter(Object paramObject, String paramString, QueryParameters paramQueryParameters)
    throws HibernateException;
  
  public abstract EntityPersister getEntityPersister(String paramString, Object paramObject)
    throws HibernateException;
  
  public abstract Object getEntityUsingInterceptor(EntityKey paramEntityKey)
    throws HibernateException;
  
  public abstract Serializable getContextEntityIdentifier(Object paramObject);
  
  public abstract String bestGuessEntityName(Object paramObject);
  
  public abstract String guessEntityName(Object paramObject)
    throws HibernateException;
  
  public abstract Object instantiate(String paramString, Serializable paramSerializable)
    throws HibernateException;
  
  public abstract List listCustomQuery(CustomQuery paramCustomQuery, QueryParameters paramQueryParameters)
    throws HibernateException;
  
  public abstract ScrollableResults scrollCustomQuery(CustomQuery paramCustomQuery, QueryParameters paramQueryParameters)
    throws HibernateException;
  
  public abstract List list(NativeSQLQuerySpecification paramNativeSQLQuerySpecification, QueryParameters paramQueryParameters)
    throws HibernateException;
  
  public abstract ScrollableResults scroll(NativeSQLQuerySpecification paramNativeSQLQuerySpecification, QueryParameters paramQueryParameters)
    throws HibernateException;
  
  @Deprecated
  public abstract Object getFilterParameterValue(String paramString);
  
  @Deprecated
  public abstract Type getFilterParameterType(String paramString);
  
  @Deprecated
  public abstract Map getEnabledFilters();
  
  public abstract int getDontFlushFromFind();
  
  public abstract PersistenceContext getPersistenceContext();
  
  public abstract int executeUpdate(String paramString, QueryParameters paramQueryParameters)
    throws HibernateException;
  
  public abstract int executeNativeUpdate(NativeSQLQuerySpecification paramNativeSQLQuerySpecification, QueryParameters paramQueryParameters)
    throws HibernateException;
  
  public abstract NonFlushedChanges getNonFlushedChanges()
    throws HibernateException;
  
  public abstract void applyNonFlushedChanges(NonFlushedChanges paramNonFlushedChanges)
    throws HibernateException;
  
  public abstract CacheMode getCacheMode();
  
  public abstract void setCacheMode(CacheMode paramCacheMode);
  
  public abstract boolean isOpen();
  
  public abstract boolean isConnected();
  
  public abstract FlushMode getFlushMode();
  
  public abstract void setFlushMode(FlushMode paramFlushMode);
  
  public abstract Connection connection();
  
  public abstract void flush();
  
  public abstract Query getNamedQuery(String paramString);
  
  public abstract Query getNamedSQLQuery(String paramString);
  
  public abstract boolean isEventSource();
  
  public abstract void afterScrollOperation();
  
  @Deprecated
  public abstract String getFetchProfile();
  
  @Deprecated
  public abstract void setFetchProfile(String paramString);
  
  public abstract TransactionCoordinator getTransactionCoordinator();
  
  public abstract boolean isClosed();
  
  public abstract LoadQueryInfluencers getLoadQueryInfluencers();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.SessionImplementor
 * JD-Core Version:    0.7.0.1
 */
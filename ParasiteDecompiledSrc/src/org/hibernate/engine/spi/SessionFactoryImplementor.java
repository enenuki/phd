package org.hibernate.engine.spi;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.cache.spi.QueryCache;
import org.hibernate.cache.spi.Region;
import org.hibernate.cache.spi.UpdateTimestampsCache;
import org.hibernate.cfg.Settings;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.SQLFunctionRegistry;
import org.hibernate.engine.ResultSetMappingDefinition;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.engine.profile.FetchProfile;
import org.hibernate.engine.query.spi.QueryPlanCache;
import org.hibernate.exception.spi.SQLExceptionConverter;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.proxy.EntityNotFoundDelegate;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.hibernate.stat.spi.StatisticsImplementor;
import org.hibernate.type.Type;
import org.hibernate.type.TypeResolver;

public abstract interface SessionFactoryImplementor
  extends Mapping, SessionFactory
{
  public abstract TypeResolver getTypeResolver();
  
  public abstract Properties getProperties();
  
  public abstract EntityPersister getEntityPersister(String paramString)
    throws MappingException;
  
  public abstract CollectionPersister getCollectionPersister(String paramString)
    throws MappingException;
  
  public abstract JdbcServices getJdbcServices();
  
  public abstract Dialect getDialect();
  
  public abstract Interceptor getInterceptor();
  
  public abstract QueryPlanCache getQueryPlanCache();
  
  public abstract Type[] getReturnTypes(String paramString)
    throws HibernateException;
  
  public abstract String[] getReturnAliases(String paramString)
    throws HibernateException;
  
  public abstract ConnectionProvider getConnectionProvider();
  
  public abstract String[] getImplementors(String paramString)
    throws MappingException;
  
  public abstract String getImportedClassName(String paramString);
  
  public abstract QueryCache getQueryCache();
  
  public abstract QueryCache getQueryCache(String paramString)
    throws HibernateException;
  
  public abstract UpdateTimestampsCache getUpdateTimestampsCache();
  
  public abstract StatisticsImplementor getStatisticsImplementor();
  
  public abstract NamedQueryDefinition getNamedQuery(String paramString);
  
  public abstract NamedSQLQueryDefinition getNamedSQLQuery(String paramString);
  
  public abstract ResultSetMappingDefinition getResultSetMapping(String paramString);
  
  public abstract IdentifierGenerator getIdentifierGenerator(String paramString);
  
  public abstract Region getSecondLevelCacheRegion(String paramString);
  
  public abstract Map getAllSecondLevelCacheRegions();
  
  public abstract SQLExceptionConverter getSQLExceptionConverter();
  
  public abstract SqlExceptionHelper getSQLExceptionHelper();
  
  public abstract Settings getSettings();
  
  public abstract Session openTemporarySession()
    throws HibernateException;
  
  public abstract Set<String> getCollectionRolesByEntityParticipant(String paramString);
  
  public abstract EntityNotFoundDelegate getEntityNotFoundDelegate();
  
  public abstract SQLFunctionRegistry getSqlFunctionRegistry();
  
  public abstract FetchProfile getFetchProfile(String paramString);
  
  public abstract ServiceRegistryImplementor getServiceRegistry();
  
  public abstract void addObserver(SessionFactoryObserver paramSessionFactoryObserver);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.SessionFactoryImplementor
 * JD-Core Version:    0.7.0.1
 */
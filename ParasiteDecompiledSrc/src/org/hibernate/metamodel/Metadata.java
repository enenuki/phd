package org.hibernate.metamodel;

import java.util.Map.Entry;
import javax.persistence.SharedCacheMode;
import org.hibernate.SessionFactory;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.engine.ResultSetMappingDefinition;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.engine.spi.NamedQueryDefinition;
import org.hibernate.engine.spi.NamedSQLQueryDefinition;
import org.hibernate.metamodel.binding.EntityBinding;
import org.hibernate.metamodel.binding.FetchProfile;
import org.hibernate.metamodel.binding.IdGenerator;
import org.hibernate.metamodel.binding.PluralAttributeBinding;
import org.hibernate.metamodel.binding.TypeDef;

public abstract interface Metadata
{
  public abstract Options getOptions();
  
  public abstract SessionFactoryBuilder getSessionFactoryBuilder();
  
  public abstract SessionFactory buildSessionFactory();
  
  public abstract Iterable<EntityBinding> getEntityBindings();
  
  public abstract EntityBinding getEntityBinding(String paramString);
  
  public abstract EntityBinding getRootEntityBinding(String paramString);
  
  public abstract Iterable<PluralAttributeBinding> getCollectionBindings();
  
  public abstract TypeDef getTypeDefinition(String paramString);
  
  public abstract Iterable<TypeDef> getTypeDefinitions();
  
  public abstract Iterable<FilterDefinition> getFilterDefinitions();
  
  public abstract Iterable<NamedQueryDefinition> getNamedQueryDefinitions();
  
  public abstract Iterable<NamedSQLQueryDefinition> getNamedNativeQueryDefinitions();
  
  public abstract Iterable<ResultSetMappingDefinition> getResultSetMappingDefinitions();
  
  public abstract Iterable<Map.Entry<String, String>> getImports();
  
  public abstract Iterable<FetchProfile> getFetchProfiles();
  
  public abstract IdGenerator getIdGenerator(String paramString);
  
  public static abstract interface Options
  {
    public abstract MetadataSourceProcessingOrder getMetadataSourceProcessingOrder();
    
    public abstract NamingStrategy getNamingStrategy();
    
    public abstract SharedCacheMode getSharedCacheMode();
    
    public abstract AccessType getDefaultAccessType();
    
    public abstract boolean useNewIdentifierGenerators();
    
    public abstract boolean isGloballyQuotedIdentifiers();
    
    public abstract String getDefaultSchemaName();
    
    public abstract String getDefaultCatalogName();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.Metadata
 * JD-Core Version:    0.7.0.1
 */
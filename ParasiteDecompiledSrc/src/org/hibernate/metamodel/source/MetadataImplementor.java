package org.hibernate.metamodel.source;

import org.hibernate.engine.ResultSetMappingDefinition;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.NamedQueryDefinition;
import org.hibernate.engine.spi.NamedSQLQueryDefinition;
import org.hibernate.metamodel.Metadata;
import org.hibernate.metamodel.binding.EntityBinding;
import org.hibernate.metamodel.binding.FetchProfile;
import org.hibernate.metamodel.binding.IdGenerator;
import org.hibernate.metamodel.binding.PluralAttributeBinding;
import org.hibernate.metamodel.binding.TypeDef;
import org.hibernate.metamodel.relational.Database;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.TypeResolver;

public abstract interface MetadataImplementor
  extends Metadata, BindingContext, Mapping
{
  public abstract ServiceRegistry getServiceRegistry();
  
  public abstract Database getDatabase();
  
  public abstract TypeResolver getTypeResolver();
  
  public abstract void addImport(String paramString1, String paramString2);
  
  public abstract void addEntity(EntityBinding paramEntityBinding);
  
  public abstract void addCollection(PluralAttributeBinding paramPluralAttributeBinding);
  
  public abstract void addFetchProfile(FetchProfile paramFetchProfile);
  
  public abstract void addTypeDefinition(TypeDef paramTypeDef);
  
  public abstract void addFilterDefinition(FilterDefinition paramFilterDefinition);
  
  public abstract void addIdGenerator(IdGenerator paramIdGenerator);
  
  public abstract void registerIdentifierGenerator(String paramString1, String paramString2);
  
  public abstract void addNamedNativeQuery(NamedSQLQueryDefinition paramNamedSQLQueryDefinition);
  
  public abstract void addNamedQuery(NamedQueryDefinition paramNamedQueryDefinition);
  
  public abstract void addResultSetMapping(ResultSetMappingDefinition paramResultSetMappingDefinition);
  
  public abstract void setGloballyQuotedIdentifiers(boolean paramBoolean);
  
  public abstract MetaAttributeContext getGlobalMetaAttributeContext();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.MetadataImplementor
 * JD-Core Version:    0.7.0.1
 */
package org.hibernate.metamodel.source;

import org.hibernate.cfg.NamingStrategy;
import org.hibernate.internal.util.Value;
import org.hibernate.metamodel.domain.Type;
import org.hibernate.service.ServiceRegistry;

public abstract interface BindingContext
{
  public abstract ServiceRegistry getServiceRegistry();
  
  public abstract NamingStrategy getNamingStrategy();
  
  public abstract MappingDefaults getMappingDefaults();
  
  public abstract MetadataImplementor getMetadataImplementor();
  
  public abstract <T> Class<T> locateClassByName(String paramString);
  
  public abstract Type makeJavaType(String paramString);
  
  public abstract boolean isGloballyQuotedIdentifiers();
  
  public abstract Value<Class<?>> makeClassReference(String paramString);
  
  public abstract String qualifyClassName(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.BindingContext
 * JD-Core Version:    0.7.0.1
 */
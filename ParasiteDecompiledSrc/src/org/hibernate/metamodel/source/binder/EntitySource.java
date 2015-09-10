package org.hibernate.metamodel.source.binder;

import java.util.List;
import org.hibernate.internal.jaxb.Origin;
import org.hibernate.metamodel.binding.CustomSQL;
import org.hibernate.metamodel.source.LocalBindingContext;

public abstract interface EntitySource
  extends SubclassEntityContainer, AttributeSourceContainer
{
  public abstract Origin getOrigin();
  
  public abstract LocalBindingContext getLocalBindingContext();
  
  public abstract String getEntityName();
  
  public abstract String getClassName();
  
  public abstract String getJpaEntityName();
  
  public abstract TableSource getPrimaryTable();
  
  public abstract Iterable<TableSource> getSecondaryTables();
  
  public abstract String getCustomTuplizerClassName();
  
  public abstract String getCustomPersisterClassName();
  
  public abstract boolean isLazy();
  
  public abstract String getProxy();
  
  public abstract int getBatchSize();
  
  public abstract boolean isAbstract();
  
  public abstract boolean isDynamicInsert();
  
  public abstract boolean isDynamicUpdate();
  
  public abstract boolean isSelectBeforeUpdate();
  
  public abstract String getCustomLoaderName();
  
  public abstract CustomSQL getCustomSqlInsert();
  
  public abstract CustomSQL getCustomSqlUpdate();
  
  public abstract CustomSQL getCustomSqlDelete();
  
  public abstract List<String> getSynchronizedTableNames();
  
  public abstract Iterable<MetaAttributeSource> metaAttributes();
  
  public abstract String getDiscriminatorMatchValue();
  
  public abstract Iterable<ConstraintSource> getConstraints();
  
  public abstract List<JpaCallbackClass> getJpaCallbackClasses();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.EntitySource
 * JD-Core Version:    0.7.0.1
 */
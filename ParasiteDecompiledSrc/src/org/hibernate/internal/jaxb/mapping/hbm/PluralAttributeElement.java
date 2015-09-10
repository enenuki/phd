package org.hibernate.internal.jaxb.mapping.hbm;

import java.util.List;

public abstract interface PluralAttributeElement
  extends MetaAttributeContainer
{
  public abstract String getName();
  
  public abstract String getAccess();
  
  public abstract JaxbKeyElement getKey();
  
  public abstract JaxbElementElement getElement();
  
  public abstract JaxbCompositeElementElement getCompositeElement();
  
  public abstract JaxbOneToManyElement getOneToMany();
  
  public abstract JaxbManyToManyElement getManyToMany();
  
  public abstract JaxbManyToAnyElement getManyToAny();
  
  public abstract String getSchema();
  
  public abstract String getCatalog();
  
  public abstract String getTable();
  
  public abstract String getComment();
  
  public abstract String getCheck();
  
  public abstract String getSubselect();
  
  public abstract String getSubselectAttribute();
  
  public abstract String getWhere();
  
  public abstract JaxbLoaderElement getLoader();
  
  public abstract JaxbSqlInsertElement getSqlInsert();
  
  public abstract JaxbSqlUpdateElement getSqlUpdate();
  
  public abstract JaxbSqlDeleteElement getSqlDelete();
  
  public abstract JaxbSqlDeleteAllElement getSqlDeleteAll();
  
  public abstract List<JaxbSynchronizeElement> getSynchronize();
  
  public abstract JaxbCacheElement getCache();
  
  public abstract List<JaxbFilterElement> getFilter();
  
  public abstract String getCascade();
  
  public abstract JaxbFetchAttributeWithSubselect getFetch();
  
  public abstract JaxbLazyAttributeWithExtra getLazy();
  
  public abstract JaxbOuterJoinAttribute getOuterJoin();
  
  public abstract String getBatchSize();
  
  public abstract boolean isInverse();
  
  public abstract boolean isMutable();
  
  public abstract boolean isOptimisticLock();
  
  public abstract String getCollectionType();
  
  public abstract String getPersister();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.PluralAttributeElement
 * JD-Core Version:    0.7.0.1
 */
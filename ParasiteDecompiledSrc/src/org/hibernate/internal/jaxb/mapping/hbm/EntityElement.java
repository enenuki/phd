package org.hibernate.internal.jaxb.mapping.hbm;

import java.util.List;

public abstract interface EntityElement
  extends MetaAttributeContainer
{
  public abstract String getName();
  
  public abstract String getEntityName();
  
  public abstract Boolean isAbstract();
  
  public abstract Boolean isLazy();
  
  public abstract String getProxy();
  
  public abstract String getBatchSize();
  
  public abstract boolean isDynamicInsert();
  
  public abstract boolean isDynamicUpdate();
  
  public abstract boolean isSelectBeforeUpdate();
  
  public abstract List<JaxbTuplizerElement> getTuplizer();
  
  public abstract String getPersister();
  
  public abstract JaxbLoaderElement getLoader();
  
  public abstract JaxbSqlInsertElement getSqlInsert();
  
  public abstract JaxbSqlUpdateElement getSqlUpdate();
  
  public abstract JaxbSqlDeleteElement getSqlDelete();
  
  public abstract List<JaxbSynchronizeElement> getSynchronize();
  
  public abstract List<JaxbFetchProfileElement> getFetchProfile();
  
  public abstract List<JaxbResultsetElement> getResultset();
  
  public abstract List<Object> getQueryOrSqlQuery();
  
  public abstract List<Object> getPropertyOrManyToOneOrOneToOne();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.EntityElement
 * JD-Core Version:    0.7.0.1
 */
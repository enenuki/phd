package org.hibernate.loader.criteria;

import java.io.Serializable;
import org.hibernate.persister.entity.PropertyMapping;
import org.hibernate.type.Type;

abstract interface CriteriaInfoProvider
{
  public abstract String getName();
  
  public abstract Serializable[] getSpaces();
  
  public abstract PropertyMapping getPropertyMapping();
  
  public abstract Type getType(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.criteria.CriteriaInfoProvider
 * JD-Core Version:    0.7.0.1
 */
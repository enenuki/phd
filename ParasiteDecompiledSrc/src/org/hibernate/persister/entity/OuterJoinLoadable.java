package org.hibernate.persister.entity;

import org.hibernate.FetchMode;
import org.hibernate.engine.spi.CascadeStyle;
import org.hibernate.type.EntityType;
import org.hibernate.type.Type;

public abstract interface OuterJoinLoadable
  extends Loadable, Joinable
{
  public abstract String selectFragment(String paramString1, String paramString2);
  
  public abstract int countSubclassProperties();
  
  public abstract FetchMode getFetchMode(int paramInt);
  
  public abstract CascadeStyle getCascadeStyle(int paramInt);
  
  public abstract boolean isDefinedOnSubclass(int paramInt);
  
  public abstract Type getSubclassPropertyType(int paramInt);
  
  public abstract String getSubclassPropertyName(int paramInt);
  
  public abstract boolean isSubclassPropertyNullable(int paramInt);
  
  public abstract String[] getSubclassPropertyColumnNames(int paramInt);
  
  public abstract String getSubclassPropertyTableName(int paramInt);
  
  public abstract String[] toColumns(String paramString, int paramInt);
  
  public abstract String fromTableFragment(String paramString);
  
  public abstract String[] getPropertyColumnNames(String paramString);
  
  public abstract String getPropertyTableName(String paramString);
  
  public abstract EntityType getEntityType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.entity.OuterJoinLoadable
 * JD-Core Version:    0.7.0.1
 */
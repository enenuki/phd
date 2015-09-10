package org.hibernate.metamodel.source.binder;

import org.hibernate.metamodel.relational.Datatype;
import org.hibernate.metamodel.relational.Size;

public abstract interface ColumnSource
  extends RelationalValueSource
{
  public abstract String getName();
  
  public abstract String getReadFragment();
  
  public abstract String getWriteFragment();
  
  public abstract boolean isNullable();
  
  public abstract String getDefaultValue();
  
  public abstract String getSqlType();
  
  public abstract Datatype getDatatype();
  
  public abstract Size getSize();
  
  public abstract boolean isUnique();
  
  public abstract String getCheckCondition();
  
  public abstract String getComment();
  
  public abstract boolean isIncludedInInsert();
  
  public abstract boolean isIncludedInUpdate();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.ColumnSource
 * JD-Core Version:    0.7.0.1
 */
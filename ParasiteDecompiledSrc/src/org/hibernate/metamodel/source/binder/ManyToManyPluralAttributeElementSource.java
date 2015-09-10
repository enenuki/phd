package org.hibernate.metamodel.source.binder;

import java.util.List;
import org.hibernate.FetchMode;

public abstract interface ManyToManyPluralAttributeElementSource
  extends PluralAttributeElementSource
{
  public abstract String getReferencedEntityName();
  
  public abstract String getReferencedEntityAttributeName();
  
  public abstract List<RelationalValueSource> getValueSources();
  
  public abstract boolean isNotFoundAnException();
  
  public abstract String getExplicitForeignKeyName();
  
  public abstract boolean isUnique();
  
  public abstract String getOrderBy();
  
  public abstract String getWhere();
  
  public abstract FetchMode getFetchMode();
  
  public abstract boolean fetchImmediately();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.ManyToManyPluralAttributeElementSource
 * JD-Core Version:    0.7.0.1
 */
package org.hibernate.metamodel.source.binder;

import java.util.List;

public abstract interface RelationalValueSourceContainer
{
  public abstract boolean areValuesIncludedInInsertByDefault();
  
  public abstract boolean areValuesIncludedInUpdateByDefault();
  
  public abstract boolean areValuesNullableByDefault();
  
  public abstract List<RelationalValueSource> relationalValueSources();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.RelationalValueSourceContainer
 * JD-Core Version:    0.7.0.1
 */
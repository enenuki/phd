package org.hibernate.metamodel.source.binder;

import java.util.List;

public abstract interface BasicPluralAttributeElementSource
  extends PluralAttributeElementSource
{
  public abstract List<RelationalValueSource> getValueSources();
  
  public abstract ExplicitHibernateTypeSource getExplicitHibernateTypeSource();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.BasicPluralAttributeElementSource
 * JD-Core Version:    0.7.0.1
 */
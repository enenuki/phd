package org.hibernate.metamodel.source.binder;

import java.util.List;
import org.hibernate.metamodel.relational.ForeignKey.ReferentialAction;

public abstract interface PluralAttributeKeySource
{
  public abstract List<RelationalValueSource> getValueSources();
  
  public abstract String getExplicitForeignKeyName();
  
  public abstract ForeignKey.ReferentialAction getOnDeleteAction();
  
  public abstract String getReferencedEntityAttributeName();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.PluralAttributeKeySource
 * JD-Core Version:    0.7.0.1
 */
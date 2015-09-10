package org.hibernate.metamodel.relational.state;

import java.util.Set;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.metamodel.relational.Size;

public abstract interface ColumnRelationalState
  extends SimpleValueRelationalState
{
  public abstract NamingStrategy getNamingStrategy();
  
  public abstract boolean isGloballyQuotedIdentifiers();
  
  public abstract String getExplicitColumnName();
  
  public abstract boolean isUnique();
  
  public abstract Size getSize();
  
  public abstract boolean isNullable();
  
  public abstract String getCheckCondition();
  
  public abstract String getDefault();
  
  public abstract String getSqlType();
  
  public abstract String getCustomWriteFragment();
  
  public abstract String getCustomReadFragment();
  
  public abstract String getComment();
  
  public abstract Set<String> getUniqueKeys();
  
  public abstract Set<String> getIndexes();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.state.ColumnRelationalState
 * JD-Core Version:    0.7.0.1
 */
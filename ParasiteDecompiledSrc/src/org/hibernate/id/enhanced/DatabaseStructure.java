package org.hibernate.id.enhanced;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;

public abstract interface DatabaseStructure
{
  public abstract String getName();
  
  public abstract int getTimesAccessed();
  
  public abstract int getInitialValue();
  
  public abstract int getIncrementSize();
  
  public abstract AccessCallback buildCallback(SessionImplementor paramSessionImplementor);
  
  public abstract void prepare(Optimizer paramOptimizer);
  
  public abstract String[] sqlCreateStrings(Dialect paramDialect);
  
  public abstract String[] sqlDropStrings(Dialect paramDialect);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.enhanced.DatabaseStructure
 * JD-Core Version:    0.7.0.1
 */
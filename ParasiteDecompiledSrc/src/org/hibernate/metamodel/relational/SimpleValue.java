package org.hibernate.metamodel.relational;

import org.hibernate.dialect.Dialect;

public abstract interface SimpleValue
  extends Value
{
  public abstract Datatype getDatatype();
  
  public abstract void setDatatype(Datatype paramDatatype);
  
  public abstract String getAlias(Dialect paramDialect);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.SimpleValue
 * JD-Core Version:    0.7.0.1
 */
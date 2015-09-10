package org.hibernate.mapping;

import java.io.Serializable;
import org.hibernate.dialect.Dialect;

public abstract interface AuxiliaryDatabaseObject
  extends RelationalModel, Serializable
{
  public abstract void addDialectScope(String paramString);
  
  public abstract boolean appliesToDialect(Dialect paramDialect);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.AuxiliaryDatabaseObject
 * JD-Core Version:    0.7.0.1
 */
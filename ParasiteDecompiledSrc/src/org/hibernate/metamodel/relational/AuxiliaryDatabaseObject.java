package org.hibernate.metamodel.relational;

import java.io.Serializable;
import org.hibernate.dialect.Dialect;

public abstract interface AuxiliaryDatabaseObject
  extends Exportable, Serializable
{
  public abstract boolean appliesToDialect(Dialect paramDialect);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.AuxiliaryDatabaseObject
 * JD-Core Version:    0.7.0.1
 */
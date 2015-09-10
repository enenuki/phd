package org.hibernate.metamodel.relational;

import org.hibernate.dialect.Dialect;

public abstract interface Exportable
{
  public abstract String getExportIdentifier();
  
  public abstract String[] sqlCreateStrings(Dialect paramDialect);
  
  public abstract String[] sqlDropStrings(Dialect paramDialect);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.Exportable
 * JD-Core Version:    0.7.0.1
 */
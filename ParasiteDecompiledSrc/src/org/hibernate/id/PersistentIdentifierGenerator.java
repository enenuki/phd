package org.hibernate.id;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;

public abstract interface PersistentIdentifierGenerator
  extends IdentifierGenerator
{
  public static final String SCHEMA = "schema";
  public static final String TABLE = "target_table";
  public static final String TABLES = "identity_tables";
  public static final String PK = "target_column";
  public static final String CATALOG = "catalog";
  public static final String IDENTIFIER_NORMALIZER = "identifier_normalizer";
  
  public abstract String[] sqlCreateStrings(Dialect paramDialect)
    throws HibernateException;
  
  public abstract String[] sqlDropStrings(Dialect paramDialect)
    throws HibernateException;
  
  public abstract Object generatorKey();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.PersistentIdentifierGenerator
 * JD-Core Version:    0.7.0.1
 */
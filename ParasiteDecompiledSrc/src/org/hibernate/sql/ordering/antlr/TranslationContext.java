package org.hibernate.sql.ordering.antlr;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.SQLFunctionRegistry;
import org.hibernate.engine.spi.SessionFactoryImplementor;

public abstract interface TranslationContext
{
  public abstract SessionFactoryImplementor getSessionFactory();
  
  public abstract Dialect getDialect();
  
  public abstract SQLFunctionRegistry getSqlFunctionRegistry();
  
  public abstract ColumnMapper getColumnMapper();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.ordering.antlr.TranslationContext
 * JD-Core Version:    0.7.0.1
 */
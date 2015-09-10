package org.hibernate.mapping;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.SQLFunctionRegistry;

public abstract interface Selectable
{
  public abstract String getAlias(Dialect paramDialect);
  
  public abstract String getAlias(Dialect paramDialect, Table paramTable);
  
  public abstract boolean isFormula();
  
  public abstract String getTemplate(Dialect paramDialect, SQLFunctionRegistry paramSQLFunctionRegistry);
  
  public abstract String getText(Dialect paramDialect);
  
  public abstract String getText();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Selectable
 * JD-Core Version:    0.7.0.1
 */
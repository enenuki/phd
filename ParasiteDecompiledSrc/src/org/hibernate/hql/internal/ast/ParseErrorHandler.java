package org.hibernate.hql.internal.ast;

import org.hibernate.QueryException;

public abstract interface ParseErrorHandler
  extends ErrorReporter
{
  public abstract int getErrorCount();
  
  public abstract void throwQueryException()
    throws QueryException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.ParseErrorHandler
 * JD-Core Version:    0.7.0.1
 */
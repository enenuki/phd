package org.hibernate.hql.internal.ast.exec;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.QueryParameters;
import org.hibernate.engine.spi.SessionImplementor;

public abstract interface StatementExecutor
{
  public abstract String[] getSqlStatements();
  
  public abstract int execute(QueryParameters paramQueryParameters, SessionImplementor paramSessionImplementor)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.exec.StatementExecutor
 * JD-Core Version:    0.7.0.1
 */
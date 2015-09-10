package org.hibernate.hql.internal.ast.tree;

import org.hibernate.hql.internal.ast.HqlSqlWalker;

public abstract interface Statement
{
  public abstract HqlSqlWalker getWalker();
  
  public abstract int getStatementType();
  
  public abstract boolean needsExecutor();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.Statement
 * JD-Core Version:    0.7.0.1
 */
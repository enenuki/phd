package org.hibernate.hql.internal.ast.tree;

import antlr.collections.AST;

public abstract interface RestrictableStatement
  extends Statement
{
  public abstract FromClause getFromClause();
  
  public abstract boolean hasWhereClause();
  
  public abstract AST getWhereClause();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.RestrictableStatement
 * JD-Core Version:    0.7.0.1
 */
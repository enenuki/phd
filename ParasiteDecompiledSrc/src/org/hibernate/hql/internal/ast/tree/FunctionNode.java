package org.hibernate.hql.internal.ast.tree;

import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.type.Type;

public abstract interface FunctionNode
{
  public abstract SQLFunction getSQLFunction();
  
  public abstract Type getFirstArgumentType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.FunctionNode
 * JD-Core Version:    0.7.0.1
 */
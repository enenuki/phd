package org.hibernate.hql.internal.ast.tree;

import antlr.SemanticException;
import org.hibernate.type.Type;

public abstract interface OperatorNode
{
  public abstract void initialize()
    throws SemanticException;
  
  public abstract Type getDataType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.OperatorNode
 * JD-Core Version:    0.7.0.1
 */
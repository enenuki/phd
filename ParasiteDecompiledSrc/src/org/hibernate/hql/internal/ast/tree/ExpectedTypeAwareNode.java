package org.hibernate.hql.internal.ast.tree;

import org.hibernate.type.Type;

public abstract interface ExpectedTypeAwareNode
{
  public abstract void setExpectedType(Type paramType);
  
  public abstract Type getExpectedType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.ExpectedTypeAwareNode
 * JD-Core Version:    0.7.0.1
 */
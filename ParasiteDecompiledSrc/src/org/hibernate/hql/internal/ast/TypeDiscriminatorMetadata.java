package org.hibernate.hql.internal.ast;

import org.hibernate.type.Type;

public abstract interface TypeDiscriminatorMetadata
{
  public abstract String getSqlFragment();
  
  public abstract Type getResolutionType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.TypeDiscriminatorMetadata
 * JD-Core Version:    0.7.0.1
 */
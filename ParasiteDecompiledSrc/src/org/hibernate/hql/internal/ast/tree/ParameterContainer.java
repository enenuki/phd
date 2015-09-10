package org.hibernate.hql.internal.ast.tree;

import org.hibernate.param.ParameterSpecification;

/**
 * @deprecated
 */
public abstract interface ParameterContainer
{
  public abstract void setText(String paramString);
  
  public abstract void addEmbeddedParameter(ParameterSpecification paramParameterSpecification);
  
  public abstract boolean hasEmbeddedParameters();
  
  public abstract ParameterSpecification[] getEmbeddedParameters();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.ParameterContainer
 * JD-Core Version:    0.7.0.1
 */
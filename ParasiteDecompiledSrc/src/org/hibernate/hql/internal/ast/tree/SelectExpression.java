package org.hibernate.hql.internal.ast.tree;

import antlr.SemanticException;
import org.hibernate.type.Type;

public abstract interface SelectExpression
{
  public abstract Type getDataType();
  
  public abstract void setScalarColumnText(int paramInt)
    throws SemanticException;
  
  public abstract void setScalarColumn(int paramInt)
    throws SemanticException;
  
  public abstract int getScalarColumnIndex();
  
  public abstract FromElement getFromElement();
  
  public abstract boolean isConstructor();
  
  public abstract boolean isReturnableEntity()
    throws SemanticException;
  
  public abstract void setText(String paramString);
  
  public abstract boolean isScalar()
    throws SemanticException;
  
  public abstract void setAlias(String paramString);
  
  public abstract String getAlias();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.SelectExpression
 * JD-Core Version:    0.7.0.1
 */
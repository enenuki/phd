package org.apache.xpath;

import javax.xml.transform.SourceLocator;

public abstract interface ExpressionNode
  extends SourceLocator
{
  public abstract void exprSetParent(ExpressionNode paramExpressionNode);
  
  public abstract ExpressionNode exprGetParent();
  
  public abstract void exprAddChild(ExpressionNode paramExpressionNode, int paramInt);
  
  public abstract ExpressionNode exprGetChild(int paramInt);
  
  public abstract int exprGetNumChildren();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.ExpressionNode
 * JD-Core Version:    0.7.0.1
 */
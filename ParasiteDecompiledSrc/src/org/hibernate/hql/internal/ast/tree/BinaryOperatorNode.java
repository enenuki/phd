package org.hibernate.hql.internal.ast.tree;

public abstract interface BinaryOperatorNode
  extends OperatorNode
{
  public abstract Node getLeftHandOperand();
  
  public abstract Node getRightHandOperand();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.BinaryOperatorNode
 * JD-Core Version:    0.7.0.1
 */
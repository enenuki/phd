package org.hibernate.hql.internal.ast.tree;

import antlr.SemanticException;
import antlr.collections.AST;

public abstract interface ResolvableNode
{
  public abstract void resolve(boolean paramBoolean1, boolean paramBoolean2, String paramString, AST paramAST)
    throws SemanticException;
  
  public abstract void resolve(boolean paramBoolean1, boolean paramBoolean2, String paramString)
    throws SemanticException;
  
  public abstract void resolve(boolean paramBoolean1, boolean paramBoolean2)
    throws SemanticException;
  
  public abstract void resolveInFunctionCall(boolean paramBoolean1, boolean paramBoolean2)
    throws SemanticException;
  
  public abstract void resolveIndex(AST paramAST)
    throws SemanticException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.ResolvableNode
 * JD-Core Version:    0.7.0.1
 */
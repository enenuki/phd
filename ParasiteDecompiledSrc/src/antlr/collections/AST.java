package antlr.collections;

import antlr.Token;

public abstract interface AST
{
  public abstract void addChild(AST paramAST);
  
  public abstract boolean equals(AST paramAST);
  
  public abstract boolean equalsList(AST paramAST);
  
  public abstract boolean equalsListPartial(AST paramAST);
  
  public abstract boolean equalsTree(AST paramAST);
  
  public abstract boolean equalsTreePartial(AST paramAST);
  
  public abstract ASTEnumeration findAll(AST paramAST);
  
  public abstract ASTEnumeration findAllPartial(AST paramAST);
  
  public abstract AST getFirstChild();
  
  public abstract AST getNextSibling();
  
  public abstract String getText();
  
  public abstract int getType();
  
  public abstract int getLine();
  
  public abstract int getColumn();
  
  public abstract int getNumberOfChildren();
  
  public abstract void initialize(int paramInt, String paramString);
  
  public abstract void initialize(AST paramAST);
  
  public abstract void initialize(Token paramToken);
  
  public abstract void setFirstChild(AST paramAST);
  
  public abstract void setNextSibling(AST paramAST);
  
  public abstract void setText(String paramString);
  
  public abstract void setType(int paramInt);
  
  public abstract String toString();
  
  public abstract String toStringList();
  
  public abstract String toStringTree();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.collections.AST
 * JD-Core Version:    0.7.0.1
 */
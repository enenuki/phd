package org.apache.xalan.xsltc.compiler;

public abstract interface Closure
{
  public abstract boolean inInnerClass();
  
  public abstract Closure getParentClosure();
  
  public abstract String getInnerClassName();
  
  public abstract void addVariable(VariableRefBase paramVariableRefBase);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Closure
 * JD-Core Version:    0.7.0.1
 */
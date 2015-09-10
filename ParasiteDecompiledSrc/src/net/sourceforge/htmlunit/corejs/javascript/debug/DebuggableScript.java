package net.sourceforge.htmlunit.corejs.javascript.debug;

public abstract interface DebuggableScript
{
  public abstract boolean isTopLevel();
  
  public abstract boolean isFunction();
  
  public abstract String getFunctionName();
  
  public abstract int getParamCount();
  
  public abstract int getParamAndVarCount();
  
  public abstract String getParamOrVarName(int paramInt);
  
  public abstract String getSourceName();
  
  public abstract boolean isGeneratedScript();
  
  public abstract int[] getLineNumbers();
  
  public abstract int getFunctionCount();
  
  public abstract DebuggableScript getFunction(int paramInt);
  
  public abstract DebuggableScript getParent();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.debug.DebuggableScript
 * JD-Core Version:    0.7.0.1
 */
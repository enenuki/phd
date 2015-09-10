package net.sourceforge.htmlunit.corejs.javascript;

public abstract interface ErrorReporter
{
  public abstract void warning(String paramString1, String paramString2, int paramInt1, String paramString3, int paramInt2);
  
  public abstract void error(String paramString1, String paramString2, int paramInt1, String paramString3, int paramInt2);
  
  public abstract EvaluatorException runtimeError(String paramString1, String paramString2, int paramInt1, String paramString3, int paramInt2);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ErrorReporter
 * JD-Core Version:    0.7.0.1
 */
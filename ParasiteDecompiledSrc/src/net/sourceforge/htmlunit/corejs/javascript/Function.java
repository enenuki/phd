package net.sourceforge.htmlunit.corejs.javascript;

public abstract interface Function
  extends Scriptable, Callable
{
  public abstract Object call(Context paramContext, Scriptable paramScriptable1, Scriptable paramScriptable2, Object[] paramArrayOfObject);
  
  public abstract Scriptable construct(Context paramContext, Scriptable paramScriptable, Object[] paramArrayOfObject);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.Function
 * JD-Core Version:    0.7.0.1
 */
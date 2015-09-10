package net.sourceforge.htmlunit.corejs.javascript.debug;

import net.sourceforge.htmlunit.corejs.javascript.Context;

public abstract interface Debugger
{
  public abstract void handleCompilationDone(Context paramContext, DebuggableScript paramDebuggableScript, String paramString);
  
  public abstract DebugFrame getFrame(Context paramContext, DebuggableScript paramDebuggableScript);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.debug.Debugger
 * JD-Core Version:    0.7.0.1
 */
package com.gargoylesoftware.htmlunit.javascript;

import net.sourceforge.htmlunit.corejs.javascript.Context;
import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
import net.sourceforge.htmlunit.corejs.javascript.debug.DebugFrame;

public class DebugFrameAdapter
  implements DebugFrame
{
  public void onDebuggerStatement(Context cx) {}
  
  public void onEnter(Context cx, Scriptable activation, Scriptable thisObj, Object[] args) {}
  
  public void onExceptionThrown(Context cx, Throwable ex) {}
  
  public void onExit(Context cx, boolean byThrow, Object resultOrException) {}
  
  public void onLineChange(Context cx, int lineNumber) {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.DebugFrameAdapter
 * JD-Core Version:    0.7.0.1
 */
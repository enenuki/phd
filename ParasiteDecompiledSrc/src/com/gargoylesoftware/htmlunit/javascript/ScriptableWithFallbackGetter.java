package com.gargoylesoftware.htmlunit.javascript;

import net.sourceforge.htmlunit.corejs.javascript.Scriptable;

public abstract interface ScriptableWithFallbackGetter
  extends Scriptable
{
  public abstract Object getWithFallback(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.ScriptableWithFallbackGetter
 * JD-Core Version:    0.7.0.1
 */
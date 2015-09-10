package com.steadystate.css.sac;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.DocumentHandler;

public abstract interface DocumentHandlerExt
  extends DocumentHandler
{
  public abstract void charset(String paramString)
    throws CSSException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.sac.DocumentHandlerExt
 * JD-Core Version:    0.7.0.1
 */
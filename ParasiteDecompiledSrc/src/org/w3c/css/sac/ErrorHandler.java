package org.w3c.css.sac;

public abstract interface ErrorHandler
{
  public abstract void warning(CSSParseException paramCSSParseException)
    throws CSSException;
  
  public abstract void error(CSSParseException paramCSSParseException)
    throws CSSException;
  
  public abstract void fatalError(CSSParseException paramCSSParseException)
    throws CSSException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.css.sac.ErrorHandler
 * JD-Core Version:    0.7.0.1
 */
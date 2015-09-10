package com.gargoylesoftware.htmlunit;

import java.io.Serializable;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

public class SilentCssErrorHandler
  implements ErrorHandler, Serializable
{
  public void error(CSSParseException exception) {}
  
  public void fatalError(CSSParseException exception) {}
  
  public void warning(CSSParseException exception) {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.SilentCssErrorHandler
 * JD-Core Version:    0.7.0.1
 */
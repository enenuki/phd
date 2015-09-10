package org.w3c.dom.css;

import org.w3c.dom.DOMException;

public abstract interface CSSPageRule
  extends CSSRule
{
  public abstract String getSelectorText();
  
  public abstract void setSelectorText(String paramString)
    throws DOMException;
  
  public abstract CSSStyleDeclaration getStyle();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.css.CSSPageRule
 * JD-Core Version:    0.7.0.1
 */
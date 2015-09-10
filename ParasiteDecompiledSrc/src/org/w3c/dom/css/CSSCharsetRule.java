package org.w3c.dom.css;

import org.w3c.dom.DOMException;

public abstract interface CSSCharsetRule
  extends CSSRule
{
  public abstract String getEncoding();
  
  public abstract void setEncoding(String paramString)
    throws DOMException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.css.CSSCharsetRule
 * JD-Core Version:    0.7.0.1
 */
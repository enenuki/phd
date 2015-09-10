package com.gargoylesoftware.htmlunit.html;

import java.io.Serializable;

public abstract interface HtmlAttributeChangeListener
  extends Serializable
{
  public abstract void attributeAdded(HtmlAttributeChangeEvent paramHtmlAttributeChangeEvent);
  
  public abstract void attributeRemoved(HtmlAttributeChangeEvent paramHtmlAttributeChangeEvent);
  
  public abstract void attributeReplaced(HtmlAttributeChangeEvent paramHtmlAttributeChangeEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlAttributeChangeListener
 * JD-Core Version:    0.7.0.1
 */
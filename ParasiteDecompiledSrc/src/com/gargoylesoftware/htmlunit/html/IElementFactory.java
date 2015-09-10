package com.gargoylesoftware.htmlunit.html;

import com.gargoylesoftware.htmlunit.SgmlPage;
import org.xml.sax.Attributes;

public abstract interface IElementFactory
{
  public abstract HtmlElement createElement(SgmlPage paramSgmlPage, String paramString, Attributes paramAttributes);
  
  public abstract HtmlElement createElementNS(SgmlPage paramSgmlPage, String paramString1, String paramString2, Attributes paramAttributes);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.IElementFactory
 * JD-Core Version:    0.7.0.1
 */
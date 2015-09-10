package com.gargoylesoftware.htmlunit;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public abstract interface ScriptPreProcessor
{
  public abstract String preProcess(HtmlPage paramHtmlPage, String paramString1, String paramString2, int paramInt, HtmlElement paramHtmlElement);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.ScriptPreProcessor
 * JD-Core Version:    0.7.0.1
 */
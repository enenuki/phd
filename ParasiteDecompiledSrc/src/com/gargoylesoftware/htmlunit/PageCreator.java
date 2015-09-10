package com.gargoylesoftware.htmlunit;

import java.io.IOException;

public abstract interface PageCreator
{
  public abstract Page createPage(WebResponse paramWebResponse, WebWindow paramWebWindow)
    throws IOException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.PageCreator
 * JD-Core Version:    0.7.0.1
 */
package com.gargoylesoftware.htmlunit;

import java.io.IOException;

public abstract interface WebConnection
{
  public abstract WebResponse getResponse(WebRequest paramWebRequest)
    throws IOException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.WebConnection
 * JD-Core Version:    0.7.0.1
 */
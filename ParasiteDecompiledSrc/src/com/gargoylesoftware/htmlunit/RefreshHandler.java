package com.gargoylesoftware.htmlunit;

import java.io.IOException;
import java.net.URL;

public abstract interface RefreshHandler
{
  public abstract void handleRefresh(Page paramPage, URL paramURL, int paramInt)
    throws IOException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.RefreshHandler
 * JD-Core Version:    0.7.0.1
 */
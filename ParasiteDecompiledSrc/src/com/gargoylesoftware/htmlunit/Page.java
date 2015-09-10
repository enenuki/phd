package com.gargoylesoftware.htmlunit;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

public abstract interface Page
  extends Serializable
{
  public abstract void initialize()
    throws IOException;
  
  public abstract void cleanUp()
    throws IOException;
  
  public abstract WebResponse getWebResponse();
  
  public abstract WebWindow getEnclosingWindow();
  
  public abstract URL getUrl();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.Page
 * JD-Core Version:    0.7.0.1
 */
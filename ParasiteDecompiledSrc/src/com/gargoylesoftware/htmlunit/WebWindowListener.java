package com.gargoylesoftware.htmlunit;

public abstract interface WebWindowListener
{
  public abstract void webWindowOpened(WebWindowEvent paramWebWindowEvent);
  
  public abstract void webWindowContentChanged(WebWindowEvent paramWebWindowEvent);
  
  public abstract void webWindowClosed(WebWindowEvent paramWebWindowEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.WebWindowListener
 * JD-Core Version:    0.7.0.1
 */
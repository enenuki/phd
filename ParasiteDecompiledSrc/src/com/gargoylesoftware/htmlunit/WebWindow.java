package com.gargoylesoftware.htmlunit;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;
import java.io.Serializable;

public abstract interface WebWindow
  extends Serializable
{
  public abstract String getName();
  
  public abstract void setName(String paramString);
  
  public abstract Page getEnclosedPage();
  
  public abstract void setEnclosedPage(Page paramPage);
  
  public abstract WebWindow getParentWindow();
  
  public abstract WebWindow getTopWindow();
  
  public abstract WebClient getWebClient();
  
  public abstract History getHistory();
  
  public abstract void setScriptObject(Object paramObject);
  
  public abstract Object getScriptObject();
  
  public abstract JavaScriptJobManager getJobManager();
  
  public abstract boolean isClosed();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.WebWindow
 * JD-Core Version:    0.7.0.1
 */
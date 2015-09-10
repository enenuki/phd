package com.gargoylesoftware.htmlunit.html;

import java.io.Serializable;

public abstract interface DomChangeListener
  extends Serializable
{
  public abstract void nodeAdded(DomChangeEvent paramDomChangeEvent);
  
  public abstract void nodeDeleted(DomChangeEvent paramDomChangeEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.DomChangeListener
 * JD-Core Version:    0.7.0.1
 */
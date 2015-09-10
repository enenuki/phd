package org.apache.xerces.xs;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.ls.LSInput;

public abstract interface XSLoader
{
  public abstract DOMConfiguration getConfig();
  
  public abstract XSModel loadURIList(StringList paramStringList);
  
  public abstract XSModel loadInputList(LSInputList paramLSInputList);
  
  public abstract XSModel loadURI(String paramString);
  
  public abstract XSModel load(LSInput paramLSInput);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xs.XSLoader
 * JD-Core Version:    0.7.0.1
 */
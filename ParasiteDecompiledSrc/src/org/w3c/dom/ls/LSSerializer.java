package org.w3c.dom.ls;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public abstract interface LSSerializer
{
  public abstract DOMConfiguration getDomConfig();
  
  public abstract String getNewLine();
  
  public abstract void setNewLine(String paramString);
  
  public abstract LSSerializerFilter getFilter();
  
  public abstract void setFilter(LSSerializerFilter paramLSSerializerFilter);
  
  public abstract boolean write(Node paramNode, LSOutput paramLSOutput)
    throws LSException;
  
  public abstract boolean writeToURI(Node paramNode, String paramString)
    throws LSException;
  
  public abstract String writeToString(Node paramNode)
    throws DOMException, LSException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.ls.LSSerializer
 * JD-Core Version:    0.7.0.1
 */
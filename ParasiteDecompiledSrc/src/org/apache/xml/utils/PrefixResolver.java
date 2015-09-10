package org.apache.xml.utils;

import org.w3c.dom.Node;

public abstract interface PrefixResolver
{
  public abstract String getNamespaceForPrefix(String paramString);
  
  public abstract String getNamespaceForPrefix(String paramString, Node paramNode);
  
  public abstract String getBaseIdentifier();
  
  public abstract boolean handlesNullPrefixes();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.PrefixResolver
 * JD-Core Version:    0.7.0.1
 */
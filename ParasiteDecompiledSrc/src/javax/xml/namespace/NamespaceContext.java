package javax.xml.namespace;

import java.util.Iterator;

public abstract interface NamespaceContext
{
  public abstract String getNamespaceURI(String paramString);
  
  public abstract String getPrefix(String paramString);
  
  public abstract Iterator getPrefixes(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.xml.namespace.NamespaceContext
 * JD-Core Version:    0.7.0.1
 */
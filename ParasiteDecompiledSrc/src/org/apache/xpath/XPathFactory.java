package org.apache.xpath;

import javax.xml.transform.SourceLocator;
import org.apache.xml.utils.PrefixResolver;

public abstract interface XPathFactory
{
  public abstract XPath create(String paramString, SourceLocator paramSourceLocator, PrefixResolver paramPrefixResolver, int paramInt);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.XPathFactory
 * JD-Core Version:    0.7.0.1
 */
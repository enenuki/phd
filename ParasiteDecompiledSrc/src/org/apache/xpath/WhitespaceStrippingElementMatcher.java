package org.apache.xpath;

import javax.xml.transform.TransformerException;
import org.w3c.dom.Element;

public abstract interface WhitespaceStrippingElementMatcher
{
  public abstract boolean shouldStripWhiteSpace(XPathContext paramXPathContext, Element paramElement)
    throws TransformerException;
  
  public abstract boolean canStripWhiteSpace();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.WhitespaceStrippingElementMatcher
 * JD-Core Version:    0.7.0.1
 */
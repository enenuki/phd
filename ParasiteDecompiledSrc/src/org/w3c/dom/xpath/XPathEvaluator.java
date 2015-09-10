package org.w3c.dom.xpath;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public abstract interface XPathEvaluator
{
  public abstract XPathExpression createExpression(String paramString, XPathNSResolver paramXPathNSResolver)
    throws XPathException, DOMException;
  
  public abstract XPathNSResolver createNSResolver(Node paramNode);
  
  public abstract Object evaluate(String paramString, Node paramNode, XPathNSResolver paramXPathNSResolver, short paramShort, Object paramObject)
    throws XPathException, DOMException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.xpath.XPathEvaluator
 * JD-Core Version:    0.7.0.1
 */
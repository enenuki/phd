package org.w3c.dom.xpath;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public abstract interface XPathExpression
{
  public abstract Object evaluate(Node paramNode, short paramShort, Object paramObject)
    throws XPathException, DOMException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.xpath.XPathExpression
 * JD-Core Version:    0.7.0.1
 */
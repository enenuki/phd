package org.apache.xerces.util;

import org.apache.xerces.xni.parser.XMLInputSource;
import org.w3c.dom.Node;

public final class DOMInputSource
  extends XMLInputSource
{
  private Node fNode;
  
  public DOMInputSource()
  {
    this(null);
  }
  
  public DOMInputSource(Node paramNode)
  {
    super(null, getSystemIdFromNode(paramNode), null);
    this.fNode = paramNode;
  }
  
  public DOMInputSource(Node paramNode, String paramString)
  {
    super(null, paramString, null);
    this.fNode = paramNode;
  }
  
  public Node getNode()
  {
    return this.fNode;
  }
  
  public void setNode(Node paramNode)
  {
    this.fNode = paramNode;
  }
  
  private static String getSystemIdFromNode(Node paramNode)
  {
    if (paramNode != null) {
      try
      {
        return paramNode.getBaseURI();
      }
      catch (NoSuchMethodError localNoSuchMethodError)
      {
        return null;
      }
      catch (Exception localException)
      {
        return null;
      }
    }
    return null;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.util.DOMInputSource
 * JD-Core Version:    0.7.0.1
 */
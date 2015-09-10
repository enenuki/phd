package org.apache.xerces.impl.xs.opti;

public class NodeImpl
  extends DefaultNode
{
  String prefix;
  String localpart;
  String rawname;
  String uri;
  short nodeType;
  boolean hidden;
  
  public NodeImpl() {}
  
  public NodeImpl(String paramString1, String paramString2, String paramString3, String paramString4, short paramShort)
  {
    this.prefix = paramString1;
    this.localpart = paramString2;
    this.rawname = paramString3;
    this.uri = paramString4;
    this.nodeType = paramShort;
  }
  
  public String getNodeName()
  {
    return this.rawname;
  }
  
  public String getNamespaceURI()
  {
    return this.uri;
  }
  
  public String getPrefix()
  {
    return this.prefix;
  }
  
  public String getLocalName()
  {
    return this.localpart;
  }
  
  public short getNodeType()
  {
    return this.nodeType;
  }
  
  public void setReadOnly(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.hidden = paramBoolean1;
  }
  
  public boolean getReadOnly()
  {
    return this.hidden;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.opti.NodeImpl
 * JD-Core Version:    0.7.0.1
 */
package org.apache.xerces.dom;

import org.w3c.dom.CDATASection;

public class CDATASectionImpl
  extends TextImpl
  implements CDATASection
{
  static final long serialVersionUID = 2372071297878177780L;
  
  public CDATASectionImpl(CoreDocumentImpl paramCoreDocumentImpl, String paramString)
  {
    super(paramCoreDocumentImpl, paramString);
  }
  
  public short getNodeType()
  {
    return 4;
  }
  
  public String getNodeName()
  {
    return "#cdata-section";
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.CDATASectionImpl
 * JD-Core Version:    0.7.0.1
 */
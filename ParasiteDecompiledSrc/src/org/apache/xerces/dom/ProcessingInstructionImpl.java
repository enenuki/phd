package org.apache.xerces.dom;

import org.w3c.dom.ProcessingInstruction;

public class ProcessingInstructionImpl
  extends CharacterDataImpl
  implements ProcessingInstruction
{
  static final long serialVersionUID = 7554435174099981510L;
  protected String target;
  
  public ProcessingInstructionImpl(CoreDocumentImpl paramCoreDocumentImpl, String paramString1, String paramString2)
  {
    super(paramCoreDocumentImpl, paramString2);
    this.target = paramString1;
  }
  
  public short getNodeType()
  {
    return 7;
  }
  
  public String getNodeName()
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    return this.target;
  }
  
  public String getTarget()
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    return this.target;
  }
  
  public String getData()
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    return this.data;
  }
  
  public void setData(String paramString)
  {
    setNodeValue(paramString);
  }
  
  public String getBaseURI()
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    return this.ownerNode.getBaseURI();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.ProcessingInstructionImpl
 * JD-Core Version:    0.7.0.1
 */
package org.apache.xerces.dom;

public class DeferredProcessingInstructionImpl
  extends ProcessingInstructionImpl
  implements DeferredNode
{
  static final long serialVersionUID = -4643577954293565388L;
  protected transient int fNodeIndex;
  
  DeferredProcessingInstructionImpl(DeferredDocumentImpl paramDeferredDocumentImpl, int paramInt)
  {
    super(paramDeferredDocumentImpl, null, null);
    this.fNodeIndex = paramInt;
    needsSyncData(true);
  }
  
  public int getNodeIndex()
  {
    return this.fNodeIndex;
  }
  
  protected void synchronizeData()
  {
    needsSyncData(false);
    DeferredDocumentImpl localDeferredDocumentImpl = (DeferredDocumentImpl)ownerDocument();
    this.target = localDeferredDocumentImpl.getNodeName(this.fNodeIndex);
    this.data = localDeferredDocumentImpl.getNodeValueString(this.fNodeIndex);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.DeferredProcessingInstructionImpl
 * JD-Core Version:    0.7.0.1
 */
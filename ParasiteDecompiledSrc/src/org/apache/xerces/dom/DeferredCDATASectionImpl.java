package org.apache.xerces.dom;

public class DeferredCDATASectionImpl
  extends CDATASectionImpl
  implements DeferredNode
{
  static final long serialVersionUID = 1983580632355645726L;
  protected transient int fNodeIndex;
  
  DeferredCDATASectionImpl(DeferredDocumentImpl paramDeferredDocumentImpl, int paramInt)
  {
    super(paramDeferredDocumentImpl, null);
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
    this.data = localDeferredDocumentImpl.getNodeValueString(this.fNodeIndex);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.DeferredCDATASectionImpl
 * JD-Core Version:    0.7.0.1
 */
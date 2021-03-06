package org.apache.xerces.dom;

public final class DeferredAttrImpl
  extends AttrImpl
  implements DeferredNode
{
  static final long serialVersionUID = 6903232312469148636L;
  protected transient int fNodeIndex;
  
  DeferredAttrImpl(DeferredDocumentImpl paramDeferredDocumentImpl, int paramInt)
  {
    super(paramDeferredDocumentImpl, null);
    this.fNodeIndex = paramInt;
    needsSyncData(true);
    needsSyncChildren(true);
  }
  
  public int getNodeIndex()
  {
    return this.fNodeIndex;
  }
  
  protected void synchronizeData()
  {
    needsSyncData(false);
    DeferredDocumentImpl localDeferredDocumentImpl = (DeferredDocumentImpl)ownerDocument();
    this.name = localDeferredDocumentImpl.getNodeName(this.fNodeIndex);
    int i = localDeferredDocumentImpl.getNodeExtra(this.fNodeIndex);
    isSpecified((i & 0x20) != 0);
    isIdAttribute((i & 0x200) != 0);
    int j = localDeferredDocumentImpl.getLastChild(this.fNodeIndex);
    this.type = localDeferredDocumentImpl.getTypeInfo(j);
  }
  
  protected void synchronizeChildren()
  {
    DeferredDocumentImpl localDeferredDocumentImpl = (DeferredDocumentImpl)ownerDocument();
    localDeferredDocumentImpl.synchronizeChildren(this, this.fNodeIndex);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.DeferredAttrImpl
 * JD-Core Version:    0.7.0.1
 */
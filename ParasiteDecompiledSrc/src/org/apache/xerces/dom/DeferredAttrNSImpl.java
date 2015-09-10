package org.apache.xerces.dom;

public final class DeferredAttrNSImpl
  extends AttrNSImpl
  implements DeferredNode
{
  static final long serialVersionUID = 6074924934945957154L;
  protected transient int fNodeIndex;
  
  DeferredAttrNSImpl(DeferredDocumentImpl paramDeferredDocumentImpl, int paramInt)
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
    int i = this.name.indexOf(':');
    if (i < 0) {
      this.localName = this.name;
    } else {
      this.localName = this.name.substring(i + 1);
    }
    int j = localDeferredDocumentImpl.getNodeExtra(this.fNodeIndex);
    isSpecified((j & 0x20) != 0);
    isIdAttribute((j & 0x200) != 0);
    this.namespaceURI = localDeferredDocumentImpl.getNodeURI(this.fNodeIndex);
    int k = localDeferredDocumentImpl.getLastChild(this.fNodeIndex);
    this.type = localDeferredDocumentImpl.getTypeInfo(k);
  }
  
  protected void synchronizeChildren()
  {
    DeferredDocumentImpl localDeferredDocumentImpl = (DeferredDocumentImpl)ownerDocument();
    localDeferredDocumentImpl.synchronizeChildren(this, this.fNodeIndex);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.DeferredAttrNSImpl
 * JD-Core Version:    0.7.0.1
 */
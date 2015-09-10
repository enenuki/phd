package org.apache.xerces.dom;

public class DeferredEntityReferenceImpl
  extends EntityReferenceImpl
  implements DeferredNode
{
  static final long serialVersionUID = 390319091370032223L;
  protected transient int fNodeIndex;
  
  DeferredEntityReferenceImpl(DeferredDocumentImpl paramDeferredDocumentImpl, int paramInt)
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
    DeferredDocumentImpl localDeferredDocumentImpl = (DeferredDocumentImpl)this.ownerDocument;
    this.name = localDeferredDocumentImpl.getNodeName(this.fNodeIndex);
    this.baseURI = localDeferredDocumentImpl.getNodeValue(this.fNodeIndex);
  }
  
  protected void synchronizeChildren()
  {
    needsSyncChildren(false);
    isReadOnly(false);
    DeferredDocumentImpl localDeferredDocumentImpl = (DeferredDocumentImpl)ownerDocument();
    localDeferredDocumentImpl.synchronizeChildren(this, this.fNodeIndex);
    setReadOnly(true, true);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.DeferredEntityReferenceImpl
 * JD-Core Version:    0.7.0.1
 */
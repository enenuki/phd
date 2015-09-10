package org.apache.xerces.dom;

public class DeferredEntityImpl
  extends EntityImpl
  implements DeferredNode
{
  static final long serialVersionUID = 4760180431078941638L;
  protected transient int fNodeIndex;
  
  DeferredEntityImpl(DeferredDocumentImpl paramDeferredDocumentImpl, int paramInt)
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
    DeferredDocumentImpl localDeferredDocumentImpl = (DeferredDocumentImpl)this.ownerDocument;
    this.name = localDeferredDocumentImpl.getNodeName(this.fNodeIndex);
    this.publicId = localDeferredDocumentImpl.getNodeValue(this.fNodeIndex);
    this.systemId = localDeferredDocumentImpl.getNodeURI(this.fNodeIndex);
    int i = localDeferredDocumentImpl.getNodeExtra(this.fNodeIndex);
    localDeferredDocumentImpl.getNodeType(i);
    this.notationName = localDeferredDocumentImpl.getNodeName(i);
    this.version = localDeferredDocumentImpl.getNodeValue(i);
    this.encoding = localDeferredDocumentImpl.getNodeURI(i);
    int j = localDeferredDocumentImpl.getNodeExtra(i);
    this.baseURI = localDeferredDocumentImpl.getNodeName(j);
    this.inputEncoding = localDeferredDocumentImpl.getNodeValue(j);
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
 * Qualified Name:     org.apache.xerces.dom.DeferredEntityImpl
 * JD-Core Version:    0.7.0.1
 */
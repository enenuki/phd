package org.apache.xerces.dom;

public class DeferredNotationImpl
  extends NotationImpl
  implements DeferredNode
{
  static final long serialVersionUID = 5705337172887990848L;
  protected transient int fNodeIndex;
  
  DeferredNotationImpl(DeferredDocumentImpl paramDeferredDocumentImpl, int paramInt)
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
    this.name = localDeferredDocumentImpl.getNodeName(this.fNodeIndex);
    localDeferredDocumentImpl.getNodeType(this.fNodeIndex);
    this.publicId = localDeferredDocumentImpl.getNodeValue(this.fNodeIndex);
    this.systemId = localDeferredDocumentImpl.getNodeURI(this.fNodeIndex);
    int i = localDeferredDocumentImpl.getNodeExtra(this.fNodeIndex);
    localDeferredDocumentImpl.getNodeType(i);
    this.baseURI = localDeferredDocumentImpl.getNodeName(i);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.DeferredNotationImpl
 * JD-Core Version:    0.7.0.1
 */
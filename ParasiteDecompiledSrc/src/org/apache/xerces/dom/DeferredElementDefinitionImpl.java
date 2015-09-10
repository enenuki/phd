package org.apache.xerces.dom;

public class DeferredElementDefinitionImpl
  extends ElementDefinitionImpl
  implements DeferredNode
{
  static final long serialVersionUID = 6703238199538041591L;
  protected transient int fNodeIndex;
  
  DeferredElementDefinitionImpl(DeferredDocumentImpl paramDeferredDocumentImpl, int paramInt)
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
  }
  
  protected void synchronizeChildren()
  {
    boolean bool = this.ownerDocument.getMutationEvents();
    this.ownerDocument.setMutationEvents(false);
    needsSyncChildren(false);
    DeferredDocumentImpl localDeferredDocumentImpl = (DeferredDocumentImpl)this.ownerDocument;
    this.attributes = new NamedNodeMapImpl(localDeferredDocumentImpl);
    for (int i = localDeferredDocumentImpl.getLastChild(this.fNodeIndex); i != -1; i = localDeferredDocumentImpl.getPrevSibling(i))
    {
      DeferredNode localDeferredNode = localDeferredDocumentImpl.getNodeObject(i);
      this.attributes.setNamedItem(localDeferredNode);
    }
    localDeferredDocumentImpl.setMutationEvents(bool);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.DeferredElementDefinitionImpl
 * JD-Core Version:    0.7.0.1
 */
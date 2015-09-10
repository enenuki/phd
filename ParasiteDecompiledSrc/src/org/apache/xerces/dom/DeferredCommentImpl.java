package org.apache.xerces.dom;

public class DeferredCommentImpl
  extends CommentImpl
  implements DeferredNode
{
  static final long serialVersionUID = 6498796371083589338L;
  protected transient int fNodeIndex;
  
  DeferredCommentImpl(DeferredDocumentImpl paramDeferredDocumentImpl, int paramInt)
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
 * Qualified Name:     org.apache.xerces.dom.DeferredCommentImpl
 * JD-Core Version:    0.7.0.1
 */
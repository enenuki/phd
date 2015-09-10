package org.apache.xerces.dom;

public class DeferredTextImpl
  extends TextImpl
  implements DeferredNode
{
  static final long serialVersionUID = 2310613872100393425L;
  protected transient int fNodeIndex;
  
  DeferredTextImpl(DeferredDocumentImpl paramDeferredDocumentImpl, int paramInt)
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
    isIgnorableWhitespace(localDeferredDocumentImpl.getNodeExtra(this.fNodeIndex) == 1);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.DeferredTextImpl
 * JD-Core Version:    0.7.0.1
 */
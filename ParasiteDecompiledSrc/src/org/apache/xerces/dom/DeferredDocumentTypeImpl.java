package org.apache.xerces.dom;

import java.io.PrintStream;
import org.w3c.dom.Node;

public class DeferredDocumentTypeImpl
  extends DocumentTypeImpl
  implements DeferredNode
{
  static final long serialVersionUID = -2172579663227313509L;
  protected transient int fNodeIndex;
  
  DeferredDocumentTypeImpl(DeferredDocumentImpl paramDeferredDocumentImpl, int paramInt)
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
    this.publicID = localDeferredDocumentImpl.getNodeValue(this.fNodeIndex);
    this.systemID = localDeferredDocumentImpl.getNodeURI(this.fNodeIndex);
    int i = localDeferredDocumentImpl.getNodeExtra(this.fNodeIndex);
    this.internalSubset = localDeferredDocumentImpl.getNodeValue(i);
  }
  
  protected void synchronizeChildren()
  {
    boolean bool = ownerDocument().getMutationEvents();
    ownerDocument().setMutationEvents(false);
    needsSyncChildren(false);
    DeferredDocumentImpl localDeferredDocumentImpl = (DeferredDocumentImpl)this.ownerDocument;
    this.entities = new NamedNodeMapImpl(this);
    this.notations = new NamedNodeMapImpl(this);
    this.elements = new NamedNodeMapImpl(this);
    Object localObject = null;
    for (int i = localDeferredDocumentImpl.getLastChild(this.fNodeIndex); i != -1; i = localDeferredDocumentImpl.getPrevSibling(i))
    {
      DeferredNode localDeferredNode = localDeferredDocumentImpl.getNodeObject(i);
      int j = localDeferredNode.getNodeType();
      switch (j)
      {
      case 6: 
        this.entities.setNamedItem(localDeferredNode);
        break;
      case 12: 
        this.notations.setNamedItem(localDeferredNode);
        break;
      case 21: 
        this.elements.setNamedItem(localDeferredNode);
        break;
      case 1: 
        if (((DocumentImpl)getOwnerDocument()).allowGrammarAccess)
        {
          insertBefore(localDeferredNode, (Node)localObject);
          localObject = localDeferredNode;
        }
        break;
      }
      System.out.println("DeferredDocumentTypeImpl#synchronizeInfo: node.getNodeType() = " + localDeferredNode.getNodeType() + ", class = " + localDeferredNode.getClass().getName());
    }
    ownerDocument().setMutationEvents(bool);
    setReadOnly(true, false);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.DeferredDocumentTypeImpl
 * JD-Core Version:    0.7.0.1
 */
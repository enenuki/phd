package org.apache.xerces.dom;

import org.w3c.dom.Node;

public abstract class ChildNode
  extends NodeImpl
{
  static final long serialVersionUID = -6112455738802414002L;
  protected ChildNode previousSibling;
  protected ChildNode nextSibling;
  
  protected ChildNode(CoreDocumentImpl paramCoreDocumentImpl)
  {
    super(paramCoreDocumentImpl);
  }
  
  public ChildNode() {}
  
  public Node cloneNode(boolean paramBoolean)
  {
    ChildNode localChildNode = (ChildNode)super.cloneNode(paramBoolean);
    localChildNode.previousSibling = null;
    localChildNode.nextSibling = null;
    localChildNode.isFirstChild(false);
    return localChildNode;
  }
  
  public Node getParentNode()
  {
    return isOwned() ? this.ownerNode : null;
  }
  
  final NodeImpl parentNode()
  {
    return isOwned() ? this.ownerNode : null;
  }
  
  public Node getNextSibling()
  {
    return this.nextSibling;
  }
  
  public Node getPreviousSibling()
  {
    return isFirstChild() ? null : this.previousSibling;
  }
  
  final ChildNode previousSibling()
  {
    return isFirstChild() ? null : this.previousSibling;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.ChildNode
 * JD-Core Version:    0.7.0.1
 */
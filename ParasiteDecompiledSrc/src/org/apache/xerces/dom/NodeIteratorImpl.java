package org.apache.xerces.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;

public class NodeIteratorImpl
  implements NodeIterator
{
  private DocumentImpl fDocument;
  private Node fRoot;
  private int fWhatToShow = -1;
  private NodeFilter fNodeFilter;
  private boolean fDetach = false;
  private Node fCurrentNode;
  private boolean fForward = true;
  private boolean fEntityReferenceExpansion;
  
  public NodeIteratorImpl(DocumentImpl paramDocumentImpl, Node paramNode, int paramInt, NodeFilter paramNodeFilter, boolean paramBoolean)
  {
    this.fDocument = paramDocumentImpl;
    this.fRoot = paramNode;
    this.fCurrentNode = null;
    this.fWhatToShow = paramInt;
    this.fNodeFilter = paramNodeFilter;
    this.fEntityReferenceExpansion = paramBoolean;
  }
  
  public Node getRoot()
  {
    return this.fRoot;
  }
  
  public int getWhatToShow()
  {
    return this.fWhatToShow;
  }
  
  public NodeFilter getFilter()
  {
    return this.fNodeFilter;
  }
  
  public boolean getExpandEntityReferences()
  {
    return this.fEntityReferenceExpansion;
  }
  
  public Node nextNode()
  {
    if (this.fDetach) {
      throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
    }
    if (this.fRoot == null) {
      return null;
    }
    Node localNode = this.fCurrentNode;
    boolean bool = false;
    while (!bool)
    {
      if ((!this.fForward) && (localNode != null)) {
        localNode = this.fCurrentNode;
      } else if ((!this.fEntityReferenceExpansion) && (localNode != null) && (localNode.getNodeType() == 5)) {
        localNode = nextNode(localNode, false);
      } else {
        localNode = nextNode(localNode, true);
      }
      this.fForward = true;
      if (localNode == null) {
        return null;
      }
      bool = acceptNode(localNode);
      if (bool)
      {
        this.fCurrentNode = localNode;
        return this.fCurrentNode;
      }
    }
    return null;
  }
  
  public Node previousNode()
  {
    if (this.fDetach) {
      throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
    }
    if ((this.fRoot == null) || (this.fCurrentNode == null)) {
      return null;
    }
    Node localNode = this.fCurrentNode;
    boolean bool = false;
    while (!bool)
    {
      if ((this.fForward) && (localNode != null)) {
        localNode = this.fCurrentNode;
      } else {
        localNode = previousNode(localNode);
      }
      this.fForward = false;
      if (localNode == null) {
        return null;
      }
      bool = acceptNode(localNode);
      if (bool)
      {
        this.fCurrentNode = localNode;
        return this.fCurrentNode;
      }
    }
    return null;
  }
  
  boolean acceptNode(Node paramNode)
  {
    if (this.fNodeFilter == null) {
      return (this.fWhatToShow & 1 << paramNode.getNodeType() - 1) != 0;
    }
    return ((this.fWhatToShow & 1 << paramNode.getNodeType() - 1) != 0) && (this.fNodeFilter.acceptNode(paramNode) == 1);
  }
  
  Node matchNodeOrParent(Node paramNode)
  {
    if (this.fCurrentNode == null) {
      return null;
    }
    for (Node localNode = this.fCurrentNode; localNode != this.fRoot; localNode = localNode.getParentNode()) {
      if (paramNode == localNode) {
        return localNode;
      }
    }
    return null;
  }
  
  Node nextNode(Node paramNode, boolean paramBoolean)
  {
    if (paramNode == null) {
      return this.fRoot;
    }
    if ((paramBoolean) && (paramNode.hasChildNodes()))
    {
      localNode1 = paramNode.getFirstChild();
      return localNode1;
    }
    if (paramNode == this.fRoot) {
      return null;
    }
    Node localNode1 = paramNode.getNextSibling();
    if (localNode1 != null) {
      return localNode1;
    }
    for (Node localNode2 = paramNode.getParentNode(); (localNode2 != null) && (localNode2 != this.fRoot); localNode2 = localNode2.getParentNode())
    {
      localNode1 = localNode2.getNextSibling();
      if (localNode1 != null) {
        return localNode1;
      }
    }
    return null;
  }
  
  Node previousNode(Node paramNode)
  {
    if (paramNode == this.fRoot) {
      return null;
    }
    Node localNode = paramNode.getPreviousSibling();
    if (localNode == null)
    {
      localNode = paramNode.getParentNode();
      return localNode;
    }
    if (localNode.hasChildNodes()) {
      if ((!this.fEntityReferenceExpansion) && (localNode != null))
      {
        if (localNode.getNodeType() == 5) {}
      }
      else {
        while (localNode.hasChildNodes()) {
          localNode = localNode.getLastChild();
        }
      }
    }
    return localNode;
  }
  
  public void removeNode(Node paramNode)
  {
    if (paramNode == null) {
      return;
    }
    Node localNode1 = matchNodeOrParent(paramNode);
    if (localNode1 == null) {
      return;
    }
    if (this.fForward)
    {
      this.fCurrentNode = previousNode(localNode1);
    }
    else
    {
      Node localNode2 = nextNode(localNode1, false);
      if (localNode2 != null)
      {
        this.fCurrentNode = localNode2;
      }
      else
      {
        this.fCurrentNode = previousNode(localNode1);
        this.fForward = true;
      }
    }
  }
  
  public void detach()
  {
    this.fDetach = true;
    this.fDocument.removeNodeIterator(this);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.NodeIteratorImpl
 * JD-Core Version:    0.7.0.1
 */
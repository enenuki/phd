package org.apache.xerces.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.TreeWalker;

public class TreeWalkerImpl
  implements TreeWalker
{
  private boolean fEntityReferenceExpansion = false;
  int fWhatToShow = -1;
  NodeFilter fNodeFilter;
  Node fCurrentNode;
  Node fRoot;
  private boolean fUseIsSameNode;
  
  public TreeWalkerImpl(Node paramNode, int paramInt, NodeFilter paramNodeFilter, boolean paramBoolean)
  {
    this.fCurrentNode = paramNode;
    this.fRoot = paramNode;
    this.fUseIsSameNode = useIsSameNode(paramNode);
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
  
  public void setWhatShow(int paramInt)
  {
    this.fWhatToShow = paramInt;
  }
  
  public NodeFilter getFilter()
  {
    return this.fNodeFilter;
  }
  
  public boolean getExpandEntityReferences()
  {
    return this.fEntityReferenceExpansion;
  }
  
  public Node getCurrentNode()
  {
    return this.fCurrentNode;
  }
  
  public void setCurrentNode(Node paramNode)
  {
    if (paramNode == null)
    {
      String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
      throw new DOMException((short)9, str);
    }
    this.fCurrentNode = paramNode;
  }
  
  public Node parentNode()
  {
    if (this.fCurrentNode == null) {
      return null;
    }
    Node localNode = getParentNode(this.fCurrentNode);
    if (localNode != null) {
      this.fCurrentNode = localNode;
    }
    return localNode;
  }
  
  public Node firstChild()
  {
    if (this.fCurrentNode == null) {
      return null;
    }
    Node localNode = getFirstChild(this.fCurrentNode);
    if (localNode != null) {
      this.fCurrentNode = localNode;
    }
    return localNode;
  }
  
  public Node lastChild()
  {
    if (this.fCurrentNode == null) {
      return null;
    }
    Node localNode = getLastChild(this.fCurrentNode);
    if (localNode != null) {
      this.fCurrentNode = localNode;
    }
    return localNode;
  }
  
  public Node previousSibling()
  {
    if (this.fCurrentNode == null) {
      return null;
    }
    Node localNode = getPreviousSibling(this.fCurrentNode);
    if (localNode != null) {
      this.fCurrentNode = localNode;
    }
    return localNode;
  }
  
  public Node nextSibling()
  {
    if (this.fCurrentNode == null) {
      return null;
    }
    Node localNode = getNextSibling(this.fCurrentNode);
    if (localNode != null) {
      this.fCurrentNode = localNode;
    }
    return localNode;
  }
  
  public Node previousNode()
  {
    if (this.fCurrentNode == null) {
      return null;
    }
    Node localNode = getPreviousSibling(this.fCurrentNode);
    if (localNode == null)
    {
      localNode = getParentNode(this.fCurrentNode);
      if (localNode != null)
      {
        this.fCurrentNode = localNode;
        return this.fCurrentNode;
      }
      return null;
    }
    Object localObject1 = getLastChild(localNode);
    Object localObject2 = localObject1;
    while (localObject1 != null)
    {
      localObject2 = localObject1;
      localObject1 = getLastChild(localObject2);
    }
    localObject1 = localObject2;
    if (localObject1 != null)
    {
      this.fCurrentNode = ((Node)localObject1);
      return this.fCurrentNode;
    }
    if (localNode != null)
    {
      this.fCurrentNode = localNode;
      return this.fCurrentNode;
    }
    return null;
  }
  
  public Node nextNode()
  {
    if (this.fCurrentNode == null) {
      return null;
    }
    Node localNode1 = getFirstChild(this.fCurrentNode);
    if (localNode1 != null)
    {
      this.fCurrentNode = localNode1;
      return localNode1;
    }
    localNode1 = getNextSibling(this.fCurrentNode);
    if (localNode1 != null)
    {
      this.fCurrentNode = localNode1;
      return localNode1;
    }
    for (Node localNode2 = getParentNode(this.fCurrentNode); localNode2 != null; localNode2 = getParentNode(localNode2))
    {
      localNode1 = getNextSibling(localNode2);
      if (localNode1 != null)
      {
        this.fCurrentNode = localNode1;
        return localNode1;
      }
    }
    return null;
  }
  
  Node getParentNode(Node paramNode)
  {
    if ((paramNode == null) || (isSameNode(paramNode, this.fRoot))) {
      return null;
    }
    Node localNode = paramNode.getParentNode();
    if (localNode == null) {
      return null;
    }
    int i = acceptNode(localNode);
    if (i == 1) {
      return localNode;
    }
    return getParentNode(localNode);
  }
  
  Node getNextSibling(Node paramNode)
  {
    return getNextSibling(paramNode, this.fRoot);
  }
  
  Node getNextSibling(Node paramNode1, Node paramNode2)
  {
    if ((paramNode1 == null) || (isSameNode(paramNode1, paramNode2))) {
      return null;
    }
    Node localNode1 = paramNode1.getNextSibling();
    if (localNode1 == null)
    {
      localNode1 = paramNode1.getParentNode();
      if ((localNode1 == null) || (isSameNode(localNode1, paramNode2))) {
        return null;
      }
      i = acceptNode(localNode1);
      if (i == 3) {
        return getNextSibling(localNode1, paramNode2);
      }
      return null;
    }
    int i = acceptNode(localNode1);
    if (i == 1) {
      return localNode1;
    }
    if (i == 3)
    {
      Node localNode2 = getFirstChild(localNode1);
      if (localNode2 == null) {
        return getNextSibling(localNode1, paramNode2);
      }
      return localNode2;
    }
    return getNextSibling(localNode1, paramNode2);
  }
  
  Node getPreviousSibling(Node paramNode)
  {
    return getPreviousSibling(paramNode, this.fRoot);
  }
  
  Node getPreviousSibling(Node paramNode1, Node paramNode2)
  {
    if ((paramNode1 == null) || (isSameNode(paramNode1, paramNode2))) {
      return null;
    }
    Node localNode1 = paramNode1.getPreviousSibling();
    if (localNode1 == null)
    {
      localNode1 = paramNode1.getParentNode();
      if ((localNode1 == null) || (isSameNode(localNode1, paramNode2))) {
        return null;
      }
      i = acceptNode(localNode1);
      if (i == 3) {
        return getPreviousSibling(localNode1, paramNode2);
      }
      return null;
    }
    int i = acceptNode(localNode1);
    if (i == 1) {
      return localNode1;
    }
    if (i == 3)
    {
      Node localNode2 = getLastChild(localNode1);
      if (localNode2 == null) {
        return getPreviousSibling(localNode1, paramNode2);
      }
      return localNode2;
    }
    return getPreviousSibling(localNode1, paramNode2);
  }
  
  Node getFirstChild(Node paramNode)
  {
    if (paramNode == null) {
      return null;
    }
    if ((!this.fEntityReferenceExpansion) && (paramNode.getNodeType() == 5)) {
      return null;
    }
    Node localNode1 = paramNode.getFirstChild();
    if (localNode1 == null) {
      return null;
    }
    int i = acceptNode(localNode1);
    if (i == 1) {
      return localNode1;
    }
    if ((i == 3) && (localNode1.hasChildNodes()))
    {
      Node localNode2 = getFirstChild(localNode1);
      if (localNode2 == null) {
        return getNextSibling(localNode1, paramNode);
      }
      return localNode2;
    }
    return getNextSibling(localNode1, paramNode);
  }
  
  Node getLastChild(Node paramNode)
  {
    if (paramNode == null) {
      return null;
    }
    if ((!this.fEntityReferenceExpansion) && (paramNode.getNodeType() == 5)) {
      return null;
    }
    Node localNode1 = paramNode.getLastChild();
    if (localNode1 == null) {
      return null;
    }
    int i = acceptNode(localNode1);
    if (i == 1) {
      return localNode1;
    }
    if ((i == 3) && (localNode1.hasChildNodes()))
    {
      Node localNode2 = getLastChild(localNode1);
      if (localNode2 == null) {
        return getPreviousSibling(localNode1, paramNode);
      }
      return localNode2;
    }
    return getPreviousSibling(localNode1, paramNode);
  }
  
  short acceptNode(Node paramNode)
  {
    if (this.fNodeFilter == null)
    {
      if ((this.fWhatToShow & 1 << paramNode.getNodeType() - 1) != 0) {
        return 1;
      }
      return 3;
    }
    if ((this.fWhatToShow & 1 << paramNode.getNodeType() - 1) != 0) {
      return this.fNodeFilter.acceptNode(paramNode);
    }
    return 3;
  }
  
  private boolean useIsSameNode(Node paramNode)
  {
    if ((paramNode instanceof NodeImpl)) {
      return false;
    }
    Document localDocument = paramNode.getNodeType() == 9 ? (Document)paramNode : paramNode.getOwnerDocument();
    return (localDocument != null) && (localDocument.getImplementation().hasFeature("Core", "3.0"));
  }
  
  private boolean isSameNode(Node paramNode1, Node paramNode2)
  {
    return paramNode1 == paramNode2 ? true : this.fUseIsSameNode ? paramNode1.isSameNode(paramNode2) : false;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.TreeWalkerImpl
 * JD-Core Version:    0.7.0.1
 */
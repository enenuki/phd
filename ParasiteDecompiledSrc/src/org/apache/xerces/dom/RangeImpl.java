package org.apache.xerces.dom;

import java.util.ArrayList;
import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ranges.Range;
import org.w3c.dom.ranges.RangeException;

public class RangeImpl
  implements Range
{
  private DocumentImpl fDocument;
  private Node fStartContainer;
  private Node fEndContainer;
  private int fStartOffset;
  private int fEndOffset;
  private boolean fDetach = false;
  private Node fInsertNode = null;
  private Node fDeleteNode = null;
  private Node fSplitNode = null;
  private boolean fInsertedFromRange = false;
  private Node fRemoveChild = null;
  static final int EXTRACT_CONTENTS = 1;
  static final int CLONE_CONTENTS = 2;
  static final int DELETE_CONTENTS = 3;
  
  public RangeImpl(DocumentImpl paramDocumentImpl)
  {
    this.fDocument = paramDocumentImpl;
    this.fStartContainer = paramDocumentImpl;
    this.fEndContainer = paramDocumentImpl;
    this.fStartOffset = 0;
    this.fEndOffset = 0;
    this.fDetach = false;
  }
  
  public Node getStartContainer()
  {
    if (this.fDetach) {
      throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
    }
    return this.fStartContainer;
  }
  
  public int getStartOffset()
  {
    if (this.fDetach) {
      throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
    }
    return this.fStartOffset;
  }
  
  public Node getEndContainer()
  {
    if (this.fDetach) {
      throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
    }
    return this.fEndContainer;
  }
  
  public int getEndOffset()
  {
    if (this.fDetach) {
      throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
    }
    return this.fEndOffset;
  }
  
  public boolean getCollapsed()
  {
    if (this.fDetach) {
      throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
    }
    return (this.fStartContainer == this.fEndContainer) && (this.fStartOffset == this.fEndOffset);
  }
  
  public Node getCommonAncestorContainer()
  {
    if (this.fDetach) {
      throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
    }
    ArrayList localArrayList1 = new ArrayList();
    for (Node localNode = this.fStartContainer; localNode != null; localNode = localNode.getParentNode()) {
      localArrayList1.add(localNode);
    }
    ArrayList localArrayList2 = new ArrayList();
    for (localNode = this.fEndContainer; localNode != null; localNode = localNode.getParentNode()) {
      localArrayList2.add(localNode);
    }
    int i = localArrayList1.size() - 1;
    int j = localArrayList2.size() - 1;
    Object localObject = null;
    while ((i >= 0) && (j >= 0))
    {
      if (localArrayList1.get(i) != localArrayList2.get(j)) {
        break;
      }
      localObject = localArrayList1.get(i);
      i--;
      j--;
    }
    return (Node)localObject;
  }
  
  public void setStart(Node paramNode, int paramInt)
    throws RangeException, DOMException
  {
    if (this.fDocument.errorChecking)
    {
      if (this.fDetach) {
        throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
      }
      if (!isLegalContainer(paramNode)) {
        throw new RangeExceptionImpl((short)2, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_NODE_TYPE_ERR", null));
      }
      if ((this.fDocument != paramNode.getOwnerDocument()) && (this.fDocument != paramNode)) {
        throw new DOMException((short)4, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null));
      }
    }
    checkIndex(paramNode, paramInt);
    this.fStartContainer = paramNode;
    this.fStartOffset = paramInt;
    if ((getCommonAncestorContainer() == null) || ((this.fStartContainer == this.fEndContainer) && (this.fEndOffset < this.fStartOffset))) {
      collapse(true);
    }
  }
  
  public void setEnd(Node paramNode, int paramInt)
    throws RangeException, DOMException
  {
    if (this.fDocument.errorChecking)
    {
      if (this.fDetach) {
        throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
      }
      if (!isLegalContainer(paramNode)) {
        throw new RangeExceptionImpl((short)2, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_NODE_TYPE_ERR", null));
      }
      if ((this.fDocument != paramNode.getOwnerDocument()) && (this.fDocument != paramNode)) {
        throw new DOMException((short)4, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null));
      }
    }
    checkIndex(paramNode, paramInt);
    this.fEndContainer = paramNode;
    this.fEndOffset = paramInt;
    if ((getCommonAncestorContainer() == null) || ((this.fStartContainer == this.fEndContainer) && (this.fEndOffset < this.fStartOffset))) {
      collapse(false);
    }
  }
  
  public void setStartBefore(Node paramNode)
    throws RangeException
  {
    if (this.fDocument.errorChecking)
    {
      if (this.fDetach) {
        throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
      }
      if ((!hasLegalRootContainer(paramNode)) || (!isLegalContainedNode(paramNode))) {
        throw new RangeExceptionImpl((short)2, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_NODE_TYPE_ERR", null));
      }
      if ((this.fDocument != paramNode.getOwnerDocument()) && (this.fDocument != paramNode)) {
        throw new DOMException((short)4, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null));
      }
    }
    this.fStartContainer = paramNode.getParentNode();
    int i = 0;
    for (Node localNode = paramNode; localNode != null; localNode = localNode.getPreviousSibling()) {
      i++;
    }
    this.fStartOffset = (i - 1);
    if ((getCommonAncestorContainer() == null) || ((this.fStartContainer == this.fEndContainer) && (this.fEndOffset < this.fStartOffset))) {
      collapse(true);
    }
  }
  
  public void setStartAfter(Node paramNode)
    throws RangeException
  {
    if (this.fDocument.errorChecking)
    {
      if (this.fDetach) {
        throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
      }
      if ((!hasLegalRootContainer(paramNode)) || (!isLegalContainedNode(paramNode))) {
        throw new RangeExceptionImpl((short)2, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_NODE_TYPE_ERR", null));
      }
      if ((this.fDocument != paramNode.getOwnerDocument()) && (this.fDocument != paramNode)) {
        throw new DOMException((short)4, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null));
      }
    }
    this.fStartContainer = paramNode.getParentNode();
    int i = 0;
    for (Node localNode = paramNode; localNode != null; localNode = localNode.getPreviousSibling()) {
      i++;
    }
    this.fStartOffset = i;
    if ((getCommonAncestorContainer() == null) || ((this.fStartContainer == this.fEndContainer) && (this.fEndOffset < this.fStartOffset))) {
      collapse(true);
    }
  }
  
  public void setEndBefore(Node paramNode)
    throws RangeException
  {
    if (this.fDocument.errorChecking)
    {
      if (this.fDetach) {
        throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
      }
      if ((!hasLegalRootContainer(paramNode)) || (!isLegalContainedNode(paramNode))) {
        throw new RangeExceptionImpl((short)2, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_NODE_TYPE_ERR", null));
      }
      if ((this.fDocument != paramNode.getOwnerDocument()) && (this.fDocument != paramNode)) {
        throw new DOMException((short)4, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null));
      }
    }
    this.fEndContainer = paramNode.getParentNode();
    int i = 0;
    for (Node localNode = paramNode; localNode != null; localNode = localNode.getPreviousSibling()) {
      i++;
    }
    this.fEndOffset = (i - 1);
    if ((getCommonAncestorContainer() == null) || ((this.fStartContainer == this.fEndContainer) && (this.fEndOffset < this.fStartOffset))) {
      collapse(false);
    }
  }
  
  public void setEndAfter(Node paramNode)
    throws RangeException
  {
    if (this.fDocument.errorChecking)
    {
      if (this.fDetach) {
        throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
      }
      if ((!hasLegalRootContainer(paramNode)) || (!isLegalContainedNode(paramNode))) {
        throw new RangeExceptionImpl((short)2, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_NODE_TYPE_ERR", null));
      }
      if ((this.fDocument != paramNode.getOwnerDocument()) && (this.fDocument != paramNode)) {
        throw new DOMException((short)4, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null));
      }
    }
    this.fEndContainer = paramNode.getParentNode();
    int i = 0;
    for (Node localNode = paramNode; localNode != null; localNode = localNode.getPreviousSibling()) {
      i++;
    }
    this.fEndOffset = i;
    if ((getCommonAncestorContainer() == null) || ((this.fStartContainer == this.fEndContainer) && (this.fEndOffset < this.fStartOffset))) {
      collapse(false);
    }
  }
  
  public void collapse(boolean paramBoolean)
  {
    if (this.fDetach) {
      throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
    }
    if (paramBoolean)
    {
      this.fEndContainer = this.fStartContainer;
      this.fEndOffset = this.fStartOffset;
    }
    else
    {
      this.fStartContainer = this.fEndContainer;
      this.fStartOffset = this.fEndOffset;
    }
  }
  
  public void selectNode(Node paramNode)
    throws RangeException
  {
    if (this.fDocument.errorChecking)
    {
      if (this.fDetach) {
        throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
      }
      if ((!isLegalContainer(paramNode.getParentNode())) || (!isLegalContainedNode(paramNode))) {
        throw new RangeExceptionImpl((short)2, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_NODE_TYPE_ERR", null));
      }
      if ((this.fDocument != paramNode.getOwnerDocument()) && (this.fDocument != paramNode)) {
        throw new DOMException((short)4, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null));
      }
    }
    Node localNode1 = paramNode.getParentNode();
    if (localNode1 != null)
    {
      this.fStartContainer = localNode1;
      this.fEndContainer = localNode1;
      int i = 0;
      for (Node localNode2 = paramNode; localNode2 != null; localNode2 = localNode2.getPreviousSibling()) {
        i++;
      }
      this.fStartOffset = (i - 1);
      this.fEndOffset = (this.fStartOffset + 1);
    }
  }
  
  public void selectNodeContents(Node paramNode)
    throws RangeException
  {
    if (this.fDocument.errorChecking)
    {
      if (this.fDetach) {
        throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
      }
      if (!isLegalContainer(paramNode)) {
        throw new RangeExceptionImpl((short)2, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_NODE_TYPE_ERR", null));
      }
      if ((this.fDocument != paramNode.getOwnerDocument()) && (this.fDocument != paramNode)) {
        throw new DOMException((short)4, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null));
      }
    }
    this.fStartContainer = paramNode;
    this.fEndContainer = paramNode;
    Node localNode1 = paramNode.getFirstChild();
    this.fStartOffset = 0;
    if (localNode1 == null)
    {
      this.fEndOffset = 0;
    }
    else
    {
      int i = 0;
      for (Node localNode2 = localNode1; localNode2 != null; localNode2 = localNode2.getNextSibling()) {
        i++;
      }
      this.fEndOffset = i;
    }
  }
  
  public short compareBoundaryPoints(short paramShort, Range paramRange)
    throws DOMException
  {
    if (this.fDocument.errorChecking)
    {
      if (this.fDetach) {
        throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
      }
      if (((this.fDocument != paramRange.getStartContainer().getOwnerDocument()) && (this.fDocument != paramRange.getStartContainer()) && (paramRange.getStartContainer() != null)) || ((this.fDocument != paramRange.getEndContainer().getOwnerDocument()) && (this.fDocument != paramRange.getEndContainer()) && (paramRange.getStartContainer() != null))) {
        throw new DOMException((short)4, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null));
      }
    }
    Object localObject1;
    Object localObject2;
    int i;
    int j;
    if (paramShort == 0)
    {
      localObject1 = paramRange.getStartContainer();
      localObject2 = this.fStartContainer;
      i = paramRange.getStartOffset();
      j = this.fStartOffset;
    }
    else if (paramShort == 1)
    {
      localObject1 = paramRange.getStartContainer();
      localObject2 = this.fEndContainer;
      i = paramRange.getStartOffset();
      j = this.fEndOffset;
    }
    else if (paramShort == 3)
    {
      localObject1 = paramRange.getEndContainer();
      localObject2 = this.fStartContainer;
      i = paramRange.getEndOffset();
      j = this.fStartOffset;
    }
    else
    {
      localObject1 = paramRange.getEndContainer();
      localObject2 = this.fEndContainer;
      i = paramRange.getEndOffset();
      j = this.fEndOffset;
    }
    if (localObject1 == localObject2)
    {
      if (i < j) {
        return 1;
      }
      if (i == j) {
        return 0;
      }
      return -1;
    }
    Object localObject3 = localObject2;
    for (Node localNode1 = localObject3.getParentNode(); localNode1 != null; localNode1 = localNode1.getParentNode())
    {
      if (localNode1 == localObject1)
      {
        int k = indexOf(localObject3, (Node)localObject1);
        if (i <= k) {
          return 1;
        }
        return -1;
      }
      localObject3 = localNode1;
    }
    Object localObject4 = localObject1;
    for (Node localNode2 = localObject4.getParentNode(); localNode2 != null; localNode2 = localNode2.getParentNode())
    {
      if (localNode2 == localObject2)
      {
        m = indexOf(localObject4, (Node)localObject2);
        if (m < j) {
          return 1;
        }
        return -1;
      }
      localObject4 = localNode2;
    }
    int m = 0;
    for (Object localObject5 = localObject1; localObject5 != null; localObject5 = ((Node)localObject5).getParentNode()) {
      m++;
    }
    for (Object localObject6 = localObject2; localObject6 != null; localObject6 = ((Node)localObject6).getParentNode()) {
      m--;
    }
    while (m > 0)
    {
      localObject1 = ((Node)localObject1).getParentNode();
      m--;
    }
    while (m < 0)
    {
      localObject2 = ((Node)localObject2).getParentNode();
      m++;
    }
    Node localNode3 = ((Node)localObject1).getParentNode();
    for (Node localNode4 = ((Node)localObject2).getParentNode(); localNode3 != localNode4; localNode4 = localNode4.getParentNode())
    {
      localObject1 = localNode3;
      localObject2 = localNode4;
      localNode3 = localNode3.getParentNode();
    }
    for (Node localNode5 = ((Node)localObject1).getNextSibling(); localNode5 != null; localNode5 = localNode5.getNextSibling()) {
      if (localNode5 == localObject2) {
        return 1;
      }
    }
    return -1;
  }
  
  public void deleteContents()
    throws DOMException
  {
    traverseContents(3);
  }
  
  public DocumentFragment extractContents()
    throws DOMException
  {
    return traverseContents(1);
  }
  
  public DocumentFragment cloneContents()
    throws DOMException
  {
    return traverseContents(2);
  }
  
  public void insertNode(Node paramNode)
    throws DOMException, RangeException
  {
    if (paramNode == null) {
      return;
    }
    int i = paramNode.getNodeType();
    if (this.fDocument.errorChecking)
    {
      if (this.fDetach) {
        throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
      }
      if (this.fDocument != paramNode.getOwnerDocument()) {
        throw new DOMException((short)4, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null));
      }
      if ((i == 2) || (i == 6) || (i == 12) || (i == 9)) {
        throw new RangeExceptionImpl((short)2, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_NODE_TYPE_ERR", null));
      }
    }
    int j = 0;
    this.fInsertedFromRange = true;
    if (this.fStartContainer.getNodeType() == 3)
    {
      Node localNode3 = this.fStartContainer.getParentNode();
      j = localNode3.getChildNodes().getLength();
      Node localNode1 = this.fStartContainer.cloneNode(false);
      ((TextImpl)localNode1).setNodeValueInternal(localNode1.getNodeValue().substring(this.fStartOffset));
      ((TextImpl)this.fStartContainer).setNodeValueInternal(this.fStartContainer.getNodeValue().substring(0, this.fStartOffset));
      Node localNode4 = this.fStartContainer.getNextSibling();
      if (localNode4 != null)
      {
        if (localNode3 != null)
        {
          localNode3.insertBefore(paramNode, localNode4);
          localNode3.insertBefore(localNode1, localNode4);
        }
      }
      else if (localNode3 != null)
      {
        localNode3.appendChild(paramNode);
        localNode3.appendChild(localNode1);
      }
      if (this.fEndContainer == this.fStartContainer)
      {
        this.fEndContainer = localNode1;
        this.fEndOffset -= this.fStartOffset;
      }
      else if (this.fEndContainer == localNode3)
      {
        this.fEndOffset += localNode3.getChildNodes().getLength() - j;
      }
      signalSplitData(this.fStartContainer, localNode1, this.fStartOffset);
    }
    else
    {
      if (this.fEndContainer == this.fStartContainer) {
        j = this.fEndContainer.getChildNodes().getLength();
      }
      Node localNode2 = this.fStartContainer.getFirstChild();
      int k = 0;
      for (k = 0; (k < this.fStartOffset) && (localNode2 != null); k++) {
        localNode2 = localNode2.getNextSibling();
      }
      if (localNode2 != null) {
        this.fStartContainer.insertBefore(paramNode, localNode2);
      } else {
        this.fStartContainer.appendChild(paramNode);
      }
      if ((this.fEndContainer == this.fStartContainer) && (this.fEndOffset != 0)) {
        this.fEndOffset += this.fEndContainer.getChildNodes().getLength() - j;
      }
    }
    this.fInsertedFromRange = false;
  }
  
  public void surroundContents(Node paramNode)
    throws DOMException, RangeException
  {
    if (paramNode == null) {
      return;
    }
    int i = paramNode.getNodeType();
    if (this.fDocument.errorChecking)
    {
      if (this.fDetach) {
        throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
      }
      if ((i == 2) || (i == 6) || (i == 12) || (i == 10) || (i == 9) || (i == 11)) {
        throw new RangeExceptionImpl((short)2, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_NODE_TYPE_ERR", null));
      }
    }
    Node localNode1 = this.fStartContainer;
    Node localNode2 = this.fEndContainer;
    if (this.fStartContainer.getNodeType() == 3) {
      localNode1 = this.fStartContainer.getParentNode();
    }
    if (this.fEndContainer.getNodeType() == 3) {
      localNode2 = this.fEndContainer.getParentNode();
    }
    if (localNode1 != localNode2) {
      throw new RangeExceptionImpl((short)1, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "BAD_BOUNDARYPOINTS_ERR", null));
    }
    DocumentFragment localDocumentFragment = extractContents();
    insertNode(paramNode);
    paramNode.appendChild(localDocumentFragment);
    selectNode(paramNode);
  }
  
  public Range cloneRange()
  {
    if (this.fDetach) {
      throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
    }
    Range localRange = this.fDocument.createRange();
    localRange.setStart(this.fStartContainer, this.fStartOffset);
    localRange.setEnd(this.fEndContainer, this.fEndOffset);
    return localRange;
  }
  
  public String toString()
  {
    if (this.fDetach) {
      throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
    }
    Node localNode1 = this.fStartContainer;
    Node localNode2 = this.fEndContainer;
    StringBuffer localStringBuffer = new StringBuffer();
    int i;
    if ((this.fStartContainer.getNodeType() == 3) || (this.fStartContainer.getNodeType() == 4))
    {
      if (this.fStartContainer == this.fEndContainer)
      {
        localStringBuffer.append(this.fStartContainer.getNodeValue().substring(this.fStartOffset, this.fEndOffset));
        return localStringBuffer.toString();
      }
      localStringBuffer.append(this.fStartContainer.getNodeValue().substring(this.fStartOffset));
      localNode1 = nextNode(localNode1, true);
    }
    else
    {
      localNode1 = localNode1.getFirstChild();
      if (this.fStartOffset > 0) {
        for (i = 0; (i < this.fStartOffset) && (localNode1 != null); i++) {
          localNode1 = localNode1.getNextSibling();
        }
      }
      if (localNode1 == null) {
        localNode1 = nextNode(this.fStartContainer, false);
      }
    }
    if ((this.fEndContainer.getNodeType() != 3) && (this.fEndContainer.getNodeType() != 4))
    {
      i = this.fEndOffset;
      for (localNode2 = this.fEndContainer.getFirstChild(); (i > 0) && (localNode2 != null); localNode2 = localNode2.getNextSibling()) {
        i--;
      }
      if (localNode2 == null) {
        localNode2 = nextNode(this.fEndContainer, false);
      }
    }
    while (localNode1 != localNode2)
    {
      if (localNode1 == null) {
        break;
      }
      if ((localNode1.getNodeType() == 3) || (localNode1.getNodeType() == 4)) {
        localStringBuffer.append(localNode1.getNodeValue());
      }
      localNode1 = nextNode(localNode1, true);
    }
    if ((this.fEndContainer.getNodeType() == 3) || (this.fEndContainer.getNodeType() == 4)) {
      localStringBuffer.append(this.fEndContainer.getNodeValue().substring(0, this.fEndOffset));
    }
    return localStringBuffer.toString();
  }
  
  public void detach()
  {
    if (this.fDetach) {
      throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
    }
    this.fDetach = true;
    this.fDocument.removeRange(this);
  }
  
  void signalSplitData(Node paramNode1, Node paramNode2, int paramInt)
  {
    this.fSplitNode = paramNode1;
    this.fDocument.splitData(paramNode1, paramNode2, paramInt);
    this.fSplitNode = null;
  }
  
  void receiveSplitData(Node paramNode1, Node paramNode2, int paramInt)
  {
    if ((paramNode1 == null) || (paramNode2 == null)) {
      return;
    }
    if (this.fSplitNode == paramNode1) {
      return;
    }
    if ((paramNode1 == this.fStartContainer) && (this.fStartContainer.getNodeType() == 3) && (this.fStartOffset > paramInt))
    {
      this.fStartOffset -= paramInt;
      this.fStartContainer = paramNode2;
    }
    if ((paramNode1 == this.fEndContainer) && (this.fEndContainer.getNodeType() == 3) && (this.fEndOffset > paramInt))
    {
      this.fEndOffset -= paramInt;
      this.fEndContainer = paramNode2;
    }
  }
  
  void deleteData(CharacterData paramCharacterData, int paramInt1, int paramInt2)
  {
    this.fDeleteNode = paramCharacterData;
    paramCharacterData.deleteData(paramInt1, paramInt2);
    this.fDeleteNode = null;
  }
  
  void receiveDeletedText(CharacterDataImpl paramCharacterDataImpl, int paramInt1, int paramInt2)
  {
    if (paramCharacterDataImpl == null) {
      return;
    }
    if (this.fDeleteNode == paramCharacterDataImpl) {
      return;
    }
    if (paramCharacterDataImpl == this.fStartContainer) {
      if (this.fStartOffset > paramInt1 + paramInt2) {
        this.fStartOffset = (paramInt1 + (this.fStartOffset - (paramInt1 + paramInt2)));
      } else if (this.fStartOffset > paramInt1) {
        this.fStartOffset = paramInt1;
      }
    }
    if (paramCharacterDataImpl == this.fEndContainer) {
      if (this.fEndOffset > paramInt1 + paramInt2) {
        this.fEndOffset = (paramInt1 + (this.fEndOffset - (paramInt1 + paramInt2)));
      } else if (this.fEndOffset > paramInt1) {
        this.fEndOffset = paramInt1;
      }
    }
  }
  
  void insertData(CharacterData paramCharacterData, int paramInt, String paramString)
  {
    this.fInsertNode = paramCharacterData;
    paramCharacterData.insertData(paramInt, paramString);
    this.fInsertNode = null;
  }
  
  void receiveInsertedText(CharacterDataImpl paramCharacterDataImpl, int paramInt1, int paramInt2)
  {
    if (paramCharacterDataImpl == null) {
      return;
    }
    if (this.fInsertNode == paramCharacterDataImpl) {
      return;
    }
    if ((paramCharacterDataImpl == this.fStartContainer) && (paramInt1 < this.fStartOffset)) {
      this.fStartOffset += paramInt2;
    }
    if ((paramCharacterDataImpl == this.fEndContainer) && (paramInt1 < this.fEndOffset)) {
      this.fEndOffset += paramInt2;
    }
  }
  
  void receiveReplacedText(CharacterDataImpl paramCharacterDataImpl)
  {
    if (paramCharacterDataImpl == null) {
      return;
    }
    if (paramCharacterDataImpl == this.fStartContainer) {
      this.fStartOffset = 0;
    }
    if (paramCharacterDataImpl == this.fEndContainer) {
      this.fEndOffset = 0;
    }
  }
  
  public void insertedNodeFromDOM(Node paramNode)
  {
    if (paramNode == null) {
      return;
    }
    if (this.fInsertNode == paramNode) {
      return;
    }
    if (this.fInsertedFromRange) {
      return;
    }
    Node localNode = paramNode.getParentNode();
    int i;
    if (localNode == this.fStartContainer)
    {
      i = indexOf(paramNode, this.fStartContainer);
      if (i < this.fStartOffset) {
        this.fStartOffset += 1;
      }
    }
    if (localNode == this.fEndContainer)
    {
      i = indexOf(paramNode, this.fEndContainer);
      if (i < this.fEndOffset) {
        this.fEndOffset += 1;
      }
    }
  }
  
  Node removeChild(Node paramNode1, Node paramNode2)
  {
    this.fRemoveChild = paramNode2;
    Node localNode = paramNode1.removeChild(paramNode2);
    this.fRemoveChild = null;
    return localNode;
  }
  
  void removeNode(Node paramNode)
  {
    if (paramNode == null) {
      return;
    }
    if (this.fRemoveChild == paramNode) {
      return;
    }
    Node localNode = paramNode.getParentNode();
    int i;
    if (localNode == this.fStartContainer)
    {
      i = indexOf(paramNode, this.fStartContainer);
      if (i < this.fStartOffset) {
        this.fStartOffset -= 1;
      }
    }
    if (localNode == this.fEndContainer)
    {
      i = indexOf(paramNode, this.fEndContainer);
      if (i < this.fEndOffset) {
        this.fEndOffset -= 1;
      }
    }
    if ((localNode != this.fStartContainer) || (localNode != this.fEndContainer))
    {
      if (isAncestorOf(paramNode, this.fStartContainer))
      {
        this.fStartContainer = localNode;
        this.fStartOffset = indexOf(paramNode, localNode);
      }
      if (isAncestorOf(paramNode, this.fEndContainer))
      {
        this.fEndContainer = localNode;
        this.fEndOffset = indexOf(paramNode, localNode);
      }
    }
  }
  
  private DocumentFragment traverseContents(int paramInt)
    throws DOMException
  {
    if ((this.fStartContainer == null) || (this.fEndContainer == null)) {
      return null;
    }
    if (this.fDetach) {
      throw new DOMException((short)11, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", null));
    }
    if (this.fStartContainer == this.fEndContainer) {
      return traverseSameContainer(paramInt);
    }
    int i = 0;
    Object localObject1 = this.fEndContainer;
    for (Node localNode1 = ((Node)localObject1).getParentNode(); localNode1 != null; localNode1 = localNode1.getParentNode())
    {
      if (localNode1 == this.fStartContainer) {
        return traverseCommonStartContainer((Node)localObject1, paramInt);
      }
      i++;
      localObject1 = localNode1;
    }
    int j = 0;
    Object localObject2 = this.fStartContainer;
    for (Node localNode2 = ((Node)localObject2).getParentNode(); localNode2 != null; localNode2 = localNode2.getParentNode())
    {
      if (localNode2 == this.fEndContainer) {
        return traverseCommonEndContainer((Node)localObject2, paramInt);
      }
      j++;
      localObject2 = localNode2;
    }
    int k = j - i;
    Object localObject3 = this.fStartContainer;
    while (k > 0)
    {
      localObject3 = ((Node)localObject3).getParentNode();
      k--;
    }
    Object localObject4 = this.fEndContainer;
    while (k < 0)
    {
      localObject4 = ((Node)localObject4).getParentNode();
      k++;
    }
    Node localNode3 = ((Node)localObject3).getParentNode();
    for (Node localNode4 = ((Node)localObject4).getParentNode(); localNode3 != localNode4; localNode4 = localNode4.getParentNode())
    {
      localObject3 = localNode3;
      localObject4 = localNode4;
      localNode3 = localNode3.getParentNode();
    }
    return traverseCommonAncestors((Node)localObject3, (Node)localObject4, paramInt);
  }
  
  private DocumentFragment traverseSameContainer(int paramInt)
  {
    DocumentFragment localDocumentFragment = null;
    if (paramInt != 3) {
      localDocumentFragment = this.fDocument.createDocumentFragment();
    }
    if (this.fStartOffset == this.fEndOffset) {
      return localDocumentFragment;
    }
    int i = this.fStartContainer.getNodeType();
    if ((i == 3) || (i == 4) || (i == 8) || (i == 7))
    {
      localObject = this.fStartContainer.getNodeValue();
      String str = ((String)localObject).substring(this.fStartOffset, this.fEndOffset);
      if (paramInt != 2)
      {
        ((CharacterDataImpl)this.fStartContainer).deleteData(this.fStartOffset, this.fEndOffset - this.fStartOffset);
        collapse(true);
      }
      if (paramInt == 3) {
        return null;
      }
      if (i == 3) {
        localDocumentFragment.appendChild(this.fDocument.createTextNode(str));
      } else if (i == 4) {
        localDocumentFragment.appendChild(this.fDocument.createCDATASection(str));
      } else if (i == 8) {
        localDocumentFragment.appendChild(this.fDocument.createComment(str));
      } else {
        localDocumentFragment.appendChild(this.fDocument.createProcessingInstruction(this.fStartContainer.getNodeName(), str));
      }
      return localDocumentFragment;
    }
    Object localObject = getSelectedNode(this.fStartContainer, this.fStartOffset);
    int j = this.fEndOffset - this.fStartOffset;
    while (j > 0)
    {
      Node localNode1 = ((Node)localObject).getNextSibling();
      Node localNode2 = traverseFullySelected((Node)localObject, paramInt);
      if (localDocumentFragment != null) {
        localDocumentFragment.appendChild(localNode2);
      }
      j--;
      localObject = localNode1;
    }
    if (paramInt != 2) {
      collapse(true);
    }
    return localDocumentFragment;
  }
  
  private DocumentFragment traverseCommonStartContainer(Node paramNode, int paramInt)
  {
    DocumentFragment localDocumentFragment = null;
    if (paramInt != 3) {
      localDocumentFragment = this.fDocument.createDocumentFragment();
    }
    Object localObject = traverseRightBoundary(paramNode, paramInt);
    if (localDocumentFragment != null) {
      localDocumentFragment.appendChild((Node)localObject);
    }
    int i = indexOf(paramNode, this.fStartContainer);
    int j = i - this.fStartOffset;
    if (j <= 0)
    {
      if (paramInt != 2)
      {
        setEndBefore(paramNode);
        collapse(false);
      }
      return localDocumentFragment;
    }
    Node localNode1;
    for (localObject = paramNode.getPreviousSibling(); j > 0; localObject = localNode1)
    {
      localNode1 = ((Node)localObject).getPreviousSibling();
      Node localNode2 = traverseFullySelected((Node)localObject, paramInt);
      if (localDocumentFragment != null) {
        localDocumentFragment.insertBefore(localNode2, localDocumentFragment.getFirstChild());
      }
      j--;
    }
    if (paramInt != 2)
    {
      setEndBefore(paramNode);
      collapse(false);
    }
    return localDocumentFragment;
  }
  
  private DocumentFragment traverseCommonEndContainer(Node paramNode, int paramInt)
  {
    DocumentFragment localDocumentFragment = null;
    if (paramInt != 3) {
      localDocumentFragment = this.fDocument.createDocumentFragment();
    }
    Object localObject = traverseLeftBoundary(paramNode, paramInt);
    if (localDocumentFragment != null) {
      localDocumentFragment.appendChild((Node)localObject);
    }
    int i = indexOf(paramNode, this.fEndContainer);
    i++;
    int j = this.fEndOffset - i;
    Node localNode1;
    for (localObject = paramNode.getNextSibling(); j > 0; localObject = localNode1)
    {
      localNode1 = ((Node)localObject).getNextSibling();
      Node localNode2 = traverseFullySelected((Node)localObject, paramInt);
      if (localDocumentFragment != null) {
        localDocumentFragment.appendChild(localNode2);
      }
      j--;
    }
    if (paramInt != 2)
    {
      setStartAfter(paramNode);
      collapse(true);
    }
    return localDocumentFragment;
  }
  
  private DocumentFragment traverseCommonAncestors(Node paramNode1, Node paramNode2, int paramInt)
  {
    DocumentFragment localDocumentFragment = null;
    if (paramInt != 3) {
      localDocumentFragment = this.fDocument.createDocumentFragment();
    }
    Node localNode1 = traverseLeftBoundary(paramNode1, paramInt);
    if (localDocumentFragment != null) {
      localDocumentFragment.appendChild(localNode1);
    }
    Node localNode2 = paramNode1.getParentNode();
    int i = indexOf(paramNode1, localNode2);
    int j = indexOf(paramNode2, localNode2);
    i++;
    int k = j - i;
    Object localObject = paramNode1.getNextSibling();
    while (k > 0)
    {
      Node localNode3 = ((Node)localObject).getNextSibling();
      localNode1 = traverseFullySelected((Node)localObject, paramInt);
      if (localDocumentFragment != null) {
        localDocumentFragment.appendChild(localNode1);
      }
      localObject = localNode3;
      k--;
    }
    localNode1 = traverseRightBoundary(paramNode2, paramInt);
    if (localDocumentFragment != null) {
      localDocumentFragment.appendChild(localNode1);
    }
    if (paramInt != 2)
    {
      setStartAfter(paramNode1);
      collapse(true);
    }
    return localDocumentFragment;
  }
  
  private Node traverseRightBoundary(Node paramNode, int paramInt)
  {
    Object localObject1 = getSelectedNode(this.fEndContainer, this.fEndOffset - 1);
    boolean bool = localObject1 != this.fEndContainer;
    if (localObject1 == paramNode) {
      return traverseNode((Node)localObject1, bool, false, paramInt);
    }
    Node localNode1 = ((Node)localObject1).getParentNode();
    Object localObject2 = traverseNode(localNode1, false, false, paramInt);
    while (localNode1 != null)
    {
      while (localObject1 != null)
      {
        localNode2 = ((Node)localObject1).getPreviousSibling();
        Node localNode3 = traverseNode((Node)localObject1, bool, false, paramInt);
        if (paramInt != 3) {
          ((Node)localObject2).insertBefore(localNode3, ((Node)localObject2).getFirstChild());
        }
        bool = true;
        localObject1 = localNode2;
      }
      if (localNode1 == paramNode) {
        return localObject2;
      }
      localObject1 = localNode1.getPreviousSibling();
      localNode1 = localNode1.getParentNode();
      Node localNode2 = traverseNode(localNode1, false, false, paramInt);
      if (paramInt != 3) {
        localNode2.appendChild((Node)localObject2);
      }
      localObject2 = localNode2;
    }
    return null;
  }
  
  private Node traverseLeftBoundary(Node paramNode, int paramInt)
  {
    Object localObject1 = getSelectedNode(getStartContainer(), getStartOffset());
    boolean bool = localObject1 != getStartContainer();
    if (localObject1 == paramNode) {
      return traverseNode((Node)localObject1, bool, true, paramInt);
    }
    Node localNode1 = ((Node)localObject1).getParentNode();
    Object localObject2 = traverseNode(localNode1, false, true, paramInt);
    while (localNode1 != null)
    {
      while (localObject1 != null)
      {
        localNode2 = ((Node)localObject1).getNextSibling();
        Node localNode3 = traverseNode((Node)localObject1, bool, true, paramInt);
        if (paramInt != 3) {
          ((Node)localObject2).appendChild(localNode3);
        }
        bool = true;
        localObject1 = localNode2;
      }
      if (localNode1 == paramNode) {
        return localObject2;
      }
      localObject1 = localNode1.getNextSibling();
      localNode1 = localNode1.getParentNode();
      Node localNode2 = traverseNode(localNode1, false, true, paramInt);
      if (paramInt != 3) {
        localNode2.appendChild((Node)localObject2);
      }
      localObject2 = localNode2;
    }
    return null;
  }
  
  private Node traverseNode(Node paramNode, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    if (paramBoolean1) {
      return traverseFullySelected(paramNode, paramInt);
    }
    int i = paramNode.getNodeType();
    if ((i == 3) || (i == 4) || (i == 8) || (i == 7)) {
      return traverseCharacterDataNode(paramNode, paramBoolean2, paramInt);
    }
    return traversePartiallySelected(paramNode, paramInt);
  }
  
  private Node traverseFullySelected(Node paramNode, int paramInt)
  {
    switch (paramInt)
    {
    case 2: 
      return paramNode.cloneNode(true);
    case 1: 
      if (paramNode.getNodeType() == 10) {
        throw new DOMException((short)3, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "HIERARCHY_REQUEST_ERR", null));
      }
      return paramNode;
    case 3: 
      paramNode.getParentNode().removeChild(paramNode);
      return null;
    }
    return null;
  }
  
  private Node traversePartiallySelected(Node paramNode, int paramInt)
  {
    switch (paramInt)
    {
    case 3: 
      return null;
    case 1: 
    case 2: 
      return paramNode.cloneNode(false);
    }
    return null;
  }
  
  private Node traverseCharacterDataNode(Node paramNode, boolean paramBoolean, int paramInt)
  {
    String str1 = paramNode.getNodeValue();
    int i;
    String str2;
    String str3;
    if (paramBoolean)
    {
      i = getStartOffset();
      str2 = str1.substring(i);
      str3 = str1.substring(0, i);
    }
    else
    {
      i = getEndOffset();
      str2 = str1.substring(0, i);
      str3 = str1.substring(i);
    }
    if (paramInt != 2) {
      paramNode.setNodeValue(str3);
    }
    if (paramInt == 3) {
      return null;
    }
    Node localNode = paramNode.cloneNode(false);
    localNode.setNodeValue(str2);
    return localNode;
  }
  
  void checkIndex(Node paramNode, int paramInt)
    throws DOMException
  {
    if (paramInt < 0) {
      throw new DOMException((short)1, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INDEX_SIZE_ERR", null));
    }
    int i = paramNode.getNodeType();
    if ((i == 3) || (i == 4) || (i == 8) || (i == 7))
    {
      if (paramInt > paramNode.getNodeValue().length()) {
        throw new DOMException((short)1, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INDEX_SIZE_ERR", null));
      }
    }
    else if (paramInt > paramNode.getChildNodes().getLength()) {
      throw new DOMException((short)1, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INDEX_SIZE_ERR", null));
    }
  }
  
  private Node getRootContainer(Node paramNode)
  {
    if (paramNode == null) {
      return null;
    }
    while (paramNode.getParentNode() != null) {
      paramNode = paramNode.getParentNode();
    }
    return paramNode;
  }
  
  private boolean isLegalContainer(Node paramNode)
  {
    if (paramNode == null) {
      return false;
    }
    while (paramNode != null)
    {
      switch (paramNode.getNodeType())
      {
      case 6: 
      case 10: 
      case 12: 
        return false;
      }
      paramNode = paramNode.getParentNode();
    }
    return true;
  }
  
  private boolean hasLegalRootContainer(Node paramNode)
  {
    if (paramNode == null) {
      return false;
    }
    Node localNode = getRootContainer(paramNode);
    switch (localNode.getNodeType())
    {
    case 2: 
    case 9: 
    case 11: 
      return true;
    }
    return false;
  }
  
  private boolean isLegalContainedNode(Node paramNode)
  {
    if (paramNode == null) {
      return false;
    }
    switch (paramNode.getNodeType())
    {
    case 2: 
    case 6: 
    case 9: 
    case 11: 
    case 12: 
      return false;
    }
    return true;
  }
  
  Node nextNode(Node paramNode, boolean paramBoolean)
  {
    if (paramNode == null) {
      return null;
    }
    if (paramBoolean)
    {
      localNode1 = paramNode.getFirstChild();
      if (localNode1 != null) {
        return localNode1;
      }
    }
    Node localNode1 = paramNode.getNextSibling();
    if (localNode1 != null) {
      return localNode1;
    }
    for (Node localNode2 = paramNode.getParentNode(); (localNode2 != null) && (localNode2 != this.fDocument); localNode2 = localNode2.getParentNode())
    {
      localNode1 = localNode2.getNextSibling();
      if (localNode1 != null) {
        return localNode1;
      }
    }
    return null;
  }
  
  boolean isAncestorOf(Node paramNode1, Node paramNode2)
  {
    for (Node localNode = paramNode2; localNode != null; localNode = localNode.getParentNode()) {
      if (localNode == paramNode1) {
        return true;
      }
    }
    return false;
  }
  
  int indexOf(Node paramNode1, Node paramNode2)
  {
    if (paramNode1.getParentNode() != paramNode2) {
      return -1;
    }
    int i = 0;
    for (Node localNode = paramNode2.getFirstChild(); localNode != paramNode1; localNode = localNode.getNextSibling()) {
      i++;
    }
    return i;
  }
  
  private Node getSelectedNode(Node paramNode, int paramInt)
  {
    if (paramNode.getNodeType() == 3) {
      return paramNode;
    }
    if (paramInt < 0) {
      return paramNode;
    }
    for (Node localNode = paramNode.getFirstChild(); (localNode != null) && (paramInt > 0); localNode = localNode.getNextSibling()) {
      paramInt--;
    }
    if (localNode != null) {
      return localNode;
    }
    return paramNode;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.RangeImpl
 * JD-Core Version:    0.7.0.1
 */
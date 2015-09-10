package org.apache.xerces.dom;

import java.util.Vector;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public class AttributeMap
  extends NamedNodeMapImpl
{
  static final long serialVersionUID = 8872606282138665383L;
  
  protected AttributeMap(ElementImpl paramElementImpl, NamedNodeMapImpl paramNamedNodeMapImpl)
  {
    super(paramElementImpl);
    if (paramNamedNodeMapImpl != null)
    {
      cloneContent(paramNamedNodeMapImpl);
      if (this.nodes != null) {
        hasDefaults(true);
      }
    }
  }
  
  public Node setNamedItem(Node paramNode)
    throws DOMException
  {
    boolean bool = this.ownerNode.ownerDocument().errorChecking;
    if (bool)
    {
      if (isReadOnly())
      {
        localObject = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NO_MODIFICATION_ALLOWED_ERR", null);
        throw new DOMException((short)7, (String)localObject);
      }
      if (paramNode.getOwnerDocument() != this.ownerNode.ownerDocument())
      {
        localObject = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null);
        throw new DOMException((short)4, (String)localObject);
      }
      if (paramNode.getNodeType() != 2)
      {
        localObject = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "HIERARCHY_REQUEST_ERR", null);
        throw new DOMException((short)3, (String)localObject);
      }
    }
    Object localObject = (AttrImpl)paramNode;
    if (((NodeImpl)localObject).isOwned())
    {
      if ((bool) && (((AttrImpl)localObject).getOwnerElement() != this.ownerNode))
      {
        String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INUSE_ATTRIBUTE_ERR", null);
        throw new DOMException((short)10, str);
      }
      return paramNode;
    }
    ((NodeImpl)localObject).ownerNode = this.ownerNode;
    ((NodeImpl)localObject).isOwned(true);
    int i = findNamePoint(((AttrImpl)localObject).getNodeName(), 0);
    AttrImpl localAttrImpl = null;
    if (i >= 0)
    {
      localAttrImpl = (AttrImpl)this.nodes.elementAt(i);
      this.nodes.setElementAt(paramNode, i);
      localAttrImpl.ownerNode = this.ownerNode.ownerDocument();
      localAttrImpl.isOwned(false);
      localAttrImpl.isSpecified(true);
    }
    else
    {
      i = -1 - i;
      if (null == this.nodes) {
        this.nodes = new Vector(5, 10);
      }
      this.nodes.insertElementAt(paramNode, i);
    }
    this.ownerNode.ownerDocument().setAttrNode((AttrImpl)localObject, localAttrImpl);
    if (!((NodeImpl)localObject).isNormalized()) {
      this.ownerNode.isNormalized(false);
    }
    return localAttrImpl;
  }
  
  public Node setNamedItemNS(Node paramNode)
    throws DOMException
  {
    boolean bool = this.ownerNode.ownerDocument().errorChecking;
    if (bool)
    {
      if (isReadOnly())
      {
        localObject = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NO_MODIFICATION_ALLOWED_ERR", null);
        throw new DOMException((short)7, (String)localObject);
      }
      if (paramNode.getOwnerDocument() != this.ownerNode.ownerDocument())
      {
        localObject = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null);
        throw new DOMException((short)4, (String)localObject);
      }
      if (paramNode.getNodeType() != 2)
      {
        localObject = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "HIERARCHY_REQUEST_ERR", null);
        throw new DOMException((short)3, (String)localObject);
      }
    }
    Object localObject = (AttrImpl)paramNode;
    if (((NodeImpl)localObject).isOwned())
    {
      if ((bool) && (((AttrImpl)localObject).getOwnerElement() != this.ownerNode))
      {
        String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INUSE_ATTRIBUTE_ERR", null);
        throw new DOMException((short)10, str);
      }
      return paramNode;
    }
    ((NodeImpl)localObject).ownerNode = this.ownerNode;
    ((NodeImpl)localObject).isOwned(true);
    int i = findNamePoint(((NodeImpl)localObject).getNamespaceURI(), ((NodeImpl)localObject).getLocalName());
    AttrImpl localAttrImpl = null;
    if (i >= 0)
    {
      localAttrImpl = (AttrImpl)this.nodes.elementAt(i);
      this.nodes.setElementAt(paramNode, i);
      localAttrImpl.ownerNode = this.ownerNode.ownerDocument();
      localAttrImpl.isOwned(false);
      localAttrImpl.isSpecified(true);
    }
    else
    {
      i = findNamePoint(paramNode.getNodeName(), 0);
      if (i >= 0)
      {
        localAttrImpl = (AttrImpl)this.nodes.elementAt(i);
        this.nodes.insertElementAt(paramNode, i);
      }
      else
      {
        i = -1 - i;
        if (null == this.nodes) {
          this.nodes = new Vector(5, 10);
        }
        this.nodes.insertElementAt(paramNode, i);
      }
    }
    this.ownerNode.ownerDocument().setAttrNode((AttrImpl)localObject, localAttrImpl);
    if (!((NodeImpl)localObject).isNormalized()) {
      this.ownerNode.isNormalized(false);
    }
    return localAttrImpl;
  }
  
  public Node removeNamedItem(String paramString)
    throws DOMException
  {
    return internalRemoveNamedItem(paramString, true);
  }
  
  Node safeRemoveNamedItem(String paramString)
  {
    return internalRemoveNamedItem(paramString, false);
  }
  
  protected Node removeItem(Node paramNode, boolean paramBoolean)
    throws DOMException
  {
    int i = -1;
    if (this.nodes != null) {
      for (int j = 0; j < this.nodes.size(); j++) {
        if (this.nodes.elementAt(j) == paramNode)
        {
          i = j;
          break;
        }
      }
    }
    if (i < 0)
    {
      String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_FOUND_ERR", null);
      throw new DOMException((short)8, str);
    }
    return remove((AttrImpl)paramNode, i, paramBoolean);
  }
  
  protected final Node internalRemoveNamedItem(String paramString, boolean paramBoolean)
  {
    if (isReadOnly())
    {
      String str1 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NO_MODIFICATION_ALLOWED_ERR", null);
      throw new DOMException((short)7, str1);
    }
    int i = findNamePoint(paramString, 0);
    if (i < 0)
    {
      if (paramBoolean)
      {
        String str2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_FOUND_ERR", null);
        throw new DOMException((short)8, str2);
      }
      return null;
    }
    return remove((AttrImpl)this.nodes.elementAt(i), i, true);
  }
  
  private final Node remove(AttrImpl paramAttrImpl, int paramInt, boolean paramBoolean)
  {
    CoreDocumentImpl localCoreDocumentImpl = this.ownerNode.ownerDocument();
    String str = paramAttrImpl.getNodeName();
    if (paramAttrImpl.isIdAttribute()) {
      localCoreDocumentImpl.removeIdentifier(paramAttrImpl.getValue());
    }
    if ((hasDefaults()) && (paramBoolean))
    {
      NamedNodeMapImpl localNamedNodeMapImpl = ((ElementImpl)this.ownerNode).getDefaultAttributes();
      Node localNode;
      if ((localNamedNodeMapImpl != null) && ((localNode = localNamedNodeMapImpl.getNamedItem(str)) != null) && (findNamePoint(str, paramInt + 1) < 0))
      {
        NodeImpl localNodeImpl = (NodeImpl)localNode.cloneNode(true);
        if (localNode.getLocalName() != null) {
          ((AttrNSImpl)localNodeImpl).namespaceURI = paramAttrImpl.getNamespaceURI();
        }
        localNodeImpl.ownerNode = this.ownerNode;
        localNodeImpl.isOwned(true);
        localNodeImpl.isSpecified(false);
        this.nodes.setElementAt(localNodeImpl, paramInt);
        if (paramAttrImpl.isIdAttribute()) {
          localCoreDocumentImpl.putIdentifier(localNodeImpl.getNodeValue(), (ElementImpl)this.ownerNode);
        }
      }
      else
      {
        this.nodes.removeElementAt(paramInt);
      }
    }
    else
    {
      this.nodes.removeElementAt(paramInt);
    }
    paramAttrImpl.ownerNode = localCoreDocumentImpl;
    paramAttrImpl.isOwned(false);
    paramAttrImpl.isSpecified(true);
    paramAttrImpl.isIdAttribute(false);
    localCoreDocumentImpl.removedAttrNode(paramAttrImpl, this.ownerNode, str);
    return paramAttrImpl;
  }
  
  public Node removeNamedItemNS(String paramString1, String paramString2)
    throws DOMException
  {
    return internalRemoveNamedItemNS(paramString1, paramString2, true);
  }
  
  Node safeRemoveNamedItemNS(String paramString1, String paramString2)
  {
    return internalRemoveNamedItemNS(paramString1, paramString2, false);
  }
  
  protected final Node internalRemoveNamedItemNS(String paramString1, String paramString2, boolean paramBoolean)
  {
    CoreDocumentImpl localCoreDocumentImpl = this.ownerNode.ownerDocument();
    if ((localCoreDocumentImpl.errorChecking) && (isReadOnly()))
    {
      String str1 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NO_MODIFICATION_ALLOWED_ERR", null);
      throw new DOMException((short)7, str1);
    }
    int i = findNamePoint(paramString1, paramString2);
    if (i < 0)
    {
      if (paramBoolean)
      {
        localObject = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_FOUND_ERR", null);
        throw new DOMException((short)8, (String)localObject);
      }
      return null;
    }
    Object localObject = (AttrImpl)this.nodes.elementAt(i);
    if (((NodeImpl)localObject).isIdAttribute()) {
      localCoreDocumentImpl.removeIdentifier(((AttrImpl)localObject).getValue());
    }
    String str2 = ((AttrImpl)localObject).getNodeName();
    if (hasDefaults())
    {
      NamedNodeMapImpl localNamedNodeMapImpl = ((ElementImpl)this.ownerNode).getDefaultAttributes();
      Node localNode;
      if ((localNamedNodeMapImpl != null) && ((localNode = localNamedNodeMapImpl.getNamedItem(str2)) != null))
      {
        int j = findNamePoint(str2, 0);
        if ((j >= 0) && (findNamePoint(str2, j + 1) < 0))
        {
          NodeImpl localNodeImpl = (NodeImpl)localNode.cloneNode(true);
          localNodeImpl.ownerNode = this.ownerNode;
          if (localNode.getLocalName() != null) {
            ((AttrNSImpl)localNodeImpl).namespaceURI = paramString1;
          }
          localNodeImpl.isOwned(true);
          localNodeImpl.isSpecified(false);
          this.nodes.setElementAt(localNodeImpl, i);
          if (localNodeImpl.isIdAttribute()) {
            localCoreDocumentImpl.putIdentifier(localNodeImpl.getNodeValue(), (ElementImpl)this.ownerNode);
          }
        }
        else
        {
          this.nodes.removeElementAt(i);
        }
      }
      else
      {
        this.nodes.removeElementAt(i);
      }
    }
    else
    {
      this.nodes.removeElementAt(i);
    }
    ((NodeImpl)localObject).ownerNode = localCoreDocumentImpl;
    ((NodeImpl)localObject).isOwned(false);
    ((NodeImpl)localObject).isSpecified(true);
    ((NodeImpl)localObject).isIdAttribute(false);
    localCoreDocumentImpl.removedAttrNode((AttrImpl)localObject, this.ownerNode, paramString2);
    return localObject;
  }
  
  public NamedNodeMapImpl cloneMap(NodeImpl paramNodeImpl)
  {
    AttributeMap localAttributeMap = new AttributeMap((ElementImpl)paramNodeImpl, null);
    localAttributeMap.hasDefaults(hasDefaults());
    localAttributeMap.cloneContent(this);
    return localAttributeMap;
  }
  
  protected void cloneContent(NamedNodeMapImpl paramNamedNodeMapImpl)
  {
    Vector localVector = paramNamedNodeMapImpl.nodes;
    if (localVector != null)
    {
      int i = localVector.size();
      if (i != 0)
      {
        if (this.nodes == null) {
          this.nodes = new Vector(i);
        }
        this.nodes.setSize(i);
        for (int j = 0; j < i; j++)
        {
          NodeImpl localNodeImpl1 = (NodeImpl)localVector.elementAt(j);
          NodeImpl localNodeImpl2 = (NodeImpl)localNodeImpl1.cloneNode(true);
          localNodeImpl2.isSpecified(localNodeImpl1.isSpecified());
          this.nodes.setElementAt(localNodeImpl2, j);
          localNodeImpl2.ownerNode = this.ownerNode;
          localNodeImpl2.isOwned(true);
        }
      }
    }
  }
  
  void moveSpecifiedAttributes(AttributeMap paramAttributeMap)
  {
    int i = paramAttributeMap.nodes != null ? paramAttributeMap.nodes.size() : 0;
    for (int j = i - 1; j >= 0; j--)
    {
      AttrImpl localAttrImpl = (AttrImpl)paramAttributeMap.nodes.elementAt(j);
      if (localAttrImpl.isSpecified())
      {
        paramAttributeMap.remove(localAttrImpl, j, false);
        if (localAttrImpl.getLocalName() != null) {
          setNamedItem(localAttrImpl);
        } else {
          setNamedItemNS(localAttrImpl);
        }
      }
    }
  }
  
  protected void reconcileDefaults(NamedNodeMapImpl paramNamedNodeMapImpl)
  {
    int i = this.nodes != null ? this.nodes.size() : 0;
    for (int j = i - 1; j >= 0; j--)
    {
      AttrImpl localAttrImpl1 = (AttrImpl)this.nodes.elementAt(j);
      if (!localAttrImpl1.isSpecified()) {
        remove(localAttrImpl1, j, false);
      }
    }
    if (paramNamedNodeMapImpl == null) {
      return;
    }
    if ((this.nodes == null) || (this.nodes.size() == 0))
    {
      cloneContent(paramNamedNodeMapImpl);
    }
    else
    {
      int k = paramNamedNodeMapImpl.nodes.size();
      for (int m = 0; m < k; m++)
      {
        AttrImpl localAttrImpl2 = (AttrImpl)paramNamedNodeMapImpl.nodes.elementAt(m);
        int n = findNamePoint(localAttrImpl2.getNodeName(), 0);
        if (n < 0)
        {
          n = -1 - n;
          NodeImpl localNodeImpl = (NodeImpl)localAttrImpl2.cloneNode(true);
          localNodeImpl.ownerNode = this.ownerNode;
          localNodeImpl.isOwned(true);
          localNodeImpl.isSpecified(false);
          this.nodes.insertElementAt(localNodeImpl, n);
        }
      }
    }
  }
  
  protected final int addItem(Node paramNode)
  {
    AttrImpl localAttrImpl = (AttrImpl)paramNode;
    localAttrImpl.ownerNode = this.ownerNode;
    localAttrImpl.isOwned(true);
    int i = findNamePoint(localAttrImpl.getNamespaceURI(), localAttrImpl.getLocalName());
    if (i >= 0)
    {
      this.nodes.setElementAt(paramNode, i);
    }
    else
    {
      i = findNamePoint(localAttrImpl.getNodeName(), 0);
      if (i >= 0)
      {
        this.nodes.insertElementAt(paramNode, i);
      }
      else
      {
        i = -1 - i;
        if (null == this.nodes) {
          this.nodes = new Vector(5, 10);
        }
        this.nodes.insertElementAt(paramNode, i);
      }
    }
    this.ownerNode.ownerDocument().setAttrNode(localAttrImpl, null);
    return i;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.AttributeMap
 * JD-Core Version:    0.7.0.1
 */
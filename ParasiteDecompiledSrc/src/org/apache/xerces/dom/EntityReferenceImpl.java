package org.apache.xerces.dom;

import org.apache.xerces.util.URI;
import org.apache.xerces.util.URI.MalformedURIException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class EntityReferenceImpl
  extends ParentNode
  implements EntityReference
{
  static final long serialVersionUID = -7381452955687102062L;
  protected String name;
  protected String baseURI;
  
  public EntityReferenceImpl(CoreDocumentImpl paramCoreDocumentImpl, String paramString)
  {
    super(paramCoreDocumentImpl);
    this.name = paramString;
    isReadOnly(true);
    needsSyncChildren(true);
  }
  
  public short getNodeType()
  {
    return 5;
  }
  
  public String getNodeName()
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    return this.name;
  }
  
  public Node cloneNode(boolean paramBoolean)
  {
    EntityReferenceImpl localEntityReferenceImpl = (EntityReferenceImpl)super.cloneNode(paramBoolean);
    localEntityReferenceImpl.setReadOnly(true, paramBoolean);
    return localEntityReferenceImpl;
  }
  
  public String getBaseURI()
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    if (this.baseURI == null)
    {
      DocumentType localDocumentType;
      NamedNodeMap localNamedNodeMap;
      if ((null != (localDocumentType = getOwnerDocument().getDoctype())) && (null != (localNamedNodeMap = localDocumentType.getEntities())))
      {
        EntityImpl localEntityImpl = (EntityImpl)localNamedNodeMap.getNamedItem(getNodeName());
        if (localEntityImpl != null) {
          return localEntityImpl.getBaseURI();
        }
      }
    }
    else if ((this.baseURI != null) && (this.baseURI.length() != 0))
    {
      try
      {
        return new URI(this.baseURI).toString();
      }
      catch (URI.MalformedURIException localMalformedURIException)
      {
        return null;
      }
    }
    return this.baseURI;
  }
  
  public void setBaseURI(String paramString)
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    this.baseURI = paramString;
  }
  
  protected String getEntityRefValue()
  {
    if (needsSyncChildren()) {
      synchronizeChildren();
    }
    String str = "";
    if (this.firstChild != null)
    {
      if (this.firstChild.getNodeType() == 5) {
        str = ((EntityReferenceImpl)this.firstChild).getEntityRefValue();
      } else if (this.firstChild.getNodeType() == 3) {
        str = this.firstChild.getNodeValue();
      } else {
        return null;
      }
      if (this.firstChild.nextSibling == null) {
        return str;
      }
      StringBuffer localStringBuffer = new StringBuffer(str);
      for (ChildNode localChildNode = this.firstChild.nextSibling; localChildNode != null; localChildNode = localChildNode.nextSibling)
      {
        if (localChildNode.getNodeType() == 5) {
          str = ((EntityReferenceImpl)localChildNode).getEntityRefValue();
        } else if (localChildNode.getNodeType() == 3) {
          str = localChildNode.getNodeValue();
        } else {
          return null;
        }
        localStringBuffer.append(str);
      }
      return localStringBuffer.toString();
    }
    return "";
  }
  
  protected void synchronizeChildren()
  {
    needsSyncChildren(false);
    DocumentType localDocumentType;
    NamedNodeMap localNamedNodeMap;
    if ((null != (localDocumentType = getOwnerDocument().getDoctype())) && (null != (localNamedNodeMap = localDocumentType.getEntities())))
    {
      EntityImpl localEntityImpl = (EntityImpl)localNamedNodeMap.getNamedItem(getNodeName());
      if (localEntityImpl == null) {
        return;
      }
      isReadOnly(false);
      for (Node localNode1 = localEntityImpl.getFirstChild(); localNode1 != null; localNode1 = localNode1.getNextSibling())
      {
        Node localNode2 = localNode1.cloneNode(true);
        insertBefore(localNode2, null);
      }
      setReadOnly(true, true);
    }
  }
  
  public void setReadOnly(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    if (paramBoolean2)
    {
      if (needsSyncChildren()) {
        synchronizeChildren();
      }
      for (ChildNode localChildNode = this.firstChild; localChildNode != null; localChildNode = localChildNode.nextSibling) {
        localChildNode.setReadOnly(paramBoolean1, true);
      }
    }
    isReadOnly(paramBoolean1);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.EntityReferenceImpl
 * JD-Core Version:    0.7.0.1
 */
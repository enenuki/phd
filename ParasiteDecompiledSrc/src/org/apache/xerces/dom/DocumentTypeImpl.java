package org.apache.xerces.dom;

import java.util.Hashtable;
import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.UserDataHandler;

public class DocumentTypeImpl
  extends ParentNode
  implements DocumentType
{
  static final long serialVersionUID = 7751299192316526485L;
  protected String name;
  protected NamedNodeMapImpl entities;
  protected NamedNodeMapImpl notations;
  protected NamedNodeMapImpl elements;
  protected String publicID;
  protected String systemID;
  protected String internalSubset;
  private int doctypeNumber = 0;
  private Hashtable userData = null;
  
  public DocumentTypeImpl(CoreDocumentImpl paramCoreDocumentImpl, String paramString)
  {
    super(paramCoreDocumentImpl);
    this.name = paramString;
    this.entities = new NamedNodeMapImpl(this);
    this.notations = new NamedNodeMapImpl(this);
    this.elements = new NamedNodeMapImpl(this);
  }
  
  public DocumentTypeImpl(CoreDocumentImpl paramCoreDocumentImpl, String paramString1, String paramString2, String paramString3)
  {
    this(paramCoreDocumentImpl, paramString1);
    this.publicID = paramString2;
    this.systemID = paramString3;
  }
  
  public String getPublicId()
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    return this.publicID;
  }
  
  public String getSystemId()
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    return this.systemID;
  }
  
  public void setInternalSubset(String paramString)
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    this.internalSubset = paramString;
  }
  
  public String getInternalSubset()
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    return this.internalSubset;
  }
  
  public short getNodeType()
  {
    return 10;
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
    DocumentTypeImpl localDocumentTypeImpl = (DocumentTypeImpl)super.cloneNode(paramBoolean);
    localDocumentTypeImpl.entities = this.entities.cloneMap(localDocumentTypeImpl);
    localDocumentTypeImpl.notations = this.notations.cloneMap(localDocumentTypeImpl);
    localDocumentTypeImpl.elements = this.elements.cloneMap(localDocumentTypeImpl);
    return localDocumentTypeImpl;
  }
  
  public String getTextContent()
    throws DOMException
  {
    return null;
  }
  
  public void setTextContent(String paramString)
    throws DOMException
  {}
  
  public boolean isEqualNode(Node paramNode)
  {
    if (!super.isEqualNode(paramNode)) {
      return false;
    }
    if (needsSyncData()) {
      synchronizeData();
    }
    DocumentTypeImpl localDocumentTypeImpl = (DocumentTypeImpl)paramNode;
    if (((getPublicId() == null) && (localDocumentTypeImpl.getPublicId() != null)) || ((getPublicId() != null) && (localDocumentTypeImpl.getPublicId() == null)) || ((getSystemId() == null) && (localDocumentTypeImpl.getSystemId() != null)) || ((getSystemId() != null) && (localDocumentTypeImpl.getSystemId() == null)) || ((getInternalSubset() == null) && (localDocumentTypeImpl.getInternalSubset() != null)) || ((getInternalSubset() != null) && (localDocumentTypeImpl.getInternalSubset() == null))) {
      return false;
    }
    if ((getPublicId() != null) && (!getPublicId().equals(localDocumentTypeImpl.getPublicId()))) {
      return false;
    }
    if ((getSystemId() != null) && (!getSystemId().equals(localDocumentTypeImpl.getSystemId()))) {
      return false;
    }
    if ((getInternalSubset() != null) && (!getInternalSubset().equals(localDocumentTypeImpl.getInternalSubset()))) {
      return false;
    }
    NamedNodeMapImpl localNamedNodeMapImpl1 = localDocumentTypeImpl.entities;
    if (((this.entities == null) && (localNamedNodeMapImpl1 != null)) || ((this.entities != null) && (localNamedNodeMapImpl1 == null))) {
      return false;
    }
    Node localNode2;
    if ((this.entities != null) && (localNamedNodeMapImpl1 != null))
    {
      if (this.entities.getLength() != localNamedNodeMapImpl1.getLength()) {
        return false;
      }
      for (int i = 0; this.entities.item(i) != null; i++)
      {
        Node localNode1 = this.entities.item(i);
        localNode2 = localNamedNodeMapImpl1.getNamedItem(localNode1.getNodeName());
        if (!((NodeImpl)localNode1).isEqualNode(localNode2)) {
          return false;
        }
      }
    }
    NamedNodeMapImpl localNamedNodeMapImpl2 = localDocumentTypeImpl.notations;
    if (((this.notations == null) && (localNamedNodeMapImpl2 != null)) || ((this.notations != null) && (localNamedNodeMapImpl2 == null))) {
      return false;
    }
    if ((this.notations != null) && (localNamedNodeMapImpl2 != null))
    {
      if (this.notations.getLength() != localNamedNodeMapImpl2.getLength()) {
        return false;
      }
      for (int j = 0; this.notations.item(j) != null; j++)
      {
        localNode2 = this.notations.item(j);
        Node localNode3 = localNamedNodeMapImpl2.getNamedItem(localNode2.getNodeName());
        if (!((NodeImpl)localNode2).isEqualNode(localNode3)) {
          return false;
        }
      }
    }
    return true;
  }
  
  protected void setOwnerDocument(CoreDocumentImpl paramCoreDocumentImpl)
  {
    super.setOwnerDocument(paramCoreDocumentImpl);
    this.entities.setOwnerDocument(paramCoreDocumentImpl);
    this.notations.setOwnerDocument(paramCoreDocumentImpl);
    this.elements.setOwnerDocument(paramCoreDocumentImpl);
  }
  
  protected int getNodeNumber()
  {
    if (getOwnerDocument() != null) {
      return super.getNodeNumber();
    }
    if (this.doctypeNumber == 0)
    {
      CoreDOMImplementationImpl localCoreDOMImplementationImpl = (CoreDOMImplementationImpl)CoreDOMImplementationImpl.getDOMImplementation();
      this.doctypeNumber = localCoreDOMImplementationImpl.assignDocTypeNumber();
    }
    return this.doctypeNumber;
  }
  
  public String getName()
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    return this.name;
  }
  
  public NamedNodeMap getEntities()
  {
    if (needsSyncChildren()) {
      synchronizeChildren();
    }
    return this.entities;
  }
  
  public NamedNodeMap getNotations()
  {
    if (needsSyncChildren()) {
      synchronizeChildren();
    }
    return this.notations;
  }
  
  public void setReadOnly(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (needsSyncChildren()) {
      synchronizeChildren();
    }
    super.setReadOnly(paramBoolean1, paramBoolean2);
    this.elements.setReadOnly(paramBoolean1, true);
    this.entities.setReadOnly(paramBoolean1, true);
    this.notations.setReadOnly(paramBoolean1, true);
  }
  
  public NamedNodeMap getElements()
  {
    if (needsSyncChildren()) {
      synchronizeChildren();
    }
    return this.elements;
  }
  
  public Object setUserData(String paramString, Object paramObject, UserDataHandler paramUserDataHandler)
  {
    if (this.userData == null) {
      this.userData = new Hashtable();
    }
    ParentNode.UserDataRecord localUserDataRecord;
    if (paramObject == null)
    {
      if (this.userData != null)
      {
        localObject = this.userData.remove(paramString);
        if (localObject != null)
        {
          localUserDataRecord = (ParentNode.UserDataRecord)localObject;
          return localUserDataRecord.fData;
        }
      }
      return null;
    }
    Object localObject = this.userData.put(paramString, new ParentNode.UserDataRecord(this, paramObject, paramUserDataHandler));
    if (localObject != null)
    {
      localUserDataRecord = (ParentNode.UserDataRecord)localObject;
      return localUserDataRecord.fData;
    }
    return null;
  }
  
  public Object getUserData(String paramString)
  {
    if (this.userData == null) {
      return null;
    }
    Object localObject = this.userData.get(paramString);
    if (localObject != null)
    {
      ParentNode.UserDataRecord localUserDataRecord = (ParentNode.UserDataRecord)localObject;
      return localUserDataRecord.fData;
    }
    return null;
  }
  
  protected Hashtable getUserDataRecord()
  {
    return this.userData;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.DocumentTypeImpl
 * JD-Core Version:    0.7.0.1
 */
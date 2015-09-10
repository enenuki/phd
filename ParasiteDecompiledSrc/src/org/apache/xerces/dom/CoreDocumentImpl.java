package org.apache.xerces.dom;

import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.Hashtable;
import org.apache.xerces.util.URI;
import org.apache.xerces.util.URI.MalformedURIException;
import org.apache.xerces.util.XML11Char;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.xni.NamespaceContext;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Notation;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

public class CoreDocumentImpl
  extends ParentNode
  implements Document
{
  static final long serialVersionUID = 0L;
  protected DocumentTypeImpl docType;
  protected ElementImpl docElement;
  transient NodeListCache fFreeNLCache;
  protected String encoding;
  protected String actualEncoding;
  protected String version;
  protected boolean standalone;
  protected String fDocumentURI;
  protected Hashtable userData;
  protected Hashtable identifiers;
  transient DOMNormalizer domNormalizer = null;
  transient DOMConfigurationImpl fConfiguration = null;
  transient Object fXPathEvaluator = null;
  private static final int[] kidOK = new int[13];
  protected int changes = 0;
  protected boolean allowGrammarAccess;
  protected boolean errorChecking = true;
  protected boolean xmlVersionChanged = false;
  private int documentNumber = 0;
  private int nodeCounter = 0;
  private Hashtable nodeTable;
  private boolean xml11Version = false;
  
  public CoreDocumentImpl()
  {
    this(false);
  }
  
  public CoreDocumentImpl(boolean paramBoolean)
  {
    super(null);
    this.ownerDocument = this;
    this.allowGrammarAccess = paramBoolean;
  }
  
  public CoreDocumentImpl(DocumentType paramDocumentType)
  {
    this(paramDocumentType, false);
  }
  
  public CoreDocumentImpl(DocumentType paramDocumentType, boolean paramBoolean)
  {
    this(paramBoolean);
    if (paramDocumentType != null)
    {
      DocumentTypeImpl localDocumentTypeImpl;
      try
      {
        localDocumentTypeImpl = (DocumentTypeImpl)paramDocumentType;
      }
      catch (ClassCastException localClassCastException)
      {
        String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null);
        throw new DOMException((short)4, str);
      }
      localDocumentTypeImpl.ownerDocument = this;
      appendChild(paramDocumentType);
    }
  }
  
  public final Document getOwnerDocument()
  {
    return null;
  }
  
  public short getNodeType()
  {
    return 9;
  }
  
  public String getNodeName()
  {
    return "#document";
  }
  
  public Node cloneNode(boolean paramBoolean)
  {
    CoreDocumentImpl localCoreDocumentImpl = new CoreDocumentImpl();
    callUserDataHandlers(this, localCoreDocumentImpl, (short)1);
    cloneNode(localCoreDocumentImpl, paramBoolean);
    return localCoreDocumentImpl;
  }
  
  protected void cloneNode(CoreDocumentImpl paramCoreDocumentImpl, boolean paramBoolean)
  {
    if (needsSyncChildren()) {
      synchronizeChildren();
    }
    if (paramBoolean)
    {
      Hashtable localHashtable = null;
      if (this.identifiers != null)
      {
        localHashtable = new Hashtable();
        localObject1 = this.identifiers.keys();
        while (((Enumeration)localObject1).hasMoreElements())
        {
          Object localObject2 = ((Enumeration)localObject1).nextElement();
          localHashtable.put(this.identifiers.get(localObject2), localObject2);
        }
      }
      for (Object localObject1 = this.firstChild; localObject1 != null; localObject1 = ((ChildNode)localObject1).nextSibling) {
        paramCoreDocumentImpl.appendChild(paramCoreDocumentImpl.importNode((Node)localObject1, true, true, localHashtable));
      }
    }
    paramCoreDocumentImpl.allowGrammarAccess = this.allowGrammarAccess;
    paramCoreDocumentImpl.errorChecking = this.errorChecking;
  }
  
  public Node insertBefore(Node paramNode1, Node paramNode2)
    throws DOMException
  {
    int i = paramNode1.getNodeType();
    if (this.errorChecking)
    {
      if (needsSyncChildren()) {
        synchronizeChildren();
      }
      if (((i == 1) && (this.docElement != null)) || ((i == 10) && (this.docType != null)))
      {
        String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "HIERARCHY_REQUEST_ERR", null);
        throw new DOMException((short)3, str);
      }
    }
    if ((paramNode1.getOwnerDocument() == null) && ((paramNode1 instanceof DocumentTypeImpl))) {
      ((DocumentTypeImpl)paramNode1).ownerDocument = this;
    }
    super.insertBefore(paramNode1, paramNode2);
    if (i == 1) {
      this.docElement = ((ElementImpl)paramNode1);
    } else if (i == 10) {
      this.docType = ((DocumentTypeImpl)paramNode1);
    }
    return paramNode1;
  }
  
  public Node removeChild(Node paramNode)
    throws DOMException
  {
    super.removeChild(paramNode);
    int i = paramNode.getNodeType();
    if (i == 1) {
      this.docElement = null;
    } else if (i == 10) {
      this.docType = null;
    }
    return paramNode;
  }
  
  public Node replaceChild(Node paramNode1, Node paramNode2)
    throws DOMException
  {
    if ((paramNode1.getOwnerDocument() == null) && ((paramNode1 instanceof DocumentTypeImpl))) {
      ((DocumentTypeImpl)paramNode1).ownerDocument = this;
    }
    if ((this.errorChecking) && (((this.docType != null) && (paramNode2.getNodeType() != 10) && (paramNode1.getNodeType() == 10)) || ((this.docElement != null) && (paramNode2.getNodeType() != 1) && (paramNode1.getNodeType() == 1)))) {
      throw new DOMException((short)3, DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "HIERARCHY_REQUEST_ERR", null));
    }
    super.replaceChild(paramNode1, paramNode2);
    int i = paramNode2.getNodeType();
    if (i == 1) {
      this.docElement = ((ElementImpl)paramNode1);
    } else if (i == 10) {
      this.docType = ((DocumentTypeImpl)paramNode1);
    }
    return paramNode2;
  }
  
  public String getTextContent()
    throws DOMException
  {
    return null;
  }
  
  public void setTextContent(String paramString)
    throws DOMException
  {}
  
  public Object getFeature(String paramString1, String paramString2)
  {
    int i = (paramString2 == null) || (paramString2.length() == 0) ? 1 : 0;
    if ((paramString1.equalsIgnoreCase("+XPath")) && ((i != 0) || (paramString2.equals("3.0"))))
    {
      if (this.fXPathEvaluator != null) {
        return this.fXPathEvaluator;
      }
      try
      {
        Class localClass = ObjectFactory.findProviderClass("org.apache.xpath.domapi.XPathEvaluatorImpl", ObjectFactory.findClassLoader(), true);
        Constructor localConstructor = localClass.getConstructor(new Class[] { Document.class });
        Class[] arrayOfClass = localClass.getInterfaces();
        for (int j = 0; j < arrayOfClass.length; j++) {
          if (arrayOfClass[j].getName().equals("org.w3c.dom.xpath.XPathEvaluator"))
          {
            this.fXPathEvaluator = localConstructor.newInstance(new Object[] { this });
            return this.fXPathEvaluator;
          }
        }
        return null;
      }
      catch (Exception localException)
      {
        return null;
      }
    }
    return super.getFeature(paramString1, paramString2);
  }
  
  public Attr createAttribute(String paramString)
    throws DOMException
  {
    if ((this.errorChecking) && (!isXMLName(paramString, this.xml11Version)))
    {
      String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
      throw new DOMException((short)5, str);
    }
    return new AttrImpl(this, paramString);
  }
  
  public CDATASection createCDATASection(String paramString)
    throws DOMException
  {
    return new CDATASectionImpl(this, paramString);
  }
  
  public Comment createComment(String paramString)
  {
    return new CommentImpl(this, paramString);
  }
  
  public DocumentFragment createDocumentFragment()
  {
    return new DocumentFragmentImpl(this);
  }
  
  public Element createElement(String paramString)
    throws DOMException
  {
    if ((this.errorChecking) && (!isXMLName(paramString, this.xml11Version)))
    {
      String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
      throw new DOMException((short)5, str);
    }
    return new ElementImpl(this, paramString);
  }
  
  public EntityReference createEntityReference(String paramString)
    throws DOMException
  {
    if ((this.errorChecking) && (!isXMLName(paramString, this.xml11Version)))
    {
      String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
      throw new DOMException((short)5, str);
    }
    return new EntityReferenceImpl(this, paramString);
  }
  
  public ProcessingInstruction createProcessingInstruction(String paramString1, String paramString2)
    throws DOMException
  {
    if ((this.errorChecking) && (!isXMLName(paramString1, this.xml11Version)))
    {
      String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
      throw new DOMException((short)5, str);
    }
    return new ProcessingInstructionImpl(this, paramString1, paramString2);
  }
  
  public Text createTextNode(String paramString)
  {
    return new TextImpl(this, paramString);
  }
  
  public DocumentType getDoctype()
  {
    if (needsSyncChildren()) {
      synchronizeChildren();
    }
    return this.docType;
  }
  
  public Element getDocumentElement()
  {
    if (needsSyncChildren()) {
      synchronizeChildren();
    }
    return this.docElement;
  }
  
  public NodeList getElementsByTagName(String paramString)
  {
    return new DeepNodeListImpl(this, paramString);
  }
  
  public DOMImplementation getImplementation()
  {
    return CoreDOMImplementationImpl.getDOMImplementation();
  }
  
  public void setErrorChecking(boolean paramBoolean)
  {
    this.errorChecking = paramBoolean;
  }
  
  public void setStrictErrorChecking(boolean paramBoolean)
  {
    this.errorChecking = paramBoolean;
  }
  
  public boolean getErrorChecking()
  {
    return this.errorChecking;
  }
  
  public boolean getStrictErrorChecking()
  {
    return this.errorChecking;
  }
  
  public String getInputEncoding()
  {
    return this.actualEncoding;
  }
  
  public void setInputEncoding(String paramString)
  {
    this.actualEncoding = paramString;
  }
  
  public void setXmlEncoding(String paramString)
  {
    this.encoding = paramString;
  }
  
  /**
   * @deprecated
   */
  public void setEncoding(String paramString)
  {
    setXmlEncoding(paramString);
  }
  
  public String getXmlEncoding()
  {
    return this.encoding;
  }
  
  /**
   * @deprecated
   */
  public String getEncoding()
  {
    return getXmlEncoding();
  }
  
  public void setXmlVersion(String paramString)
  {
    if ((paramString.equals("1.0")) || (paramString.equals("1.1")))
    {
      if (!getXmlVersion().equals(paramString))
      {
        this.xmlVersionChanged = true;
        isNormalized(false);
        this.version = paramString;
      }
    }
    else
    {
      String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
      throw new DOMException((short)9, str);
    }
    if (getXmlVersion().equals("1.1")) {
      this.xml11Version = true;
    } else {
      this.xml11Version = false;
    }
  }
  
  /**
   * @deprecated
   */
  public void setVersion(String paramString)
  {
    setXmlVersion(paramString);
  }
  
  public String getXmlVersion()
  {
    return this.version == null ? "1.0" : this.version;
  }
  
  /**
   * @deprecated
   */
  public String getVersion()
  {
    return getXmlVersion();
  }
  
  public void setXmlStandalone(boolean paramBoolean)
    throws DOMException
  {
    this.standalone = paramBoolean;
  }
  
  /**
   * @deprecated
   */
  public void setStandalone(boolean paramBoolean)
  {
    setXmlStandalone(paramBoolean);
  }
  
  public boolean getXmlStandalone()
  {
    return this.standalone;
  }
  
  /**
   * @deprecated
   */
  public boolean getStandalone()
  {
    return getXmlStandalone();
  }
  
  public String getDocumentURI()
  {
    return this.fDocumentURI;
  }
  
  public Node renameNode(Node paramNode, String paramString1, String paramString2)
    throws DOMException
  {
    if ((this.errorChecking) && (paramNode.getOwnerDocument() != this) && (paramNode != this))
    {
      localObject1 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null);
      throw new DOMException((short)4, (String)localObject1);
    }
    Object localObject3;
    Object localObject2;
    Object localObject4;
    Node localNode1;
    switch (paramNode.getNodeType())
    {
    case 1: 
      localObject1 = (ElementImpl)paramNode;
      if ((localObject1 instanceof ElementNSImpl))
      {
        ((ElementNSImpl)localObject1).rename(paramString1, paramString2);
        callUserDataHandlers((Node)localObject1, null, (short)4);
      }
      else if (paramString1 == null)
      {
        if (this.errorChecking)
        {
          int i = paramString2.indexOf(':');
          if (i != -1)
          {
            localObject3 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
            throw new DOMException((short)14, (String)localObject3);
          }
          if (!isXMLName(paramString2, this.xml11Version))
          {
            localObject3 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
            throw new DOMException((short)5, (String)localObject3);
          }
        }
        ((ElementImpl)localObject1).rename(paramString2);
        callUserDataHandlers((Node)localObject1, null, (short)4);
      }
      else
      {
        localObject2 = new ElementNSImpl(this, paramString1, paramString2);
        copyEventListeners((NodeImpl)localObject1, (NodeImpl)localObject2);
        localObject3 = removeUserDataTable((Node)localObject1);
        localObject4 = ((ChildNode)localObject1).getParentNode();
        localNode1 = ((ChildNode)localObject1).getNextSibling();
        if (localObject4 != null) {
          ((Node)localObject4).removeChild((Node)localObject1);
        }
        for (Node localNode2 = ((ParentNode)localObject1).getFirstChild(); localNode2 != null; localNode2 = ((ParentNode)localObject1).getFirstChild())
        {
          ((ParentNode)localObject1).removeChild(localNode2);
          ((NodeImpl)localObject2).appendChild(localNode2);
        }
        ((ElementImpl)localObject2).moveSpecifiedAttributes((ElementImpl)localObject1);
        setUserDataTable((Node)localObject2, (Hashtable)localObject3);
        callUserDataHandlers((Node)localObject1, (Node)localObject2, (short)4);
        if (localObject4 != null) {
          ((Node)localObject4).insertBefore((Node)localObject2, localNode1);
        }
        localObject1 = localObject2;
      }
      renamedElement((Element)paramNode, (Element)localObject1);
      return localObject1;
    case 2: 
      localObject1 = (AttrImpl)paramNode;
      localObject2 = ((AttrImpl)localObject1).getOwnerElement();
      if (localObject2 != null) {
        ((Element)localObject2).removeAttributeNode((Attr)localObject1);
      }
      if ((paramNode instanceof AttrNSImpl))
      {
        ((AttrNSImpl)localObject1).rename(paramString1, paramString2);
        if (localObject2 != null) {
          ((Element)localObject2).setAttributeNodeNS((Attr)localObject1);
        }
        callUserDataHandlers((Node)localObject1, null, (short)4);
      }
      else if (paramString1 == null)
      {
        ((AttrImpl)localObject1).rename(paramString2);
        if (localObject2 != null) {
          ((Element)localObject2).setAttributeNode((Attr)localObject1);
        }
        callUserDataHandlers((Node)localObject1, null, (short)4);
      }
      else
      {
        localObject3 = new AttrNSImpl(this, paramString1, paramString2);
        copyEventListeners((NodeImpl)localObject1, (NodeImpl)localObject3);
        localObject4 = removeUserDataTable((Node)localObject1);
        for (localNode1 = ((AttrImpl)localObject1).getFirstChild(); localNode1 != null; localNode1 = ((AttrImpl)localObject1).getFirstChild())
        {
          ((AttrImpl)localObject1).removeChild(localNode1);
          ((NodeImpl)localObject3).appendChild(localNode1);
        }
        setUserDataTable((Node)localObject3, (Hashtable)localObject4);
        callUserDataHandlers((Node)localObject1, (Node)localObject3, (short)4);
        if (localObject2 != null) {
          ((Element)localObject2).setAttributeNode((Attr)localObject3);
        }
        localObject1 = localObject3;
      }
      renamedAttrNode((Attr)paramNode, (Attr)localObject1);
      return localObject1;
    }
    Object localObject1 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
    throw new DOMException((short)9, (String)localObject1);
  }
  
  public void normalizeDocument()
  {
    if ((isNormalized()) && (!isNormalizeDocRequired())) {
      return;
    }
    if (needsSyncChildren()) {
      synchronizeChildren();
    }
    if (this.domNormalizer == null) {
      this.domNormalizer = new DOMNormalizer();
    }
    if (this.fConfiguration == null) {
      this.fConfiguration = new DOMConfigurationImpl();
    } else {
      this.fConfiguration.reset();
    }
    this.domNormalizer.normalizeDocument(this, this.fConfiguration);
    isNormalized(true);
    this.xmlVersionChanged = false;
  }
  
  public DOMConfiguration getDomConfig()
  {
    if (this.fConfiguration == null) {
      this.fConfiguration = new DOMConfigurationImpl();
    }
    return this.fConfiguration;
  }
  
  public String getBaseURI()
  {
    if ((this.fDocumentURI != null) && (this.fDocumentURI.length() != 0)) {
      try
      {
        return new URI(this.fDocumentURI).toString();
      }
      catch (URI.MalformedURIException localMalformedURIException)
      {
        return null;
      }
    }
    return this.fDocumentURI;
  }
  
  public void setDocumentURI(String paramString)
  {
    this.fDocumentURI = paramString;
  }
  
  public boolean getAsync()
  {
    return false;
  }
  
  public void setAsync(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
      throw new DOMException((short)9, str);
    }
  }
  
  public void abort() {}
  
  public boolean load(String paramString)
  {
    return false;
  }
  
  public boolean loadXML(String paramString)
  {
    return false;
  }
  
  public String saveXML(Node paramNode)
    throws DOMException
  {
    if ((this.errorChecking) && (paramNode != null) && (this != paramNode.getOwnerDocument()))
    {
      localObject = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null);
      throw new DOMException((short)4, (String)localObject);
    }
    Object localObject = (DOMImplementationLS)DOMImplementationImpl.getDOMImplementation();
    LSSerializer localLSSerializer = ((DOMImplementationLS)localObject).createLSSerializer();
    if (paramNode == null) {
      paramNode = this;
    }
    return localLSSerializer.writeToString(paramNode);
  }
  
  void setMutationEvents(boolean paramBoolean) {}
  
  boolean getMutationEvents()
  {
    return false;
  }
  
  public DocumentType createDocumentType(String paramString1, String paramString2, String paramString3)
    throws DOMException
  {
    return new DocumentTypeImpl(this, paramString1, paramString2, paramString3);
  }
  
  public Entity createEntity(String paramString)
    throws DOMException
  {
    if ((this.errorChecking) && (!isXMLName(paramString, this.xml11Version)))
    {
      String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
      throw new DOMException((short)5, str);
    }
    return new EntityImpl(this, paramString);
  }
  
  public Notation createNotation(String paramString)
    throws DOMException
  {
    if ((this.errorChecking) && (!isXMLName(paramString, this.xml11Version)))
    {
      String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
      throw new DOMException((short)5, str);
    }
    return new NotationImpl(this, paramString);
  }
  
  public ElementDefinitionImpl createElementDefinition(String paramString)
    throws DOMException
  {
    if ((this.errorChecking) && (!isXMLName(paramString, this.xml11Version)))
    {
      String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
      throw new DOMException((short)5, str);
    }
    return new ElementDefinitionImpl(this, paramString);
  }
  
  protected int getNodeNumber()
  {
    if (this.documentNumber == 0)
    {
      CoreDOMImplementationImpl localCoreDOMImplementationImpl = (CoreDOMImplementationImpl)CoreDOMImplementationImpl.getDOMImplementation();
      this.documentNumber = localCoreDOMImplementationImpl.assignDocumentNumber();
    }
    return this.documentNumber;
  }
  
  protected int getNodeNumber(Node paramNode)
  {
    int i;
    if (this.nodeTable == null)
    {
      this.nodeTable = new Hashtable();
      i = --this.nodeCounter;
      this.nodeTable.put(paramNode, new Integer(i));
    }
    else
    {
      Integer localInteger = (Integer)this.nodeTable.get(paramNode);
      if (localInteger == null)
      {
        i = --this.nodeCounter;
        this.nodeTable.put(paramNode, new Integer(i));
      }
      else
      {
        i = localInteger.intValue();
      }
    }
    return i;
  }
  
  public Node importNode(Node paramNode, boolean paramBoolean)
    throws DOMException
  {
    return importNode(paramNode, paramBoolean, false, null);
  }
  
  private Node importNode(Node paramNode, boolean paramBoolean1, boolean paramBoolean2, Hashtable paramHashtable)
    throws DOMException
  {
    Object localObject1 = null;
    Hashtable localHashtable = null;
    if ((paramNode instanceof NodeImpl)) {
      localHashtable = ((NodeImpl)paramNode).getUserDataRecord();
    }
    int i = paramNode.getNodeType();
    Object localObject2;
    NamedNodeMap localNamedNodeMap;
    int k;
    Object localObject4;
    Object localObject3;
    switch (i)
    {
    case 1: 
      boolean bool = paramNode.getOwnerDocument().getImplementation().hasFeature("XML", "2.0");
      if ((!bool) || (paramNode.getLocalName() == null)) {
        localObject2 = createElement(paramNode.getNodeName());
      } else {
        localObject2 = createElementNS(paramNode.getNamespaceURI(), paramNode.getNodeName());
      }
      localNamedNodeMap = paramNode.getAttributes();
      if (localNamedNodeMap != null)
      {
        int j = localNamedNodeMap.getLength();
        for (k = 0; k < j; k++)
        {
          Attr localAttr1 = (Attr)localNamedNodeMap.item(k);
          if ((localAttr1.getSpecified()) || (paramBoolean2))
          {
            Attr localAttr2 = (Attr)importNode(localAttr1, true, paramBoolean2, paramHashtable);
            if ((!bool) || (localAttr1.getLocalName() == null)) {
              ((Element)localObject2).setAttributeNode(localAttr2);
            } else {
              ((Element)localObject2).setAttributeNodeNS(localAttr2);
            }
          }
        }
      }
      if (paramHashtable != null)
      {
        localObject4 = paramHashtable.get(paramNode);
        if (localObject4 != null)
        {
          if (this.identifiers == null) {
            this.identifiers = new Hashtable();
          }
          this.identifiers.put(localObject4, localObject2);
        }
      }
      localObject1 = localObject2;
      break;
    case 2: 
      if (paramNode.getOwnerDocument().getImplementation().hasFeature("XML", "2.0"))
      {
        if (paramNode.getLocalName() == null) {
          localObject1 = createAttribute(paramNode.getNodeName());
        } else {
          localObject1 = createAttributeNS(paramNode.getNamespaceURI(), paramNode.getNodeName());
        }
      }
      else {
        localObject1 = createAttribute(paramNode.getNodeName());
      }
      if ((paramNode instanceof AttrImpl))
      {
        localObject2 = (AttrImpl)paramNode;
        if (((NodeImpl)localObject2).hasStringValue())
        {
          localObject3 = (AttrImpl)localObject1;
          ((AttrImpl)localObject3).setValue(((AttrImpl)localObject2).getValue());
          paramBoolean1 = false;
        }
        else
        {
          paramBoolean1 = true;
        }
      }
      else if (paramNode.getFirstChild() == null)
      {
        ((Node)localObject1).setNodeValue(paramNode.getNodeValue());
        paramBoolean1 = false;
      }
      else
      {
        paramBoolean1 = true;
      }
      break;
    case 3: 
      localObject1 = createTextNode(paramNode.getNodeValue());
      break;
    case 4: 
      localObject1 = createCDATASection(paramNode.getNodeValue());
      break;
    case 5: 
      localObject1 = createEntityReference(paramNode.getNodeName());
      paramBoolean1 = false;
      break;
    case 6: 
      localObject2 = (Entity)paramNode;
      localObject3 = (EntityImpl)createEntity(paramNode.getNodeName());
      ((EntityImpl)localObject3).setPublicId(((Entity)localObject2).getPublicId());
      ((EntityImpl)localObject3).setSystemId(((Entity)localObject2).getSystemId());
      ((EntityImpl)localObject3).setNotationName(((Entity)localObject2).getNotationName());
      ((NodeImpl)localObject3).isReadOnly(false);
      localObject1 = localObject3;
      break;
    case 7: 
      localObject1 = createProcessingInstruction(paramNode.getNodeName(), paramNode.getNodeValue());
      break;
    case 8: 
      localObject1 = createComment(paramNode.getNodeValue());
      break;
    case 10: 
      if (!paramBoolean2)
      {
        localObject2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
        throw new DOMException((short)9, (String)localObject2);
      }
      localObject2 = (DocumentType)paramNode;
      localObject3 = (DocumentTypeImpl)createDocumentType(((Node)localObject2).getNodeName(), ((DocumentType)localObject2).getPublicId(), ((DocumentType)localObject2).getSystemId());
      ((DocumentTypeImpl)localObject3).setInternalSubset(((DocumentType)localObject2).getInternalSubset());
      localNamedNodeMap = ((DocumentType)localObject2).getEntities();
      localObject4 = ((DocumentTypeImpl)localObject3).getEntities();
      if (localNamedNodeMap != null) {
        for (k = 0; k < localNamedNodeMap.getLength(); k++) {
          ((NamedNodeMap)localObject4).setNamedItem(importNode(localNamedNodeMap.item(k), true, true, paramHashtable));
        }
      }
      localNamedNodeMap = ((DocumentType)localObject2).getNotations();
      localObject4 = ((DocumentTypeImpl)localObject3).getNotations();
      if (localNamedNodeMap != null) {
        for (k = 0; k < localNamedNodeMap.getLength(); k++) {
          ((NamedNodeMap)localObject4).setNamedItem(importNode(localNamedNodeMap.item(k), true, true, paramHashtable));
        }
      }
      localObject1 = localObject3;
      break;
    case 11: 
      localObject1 = createDocumentFragment();
      break;
    case 12: 
      localObject2 = (Notation)paramNode;
      localObject3 = (NotationImpl)createNotation(paramNode.getNodeName());
      ((NotationImpl)localObject3).setPublicId(((Notation)localObject2).getPublicId());
      ((NotationImpl)localObject3).setSystemId(((Notation)localObject2).getSystemId());
      localObject1 = localObject3;
      break;
    case 9: 
    default: 
      localObject2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
      throw new DOMException((short)9, (String)localObject2);
    }
    if (localHashtable != null) {
      callUserDataHandlers(paramNode, (Node)localObject1, (short)2, localHashtable);
    }
    if (paramBoolean1) {
      for (localObject2 = paramNode.getFirstChild(); localObject2 != null; localObject2 = ((Node)localObject2).getNextSibling()) {
        ((Node)localObject1).appendChild(importNode((Node)localObject2, true, paramBoolean2, paramHashtable));
      }
    }
    if (((Node)localObject1).getNodeType() == 6) {
      ((NodeImpl)localObject1).setReadOnly(true, true);
    }
    return localObject1;
  }
  
  public Node adoptNode(Node paramNode)
  {
    Hashtable localHashtable = null;
    NodeImpl localNodeImpl;
    try
    {
      localNodeImpl = (NodeImpl)paramNode;
    }
    catch (ClassCastException localClassCastException)
    {
      return null;
    }
    if (paramNode == null) {
      return null;
    }
    Object localObject1;
    Object localObject2;
    if ((paramNode != null) && (paramNode.getOwnerDocument() != null))
    {
      localObject1 = getImplementation();
      localObject2 = paramNode.getOwnerDocument().getImplementation();
      if (localObject1 != localObject2) {
        if (((localObject1 instanceof DOMImplementationImpl)) && ((localObject2 instanceof DeferredDOMImplementationImpl))) {
          undeferChildren(localNodeImpl);
        } else if ((!(localObject1 instanceof DeferredDOMImplementationImpl)) || (!(localObject2 instanceof DOMImplementationImpl))) {
          return null;
        }
      }
    }
    switch (localNodeImpl.getNodeType())
    {
    case 2: 
      localObject1 = (AttrImpl)localNodeImpl;
      if (((AttrImpl)localObject1).getOwnerElement() != null) {
        ((AttrImpl)localObject1).getOwnerElement().removeAttributeNode((Attr)localObject1);
      }
      ((NodeImpl)localObject1).isSpecified(true);
      localHashtable = localNodeImpl.getUserDataRecord();
      ((AttrImpl)localObject1).setOwnerDocument(this);
      if (localHashtable != null) {
        setUserDataTable(localNodeImpl, localHashtable);
      }
      break;
    case 6: 
    case 12: 
      localObject1 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NO_MODIFICATION_ALLOWED_ERR", null);
      throw new DOMException((short)7, (String)localObject1);
    case 9: 
    case 10: 
      localObject1 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
      throw new DOMException((short)9, (String)localObject1);
    case 5: 
      localHashtable = localNodeImpl.getUserDataRecord();
      localObject1 = localNodeImpl.getParentNode();
      if (localObject1 != null) {
        ((Node)localObject1).removeChild(paramNode);
      }
      while ((localObject2 = localNodeImpl.getFirstChild()) != null) {
        localNodeImpl.removeChild((Node)localObject2);
      }
      localNodeImpl.setOwnerDocument(this);
      if (localHashtable != null) {
        setUserDataTable(localNodeImpl, localHashtable);
      }
      if (this.docType != null)
      {
        NamedNodeMap localNamedNodeMap = this.docType.getEntities();
        Node localNode1 = localNamedNodeMap.getNamedItem(localNodeImpl.getNodeName());
        if (localNode1 != null) {
          for (localObject2 = localNode1.getFirstChild(); localObject2 != null; localObject2 = ((Node)localObject2).getNextSibling())
          {
            Node localNode2 = ((Node)localObject2).cloneNode(true);
            localNodeImpl.appendChild(localNode2);
          }
        }
      }
      break;
    case 1: 
      localHashtable = localNodeImpl.getUserDataRecord();
      localObject1 = localNodeImpl.getParentNode();
      if (localObject1 != null) {
        ((Node)localObject1).removeChild(paramNode);
      }
      localNodeImpl.setOwnerDocument(this);
      if (localHashtable != null) {
        setUserDataTable(localNodeImpl, localHashtable);
      }
      ((ElementImpl)localNodeImpl).reconcileDefaultAttributes();
      break;
    case 3: 
    case 4: 
    case 7: 
    case 8: 
    case 11: 
    default: 
      localHashtable = localNodeImpl.getUserDataRecord();
      localObject1 = localNodeImpl.getParentNode();
      if (localObject1 != null) {
        ((Node)localObject1).removeChild(paramNode);
      }
      localNodeImpl.setOwnerDocument(this);
      if (localHashtable != null) {
        setUserDataTable(localNodeImpl, localHashtable);
      }
      break;
    }
    if (localHashtable != null) {
      callUserDataHandlers(paramNode, null, (short)5, localHashtable);
    }
    return localNodeImpl;
  }
  
  protected void undeferChildren(Node paramNode)
  {
    Node localNode1 = paramNode;
    while (null != paramNode)
    {
      if (((NodeImpl)paramNode).needsSyncData()) {
        ((NodeImpl)paramNode).synchronizeData();
      }
      NamedNodeMap localNamedNodeMap = paramNode.getAttributes();
      if (localNamedNodeMap != null)
      {
        int i = localNamedNodeMap.getLength();
        for (int j = 0; j < i; j++) {
          undeferChildren(localNamedNodeMap.item(j));
        }
      }
      Node localNode2 = null;
      localNode2 = paramNode.getFirstChild();
      while (null == localNode2)
      {
        if (localNode1.equals(paramNode)) {
          break;
        }
        localNode2 = paramNode.getNextSibling();
        if (null == localNode2)
        {
          paramNode = paramNode.getParentNode();
          if ((null == paramNode) || (localNode1.equals(paramNode)))
          {
            localNode2 = null;
            break;
          }
        }
      }
      paramNode = localNode2;
    }
  }
  
  public Element getElementById(String paramString)
  {
    return getIdentifier(paramString);
  }
  
  protected final void clearIdentifiers()
  {
    if (this.identifiers != null) {
      this.identifiers.clear();
    }
  }
  
  public void putIdentifier(String paramString, Element paramElement)
  {
    if (paramElement == null)
    {
      removeIdentifier(paramString);
      return;
    }
    if (needsSyncData()) {
      synchronizeData();
    }
    if (this.identifiers == null) {
      this.identifiers = new Hashtable();
    }
    this.identifiers.put(paramString, paramElement);
  }
  
  public Element getIdentifier(String paramString)
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    if (this.identifiers == null) {
      return null;
    }
    Element localElement = (Element)this.identifiers.get(paramString);
    if (localElement != null) {
      for (Node localNode = localElement.getParentNode(); localNode != null; localNode = localNode.getParentNode()) {
        if (localNode == this) {
          return localElement;
        }
      }
    }
    return null;
  }
  
  public void removeIdentifier(String paramString)
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    if (this.identifiers == null) {
      return;
    }
    this.identifiers.remove(paramString);
  }
  
  public Enumeration getIdentifiers()
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    if (this.identifiers == null) {
      this.identifiers = new Hashtable();
    }
    return this.identifiers.keys();
  }
  
  public Element createElementNS(String paramString1, String paramString2)
    throws DOMException
  {
    return new ElementNSImpl(this, paramString1, paramString2);
  }
  
  public Element createElementNS(String paramString1, String paramString2, String paramString3)
    throws DOMException
  {
    return new ElementNSImpl(this, paramString1, paramString2, paramString3);
  }
  
  public Attr createAttributeNS(String paramString1, String paramString2)
    throws DOMException
  {
    return new AttrNSImpl(this, paramString1, paramString2);
  }
  
  public Attr createAttributeNS(String paramString1, String paramString2, String paramString3)
    throws DOMException
  {
    return new AttrNSImpl(this, paramString1, paramString2, paramString3);
  }
  
  public NodeList getElementsByTagNameNS(String paramString1, String paramString2)
  {
    return new DeepNodeListImpl(this, paramString1, paramString2);
  }
  
  public Object clone()
    throws CloneNotSupportedException
  {
    CoreDocumentImpl localCoreDocumentImpl = (CoreDocumentImpl)super.clone();
    localCoreDocumentImpl.docType = null;
    localCoreDocumentImpl.docElement = null;
    return localCoreDocumentImpl;
  }
  
  public static final boolean isXMLName(String paramString, boolean paramBoolean)
  {
    if (paramString == null) {
      return false;
    }
    if (!paramBoolean) {
      return XMLChar.isValidName(paramString);
    }
    return XML11Char.isXML11ValidName(paramString);
  }
  
  public static final boolean isValidQName(String paramString1, String paramString2, boolean paramBoolean)
  {
    if (paramString2 == null) {
      return false;
    }
    boolean bool = false;
    if (!paramBoolean) {
      bool = ((paramString1 == null) || (XMLChar.isValidNCName(paramString1))) && (XMLChar.isValidNCName(paramString2));
    } else {
      bool = ((paramString1 == null) || (XML11Char.isXML11ValidNCName(paramString1))) && (XML11Char.isXML11ValidNCName(paramString2));
    }
    return bool;
  }
  
  protected boolean isKidOK(Node paramNode1, Node paramNode2)
  {
    if ((this.allowGrammarAccess) && (paramNode1.getNodeType() == 10)) {
      return paramNode2.getNodeType() == 1;
    }
    return 0 != (kidOK[paramNode1.getNodeType()] & 1 << paramNode2.getNodeType());
  }
  
  protected void changed()
  {
    this.changes += 1;
  }
  
  protected int changes()
  {
    return this.changes;
  }
  
  NodeListCache getNodeListCache(ParentNode paramParentNode)
  {
    if (this.fFreeNLCache == null) {
      return new NodeListCache(paramParentNode);
    }
    NodeListCache localNodeListCache = this.fFreeNLCache;
    this.fFreeNLCache = this.fFreeNLCache.next;
    localNodeListCache.fChild = null;
    localNodeListCache.fChildIndex = -1;
    localNodeListCache.fLength = -1;
    if (localNodeListCache.fOwner != null) {
      localNodeListCache.fOwner.fNodeListCache = null;
    }
    localNodeListCache.fOwner = paramParentNode;
    return localNodeListCache;
  }
  
  void freeNodeListCache(NodeListCache paramNodeListCache)
  {
    paramNodeListCache.next = this.fFreeNLCache;
    this.fFreeNLCache = paramNodeListCache;
  }
  
  public Object setUserData(Node paramNode, String paramString, Object paramObject, UserDataHandler paramUserDataHandler)
  {
    Hashtable localHashtable;
    ParentNode.UserDataRecord localUserDataRecord;
    if (paramObject == null)
    {
      if (this.userData != null)
      {
        localHashtable = (Hashtable)this.userData.get(paramNode);
        if (localHashtable != null)
        {
          localObject = localHashtable.remove(paramString);
          if (localObject != null)
          {
            localUserDataRecord = (ParentNode.UserDataRecord)localObject;
            return localUserDataRecord.fData;
          }
        }
      }
      return null;
    }
    if (this.userData == null)
    {
      this.userData = new Hashtable();
      localHashtable = new Hashtable();
      this.userData.put(paramNode, localHashtable);
    }
    else
    {
      localHashtable = (Hashtable)this.userData.get(paramNode);
      if (localHashtable == null)
      {
        localHashtable = new Hashtable();
        this.userData.put(paramNode, localHashtable);
      }
    }
    Object localObject = localHashtable.put(paramString, new ParentNode.UserDataRecord(this, paramObject, paramUserDataHandler));
    if (localObject != null)
    {
      localUserDataRecord = (ParentNode.UserDataRecord)localObject;
      return localUserDataRecord.fData;
    }
    return null;
  }
  
  public Object getUserData(Node paramNode, String paramString)
  {
    if (this.userData == null) {
      return null;
    }
    Hashtable localHashtable = (Hashtable)this.userData.get(paramNode);
    if (localHashtable == null) {
      return null;
    }
    Object localObject = localHashtable.get(paramString);
    if (localObject != null)
    {
      ParentNode.UserDataRecord localUserDataRecord = (ParentNode.UserDataRecord)localObject;
      return localUserDataRecord.fData;
    }
    return null;
  }
  
  protected Hashtable getUserDataRecord(Node paramNode)
  {
    if (this.userData == null) {
      return null;
    }
    Hashtable localHashtable = (Hashtable)this.userData.get(paramNode);
    if (localHashtable == null) {
      return null;
    }
    return localHashtable;
  }
  
  Hashtable removeUserDataTable(Node paramNode)
  {
    if (this.userData == null) {
      return null;
    }
    return (Hashtable)this.userData.get(paramNode);
  }
  
  void setUserDataTable(Node paramNode, Hashtable paramHashtable)
  {
    if (this.userData == null) {
      this.userData = new Hashtable();
    }
    if (paramHashtable != null) {
      this.userData.put(paramNode, paramHashtable);
    }
  }
  
  protected void callUserDataHandlers(Node paramNode1, Node paramNode2, short paramShort)
  {
    if (this.userData == null) {
      return;
    }
    if ((paramNode1 instanceof NodeImpl))
    {
      Hashtable localHashtable = ((NodeImpl)paramNode1).getUserDataRecord();
      if ((localHashtable == null) || (localHashtable.isEmpty())) {
        return;
      }
      callUserDataHandlers(paramNode1, paramNode2, paramShort, localHashtable);
    }
  }
  
  void callUserDataHandlers(Node paramNode1, Node paramNode2, short paramShort, Hashtable paramHashtable)
  {
    if ((paramHashtable == null) || (paramHashtable.isEmpty())) {
      return;
    }
    Enumeration localEnumeration = paramHashtable.keys();
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      ParentNode.UserDataRecord localUserDataRecord = (ParentNode.UserDataRecord)paramHashtable.get(str);
      if (localUserDataRecord.fHandler != null) {
        localUserDataRecord.fHandler.handle(paramShort, str, localUserDataRecord.fData, paramNode1, paramNode2);
      }
    }
  }
  
  protected final void checkNamespaceWF(String paramString, int paramInt1, int paramInt2)
  {
    if (!this.errorChecking) {
      return;
    }
    if ((paramInt1 == 0) || (paramInt1 == paramString.length() - 1) || (paramInt2 != paramInt1))
    {
      String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
      throw new DOMException((short)14, str);
    }
  }
  
  protected final void checkDOMNSErr(String paramString1, String paramString2)
  {
    if (this.errorChecking)
    {
      String str;
      if (paramString2 == null)
      {
        str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
        throw new DOMException((short)14, str);
      }
      if ((paramString1.equals("xml")) && (!paramString2.equals(NamespaceContext.XML_URI)))
      {
        str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
        throw new DOMException((short)14, str);
      }
      if (((paramString1.equals("xmlns")) && (!paramString2.equals(NamespaceContext.XMLNS_URI))) || ((!paramString1.equals("xmlns")) && (paramString2.equals(NamespaceContext.XMLNS_URI))))
      {
        str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
        throw new DOMException((short)14, str);
      }
    }
  }
  
  protected final void checkQName(String paramString1, String paramString2)
  {
    if (!this.errorChecking) {
      return;
    }
    int i = 0;
    if (!this.xml11Version) {
      i = ((paramString1 == null) || (XMLChar.isValidNCName(paramString1))) && (XMLChar.isValidNCName(paramString2)) ? 1 : 0;
    } else {
      i = ((paramString1 == null) || (XML11Char.isXML11ValidNCName(paramString1))) && (XML11Char.isXML11ValidNCName(paramString2)) ? 1 : 0;
    }
    if (i == 0)
    {
      String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
      throw new DOMException((short)5, str);
    }
  }
  
  boolean isXML11Version()
  {
    return this.xml11Version;
  }
  
  boolean isNormalizeDocRequired()
  {
    return true;
  }
  
  boolean isXMLVersionChanged()
  {
    return this.xmlVersionChanged;
  }
  
  protected void setUserData(NodeImpl paramNodeImpl, Object paramObject)
  {
    setUserData(paramNodeImpl, "XERCES1DOMUSERDATA", paramObject, null);
  }
  
  protected Object getUserData(NodeImpl paramNodeImpl)
  {
    return getUserData(paramNodeImpl, "XERCES1DOMUSERDATA");
  }
  
  protected void addEventListener(NodeImpl paramNodeImpl, String paramString, EventListener paramEventListener, boolean paramBoolean) {}
  
  protected void removeEventListener(NodeImpl paramNodeImpl, String paramString, EventListener paramEventListener, boolean paramBoolean) {}
  
  protected void copyEventListeners(NodeImpl paramNodeImpl1, NodeImpl paramNodeImpl2) {}
  
  protected boolean dispatchEvent(NodeImpl paramNodeImpl, Event paramEvent)
  {
    return false;
  }
  
  void replacedText(CharacterDataImpl paramCharacterDataImpl) {}
  
  void deletedText(CharacterDataImpl paramCharacterDataImpl, int paramInt1, int paramInt2) {}
  
  void insertedText(CharacterDataImpl paramCharacterDataImpl, int paramInt1, int paramInt2) {}
  
  void modifyingCharacterData(NodeImpl paramNodeImpl, boolean paramBoolean) {}
  
  void modifiedCharacterData(NodeImpl paramNodeImpl, String paramString1, String paramString2, boolean paramBoolean) {}
  
  void insertingNode(NodeImpl paramNodeImpl, boolean paramBoolean) {}
  
  void insertedNode(NodeImpl paramNodeImpl1, NodeImpl paramNodeImpl2, boolean paramBoolean) {}
  
  void removingNode(NodeImpl paramNodeImpl1, NodeImpl paramNodeImpl2, boolean paramBoolean) {}
  
  void removedNode(NodeImpl paramNodeImpl, boolean paramBoolean) {}
  
  void replacingNode(NodeImpl paramNodeImpl) {}
  
  void replacedNode(NodeImpl paramNodeImpl) {}
  
  void replacingData(NodeImpl paramNodeImpl) {}
  
  void replacedCharacterData(NodeImpl paramNodeImpl, String paramString1, String paramString2) {}
  
  void modifiedAttrValue(AttrImpl paramAttrImpl, String paramString) {}
  
  void setAttrNode(AttrImpl paramAttrImpl1, AttrImpl paramAttrImpl2) {}
  
  void removedAttrNode(AttrImpl paramAttrImpl, NodeImpl paramNodeImpl, String paramString) {}
  
  void renamedAttrNode(Attr paramAttr1, Attr paramAttr2) {}
  
  void renamedElement(Element paramElement1, Element paramElement2) {}
  
  static
  {
    kidOK[9] = 1410;
    short tmp41_40 = (kidOK[5] = kidOK[1] = 442);
    kidOK[6] = tmp41_40;
    kidOK[11] = tmp41_40;
    kidOK[2] = 40;
    int tmp88_87 = (kidOK[8] = kidOK[3] = kidOK[4] = kidOK[12] = 0);
    kidOK[7] = tmp88_87;
    kidOK[10] = tmp88_87;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.CoreDocumentImpl
 * JD-Core Version:    0.7.0.1
 */
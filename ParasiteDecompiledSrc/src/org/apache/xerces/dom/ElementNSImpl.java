package org.apache.xerces.dom;

import org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl;
import org.apache.xerces.impl.xs.XSComplexTypeDecl;
import org.apache.xerces.util.URI;
import org.apache.xerces.util.URI.MalformedURIException;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public class ElementNSImpl
  extends ElementImpl
{
  static final long serialVersionUID = -9142310625494392642L;
  static final String xmlURI = "http://www.w3.org/XML/1998/namespace";
  protected String namespaceURI;
  protected String localName;
  transient XSTypeDefinition type;
  
  protected ElementNSImpl() {}
  
  protected ElementNSImpl(CoreDocumentImpl paramCoreDocumentImpl, String paramString1, String paramString2)
    throws DOMException
  {
    super(paramCoreDocumentImpl, paramString2);
    setName(paramString1, paramString2);
  }
  
  private void setName(String paramString1, String paramString2)
  {
    this.namespaceURI = paramString1;
    if (paramString1 != null) {
      this.namespaceURI = (paramString1.length() == 0 ? null : paramString1);
    }
    String str2;
    if (paramString2 == null)
    {
      str2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
      throw new DOMException((short)14, str2);
    }
    int i = paramString2.indexOf(':');
    int j = paramString2.lastIndexOf(':');
    this.ownerDocument.checkNamespaceWF(paramString2, i, j);
    if (i < 0)
    {
      this.localName = paramString2;
      if (this.ownerDocument.errorChecking)
      {
        this.ownerDocument.checkQName(null, this.localName);
        if (((paramString2.equals("xmlns")) && ((paramString1 == null) || (!paramString1.equals(NamespaceContext.XMLNS_URI)))) || ((paramString1 != null) && (paramString1.equals(NamespaceContext.XMLNS_URI)) && (!paramString2.equals("xmlns"))))
        {
          str2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
          throw new DOMException((short)14, str2);
        }
      }
    }
    else
    {
      String str1 = paramString2.substring(0, i);
      this.localName = paramString2.substring(j + 1);
      if (this.ownerDocument.errorChecking)
      {
        if ((paramString1 == null) || ((str1.equals("xml")) && (!paramString1.equals(NamespaceContext.XML_URI))))
        {
          str2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
          throw new DOMException((short)14, str2);
        }
        this.ownerDocument.checkQName(str1, this.localName);
        this.ownerDocument.checkDOMNSErr(str1, paramString1);
      }
    }
  }
  
  protected ElementNSImpl(CoreDocumentImpl paramCoreDocumentImpl, String paramString1, String paramString2, String paramString3)
    throws DOMException
  {
    super(paramCoreDocumentImpl, paramString2);
    this.localName = paramString3;
    this.namespaceURI = paramString1;
  }
  
  protected ElementNSImpl(CoreDocumentImpl paramCoreDocumentImpl, String paramString)
  {
    super(paramCoreDocumentImpl, paramString);
  }
  
  void rename(String paramString1, String paramString2)
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    this.name = paramString2;
    setName(paramString1, paramString2);
    reconcileDefaultAttributes();
  }
  
  protected void setValues(CoreDocumentImpl paramCoreDocumentImpl, String paramString1, String paramString2, String paramString3)
  {
    this.firstChild = null;
    this.previousSibling = null;
    this.nextSibling = null;
    this.fNodeListCache = null;
    this.attributes = null;
    this.flags = 0;
    setOwnerDocument(paramCoreDocumentImpl);
    needsSyncData(true);
    this.name = paramString2;
    this.localName = paramString3;
    this.namespaceURI = paramString1;
  }
  
  public String getNamespaceURI()
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    return this.namespaceURI;
  }
  
  public String getPrefix()
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    int i = this.name.indexOf(':');
    return i < 0 ? null : this.name.substring(0, i);
  }
  
  public void setPrefix(String paramString)
    throws DOMException
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    if (this.ownerDocument.errorChecking)
    {
      String str;
      if (isReadOnly())
      {
        str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NO_MODIFICATION_ALLOWED_ERR", null);
        throw new DOMException((short)7, str);
      }
      if ((paramString != null) && (paramString.length() != 0))
      {
        if (!CoreDocumentImpl.isXMLName(paramString, this.ownerDocument.isXML11Version()))
        {
          str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
          throw new DOMException((short)5, str);
        }
        if ((this.namespaceURI == null) || (paramString.indexOf(':') >= 0))
        {
          str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
          throw new DOMException((short)14, str);
        }
        if ((paramString.equals("xml")) && (!this.namespaceURI.equals("http://www.w3.org/XML/1998/namespace")))
        {
          str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
          throw new DOMException((short)14, str);
        }
      }
    }
    if ((paramString != null) && (paramString.length() != 0)) {
      this.name = (paramString + ":" + this.localName);
    } else {
      this.name = this.localName;
    }
  }
  
  public String getLocalName()
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    return this.localName;
  }
  
  public String getBaseURI()
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    if (this.attributes != null)
    {
      localAttr = (Attr)this.attributes.getNamedItemNS("http://www.w3.org/XML/1998/namespace", "base");
      if (localAttr != null)
      {
        String str1 = localAttr.getNodeValue();
        if (str1.length() != 0)
        {
          try
          {
            str1 = new URI(str1).toString();
          }
          catch (URI.MalformedURIException localMalformedURIException2)
          {
            NodeImpl localNodeImpl = parentNode() != null ? parentNode() : this.ownerNode;
            String str3 = localNodeImpl != null ? localNodeImpl.getBaseURI() : null;
            if (str3 != null)
            {
              try
              {
                str1 = new URI(new URI(str3), str1).toString();
              }
              catch (URI.MalformedURIException localMalformedURIException4)
              {
                return null;
              }
              return str1;
            }
            return null;
          }
          return str1;
        }
      }
    }
    Attr localAttr = parentNode() != null ? parentNode().getBaseURI() : null;
    if (localAttr != null) {
      try
      {
        return new URI(localAttr).toString();
      }
      catch (URI.MalformedURIException localMalformedURIException1)
      {
        return null;
      }
    }
    String str2 = this.ownerNode != null ? this.ownerNode.getBaseURI() : null;
    if (str2 != null) {
      try
      {
        return new URI(str2).toString();
      }
      catch (URI.MalformedURIException localMalformedURIException3)
      {
        return null;
      }
    }
    return null;
  }
  
  public String getTypeName()
  {
    if (this.type != null)
    {
      if ((this.type instanceof XSSimpleTypeDefinition)) {
        return ((XSSimpleTypeDecl)this.type).getTypeName();
      }
      return ((XSComplexTypeDecl)this.type).getTypeName();
    }
    return null;
  }
  
  public String getTypeNamespace()
  {
    if (this.type != null) {
      return this.type.getNamespace();
    }
    return null;
  }
  
  public boolean isDerivedFrom(String paramString1, String paramString2, int paramInt)
  {
    if (needsSyncData()) {
      synchronizeData();
    }
    if (this.type != null)
    {
      if ((this.type instanceof XSSimpleTypeDefinition)) {
        return ((XSSimpleTypeDecl)this.type).isDOMDerivedFrom(paramString1, paramString2, paramInt);
      }
      return ((XSComplexTypeDecl)this.type).isDOMDerivedFrom(paramString1, paramString2, paramInt);
    }
    return false;
  }
  
  public void setType(XSTypeDefinition paramXSTypeDefinition)
  {
    this.type = paramXSTypeDefinition;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.ElementNSImpl
 * JD-Core Version:    0.7.0.1
 */
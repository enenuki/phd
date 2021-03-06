package org.apache.xerces.dom;

import org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.w3c.dom.DOMException;

public class AttrNSImpl
  extends AttrImpl
{
  static final long serialVersionUID = -781906615369795414L;
  static final String xmlnsURI = "http://www.w3.org/2000/xmlns/";
  static final String xmlURI = "http://www.w3.org/XML/1998/namespace";
  protected String namespaceURI;
  protected String localName;
  
  public AttrNSImpl() {}
  
  protected AttrNSImpl(CoreDocumentImpl paramCoreDocumentImpl, String paramString1, String paramString2)
  {
    super(paramCoreDocumentImpl, paramString2);
    setName(paramString1, paramString2);
  }
  
  private void setName(String paramString1, String paramString2)
  {
    CoreDocumentImpl localCoreDocumentImpl = ownerDocument();
    this.namespaceURI = paramString1;
    if (paramString1 != null) {
      this.namespaceURI = (paramString1.length() == 0 ? null : paramString1);
    }
    int i = paramString2.indexOf(':');
    int j = paramString2.lastIndexOf(':');
    localCoreDocumentImpl.checkNamespaceWF(paramString2, i, j);
    if (i < 0)
    {
      this.localName = paramString2;
      if (localCoreDocumentImpl.errorChecking)
      {
        localCoreDocumentImpl.checkQName(null, this.localName);
        if (((paramString2.equals("xmlns")) && ((paramString1 == null) || (!paramString1.equals(NamespaceContext.XMLNS_URI)))) || ((paramString1 != null) && (paramString1.equals(NamespaceContext.XMLNS_URI)) && (!paramString2.equals("xmlns"))))
        {
          String str2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
          throw new DOMException((short)14, str2);
        }
      }
    }
    else
    {
      String str1 = paramString2.substring(0, i);
      this.localName = paramString2.substring(j + 1);
      localCoreDocumentImpl.checkQName(str1, this.localName);
      localCoreDocumentImpl.checkDOMNSErr(str1, paramString1);
    }
  }
  
  public AttrNSImpl(CoreDocumentImpl paramCoreDocumentImpl, String paramString1, String paramString2, String paramString3)
  {
    super(paramCoreDocumentImpl, paramString2);
    this.localName = paramString3;
    this.namespaceURI = paramString1;
  }
  
  protected AttrNSImpl(CoreDocumentImpl paramCoreDocumentImpl, String paramString)
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
  }
  
  public void setValues(CoreDocumentImpl paramCoreDocumentImpl, String paramString1, String paramString2, String paramString3)
  {
    AttrImpl.textNode = null;
    this.flags = 0;
    isSpecified(true);
    hasStringValue(true);
    super.setOwnerDocument(paramCoreDocumentImpl);
    this.localName = paramString3;
    this.namespaceURI = paramString1;
    this.name = paramString2;
    this.value = null;
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
    if (ownerDocument().errorChecking)
    {
      String str;
      if (isReadOnly())
      {
        str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NO_MODIFICATION_ALLOWED_ERR", null);
        throw new DOMException((short)7, str);
      }
      if ((paramString != null) && (paramString.length() != 0))
      {
        if (!CoreDocumentImpl.isXMLName(paramString, ownerDocument().isXML11Version()))
        {
          str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
          throw new DOMException((short)5, str);
        }
        if ((this.namespaceURI == null) || (paramString.indexOf(':') >= 0))
        {
          str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
          throw new DOMException((short)14, str);
        }
        if (paramString.equals("xmlns"))
        {
          if (!this.namespaceURI.equals("http://www.w3.org/2000/xmlns/"))
          {
            str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
            throw new DOMException((short)14, str);
          }
        }
        else if (paramString.equals("xml"))
        {
          if (!this.namespaceURI.equals("http://www.w3.org/XML/1998/namespace"))
          {
            str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
            throw new DOMException((short)14, str);
          }
        }
        else if (this.name.equals("xmlns"))
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
  
  public String getTypeName()
  {
    if (this.type != null)
    {
      if ((this.type instanceof XSSimpleTypeDecl)) {
        return ((XSSimpleTypeDecl)this.type).getName();
      }
      return (String)this.type;
    }
    return null;
  }
  
  public boolean isDerivedFrom(String paramString1, String paramString2, int paramInt)
  {
    if ((this.type != null) && ((this.type instanceof XSSimpleTypeDefinition))) {
      return ((XSSimpleTypeDecl)this.type).isDOMDerivedFrom(paramString1, paramString2, paramInt);
    }
    return false;
  }
  
  public String getTypeNamespace()
  {
    if (this.type != null)
    {
      if ((this.type instanceof XSSimpleTypeDecl)) {
        return ((XSSimpleTypeDecl)this.type).getNamespace();
      }
      return "http://www.w3.org/TR/REC-xml";
    }
    return null;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.AttrNSImpl
 * JD-Core Version:    0.7.0.1
 */
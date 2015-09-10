package org.apache.xerces.dom;

import java.lang.ref.SoftReference;
import org.apache.xerces.impl.RevalidationHandler;
import org.apache.xerces.impl.dtd.XMLDTDLoader;
import org.apache.xerces.parsers.DOMParserImpl;
import org.apache.xerces.util.XMLChar;
import org.apache.xml.serialize.DOMSerializerImpl;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSSerializer;

public class CoreDOMImplementationImpl
  implements DOMImplementation, DOMImplementationLS
{
  private static final int SIZE = 2;
  private SoftReference[] schemaValidators = new SoftReference[2];
  private SoftReference[] xml10DTDValidators = new SoftReference[2];
  private SoftReference[] xml11DTDValidators = new SoftReference[2];
  private int freeSchemaValidatorIndex = -1;
  private int freeXML10DTDValidatorIndex = -1;
  private int freeXML11DTDValidatorIndex = -1;
  private int schemaValidatorsCurrentSize = 2;
  private int xml10DTDValidatorsCurrentSize = 2;
  private int xml11DTDValidatorsCurrentSize = 2;
  private SoftReference[] xml10DTDLoaders = new SoftReference[2];
  private SoftReference[] xml11DTDLoaders = new SoftReference[2];
  private int freeXML10DTDLoaderIndex = -1;
  private int freeXML11DTDLoaderIndex = -1;
  private int xml10DTDLoaderCurrentSize = 2;
  private int xml11DTDLoaderCurrentSize = 2;
  private int docAndDoctypeCounter = 0;
  static CoreDOMImplementationImpl singleton = new CoreDOMImplementationImpl();
  
  public static DOMImplementation getDOMImplementation()
  {
    return singleton;
  }
  
  public boolean hasFeature(String paramString1, String paramString2)
  {
    int i = (paramString2 == null) || (paramString2.length() == 0) ? 1 : 0;
    if ((paramString1.equalsIgnoreCase("+XPath")) && ((i != 0) || (paramString2.equals("3.0"))))
    {
      try
      {
        Class localClass = ObjectFactory.findProviderClass("org.apache.xpath.domapi.XPathEvaluatorImpl", ObjectFactory.findClassLoader(), true);
        Class[] arrayOfClass = localClass.getInterfaces();
        for (int j = 0; j < arrayOfClass.length; j++) {
          if (arrayOfClass[j].getName().equals("org.w3c.dom.xpath.XPathEvaluator")) {
            return true;
          }
        }
      }
      catch (Exception localException)
      {
        return false;
      }
      return true;
    }
    if (paramString1.startsWith("+")) {
      paramString1 = paramString1.substring(1);
    }
    return ((paramString1.equalsIgnoreCase("Core")) && ((i != 0) || (paramString2.equals("1.0")) || (paramString2.equals("2.0")) || (paramString2.equals("3.0")))) || ((paramString1.equalsIgnoreCase("XML")) && ((i != 0) || (paramString2.equals("1.0")) || (paramString2.equals("2.0")) || (paramString2.equals("3.0")))) || ((paramString1.equalsIgnoreCase("XMLVersion")) && ((i != 0) || (paramString2.equals("1.0")) || (paramString2.equals("1.1")))) || ((paramString1.equalsIgnoreCase("LS")) && ((i != 0) || (paramString2.equals("3.0"))));
  }
  
  public DocumentType createDocumentType(String paramString1, String paramString2, String paramString3)
  {
    checkQName(paramString1);
    return new DocumentTypeImpl(null, paramString1, paramString2, paramString3);
  }
  
  final void checkQName(String paramString)
  {
    int i = paramString.indexOf(':');
    int j = paramString.lastIndexOf(':');
    int k = paramString.length();
    if ((i == 0) || (i == k - 1) || (j != i))
    {
      String str1 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
      throw new DOMException((short)14, str1);
    }
    int m = 0;
    String str4;
    if (i > 0)
    {
      if (!XMLChar.isNCNameStart(paramString.charAt(m)))
      {
        String str2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
        throw new DOMException((short)5, str2);
      }
      for (int n = 1; n < i; n++) {
        if (!XMLChar.isNCName(paramString.charAt(n)))
        {
          str4 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
          throw new DOMException((short)5, str4);
        }
      }
      m = i + 1;
    }
    if (!XMLChar.isNCNameStart(paramString.charAt(m)))
    {
      String str3 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
      throw new DOMException((short)5, str3);
    }
    for (int i1 = m + 1; i1 < k; i1++) {
      if (!XMLChar.isNCName(paramString.charAt(i1)))
      {
        str4 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
        throw new DOMException((short)5, str4);
      }
    }
  }
  
  public Document createDocument(String paramString1, String paramString2, DocumentType paramDocumentType)
    throws DOMException
  {
    if ((paramDocumentType != null) && (paramDocumentType.getOwnerDocument() != null))
    {
      localObject = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null);
      throw new DOMException((short)4, (String)localObject);
    }
    Object localObject = new CoreDocumentImpl(paramDocumentType);
    if ((paramString2 != null) || (paramString1 != null))
    {
      Element localElement = ((CoreDocumentImpl)localObject).createElementNS(paramString1, paramString2);
      ((NodeImpl)localObject).appendChild(localElement);
    }
    return localObject;
  }
  
  public Object getFeature(String paramString1, String paramString2)
  {
    if (singleton.hasFeature(paramString1, paramString2)) {
      if (paramString1.equalsIgnoreCase("+XPath")) {
        try
        {
          Class localClass = ObjectFactory.findProviderClass("org.apache.xpath.domapi.XPathEvaluatorImpl", ObjectFactory.findClassLoader(), true);
          Class[] arrayOfClass = localClass.getInterfaces();
          for (int i = 0; i < arrayOfClass.length; i++) {
            if (arrayOfClass[i].getName().equals("org.w3c.dom.xpath.XPathEvaluator")) {
              return localClass.newInstance();
            }
          }
        }
        catch (Exception localException)
        {
          return null;
        }
      } else {
        return singleton;
      }
    }
    return null;
  }
  
  public LSParser createLSParser(short paramShort, String paramString)
    throws DOMException
  {
    if ((paramShort != 1) || ((paramString != null) && (!"http://www.w3.org/2001/XMLSchema".equals(paramString)) && (!"http://www.w3.org/TR/REC-xml".equals(paramString))))
    {
      String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
      throw new DOMException((short)9, str);
    }
    if ((paramString != null) && (paramString.equals("http://www.w3.org/TR/REC-xml"))) {
      return new DOMParserImpl("org.apache.xerces.parsers.DTDConfiguration", paramString);
    }
    return new DOMParserImpl("org.apache.xerces.parsers.XIncludeAwareParserConfiguration", paramString);
  }
  
  public LSSerializer createLSSerializer()
  {
    try
    {
      Class localClass = ObjectFactory.findProviderClass("org.apache.xml.serializer.dom3.LSSerializerImpl", ObjectFactory.findClassLoader(), true);
      return (LSSerializer)localClass.newInstance();
    }
    catch (Exception localException) {}
    return new DOMSerializerImpl();
  }
  
  public LSInput createLSInput()
  {
    return new DOMInputImpl();
  }
  
  synchronized RevalidationHandler getValidator(String paramString1, String paramString2)
  {
    SoftReference localSoftReference;
    RevalidationHandlerHolder localRevalidationHandlerHolder;
    RevalidationHandler localRevalidationHandler;
    if (paramString1 == "http://www.w3.org/2001/XMLSchema")
    {
      while (this.freeSchemaValidatorIndex >= 0)
      {
        localSoftReference = this.schemaValidators[this.freeSchemaValidatorIndex];
        localRevalidationHandlerHolder = (RevalidationHandlerHolder)localSoftReference.get();
        if ((localRevalidationHandlerHolder != null) && (localRevalidationHandlerHolder.handler != null))
        {
          localRevalidationHandler = localRevalidationHandlerHolder.handler;
          localRevalidationHandlerHolder.handler = null;
          this.freeSchemaValidatorIndex -= 1;
          return localRevalidationHandler;
        }
        this.schemaValidators[(this.freeSchemaValidatorIndex--)] = null;
      }
      return (RevalidationHandler)ObjectFactory.newInstance("org.apache.xerces.impl.xs.XMLSchemaValidator", ObjectFactory.findClassLoader(), true);
    }
    if (paramString1 == "http://www.w3.org/TR/REC-xml")
    {
      if ("1.1".equals(paramString2))
      {
        while (this.freeXML11DTDValidatorIndex >= 0)
        {
          localSoftReference = this.xml11DTDValidators[this.freeXML11DTDValidatorIndex];
          localRevalidationHandlerHolder = (RevalidationHandlerHolder)localSoftReference.get();
          if ((localRevalidationHandlerHolder != null) && (localRevalidationHandlerHolder.handler != null))
          {
            localRevalidationHandler = localRevalidationHandlerHolder.handler;
            localRevalidationHandlerHolder.handler = null;
            this.freeXML11DTDValidatorIndex -= 1;
            return localRevalidationHandler;
          }
          this.xml11DTDValidators[(this.freeXML11DTDValidatorIndex--)] = null;
        }
        return (RevalidationHandler)ObjectFactory.newInstance("org.apache.xerces.impl.dtd.XML11DTDValidator", ObjectFactory.findClassLoader(), true);
      }
      while (this.freeXML10DTDValidatorIndex >= 0)
      {
        localSoftReference = this.xml10DTDValidators[this.freeXML10DTDValidatorIndex];
        localRevalidationHandlerHolder = (RevalidationHandlerHolder)localSoftReference.get();
        if ((localRevalidationHandlerHolder != null) && (localRevalidationHandlerHolder.handler != null))
        {
          localRevalidationHandler = localRevalidationHandlerHolder.handler;
          localRevalidationHandlerHolder.handler = null;
          this.freeXML10DTDValidatorIndex -= 1;
          return localRevalidationHandler;
        }
        this.xml10DTDValidators[(this.freeXML10DTDValidatorIndex--)] = null;
      }
      return (RevalidationHandler)ObjectFactory.newInstance("org.apache.xerces.impl.dtd.XMLDTDValidator", ObjectFactory.findClassLoader(), true);
    }
    return null;
  }
  
  synchronized void releaseValidator(String paramString1, String paramString2, RevalidationHandler paramRevalidationHandler)
  {
    Object localObject;
    RevalidationHandlerHolder localRevalidationHandlerHolder;
    if (paramString1 == "http://www.w3.org/2001/XMLSchema")
    {
      this.freeSchemaValidatorIndex += 1;
      if (this.schemaValidators.length == this.freeSchemaValidatorIndex)
      {
        this.schemaValidatorsCurrentSize += 2;
        localObject = new SoftReference[this.schemaValidatorsCurrentSize];
        System.arraycopy(this.schemaValidators, 0, localObject, 0, this.schemaValidators.length);
        this.schemaValidators = ((SoftReference[])localObject);
      }
      localObject = this.schemaValidators[this.freeSchemaValidatorIndex];
      if (localObject != null)
      {
        localRevalidationHandlerHolder = (RevalidationHandlerHolder)((SoftReference)localObject).get();
        if (localRevalidationHandlerHolder != null)
        {
          localRevalidationHandlerHolder.handler = paramRevalidationHandler;
          return;
        }
      }
      this.schemaValidators[this.freeSchemaValidatorIndex] = new SoftReference(new RevalidationHandlerHolder(paramRevalidationHandler));
    }
    else if (paramString1 == "http://www.w3.org/TR/REC-xml")
    {
      if ("1.1".equals(paramString2))
      {
        this.freeXML11DTDValidatorIndex += 1;
        if (this.xml11DTDValidators.length == this.freeXML11DTDValidatorIndex)
        {
          this.xml11DTDValidatorsCurrentSize += 2;
          localObject = new SoftReference[this.xml11DTDValidatorsCurrentSize];
          System.arraycopy(this.xml11DTDValidators, 0, localObject, 0, this.xml11DTDValidators.length);
          this.xml11DTDValidators = ((SoftReference[])localObject);
        }
        localObject = this.xml11DTDValidators[this.freeXML11DTDValidatorIndex];
        if (localObject != null)
        {
          localRevalidationHandlerHolder = (RevalidationHandlerHolder)((SoftReference)localObject).get();
          if (localRevalidationHandlerHolder != null)
          {
            localRevalidationHandlerHolder.handler = paramRevalidationHandler;
            return;
          }
        }
        this.xml11DTDValidators[this.freeXML11DTDValidatorIndex] = new SoftReference(new RevalidationHandlerHolder(paramRevalidationHandler));
      }
      else
      {
        this.freeXML10DTDValidatorIndex += 1;
        if (this.xml10DTDValidators.length == this.freeXML10DTDValidatorIndex)
        {
          this.xml10DTDValidatorsCurrentSize += 2;
          localObject = new SoftReference[this.xml10DTDValidatorsCurrentSize];
          System.arraycopy(this.xml10DTDValidators, 0, localObject, 0, this.xml10DTDValidators.length);
          this.xml10DTDValidators = ((SoftReference[])localObject);
        }
        localObject = this.xml10DTDValidators[this.freeXML10DTDValidatorIndex];
        if (localObject != null)
        {
          localRevalidationHandlerHolder = (RevalidationHandlerHolder)((SoftReference)localObject).get();
          if (localRevalidationHandlerHolder != null)
          {
            localRevalidationHandlerHolder.handler = paramRevalidationHandler;
            return;
          }
        }
        this.xml10DTDValidators[this.freeXML10DTDValidatorIndex] = new SoftReference(new RevalidationHandlerHolder(paramRevalidationHandler));
      }
    }
  }
  
  final synchronized XMLDTDLoader getDTDLoader(String paramString)
  {
    if ("1.1".equals(paramString))
    {
      while (this.freeXML11DTDLoaderIndex >= 0)
      {
        localSoftReference = this.xml11DTDLoaders[this.freeXML11DTDLoaderIndex];
        localXMLDTDLoaderHolder = (XMLDTDLoaderHolder)localSoftReference.get();
        if ((localXMLDTDLoaderHolder != null) && (localXMLDTDLoaderHolder.loader != null))
        {
          localXMLDTDLoader = localXMLDTDLoaderHolder.loader;
          localXMLDTDLoaderHolder.loader = null;
          this.freeXML11DTDLoaderIndex -= 1;
          return localXMLDTDLoader;
        }
        this.xml11DTDLoaders[(this.freeXML11DTDLoaderIndex--)] = null;
      }
      return (XMLDTDLoader)ObjectFactory.newInstance("org.apache.xerces.impl.dtd.XML11DTDProcessor", ObjectFactory.findClassLoader(), true);
    }
    while (this.freeXML10DTDLoaderIndex >= 0)
    {
      XMLDTDLoader localXMLDTDLoader;
      SoftReference localSoftReference = this.xml10DTDLoaders[this.freeXML10DTDLoaderIndex];
      XMLDTDLoaderHolder localXMLDTDLoaderHolder = (XMLDTDLoaderHolder)localSoftReference.get();
      if ((localXMLDTDLoaderHolder != null) && (localXMLDTDLoaderHolder.loader != null))
      {
        localXMLDTDLoader = localXMLDTDLoaderHolder.loader;
        localXMLDTDLoaderHolder.loader = null;
        this.freeXML10DTDLoaderIndex -= 1;
        return localXMLDTDLoader;
      }
      this.xml10DTDLoaders[(this.freeXML10DTDLoaderIndex--)] = null;
    }
    return new XMLDTDLoader();
  }
  
  final synchronized void releaseDTDLoader(String paramString, XMLDTDLoader paramXMLDTDLoader)
  {
    Object localObject;
    XMLDTDLoaderHolder localXMLDTDLoaderHolder;
    if ("1.1".equals(paramString))
    {
      this.freeXML11DTDLoaderIndex += 1;
      if (this.xml11DTDLoaders.length == this.freeXML11DTDLoaderIndex)
      {
        this.xml11DTDLoaderCurrentSize += 2;
        localObject = new SoftReference[this.xml11DTDLoaderCurrentSize];
        System.arraycopy(this.xml11DTDLoaders, 0, localObject, 0, this.xml11DTDLoaders.length);
        this.xml11DTDLoaders = ((SoftReference[])localObject);
      }
      localObject = this.xml11DTDLoaders[this.freeXML11DTDLoaderIndex];
      if (localObject != null)
      {
        localXMLDTDLoaderHolder = (XMLDTDLoaderHolder)((SoftReference)localObject).get();
        if (localXMLDTDLoaderHolder != null)
        {
          localXMLDTDLoaderHolder.loader = paramXMLDTDLoader;
          return;
        }
      }
      this.xml11DTDLoaders[this.freeXML11DTDLoaderIndex] = new SoftReference(new XMLDTDLoaderHolder(paramXMLDTDLoader));
    }
    else
    {
      this.freeXML10DTDLoaderIndex += 1;
      if (this.xml10DTDLoaders.length == this.freeXML10DTDLoaderIndex)
      {
        this.xml10DTDLoaderCurrentSize += 2;
        localObject = new SoftReference[this.xml10DTDLoaderCurrentSize];
        System.arraycopy(this.xml10DTDLoaders, 0, localObject, 0, this.xml10DTDLoaders.length);
        this.xml10DTDLoaders = ((SoftReference[])localObject);
      }
      localObject = this.xml10DTDLoaders[this.freeXML10DTDLoaderIndex];
      if (localObject != null)
      {
        localXMLDTDLoaderHolder = (XMLDTDLoaderHolder)((SoftReference)localObject).get();
        if (localXMLDTDLoaderHolder != null)
        {
          localXMLDTDLoaderHolder.loader = paramXMLDTDLoader;
          return;
        }
      }
      this.xml10DTDLoaders[this.freeXML10DTDLoaderIndex] = new SoftReference(new XMLDTDLoaderHolder(paramXMLDTDLoader));
    }
  }
  
  protected synchronized int assignDocumentNumber()
  {
    return ++this.docAndDoctypeCounter;
  }
  
  protected synchronized int assignDocTypeNumber()
  {
    return ++this.docAndDoctypeCounter;
  }
  
  public LSOutput createLSOutput()
  {
    return new DOMOutputImpl();
  }
  
  static class XMLDTDLoaderHolder
  {
    XMLDTDLoader loader;
    
    XMLDTDLoaderHolder(XMLDTDLoader paramXMLDTDLoader)
    {
      this.loader = paramXMLDTDLoader;
    }
  }
  
  static class RevalidationHandlerHolder
  {
    RevalidationHandler handler;
    
    RevalidationHandlerHolder(RevalidationHandler paramRevalidationHandler)
    {
      this.handler = paramRevalidationHandler;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.CoreDOMImplementationImpl
 * JD-Core Version:    0.7.0.1
 */
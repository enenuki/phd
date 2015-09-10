package org.apache.xerces.parsers;

import java.io.CharConversionException;
import java.io.IOException;
import java.util.Locale;
import org.apache.xerces.util.EntityResolver2Wrapper;
import org.apache.xerces.util.EntityResolverWrapper;
import org.apache.xerces.util.ErrorHandlerWrapper;
import org.apache.xerces.util.SAXMessageFormatter;
import org.apache.xerces.util.SymbolHash;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLErrorHandler;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLParseException;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.apache.xerces.xs.AttributePSVI;
import org.apache.xerces.xs.ElementPSVI;
import org.apache.xerces.xs.PSVIProvider;
import org.xml.sax.AttributeList;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.Attributes2;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.EntityResolver2;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.ext.Locator2;
import org.xml.sax.ext.Locator2Impl;
import org.xml.sax.helpers.LocatorImpl;

public abstract class AbstractSAXParser
  extends AbstractXMLDocumentParser
  implements PSVIProvider, Parser, XMLReader
{
  protected static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
  protected static final String STRING_INTERNING = "http://xml.org/sax/features/string-interning";
  protected static final String ALLOW_UE_AND_NOTATION_EVENTS = "http://xml.org/sax/features/allow-dtd-events-after-endDTD";
  private static final String[] RECOGNIZED_FEATURES = { "http://xml.org/sax/features/namespaces", "http://xml.org/sax/features/string-interning" };
  protected static final String LEXICAL_HANDLER = "http://xml.org/sax/properties/lexical-handler";
  protected static final String DECLARATION_HANDLER = "http://xml.org/sax/properties/declaration-handler";
  protected static final String DOM_NODE = "http://xml.org/sax/properties/dom-node";
  private static final String[] RECOGNIZED_PROPERTIES = { "http://xml.org/sax/properties/lexical-handler", "http://xml.org/sax/properties/declaration-handler", "http://xml.org/sax/properties/dom-node" };
  protected boolean fNamespaces;
  protected boolean fNamespacePrefixes = false;
  protected boolean fLexicalHandlerParameterEntities = true;
  protected boolean fStandalone;
  protected boolean fResolveDTDURIs = true;
  protected boolean fUseEntityResolver2 = true;
  protected boolean fXMLNSURIs = false;
  protected ContentHandler fContentHandler;
  protected DocumentHandler fDocumentHandler;
  protected NamespaceContext fNamespaceContext;
  protected DTDHandler fDTDHandler;
  protected DeclHandler fDeclHandler;
  protected LexicalHandler fLexicalHandler;
  protected QName fQName = new QName();
  protected boolean fParseInProgress = false;
  protected String fVersion;
  private final AttributesProxy fAttributesProxy = new AttributesProxy();
  private Augmentations fAugmentations = null;
  private static final int BUFFER_SIZE = 20;
  private char[] fCharBuffer = new char[20];
  protected SymbolHash fDeclaredAttrs = null;
  
  protected AbstractSAXParser(XMLParserConfiguration paramXMLParserConfiguration)
  {
    super(paramXMLParserConfiguration);
    paramXMLParserConfiguration.addRecognizedFeatures(RECOGNIZED_FEATURES);
    paramXMLParserConfiguration.addRecognizedProperties(RECOGNIZED_PROPERTIES);
    try
    {
      paramXMLParserConfiguration.setFeature("http://xml.org/sax/features/allow-dtd-events-after-endDTD", false);
    }
    catch (XMLConfigurationException localXMLConfigurationException) {}
  }
  
  public void startDocument(XMLLocator paramXMLLocator, String paramString, NamespaceContext paramNamespaceContext, Augmentations paramAugmentations)
    throws XNIException
  {
    this.fNamespaceContext = paramNamespaceContext;
    try
    {
      if (this.fDocumentHandler != null)
      {
        if (paramXMLLocator != null) {
          this.fDocumentHandler.setDocumentLocator(new LocatorProxy(paramXMLLocator));
        }
        this.fDocumentHandler.startDocument();
      }
      if (this.fContentHandler != null)
      {
        if (paramXMLLocator != null) {
          this.fContentHandler.setDocumentLocator(new LocatorProxy(paramXMLLocator));
        }
        this.fContentHandler.startDocument();
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void xmlDecl(String paramString1, String paramString2, String paramString3, Augmentations paramAugmentations)
    throws XNIException
  {
    this.fVersion = paramString1;
    this.fStandalone = "yes".equals(paramString3);
  }
  
  public void doctypeDecl(String paramString1, String paramString2, String paramString3, Augmentations paramAugmentations)
    throws XNIException
  {
    this.fInDTD = true;
    try
    {
      if (this.fLexicalHandler != null) {
        this.fLexicalHandler.startDTD(paramString1, paramString2, paramString3);
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
    if (this.fDeclHandler != null) {
      this.fDeclaredAttrs = new SymbolHash();
    }
  }
  
  public void startGeneralEntity(String paramString1, XMLResourceIdentifier paramXMLResourceIdentifier, String paramString2, Augmentations paramAugmentations)
    throws XNIException
  {
    try
    {
      if ((paramAugmentations != null) && (Boolean.TRUE.equals(paramAugmentations.getItem("ENTITY_SKIPPED"))))
      {
        if (this.fContentHandler != null) {
          this.fContentHandler.skippedEntity(paramString1);
        }
      }
      else if (this.fLexicalHandler != null) {
        this.fLexicalHandler.startEntity(paramString1);
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void endGeneralEntity(String paramString, Augmentations paramAugmentations)
    throws XNIException
  {
    try
    {
      if (((paramAugmentations == null) || (!Boolean.TRUE.equals(paramAugmentations.getItem("ENTITY_SKIPPED")))) && (this.fLexicalHandler != null)) {
        this.fLexicalHandler.endEntity(paramString);
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void startElement(QName paramQName, XMLAttributes paramXMLAttributes, Augmentations paramAugmentations)
    throws XNIException
  {
    try
    {
      if (this.fDocumentHandler != null)
      {
        this.fAttributesProxy.setAttributes(paramXMLAttributes);
        this.fDocumentHandler.startElement(paramQName.rawname, this.fAttributesProxy);
      }
      if (this.fContentHandler != null)
      {
        if (this.fNamespaces)
        {
          startNamespaceMapping();
          int i = paramXMLAttributes.getLength();
          int j;
          if (!this.fNamespacePrefixes) {
            for (j = i - 1; j >= 0; j--)
            {
              paramXMLAttributes.getName(j, this.fQName);
              if ((this.fQName.prefix == XMLSymbols.PREFIX_XMLNS) || (this.fQName.rawname == XMLSymbols.PREFIX_XMLNS)) {
                paramXMLAttributes.removeAttributeAt(j);
              }
            }
          } else if (!this.fXMLNSURIs) {
            for (j = i - 1; j >= 0; j--)
            {
              paramXMLAttributes.getName(j, this.fQName);
              if ((this.fQName.prefix == XMLSymbols.PREFIX_XMLNS) || (this.fQName.rawname == XMLSymbols.PREFIX_XMLNS))
              {
                this.fQName.prefix = "";
                this.fQName.uri = "";
                this.fQName.localpart = "";
                paramXMLAttributes.setName(j, this.fQName);
              }
            }
          }
        }
        this.fAugmentations = paramAugmentations;
        String str1 = paramQName.uri != null ? paramQName.uri : "";
        String str2 = this.fNamespaces ? paramQName.localpart : "";
        this.fAttributesProxy.setAttributes(paramXMLAttributes);
        this.fContentHandler.startElement(str1, str2, paramQName.rawname, this.fAttributesProxy);
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void characters(XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {
    if (paramXMLString.length == 0) {
      return;
    }
    try
    {
      if (this.fDocumentHandler != null) {
        this.fDocumentHandler.characters(paramXMLString.ch, paramXMLString.offset, paramXMLString.length);
      }
      if (this.fContentHandler != null) {
        this.fContentHandler.characters(paramXMLString.ch, paramXMLString.offset, paramXMLString.length);
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void ignorableWhitespace(XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {
    try
    {
      if (this.fDocumentHandler != null) {
        this.fDocumentHandler.ignorableWhitespace(paramXMLString.ch, paramXMLString.offset, paramXMLString.length);
      }
      if (this.fContentHandler != null) {
        this.fContentHandler.ignorableWhitespace(paramXMLString.ch, paramXMLString.offset, paramXMLString.length);
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void endElement(QName paramQName, Augmentations paramAugmentations)
    throws XNIException
  {
    try
    {
      if (this.fDocumentHandler != null) {
        this.fDocumentHandler.endElement(paramQName.rawname);
      }
      if (this.fContentHandler != null)
      {
        this.fAugmentations = paramAugmentations;
        String str1 = paramQName.uri != null ? paramQName.uri : "";
        String str2 = this.fNamespaces ? paramQName.localpart : "";
        this.fContentHandler.endElement(str1, str2, paramQName.rawname);
        if (this.fNamespaces) {
          endNamespaceMapping();
        }
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void startCDATA(Augmentations paramAugmentations)
    throws XNIException
  {
    try
    {
      if (this.fLexicalHandler != null) {
        this.fLexicalHandler.startCDATA();
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void endCDATA(Augmentations paramAugmentations)
    throws XNIException
  {
    try
    {
      if (this.fLexicalHandler != null) {
        this.fLexicalHandler.endCDATA();
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void comment(XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {
    try
    {
      if (this.fLexicalHandler != null) {
        this.fLexicalHandler.comment(paramXMLString.ch, 0, paramXMLString.length);
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void processingInstruction(String paramString, XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {
    try
    {
      if (this.fDocumentHandler != null) {
        this.fDocumentHandler.processingInstruction(paramString, paramXMLString.toString());
      }
      if (this.fContentHandler != null) {
        this.fContentHandler.processingInstruction(paramString, paramXMLString.toString());
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void endDocument(Augmentations paramAugmentations)
    throws XNIException
  {
    try
    {
      if (this.fDocumentHandler != null) {
        this.fDocumentHandler.endDocument();
      }
      if (this.fContentHandler != null) {
        this.fContentHandler.endDocument();
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void startExternalSubset(XMLResourceIdentifier paramXMLResourceIdentifier, Augmentations paramAugmentations)
    throws XNIException
  {
    startParameterEntity("[dtd]", null, null, paramAugmentations);
  }
  
  public void endExternalSubset(Augmentations paramAugmentations)
    throws XNIException
  {
    endParameterEntity("[dtd]", paramAugmentations);
  }
  
  public void startParameterEntity(String paramString1, XMLResourceIdentifier paramXMLResourceIdentifier, String paramString2, Augmentations paramAugmentations)
    throws XNIException
  {
    try
    {
      if ((paramAugmentations != null) && (Boolean.TRUE.equals(paramAugmentations.getItem("ENTITY_SKIPPED"))))
      {
        if (this.fContentHandler != null) {
          this.fContentHandler.skippedEntity(paramString1);
        }
      }
      else if ((this.fLexicalHandler != null) && (this.fLexicalHandlerParameterEntities)) {
        this.fLexicalHandler.startEntity(paramString1);
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void endParameterEntity(String paramString, Augmentations paramAugmentations)
    throws XNIException
  {
    try
    {
      if (((paramAugmentations == null) || (!Boolean.TRUE.equals(paramAugmentations.getItem("ENTITY_SKIPPED")))) && (this.fLexicalHandler != null) && (this.fLexicalHandlerParameterEntities)) {
        this.fLexicalHandler.endEntity(paramString);
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void elementDecl(String paramString1, String paramString2, Augmentations paramAugmentations)
    throws XNIException
  {
    try
    {
      if (this.fDeclHandler != null) {
        this.fDeclHandler.elementDecl(paramString1, paramString2);
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void attributeDecl(String paramString1, String paramString2, String paramString3, String[] paramArrayOfString, String paramString4, XMLString paramXMLString1, XMLString paramXMLString2, Augmentations paramAugmentations)
    throws XNIException
  {
    try
    {
      if (this.fDeclHandler != null)
      {
        String str = paramString1 + "<" + paramString2;
        if (this.fDeclaredAttrs.get(str) != null) {
          return;
        }
        this.fDeclaredAttrs.put(str, Boolean.TRUE);
        if ((paramString3.equals("NOTATION")) || (paramString3.equals("ENUMERATION")))
        {
          localObject = new StringBuffer();
          if (paramString3.equals("NOTATION"))
          {
            ((StringBuffer)localObject).append(paramString3);
            ((StringBuffer)localObject).append(" (");
          }
          else
          {
            ((StringBuffer)localObject).append("(");
          }
          for (int i = 0; i < paramArrayOfString.length; i++)
          {
            ((StringBuffer)localObject).append(paramArrayOfString[i]);
            if (i < paramArrayOfString.length - 1) {
              ((StringBuffer)localObject).append('|');
            }
          }
          ((StringBuffer)localObject).append(')');
          paramString3 = ((StringBuffer)localObject).toString();
        }
        Object localObject = paramXMLString1 == null ? null : paramXMLString1.toString();
        this.fDeclHandler.attributeDecl(paramString1, paramString2, paramString3, paramString4, (String)localObject);
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void internalEntityDecl(String paramString, XMLString paramXMLString1, XMLString paramXMLString2, Augmentations paramAugmentations)
    throws XNIException
  {
    try
    {
      if (this.fDeclHandler != null) {
        this.fDeclHandler.internalEntityDecl(paramString, paramXMLString1.toString());
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void externalEntityDecl(String paramString, XMLResourceIdentifier paramXMLResourceIdentifier, Augmentations paramAugmentations)
    throws XNIException
  {
    try
    {
      if (this.fDeclHandler != null)
      {
        String str1 = paramXMLResourceIdentifier.getPublicId();
        String str2 = this.fResolveDTDURIs ? paramXMLResourceIdentifier.getExpandedSystemId() : paramXMLResourceIdentifier.getLiteralSystemId();
        this.fDeclHandler.externalEntityDecl(paramString, str1, str2);
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void unparsedEntityDecl(String paramString1, XMLResourceIdentifier paramXMLResourceIdentifier, String paramString2, Augmentations paramAugmentations)
    throws XNIException
  {
    try
    {
      if (this.fDTDHandler != null)
      {
        String str1 = paramXMLResourceIdentifier.getPublicId();
        String str2 = this.fResolveDTDURIs ? paramXMLResourceIdentifier.getExpandedSystemId() : paramXMLResourceIdentifier.getLiteralSystemId();
        this.fDTDHandler.unparsedEntityDecl(paramString1, str1, str2, paramString2);
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void notationDecl(String paramString, XMLResourceIdentifier paramXMLResourceIdentifier, Augmentations paramAugmentations)
    throws XNIException
  {
    try
    {
      if (this.fDTDHandler != null)
      {
        String str1 = paramXMLResourceIdentifier.getPublicId();
        String str2 = this.fResolveDTDURIs ? paramXMLResourceIdentifier.getExpandedSystemId() : paramXMLResourceIdentifier.getLiteralSystemId();
        this.fDTDHandler.notationDecl(paramString, str1, str2);
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
  }
  
  public void endDTD(Augmentations paramAugmentations)
    throws XNIException
  {
    this.fInDTD = false;
    try
    {
      if (this.fLexicalHandler != null) {
        this.fLexicalHandler.endDTD();
      }
    }
    catch (SAXException localSAXException)
    {
      throw new XNIException(localSAXException);
    }
    if (this.fDeclaredAttrs != null) {
      this.fDeclaredAttrs.clear();
    }
  }
  
  public void parse(String paramString)
    throws SAXException, IOException
  {
    XMLInputSource localXMLInputSource = new XMLInputSource(null, paramString, null);
    try
    {
      parse(localXMLInputSource);
    }
    catch (XMLParseException localXMLParseException)
    {
      Exception localException = localXMLParseException.getException();
      if ((localException == null) || ((localException instanceof CharConversionException)))
      {
        localObject = new Locator2Impl();
        ((Locator2Impl)localObject).setXMLVersion(this.fVersion);
        ((LocatorImpl)localObject).setPublicId(localXMLParseException.getPublicId());
        ((LocatorImpl)localObject).setSystemId(localXMLParseException.getExpandedSystemId());
        ((LocatorImpl)localObject).setLineNumber(localXMLParseException.getLineNumber());
        ((LocatorImpl)localObject).setColumnNumber(localXMLParseException.getColumnNumber());
        throw (localException == null ? new SAXParseException(localXMLParseException.getMessage(), (Locator)localObject) : new SAXParseException(localXMLParseException.getMessage(), (Locator)localObject, localException));
      }
      if ((localException instanceof SAXException)) {
        throw ((SAXException)localException);
      }
      if ((localException instanceof IOException)) {
        throw ((IOException)localException);
      }
      throw new SAXException(localException);
    }
    catch (XNIException localXNIException)
    {
      Object localObject = localXNIException.getException();
      if (localObject == null) {
        throw new SAXException(localXNIException.getMessage());
      }
      if ((localObject instanceof SAXException)) {
        throw ((SAXException)localObject);
      }
      if ((localObject instanceof IOException)) {
        throw ((IOException)localObject);
      }
      throw new SAXException((Exception)localObject);
    }
  }
  
  public void parse(InputSource paramInputSource)
    throws SAXException, IOException
  {
    try
    {
      XMLInputSource localXMLInputSource = new XMLInputSource(paramInputSource.getPublicId(), paramInputSource.getSystemId(), null);
      localXMLInputSource.setByteStream(paramInputSource.getByteStream());
      localXMLInputSource.setCharacterStream(paramInputSource.getCharacterStream());
      localXMLInputSource.setEncoding(paramInputSource.getEncoding());
      parse(localXMLInputSource);
    }
    catch (XMLParseException localXMLParseException)
    {
      Exception localException = localXMLParseException.getException();
      if ((localException == null) || ((localException instanceof CharConversionException)))
      {
        localObject = new Locator2Impl();
        ((Locator2Impl)localObject).setXMLVersion(this.fVersion);
        ((LocatorImpl)localObject).setPublicId(localXMLParseException.getPublicId());
        ((LocatorImpl)localObject).setSystemId(localXMLParseException.getExpandedSystemId());
        ((LocatorImpl)localObject).setLineNumber(localXMLParseException.getLineNumber());
        ((LocatorImpl)localObject).setColumnNumber(localXMLParseException.getColumnNumber());
        throw (localException == null ? new SAXParseException(localXMLParseException.getMessage(), (Locator)localObject) : new SAXParseException(localXMLParseException.getMessage(), (Locator)localObject, localException));
      }
      if ((localException instanceof SAXException)) {
        throw ((SAXException)localException);
      }
      if ((localException instanceof IOException)) {
        throw ((IOException)localException);
      }
      throw new SAXException(localException);
    }
    catch (XNIException localXNIException)
    {
      Object localObject = localXNIException.getException();
      if (localObject == null) {
        throw new SAXException(localXNIException.getMessage());
      }
      if ((localObject instanceof SAXException)) {
        throw ((SAXException)localObject);
      }
      if ((localObject instanceof IOException)) {
        throw ((IOException)localObject);
      }
      throw new SAXException((Exception)localObject);
    }
  }
  
  public void setEntityResolver(EntityResolver paramEntityResolver)
  {
    try
    {
      XMLEntityResolver localXMLEntityResolver = (XMLEntityResolver)this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/entity-resolver");
      Object localObject;
      if ((this.fUseEntityResolver2) && ((paramEntityResolver instanceof EntityResolver2)))
      {
        if ((localXMLEntityResolver instanceof EntityResolver2Wrapper))
        {
          localObject = (EntityResolver2Wrapper)localXMLEntityResolver;
          ((EntityResolver2Wrapper)localObject).setEntityResolver((EntityResolver2)paramEntityResolver);
        }
        else
        {
          this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/entity-resolver", new EntityResolver2Wrapper((EntityResolver2)paramEntityResolver));
        }
      }
      else if ((localXMLEntityResolver instanceof EntityResolverWrapper))
      {
        localObject = (EntityResolverWrapper)localXMLEntityResolver;
        ((EntityResolverWrapper)localObject).setEntityResolver(paramEntityResolver);
      }
      else
      {
        this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/entity-resolver", new EntityResolverWrapper(paramEntityResolver));
      }
    }
    catch (XMLConfigurationException localXMLConfigurationException) {}
  }
  
  public EntityResolver getEntityResolver()
  {
    Object localObject = null;
    try
    {
      XMLEntityResolver localXMLEntityResolver = (XMLEntityResolver)this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/entity-resolver");
      if (localXMLEntityResolver != null) {
        if ((localXMLEntityResolver instanceof EntityResolverWrapper)) {
          localObject = ((EntityResolverWrapper)localXMLEntityResolver).getEntityResolver();
        } else if ((localXMLEntityResolver instanceof EntityResolver2Wrapper)) {
          localObject = ((EntityResolver2Wrapper)localXMLEntityResolver).getEntityResolver();
        }
      }
    }
    catch (XMLConfigurationException localXMLConfigurationException) {}
    return localObject;
  }
  
  public void setErrorHandler(ErrorHandler paramErrorHandler)
  {
    try
    {
      XMLErrorHandler localXMLErrorHandler = (XMLErrorHandler)this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/error-handler");
      if ((localXMLErrorHandler instanceof ErrorHandlerWrapper))
      {
        ErrorHandlerWrapper localErrorHandlerWrapper = (ErrorHandlerWrapper)localXMLErrorHandler;
        localErrorHandlerWrapper.setErrorHandler(paramErrorHandler);
      }
      else
      {
        this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/error-handler", new ErrorHandlerWrapper(paramErrorHandler));
      }
    }
    catch (XMLConfigurationException localXMLConfigurationException) {}
  }
  
  public ErrorHandler getErrorHandler()
  {
    ErrorHandler localErrorHandler = null;
    try
    {
      XMLErrorHandler localXMLErrorHandler = (XMLErrorHandler)this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/error-handler");
      if ((localXMLErrorHandler != null) && ((localXMLErrorHandler instanceof ErrorHandlerWrapper))) {
        localErrorHandler = ((ErrorHandlerWrapper)localXMLErrorHandler).getErrorHandler();
      }
    }
    catch (XMLConfigurationException localXMLConfigurationException) {}
    return localErrorHandler;
  }
  
  public void setLocale(Locale paramLocale)
    throws SAXException
  {
    this.fConfiguration.setLocale(paramLocale);
  }
  
  public void setDTDHandler(DTDHandler paramDTDHandler)
  {
    this.fDTDHandler = paramDTDHandler;
  }
  
  public void setDocumentHandler(DocumentHandler paramDocumentHandler)
  {
    this.fDocumentHandler = paramDocumentHandler;
  }
  
  public void setContentHandler(ContentHandler paramContentHandler)
  {
    this.fContentHandler = paramContentHandler;
  }
  
  public ContentHandler getContentHandler()
  {
    return this.fContentHandler;
  }
  
  public DTDHandler getDTDHandler()
  {
    return this.fDTDHandler;
  }
  
  public void setFeature(String paramString, boolean paramBoolean)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    try
    {
      if (paramString.startsWith("http://xml.org/sax/features/"))
      {
        int i = paramString.length() - "http://xml.org/sax/features/".length();
        if ((i == "namespaces".length()) && (paramString.endsWith("namespaces")))
        {
          this.fConfiguration.setFeature(paramString, paramBoolean);
          this.fNamespaces = paramBoolean;
          return;
        }
        if ((i == "namespace-prefixes".length()) && (paramString.endsWith("namespace-prefixes")))
        {
          this.fNamespacePrefixes = paramBoolean;
          return;
        }
        if ((i == "string-interning".length()) && (paramString.endsWith("string-interning")))
        {
          if (!paramBoolean) {
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "false-not-supported", new Object[] { paramString }));
          }
          return;
        }
        if ((i == "lexical-handler/parameter-entities".length()) && (paramString.endsWith("lexical-handler/parameter-entities")))
        {
          this.fLexicalHandlerParameterEntities = paramBoolean;
          return;
        }
        if ((i == "resolve-dtd-uris".length()) && (paramString.endsWith("resolve-dtd-uris")))
        {
          this.fResolveDTDURIs = paramBoolean;
          return;
        }
        if ((i == "unicode-normalization-checking".length()) && (paramString.endsWith("unicode-normalization-checking")))
        {
          if (paramBoolean) {
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "true-not-supported", new Object[] { paramString }));
          }
          return;
        }
        if ((i == "xmlns-uris".length()) && (paramString.endsWith("xmlns-uris")))
        {
          this.fXMLNSURIs = paramBoolean;
          return;
        }
        if ((i == "use-entity-resolver2".length()) && (paramString.endsWith("use-entity-resolver2")))
        {
          if (paramBoolean != this.fUseEntityResolver2)
          {
            this.fUseEntityResolver2 = paramBoolean;
            setEntityResolver(getEntityResolver());
          }
          return;
        }
        if (((i == "is-standalone".length()) && (paramString.endsWith("is-standalone"))) || ((i == "use-attributes2".length()) && (paramString.endsWith("use-attributes2"))) || ((i == "use-locator2".length()) && (paramString.endsWith("use-locator2"))) || ((i == "xml-1.1".length()) && (paramString.endsWith("xml-1.1")))) {
          throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "feature-read-only", new Object[] { paramString }));
        }
      }
      this.fConfiguration.setFeature(paramString, paramBoolean);
    }
    catch (XMLConfigurationException localXMLConfigurationException)
    {
      String str = localXMLConfigurationException.getIdentifier();
      if (localXMLConfigurationException.getType() == 0) {
        throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "feature-not-recognized", new Object[] { str }));
      }
      throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "feature-not-supported", new Object[] { str }));
    }
  }
  
  public boolean getFeature(String paramString)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    try
    {
      if (paramString.startsWith("http://xml.org/sax/features/"))
      {
        int i = paramString.length() - "http://xml.org/sax/features/".length();
        if ((i == "namespace-prefixes".length()) && (paramString.endsWith("namespace-prefixes"))) {
          return this.fNamespacePrefixes;
        }
        if ((i == "string-interning".length()) && (paramString.endsWith("string-interning"))) {
          return true;
        }
        if ((i == "is-standalone".length()) && (paramString.endsWith("is-standalone"))) {
          return this.fStandalone;
        }
        if ((i == "xml-1.1".length()) && (paramString.endsWith("xml-1.1"))) {
          return this.fConfiguration instanceof XML11Configurable;
        }
        if ((i == "lexical-handler/parameter-entities".length()) && (paramString.endsWith("lexical-handler/parameter-entities"))) {
          return this.fLexicalHandlerParameterEntities;
        }
        if ((i == "resolve-dtd-uris".length()) && (paramString.endsWith("resolve-dtd-uris"))) {
          return this.fResolveDTDURIs;
        }
        if ((i == "xmlns-uris".length()) && (paramString.endsWith("xmlns-uris"))) {
          return this.fXMLNSURIs;
        }
        if ((i == "unicode-normalization-checking".length()) && (paramString.endsWith("unicode-normalization-checking"))) {
          return false;
        }
        if ((i == "use-entity-resolver2".length()) && (paramString.endsWith("use-entity-resolver2"))) {
          return this.fUseEntityResolver2;
        }
        if (((i == "use-attributes2".length()) && (paramString.endsWith("use-attributes2"))) || ((i == "use-locator2".length()) && (paramString.endsWith("use-locator2")))) {
          return true;
        }
      }
      return this.fConfiguration.getFeature(paramString);
    }
    catch (XMLConfigurationException localXMLConfigurationException)
    {
      String str = localXMLConfigurationException.getIdentifier();
      if (localXMLConfigurationException.getType() == 0) {
        throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "feature-not-recognized", new Object[] { str }));
      }
      throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "feature-not-supported", new Object[] { str }));
    }
  }
  
  public void setProperty(String paramString, Object paramObject)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    try
    {
      if (paramString.startsWith("http://xml.org/sax/properties/"))
      {
        int i = paramString.length() - "http://xml.org/sax/properties/".length();
        if ((i == "lexical-handler".length()) && (paramString.endsWith("lexical-handler")))
        {
          try
          {
            setLexicalHandler((LexicalHandler)paramObject);
          }
          catch (ClassCastException localClassCastException1)
          {
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "incompatible-class", new Object[] { paramString, "org.xml.sax.ext.LexicalHandler" }));
          }
          return;
        }
        if ((i == "declaration-handler".length()) && (paramString.endsWith("declaration-handler")))
        {
          try
          {
            setDeclHandler((DeclHandler)paramObject);
          }
          catch (ClassCastException localClassCastException2)
          {
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "incompatible-class", new Object[] { paramString, "org.xml.sax.ext.DeclHandler" }));
          }
          return;
        }
        if (((i == "dom-node".length()) && (paramString.endsWith("dom-node"))) || ((i == "document-xml-version".length()) && (paramString.endsWith("document-xml-version")))) {
          throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-read-only", new Object[] { paramString }));
        }
      }
      this.fConfiguration.setProperty(paramString, paramObject);
    }
    catch (XMLConfigurationException localXMLConfigurationException)
    {
      String str = localXMLConfigurationException.getIdentifier();
      if (localXMLConfigurationException.getType() == 0) {
        throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-recognized", new Object[] { str }));
      }
      throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-supported", new Object[] { str }));
    }
  }
  
  public Object getProperty(String paramString)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    try
    {
      if (paramString.startsWith("http://xml.org/sax/properties/"))
      {
        int i = paramString.length() - "http://xml.org/sax/properties/".length();
        if ((i == "document-xml-version".length()) && (paramString.endsWith("document-xml-version"))) {
          return this.fVersion;
        }
        if ((i == "lexical-handler".length()) && (paramString.endsWith("lexical-handler"))) {
          return getLexicalHandler();
        }
        if ((i == "declaration-handler".length()) && (paramString.endsWith("declaration-handler"))) {
          return getDeclHandler();
        }
        if ((i == "dom-node".length()) && (paramString.endsWith("dom-node"))) {
          throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "dom-node-read-not-supported", null));
        }
      }
      return this.fConfiguration.getProperty(paramString);
    }
    catch (XMLConfigurationException localXMLConfigurationException)
    {
      String str = localXMLConfigurationException.getIdentifier();
      if (localXMLConfigurationException.getType() == 0) {
        throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-recognized", new Object[] { str }));
      }
      throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-supported", new Object[] { str }));
    }
  }
  
  protected void setDeclHandler(DeclHandler paramDeclHandler)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (this.fParseInProgress) {
      throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-parsing-supported", new Object[] { "http://xml.org/sax/properties/declaration-handler" }));
    }
    this.fDeclHandler = paramDeclHandler;
  }
  
  protected DeclHandler getDeclHandler()
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    return this.fDeclHandler;
  }
  
  protected void setLexicalHandler(LexicalHandler paramLexicalHandler)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (this.fParseInProgress) {
      throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-parsing-supported", new Object[] { "http://xml.org/sax/properties/lexical-handler" }));
    }
    this.fLexicalHandler = paramLexicalHandler;
  }
  
  protected LexicalHandler getLexicalHandler()
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    return this.fLexicalHandler;
  }
  
  protected final void startNamespaceMapping()
    throws SAXException
  {
    int i = this.fNamespaceContext.getDeclaredPrefixCount();
    if (i > 0)
    {
      String str1 = null;
      String str2 = null;
      for (int j = 0; j < i; j++)
      {
        str1 = this.fNamespaceContext.getDeclaredPrefixAt(j);
        str2 = this.fNamespaceContext.getURI(str1);
        this.fContentHandler.startPrefixMapping(str1, str2 == null ? "" : str2);
      }
    }
  }
  
  protected final void endNamespaceMapping()
    throws SAXException
  {
    int i = this.fNamespaceContext.getDeclaredPrefixCount();
    if (i > 0) {
      for (int j = 0; j < i; j++) {
        this.fContentHandler.endPrefixMapping(this.fNamespaceContext.getDeclaredPrefixAt(j));
      }
    }
  }
  
  public void reset()
    throws XNIException
  {
    super.reset();
    this.fInDTD = false;
    this.fVersion = "1.0";
    this.fStandalone = false;
    this.fNamespaces = this.fConfiguration.getFeature("http://xml.org/sax/features/namespaces");
    this.fAugmentations = null;
    this.fDeclaredAttrs = null;
  }
  
  public ElementPSVI getElementPSVI()
  {
    return this.fAugmentations != null ? (ElementPSVI)this.fAugmentations.getItem("ELEMENT_PSVI") : null;
  }
  
  public AttributePSVI getAttributePSVI(int paramInt)
  {
    return (AttributePSVI)this.fAttributesProxy.fAttributes.getAugmentations(paramInt).getItem("ATTRIBUTE_PSVI");
  }
  
  public AttributePSVI getAttributePSVIByName(String paramString1, String paramString2)
  {
    return (AttributePSVI)this.fAttributesProxy.fAttributes.getAugmentations(paramString1, paramString2).getItem("ATTRIBUTE_PSVI");
  }
  
  protected static final class AttributesProxy
    implements AttributeList, Attributes2
  {
    protected XMLAttributes fAttributes;
    
    public void setAttributes(XMLAttributes paramXMLAttributes)
    {
      this.fAttributes = paramXMLAttributes;
    }
    
    public int getLength()
    {
      return this.fAttributes.getLength();
    }
    
    public String getName(int paramInt)
    {
      return this.fAttributes.getQName(paramInt);
    }
    
    public String getQName(int paramInt)
    {
      return this.fAttributes.getQName(paramInt);
    }
    
    public String getURI(int paramInt)
    {
      String str = this.fAttributes.getURI(paramInt);
      return str != null ? str : "";
    }
    
    public String getLocalName(int paramInt)
    {
      return this.fAttributes.getLocalName(paramInt);
    }
    
    public String getType(int paramInt)
    {
      return this.fAttributes.getType(paramInt);
    }
    
    public String getType(String paramString)
    {
      return this.fAttributes.getType(paramString);
    }
    
    public String getType(String paramString1, String paramString2)
    {
      return paramString1.equals("") ? this.fAttributes.getType(null, paramString2) : this.fAttributes.getType(paramString1, paramString2);
    }
    
    public String getValue(int paramInt)
    {
      return this.fAttributes.getValue(paramInt);
    }
    
    public String getValue(String paramString)
    {
      return this.fAttributes.getValue(paramString);
    }
    
    public String getValue(String paramString1, String paramString2)
    {
      return paramString1.equals("") ? this.fAttributes.getValue(null, paramString2) : this.fAttributes.getValue(paramString1, paramString2);
    }
    
    public int getIndex(String paramString)
    {
      return this.fAttributes.getIndex(paramString);
    }
    
    public int getIndex(String paramString1, String paramString2)
    {
      return paramString1.equals("") ? this.fAttributes.getIndex(null, paramString2) : this.fAttributes.getIndex(paramString1, paramString2);
    }
    
    public boolean isDeclared(int paramInt)
    {
      if ((paramInt < 0) || (paramInt >= this.fAttributes.getLength())) {
        throw new ArrayIndexOutOfBoundsException(paramInt);
      }
      return Boolean.TRUE.equals(this.fAttributes.getAugmentations(paramInt).getItem("ATTRIBUTE_DECLARED"));
    }
    
    public boolean isDeclared(String paramString)
    {
      int i = getIndex(paramString);
      if (i == -1) {
        throw new IllegalArgumentException(paramString);
      }
      return Boolean.TRUE.equals(this.fAttributes.getAugmentations(i).getItem("ATTRIBUTE_DECLARED"));
    }
    
    public boolean isDeclared(String paramString1, String paramString2)
    {
      int i = getIndex(paramString1, paramString2);
      if (i == -1) {
        throw new IllegalArgumentException(paramString2);
      }
      return Boolean.TRUE.equals(this.fAttributes.getAugmentations(i).getItem("ATTRIBUTE_DECLARED"));
    }
    
    public boolean isSpecified(int paramInt)
    {
      if ((paramInt < 0) || (paramInt >= this.fAttributes.getLength())) {
        throw new ArrayIndexOutOfBoundsException(paramInt);
      }
      return this.fAttributes.isSpecified(paramInt);
    }
    
    public boolean isSpecified(String paramString)
    {
      int i = getIndex(paramString);
      if (i == -1) {
        throw new IllegalArgumentException(paramString);
      }
      return this.fAttributes.isSpecified(i);
    }
    
    public boolean isSpecified(String paramString1, String paramString2)
    {
      int i = getIndex(paramString1, paramString2);
      if (i == -1) {
        throw new IllegalArgumentException(paramString2);
      }
      return this.fAttributes.isSpecified(i);
    }
  }
  
  protected static final class LocatorProxy
    implements Locator2
  {
    protected XMLLocator fLocator;
    
    public LocatorProxy(XMLLocator paramXMLLocator)
    {
      this.fLocator = paramXMLLocator;
    }
    
    public String getPublicId()
    {
      return this.fLocator.getPublicId();
    }
    
    public String getSystemId()
    {
      return this.fLocator.getExpandedSystemId();
    }
    
    public int getLineNumber()
    {
      return this.fLocator.getLineNumber();
    }
    
    public int getColumnNumber()
    {
      return this.fLocator.getColumnNumber();
    }
    
    public String getXMLVersion()
    {
      return this.fLocator.getXMLVersion();
    }
    
    public String getEncoding()
    {
      return this.fLocator.getEncoding();
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.parsers.AbstractSAXParser
 * JD-Core Version:    0.7.0.1
 */
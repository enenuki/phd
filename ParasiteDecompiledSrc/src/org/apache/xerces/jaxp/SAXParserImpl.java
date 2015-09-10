package org.apache.xerces.jaxp;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.impl.xs.XMLSchemaValidator;
import org.apache.xerces.jaxp.validation.XSGrammarPoolContainer;
import org.apache.xerces.parsers.AbstractSAXParser;
import org.apache.xerces.parsers.AbstractXMLDocumentParser;
import org.apache.xerces.parsers.XMLParser;
import org.apache.xerces.util.SAXMessageFormatter;
import org.apache.xerces.util.SecurityManager;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.apache.xerces.xs.AttributePSVI;
import org.apache.xerces.xs.ElementPSVI;
import org.apache.xerces.xs.PSVIProvider;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParserImpl
  extends javax.xml.parsers.SAXParser
  implements JAXPConstants, PSVIProvider
{
  private static final String NAMESPACES_FEATURE = "http://xml.org/sax/features/namespaces";
  private static final String NAMESPACE_PREFIXES_FEATURE = "http://xml.org/sax/features/namespace-prefixes";
  private static final String VALIDATION_FEATURE = "http://xml.org/sax/features/validation";
  private static final String XMLSCHEMA_VALIDATION_FEATURE = "http://apache.org/xml/features/validation/schema";
  private static final String XINCLUDE_FEATURE = "http://apache.org/xml/features/xinclude";
  private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
  private JAXPSAXParser xmlReader = new JAXPSAXParser(this);
  private String schemaLanguage = null;
  private final Schema grammar;
  private XMLComponent fSchemaValidator;
  private XMLComponentManager fSchemaValidatorComponentManager;
  private ValidationManager fSchemaValidationManager;
  private UnparsedEntityHandler fUnparsedEntityHandler;
  private final ErrorHandler fInitErrorHandler;
  private final EntityResolver fInitEntityResolver;
  
  SAXParserImpl(SAXParserFactoryImpl paramSAXParserFactoryImpl, Hashtable paramHashtable)
    throws SAXException
  {
    this(paramSAXParserFactoryImpl, paramHashtable, false);
  }
  
  SAXParserImpl(SAXParserFactoryImpl paramSAXParserFactoryImpl, Hashtable paramHashtable, boolean paramBoolean)
    throws SAXException
  {
    this.xmlReader.setFeature0("http://xml.org/sax/features/namespaces", paramSAXParserFactoryImpl.isNamespaceAware());
    this.xmlReader.setFeature0("http://xml.org/sax/features/namespace-prefixes", !paramSAXParserFactoryImpl.isNamespaceAware());
    if (paramSAXParserFactoryImpl.isXIncludeAware()) {
      this.xmlReader.setFeature0("http://apache.org/xml/features/xinclude", true);
    }
    if (paramBoolean) {
      this.xmlReader.setProperty0("http://apache.org/xml/properties/security-manager", new SecurityManager());
    }
    setFeatures(paramHashtable);
    if (paramSAXParserFactoryImpl.isValidating())
    {
      this.fInitErrorHandler = new DefaultValidationErrorHandler();
      this.xmlReader.setErrorHandler(this.fInitErrorHandler);
    }
    else
    {
      this.fInitErrorHandler = this.xmlReader.getErrorHandler();
    }
    this.xmlReader.setFeature0("http://xml.org/sax/features/validation", paramSAXParserFactoryImpl.isValidating());
    this.grammar = paramSAXParserFactoryImpl.getSchema();
    if (this.grammar != null)
    {
      XMLParserConfiguration localXMLParserConfiguration = this.xmlReader.getXMLParserConfiguration();
      Object localObject = null;
      if ((this.grammar instanceof XSGrammarPoolContainer))
      {
        localObject = new XMLSchemaValidator();
        this.fSchemaValidationManager = new ValidationManager();
        this.fUnparsedEntityHandler = new UnparsedEntityHandler(this.fSchemaValidationManager);
        localXMLParserConfiguration.setDTDHandler(this.fUnparsedEntityHandler);
        this.fUnparsedEntityHandler.setDTDHandler(this.xmlReader);
        this.xmlReader.setDTDSource(this.fUnparsedEntityHandler);
        this.fSchemaValidatorComponentManager = new SchemaValidatorConfiguration(localXMLParserConfiguration, (XSGrammarPoolContainer)this.grammar, this.fSchemaValidationManager);
      }
      else
      {
        localObject = new JAXPValidatorComponent(this.grammar.newValidatorHandler());
        this.fSchemaValidatorComponentManager = localXMLParserConfiguration;
      }
      localXMLParserConfiguration.addRecognizedFeatures(((XMLComponent)localObject).getRecognizedFeatures());
      localXMLParserConfiguration.addRecognizedProperties(((XMLComponent)localObject).getRecognizedProperties());
      localXMLParserConfiguration.setDocumentHandler((XMLDocumentHandler)localObject);
      ((XMLDocumentSource)localObject).setDocumentHandler(this.xmlReader);
      this.xmlReader.setDocumentSource((XMLDocumentSource)localObject);
      this.fSchemaValidator = ((XMLComponent)localObject);
    }
    this.fInitEntityResolver = this.xmlReader.getEntityResolver();
  }
  
  private void setFeatures(Hashtable paramHashtable)
    throws SAXNotSupportedException, SAXNotRecognizedException
  {
    if (paramHashtable != null)
    {
      Enumeration localEnumeration = paramHashtable.keys();
      while (localEnumeration.hasMoreElements())
      {
        String str = (String)localEnumeration.nextElement();
        boolean bool = ((Boolean)paramHashtable.get(str)).booleanValue();
        this.xmlReader.setFeature0(str, bool);
      }
    }
  }
  
  public Parser getParser()
    throws SAXException
  {
    return this.xmlReader;
  }
  
  public XMLReader getXMLReader()
  {
    return this.xmlReader;
  }
  
  public boolean isNamespaceAware()
  {
    try
    {
      return this.xmlReader.getFeature("http://xml.org/sax/features/namespaces");
    }
    catch (SAXException localSAXException)
    {
      throw new IllegalStateException(localSAXException.getMessage());
    }
  }
  
  public boolean isValidating()
  {
    try
    {
      return this.xmlReader.getFeature("http://xml.org/sax/features/validation");
    }
    catch (SAXException localSAXException)
    {
      throw new IllegalStateException(localSAXException.getMessage());
    }
  }
  
  public boolean isXIncludeAware()
  {
    try
    {
      return this.xmlReader.getFeature("http://apache.org/xml/features/xinclude");
    }
    catch (SAXException localSAXException) {}
    return false;
  }
  
  public void setProperty(String paramString, Object paramObject)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    this.xmlReader.setProperty(paramString, paramObject);
  }
  
  public Object getProperty(String paramString)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    return this.xmlReader.getProperty(paramString);
  }
  
  public void parse(InputSource paramInputSource, DefaultHandler paramDefaultHandler)
    throws SAXException, IOException
  {
    if (paramInputSource == null) {
      throw new IllegalArgumentException();
    }
    if (paramDefaultHandler != null)
    {
      this.xmlReader.setContentHandler(paramDefaultHandler);
      this.xmlReader.setEntityResolver(paramDefaultHandler);
      this.xmlReader.setErrorHandler(paramDefaultHandler);
      this.xmlReader.setDTDHandler(paramDefaultHandler);
      this.xmlReader.setDocumentHandler(null);
    }
    this.xmlReader.parse(paramInputSource);
  }
  
  public void parse(InputSource paramInputSource, HandlerBase paramHandlerBase)
    throws SAXException, IOException
  {
    if (paramInputSource == null) {
      throw new IllegalArgumentException();
    }
    if (paramHandlerBase != null)
    {
      this.xmlReader.setDocumentHandler(paramHandlerBase);
      this.xmlReader.setEntityResolver(paramHandlerBase);
      this.xmlReader.setErrorHandler(paramHandlerBase);
      this.xmlReader.setDTDHandler(paramHandlerBase);
      this.xmlReader.setContentHandler(null);
    }
    this.xmlReader.parse(paramInputSource);
  }
  
  public Schema getSchema()
  {
    return this.grammar;
  }
  
  public void reset()
  {
    try
    {
      this.xmlReader.restoreInitState();
    }
    catch (SAXException localSAXException) {}
    this.xmlReader.setContentHandler(null);
    this.xmlReader.setDTDHandler(null);
    if (this.xmlReader.getErrorHandler() != this.fInitErrorHandler) {
      this.xmlReader.setErrorHandler(this.fInitErrorHandler);
    }
    if (this.xmlReader.getEntityResolver() != this.fInitEntityResolver) {
      this.xmlReader.setEntityResolver(this.fInitEntityResolver);
    }
  }
  
  public ElementPSVI getElementPSVI()
  {
    return this.xmlReader.getElementPSVI();
  }
  
  public AttributePSVI getAttributePSVI(int paramInt)
  {
    return this.xmlReader.getAttributePSVI(paramInt);
  }
  
  public AttributePSVI getAttributePSVIByName(String paramString1, String paramString2)
  {
    return this.xmlReader.getAttributePSVIByName(paramString1, paramString2);
  }
  
  public static class JAXPSAXParser
    extends org.apache.xerces.parsers.SAXParser
  {
    private HashMap fInitFeatures = new HashMap();
    private HashMap fInitProperties = new HashMap();
    private SAXParserImpl fSAXParser;
    
    public JAXPSAXParser() {}
    
    JAXPSAXParser(SAXParserImpl paramSAXParserImpl)
    {
      this.fSAXParser = paramSAXParserImpl;
    }
    
    public synchronized void setFeature(String paramString, boolean paramBoolean)
      throws SAXNotRecognizedException, SAXNotSupportedException
    {
      if (paramString == null) {
        throw new NullPointerException();
      }
      if (paramString.equals("http://javax.xml.XMLConstants/feature/secure-processing"))
      {
        try
        {
          setProperty("http://apache.org/xml/properties/security-manager", paramBoolean ? new SecurityManager() : null);
        }
        catch (SAXNotRecognizedException localSAXNotRecognizedException)
        {
          if (paramBoolean) {
            throw localSAXNotRecognizedException;
          }
        }
        catch (SAXNotSupportedException localSAXNotSupportedException)
        {
          if (paramBoolean) {
            throw localSAXNotSupportedException;
          }
        }
        return;
      }
      if (!this.fInitFeatures.containsKey(paramString))
      {
        boolean bool = super.getFeature(paramString);
        this.fInitFeatures.put(paramString, bool ? Boolean.TRUE : Boolean.FALSE);
      }
      if ((this.fSAXParser != null) && (this.fSAXParser.fSchemaValidator != null)) {
        setSchemaValidatorFeature(paramString, paramBoolean);
      }
      super.setFeature(paramString, paramBoolean);
    }
    
    public synchronized boolean getFeature(String paramString)
      throws SAXNotRecognizedException, SAXNotSupportedException
    {
      if (paramString == null) {
        throw new NullPointerException();
      }
      if (paramString.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
        try
        {
          return super.getProperty("http://apache.org/xml/properties/security-manager") != null;
        }
        catch (SAXException localSAXException)
        {
          return false;
        }
      }
      return super.getFeature(paramString);
    }
    
    public synchronized void setProperty(String paramString, Object paramObject)
      throws SAXNotRecognizedException, SAXNotSupportedException
    {
      if (paramString == null) {
        throw new NullPointerException();
      }
      if (this.fSAXParser != null)
      {
        if ("http://java.sun.com/xml/jaxp/properties/schemaLanguage".equals(paramString))
        {
          if (this.fSAXParser.grammar != null) {
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "schema-already-specified", new Object[] { paramString }));
          }
          if ("http://www.w3.org/2001/XMLSchema".equals(paramObject))
          {
            if (this.fSAXParser.isValidating())
            {
              this.fSAXParser.schemaLanguage = "http://www.w3.org/2001/XMLSchema";
              setFeature("http://apache.org/xml/features/validation/schema", true);
              if (!this.fInitProperties.containsKey("http://java.sun.com/xml/jaxp/properties/schemaLanguage")) {
                this.fInitProperties.put("http://java.sun.com/xml/jaxp/properties/schemaLanguage", super.getProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage"));
              }
              super.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
            }
          }
          else if (paramObject == null)
          {
            this.fSAXParser.schemaLanguage = null;
            setFeature("http://apache.org/xml/features/validation/schema", false);
          }
          else
          {
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "schema-not-supported", null));
          }
          return;
        }
        if ("http://java.sun.com/xml/jaxp/properties/schemaSource".equals(paramString))
        {
          if (this.fSAXParser.grammar != null) {
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "schema-already-specified", new Object[] { paramString }));
          }
          String str = (String)getProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage");
          if ((str != null) && ("http://www.w3.org/2001/XMLSchema".equals(str)))
          {
            if (!this.fInitProperties.containsKey("http://java.sun.com/xml/jaxp/properties/schemaSource")) {
              this.fInitProperties.put("http://java.sun.com/xml/jaxp/properties/schemaSource", super.getProperty("http://java.sun.com/xml/jaxp/properties/schemaSource"));
            }
            super.setProperty(paramString, paramObject);
          }
          else
          {
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "jaxp-order-not-supported", new Object[] { "http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://java.sun.com/xml/jaxp/properties/schemaSource" }));
          }
          return;
        }
      }
      if (!this.fInitProperties.containsKey(paramString)) {
        this.fInitProperties.put(paramString, super.getProperty(paramString));
      }
      if ((this.fSAXParser != null) && (this.fSAXParser.fSchemaValidator != null)) {
        setSchemaValidatorProperty(paramString, paramObject);
      }
      super.setProperty(paramString, paramObject);
    }
    
    public synchronized Object getProperty(String paramString)
      throws SAXNotRecognizedException, SAXNotSupportedException
    {
      if (paramString == null) {
        throw new NullPointerException();
      }
      if ((this.fSAXParser != null) && ("http://java.sun.com/xml/jaxp/properties/schemaLanguage".equals(paramString))) {
        return this.fSAXParser.schemaLanguage;
      }
      return super.getProperty(paramString);
    }
    
    synchronized void restoreInitState()
      throws SAXNotRecognizedException, SAXNotSupportedException
    {
      Iterator localIterator;
      Map.Entry localEntry;
      String str;
      if (!this.fInitFeatures.isEmpty())
      {
        localIterator = this.fInitFeatures.entrySet().iterator();
        while (localIterator.hasNext())
        {
          localEntry = (Map.Entry)localIterator.next();
          str = (String)localEntry.getKey();
          boolean bool = ((Boolean)localEntry.getValue()).booleanValue();
          super.setFeature(str, bool);
        }
        this.fInitFeatures.clear();
      }
      if (!this.fInitProperties.isEmpty())
      {
        localIterator = this.fInitProperties.entrySet().iterator();
        while (localIterator.hasNext())
        {
          localEntry = (Map.Entry)localIterator.next();
          str = (String)localEntry.getKey();
          Object localObject = localEntry.getValue();
          super.setProperty(str, localObject);
        }
        this.fInitProperties.clear();
      }
    }
    
    public void parse(InputSource paramInputSource)
      throws SAXException, IOException
    {
      if ((this.fSAXParser != null) && (this.fSAXParser.fSchemaValidator != null))
      {
        if (this.fSAXParser.fSchemaValidationManager != null)
        {
          this.fSAXParser.fSchemaValidationManager.reset();
          this.fSAXParser.fUnparsedEntityHandler.reset();
        }
        resetSchemaValidator();
      }
      super.parse(paramInputSource);
    }
    
    public void parse(String paramString)
      throws SAXException, IOException
    {
      if ((this.fSAXParser != null) && (this.fSAXParser.fSchemaValidator != null))
      {
        if (this.fSAXParser.fSchemaValidationManager != null)
        {
          this.fSAXParser.fSchemaValidationManager.reset();
          this.fSAXParser.fUnparsedEntityHandler.reset();
        }
        resetSchemaValidator();
      }
      super.parse(paramString);
    }
    
    XMLParserConfiguration getXMLParserConfiguration()
    {
      return this.fConfiguration;
    }
    
    void setFeature0(String paramString, boolean paramBoolean)
      throws SAXNotRecognizedException, SAXNotSupportedException
    {
      super.setFeature(paramString, paramBoolean);
    }
    
    boolean getFeature0(String paramString)
      throws SAXNotRecognizedException, SAXNotSupportedException
    {
      return super.getFeature(paramString);
    }
    
    void setProperty0(String paramString, Object paramObject)
      throws SAXNotRecognizedException, SAXNotSupportedException
    {
      super.setProperty(paramString, paramObject);
    }
    
    Object getProperty0(String paramString)
      throws SAXNotRecognizedException, SAXNotSupportedException
    {
      return super.getProperty(paramString);
    }
    
    private void setSchemaValidatorFeature(String paramString, boolean paramBoolean)
      throws SAXNotRecognizedException, SAXNotSupportedException
    {
      try
      {
        this.fSAXParser.fSchemaValidator.setFeature(paramString, paramBoolean);
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
    
    private void setSchemaValidatorProperty(String paramString, Object paramObject)
      throws SAXNotRecognizedException, SAXNotSupportedException
    {
      try
      {
        this.fSAXParser.fSchemaValidator.setProperty(paramString, paramObject);
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
    
    private void resetSchemaValidator()
      throws SAXException
    {
      try
      {
        this.fSAXParser.fSchemaValidator.reset(this.fSAXParser.fSchemaValidatorComponentManager);
      }
      catch (XMLConfigurationException localXMLConfigurationException)
      {
        throw new SAXException(localXMLConfigurationException);
      }
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.jaxp.SAXParserImpl
 * JD-Core Version:    0.7.0.1
 */
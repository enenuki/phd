package org.apache.xerces.dom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.dv.DTDDVFactory;
import org.apache.xerces.impl.msg.XMLMessageFormatter;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.util.DOMEntityResolverWrapper;
import org.apache.xerces.util.DOMErrorHandlerWrapper;
import org.apache.xerces.util.MessageFormatter;
import org.apache.xerces.util.ParserConfigurationSettings;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.XMLDTDContentModelHandler;
import org.apache.xerces.xni.XMLDTDHandler;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLErrorHandler;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMStringList;
import org.w3c.dom.ls.LSResourceResolver;

public class DOMConfigurationImpl
  extends ParserConfigurationSettings
  implements XMLParserConfiguration, DOMConfiguration
{
  protected static final String XML11_DATATYPE_VALIDATOR_FACTORY = "org.apache.xerces.impl.dv.dtd.XML11DTDDVFactoryImpl";
  protected static final String XERCES_VALIDATION = "http://xml.org/sax/features/validation";
  protected static final String XERCES_NAMESPACES = "http://xml.org/sax/features/namespaces";
  protected static final String SCHEMA = "http://apache.org/xml/features/validation/schema";
  protected static final String SCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
  protected static final String DYNAMIC_VALIDATION = "http://apache.org/xml/features/validation/dynamic";
  protected static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
  protected static final String SCHEMA_ELEMENT_DEFAULT = "http://apache.org/xml/features/validation/schema/element-default";
  protected static final String SEND_PSVI = "http://apache.org/xml/features/validation/schema/augment-psvi";
  protected static final String GENERATE_SYNTHETIC_ANNOTATIONS = "http://apache.org/xml/features/generate-synthetic-annotations";
  protected static final String VALIDATE_ANNOTATIONS = "http://apache.org/xml/features/validate-annotations";
  protected static final String HONOUR_ALL_SCHEMALOCATIONS = "http://apache.org/xml/features/honour-all-schemaLocations";
  protected static final String USE_GRAMMAR_POOL_ONLY = "http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only";
  protected static final String DISALLOW_DOCTYPE_DECL_FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
  protected static final String BALANCE_SYNTAX_TREES = "http://apache.org/xml/features/validation/balance-syntax-trees";
  protected static final String WARN_ON_DUPLICATE_ATTDEF = "http://apache.org/xml/features/validation/warn-on-duplicate-attdef";
  protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
  protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
  protected static final String XML_STRING = "http://xml.org/sax/properties/xml-string";
  protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
  protected static final String GRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
  protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
  protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
  protected static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
  protected static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
  protected static final String DTD_VALIDATOR_PROPERTY = "http://apache.org/xml/properties/internal/validator/dtd";
  protected static final String DTD_VALIDATOR_FACTORY_PROPERTY = "http://apache.org/xml/properties/internal/datatype-validator-factory";
  protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
  protected static final String SCHEMA_LOCATION = "http://apache.org/xml/properties/schema/external-schemaLocation";
  protected static final String SCHEMA_NONS_LOCATION = "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation";
  XMLDocumentHandler fDocumentHandler;
  protected short features = 0;
  protected static final short NAMESPACES = 1;
  protected static final short DTNORMALIZATION = 2;
  protected static final short ENTITIES = 4;
  protected static final short CDATA = 8;
  protected static final short SPLITCDATA = 16;
  protected static final short COMMENTS = 32;
  protected static final short VALIDATE = 64;
  protected static final short PSVI = 128;
  protected static final short WELLFORMED = 256;
  protected static final short NSDECL = 512;
  protected static final short INFOSET_TRUE_PARAMS = 801;
  protected static final short INFOSET_FALSE_PARAMS = 14;
  protected static final short INFOSET_MASK = 815;
  protected SymbolTable fSymbolTable;
  protected ArrayList fComponents;
  protected ValidationManager fValidationManager;
  protected Locale fLocale;
  protected XMLErrorReporter fErrorReporter;
  protected final DOMErrorHandlerWrapper fErrorHandlerWrapper = new DOMErrorHandlerWrapper();
  protected DTDDVFactory fCurrentDVFactory;
  protected DTDDVFactory fDatatypeValidatorFactory;
  protected DTDDVFactory fXML11DatatypeFactory;
  private String fSchemaLocation = null;
  private DOMStringList fRecognizedParameters;
  
  protected DOMConfigurationImpl()
  {
    this(null, null);
  }
  
  protected DOMConfigurationImpl(SymbolTable paramSymbolTable)
  {
    this(paramSymbolTable, null);
  }
  
  protected DOMConfigurationImpl(SymbolTable paramSymbolTable, XMLComponentManager paramXMLComponentManager)
  {
    super(paramXMLComponentManager);
    this.fRecognizedFeatures = new ArrayList();
    this.fRecognizedProperties = new ArrayList();
    this.fFeatures = new HashMap();
    this.fProperties = new HashMap();
    String[] arrayOfString1 = { "http://xml.org/sax/features/validation", "http://xml.org/sax/features/namespaces", "http://apache.org/xml/features/validation/schema", "http://apache.org/xml/features/validation/schema-full-checking", "http://apache.org/xml/features/validation/dynamic", "http://apache.org/xml/features/validation/schema/normalized-value", "http://apache.org/xml/features/validation/schema/element-default", "http://apache.org/xml/features/validation/schema/augment-psvi", "http://apache.org/xml/features/generate-synthetic-annotations", "http://apache.org/xml/features/validate-annotations", "http://apache.org/xml/features/honour-all-schemaLocations", "http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only", "http://apache.org/xml/features/disallow-doctype-decl", "http://apache.org/xml/features/validation/balance-syntax-trees", "http://apache.org/xml/features/validation/warn-on-duplicate-attdef", "http://apache.org/xml/features/internal/parser-settings" };
    addRecognizedFeatures(arrayOfString1);
    setFeature("http://xml.org/sax/features/validation", false);
    setFeature("http://apache.org/xml/features/validation/schema", false);
    setFeature("http://apache.org/xml/features/validation/schema-full-checking", false);
    setFeature("http://apache.org/xml/features/validation/dynamic", false);
    setFeature("http://apache.org/xml/features/validation/schema/normalized-value", false);
    setFeature("http://apache.org/xml/features/validation/schema/element-default", false);
    setFeature("http://xml.org/sax/features/namespaces", true);
    setFeature("http://apache.org/xml/features/validation/schema/augment-psvi", true);
    setFeature("http://apache.org/xml/features/generate-synthetic-annotations", false);
    setFeature("http://apache.org/xml/features/validate-annotations", false);
    setFeature("http://apache.org/xml/features/honour-all-schemaLocations", false);
    setFeature("http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only", false);
    setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
    setFeature("http://apache.org/xml/features/validation/balance-syntax-trees", false);
    setFeature("http://apache.org/xml/features/validation/warn-on-duplicate-attdef", false);
    setFeature("http://apache.org/xml/features/internal/parser-settings", true);
    String[] arrayOfString2 = { "http://xml.org/sax/properties/xml-string", "http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-handler", "http://apache.org/xml/properties/internal/entity-resolver", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-manager", "http://apache.org/xml/properties/internal/validation-manager", "http://apache.org/xml/properties/internal/grammar-pool", "http://java.sun.com/xml/jaxp/properties/schemaSource", "http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://apache.org/xml/properties/schema/external-schemaLocation", "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", "http://apache.org/xml/properties/internal/validator/dtd", "http://apache.org/xml/properties/internal/datatype-validator-factory" };
    addRecognizedProperties(arrayOfString2);
    this.features = ((short)(this.features | 0x1));
    this.features = ((short)(this.features | 0x4));
    this.features = ((short)(this.features | 0x20));
    this.features = ((short)(this.features | 0x8));
    this.features = ((short)(this.features | 0x10));
    this.features = ((short)(this.features | 0x100));
    this.features = ((short)(this.features | 0x200));
    if (paramSymbolTable == null) {
      paramSymbolTable = new SymbolTable();
    }
    this.fSymbolTable = paramSymbolTable;
    this.fComponents = new ArrayList();
    setProperty("http://apache.org/xml/properties/internal/symbol-table", this.fSymbolTable);
    this.fErrorReporter = new XMLErrorReporter();
    setProperty("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
    addComponent(this.fErrorReporter);
    this.fDatatypeValidatorFactory = DTDDVFactory.getInstance();
    this.fXML11DatatypeFactory = DTDDVFactory.getInstance("org.apache.xerces.impl.dv.dtd.XML11DTDDVFactoryImpl");
    this.fCurrentDVFactory = this.fDatatypeValidatorFactory;
    setProperty("http://apache.org/xml/properties/internal/datatype-validator-factory", this.fCurrentDVFactory);
    XMLEntityManager localXMLEntityManager = new XMLEntityManager();
    setProperty("http://apache.org/xml/properties/internal/entity-manager", localXMLEntityManager);
    addComponent(localXMLEntityManager);
    this.fValidationManager = createValidationManager();
    setProperty("http://apache.org/xml/properties/internal/validation-manager", this.fValidationManager);
    Object localObject;
    if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null)
    {
      localObject = new XMLMessageFormatter();
      this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", (MessageFormatter)localObject);
      this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1999/REC-xml-names-19990114", (MessageFormatter)localObject);
    }
    if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/xml-schema-1") == null)
    {
      localObject = null;
      try
      {
        localObject = (MessageFormatter)ObjectFactory.newInstance("org.apache.xerces.impl.xs.XSMessageFormatter", ObjectFactory.findClassLoader(), true);
      }
      catch (Exception localException) {}
      if (localObject != null) {
        this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/xml-schema-1", (MessageFormatter)localObject);
      }
    }
    try
    {
      setLocale(Locale.getDefault());
    }
    catch (XNIException localXNIException) {}
  }
  
  public void parse(XMLInputSource paramXMLInputSource)
    throws XNIException, IOException
  {}
  
  public void setDocumentHandler(XMLDocumentHandler paramXMLDocumentHandler)
  {
    this.fDocumentHandler = paramXMLDocumentHandler;
  }
  
  public XMLDocumentHandler getDocumentHandler()
  {
    return this.fDocumentHandler;
  }
  
  public void setDTDHandler(XMLDTDHandler paramXMLDTDHandler) {}
  
  public XMLDTDHandler getDTDHandler()
  {
    return null;
  }
  
  public void setDTDContentModelHandler(XMLDTDContentModelHandler paramXMLDTDContentModelHandler) {}
  
  public XMLDTDContentModelHandler getDTDContentModelHandler()
  {
    return null;
  }
  
  public void setEntityResolver(XMLEntityResolver paramXMLEntityResolver)
  {
    this.fProperties.put("http://apache.org/xml/properties/internal/entity-resolver", paramXMLEntityResolver);
  }
  
  public XMLEntityResolver getEntityResolver()
  {
    return (XMLEntityResolver)this.fProperties.get("http://apache.org/xml/properties/internal/entity-resolver");
  }
  
  public void setErrorHandler(XMLErrorHandler paramXMLErrorHandler)
  {
    if (paramXMLErrorHandler != null) {
      this.fProperties.put("http://apache.org/xml/properties/internal/error-handler", paramXMLErrorHandler);
    }
  }
  
  public XMLErrorHandler getErrorHandler()
  {
    return (XMLErrorHandler)this.fProperties.get("http://apache.org/xml/properties/internal/error-handler");
  }
  
  public boolean getFeature(String paramString)
    throws XMLConfigurationException
  {
    if (paramString.equals("http://apache.org/xml/features/internal/parser-settings")) {
      return true;
    }
    return super.getFeature(paramString);
  }
  
  public void setFeature(String paramString, boolean paramBoolean)
    throws XMLConfigurationException
  {
    super.setFeature(paramString, paramBoolean);
  }
  
  public void setProperty(String paramString, Object paramObject)
    throws XMLConfigurationException
  {
    super.setProperty(paramString, paramObject);
  }
  
  public void setLocale(Locale paramLocale)
    throws XNIException
  {
    this.fLocale = paramLocale;
    this.fErrorReporter.setLocale(paramLocale);
  }
  
  public Locale getLocale()
  {
    return this.fLocale;
  }
  
  public void setParameter(String paramString, Object paramObject)
    throws DOMException
  {
    int i = 1;
    Object localObject2;
    if ((paramObject instanceof Boolean))
    {
      boolean bool = ((Boolean)paramObject).booleanValue();
      if (paramString.equalsIgnoreCase("comments"))
      {
        this.features = (bool ? (short)(this.features | 0x20) : (short)(this.features & 0xFFFFFFDF));
      }
      else if (paramString.equalsIgnoreCase("datatype-normalization"))
      {
        setFeature("http://apache.org/xml/features/validation/schema/normalized-value", bool);
        this.features = (bool ? (short)(this.features | 0x2) : (short)(this.features & 0xFFFFFFFD));
        if (bool) {
          this.features = ((short)(this.features | 0x40));
        }
      }
      else if (paramString.equalsIgnoreCase("namespaces"))
      {
        this.features = (bool ? (short)(this.features | 0x1) : (short)(this.features & 0xFFFFFFFE));
      }
      else if (paramString.equalsIgnoreCase("cdata-sections"))
      {
        this.features = (bool ? (short)(this.features | 0x8) : (short)(this.features & 0xFFFFFFF7));
      }
      else if (paramString.equalsIgnoreCase("entities"))
      {
        this.features = (bool ? (short)(this.features | 0x4) : (short)(this.features & 0xFFFFFFFB));
      }
      else if (paramString.equalsIgnoreCase("split-cdata-sections"))
      {
        this.features = (bool ? (short)(this.features | 0x10) : (short)(this.features & 0xFFFFFFEF));
      }
      else if (paramString.equalsIgnoreCase("validate"))
      {
        this.features = (bool ? (short)(this.features | 0x40) : (short)(this.features & 0xFFFFFFBF));
      }
      else if (paramString.equalsIgnoreCase("well-formed"))
      {
        this.features = (bool ? (short)(this.features | 0x100) : (short)(this.features & 0xFFFFFEFF));
      }
      else if (paramString.equalsIgnoreCase("namespace-declarations"))
      {
        this.features = (bool ? (short)(this.features | 0x200) : (short)(this.features & 0xFFFFFDFF));
      }
      else if (paramString.equalsIgnoreCase("infoset"))
      {
        if (bool)
        {
          this.features = ((short)(this.features | 0x321));
          this.features = ((short)(this.features & 0xFFFFFFF1));
          setFeature("http://apache.org/xml/features/validation/schema/normalized-value", false);
        }
      }
      else if ((paramString.equalsIgnoreCase("normalize-characters")) || (paramString.equalsIgnoreCase("canonical-form")) || (paramString.equalsIgnoreCase("validate-if-schema")) || (paramString.equalsIgnoreCase("check-character-normalization")))
      {
        if (bool)
        {
          localObject2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { paramString });
          throw new DOMException((short)9, (String)localObject2);
        }
      }
      else if (paramString.equalsIgnoreCase("element-content-whitespace"))
      {
        if (!bool)
        {
          localObject2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { paramString });
          throw new DOMException((short)9, (String)localObject2);
        }
      }
      else if (paramString.equalsIgnoreCase("http://apache.org/xml/features/validation/schema/augment-psvi"))
      {
        if (!bool)
        {
          localObject2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { paramString });
          throw new DOMException((short)9, (String)localObject2);
        }
      }
      else if (paramString.equalsIgnoreCase("psvi"))
      {
        this.features = (bool ? (short)(this.features | 0x80) : (short)(this.features & 0xFFFFFF7F));
      }
      else
      {
        i = 0;
      }
    }
    if ((i == 0) || (!(paramObject instanceof Boolean)))
    {
      i = 1;
      if (paramString.equalsIgnoreCase("error-handler"))
      {
        if (((paramObject instanceof DOMErrorHandler)) || (paramObject == null))
        {
          this.fErrorHandlerWrapper.setErrorHandler((DOMErrorHandler)paramObject);
          setErrorHandler(this.fErrorHandlerWrapper);
        }
        else
        {
          String str1 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "TYPE_MISMATCH_ERR", new Object[] { paramString });
          throw new DOMException((short)17, str1);
        }
      }
      else
      {
        Object localObject1;
        if (paramString.equalsIgnoreCase("resource-resolver"))
        {
          if (((paramObject instanceof LSResourceResolver)) || (paramObject == null))
          {
            try
            {
              setEntityResolver(new DOMEntityResolverWrapper((LSResourceResolver)paramObject));
            }
            catch (XMLConfigurationException localXMLConfigurationException1) {}
          }
          else
          {
            localObject1 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "TYPE_MISMATCH_ERR", new Object[] { paramString });
            throw new DOMException((short)17, (String)localObject1);
          }
        }
        else if (paramString.equalsIgnoreCase("schema-location"))
        {
          if (((paramObject instanceof String)) || (paramObject == null))
          {
            try
            {
              if (paramObject == null)
              {
                this.fSchemaLocation = null;
                setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", null);
              }
              else
              {
                this.fSchemaLocation = ((String)paramObject);
                localObject1 = new StringTokenizer(this.fSchemaLocation, " \n\t\r");
                if (((StringTokenizer)localObject1).hasMoreTokens())
                {
                  localObject2 = new ArrayList();
                  ((ArrayList)localObject2).add(((StringTokenizer)localObject1).nextToken());
                  while (((StringTokenizer)localObject1).hasMoreTokens()) {
                    ((ArrayList)localObject2).add(((StringTokenizer)localObject1).nextToken());
                  }
                  setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", ((ArrayList)localObject2).toArray(new String[((ArrayList)localObject2).size()]));
                }
                else
                {
                  setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", new String[] { (String)paramObject });
                }
              }
            }
            catch (XMLConfigurationException localXMLConfigurationException2) {}
          }
          else
          {
            String str2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "TYPE_MISMATCH_ERR", new Object[] { paramString });
            throw new DOMException((short)17, str2);
          }
        }
        else if (paramString.equalsIgnoreCase("schema-type"))
        {
          if (((paramObject instanceof String)) || (paramObject == null))
          {
            try
            {
              if (paramObject == null) {
                setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", null);
              } else if (paramObject.equals(Constants.NS_XMLSCHEMA)) {
                setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", Constants.NS_XMLSCHEMA);
              } else if (paramObject.equals(Constants.NS_DTD)) {
                setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", Constants.NS_DTD);
              }
            }
            catch (XMLConfigurationException localXMLConfigurationException3) {}
          }
          else
          {
            String str3 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "TYPE_MISMATCH_ERR", new Object[] { paramString });
            throw new DOMException((short)17, str3);
          }
        }
        else
        {
          String str4;
          if (paramString.equalsIgnoreCase("http://apache.org/xml/properties/internal/entity-resolver"))
          {
            if (((paramObject instanceof XMLEntityResolver)) || (paramObject == null))
            {
              try
              {
                setEntityResolver((XMLEntityResolver)paramObject);
              }
              catch (XMLConfigurationException localXMLConfigurationException4) {}
            }
            else
            {
              str4 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "TYPE_MISMATCH_ERR", new Object[] { paramString });
              throw new DOMException((short)17, str4);
            }
          }
          else if (paramString.equalsIgnoreCase("http://apache.org/xml/properties/internal/symbol-table"))
          {
            if ((paramObject instanceof SymbolTable))
            {
              setProperty("http://apache.org/xml/properties/internal/symbol-table", paramObject);
            }
            else
            {
              str4 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "TYPE_MISMATCH_ERR", new Object[] { paramString });
              throw new DOMException((short)17, str4);
            }
          }
          else if (paramString.equalsIgnoreCase("http://apache.org/xml/properties/internal/grammar-pool"))
          {
            if (((paramObject instanceof XMLGrammarPool)) || (paramObject == null))
            {
              setProperty("http://apache.org/xml/properties/internal/grammar-pool", paramObject);
            }
            else
            {
              str4 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "TYPE_MISMATCH_ERR", new Object[] { paramString });
              throw new DOMException((short)17, str4);
            }
          }
          else
          {
            str4 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_FOUND", new Object[] { paramString });
            throw new DOMException((short)8, str4);
          }
        }
      }
    }
  }
  
  public Object getParameter(String paramString)
    throws DOMException
  {
    if (paramString.equalsIgnoreCase("comments")) {
      return (this.features & 0x20) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("namespaces")) {
      return (this.features & 0x1) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("datatype-normalization")) {
      return (this.features & 0x2) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("cdata-sections")) {
      return (this.features & 0x8) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("entities")) {
      return (this.features & 0x4) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("split-cdata-sections")) {
      return (this.features & 0x10) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("validate")) {
      return (this.features & 0x40) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("well-formed")) {
      return (this.features & 0x100) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("namespace-declarations")) {
      return (this.features & 0x200) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("infoset")) {
      return (this.features & 0x32F) == 801 ? Boolean.TRUE : Boolean.FALSE;
    }
    if ((paramString.equalsIgnoreCase("normalize-characters")) || (paramString.equalsIgnoreCase("canonical-form")) || (paramString.equalsIgnoreCase("validate-if-schema")) || (paramString.equalsIgnoreCase("check-character-normalization"))) {
      return Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("http://apache.org/xml/features/validation/schema/augment-psvi")) {
      return Boolean.TRUE;
    }
    if (paramString.equalsIgnoreCase("psvi")) {
      return (this.features & 0x80) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("element-content-whitespace")) {
      return Boolean.TRUE;
    }
    if (paramString.equalsIgnoreCase("error-handler")) {
      return this.fErrorHandlerWrapper.getErrorHandler();
    }
    if (paramString.equalsIgnoreCase("resource-resolver"))
    {
      localObject = getEntityResolver();
      if ((localObject != null) && ((localObject instanceof DOMEntityResolverWrapper))) {
        return ((DOMEntityResolverWrapper)localObject).getEntityResolver();
      }
      return null;
    }
    if (paramString.equalsIgnoreCase("schema-type")) {
      return getProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage");
    }
    if (paramString.equalsIgnoreCase("schema-location")) {
      return this.fSchemaLocation;
    }
    if (paramString.equalsIgnoreCase("http://apache.org/xml/properties/internal/entity-resolver")) {
      return getEntityResolver();
    }
    if (paramString.equalsIgnoreCase("http://apache.org/xml/properties/internal/symbol-table")) {
      return getProperty("http://apache.org/xml/properties/internal/symbol-table");
    }
    if (paramString.equalsIgnoreCase("http://apache.org/xml/properties/internal/grammar-pool")) {
      return getProperty("http://apache.org/xml/properties/internal/grammar-pool");
    }
    Object localObject = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_FOUND", new Object[] { paramString });
    throw new DOMException((short)8, (String)localObject);
  }
  
  public boolean canSetParameter(String paramString, Object paramObject)
  {
    if (paramObject == null) {
      return true;
    }
    if ((paramObject instanceof Boolean))
    {
      if ((paramString.equalsIgnoreCase("comments")) || (paramString.equalsIgnoreCase("datatype-normalization")) || (paramString.equalsIgnoreCase("cdata-sections")) || (paramString.equalsIgnoreCase("entities")) || (paramString.equalsIgnoreCase("split-cdata-sections")) || (paramString.equalsIgnoreCase("namespaces")) || (paramString.equalsIgnoreCase("validate")) || (paramString.equalsIgnoreCase("well-formed")) || (paramString.equalsIgnoreCase("infoset")) || (paramString.equalsIgnoreCase("namespace-declarations"))) {
        return true;
      }
      if ((paramString.equalsIgnoreCase("normalize-characters")) || (paramString.equalsIgnoreCase("canonical-form")) || (paramString.equalsIgnoreCase("validate-if-schema")) || (paramString.equalsIgnoreCase("check-character-normalization"))) {
        return !paramObject.equals(Boolean.TRUE);
      }
      if ((paramString.equalsIgnoreCase("element-content-whitespace")) || (paramString.equalsIgnoreCase("http://apache.org/xml/features/validation/schema/augment-psvi"))) {
        return paramObject.equals(Boolean.TRUE);
      }
      return false;
    }
    if (paramString.equalsIgnoreCase("error-handler")) {
      return (paramObject instanceof DOMErrorHandler);
    }
    if (paramString.equalsIgnoreCase("resource-resolver")) {
      return (paramObject instanceof LSResourceResolver);
    }
    if (paramString.equalsIgnoreCase("schema-location")) {
      return (paramObject instanceof String);
    }
    if (paramString.equalsIgnoreCase("schema-type")) {
      return ((paramObject instanceof String)) && ((paramObject.equals(Constants.NS_XMLSCHEMA)) || (paramObject.equals(Constants.NS_DTD)));
    }
    if (paramString.equalsIgnoreCase("http://apache.org/xml/properties/internal/entity-resolver")) {
      return (paramObject instanceof XMLEntityResolver);
    }
    if (paramString.equalsIgnoreCase("http://apache.org/xml/properties/internal/symbol-table")) {
      return (paramObject instanceof SymbolTable);
    }
    if (paramString.equalsIgnoreCase("http://apache.org/xml/properties/internal/grammar-pool")) {
      return (paramObject instanceof XMLGrammarPool);
    }
    return false;
  }
  
  public DOMStringList getParameterNames()
  {
    if (this.fRecognizedParameters == null)
    {
      Vector localVector = new Vector();
      localVector.add("comments");
      localVector.add("datatype-normalization");
      localVector.add("cdata-sections");
      localVector.add("entities");
      localVector.add("split-cdata-sections");
      localVector.add("namespaces");
      localVector.add("validate");
      localVector.add("infoset");
      localVector.add("normalize-characters");
      localVector.add("canonical-form");
      localVector.add("validate-if-schema");
      localVector.add("check-character-normalization");
      localVector.add("well-formed");
      localVector.add("namespace-declarations");
      localVector.add("element-content-whitespace");
      localVector.add("error-handler");
      localVector.add("schema-type");
      localVector.add("schema-location");
      localVector.add("resource-resolver");
      localVector.add("http://apache.org/xml/properties/internal/entity-resolver");
      localVector.add("http://apache.org/xml/properties/internal/grammar-pool");
      localVector.add("http://apache.org/xml/properties/internal/symbol-table");
      localVector.add("http://apache.org/xml/features/validation/schema/augment-psvi");
      this.fRecognizedParameters = new DOMStringListImpl(localVector);
    }
    return this.fRecognizedParameters;
  }
  
  protected void reset()
    throws XNIException
  {
    if (this.fValidationManager != null) {
      this.fValidationManager.reset();
    }
    int i = this.fComponents.size();
    for (int j = 0; j < i; j++)
    {
      XMLComponent localXMLComponent = (XMLComponent)this.fComponents.get(j);
      localXMLComponent.reset(this);
    }
  }
  
  protected void checkProperty(String paramString)
    throws XMLConfigurationException
  {
    if (paramString.startsWith("http://xml.org/sax/properties/"))
    {
      int i = paramString.length() - "http://xml.org/sax/properties/".length();
      if ((i == "xml-string".length()) && (paramString.endsWith("xml-string")))
      {
        short s = 1;
        throw new XMLConfigurationException(s, paramString);
      }
    }
    super.checkProperty(paramString);
  }
  
  protected void addComponent(XMLComponent paramXMLComponent)
  {
    if (this.fComponents.contains(paramXMLComponent)) {
      return;
    }
    this.fComponents.add(paramXMLComponent);
    String[] arrayOfString1 = paramXMLComponent.getRecognizedFeatures();
    addRecognizedFeatures(arrayOfString1);
    String[] arrayOfString2 = paramXMLComponent.getRecognizedProperties();
    addRecognizedProperties(arrayOfString2);
  }
  
  protected ValidationManager createValidationManager()
  {
    return new ValidationManager();
  }
  
  protected final void setDTDValidatorFactory(String paramString)
  {
    if ("1.1".equals(paramString))
    {
      if (this.fCurrentDVFactory != this.fXML11DatatypeFactory)
      {
        this.fCurrentDVFactory = this.fXML11DatatypeFactory;
        setProperty("http://apache.org/xml/properties/internal/datatype-validator-factory", this.fCurrentDVFactory);
      }
    }
    else if (this.fCurrentDVFactory != this.fDatatypeValidatorFactory)
    {
      this.fCurrentDVFactory = this.fDatatypeValidatorFactory;
      setProperty("http://apache.org/xml/properties/internal/datatype-validator-factory", this.fCurrentDVFactory);
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.DOMConfigurationImpl
 * JD-Core Version:    0.7.0.1
 */
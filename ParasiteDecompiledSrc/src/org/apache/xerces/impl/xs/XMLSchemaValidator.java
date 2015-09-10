package org.apache.xerces.impl.xs;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;
import org.apache.xerces.impl.RevalidationHandler;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.dv.DatatypeException;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidatedInfo;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.validation.ConfigurableValidationState;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.impl.validation.ValidationState;
import org.apache.xerces.impl.xs.identity.Field;
import org.apache.xerces.impl.xs.identity.FieldActivator;
import org.apache.xerces.impl.xs.identity.IdentityConstraint;
import org.apache.xerces.impl.xs.identity.KeyRef;
import org.apache.xerces.impl.xs.identity.Selector;
import org.apache.xerces.impl.xs.identity.Selector.Matcher;
import org.apache.xerces.impl.xs.identity.UniqueOrKey;
import org.apache.xerces.impl.xs.identity.ValueStore;
import org.apache.xerces.impl.xs.identity.XPathMatcher;
import org.apache.xerces.impl.xs.models.CMBuilder;
import org.apache.xerces.impl.xs.models.CMNodeFactory;
import org.apache.xerces.impl.xs.models.XSCMValidator;
import org.apache.xerces.util.AugmentationsImpl;
import org.apache.xerces.util.IntStack;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLAttributesImpl;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLResourceIdentifierImpl;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDocumentFilter;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xs.ShortList;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;

public class XMLSchemaValidator
  implements XMLComponent, XMLDocumentFilter, FieldActivator, RevalidationHandler
{
  private static final boolean DEBUG = false;
  protected static final String VALIDATION = "http://xml.org/sax/features/validation";
  protected static final String SCHEMA_VALIDATION = "http://apache.org/xml/features/validation/schema";
  protected static final String SCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
  protected static final String DYNAMIC_VALIDATION = "http://apache.org/xml/features/validation/dynamic";
  protected static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
  protected static final String SCHEMA_ELEMENT_DEFAULT = "http://apache.org/xml/features/validation/schema/element-default";
  protected static final String SCHEMA_AUGMENT_PSVI = "http://apache.org/xml/features/validation/schema/augment-psvi";
  protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
  protected static final String STANDARD_URI_CONFORMANT_FEATURE = "http://apache.org/xml/features/standard-uri-conformant";
  protected static final String GENERATE_SYNTHETIC_ANNOTATIONS = "http://apache.org/xml/features/generate-synthetic-annotations";
  protected static final String VALIDATE_ANNOTATIONS = "http://apache.org/xml/features/validate-annotations";
  protected static final String HONOUR_ALL_SCHEMALOCATIONS = "http://apache.org/xml/features/honour-all-schemaLocations";
  protected static final String USE_GRAMMAR_POOL_ONLY = "http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only";
  protected static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
  protected static final String PARSER_SETTINGS = "http://apache.org/xml/features/internal/parser-settings";
  protected static final String IGNORE_XSI_TYPE = "http://apache.org/xml/features/validation/schema/ignore-xsi-type-until-elemdecl";
  protected static final String ID_IDREF_CHECKING = "http://apache.org/xml/features/validation/id-idref-checking";
  protected static final String UNPARSED_ENTITY_CHECKING = "http://apache.org/xml/features/validation/unparsed-entity-checking";
  protected static final String IDENTITY_CONSTRAINT_CHECKING = "http://apache.org/xml/features/validation/identity-constraint-checking";
  public static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
  public static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
  public static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
  public static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
  protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
  protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
  protected static final String SCHEMA_LOCATION = "http://apache.org/xml/properties/schema/external-schemaLocation";
  protected static final String SCHEMA_NONS_LOCATION = "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation";
  protected static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
  protected static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
  protected static final String ROOT_TYPE_DEF = "http://apache.org/xml/properties/validation/schema/root-type-definition";
  private static final String[] RECOGNIZED_FEATURES = { "http://xml.org/sax/features/validation", "http://apache.org/xml/features/validation/schema", "http://apache.org/xml/features/validation/dynamic", "http://apache.org/xml/features/validation/schema-full-checking", "http://apache.org/xml/features/allow-java-encodings", "http://apache.org/xml/features/continue-after-fatal-error", "http://apache.org/xml/features/standard-uri-conformant", "http://apache.org/xml/features/generate-synthetic-annotations", "http://apache.org/xml/features/validate-annotations", "http://apache.org/xml/features/honour-all-schemaLocations", "http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only", "http://apache.org/xml/features/validation/schema/ignore-xsi-type-until-elemdecl", "http://apache.org/xml/features/validation/id-idref-checking", "http://apache.org/xml/features/validation/identity-constraint-checking", "http://apache.org/xml/features/validation/unparsed-entity-checking" };
  private static final Boolean[] FEATURE_DEFAULTS = { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null };
  private static final String[] RECOGNIZED_PROPERTIES = { "http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-resolver", "http://apache.org/xml/properties/internal/validation-manager", "http://apache.org/xml/properties/schema/external-schemaLocation", "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", "http://java.sun.com/xml/jaxp/properties/schemaSource", "http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://apache.org/xml/properties/validation/schema/root-type-definition" };
  private static final Object[] PROPERTY_DEFAULTS = { null, null, null, null, null, null, null, null, null };
  protected static final int ID_CONSTRAINT_NUM = 1;
  static final XSAttributeDecl XSI_TYPE = SchemaGrammar.SG_XSI.getGlobalAttributeDecl(SchemaSymbols.XSI_TYPE);
  static final XSAttributeDecl XSI_NIL = SchemaGrammar.SG_XSI.getGlobalAttributeDecl(SchemaSymbols.XSI_NIL);
  static final XSAttributeDecl XSI_SCHEMALOCATION = SchemaGrammar.SG_XSI.getGlobalAttributeDecl(SchemaSymbols.XSI_SCHEMALOCATION);
  static final XSAttributeDecl XSI_NONAMESPACESCHEMALOCATION = SchemaGrammar.SG_XSI.getGlobalAttributeDecl(SchemaSymbols.XSI_NONAMESPACESCHEMALOCATION);
  protected ElementPSVImpl fCurrentPSVI = new ElementPSVImpl();
  protected final AugmentationsImpl fAugmentations = new AugmentationsImpl();
  protected XMLString fDefaultValue;
  protected boolean fDynamicValidation = false;
  protected boolean fSchemaDynamicValidation = false;
  protected boolean fDoValidation = false;
  protected boolean fFullChecking = false;
  protected boolean fNormalizeData = true;
  protected boolean fSchemaElementDefault = true;
  protected boolean fAugPSVI = true;
  protected boolean fIdConstraint = false;
  protected boolean fUseGrammarPoolOnly = false;
  private String fSchemaType = null;
  protected boolean fEntityRef = false;
  protected boolean fInCDATA = false;
  protected SymbolTable fSymbolTable;
  private XMLLocator fLocator;
  protected final XSIErrorReporter fXSIErrorReporter = new XSIErrorReporter();
  protected XMLEntityResolver fEntityResolver;
  protected ValidationManager fValidationManager = null;
  protected ConfigurableValidationState fValidationState = new ConfigurableValidationState();
  protected XMLGrammarPool fGrammarPool;
  protected String fExternalSchemas = null;
  protected String fExternalNoNamespaceSchema = null;
  protected Object fJaxpSchemaSource = null;
  protected final XSDDescription fXSDDescription = new XSDDescription();
  protected final Hashtable fLocationPairs = new Hashtable();
  protected XMLDocumentHandler fDocumentHandler;
  protected XMLDocumentSource fDocumentSource;
  static final int INITIAL_STACK_SIZE = 8;
  static final int INC_STACK_SIZE = 8;
  private static final boolean DEBUG_NORMALIZATION = false;
  private final XMLString fEmptyXMLStr = new XMLString(null, 0, -1);
  private static final int BUFFER_SIZE = 20;
  private final XMLString fNormalizedStr = new XMLString();
  private boolean fFirstChunk = true;
  private boolean fTrailing = false;
  private short fWhiteSpace = -1;
  private boolean fUnionType = false;
  private final XSGrammarBucket fGrammarBucket = new XSGrammarBucket();
  private final SubstitutionGroupHandler fSubGroupHandler = new SubstitutionGroupHandler(this.fGrammarBucket);
  private final XSSimpleType fQNameDV = (XSSimpleType)SchemaGrammar.SG_SchemaNS.getGlobalTypeDecl("QName");
  private final CMNodeFactory nodeFactory = new CMNodeFactory();
  private final CMBuilder fCMBuilder = new CMBuilder(this.nodeFactory);
  private final XMLSchemaLoader fSchemaLoader = new XMLSchemaLoader(this.fXSIErrorReporter.fErrorReporter, this.fGrammarBucket, this.fSubGroupHandler, this.fCMBuilder);
  private String fValidationRoot;
  private int fSkipValidationDepth;
  private int fNFullValidationDepth;
  private int fNNoneValidationDepth;
  private int fElementDepth;
  private boolean fSubElement;
  private boolean[] fSubElementStack = new boolean[8];
  private XSElementDecl fCurrentElemDecl;
  private XSElementDecl[] fElemDeclStack = new XSElementDecl[8];
  private boolean fNil;
  private boolean[] fNilStack = new boolean[8];
  private XSNotationDecl fNotation;
  private XSNotationDecl[] fNotationStack = new XSNotationDecl[8];
  private XSTypeDefinition fCurrentType;
  private XSTypeDefinition[] fTypeStack = new XSTypeDefinition[8];
  private XSCMValidator fCurrentCM;
  private XSCMValidator[] fCMStack = new XSCMValidator[8];
  private int[] fCurrCMState;
  private int[][] fCMStateStack = new int[8][];
  private boolean fStrictAssess = true;
  private boolean[] fStrictAssessStack = new boolean[8];
  private final StringBuffer fBuffer = new StringBuffer();
  private boolean fAppendBuffer = true;
  private boolean fSawText = false;
  private boolean[] fSawTextStack = new boolean[8];
  private boolean fSawCharacters = false;
  private boolean[] fStringContent = new boolean[8];
  private final org.apache.xerces.xni.QName fTempQName = new org.apache.xerces.xni.QName();
  private javax.xml.namespace.QName fRootTypeQName = null;
  private int fIgnoreXSITypeDepth;
  private boolean fIDCChecking;
  private ValidatedInfo fValidatedInfo = new ValidatedInfo();
  private ValidationState fState4XsiType = new ValidationState();
  private ValidationState fState4ApplyDefault = new ValidationState();
  protected XPathMatcherStack fMatcherStack = new XPathMatcherStack();
  protected ValueStoreCache fValueStoreCache = new ValueStoreCache();
  
  public String[] getRecognizedFeatures()
  {
    return (String[])RECOGNIZED_FEATURES.clone();
  }
  
  public void setFeature(String paramString, boolean paramBoolean)
    throws XMLConfigurationException
  {}
  
  public String[] getRecognizedProperties()
  {
    return (String[])RECOGNIZED_PROPERTIES.clone();
  }
  
  public void setProperty(String paramString, Object paramObject)
    throws XMLConfigurationException
  {
    if (paramString.equals("http://apache.org/xml/properties/validation/schema/root-type-definition")) {
      this.fRootTypeQName = ((javax.xml.namespace.QName)paramObject);
    }
  }
  
  public Boolean getFeatureDefault(String paramString)
  {
    for (int i = 0; i < RECOGNIZED_FEATURES.length; i++) {
      if (RECOGNIZED_FEATURES[i].equals(paramString)) {
        return FEATURE_DEFAULTS[i];
      }
    }
    return null;
  }
  
  public Object getPropertyDefault(String paramString)
  {
    for (int i = 0; i < RECOGNIZED_PROPERTIES.length; i++) {
      if (RECOGNIZED_PROPERTIES[i].equals(paramString)) {
        return PROPERTY_DEFAULTS[i];
      }
    }
    return null;
  }
  
  public void setDocumentHandler(XMLDocumentHandler paramXMLDocumentHandler)
  {
    this.fDocumentHandler = paramXMLDocumentHandler;
  }
  
  public XMLDocumentHandler getDocumentHandler()
  {
    return this.fDocumentHandler;
  }
  
  public void setDocumentSource(XMLDocumentSource paramXMLDocumentSource)
  {
    this.fDocumentSource = paramXMLDocumentSource;
  }
  
  public XMLDocumentSource getDocumentSource()
  {
    return this.fDocumentSource;
  }
  
  public void startDocument(XMLLocator paramXMLLocator, String paramString, NamespaceContext paramNamespaceContext, Augmentations paramAugmentations)
    throws XNIException
  {
    this.fValidationState.setNamespaceSupport(paramNamespaceContext);
    this.fState4XsiType.setNamespaceSupport(paramNamespaceContext);
    this.fState4ApplyDefault.setNamespaceSupport(paramNamespaceContext);
    this.fLocator = paramXMLLocator;
    handleStartDocument(paramXMLLocator, paramString);
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.startDocument(paramXMLLocator, paramString, paramNamespaceContext, paramAugmentations);
    }
  }
  
  public void xmlDecl(String paramString1, String paramString2, String paramString3, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.xmlDecl(paramString1, paramString2, paramString3, paramAugmentations);
    }
  }
  
  public void doctypeDecl(String paramString1, String paramString2, String paramString3, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.doctypeDecl(paramString1, paramString2, paramString3, paramAugmentations);
    }
  }
  
  public void startElement(org.apache.xerces.xni.QName paramQName, XMLAttributes paramXMLAttributes, Augmentations paramAugmentations)
    throws XNIException
  {
    Augmentations localAugmentations = handleStartElement(paramQName, paramXMLAttributes, paramAugmentations);
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.startElement(paramQName, paramXMLAttributes, localAugmentations);
    }
  }
  
  public void emptyElement(org.apache.xerces.xni.QName paramQName, XMLAttributes paramXMLAttributes, Augmentations paramAugmentations)
    throws XNIException
  {
    Augmentations localAugmentations = handleStartElement(paramQName, paramXMLAttributes, paramAugmentations);
    this.fDefaultValue = null;
    if (this.fElementDepth != -2) {
      localAugmentations = handleEndElement(paramQName, localAugmentations);
    }
    if (this.fDocumentHandler != null) {
      if ((!this.fSchemaElementDefault) || (this.fDefaultValue == null))
      {
        this.fDocumentHandler.emptyElement(paramQName, paramXMLAttributes, localAugmentations);
      }
      else
      {
        this.fDocumentHandler.startElement(paramQName, paramXMLAttributes, localAugmentations);
        this.fDocumentHandler.characters(this.fDefaultValue, null);
        this.fDocumentHandler.endElement(paramQName, localAugmentations);
      }
    }
  }
  
  public void characters(XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {
    paramXMLString = handleCharacters(paramXMLString);
    if (this.fDocumentHandler != null) {
      if ((this.fNormalizeData) && (this.fUnionType))
      {
        if (paramAugmentations != null) {
          this.fDocumentHandler.characters(this.fEmptyXMLStr, paramAugmentations);
        }
      }
      else {
        this.fDocumentHandler.characters(paramXMLString, paramAugmentations);
      }
    }
  }
  
  public void ignorableWhitespace(XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {
    handleIgnorableWhitespace(paramXMLString);
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.ignorableWhitespace(paramXMLString, paramAugmentations);
    }
  }
  
  public void endElement(org.apache.xerces.xni.QName paramQName, Augmentations paramAugmentations)
    throws XNIException
  {
    this.fDefaultValue = null;
    Augmentations localAugmentations = handleEndElement(paramQName, paramAugmentations);
    if (this.fDocumentHandler != null) {
      if ((!this.fSchemaElementDefault) || (this.fDefaultValue == null))
      {
        this.fDocumentHandler.endElement(paramQName, localAugmentations);
      }
      else
      {
        this.fDocumentHandler.characters(this.fDefaultValue, null);
        this.fDocumentHandler.endElement(paramQName, localAugmentations);
      }
    }
  }
  
  public void startCDATA(Augmentations paramAugmentations)
    throws XNIException
  {
    this.fInCDATA = true;
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.startCDATA(paramAugmentations);
    }
  }
  
  public void endCDATA(Augmentations paramAugmentations)
    throws XNIException
  {
    this.fInCDATA = false;
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.endCDATA(paramAugmentations);
    }
  }
  
  public void endDocument(Augmentations paramAugmentations)
    throws XNIException
  {
    handleEndDocument();
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.endDocument(paramAugmentations);
    }
    this.fLocator = null;
  }
  
  public boolean characterData(String paramString, Augmentations paramAugmentations)
  {
    this.fSawText = ((this.fSawText) || (paramString.length() > 0));
    if ((this.fNormalizeData) && (this.fWhiteSpace != -1) && (this.fWhiteSpace != 0))
    {
      normalizeWhitespace(paramString, this.fWhiteSpace == 2);
      this.fBuffer.append(this.fNormalizedStr.ch, this.fNormalizedStr.offset, this.fNormalizedStr.length);
    }
    else if (this.fAppendBuffer)
    {
      this.fBuffer.append(paramString);
    }
    boolean bool = true;
    if ((this.fCurrentType != null) && (this.fCurrentType.getTypeCategory() == 15))
    {
      XSComplexTypeDecl localXSComplexTypeDecl = (XSComplexTypeDecl)this.fCurrentType;
      if (localXSComplexTypeDecl.fContentType == 2) {
        for (int i = 0; i < paramString.length(); i++) {
          if (!XMLChar.isSpace(paramString.charAt(i)))
          {
            bool = false;
            this.fSawCharacters = true;
            break;
          }
        }
      }
    }
    return bool;
  }
  
  public void elementDefault(String paramString) {}
  
  public void startGeneralEntity(String paramString1, XMLResourceIdentifier paramXMLResourceIdentifier, String paramString2, Augmentations paramAugmentations)
    throws XNIException
  {
    this.fEntityRef = true;
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.startGeneralEntity(paramString1, paramXMLResourceIdentifier, paramString2, paramAugmentations);
    }
  }
  
  public void textDecl(String paramString1, String paramString2, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.textDecl(paramString1, paramString2, paramAugmentations);
    }
  }
  
  public void comment(XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.comment(paramXMLString, paramAugmentations);
    }
  }
  
  public void processingInstruction(String paramString, XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.processingInstruction(paramString, paramXMLString, paramAugmentations);
    }
  }
  
  public void endGeneralEntity(String paramString, Augmentations paramAugmentations)
    throws XNIException
  {
    this.fEntityRef = false;
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.endGeneralEntity(paramString, paramAugmentations);
    }
  }
  
  public XMLSchemaValidator()
  {
    this.fState4XsiType.setExtraChecking(false);
    this.fState4ApplyDefault.setFacetChecking(false);
  }
  
  public void reset(XMLComponentManager paramXMLComponentManager)
    throws XMLConfigurationException
  {
    this.fIdConstraint = false;
    this.fLocationPairs.clear();
    this.fValidationState.resetIDTables();
    this.nodeFactory.reset(paramXMLComponentManager);
    this.fSchemaLoader.reset(paramXMLComponentManager);
    this.fCurrentElemDecl = null;
    this.fCurrentCM = null;
    this.fCurrCMState = null;
    this.fSkipValidationDepth = -1;
    this.fNFullValidationDepth = -1;
    this.fNNoneValidationDepth = -1;
    this.fElementDepth = -1;
    this.fSubElement = false;
    this.fSchemaDynamicValidation = false;
    this.fEntityRef = false;
    this.fInCDATA = false;
    this.fMatcherStack.clear();
    this.fXSIErrorReporter.reset((XMLErrorReporter)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter"));
    boolean bool1;
    try
    {
      bool1 = paramXMLComponentManager.getFeature("http://apache.org/xml/features/internal/parser-settings");
    }
    catch (XMLConfigurationException localXMLConfigurationException1)
    {
      bool1 = true;
    }
    if (!bool1)
    {
      this.fValidationManager.addValidationState(this.fValidationState);
      XMLSchemaLoader.processExternalHints(this.fExternalSchemas, this.fExternalNoNamespaceSchema, this.fLocationPairs, this.fXSIErrorReporter.fErrorReporter);
      return;
    }
    SymbolTable localSymbolTable = (SymbolTable)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
    if (localSymbolTable != this.fSymbolTable) {
      this.fSymbolTable = localSymbolTable;
    }
    try
    {
      this.fDynamicValidation = paramXMLComponentManager.getFeature("http://apache.org/xml/features/validation/dynamic");
    }
    catch (XMLConfigurationException localXMLConfigurationException2)
    {
      this.fDynamicValidation = false;
    }
    if (this.fDynamicValidation) {
      this.fDoValidation = true;
    } else {
      try
      {
        this.fDoValidation = paramXMLComponentManager.getFeature("http://xml.org/sax/features/validation");
      }
      catch (XMLConfigurationException localXMLConfigurationException3)
      {
        this.fDoValidation = false;
      }
    }
    if (this.fDoValidation) {
      try
      {
        this.fDoValidation = paramXMLComponentManager.getFeature("http://apache.org/xml/features/validation/schema");
      }
      catch (XMLConfigurationException localXMLConfigurationException4) {}
    }
    try
    {
      this.fFullChecking = paramXMLComponentManager.getFeature("http://apache.org/xml/features/validation/schema-full-checking");
    }
    catch (XMLConfigurationException localXMLConfigurationException5)
    {
      this.fFullChecking = false;
    }
    try
    {
      this.fNormalizeData = paramXMLComponentManager.getFeature("http://apache.org/xml/features/validation/schema/normalized-value");
    }
    catch (XMLConfigurationException localXMLConfigurationException6)
    {
      this.fNormalizeData = false;
    }
    try
    {
      this.fSchemaElementDefault = paramXMLComponentManager.getFeature("http://apache.org/xml/features/validation/schema/element-default");
    }
    catch (XMLConfigurationException localXMLConfigurationException7)
    {
      this.fSchemaElementDefault = false;
    }
    try
    {
      this.fAugPSVI = paramXMLComponentManager.getFeature("http://apache.org/xml/features/validation/schema/augment-psvi");
    }
    catch (XMLConfigurationException localXMLConfigurationException8)
    {
      this.fAugPSVI = true;
    }
    try
    {
      this.fSchemaType = ((String)paramXMLComponentManager.getProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage"));
    }
    catch (XMLConfigurationException localXMLConfigurationException9)
    {
      this.fSchemaType = null;
    }
    try
    {
      this.fUseGrammarPoolOnly = paramXMLComponentManager.getFeature("http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only");
    }
    catch (XMLConfigurationException localXMLConfigurationException10)
    {
      this.fUseGrammarPoolOnly = false;
    }
    this.fEntityResolver = ((XMLEntityResolver)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/entity-manager"));
    this.fValidationManager = ((ValidationManager)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/validation-manager"));
    this.fValidationManager.addValidationState(this.fValidationState);
    this.fValidationState.setSymbolTable(this.fSymbolTable);
    try
    {
      this.fRootTypeQName = ((javax.xml.namespace.QName)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/validation/schema/root-type-definition"));
    }
    catch (XMLConfigurationException localXMLConfigurationException11)
    {
      this.fRootTypeQName = null;
    }
    boolean bool2;
    try
    {
      bool2 = paramXMLComponentManager.getFeature("http://apache.org/xml/features/validation/schema/ignore-xsi-type-until-elemdecl");
    }
    catch (XMLConfigurationException localXMLConfigurationException12)
    {
      bool2 = false;
    }
    this.fIgnoreXSITypeDepth = (bool2 ? 0 : -1);
    try
    {
      this.fIDCChecking = paramXMLComponentManager.getFeature("http://apache.org/xml/features/validation/identity-constraint-checking");
    }
    catch (XMLConfigurationException localXMLConfigurationException13)
    {
      this.fIDCChecking = true;
    }
    try
    {
      this.fValidationState.setIdIdrefChecking(paramXMLComponentManager.getFeature("http://apache.org/xml/features/validation/id-idref-checking"));
    }
    catch (XMLConfigurationException localXMLConfigurationException14)
    {
      this.fValidationState.setIdIdrefChecking(true);
    }
    try
    {
      this.fValidationState.setUnparsedEntityChecking(paramXMLComponentManager.getFeature("http://apache.org/xml/features/validation/unparsed-entity-checking"));
    }
    catch (XMLConfigurationException localXMLConfigurationException15)
    {
      this.fValidationState.setUnparsedEntityChecking(true);
    }
    try
    {
      this.fExternalSchemas = ((String)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/schema/external-schemaLocation"));
      this.fExternalNoNamespaceSchema = ((String)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation"));
    }
    catch (XMLConfigurationException localXMLConfigurationException16)
    {
      this.fExternalSchemas = null;
      this.fExternalNoNamespaceSchema = null;
    }
    XMLSchemaLoader.processExternalHints(this.fExternalSchemas, this.fExternalNoNamespaceSchema, this.fLocationPairs, this.fXSIErrorReporter.fErrorReporter);
    try
    {
      this.fJaxpSchemaSource = paramXMLComponentManager.getProperty("http://java.sun.com/xml/jaxp/properties/schemaSource");
    }
    catch (XMLConfigurationException localXMLConfigurationException17)
    {
      this.fJaxpSchemaSource = null;
    }
    try
    {
      this.fGrammarPool = ((XMLGrammarPool)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/grammar-pool"));
    }
    catch (XMLConfigurationException localXMLConfigurationException18)
    {
      this.fGrammarPool = null;
    }
    this.fState4XsiType.setSymbolTable(localSymbolTable);
    this.fState4ApplyDefault.setSymbolTable(localSymbolTable);
  }
  
  public void startValueScopeFor(IdentityConstraint paramIdentityConstraint, int paramInt)
  {
    ValueStoreBase localValueStoreBase = this.fValueStoreCache.getValueStoreFor(paramIdentityConstraint, paramInt);
    localValueStoreBase.startValueScope();
  }
  
  public XPathMatcher activateField(Field paramField, int paramInt)
  {
    ValueStoreBase localValueStoreBase = this.fValueStoreCache.getValueStoreFor(paramField.getIdentityConstraint(), paramInt);
    XPathMatcher localXPathMatcher = paramField.createMatcher(localValueStoreBase);
    this.fMatcherStack.addMatcher(localXPathMatcher);
    localXPathMatcher.startDocumentFragment();
    return localXPathMatcher;
  }
  
  public void endValueScopeFor(IdentityConstraint paramIdentityConstraint, int paramInt)
  {
    ValueStoreBase localValueStoreBase = this.fValueStoreCache.getValueStoreFor(paramIdentityConstraint, paramInt);
    localValueStoreBase.endValueScope();
  }
  
  private void activateSelectorFor(IdentityConstraint paramIdentityConstraint)
  {
    Selector localSelector = paramIdentityConstraint.getSelector();
    XMLSchemaValidator localXMLSchemaValidator = this;
    if (localSelector == null) {
      return;
    }
    XPathMatcher localXPathMatcher = localSelector.createMatcher(localXMLSchemaValidator, this.fElementDepth);
    this.fMatcherStack.addMatcher(localXPathMatcher);
    localXPathMatcher.startDocumentFragment();
  }
  
  void ensureStackCapacity()
  {
    if (this.fElementDepth == this.fElemDeclStack.length)
    {
      int i = this.fElementDepth + 8;
      boolean[] arrayOfBoolean = new boolean[i];
      System.arraycopy(this.fSubElementStack, 0, arrayOfBoolean, 0, this.fElementDepth);
      this.fSubElementStack = arrayOfBoolean;
      XSElementDecl[] arrayOfXSElementDecl = new XSElementDecl[i];
      System.arraycopy(this.fElemDeclStack, 0, arrayOfXSElementDecl, 0, this.fElementDepth);
      this.fElemDeclStack = arrayOfXSElementDecl;
      arrayOfBoolean = new boolean[i];
      System.arraycopy(this.fNilStack, 0, arrayOfBoolean, 0, this.fElementDepth);
      this.fNilStack = arrayOfBoolean;
      XSNotationDecl[] arrayOfXSNotationDecl = new XSNotationDecl[i];
      System.arraycopy(this.fNotationStack, 0, arrayOfXSNotationDecl, 0, this.fElementDepth);
      this.fNotationStack = arrayOfXSNotationDecl;
      XSTypeDefinition[] arrayOfXSTypeDefinition = new XSTypeDefinition[i];
      System.arraycopy(this.fTypeStack, 0, arrayOfXSTypeDefinition, 0, this.fElementDepth);
      this.fTypeStack = arrayOfXSTypeDefinition;
      XSCMValidator[] arrayOfXSCMValidator = new XSCMValidator[i];
      System.arraycopy(this.fCMStack, 0, arrayOfXSCMValidator, 0, this.fElementDepth);
      this.fCMStack = arrayOfXSCMValidator;
      arrayOfBoolean = new boolean[i];
      System.arraycopy(this.fSawTextStack, 0, arrayOfBoolean, 0, this.fElementDepth);
      this.fSawTextStack = arrayOfBoolean;
      arrayOfBoolean = new boolean[i];
      System.arraycopy(this.fStringContent, 0, arrayOfBoolean, 0, this.fElementDepth);
      this.fStringContent = arrayOfBoolean;
      arrayOfBoolean = new boolean[i];
      System.arraycopy(this.fStrictAssessStack, 0, arrayOfBoolean, 0, this.fElementDepth);
      this.fStrictAssessStack = arrayOfBoolean;
      int[][] arrayOfInt = new int[i][];
      System.arraycopy(this.fCMStateStack, 0, arrayOfInt, 0, this.fElementDepth);
      this.fCMStateStack = arrayOfInt;
    }
  }
  
  void handleStartDocument(XMLLocator paramXMLLocator, String paramString)
  {
    if (this.fIDCChecking) {
      this.fValueStoreCache.startDocument();
    }
    if (this.fAugPSVI)
    {
      this.fCurrentPSVI.fGrammars = null;
      this.fCurrentPSVI.fSchemaInformation = null;
    }
  }
  
  void handleEndDocument()
  {
    if (this.fIDCChecking) {
      this.fValueStoreCache.endDocument();
    }
  }
  
  XMLString handleCharacters(XMLString paramXMLString)
  {
    if (this.fSkipValidationDepth >= 0) {
      return paramXMLString;
    }
    this.fSawText = ((this.fSawText) || (paramXMLString.length > 0));
    if ((this.fNormalizeData) && (this.fWhiteSpace != -1) && (this.fWhiteSpace != 0))
    {
      normalizeWhitespace(paramXMLString, this.fWhiteSpace == 2);
      paramXMLString = this.fNormalizedStr;
    }
    if (this.fAppendBuffer) {
      this.fBuffer.append(paramXMLString.ch, paramXMLString.offset, paramXMLString.length);
    }
    if ((this.fCurrentType != null) && (this.fCurrentType.getTypeCategory() == 15))
    {
      XSComplexTypeDecl localXSComplexTypeDecl = (XSComplexTypeDecl)this.fCurrentType;
      if (localXSComplexTypeDecl.fContentType == 2) {
        for (int i = paramXMLString.offset; i < paramXMLString.offset + paramXMLString.length; i++) {
          if (!XMLChar.isSpace(paramXMLString.ch[i]))
          {
            this.fSawCharacters = true;
            break;
          }
        }
      }
    }
    return paramXMLString;
  }
  
  private void normalizeWhitespace(XMLString paramXMLString, boolean paramBoolean)
  {
    boolean bool1 = paramBoolean;
    int i = 0;
    int j = 0;
    boolean bool2 = false;
    int m = paramXMLString.offset + paramXMLString.length;
    if ((this.fNormalizedStr.ch == null) || (this.fNormalizedStr.ch.length < paramXMLString.length + 1)) {
      this.fNormalizedStr.ch = new char[paramXMLString.length + 1];
    }
    this.fNormalizedStr.offset = 1;
    this.fNormalizedStr.length = 1;
    for (int n = paramXMLString.offset; n < m; n++)
    {
      int k = paramXMLString.ch[n];
      if (XMLChar.isSpace(k))
      {
        if (!bool1)
        {
          this.fNormalizedStr.ch[(this.fNormalizedStr.length++)] = ' ';
          bool1 = paramBoolean;
        }
        if (i == 0) {
          j = 1;
        }
      }
      else
      {
        this.fNormalizedStr.ch[(this.fNormalizedStr.length++)] = k;
        bool1 = false;
        i = 1;
      }
    }
    if (bool1) {
      if (this.fNormalizedStr.length > 1)
      {
        this.fNormalizedStr.length -= 1;
        bool2 = true;
      }
      else if ((j != 0) && (!this.fFirstChunk))
      {
        bool2 = true;
      }
    }
    if ((this.fNormalizedStr.length > 1) && (!this.fFirstChunk) && (this.fWhiteSpace == 2)) {
      if (this.fTrailing)
      {
        this.fNormalizedStr.offset = 0;
        this.fNormalizedStr.ch[0] = ' ';
      }
      else if (j != 0)
      {
        this.fNormalizedStr.offset = 0;
        this.fNormalizedStr.ch[0] = ' ';
      }
    }
    this.fNormalizedStr.length -= this.fNormalizedStr.offset;
    this.fTrailing = bool2;
    if ((bool2) || (i != 0)) {
      this.fFirstChunk = false;
    }
  }
  
  private void normalizeWhitespace(String paramString, boolean paramBoolean)
  {
    boolean bool = paramBoolean;
    int j = paramString.length();
    if ((this.fNormalizedStr.ch == null) || (this.fNormalizedStr.ch.length < j)) {
      this.fNormalizedStr.ch = new char[j];
    }
    this.fNormalizedStr.offset = 0;
    this.fNormalizedStr.length = 0;
    for (int k = 0; k < j; k++)
    {
      int i = paramString.charAt(k);
      if (XMLChar.isSpace(i))
      {
        if (!bool)
        {
          this.fNormalizedStr.ch[(this.fNormalizedStr.length++)] = ' ';
          bool = paramBoolean;
        }
      }
      else
      {
        this.fNormalizedStr.ch[(this.fNormalizedStr.length++)] = i;
        bool = false;
      }
    }
    if ((bool) && (this.fNormalizedStr.length != 0)) {
      this.fNormalizedStr.length -= 1;
    }
  }
  
  void handleIgnorableWhitespace(XMLString paramXMLString)
  {
    if (this.fSkipValidationDepth >= 0) {}
  }
  
  Augmentations handleStartElement(org.apache.xerces.xni.QName paramQName, XMLAttributes paramXMLAttributes, Augmentations paramAugmentations)
  {
    if ((this.fElementDepth == -1) && (this.fValidationManager.isGrammarFound()) && (this.fSchemaType == null)) {
      this.fSchemaDynamicValidation = true;
    }
    String str1 = paramXMLAttributes.getValue(SchemaSymbols.URI_XSI, SchemaSymbols.XSI_SCHEMALOCATION);
    String str2 = paramXMLAttributes.getValue(SchemaSymbols.URI_XSI, SchemaSymbols.XSI_NONAMESPACESCHEMALOCATION);
    storeLocations(str1, str2);
    if (this.fSkipValidationDepth >= 0)
    {
      this.fElementDepth += 1;
      if (this.fAugPSVI) {
        paramAugmentations = getEmptyAugs(paramAugmentations);
      }
      return paramAugmentations;
    }
    SchemaGrammar localSchemaGrammar = findSchemaGrammar((short)5, paramQName.uri, null, paramQName, paramXMLAttributes);
    Object localObject1 = null;
    if (this.fCurrentCM != null)
    {
      localObject1 = this.fCurrentCM.oneTransition(paramQName, this.fCurrCMState, this.fSubGroupHandler);
      if (this.fCurrCMState[0] == -1)
      {
        localObject2 = (XSComplexTypeDecl)this.fCurrentType;
        if ((((XSComplexTypeDecl)localObject2).fParticle != null) && ((localObject3 = this.fCurrentCM.whatCanGoHere(this.fCurrCMState)).size() > 0))
        {
          localObject4 = expectedStr((Vector)localObject3);
          reportSchemaError("cvc-complex-type.2.4.a", new Object[] { paramQName.rawname, localObject4 });
        }
        else
        {
          reportSchemaError("cvc-complex-type.2.4.d", new Object[] { paramQName.rawname });
        }
      }
    }
    if (this.fElementDepth != -1)
    {
      ensureStackCapacity();
      this.fSubElementStack[this.fElementDepth] = true;
      this.fSubElement = false;
      this.fElemDeclStack[this.fElementDepth] = this.fCurrentElemDecl;
      this.fNilStack[this.fElementDepth] = this.fNil;
      this.fNotationStack[this.fElementDepth] = this.fNotation;
      this.fTypeStack[this.fElementDepth] = this.fCurrentType;
      this.fStrictAssessStack[this.fElementDepth] = this.fStrictAssess;
      this.fCMStack[this.fElementDepth] = this.fCurrentCM;
      this.fCMStateStack[this.fElementDepth] = this.fCurrCMState;
      this.fSawTextStack[this.fElementDepth] = this.fSawText;
      this.fStringContent[this.fElementDepth] = this.fSawCharacters;
    }
    this.fElementDepth += 1;
    this.fCurrentElemDecl = null;
    Object localObject2 = null;
    this.fCurrentType = null;
    this.fStrictAssess = true;
    this.fNil = false;
    this.fNotation = null;
    this.fBuffer.setLength(0);
    this.fSawText = false;
    this.fSawCharacters = false;
    if (localObject1 != null) {
      if ((localObject1 instanceof XSElementDecl)) {
        this.fCurrentElemDecl = ((XSElementDecl)localObject1);
      } else {
        localObject2 = (XSWildcardDecl)localObject1;
      }
    }
    if ((localObject2 != null) && (((XSWildcardDecl)localObject2).fProcessContents == 2))
    {
      this.fSkipValidationDepth = this.fElementDepth;
      if (this.fAugPSVI) {
        paramAugmentations = getEmptyAugs(paramAugmentations);
      }
      return paramAugmentations;
    }
    if ((this.fElementDepth == 0) && (this.fRootTypeQName != null))
    {
      localObject3 = this.fRootTypeQName.getNamespaceURI();
      if ((localObject3 != null) && (((String)localObject3).equals(""))) {
        localObject3 = null;
      }
      if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(localObject3))
      {
        this.fCurrentType = SchemaGrammar.SG_SchemaNS.getGlobalTypeDecl(this.fRootTypeQName.getLocalPart());
      }
      else
      {
        localObject4 = findSchemaGrammar((short)5, (String)localObject3, null, null, null);
        if (localObject4 != null) {
          this.fCurrentType = ((SchemaGrammar)localObject4).getGlobalTypeDecl(this.fRootTypeQName.getLocalPart());
        }
      }
      if (this.fCurrentType == null)
      {
        localObject4 = this.fRootTypeQName.getPrefix() + ":" + this.fRootTypeQName.getLocalPart();
        reportSchemaError("cvc-type.1", new Object[] { localObject4 });
      }
    }
    if (this.fCurrentType == null)
    {
      if ((this.fCurrentElemDecl == null) && (localSchemaGrammar != null)) {
        this.fCurrentElemDecl = localSchemaGrammar.getGlobalElementDecl(paramQName.localpart);
      }
      if (this.fCurrentElemDecl != null) {
        this.fCurrentType = this.fCurrentElemDecl.fType;
      }
    }
    if ((this.fElementDepth == this.fIgnoreXSITypeDepth) && (this.fCurrentElemDecl == null)) {
      this.fIgnoreXSITypeDepth += 1;
    }
    Object localObject3 = null;
    if (this.fElementDepth >= this.fIgnoreXSITypeDepth) {
      localObject3 = paramXMLAttributes.getValue(SchemaSymbols.URI_XSI, SchemaSymbols.XSI_TYPE);
    }
    if ((this.fCurrentType == null) && (localObject3 == null))
    {
      if (this.fElementDepth == 0)
      {
        if ((this.fDynamicValidation) || (this.fSchemaDynamicValidation))
        {
          if (this.fDocumentSource != null)
          {
            this.fDocumentSource.setDocumentHandler(this.fDocumentHandler);
            if (this.fDocumentHandler != null) {
              this.fDocumentHandler.setDocumentSource(this.fDocumentSource);
            }
            this.fElementDepth = -2;
            return paramAugmentations;
          }
          this.fSkipValidationDepth = this.fElementDepth;
          if (this.fAugPSVI) {
            paramAugmentations = getEmptyAugs(paramAugmentations);
          }
          return paramAugmentations;
        }
        this.fXSIErrorReporter.fErrorReporter.reportError("http://www.w3.org/TR/xml-schema-1", "cvc-elt.1", new Object[] { paramQName.rawname }, (short)1);
      }
      else if ((localObject2 != null) && (((XSWildcardDecl)localObject2).fProcessContents == 1))
      {
        reportSchemaError("cvc-complex-type.2.4.c", new Object[] { paramQName.rawname });
      }
      this.fCurrentType = SchemaGrammar.fAnyType;
      this.fStrictAssess = false;
      this.fNFullValidationDepth = this.fElementDepth;
      this.fAppendBuffer = false;
      this.fXSIErrorReporter.pushContext();
    }
    else
    {
      this.fXSIErrorReporter.pushContext();
      if (localObject3 != null)
      {
        localObject4 = this.fCurrentType;
        this.fCurrentType = getAndCheckXsiType(paramQName, (String)localObject3, paramXMLAttributes);
        if (this.fCurrentType == null) {
          if (localObject4 == null) {
            this.fCurrentType = SchemaGrammar.fAnyType;
          } else {
            this.fCurrentType = ((XSTypeDefinition)localObject4);
          }
        }
      }
      this.fNNoneValidationDepth = this.fElementDepth;
      if ((this.fCurrentElemDecl != null) && (this.fCurrentElemDecl.getConstraintType() == 2))
      {
        this.fAppendBuffer = true;
      }
      else if (this.fCurrentType.getTypeCategory() == 16)
      {
        this.fAppendBuffer = true;
      }
      else
      {
        localObject4 = (XSComplexTypeDecl)this.fCurrentType;
        this.fAppendBuffer = (((XSComplexTypeDecl)localObject4).fContentType == 1);
      }
    }
    if ((this.fCurrentElemDecl != null) && (this.fCurrentElemDecl.getAbstract())) {
      reportSchemaError("cvc-elt.2", new Object[] { paramQName.rawname });
    }
    if (this.fElementDepth == 0) {
      this.fValidationRoot = paramQName.rawname;
    }
    if (this.fNormalizeData)
    {
      this.fFirstChunk = true;
      this.fTrailing = false;
      this.fUnionType = false;
      this.fWhiteSpace = -1;
    }
    if (this.fCurrentType.getTypeCategory() == 15)
    {
      localObject4 = (XSComplexTypeDecl)this.fCurrentType;
      if (((XSComplexTypeDecl)localObject4).getAbstract()) {
        reportSchemaError("cvc-type.2", new Object[] { paramQName.rawname });
      }
      if ((this.fNormalizeData) && (((XSComplexTypeDecl)localObject4).fContentType == 1)) {
        if (((XSComplexTypeDecl)localObject4).fXSSimpleType.getVariety() == 3) {
          this.fUnionType = true;
        } else {
          try
          {
            this.fWhiteSpace = ((XSComplexTypeDecl)localObject4).fXSSimpleType.getWhitespace();
          }
          catch (DatatypeException localDatatypeException1) {}
        }
      }
    }
    else if (this.fNormalizeData)
    {
      localObject4 = (XSSimpleType)this.fCurrentType;
      if (((XSSimpleTypeDefinition)localObject4).getVariety() == 3) {
        this.fUnionType = true;
      } else {
        try
        {
          this.fWhiteSpace = ((XSSimpleType)localObject4).getWhitespace();
        }
        catch (DatatypeException localDatatypeException2) {}
      }
    }
    this.fCurrentCM = null;
    if (this.fCurrentType.getTypeCategory() == 15) {
      this.fCurrentCM = ((XSComplexTypeDecl)this.fCurrentType).getContentModel(this.fCMBuilder);
    }
    this.fCurrCMState = null;
    if (this.fCurrentCM != null) {
      this.fCurrCMState = this.fCurrentCM.startContentModel();
    }
    Object localObject4 = paramXMLAttributes.getValue(SchemaSymbols.URI_XSI, SchemaSymbols.XSI_NIL);
    if ((localObject4 != null) && (this.fCurrentElemDecl != null)) {
      this.fNil = getXsiNil(paramQName, (String)localObject4);
    }
    XSAttributeGroupDecl localXSAttributeGroupDecl = null;
    if (this.fCurrentType.getTypeCategory() == 15)
    {
      XSComplexTypeDecl localXSComplexTypeDecl = (XSComplexTypeDecl)this.fCurrentType;
      localXSAttributeGroupDecl = localXSComplexTypeDecl.getAttrGrp();
    }
    if (this.fIDCChecking)
    {
      this.fValueStoreCache.startElement();
      this.fMatcherStack.pushContext();
      if ((this.fCurrentElemDecl != null) && (this.fCurrentElemDecl.fIDCPos > 0))
      {
        this.fIdConstraint = true;
        this.fValueStoreCache.initValueStoresFor(this.fCurrentElemDecl, this);
      }
    }
    processAttributes(paramQName, paramXMLAttributes, localXSAttributeGroupDecl);
    if (localXSAttributeGroupDecl != null) {
      addDefaultAttributes(paramQName, paramXMLAttributes, localXSAttributeGroupDecl);
    }
    int i = this.fMatcherStack.getMatcherCount();
    for (int j = 0; j < i; j++)
    {
      XPathMatcher localXPathMatcher = this.fMatcherStack.getMatcherAt(j);
      localXPathMatcher.startElement(paramQName, paramXMLAttributes);
    }
    if (this.fAugPSVI)
    {
      paramAugmentations = getEmptyAugs(paramAugmentations);
      this.fCurrentPSVI.fValidationContext = this.fValidationRoot;
      this.fCurrentPSVI.fDeclaration = this.fCurrentElemDecl;
      this.fCurrentPSVI.fTypeDecl = this.fCurrentType;
      this.fCurrentPSVI.fNotation = this.fNotation;
      this.fCurrentPSVI.fNil = this.fNil;
    }
    return paramAugmentations;
  }
  
  Augmentations handleEndElement(org.apache.xerces.xni.QName paramQName, Augmentations paramAugmentations)
  {
    if (this.fSkipValidationDepth >= 0)
    {
      if ((this.fSkipValidationDepth == this.fElementDepth) && (this.fSkipValidationDepth > 0))
      {
        this.fNFullValidationDepth = (this.fSkipValidationDepth - 1);
        this.fSkipValidationDepth = -1;
        this.fElementDepth -= 1;
        this.fSubElement = this.fSubElementStack[this.fElementDepth];
        this.fCurrentElemDecl = this.fElemDeclStack[this.fElementDepth];
        this.fNil = this.fNilStack[this.fElementDepth];
        this.fNotation = this.fNotationStack[this.fElementDepth];
        this.fCurrentType = this.fTypeStack[this.fElementDepth];
        this.fCurrentCM = this.fCMStack[this.fElementDepth];
        this.fStrictAssess = this.fStrictAssessStack[this.fElementDepth];
        this.fCurrCMState = this.fCMStateStack[this.fElementDepth];
        this.fSawText = this.fSawTextStack[this.fElementDepth];
        this.fSawCharacters = this.fStringContent[this.fElementDepth];
      }
      else
      {
        this.fElementDepth -= 1;
      }
      if ((this.fElementDepth == -1) && (this.fFullChecking) && (!this.fUseGrammarPoolOnly)) {
        XSConstraints.fullSchemaChecking(this.fGrammarBucket, this.fSubGroupHandler, this.fCMBuilder, this.fXSIErrorReporter.fErrorReporter);
      }
      if (this.fAugPSVI) {
        paramAugmentations = getEmptyAugs(paramAugmentations);
      }
      return paramAugmentations;
    }
    processElementContent(paramQName);
    if (this.fIDCChecking)
    {
      int i = this.fMatcherStack.getMatcherCount();
      for (int j = i - 1; j >= 0; j--)
      {
        XPathMatcher localXPathMatcher1 = this.fMatcherStack.getMatcherAt(j);
        if (this.fCurrentElemDecl == null) {
          localXPathMatcher1.endElement(paramQName, this.fCurrentType, false, this.fValidatedInfo.actualValue, this.fValidatedInfo.actualValueType, this.fValidatedInfo.itemValueTypes);
        } else {
          localXPathMatcher1.endElement(paramQName, this.fCurrentType, this.fCurrentElemDecl.getNillable(), this.fDefaultValue == null ? this.fValidatedInfo.actualValue : this.fCurrentElemDecl.fDefault.actualValue, this.fDefaultValue == null ? this.fValidatedInfo.actualValueType : this.fCurrentElemDecl.fDefault.actualValueType, this.fDefaultValue == null ? this.fValidatedInfo.itemValueTypes : this.fCurrentElemDecl.fDefault.itemValueTypes);
        }
      }
      if (this.fMatcherStack.size() > 0) {
        this.fMatcherStack.popContext();
      }
      int k = this.fMatcherStack.getMatcherCount();
      Object localObject1;
      Object localObject2;
      for (int m = i - 1; m >= k; m--)
      {
        XPathMatcher localXPathMatcher2 = this.fMatcherStack.getMatcherAt(m);
        if ((localXPathMatcher2 instanceof Selector.Matcher))
        {
          localObject1 = (Selector.Matcher)localXPathMatcher2;
          if (((localObject2 = ((Selector.Matcher)localObject1).getIdentityConstraint()) != null) && (((IdentityConstraint)localObject2).getCategory() != 2)) {
            this.fValueStoreCache.transplant((IdentityConstraint)localObject2, ((Selector.Matcher)localObject1).getInitialDepth());
          }
        }
      }
      for (int n = i - 1; n >= k; n--)
      {
        localObject1 = this.fMatcherStack.getMatcherAt(n);
        if ((localObject1 instanceof Selector.Matcher))
        {
          localObject2 = (Selector.Matcher)localObject1;
          IdentityConstraint localIdentityConstraint;
          if (((localIdentityConstraint = ((Selector.Matcher)localObject2).getIdentityConstraint()) != null) && (localIdentityConstraint.getCategory() == 2))
          {
            ValueStoreBase localValueStoreBase = this.fValueStoreCache.getValueStoreFor(localIdentityConstraint, ((Selector.Matcher)localObject2).getInitialDepth());
            if (localValueStoreBase != null) {
              localValueStoreBase.endDocumentFragment();
            }
          }
        }
      }
      this.fValueStoreCache.endElement();
    }
    if (this.fElementDepth < this.fIgnoreXSITypeDepth) {
      this.fIgnoreXSITypeDepth -= 1;
    }
    SchemaGrammar[] arrayOfSchemaGrammar = null;
    if (this.fElementDepth == 0)
    {
      String str = this.fValidationState.checkIDRefID();
      this.fValidationState.resetIDTables();
      if (str != null) {
        reportSchemaError("cvc-id.1", new Object[] { str });
      }
      if ((this.fFullChecking) && (!this.fUseGrammarPoolOnly)) {
        XSConstraints.fullSchemaChecking(this.fGrammarBucket, this.fSubGroupHandler, this.fCMBuilder, this.fXSIErrorReporter.fErrorReporter);
      }
      arrayOfSchemaGrammar = this.fGrammarBucket.getGrammars();
      if (this.fGrammarPool != null) {
        this.fGrammarPool.cacheGrammars("http://www.w3.org/2001/XMLSchema", arrayOfSchemaGrammar);
      }
      paramAugmentations = endElementPSVI(true, arrayOfSchemaGrammar, paramAugmentations);
    }
    else
    {
      paramAugmentations = endElementPSVI(false, arrayOfSchemaGrammar, paramAugmentations);
      this.fElementDepth -= 1;
      this.fSubElement = this.fSubElementStack[this.fElementDepth];
      this.fCurrentElemDecl = this.fElemDeclStack[this.fElementDepth];
      this.fNil = this.fNilStack[this.fElementDepth];
      this.fNotation = this.fNotationStack[this.fElementDepth];
      this.fCurrentType = this.fTypeStack[this.fElementDepth];
      this.fCurrentCM = this.fCMStack[this.fElementDepth];
      this.fStrictAssess = this.fStrictAssessStack[this.fElementDepth];
      this.fCurrCMState = this.fCMStateStack[this.fElementDepth];
      this.fSawText = this.fSawTextStack[this.fElementDepth];
      this.fSawCharacters = this.fStringContent[this.fElementDepth];
      this.fWhiteSpace = -1;
      this.fAppendBuffer = false;
      this.fUnionType = false;
    }
    return paramAugmentations;
  }
  
  final Augmentations endElementPSVI(boolean paramBoolean, SchemaGrammar[] paramArrayOfSchemaGrammar, Augmentations paramAugmentations)
  {
    if (this.fAugPSVI)
    {
      paramAugmentations = getEmptyAugs(paramAugmentations);
      this.fCurrentPSVI.fDeclaration = this.fCurrentElemDecl;
      this.fCurrentPSVI.fTypeDecl = this.fCurrentType;
      this.fCurrentPSVI.fNotation = this.fNotation;
      this.fCurrentPSVI.fValidationContext = this.fValidationRoot;
      this.fCurrentPSVI.fNil = this.fNil;
      if (this.fElementDepth > this.fNFullValidationDepth) {
        this.fCurrentPSVI.fValidationAttempted = 2;
      } else if (this.fElementDepth > this.fNNoneValidationDepth) {
        this.fCurrentPSVI.fValidationAttempted = 0;
      } else {
        this.fCurrentPSVI.fValidationAttempted = 1;
      }
      if (this.fNFullValidationDepth == this.fElementDepth) {
        this.fNFullValidationDepth = (this.fElementDepth - 1);
      }
      if (this.fNNoneValidationDepth == this.fElementDepth) {
        this.fNNoneValidationDepth = (this.fElementDepth - 1);
      }
      if (this.fDefaultValue != null) {
        this.fCurrentPSVI.fSpecified = true;
      }
      this.fCurrentPSVI.fMemberType = this.fValidatedInfo.memberType;
      this.fCurrentPSVI.fNormalizedValue = this.fValidatedInfo.normalizedValue;
      this.fCurrentPSVI.fActualValue = this.fValidatedInfo.actualValue;
      this.fCurrentPSVI.fActualValueType = this.fValidatedInfo.actualValueType;
      this.fCurrentPSVI.fItemValueTypes = this.fValidatedInfo.itemValueTypes;
      if (this.fStrictAssess)
      {
        String[] arrayOfString = this.fXSIErrorReporter.mergeContext();
        this.fCurrentPSVI.fErrorCodes = arrayOfString;
        this.fCurrentPSVI.fValidity = (arrayOfString == null ? 2 : 1);
      }
      else
      {
        this.fCurrentPSVI.fValidity = 0;
        this.fXSIErrorReporter.popContext();
      }
      if (paramBoolean)
      {
        this.fCurrentPSVI.fGrammars = paramArrayOfSchemaGrammar;
        this.fCurrentPSVI.fSchemaInformation = null;
      }
    }
    return paramAugmentations;
  }
  
  Augmentations getEmptyAugs(Augmentations paramAugmentations)
  {
    if (paramAugmentations == null)
    {
      paramAugmentations = this.fAugmentations;
      paramAugmentations.removeAllItems();
    }
    paramAugmentations.putItem("ELEMENT_PSVI", this.fCurrentPSVI);
    this.fCurrentPSVI.reset();
    return paramAugmentations;
  }
  
  void storeLocations(String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (!XMLSchemaLoader.tokenizeSchemaLocationStr(paramString1, this.fLocationPairs))) {
      this.fXSIErrorReporter.reportError("http://www.w3.org/TR/xml-schema-1", "SchemaLocation", new Object[] { paramString1 }, (short)0);
    }
    if (paramString2 != null)
    {
      XMLSchemaLoader.LocationArray localLocationArray = (XMLSchemaLoader.LocationArray)this.fLocationPairs.get(XMLSymbols.EMPTY_STRING);
      if (localLocationArray == null)
      {
        localLocationArray = new XMLSchemaLoader.LocationArray();
        this.fLocationPairs.put(XMLSymbols.EMPTY_STRING, localLocationArray);
      }
      localLocationArray.addLocation(paramString2);
    }
  }
  
  SchemaGrammar findSchemaGrammar(short paramShort, String paramString, org.apache.xerces.xni.QName paramQName1, org.apache.xerces.xni.QName paramQName2, XMLAttributes paramXMLAttributes)
  {
    SchemaGrammar localSchemaGrammar = null;
    localSchemaGrammar = this.fGrammarBucket.getGrammar(paramString);
    if (localSchemaGrammar == null)
    {
      this.fXSDDescription.reset();
      this.fXSDDescription.fContextType = paramShort;
      this.fXSDDescription.setNamespace(paramString);
      this.fXSDDescription.fEnclosedElementName = paramQName1;
      this.fXSDDescription.fTriggeringComponent = paramQName2;
      this.fXSDDescription.fAttributes = paramXMLAttributes;
      if (this.fLocator != null) {
        this.fXSDDescription.setBaseSystemId(this.fLocator.getExpandedSystemId());
      }
      String[] arrayOfString1 = null;
      Object localObject = this.fLocationPairs.get(paramString == null ? XMLSymbols.EMPTY_STRING : paramString);
      if (localObject != null) {
        arrayOfString1 = ((XMLSchemaLoader.LocationArray)localObject).getLocationArray();
      }
      if ((arrayOfString1 != null) && (arrayOfString1.length != 0))
      {
        this.fXSDDescription.fLocationHints = new String[arrayOfString1.length];
        System.arraycopy(arrayOfString1, 0, this.fXSDDescription.fLocationHints, 0, arrayOfString1.length);
      }
      if (this.fGrammarPool != null)
      {
        localSchemaGrammar = (SchemaGrammar)this.fGrammarPool.retrieveGrammar(this.fXSDDescription);
        if ((localSchemaGrammar != null) && (!this.fGrammarBucket.putGrammar(localSchemaGrammar, true)))
        {
          this.fXSIErrorReporter.fErrorReporter.reportError("http://www.w3.org/TR/xml-schema-1", "GrammarConflict", null, (short)0);
          localSchemaGrammar = null;
        }
      }
      if ((localSchemaGrammar == null) && (!this.fUseGrammarPoolOnly)) {
        try
        {
          XMLInputSource localXMLInputSource = XMLSchemaLoader.resolveDocument(this.fXSDDescription, this.fLocationPairs, this.fEntityResolver);
          localSchemaGrammar = this.fSchemaLoader.loadSchema(this.fXSDDescription, localXMLInputSource, this.fLocationPairs);
        }
        catch (IOException localIOException)
        {
          String[] arrayOfString2 = this.fXSDDescription.getLocationHints();
          this.fXSIErrorReporter.fErrorReporter.reportError("http://www.w3.org/TR/xml-schema-1", "schema_reference.4", new Object[] { arrayOfString2 != null ? arrayOfString2[0] : XMLSymbols.EMPTY_STRING }, (short)0);
        }
      }
    }
    return localSchemaGrammar;
  }
  
  XSTypeDefinition getAndCheckXsiType(org.apache.xerces.xni.QName paramQName, String paramString, XMLAttributes paramXMLAttributes)
  {
    org.apache.xerces.xni.QName localQName = null;
    try
    {
      localQName = (org.apache.xerces.xni.QName)this.fQNameDV.validate(paramString, this.fValidationState, null);
    }
    catch (InvalidDatatypeValueException localInvalidDatatypeValueException)
    {
      reportSchemaError(localInvalidDatatypeValueException.getKey(), localInvalidDatatypeValueException.getArgs());
      reportSchemaError("cvc-elt.4.1", new Object[] { paramQName.rawname, SchemaSymbols.URI_XSI + "," + SchemaSymbols.XSI_TYPE, paramString });
      return null;
    }
    XSTypeDefinition localXSTypeDefinition = null;
    if (localQName.uri == SchemaSymbols.URI_SCHEMAFORSCHEMA) {
      localXSTypeDefinition = SchemaGrammar.SG_SchemaNS.getGlobalTypeDecl(localQName.localpart);
    }
    if (localXSTypeDefinition == null)
    {
      SchemaGrammar localSchemaGrammar = findSchemaGrammar((short)7, localQName.uri, paramQName, localQName, paramXMLAttributes);
      if (localSchemaGrammar != null) {
        localXSTypeDefinition = localSchemaGrammar.getGlobalTypeDecl(localQName.localpart);
      }
    }
    if (localXSTypeDefinition == null)
    {
      reportSchemaError("cvc-elt.4.2", new Object[] { paramQName.rawname, paramString });
      return null;
    }
    if (this.fCurrentType != null)
    {
      short s = 0;
      if (this.fCurrentElemDecl != null) {
        s = this.fCurrentElemDecl.fBlock;
      }
      if (this.fCurrentType.getTypeCategory() == 15) {
        s = (short)(s | ((XSComplexTypeDecl)this.fCurrentType).fBlock);
      }
      if (!XSConstraints.checkTypeDerivationOk(localXSTypeDefinition, this.fCurrentType, s)) {
        reportSchemaError("cvc-elt.4.3", new Object[] { paramQName.rawname, paramString, this.fCurrentType.getName() });
      }
    }
    return localXSTypeDefinition;
  }
  
  boolean getXsiNil(org.apache.xerces.xni.QName paramQName, String paramString)
  {
    if ((this.fCurrentElemDecl != null) && (!this.fCurrentElemDecl.getNillable()))
    {
      reportSchemaError("cvc-elt.3.1", new Object[] { paramQName.rawname, SchemaSymbols.URI_XSI + "," + SchemaSymbols.XSI_NIL });
    }
    else
    {
      String str = XMLChar.trim(paramString);
      if ((str.equals("true")) || (str.equals("1")))
      {
        if ((this.fCurrentElemDecl != null) && (this.fCurrentElemDecl.getConstraintType() == 2)) {
          reportSchemaError("cvc-elt.3.2.2", new Object[] { paramQName.rawname, SchemaSymbols.URI_XSI + "," + SchemaSymbols.XSI_NIL });
        }
        return true;
      }
    }
    return false;
  }
  
  void processAttributes(org.apache.xerces.xni.QName paramQName, XMLAttributes paramXMLAttributes, XSAttributeGroupDecl paramXSAttributeGroupDecl)
  {
    String str = null;
    int i = paramXMLAttributes.getLength();
    Augmentations localAugmentations = null;
    AttributePSVImpl localAttributePSVImpl = null;
    int j = (this.fCurrentType == null) || (this.fCurrentType.getTypeCategory() == 16) ? 1 : 0;
    XSObjectList localXSObjectList = null;
    int k = 0;
    XSWildcardDecl localXSWildcardDecl = null;
    if (j == 0)
    {
      localXSObjectList = paramXSAttributeGroupDecl.getAttributeUses();
      k = localXSObjectList.getLength();
      localXSWildcardDecl = paramXSAttributeGroupDecl.fAttributeWC;
    }
    for (int m = 0; m < i; m++)
    {
      paramXMLAttributes.getName(m, this.fTempQName);
      if ((this.fAugPSVI) || (this.fIdConstraint))
      {
        localAugmentations = paramXMLAttributes.getAugmentations(m);
        localAttributePSVImpl = (AttributePSVImpl)localAugmentations.getItem("ATTRIBUTE_PSVI");
        if (localAttributePSVImpl != null)
        {
          localAttributePSVImpl.reset();
        }
        else
        {
          localAttributePSVImpl = new AttributePSVImpl();
          localAugmentations.putItem("ATTRIBUTE_PSVI", localAttributePSVImpl);
        }
        localAttributePSVImpl.fValidationContext = this.fValidationRoot;
      }
      Object localObject;
      if (this.fTempQName.uri == SchemaSymbols.URI_XSI)
      {
        localObject = null;
        if (this.fTempQName.localpart == SchemaSymbols.XSI_TYPE) {
          localObject = XSI_TYPE;
        } else if (this.fTempQName.localpart == SchemaSymbols.XSI_NIL) {
          localObject = XSI_NIL;
        } else if (this.fTempQName.localpart == SchemaSymbols.XSI_SCHEMALOCATION) {
          localObject = XSI_SCHEMALOCATION;
        } else if (this.fTempQName.localpart == SchemaSymbols.XSI_NONAMESPACESCHEMALOCATION) {
          localObject = XSI_NONAMESPACESCHEMALOCATION;
        }
        if (localObject != null)
        {
          processOneAttribute(paramQName, paramXMLAttributes, m, (XSAttributeDecl)localObject, null, localAttributePSVImpl);
          continue;
        }
      }
      if ((this.fTempQName.rawname != XMLSymbols.PREFIX_XMLNS) && (!this.fTempQName.rawname.startsWith("xmlns:"))) {
        if (j != 0)
        {
          reportSchemaError("cvc-type.3.1.1", new Object[] { paramQName.rawname, this.fTempQName.rawname });
        }
        else
        {
          localObject = null;
          for (int n = 0; n < k; n++)
          {
            XSAttributeUseImpl localXSAttributeUseImpl = (XSAttributeUseImpl)localXSObjectList.item(n);
            if ((localXSAttributeUseImpl.fAttrDecl.fName == this.fTempQName.localpart) && (localXSAttributeUseImpl.fAttrDecl.fTargetNamespace == this.fTempQName.uri))
            {
              localObject = localXSAttributeUseImpl;
              break;
            }
          }
          if ((localObject == null) && ((localXSWildcardDecl == null) || (!localXSWildcardDecl.allowNamespace(this.fTempQName.uri))))
          {
            reportSchemaError("cvc-complex-type.3.2.2", new Object[] { paramQName.rawname, this.fTempQName.rawname });
          }
          else
          {
            XSAttributeDecl localXSAttributeDecl = null;
            if (localObject != null)
            {
              localXSAttributeDecl = ((XSAttributeUseImpl)localObject).fAttrDecl;
            }
            else
            {
              if (localXSWildcardDecl.fProcessContents == 2) {
                continue;
              }
              SchemaGrammar localSchemaGrammar = findSchemaGrammar((short)6, this.fTempQName.uri, paramQName, this.fTempQName, paramXMLAttributes);
              if (localSchemaGrammar != null) {
                localXSAttributeDecl = localSchemaGrammar.getGlobalAttributeDecl(this.fTempQName.localpart);
              }
              if (localXSAttributeDecl == null)
              {
                if (localXSWildcardDecl.fProcessContents != 1) {
                  continue;
                }
                reportSchemaError("cvc-complex-type.3.2.2", new Object[] { paramQName.rawname, this.fTempQName.rawname });
                continue;
              }
              if ((localXSAttributeDecl.fType.getTypeCategory() == 16) && (localXSAttributeDecl.fType.isIDType())) {
                if (str != null) {
                  reportSchemaError("cvc-complex-type.5.1", new Object[] { paramQName.rawname, localXSAttributeDecl.fName, str });
                } else {
                  str = localXSAttributeDecl.fName;
                }
              }
            }
            processOneAttribute(paramQName, paramXMLAttributes, m, localXSAttributeDecl, (XSAttributeUseImpl)localObject, localAttributePSVImpl);
          }
        }
      }
    }
    if ((j == 0) && (paramXSAttributeGroupDecl.fIDAttrName != null) && (str != null)) {
      reportSchemaError("cvc-complex-type.5.2", new Object[] { paramQName.rawname, str, paramXSAttributeGroupDecl.fIDAttrName });
    }
  }
  
  void processOneAttribute(org.apache.xerces.xni.QName paramQName, XMLAttributes paramXMLAttributes, int paramInt, XSAttributeDecl paramXSAttributeDecl, XSAttributeUseImpl paramXSAttributeUseImpl, AttributePSVImpl paramAttributePSVImpl)
  {
    String str = paramXMLAttributes.getValue(paramInt);
    this.fXSIErrorReporter.pushContext();
    XSSimpleType localXSSimpleType = paramXSAttributeDecl.fType;
    Object localObject1 = null;
    try
    {
      localObject1 = localXSSimpleType.validate(str, this.fValidationState, this.fValidatedInfo);
      if (this.fNormalizeData) {
        paramXMLAttributes.setValue(paramInt, this.fValidatedInfo.normalizedValue);
      }
      Object localObject2;
      if ((paramXMLAttributes instanceof XMLAttributesImpl))
      {
        localObject2 = (XMLAttributesImpl)paramXMLAttributes;
        boolean bool = this.fValidatedInfo.memberType != null ? this.fValidatedInfo.memberType.isIDType() : localXSSimpleType.isIDType();
        ((XMLAttributesImpl)localObject2).setSchemaId(paramInt, bool);
      }
      if ((localXSSimpleType.getVariety() == 1) && (localXSSimpleType.getPrimitiveKind() == 20))
      {
        localObject2 = (org.apache.xerces.xni.QName)localObject1;
        SchemaGrammar localSchemaGrammar = this.fGrammarBucket.getGrammar(((org.apache.xerces.xni.QName)localObject2).uri);
        if (localSchemaGrammar != null) {
          this.fNotation = localSchemaGrammar.getGlobalNotationDecl(((org.apache.xerces.xni.QName)localObject2).localpart);
        }
      }
    }
    catch (InvalidDatatypeValueException localInvalidDatatypeValueException)
    {
      reportSchemaError(localInvalidDatatypeValueException.getKey(), localInvalidDatatypeValueException.getArgs());
      reportSchemaError("cvc-attribute.3", new Object[] { paramQName.rawname, this.fTempQName.rawname, str, localXSSimpleType.getName() });
    }
    if ((localObject1 != null) && (paramXSAttributeDecl.getConstraintType() == 2) && ((!ValidatedInfo.isComparable(this.fValidatedInfo, paramXSAttributeDecl.fDefault)) || (!localObject1.equals(paramXSAttributeDecl.fDefault.actualValue)))) {
      reportSchemaError("cvc-attribute.4", new Object[] { paramQName.rawname, this.fTempQName.rawname, str, paramXSAttributeDecl.fDefault.stringValue() });
    }
    if ((localObject1 != null) && (paramXSAttributeUseImpl != null) && (paramXSAttributeUseImpl.fConstraintType == 2) && ((!ValidatedInfo.isComparable(this.fValidatedInfo, paramXSAttributeUseImpl.fDefault)) || (!localObject1.equals(paramXSAttributeUseImpl.fDefault.actualValue)))) {
      reportSchemaError("cvc-complex-type.3.1", new Object[] { paramQName.rawname, this.fTempQName.rawname, str, paramXSAttributeUseImpl.fDefault.stringValue() });
    }
    if (this.fIdConstraint) {
      paramAttributePSVImpl.fActualValue = localObject1;
    }
    if (this.fAugPSVI)
    {
      paramAttributePSVImpl.fDeclaration = paramXSAttributeDecl;
      paramAttributePSVImpl.fTypeDecl = localXSSimpleType;
      paramAttributePSVImpl.fMemberType = this.fValidatedInfo.memberType;
      paramAttributePSVImpl.fNormalizedValue = this.fValidatedInfo.normalizedValue;
      paramAttributePSVImpl.fActualValue = this.fValidatedInfo.actualValue;
      paramAttributePSVImpl.fActualValueType = this.fValidatedInfo.actualValueType;
      paramAttributePSVImpl.fItemValueTypes = this.fValidatedInfo.itemValueTypes;
      paramAttributePSVImpl.fValidationAttempted = 2;
      String[] arrayOfString = this.fXSIErrorReporter.mergeContext();
      paramAttributePSVImpl.fErrorCodes = arrayOfString;
      paramAttributePSVImpl.fValidity = (arrayOfString == null ? 2 : 1);
    }
  }
  
  void addDefaultAttributes(org.apache.xerces.xni.QName paramQName, XMLAttributes paramXMLAttributes, XSAttributeGroupDecl paramXSAttributeGroupDecl)
  {
    XSObjectList localXSObjectList = paramXSAttributeGroupDecl.getAttributeUses();
    int i = localXSObjectList.getLength();
    for (int m = 0; m < i; m++)
    {
      XSAttributeUseImpl localXSAttributeUseImpl = (XSAttributeUseImpl)localXSObjectList.item(m);
      XSAttributeDecl localXSAttributeDecl = localXSAttributeUseImpl.fAttrDecl;
      int j = localXSAttributeUseImpl.fConstraintType;
      ValidatedInfo localValidatedInfo = localXSAttributeUseImpl.fDefault;
      if (j == 0)
      {
        j = localXSAttributeDecl.getConstraintType();
        localValidatedInfo = localXSAttributeDecl.fDefault;
      }
      int k = paramXMLAttributes.getValue(localXSAttributeDecl.fTargetNamespace, localXSAttributeDecl.fName) != null ? 1 : 0;
      if ((localXSAttributeUseImpl.fUse == 1) && (k == 0)) {
        reportSchemaError("cvc-complex-type.4", new Object[] { paramQName.rawname, localXSAttributeDecl.fName });
      }
      if ((k == 0) && (j != 0))
      {
        org.apache.xerces.xni.QName localQName = new org.apache.xerces.xni.QName(null, localXSAttributeDecl.fName, localXSAttributeDecl.fName, localXSAttributeDecl.fTargetNamespace);
        String str = localValidatedInfo != null ? localValidatedInfo.stringValue() : "";
        Object localObject;
        int n;
        if ((paramXMLAttributes instanceof XMLAttributesImpl))
        {
          localObject = (XMLAttributesImpl)paramXMLAttributes;
          n = ((XMLAttributesImpl)localObject).getLength();
          ((XMLAttributesImpl)localObject).addAttributeNS(localQName, "CDATA", str);
          boolean bool = (localValidatedInfo != null) && (localValidatedInfo.memberType != null) ? localValidatedInfo.memberType.isIDType() : localXSAttributeDecl.fType.isIDType();
          ((XMLAttributesImpl)localObject).setSchemaId(n, bool);
        }
        else
        {
          n = paramXMLAttributes.addAttribute(localQName, "CDATA", str);
        }
        if (this.fAugPSVI)
        {
          localObject = paramXMLAttributes.getAugmentations(n);
          AttributePSVImpl localAttributePSVImpl = new AttributePSVImpl();
          ((Augmentations)localObject).putItem("ATTRIBUTE_PSVI", localAttributePSVImpl);
          localAttributePSVImpl.fDeclaration = localXSAttributeDecl;
          localAttributePSVImpl.fTypeDecl = localXSAttributeDecl.fType;
          localAttributePSVImpl.fMemberType = localValidatedInfo.memberType;
          localAttributePSVImpl.fNormalizedValue = str;
          localAttributePSVImpl.fActualValue = localValidatedInfo.actualValue;
          localAttributePSVImpl.fActualValueType = localValidatedInfo.actualValueType;
          localAttributePSVImpl.fItemValueTypes = localValidatedInfo.itemValueTypes;
          localAttributePSVImpl.fValidationContext = this.fValidationRoot;
          localAttributePSVImpl.fValidity = 2;
          localAttributePSVImpl.fValidationAttempted = 2;
          localAttributePSVImpl.fSpecified = true;
        }
      }
    }
  }
  
  void processElementContent(org.apache.xerces.xni.QName paramQName)
  {
    Object localObject;
    if ((this.fCurrentElemDecl != null) && (this.fCurrentElemDecl.fDefault != null) && (!this.fSawText) && (!this.fSubElement) && (!this.fNil))
    {
      localObject = this.fCurrentElemDecl.fDefault.stringValue();
      int i = ((String)localObject).length();
      if ((this.fNormalizedStr.ch == null) || (this.fNormalizedStr.ch.length < i)) {
        this.fNormalizedStr.ch = new char[i];
      }
      ((String)localObject).getChars(0, i, this.fNormalizedStr.ch, 0);
      this.fNormalizedStr.offset = 0;
      this.fNormalizedStr.length = i;
      this.fDefaultValue = this.fNormalizedStr;
    }
    this.fValidatedInfo.normalizedValue = null;
    if ((this.fNil) && ((this.fSubElement) || (this.fSawText))) {
      reportSchemaError("cvc-elt.3.2.1", new Object[] { paramQName.rawname, SchemaSymbols.URI_XSI + "," + SchemaSymbols.XSI_NIL });
    }
    this.fValidatedInfo.reset();
    if ((this.fCurrentElemDecl != null) && (this.fCurrentElemDecl.getConstraintType() != 0) && (!this.fSubElement) && (!this.fSawText) && (!this.fNil))
    {
      if ((this.fCurrentType != this.fCurrentElemDecl.fType) && (XSConstraints.ElementDefaultValidImmediate(this.fCurrentType, this.fCurrentElemDecl.fDefault.stringValue(), this.fState4XsiType, null) == null)) {
        reportSchemaError("cvc-elt.5.1.1", new Object[] { paramQName.rawname, this.fCurrentType.getName(), this.fCurrentElemDecl.fDefault.stringValue() });
      }
      elementLocallyValidType(paramQName, this.fCurrentElemDecl.fDefault.stringValue());
    }
    else
    {
      localObject = elementLocallyValidType(paramQName, this.fBuffer);
      if ((this.fCurrentElemDecl != null) && (this.fCurrentElemDecl.getConstraintType() == 2) && (!this.fNil))
      {
        String str = this.fBuffer.toString();
        if (this.fSubElement) {
          reportSchemaError("cvc-elt.5.2.2.1", new Object[] { paramQName.rawname });
        }
        if (this.fCurrentType.getTypeCategory() == 15)
        {
          XSComplexTypeDecl localXSComplexTypeDecl = (XSComplexTypeDecl)this.fCurrentType;
          if (localXSComplexTypeDecl.fContentType == 3)
          {
            if (!this.fCurrentElemDecl.fDefault.normalizedValue.equals(str)) {
              reportSchemaError("cvc-elt.5.2.2.2.1", new Object[] { paramQName.rawname, str, this.fCurrentElemDecl.fDefault.normalizedValue });
            }
          }
          else if ((localXSComplexTypeDecl.fContentType == 1) && (localObject != null) && ((!ValidatedInfo.isComparable(this.fValidatedInfo, this.fCurrentElemDecl.fDefault)) || (!localObject.equals(this.fCurrentElemDecl.fDefault.actualValue)))) {
            reportSchemaError("cvc-elt.5.2.2.2.2", new Object[] { paramQName.rawname, str, this.fCurrentElemDecl.fDefault.stringValue() });
          }
        }
        else if ((this.fCurrentType.getTypeCategory() == 16) && (localObject != null) && ((!ValidatedInfo.isComparable(this.fValidatedInfo, this.fCurrentElemDecl.fDefault)) || (!localObject.equals(this.fCurrentElemDecl.fDefault.actualValue))))
        {
          reportSchemaError("cvc-elt.5.2.2.2.2", new Object[] { paramQName.rawname, str, this.fCurrentElemDecl.fDefault.stringValue() });
        }
      }
    }
    if ((this.fDefaultValue == null) && (this.fNormalizeData) && (this.fDocumentHandler != null) && (this.fUnionType))
    {
      localObject = this.fValidatedInfo.normalizedValue;
      if (localObject == null) {
        localObject = this.fBuffer.toString();
      }
      int j = ((String)localObject).length();
      if ((this.fNormalizedStr.ch == null) || (this.fNormalizedStr.ch.length < j)) {
        this.fNormalizedStr.ch = new char[j];
      }
      ((String)localObject).getChars(0, j, this.fNormalizedStr.ch, 0);
      this.fNormalizedStr.offset = 0;
      this.fNormalizedStr.length = j;
      this.fDocumentHandler.characters(this.fNormalizedStr, null);
    }
  }
  
  Object elementLocallyValidType(org.apache.xerces.xni.QName paramQName, Object paramObject)
  {
    if (this.fCurrentType == null) {
      return null;
    }
    Object localObject = null;
    if (this.fCurrentType.getTypeCategory() == 16)
    {
      if (this.fSubElement) {
        reportSchemaError("cvc-type.3.1.2", new Object[] { paramQName.rawname });
      }
      if (!this.fNil)
      {
        XSSimpleType localXSSimpleType = (XSSimpleType)this.fCurrentType;
        try
        {
          if ((!this.fNormalizeData) || (this.fUnionType)) {
            this.fValidationState.setNormalizationRequired(true);
          }
          localObject = localXSSimpleType.validate(paramObject, this.fValidationState, this.fValidatedInfo);
        }
        catch (InvalidDatatypeValueException localInvalidDatatypeValueException)
        {
          reportSchemaError(localInvalidDatatypeValueException.getKey(), localInvalidDatatypeValueException.getArgs());
          reportSchemaError("cvc-type.3.1.3", new Object[] { paramQName.rawname, paramObject });
        }
      }
    }
    else
    {
      localObject = elementLocallyValidComplexType(paramQName, paramObject);
    }
    return localObject;
  }
  
  Object elementLocallyValidComplexType(org.apache.xerces.xni.QName paramQName, Object paramObject)
  {
    Object localObject1 = null;
    XSComplexTypeDecl localXSComplexTypeDecl = (XSComplexTypeDecl)this.fCurrentType;
    if (!this.fNil)
    {
      Object localObject2;
      if ((localXSComplexTypeDecl.fContentType == 0) && ((this.fSubElement) || (this.fSawText)))
      {
        reportSchemaError("cvc-complex-type.2.1", new Object[] { paramQName.rawname });
      }
      else if (localXSComplexTypeDecl.fContentType == 1)
      {
        if (this.fSubElement) {
          reportSchemaError("cvc-complex-type.2.2", new Object[] { paramQName.rawname });
        }
        localObject2 = localXSComplexTypeDecl.fXSSimpleType;
        try
        {
          if ((!this.fNormalizeData) || (this.fUnionType)) {
            this.fValidationState.setNormalizationRequired(true);
          }
          localObject1 = ((XSSimpleType)localObject2).validate(paramObject, this.fValidationState, this.fValidatedInfo);
        }
        catch (InvalidDatatypeValueException localInvalidDatatypeValueException)
        {
          reportSchemaError(localInvalidDatatypeValueException.getKey(), localInvalidDatatypeValueException.getArgs());
          reportSchemaError("cvc-complex-type.2.2", new Object[] { paramQName.rawname });
        }
      }
      else if ((localXSComplexTypeDecl.fContentType == 2) && (this.fSawCharacters))
      {
        reportSchemaError("cvc-complex-type.2.3", new Object[] { paramQName.rawname });
      }
      if (((localXSComplexTypeDecl.fContentType == 2) || (localXSComplexTypeDecl.fContentType == 3)) && (this.fCurrCMState[0] >= 0) && (!this.fCurrentCM.endContentModel(this.fCurrCMState)))
      {
        localObject2 = expectedStr(this.fCurrentCM.whatCanGoHere(this.fCurrCMState));
        reportSchemaError("cvc-complex-type.2.4.b", new Object[] { paramQName.rawname, localObject2 });
      }
    }
    return localObject1;
  }
  
  void reportSchemaError(String paramString, Object[] paramArrayOfObject)
  {
    if (this.fDoValidation) {
      this.fXSIErrorReporter.reportError("http://www.w3.org/TR/xml-schema-1", paramString, paramArrayOfObject, (short)1);
    }
  }
  
  private String expectedStr(Vector paramVector)
  {
    StringBuffer localStringBuffer = new StringBuffer("{");
    int i = paramVector.size();
    for (int j = 0; j < i; j++)
    {
      if (j > 0) {
        localStringBuffer.append(", ");
      }
      localStringBuffer.append(paramVector.elementAt(j).toString());
    }
    localStringBuffer.append('}');
    return localStringBuffer.toString();
  }
  
  protected static final class ShortVector
  {
    private int fLength;
    private short[] fData;
    
    public ShortVector() {}
    
    public ShortVector(int paramInt)
    {
      this.fData = new short[paramInt];
    }
    
    public int length()
    {
      return this.fLength;
    }
    
    public void add(short paramShort)
    {
      ensureCapacity(this.fLength + 1);
      this.fData[(this.fLength++)] = paramShort;
    }
    
    public short valueAt(int paramInt)
    {
      return this.fData[paramInt];
    }
    
    public void clear()
    {
      this.fLength = 0;
    }
    
    public boolean contains(short paramShort)
    {
      for (int i = 0; i < this.fLength; i++) {
        if (this.fData[i] == paramShort) {
          return true;
        }
      }
      return false;
    }
    
    private void ensureCapacity(int paramInt)
    {
      if (this.fData == null)
      {
        this.fData = new short[8];
      }
      else if (this.fData.length <= paramInt)
      {
        short[] arrayOfShort = new short[this.fData.length * 2];
        System.arraycopy(this.fData, 0, arrayOfShort, 0, this.fData.length);
        this.fData = arrayOfShort;
      }
    }
  }
  
  protected static final class LocalIDKey
  {
    public IdentityConstraint fId;
    public int fDepth;
    
    public LocalIDKey() {}
    
    public LocalIDKey(IdentityConstraint paramIdentityConstraint, int paramInt)
    {
      this.fId = paramIdentityConstraint;
      this.fDepth = paramInt;
    }
    
    public int hashCode()
    {
      return this.fId.hashCode() + this.fDepth;
    }
    
    public boolean equals(Object paramObject)
    {
      if ((paramObject instanceof LocalIDKey))
      {
        LocalIDKey localLocalIDKey = (LocalIDKey)paramObject;
        return (localLocalIDKey.fId == this.fId) && (localLocalIDKey.fDepth == this.fDepth);
      }
      return false;
    }
  }
  
  protected class ValueStoreCache
  {
    final XMLSchemaValidator.LocalIDKey fLocalId = new XMLSchemaValidator.LocalIDKey();
    protected final Vector fValueStores = new Vector();
    protected final Hashtable fIdentityConstraint2ValueStoreMap = new Hashtable();
    protected final Stack fGlobalMapStack = new Stack();
    protected final Hashtable fGlobalIDConstraintMap = new Hashtable();
    
    public ValueStoreCache() {}
    
    public void startDocument()
    {
      this.fValueStores.removeAllElements();
      this.fIdentityConstraint2ValueStoreMap.clear();
      this.fGlobalIDConstraintMap.clear();
      this.fGlobalMapStack.removeAllElements();
    }
    
    public void startElement()
    {
      if (this.fGlobalIDConstraintMap.size() > 0) {
        this.fGlobalMapStack.push(this.fGlobalIDConstraintMap.clone());
      } else {
        this.fGlobalMapStack.push(null);
      }
      this.fGlobalIDConstraintMap.clear();
    }
    
    public void endElement()
    {
      if (this.fGlobalMapStack.isEmpty()) {
        return;
      }
      Hashtable localHashtable = (Hashtable)this.fGlobalMapStack.pop();
      if (localHashtable == null) {
        return;
      }
      Enumeration localEnumeration = localHashtable.keys();
      while (localEnumeration.hasMoreElements())
      {
        IdentityConstraint localIdentityConstraint = (IdentityConstraint)localEnumeration.nextElement();
        XMLSchemaValidator.ValueStoreBase localValueStoreBase1 = (XMLSchemaValidator.ValueStoreBase)localHashtable.get(localIdentityConstraint);
        if (localValueStoreBase1 != null)
        {
          XMLSchemaValidator.ValueStoreBase localValueStoreBase2 = (XMLSchemaValidator.ValueStoreBase)this.fGlobalIDConstraintMap.get(localIdentityConstraint);
          if (localValueStoreBase2 == null) {
            this.fGlobalIDConstraintMap.put(localIdentityConstraint, localValueStoreBase1);
          } else if (localValueStoreBase2 != localValueStoreBase1) {
            localValueStoreBase2.append(localValueStoreBase1);
          }
        }
      }
    }
    
    public void initValueStoresFor(XSElementDecl paramXSElementDecl, FieldActivator paramFieldActivator)
    {
      IdentityConstraint[] arrayOfIdentityConstraint = paramXSElementDecl.fIDConstraints;
      int i = paramXSElementDecl.fIDCPos;
      for (int j = 0; j < i; j++)
      {
        XMLSchemaValidator.LocalIDKey localLocalIDKey;
        switch (arrayOfIdentityConstraint[j].getCategory())
        {
        case 3: 
          UniqueOrKey localUniqueOrKey1 = (UniqueOrKey)arrayOfIdentityConstraint[j];
          localLocalIDKey = new XMLSchemaValidator.LocalIDKey(localUniqueOrKey1, XMLSchemaValidator.this.fElementDepth);
          XMLSchemaValidator.UniqueValueStore localUniqueValueStore = (XMLSchemaValidator.UniqueValueStore)this.fIdentityConstraint2ValueStoreMap.get(localLocalIDKey);
          if (localUniqueValueStore == null)
          {
            localUniqueValueStore = new XMLSchemaValidator.UniqueValueStore(XMLSchemaValidator.this, localUniqueOrKey1);
            this.fIdentityConstraint2ValueStoreMap.put(localLocalIDKey, localUniqueValueStore);
          }
          else
          {
            localUniqueValueStore.clear();
          }
          this.fValueStores.addElement(localUniqueValueStore);
          XMLSchemaValidator.this.activateSelectorFor(arrayOfIdentityConstraint[j]);
          break;
        case 1: 
          UniqueOrKey localUniqueOrKey2 = (UniqueOrKey)arrayOfIdentityConstraint[j];
          localLocalIDKey = new XMLSchemaValidator.LocalIDKey(localUniqueOrKey2, XMLSchemaValidator.this.fElementDepth);
          XMLSchemaValidator.KeyValueStore localKeyValueStore = (XMLSchemaValidator.KeyValueStore)this.fIdentityConstraint2ValueStoreMap.get(localLocalIDKey);
          if (localKeyValueStore == null)
          {
            localKeyValueStore = new XMLSchemaValidator.KeyValueStore(XMLSchemaValidator.this, localUniqueOrKey2);
            this.fIdentityConstraint2ValueStoreMap.put(localLocalIDKey, localKeyValueStore);
          }
          else
          {
            localKeyValueStore.clear();
          }
          this.fValueStores.addElement(localKeyValueStore);
          XMLSchemaValidator.this.activateSelectorFor(arrayOfIdentityConstraint[j]);
          break;
        case 2: 
          KeyRef localKeyRef = (KeyRef)arrayOfIdentityConstraint[j];
          localLocalIDKey = new XMLSchemaValidator.LocalIDKey(localKeyRef, XMLSchemaValidator.this.fElementDepth);
          XMLSchemaValidator.KeyRefValueStore localKeyRefValueStore = (XMLSchemaValidator.KeyRefValueStore)this.fIdentityConstraint2ValueStoreMap.get(localLocalIDKey);
          if (localKeyRefValueStore == null)
          {
            localKeyRefValueStore = new XMLSchemaValidator.KeyRefValueStore(XMLSchemaValidator.this, localKeyRef, null);
            this.fIdentityConstraint2ValueStoreMap.put(localLocalIDKey, localKeyRefValueStore);
          }
          else
          {
            localKeyRefValueStore.clear();
          }
          this.fValueStores.addElement(localKeyRefValueStore);
          XMLSchemaValidator.this.activateSelectorFor(arrayOfIdentityConstraint[j]);
        }
      }
    }
    
    public XMLSchemaValidator.ValueStoreBase getValueStoreFor(IdentityConstraint paramIdentityConstraint, int paramInt)
    {
      this.fLocalId.fDepth = paramInt;
      this.fLocalId.fId = paramIdentityConstraint;
      return (XMLSchemaValidator.ValueStoreBase)this.fIdentityConstraint2ValueStoreMap.get(this.fLocalId);
    }
    
    public XMLSchemaValidator.ValueStoreBase getGlobalValueStoreFor(IdentityConstraint paramIdentityConstraint)
    {
      return (XMLSchemaValidator.ValueStoreBase)this.fGlobalIDConstraintMap.get(paramIdentityConstraint);
    }
    
    public void transplant(IdentityConstraint paramIdentityConstraint, int paramInt)
    {
      this.fLocalId.fDepth = paramInt;
      this.fLocalId.fId = paramIdentityConstraint;
      XMLSchemaValidator.ValueStoreBase localValueStoreBase1 = (XMLSchemaValidator.ValueStoreBase)this.fIdentityConstraint2ValueStoreMap.get(this.fLocalId);
      if (paramIdentityConstraint.getCategory() == 2) {
        return;
      }
      XMLSchemaValidator.ValueStoreBase localValueStoreBase2 = (XMLSchemaValidator.ValueStoreBase)this.fGlobalIDConstraintMap.get(paramIdentityConstraint);
      if (localValueStoreBase2 != null)
      {
        localValueStoreBase2.append(localValueStoreBase1);
        this.fGlobalIDConstraintMap.put(paramIdentityConstraint, localValueStoreBase2);
      }
      else
      {
        this.fGlobalIDConstraintMap.put(paramIdentityConstraint, localValueStoreBase1);
      }
    }
    
    public void endDocument()
    {
      int i = this.fValueStores.size();
      for (int j = 0; j < i; j++)
      {
        XMLSchemaValidator.ValueStoreBase localValueStoreBase = (XMLSchemaValidator.ValueStoreBase)this.fValueStores.elementAt(j);
        localValueStoreBase.endDocument();
      }
    }
    
    public String toString()
    {
      String str = super.toString();
      int i = str.lastIndexOf('$');
      if (i != -1) {
        return str.substring(i + 1);
      }
      int j = str.lastIndexOf('.');
      if (j != -1) {
        return str.substring(j + 1);
      }
      return str;
    }
  }
  
  protected class KeyRefValueStore
    extends XMLSchemaValidator.ValueStoreBase
  {
    protected XMLSchemaValidator.ValueStoreBase fKeyValueStore;
    
    public KeyRefValueStore(KeyRef paramKeyRef, XMLSchemaValidator.KeyValueStore paramKeyValueStore)
    {
      super(paramKeyRef);
      this.fKeyValueStore = paramKeyValueStore;
    }
    
    public void endDocumentFragment()
    {
      super.endDocumentFragment();
      this.fKeyValueStore = ((XMLSchemaValidator.ValueStoreBase)XMLSchemaValidator.this.fValueStoreCache.fGlobalIDConstraintMap.get(((KeyRef)this.fIdentityConstraint).getKey()));
      String str2;
      if (this.fKeyValueStore == null)
      {
        String str1 = "KeyRefOutOfScope";
        str2 = this.fIdentityConstraint.toString();
        XMLSchemaValidator.this.reportSchemaError(str1, new Object[] { str2 });
        return;
      }
      int i = this.fKeyValueStore.contains(this);
      if (i != -1)
      {
        str2 = "KeyNotFound";
        String str3 = toString(this.fValues, i, this.fFieldCount);
        String str4 = this.fIdentityConstraint.getElementName();
        String str5 = this.fIdentityConstraint.getName();
        XMLSchemaValidator.this.reportSchemaError(str2, new Object[] { str5, str3, str4 });
      }
    }
    
    public void endDocument()
    {
      super.endDocument();
    }
  }
  
  protected class KeyValueStore
    extends XMLSchemaValidator.ValueStoreBase
  {
    public KeyValueStore(UniqueOrKey paramUniqueOrKey)
    {
      super(paramUniqueOrKey);
    }
    
    protected void checkDuplicateValues()
    {
      if (contains())
      {
        String str1 = "DuplicateKey";
        String str2 = toString(this.fLocalValues);
        String str3 = this.fIdentityConstraint.getElementName();
        String str4 = this.fIdentityConstraint.getIdentityConstraintName();
        XMLSchemaValidator.this.reportSchemaError(str1, new Object[] { str2, str3, str4 });
      }
    }
  }
  
  protected class UniqueValueStore
    extends XMLSchemaValidator.ValueStoreBase
  {
    public UniqueValueStore(UniqueOrKey paramUniqueOrKey)
    {
      super(paramUniqueOrKey);
    }
    
    protected void checkDuplicateValues()
    {
      if (contains())
      {
        String str1 = "DuplicateUnique";
        String str2 = toString(this.fLocalValues);
        String str3 = this.fIdentityConstraint.getElementName();
        String str4 = this.fIdentityConstraint.getIdentityConstraintName();
        XMLSchemaValidator.this.reportSchemaError(str1, new Object[] { str2, str3, str4 });
      }
    }
  }
  
  protected abstract class ValueStoreBase
    implements ValueStore
  {
    protected IdentityConstraint fIdentityConstraint;
    protected int fFieldCount = 0;
    protected Field[] fFields = null;
    protected Object[] fLocalValues = null;
    protected short[] fLocalValueTypes = null;
    protected ShortList[] fLocalItemValueTypes = null;
    protected int fValuesCount;
    public final Vector fValues = new Vector();
    public XMLSchemaValidator.ShortVector fValueTypes = null;
    public Vector fItemValueTypes = null;
    private boolean fUseValueTypeVector = false;
    private int fValueTypesLength = 0;
    private short fValueType = 0;
    private boolean fUseItemValueTypeVector = false;
    private int fItemValueTypesLength = 0;
    private ShortList fItemValueType = null;
    final StringBuffer fTempBuffer = new StringBuffer();
    
    protected ValueStoreBase(IdentityConstraint paramIdentityConstraint)
    {
      this.fIdentityConstraint = paramIdentityConstraint;
      this.fFieldCount = this.fIdentityConstraint.getFieldCount();
      this.fFields = new Field[this.fFieldCount];
      this.fLocalValues = new Object[this.fFieldCount];
      this.fLocalValueTypes = new short[this.fFieldCount];
      this.fLocalItemValueTypes = new ShortList[this.fFieldCount];
      for (int i = 0; i < this.fFieldCount; i++) {
        this.fFields[i] = this.fIdentityConstraint.getFieldAt(i);
      }
    }
    
    public void clear()
    {
      this.fValuesCount = 0;
      this.fUseValueTypeVector = false;
      this.fValueTypesLength = 0;
      this.fValueType = 0;
      this.fUseItemValueTypeVector = false;
      this.fItemValueTypesLength = 0;
      this.fItemValueType = null;
      this.fValues.setSize(0);
      if (this.fValueTypes != null) {
        this.fValueTypes.clear();
      }
      if (this.fItemValueTypes != null) {
        this.fItemValueTypes.setSize(0);
      }
    }
    
    public void append(ValueStoreBase paramValueStoreBase)
    {
      for (int i = 0; i < paramValueStoreBase.fValues.size(); i++) {
        this.fValues.addElement(paramValueStoreBase.fValues.elementAt(i));
      }
    }
    
    public void startValueScope()
    {
      this.fValuesCount = 0;
      for (int i = 0; i < this.fFieldCount; i++)
      {
        this.fLocalValues[i] = null;
        this.fLocalValueTypes[i] = 0;
        this.fLocalItemValueTypes[i] = null;
      }
    }
    
    public void endValueScope()
    {
      String str1;
      Object localObject;
      String str2;
      if (this.fValuesCount == 0)
      {
        if (this.fIdentityConstraint.getCategory() == 1)
        {
          str1 = "AbsentKeyValue";
          localObject = this.fIdentityConstraint.getElementName();
          str2 = this.fIdentityConstraint.getIdentityConstraintName();
          XMLSchemaValidator.this.reportSchemaError(str1, new Object[] { localObject, str2 });
        }
        return;
      }
      if (this.fValuesCount != this.fFieldCount)
      {
        if (this.fIdentityConstraint.getCategory() == 1)
        {
          str1 = "KeyNotEnoughValues";
          localObject = (UniqueOrKey)this.fIdentityConstraint;
          str2 = this.fIdentityConstraint.getElementName();
          String str3 = ((IdentityConstraint)localObject).getIdentityConstraintName();
          XMLSchemaValidator.this.reportSchemaError(str1, new Object[] { str2, str3 });
        }
        return;
      }
    }
    
    public void endDocumentFragment() {}
    
    public void endDocument() {}
    
    public void reportError(String paramString, Object[] paramArrayOfObject)
    {
      XMLSchemaValidator.this.reportSchemaError(paramString, paramArrayOfObject);
    }
    
    public void addValue(Field paramField, boolean paramBoolean, Object paramObject, short paramShort, ShortList paramShortList)
    {
      for (int i = this.fFieldCount - 1; i > -1; i--) {
        if (this.fFields[i] == paramField) {
          break;
        }
      }
      String str1;
      String str2;
      if (i == -1)
      {
        str1 = "UnknownField";
        str2 = this.fIdentityConstraint.getElementName();
        String str3 = this.fIdentityConstraint.getIdentityConstraintName();
        XMLSchemaValidator.this.reportSchemaError(str1, new Object[] { paramField.toString(), str2, str3 });
        return;
      }
      if (!paramBoolean)
      {
        str1 = "FieldMultipleMatch";
        str2 = this.fIdentityConstraint.getIdentityConstraintName();
        XMLSchemaValidator.this.reportSchemaError(str1, new Object[] { paramField.toString(), str2 });
      }
      else
      {
        this.fValuesCount += 1;
      }
      this.fLocalValues[i] = paramObject;
      this.fLocalValueTypes[i] = paramShort;
      this.fLocalItemValueTypes[i] = paramShortList;
      if (this.fValuesCount == this.fFieldCount)
      {
        checkDuplicateValues();
        for (i = 0; i < this.fFieldCount; i++)
        {
          this.fValues.addElement(this.fLocalValues[i]);
          addValueType(this.fLocalValueTypes[i]);
          addItemValueType(this.fLocalItemValueTypes[i]);
        }
      }
    }
    
    public boolean contains()
    {
      int i = 0;
      int j = this.fValues.size();
      for (int k = 0; k < j; k = i)
      {
        i = k + this.fFieldCount;
        for (int m = 0; m < this.fFieldCount; m++)
        {
          Object localObject1 = this.fLocalValues[m];
          Object localObject2 = this.fValues.elementAt(k);
          int n = this.fLocalValueTypes[m];
          int i1 = getValueTypeAt(k);
          if ((localObject1 == null) || (localObject2 == null) || (n != i1) || (!localObject1.equals(localObject2))) {
            break;
          }
          if ((n == 44) || (n == 43))
          {
            ShortList localShortList1 = this.fLocalItemValueTypes[m];
            ShortList localShortList2 = getItemValueTypeAt(k);
            if ((localShortList1 == null) || (localShortList2 == null) || (!localShortList1.equals(localShortList2))) {
              break;
            }
          }
          k++;
        }
        return true;
      }
      return false;
    }
    
    public int contains(ValueStoreBase paramValueStoreBase)
    {
      Vector localVector = paramValueStoreBase.fValues;
      int i = localVector.size();
      int j;
      int k;
      if (this.fFieldCount <= 1)
      {
        for (j = 0; j < i; j++)
        {
          k = paramValueStoreBase.getValueTypeAt(j);
          if ((!valueTypeContains(k)) || (!this.fValues.contains(localVector.elementAt(j)))) {
            return j;
          }
          if ((k == 44) || (k == 43))
          {
            ShortList localShortList1 = paramValueStoreBase.getItemValueTypeAt(j);
            if (!itemValueTypeContains(localShortList1)) {
              return j;
            }
          }
        }
      }
      else
      {
        j = this.fValues.size();
        k = 0;
        int m;
        while (m < i)
        {
          int n = 0;
          while (n < j)
          {
            for (int i1 = 0; i1 < this.fFieldCount; i1++)
            {
              Object localObject1 = localVector.elementAt(k + i1);
              Object localObject2 = this.fValues.elementAt(n + i1);
              int i2 = paramValueStoreBase.getValueTypeAt(k + i1);
              int i3 = getValueTypeAt(n + i1);
              if ((localObject1 != localObject2) && ((i2 != i3) || (localObject1 == null) || (!localObject1.equals(localObject2)))) {
                break;
              }
              if ((i2 == 44) || (i2 == 43))
              {
                ShortList localShortList2 = paramValueStoreBase.getItemValueTypeAt(k + i1);
                ShortList localShortList3 = getItemValueTypeAt(n + i1);
                if ((localShortList2 == null) || (localShortList3 == null) || (!localShortList2.equals(localShortList3))) {
                  break;
                }
              }
            }
            break;
            n += this.fFieldCount;
          }
          return k;
          k += this.fFieldCount;
        }
      }
      return -1;
    }
    
    protected void checkDuplicateValues() {}
    
    protected String toString(Object[] paramArrayOfObject)
    {
      int i = paramArrayOfObject.length;
      if (i == 0) {
        return "";
      }
      this.fTempBuffer.setLength(0);
      for (int j = 0; j < i; j++)
      {
        if (j > 0) {
          this.fTempBuffer.append(',');
        }
        this.fTempBuffer.append(paramArrayOfObject[j]);
      }
      return this.fTempBuffer.toString();
    }
    
    protected String toString(Vector paramVector, int paramInt1, int paramInt2)
    {
      if (paramInt2 == 0) {
        return "";
      }
      if (paramInt2 == 1) {
        return String.valueOf(paramVector.elementAt(paramInt1));
      }
      StringBuffer localStringBuffer = new StringBuffer();
      for (int i = 0; i < paramInt2; i++)
      {
        if (i > 0) {
          localStringBuffer.append(',');
        }
        localStringBuffer.append(paramVector.elementAt(paramInt1 + i));
      }
      return localStringBuffer.toString();
    }
    
    public String toString()
    {
      String str = super.toString();
      int i = str.lastIndexOf('$');
      if (i != -1) {
        str = str.substring(i + 1);
      }
      int j = str.lastIndexOf('.');
      if (j != -1) {
        str = str.substring(j + 1);
      }
      return str + '[' + this.fIdentityConstraint + ']';
    }
    
    private void addValueType(short paramShort)
    {
      if (this.fUseValueTypeVector)
      {
        this.fValueTypes.add(paramShort);
      }
      else if (this.fValueTypesLength++ == 0)
      {
        this.fValueType = paramShort;
      }
      else if (this.fValueType != paramShort)
      {
        this.fUseValueTypeVector = true;
        if (this.fValueTypes == null) {
          this.fValueTypes = new XMLSchemaValidator.ShortVector(this.fValueTypesLength * 2);
        }
        for (int i = 1; i < this.fValueTypesLength; i++) {
          this.fValueTypes.add(this.fValueType);
        }
        this.fValueTypes.add(paramShort);
      }
    }
    
    private short getValueTypeAt(int paramInt)
    {
      if (this.fUseValueTypeVector) {
        return this.fValueTypes.valueAt(paramInt);
      }
      return this.fValueType;
    }
    
    private boolean valueTypeContains(short paramShort)
    {
      if (this.fUseValueTypeVector) {
        return this.fValueTypes.contains(paramShort);
      }
      return this.fValueType == paramShort;
    }
    
    private void addItemValueType(ShortList paramShortList)
    {
      if (this.fUseItemValueTypeVector)
      {
        this.fItemValueTypes.add(paramShortList);
      }
      else if (this.fItemValueTypesLength++ == 0)
      {
        this.fItemValueType = paramShortList;
      }
      else if ((this.fItemValueType != paramShortList) && ((this.fItemValueType == null) || (!this.fItemValueType.equals(paramShortList))))
      {
        this.fUseItemValueTypeVector = true;
        if (this.fItemValueTypes == null) {
          this.fItemValueTypes = new Vector(this.fItemValueTypesLength * 2);
        }
        for (int i = 1; i < this.fItemValueTypesLength; i++) {
          this.fItemValueTypes.add(this.fItemValueType);
        }
        this.fItemValueTypes.add(paramShortList);
      }
    }
    
    private ShortList getItemValueTypeAt(int paramInt)
    {
      if (this.fUseItemValueTypeVector) {
        return (ShortList)this.fItemValueTypes.elementAt(paramInt);
      }
      return this.fItemValueType;
    }
    
    private boolean itemValueTypeContains(ShortList paramShortList)
    {
      if (this.fUseItemValueTypeVector) {
        return this.fItemValueTypes.contains(paramShortList);
      }
      return (this.fItemValueType == paramShortList) || ((this.fItemValueType != null) && (this.fItemValueType.equals(paramShortList)));
    }
  }
  
  protected static class XPathMatcherStack
  {
    protected XPathMatcher[] fMatchers = new XPathMatcher[4];
    protected int fMatchersCount;
    protected IntStack fContextStack = new IntStack();
    
    public void clear()
    {
      for (int i = 0; i < this.fMatchersCount; i++) {
        this.fMatchers[i] = null;
      }
      this.fMatchersCount = 0;
      this.fContextStack.clear();
    }
    
    public int size()
    {
      return this.fContextStack.size();
    }
    
    public int getMatcherCount()
    {
      return this.fMatchersCount;
    }
    
    public void addMatcher(XPathMatcher paramXPathMatcher)
    {
      ensureMatcherCapacity();
      this.fMatchers[(this.fMatchersCount++)] = paramXPathMatcher;
    }
    
    public XPathMatcher getMatcherAt(int paramInt)
    {
      return this.fMatchers[paramInt];
    }
    
    public void pushContext()
    {
      this.fContextStack.push(this.fMatchersCount);
    }
    
    public void popContext()
    {
      this.fMatchersCount = this.fContextStack.pop();
    }
    
    private void ensureMatcherCapacity()
    {
      if (this.fMatchersCount == this.fMatchers.length)
      {
        XPathMatcher[] arrayOfXPathMatcher = new XPathMatcher[this.fMatchers.length * 2];
        System.arraycopy(this.fMatchers, 0, arrayOfXPathMatcher, 0, this.fMatchers.length);
        this.fMatchers = arrayOfXPathMatcher;
      }
    }
  }
  
  protected final class XSIErrorReporter
  {
    XMLErrorReporter fErrorReporter;
    Vector fErrors = new Vector();
    int[] fContext = new int[8];
    int fContextCount;
    
    protected XSIErrorReporter() {}
    
    public void reset(XMLErrorReporter paramXMLErrorReporter)
    {
      this.fErrorReporter = paramXMLErrorReporter;
      this.fErrors.removeAllElements();
      this.fContextCount = 0;
    }
    
    public void pushContext()
    {
      if (!XMLSchemaValidator.this.fAugPSVI) {
        return;
      }
      if (this.fContextCount == this.fContext.length)
      {
        int i = this.fContextCount + 8;
        int[] arrayOfInt = new int[i];
        System.arraycopy(this.fContext, 0, arrayOfInt, 0, this.fContextCount);
        this.fContext = arrayOfInt;
      }
      this.fContext[(this.fContextCount++)] = this.fErrors.size();
    }
    
    public String[] popContext()
    {
      if (!XMLSchemaValidator.this.fAugPSVI) {
        return null;
      }
      int i = this.fContext[(--this.fContextCount)];
      int j = this.fErrors.size() - i;
      if (j == 0) {
        return null;
      }
      String[] arrayOfString = new String[j];
      for (int k = 0; k < j; k++) {
        arrayOfString[k] = ((String)this.fErrors.elementAt(i + k));
      }
      this.fErrors.setSize(i);
      return arrayOfString;
    }
    
    public String[] mergeContext()
    {
      if (!XMLSchemaValidator.this.fAugPSVI) {
        return null;
      }
      int i = this.fContext[(--this.fContextCount)];
      int j = this.fErrors.size() - i;
      if (j == 0) {
        return null;
      }
      String[] arrayOfString = new String[j];
      for (int k = 0; k < j; k++) {
        arrayOfString[k] = ((String)this.fErrors.elementAt(i + k));
      }
      return arrayOfString;
    }
    
    public void reportError(String paramString1, String paramString2, Object[] paramArrayOfObject, short paramShort)
      throws XNIException
    {
      this.fErrorReporter.reportError(paramString1, paramString2, paramArrayOfObject, paramShort);
      if (XMLSchemaValidator.this.fAugPSVI) {
        this.fErrors.addElement(paramString2);
      }
    }
    
    public void reportError(XMLLocator paramXMLLocator, String paramString1, String paramString2, Object[] paramArrayOfObject, short paramShort)
      throws XNIException
    {
      this.fErrorReporter.reportError(paramXMLLocator, paramString1, paramString2, paramArrayOfObject, paramShort);
      if (XMLSchemaValidator.this.fAugPSVI) {
        this.fErrors.addElement(paramString2);
      }
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.XMLSchemaValidator
 * JD-Core Version:    0.7.0.1
 */
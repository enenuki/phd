package org.apache.xerces.impl.dtd;

import java.io.IOException;
import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.RevalidationHandler;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.dtd.models.ContentModelValidator;
import org.apache.xerces.impl.dv.DTDDVFactory;
import org.apache.xerces.impl.dv.DatatypeException;
import org.apache.xerces.impl.dv.DatatypeValidator;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.impl.validation.ValidationState;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDocumentFilter;
import org.apache.xerces.xni.parser.XMLDocumentSource;

public class XMLDTDValidator
  implements XMLComponent, XMLDocumentFilter, XMLDTDValidatorFilter, RevalidationHandler
{
  private static final int TOP_LEVEL_SCOPE = -1;
  protected static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
  protected static final String VALIDATION = "http://xml.org/sax/features/validation";
  protected static final String DYNAMIC_VALIDATION = "http://apache.org/xml/features/validation/dynamic";
  protected static final String BALANCE_SYNTAX_TREES = "http://apache.org/xml/features/validation/balance-syntax-trees";
  protected static final String WARN_ON_DUPLICATE_ATTDEF = "http://apache.org/xml/features/validation/warn-on-duplicate-attdef";
  protected static final String PARSER_SETTINGS = "http://apache.org/xml/features/internal/parser-settings";
  protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
  protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
  protected static final String GRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
  protected static final String DATATYPE_VALIDATOR_FACTORY = "http://apache.org/xml/properties/internal/datatype-validator-factory";
  protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
  private static final String[] RECOGNIZED_FEATURES = { "http://xml.org/sax/features/namespaces", "http://xml.org/sax/features/validation", "http://apache.org/xml/features/validation/dynamic", "http://apache.org/xml/features/validation/balance-syntax-trees" };
  private static final Boolean[] FEATURE_DEFAULTS = { null, null, Boolean.FALSE, Boolean.FALSE };
  private static final String[] RECOGNIZED_PROPERTIES = { "http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/grammar-pool", "http://apache.org/xml/properties/internal/datatype-validator-factory", "http://apache.org/xml/properties/internal/validation-manager" };
  private static final Object[] PROPERTY_DEFAULTS = { null, null, null, null, null };
  private static final boolean DEBUG_ATTRIBUTES = false;
  private static final boolean DEBUG_ELEMENT_CHILDREN = false;
  protected ValidationManager fValidationManager = null;
  protected final ValidationState fValidationState = new ValidationState();
  protected boolean fNamespaces;
  protected boolean fValidation;
  protected boolean fDTDValidation;
  protected boolean fDynamicValidation;
  protected boolean fBalanceSyntaxTrees;
  protected boolean fWarnDuplicateAttdef;
  protected SymbolTable fSymbolTable;
  protected XMLErrorReporter fErrorReporter;
  protected XMLGrammarPool fGrammarPool;
  protected DTDGrammarBucket fGrammarBucket;
  protected XMLLocator fDocLocation;
  protected NamespaceContext fNamespaceContext = null;
  protected DTDDVFactory fDatatypeValidatorFactory;
  protected XMLDocumentHandler fDocumentHandler;
  protected XMLDocumentSource fDocumentSource;
  protected DTDGrammar fDTDGrammar;
  protected boolean fSeenDoctypeDecl = false;
  private boolean fPerformValidation;
  private String fSchemaType;
  private final QName fCurrentElement = new QName();
  private int fCurrentElementIndex = -1;
  private int fCurrentContentSpecType = -1;
  private final QName fRootElement = new QName();
  private boolean fInCDATASection = false;
  private int[] fElementIndexStack = new int[8];
  private int[] fContentSpecTypeStack = new int[8];
  private QName[] fElementQNamePartsStack = new QName[8];
  private QName[] fElementChildren = new QName[32];
  private int fElementChildrenLength = 0;
  private int[] fElementChildrenOffsetStack = new int[32];
  private int fElementDepth = -1;
  private boolean fSeenRootElement = false;
  private boolean fInElementContent = false;
  private XMLElementDecl fTempElementDecl = new XMLElementDecl();
  private XMLAttributeDecl fTempAttDecl = new XMLAttributeDecl();
  private XMLEntityDecl fEntityDecl = new XMLEntityDecl();
  private QName fTempQName = new QName();
  private StringBuffer fBuffer = new StringBuffer();
  protected DatatypeValidator fValID;
  protected DatatypeValidator fValIDRef;
  protected DatatypeValidator fValIDRefs;
  protected DatatypeValidator fValENTITY;
  protected DatatypeValidator fValENTITIES;
  protected DatatypeValidator fValNMTOKEN;
  protected DatatypeValidator fValNMTOKENS;
  protected DatatypeValidator fValNOTATION;
  
  public XMLDTDValidator()
  {
    for (int i = 0; i < this.fElementQNamePartsStack.length; i++) {
      this.fElementQNamePartsStack[i] = new QName();
    }
    this.fGrammarBucket = new DTDGrammarBucket();
  }
  
  DTDGrammarBucket getGrammarBucket()
  {
    return this.fGrammarBucket;
  }
  
  public void reset(XMLComponentManager paramXMLComponentManager)
    throws XMLConfigurationException
  {
    this.fDTDGrammar = null;
    this.fSeenDoctypeDecl = false;
    this.fInCDATASection = false;
    this.fSeenRootElement = false;
    this.fInElementContent = false;
    this.fCurrentElementIndex = -1;
    this.fCurrentContentSpecType = -1;
    this.fRootElement.clear();
    this.fValidationState.resetIDTables();
    this.fGrammarBucket.clear();
    this.fElementDepth = -1;
    this.fElementChildrenLength = 0;
    boolean bool;
    try
    {
      bool = paramXMLComponentManager.getFeature("http://apache.org/xml/features/internal/parser-settings");
    }
    catch (XMLConfigurationException localXMLConfigurationException1)
    {
      bool = true;
    }
    if (!bool)
    {
      this.fValidationManager.addValidationState(this.fValidationState);
      return;
    }
    try
    {
      this.fNamespaces = paramXMLComponentManager.getFeature("http://xml.org/sax/features/namespaces");
    }
    catch (XMLConfigurationException localXMLConfigurationException2)
    {
      this.fNamespaces = true;
    }
    try
    {
      this.fValidation = paramXMLComponentManager.getFeature("http://xml.org/sax/features/validation");
    }
    catch (XMLConfigurationException localXMLConfigurationException3)
    {
      this.fValidation = false;
    }
    try
    {
      this.fDTDValidation = (!paramXMLComponentManager.getFeature("http://apache.org/xml/features/validation/schema"));
    }
    catch (XMLConfigurationException localXMLConfigurationException4)
    {
      this.fDTDValidation = true;
    }
    try
    {
      this.fDynamicValidation = paramXMLComponentManager.getFeature("http://apache.org/xml/features/validation/dynamic");
    }
    catch (XMLConfigurationException localXMLConfigurationException5)
    {
      this.fDynamicValidation = false;
    }
    try
    {
      this.fBalanceSyntaxTrees = paramXMLComponentManager.getFeature("http://apache.org/xml/features/validation/balance-syntax-trees");
    }
    catch (XMLConfigurationException localXMLConfigurationException6)
    {
      this.fBalanceSyntaxTrees = false;
    }
    try
    {
      this.fWarnDuplicateAttdef = paramXMLComponentManager.getFeature("http://apache.org/xml/features/validation/warn-on-duplicate-attdef");
    }
    catch (XMLConfigurationException localXMLConfigurationException7)
    {
      this.fWarnDuplicateAttdef = false;
    }
    try
    {
      this.fSchemaType = ((String)paramXMLComponentManager.getProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage"));
    }
    catch (XMLConfigurationException localXMLConfigurationException8)
    {
      this.fSchemaType = null;
    }
    this.fValidationManager = ((ValidationManager)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/validation-manager"));
    this.fValidationManager.addValidationState(this.fValidationState);
    this.fValidationState.setUsingNamespaces(this.fNamespaces);
    this.fErrorReporter = ((XMLErrorReporter)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter"));
    this.fSymbolTable = ((SymbolTable)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table"));
    try
    {
      this.fGrammarPool = ((XMLGrammarPool)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/grammar-pool"));
    }
    catch (XMLConfigurationException localXMLConfigurationException9)
    {
      this.fGrammarPool = null;
    }
    this.fDatatypeValidatorFactory = ((DTDDVFactory)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/datatype-validator-factory"));
    init();
  }
  
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
  {}
  
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
    if (this.fGrammarPool != null)
    {
      Grammar[] arrayOfGrammar = this.fGrammarPool.retrieveInitialGrammarSet("http://www.w3.org/TR/REC-xml");
      for (int i = 0; i < arrayOfGrammar.length; i++) {
        this.fGrammarBucket.putGrammar((DTDGrammar)arrayOfGrammar[i]);
      }
    }
    this.fDocLocation = paramXMLLocator;
    this.fNamespaceContext = paramNamespaceContext;
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.startDocument(paramXMLLocator, paramString, paramNamespaceContext, paramAugmentations);
    }
  }
  
  public void xmlDecl(String paramString1, String paramString2, String paramString3, Augmentations paramAugmentations)
    throws XNIException
  {
    this.fGrammarBucket.setStandalone((paramString3 != null) && (paramString3.equals("yes")));
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.xmlDecl(paramString1, paramString2, paramString3, paramAugmentations);
    }
  }
  
  public void doctypeDecl(String paramString1, String paramString2, String paramString3, Augmentations paramAugmentations)
    throws XNIException
  {
    this.fSeenDoctypeDecl = true;
    this.fRootElement.setValues(null, paramString1, paramString1, null);
    String str = null;
    try
    {
      str = XMLEntityManager.expandSystemId(paramString3, this.fDocLocation.getExpandedSystemId(), false);
    }
    catch (IOException localIOException) {}
    XMLDTDDescription localXMLDTDDescription = new XMLDTDDescription(paramString2, paramString3, this.fDocLocation.getExpandedSystemId(), str, paramString1);
    this.fDTDGrammar = this.fGrammarBucket.getGrammar(localXMLDTDDescription);
    if ((this.fDTDGrammar == null) && (this.fGrammarPool != null) && ((paramString3 != null) || (paramString2 != null))) {
      this.fDTDGrammar = ((DTDGrammar)this.fGrammarPool.retrieveGrammar(localXMLDTDDescription));
    }
    if (this.fDTDGrammar == null)
    {
      if (!this.fBalanceSyntaxTrees) {
        this.fDTDGrammar = new DTDGrammar(this.fSymbolTable, localXMLDTDDescription);
      } else {
        this.fDTDGrammar = new BalancedDTDGrammar(this.fSymbolTable, localXMLDTDDescription);
      }
    }
    else {
      this.fValidationManager.setCachedDTD(true);
    }
    this.fGrammarBucket.setActiveGrammar(this.fDTDGrammar);
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.doctypeDecl(paramString1, paramString2, paramString3, paramAugmentations);
    }
  }
  
  public void startElement(QName paramQName, XMLAttributes paramXMLAttributes, Augmentations paramAugmentations)
    throws XNIException
  {
    handleStartElement(paramQName, paramXMLAttributes, paramAugmentations);
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.startElement(paramQName, paramXMLAttributes, paramAugmentations);
    }
  }
  
  public void emptyElement(QName paramQName, XMLAttributes paramXMLAttributes, Augmentations paramAugmentations)
    throws XNIException
  {
    boolean bool = handleStartElement(paramQName, paramXMLAttributes, paramAugmentations);
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.emptyElement(paramQName, paramXMLAttributes, paramAugmentations);
    }
    if (!bool) {
      handleEndElement(paramQName, paramAugmentations, true);
    }
  }
  
  public void characters(XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {
    int i = 1;
    int j = 1;
    for (int k = paramXMLString.offset; k < paramXMLString.offset + paramXMLString.length; k++) {
      if (!isSpace(paramXMLString.ch[k]))
      {
        j = 0;
        break;
      }
    }
    if ((this.fInElementContent) && (j != 0) && (!this.fInCDATASection) && (this.fDocumentHandler != null))
    {
      this.fDocumentHandler.ignorableWhitespace(paramXMLString, paramAugmentations);
      i = 0;
    }
    if (this.fPerformValidation)
    {
      if (this.fInElementContent)
      {
        if ((this.fGrammarBucket.getStandalone()) && (this.fDTDGrammar.getElementDeclIsExternal(this.fCurrentElementIndex)) && (j != 0)) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_WHITE_SPACE_IN_ELEMENT_CONTENT_WHEN_STANDALONE", null, (short)1);
        }
        if (j == 0) {
          charDataInContent();
        }
        if ((paramAugmentations != null) && (paramAugmentations.getItem("CHAR_REF_PROBABLE_WS") == Boolean.TRUE)) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_CONTENT_INVALID_SPECIFIED", new Object[] { this.fCurrentElement.rawname, this.fDTDGrammar.getContentSpecAsString(this.fElementDepth), "character reference" }, (short)1);
        }
      }
      if (this.fCurrentContentSpecType == 1) {
        charDataInContent();
      }
    }
    if ((i != 0) && (this.fDocumentHandler != null)) {
      this.fDocumentHandler.characters(paramXMLString, paramAugmentations);
    }
  }
  
  public void ignorableWhitespace(XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.ignorableWhitespace(paramXMLString, paramAugmentations);
    }
  }
  
  public void endElement(QName paramQName, Augmentations paramAugmentations)
    throws XNIException
  {
    handleEndElement(paramQName, paramAugmentations, false);
  }
  
  public void startCDATA(Augmentations paramAugmentations)
    throws XNIException
  {
    if ((this.fPerformValidation) && (this.fInElementContent)) {
      charDataInContent();
    }
    this.fInCDATASection = true;
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.startCDATA(paramAugmentations);
    }
  }
  
  public void endCDATA(Augmentations paramAugmentations)
    throws XNIException
  {
    this.fInCDATASection = false;
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.endCDATA(paramAugmentations);
    }
  }
  
  public void endDocument(Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.endDocument(paramAugmentations);
    }
  }
  
  public void comment(XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {
    if ((this.fPerformValidation) && (this.fElementDepth >= 0) && (this.fDTDGrammar != null))
    {
      this.fDTDGrammar.getElementDecl(this.fCurrentElementIndex, this.fTempElementDecl);
      if (this.fTempElementDecl.type == 1) {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_CONTENT_INVALID_SPECIFIED", new Object[] { this.fCurrentElement.rawname, "EMPTY", "comment" }, (short)1);
      }
    }
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.comment(paramXMLString, paramAugmentations);
    }
  }
  
  public void processingInstruction(String paramString, XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {
    if ((this.fPerformValidation) && (this.fElementDepth >= 0) && (this.fDTDGrammar != null))
    {
      this.fDTDGrammar.getElementDecl(this.fCurrentElementIndex, this.fTempElementDecl);
      if (this.fTempElementDecl.type == 1) {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_CONTENT_INVALID_SPECIFIED", new Object[] { this.fCurrentElement.rawname, "EMPTY", "processing instruction" }, (short)1);
      }
    }
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.processingInstruction(paramString, paramXMLString, paramAugmentations);
    }
  }
  
  public void startGeneralEntity(String paramString1, XMLResourceIdentifier paramXMLResourceIdentifier, String paramString2, Augmentations paramAugmentations)
    throws XNIException
  {
    if ((this.fPerformValidation) && (this.fElementDepth >= 0) && (this.fDTDGrammar != null))
    {
      this.fDTDGrammar.getElementDecl(this.fCurrentElementIndex, this.fTempElementDecl);
      if (this.fTempElementDecl.type == 1) {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_CONTENT_INVALID_SPECIFIED", new Object[] { this.fCurrentElement.rawname, "EMPTY", "ENTITY" }, (short)1);
      }
      if (this.fGrammarBucket.getStandalone()) {
        XMLDTDProcessor.checkStandaloneEntityRef(paramString1, this.fDTDGrammar, this.fEntityDecl, this.fErrorReporter);
      }
    }
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.startGeneralEntity(paramString1, paramXMLResourceIdentifier, paramString2, paramAugmentations);
    }
  }
  
  public void endGeneralEntity(String paramString, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.endGeneralEntity(paramString, paramAugmentations);
    }
  }
  
  public void textDecl(String paramString1, String paramString2, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDocumentHandler != null) {
      this.fDocumentHandler.textDecl(paramString1, paramString2, paramAugmentations);
    }
  }
  
  public final boolean hasGrammar()
  {
    return this.fDTDGrammar != null;
  }
  
  public final boolean validate()
  {
    return (this.fSchemaType != Constants.NS_XMLSCHEMA) && (((!this.fDynamicValidation) && (this.fValidation)) || ((this.fDynamicValidation) && (this.fSeenDoctypeDecl) && ((this.fDTDValidation) || (this.fSeenDoctypeDecl))));
  }
  
  protected void addDTDDefaultAttrsAndValidate(QName paramQName, int paramInt, XMLAttributes paramXMLAttributes)
    throws XNIException
  {
    if ((paramInt == -1) || (this.fDTDGrammar == null)) {
      return;
    }
    String str3;
    String str6;
    boolean bool;
    for (int i = this.fDTDGrammar.getFirstAttributeDeclIndex(paramInt); i != -1; i = this.fDTDGrammar.getNextAttributeDeclIndex(i))
    {
      this.fDTDGrammar.getAttributeDecl(i, this.fTempAttDecl);
      String str1 = this.fTempAttDecl.name.prefix;
      String str2 = this.fTempAttDecl.name.localpart;
      str3 = this.fTempAttDecl.name.rawname;
      String str4 = getAttributeTypeName(this.fTempAttDecl);
      int n = this.fTempAttDecl.simpleType.defaultType;
      str6 = null;
      if (this.fTempAttDecl.simpleType.defaultValue != null) {
        str6 = this.fTempAttDecl.simpleType.defaultValue;
      }
      int i3 = 0;
      bool = n == 2;
      int i4 = str4 == XMLSymbols.fCDATASymbol ? 1 : 0;
      if ((i4 == 0) || (bool) || (str6 != null))
      {
        int i5 = paramXMLAttributes.getLength();
        for (int i7 = 0; i7 < i5; i7++) {
          if (paramXMLAttributes.getQName(i7) == str3)
          {
            i3 = 1;
            break;
          }
        }
      }
      if (i3 == 0)
      {
        Object[] arrayOfObject1;
        if (bool)
        {
          if (this.fPerformValidation)
          {
            arrayOfObject1 = new Object[] { paramQName.localpart, str3 };
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_REQUIRED_ATTRIBUTE_NOT_SPECIFIED", arrayOfObject1, (short)1);
          }
        }
        else if (str6 != null)
        {
          if ((this.fPerformValidation) && (this.fGrammarBucket.getStandalone()) && (this.fDTDGrammar.getAttributeDeclIsExternal(i)))
          {
            arrayOfObject1 = new Object[] { paramQName.localpart, str3 };
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DEFAULTED_ATTRIBUTE_NOT_SPECIFIED", arrayOfObject1, (short)1);
          }
          if (this.fNamespaces)
          {
            i6 = str3.indexOf(':');
            if (i6 != -1)
            {
              str1 = str3.substring(0, i6);
              str1 = this.fSymbolTable.addSymbol(str1);
              str2 = str3.substring(i6 + 1);
              str2 = this.fSymbolTable.addSymbol(str2);
            }
          }
          this.fTempQName.setValues(str1, str2, str3, this.fTempAttDecl.name.uri);
          int i6 = paramXMLAttributes.addAttribute(this.fTempQName, str4, str6);
        }
      }
    }
    int j = paramXMLAttributes.getLength();
    for (int k = 0; k < j; k++)
    {
      str3 = paramXMLAttributes.getQName(k);
      int m = 0;
      if ((this.fPerformValidation) && (this.fGrammarBucket.getStandalone()))
      {
        String str5 = paramXMLAttributes.getNonNormalizedValue(k);
        if (str5 != null)
        {
          str6 = getExternalEntityRefInAttrValue(str5);
          if (str6 != null) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_REFERENCE_TO_EXTERNALLY_DECLARED_ENTITY_WHEN_STANDALONE", new Object[] { str6 }, (short)1);
          }
        }
      }
      int i1 = -1;
      for (int i2 = this.fDTDGrammar.getFirstAttributeDeclIndex(paramInt); i2 != -1; i2 = this.fDTDGrammar.getNextAttributeDeclIndex(i2))
      {
        this.fDTDGrammar.getAttributeDecl(i2, this.fTempAttDecl);
        if (this.fTempAttDecl.name.rawname == str3)
        {
          i1 = i2;
          m = 1;
          break;
        }
      }
      Object localObject;
      if (m == 0)
      {
        if (this.fPerformValidation)
        {
          localObject = new Object[] { paramQName.rawname, str3 };
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_ATTRIBUTE_NOT_DECLARED", (Object[])localObject, (short)1);
        }
      }
      else
      {
        localObject = getAttributeTypeName(this.fTempAttDecl);
        paramXMLAttributes.setType(k, (String)localObject);
        paramXMLAttributes.getAugmentations(k).putItem("ATTRIBUTE_DECLARED", Boolean.TRUE);
        bool = false;
        String str7 = paramXMLAttributes.getValue(k);
        String str8 = str7;
        if ((paramXMLAttributes.isSpecified(k)) && (localObject != XMLSymbols.fCDATASymbol))
        {
          bool = normalizeAttrValue(paramXMLAttributes, k);
          str8 = paramXMLAttributes.getValue(k);
          if ((this.fPerformValidation) && (this.fGrammarBucket.getStandalone()) && (bool) && (this.fDTDGrammar.getAttributeDeclIsExternal(i2))) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_ATTVALUE_CHANGED_DURING_NORMALIZATION_WHEN_STANDALONE", new Object[] { str3, str7, str8 }, (short)1);
          }
        }
        if (this.fPerformValidation)
        {
          if (this.fTempAttDecl.simpleType.defaultType == 1)
          {
            String str9 = this.fTempAttDecl.simpleType.defaultValue;
            if (!str8.equals(str9))
            {
              Object[] arrayOfObject2 = { paramQName.localpart, str3, str8, str9 };
              this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_FIXED_ATTVALUE_INVALID", arrayOfObject2, (short)1);
            }
          }
          if ((this.fTempAttDecl.simpleType.type == 1) || (this.fTempAttDecl.simpleType.type == 2) || (this.fTempAttDecl.simpleType.type == 3) || (this.fTempAttDecl.simpleType.type == 4) || (this.fTempAttDecl.simpleType.type == 5) || (this.fTempAttDecl.simpleType.type == 6)) {
            validateDTDattribute(paramQName, str8, this.fTempAttDecl);
          }
        }
      }
    }
  }
  
  protected String getExternalEntityRefInAttrValue(String paramString)
  {
    int i = paramString.length();
    for (int j = paramString.indexOf('&'); j != -1; j = paramString.indexOf('&', j + 1)) {
      if ((j + 1 < i) && (paramString.charAt(j + 1) != '#'))
      {
        int k = paramString.indexOf(';', j + 1);
        String str = paramString.substring(j + 1, k);
        str = this.fSymbolTable.addSymbol(str);
        int m = this.fDTDGrammar.getEntityDeclIndex(str);
        if (m > -1)
        {
          this.fDTDGrammar.getEntityDecl(m, this.fEntityDecl);
          if ((this.fEntityDecl.inExternal) || ((str = getExternalEntityRefInAttrValue(this.fEntityDecl.value)) != null)) {
            return str;
          }
        }
      }
    }
    return null;
  }
  
  protected void validateDTDattribute(QName paramQName, String paramString, XMLAttributeDecl paramXMLAttributeDecl)
    throws XNIException
  {
    boolean bool1;
    boolean bool2;
    switch (paramXMLAttributeDecl.simpleType.type)
    {
    case 1: 
      bool1 = paramXMLAttributeDecl.simpleType.list;
      try
      {
        if (bool1) {
          this.fValENTITIES.validate(paramString, this.fValidationState);
        } else {
          this.fValENTITY.validate(paramString, this.fValidationState);
        }
      }
      catch (InvalidDatatypeValueException localInvalidDatatypeValueException2)
      {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", localInvalidDatatypeValueException2.getKey(), localInvalidDatatypeValueException2.getArgs(), (short)1);
      }
    case 2: 
    case 6: 
      bool1 = false;
      String[] arrayOfString = paramXMLAttributeDecl.simpleType.enumeration;
      if (arrayOfString == null) {
        bool1 = false;
      } else {
        for (int i = 0; i < arrayOfString.length; i++) {
          if ((paramString == arrayOfString[i]) || (paramString.equals(arrayOfString[i])))
          {
            bool1 = true;
            break;
          }
        }
      }
      if (!bool1)
      {
        StringBuffer localStringBuffer = new StringBuffer();
        if (arrayOfString != null) {
          for (int j = 0; j < arrayOfString.length; j++) {
            localStringBuffer.append(arrayOfString[j] + " ");
          }
        }
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_ATTRIBUTE_VALUE_NOT_IN_LIST", new Object[] { paramXMLAttributeDecl.name.rawname, paramString, localStringBuffer }, (short)1);
      }
      break;
    case 3: 
      try
      {
        this.fValID.validate(paramString, this.fValidationState);
      }
      catch (InvalidDatatypeValueException localInvalidDatatypeValueException1)
      {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", localInvalidDatatypeValueException1.getKey(), localInvalidDatatypeValueException1.getArgs(), (short)1);
      }
    case 4: 
      bool2 = paramXMLAttributeDecl.simpleType.list;
      try
      {
        if (bool2) {
          this.fValIDRefs.validate(paramString, this.fValidationState);
        } else {
          this.fValIDRef.validate(paramString, this.fValidationState);
        }
      }
      catch (InvalidDatatypeValueException localInvalidDatatypeValueException3)
      {
        if (bool2) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "IDREFSInvalid", new Object[] { paramString }, (short)1);
        } else {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", localInvalidDatatypeValueException3.getKey(), localInvalidDatatypeValueException3.getArgs(), (short)1);
        }
      }
    case 5: 
      bool2 = paramXMLAttributeDecl.simpleType.list;
      try
      {
        if (bool2) {
          this.fValNMTOKENS.validate(paramString, this.fValidationState);
        } else {
          this.fValNMTOKEN.validate(paramString, this.fValidationState);
        }
      }
      catch (InvalidDatatypeValueException localInvalidDatatypeValueException4)
      {
        if (bool2) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "NMTOKENSInvalid", new Object[] { paramString }, (short)1);
        } else {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "NMTOKENInvalid", new Object[] { paramString }, (short)1);
        }
      }
    }
  }
  
  protected boolean invalidStandaloneAttDef(QName paramQName1, QName paramQName2)
  {
    boolean bool = true;
    return bool;
  }
  
  private boolean normalizeAttrValue(XMLAttributes paramXMLAttributes, int paramInt)
  {
    int i = 1;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    String str1 = paramXMLAttributes.getValue(paramInt);
    char[] arrayOfChar = new char[str1.length()];
    this.fBuffer.setLength(0);
    str1.getChars(0, str1.length(), arrayOfChar, 0);
    for (int i1 = 0; i1 < arrayOfChar.length; i1++) {
      if (arrayOfChar[i1] == ' ')
      {
        if (k != 0)
        {
          j = 1;
          k = 0;
        }
        if ((j != 0) && (i == 0))
        {
          j = 0;
          this.fBuffer.append(arrayOfChar[i1]);
          m++;
        }
        else if ((i != 0) || (j == 0))
        {
          n++;
        }
      }
      else
      {
        k = 1;
        j = 0;
        i = 0;
        this.fBuffer.append(arrayOfChar[i1]);
        m++;
      }
    }
    if ((m > 0) && (this.fBuffer.charAt(m - 1) == ' ')) {
      this.fBuffer.setLength(m - 1);
    }
    String str2 = this.fBuffer.toString();
    paramXMLAttributes.setValue(paramInt, str2);
    return !str1.equals(str2);
  }
  
  private final void rootElementSpecified(QName paramQName)
    throws XNIException
  {
    if (this.fPerformValidation)
    {
      String str1 = this.fRootElement.rawname;
      String str2 = paramQName.rawname;
      if ((str1 == null) || (!str1.equals(str2))) {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "RootElementTypeMustMatchDoctypedecl", new Object[] { str1, str2 }, (short)1);
      }
    }
  }
  
  private int checkContent(int paramInt1, QName[] paramArrayOfQName, int paramInt2, int paramInt3)
    throws XNIException
  {
    this.fDTDGrammar.getElementDecl(paramInt1, this.fTempElementDecl);
    String str = this.fCurrentElement.rawname;
    int i = this.fCurrentContentSpecType;
    if (i == 1)
    {
      if (paramInt3 != 0) {
        return 0;
      }
    }
    else if (i != 0)
    {
      if ((i == 2) || (i == 3))
      {
        ContentModelValidator localContentModelValidator = null;
        localContentModelValidator = this.fTempElementDecl.contentModelValidator;
        int j = localContentModelValidator.validate(paramArrayOfQName, paramInt2, paramInt3);
        return j;
      }
      if ((i == -1) || (i != 4)) {}
    }
    return -1;
  }
  
  private int getContentSpecType(int paramInt)
  {
    int i = -1;
    if ((paramInt > -1) && (this.fDTDGrammar.getElementDecl(paramInt, this.fTempElementDecl))) {
      i = this.fTempElementDecl.type;
    }
    return i;
  }
  
  private void charDataInContent()
  {
    if (this.fElementChildren.length <= this.fElementChildrenLength)
    {
      localObject = new QName[this.fElementChildren.length * 2];
      System.arraycopy(this.fElementChildren, 0, localObject, 0, this.fElementChildren.length);
      this.fElementChildren = ((QName[])localObject);
    }
    Object localObject = this.fElementChildren[this.fElementChildrenLength];
    if (localObject == null)
    {
      for (int i = this.fElementChildrenLength; i < this.fElementChildren.length; i++) {
        this.fElementChildren[i] = new QName();
      }
      localObject = this.fElementChildren[this.fElementChildrenLength];
    }
    ((QName)localObject).clear();
    this.fElementChildrenLength += 1;
  }
  
  private String getAttributeTypeName(XMLAttributeDecl paramXMLAttributeDecl)
  {
    switch (paramXMLAttributeDecl.simpleType.type)
    {
    case 1: 
      return paramXMLAttributeDecl.simpleType.list ? XMLSymbols.fENTITIESSymbol : XMLSymbols.fENTITYSymbol;
    case 2: 
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append('(');
      for (int i = 0; i < paramXMLAttributeDecl.simpleType.enumeration.length; i++)
      {
        if (i > 0) {
          localStringBuffer.append("|");
        }
        localStringBuffer.append(paramXMLAttributeDecl.simpleType.enumeration[i]);
      }
      localStringBuffer.append(')');
      return this.fSymbolTable.addSymbol(localStringBuffer.toString());
    case 3: 
      return XMLSymbols.fIDSymbol;
    case 4: 
      return paramXMLAttributeDecl.simpleType.list ? XMLSymbols.fIDREFSSymbol : XMLSymbols.fIDREFSymbol;
    case 5: 
      return paramXMLAttributeDecl.simpleType.list ? XMLSymbols.fNMTOKENSSymbol : XMLSymbols.fNMTOKENSymbol;
    case 6: 
      return XMLSymbols.fNOTATIONSymbol;
    }
    return XMLSymbols.fCDATASymbol;
  }
  
  protected void init()
  {
    if ((this.fValidation) || (this.fDynamicValidation)) {
      try
      {
        this.fValID = this.fDatatypeValidatorFactory.getBuiltInDV(XMLSymbols.fIDSymbol);
        this.fValIDRef = this.fDatatypeValidatorFactory.getBuiltInDV(XMLSymbols.fIDREFSymbol);
        this.fValIDRefs = this.fDatatypeValidatorFactory.getBuiltInDV(XMLSymbols.fIDREFSSymbol);
        this.fValENTITY = this.fDatatypeValidatorFactory.getBuiltInDV(XMLSymbols.fENTITYSymbol);
        this.fValENTITIES = this.fDatatypeValidatorFactory.getBuiltInDV(XMLSymbols.fENTITIESSymbol);
        this.fValNMTOKEN = this.fDatatypeValidatorFactory.getBuiltInDV(XMLSymbols.fNMTOKENSymbol);
        this.fValNMTOKENS = this.fDatatypeValidatorFactory.getBuiltInDV(XMLSymbols.fNMTOKENSSymbol);
        this.fValNOTATION = this.fDatatypeValidatorFactory.getBuiltInDV(XMLSymbols.fNOTATIONSymbol);
      }
      catch (Exception localException)
      {
        localException.printStackTrace(System.err);
      }
    }
  }
  
  private void ensureStackCapacity(int paramInt)
  {
    if (paramInt == this.fElementQNamePartsStack.length)
    {
      QName[] arrayOfQName = new QName[paramInt * 2];
      System.arraycopy(this.fElementQNamePartsStack, 0, arrayOfQName, 0, paramInt);
      this.fElementQNamePartsStack = arrayOfQName;
      QName localQName = this.fElementQNamePartsStack[paramInt];
      if (localQName == null) {
        for (int i = paramInt; i < this.fElementQNamePartsStack.length; i++) {
          this.fElementQNamePartsStack[i] = new QName();
        }
      }
      int[] arrayOfInt = new int[paramInt * 2];
      System.arraycopy(this.fElementIndexStack, 0, arrayOfInt, 0, paramInt);
      this.fElementIndexStack = arrayOfInt;
      arrayOfInt = new int[paramInt * 2];
      System.arraycopy(this.fContentSpecTypeStack, 0, arrayOfInt, 0, paramInt);
      this.fContentSpecTypeStack = arrayOfInt;
    }
  }
  
  protected boolean handleStartElement(QName paramQName, XMLAttributes paramXMLAttributes, Augmentations paramAugmentations)
    throws XNIException
  {
    if (!this.fSeenRootElement)
    {
      this.fPerformValidation = validate();
      this.fSeenRootElement = true;
      this.fValidationManager.setEntityState(this.fDTDGrammar);
      this.fValidationManager.setGrammarFound(this.fSeenDoctypeDecl);
      rootElementSpecified(paramQName);
    }
    if (this.fDTDGrammar == null)
    {
      if (!this.fPerformValidation)
      {
        this.fCurrentElementIndex = -1;
        this.fCurrentContentSpecType = -1;
        this.fInElementContent = false;
      }
      if (this.fPerformValidation) {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_GRAMMAR_NOT_FOUND", new Object[] { paramQName.rawname }, (short)1);
      }
      if (this.fDocumentSource != null)
      {
        this.fDocumentSource.setDocumentHandler(this.fDocumentHandler);
        if (this.fDocumentHandler != null) {
          this.fDocumentHandler.setDocumentSource(this.fDocumentSource);
        }
        return true;
      }
    }
    else
    {
      this.fCurrentElementIndex = this.fDTDGrammar.getElementDeclIndex(paramQName);
      this.fCurrentContentSpecType = this.fDTDGrammar.getContentSpecType(this.fCurrentElementIndex);
      if ((this.fCurrentContentSpecType == -1) && (this.fPerformValidation)) {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_ELEMENT_NOT_DECLARED", new Object[] { paramQName.rawname }, (short)1);
      }
      addDTDDefaultAttrsAndValidate(paramQName, this.fCurrentElementIndex, paramXMLAttributes);
    }
    this.fInElementContent = (this.fCurrentContentSpecType == 3);
    this.fElementDepth += 1;
    if (this.fPerformValidation)
    {
      if (this.fElementChildrenOffsetStack.length <= this.fElementDepth)
      {
        localObject = new int[this.fElementChildrenOffsetStack.length * 2];
        System.arraycopy(this.fElementChildrenOffsetStack, 0, localObject, 0, this.fElementChildrenOffsetStack.length);
        this.fElementChildrenOffsetStack = ((int[])localObject);
      }
      this.fElementChildrenOffsetStack[this.fElementDepth] = this.fElementChildrenLength;
      if (this.fElementChildren.length <= this.fElementChildrenLength)
      {
        localObject = new QName[this.fElementChildrenLength * 2];
        System.arraycopy(this.fElementChildren, 0, localObject, 0, this.fElementChildren.length);
        this.fElementChildren = ((QName[])localObject);
      }
      Object localObject = this.fElementChildren[this.fElementChildrenLength];
      if (localObject == null)
      {
        for (int i = this.fElementChildrenLength; i < this.fElementChildren.length; i++) {
          this.fElementChildren[i] = new QName();
        }
        localObject = this.fElementChildren[this.fElementChildrenLength];
      }
      ((QName)localObject).setValues(paramQName);
      this.fElementChildrenLength += 1;
    }
    this.fCurrentElement.setValues(paramQName);
    ensureStackCapacity(this.fElementDepth);
    this.fElementQNamePartsStack[this.fElementDepth].setValues(this.fCurrentElement);
    this.fElementIndexStack[this.fElementDepth] = this.fCurrentElementIndex;
    this.fContentSpecTypeStack[this.fElementDepth] = this.fCurrentContentSpecType;
    startNamespaceScope(paramQName, paramXMLAttributes, paramAugmentations);
    return false;
  }
  
  protected void startNamespaceScope(QName paramQName, XMLAttributes paramXMLAttributes, Augmentations paramAugmentations) {}
  
  protected void handleEndElement(QName paramQName, Augmentations paramAugmentations, boolean paramBoolean)
    throws XNIException
  {
    this.fElementDepth -= 1;
    if (this.fPerformValidation)
    {
      int i = this.fCurrentElementIndex;
      if ((i != -1) && (this.fCurrentContentSpecType != -1))
      {
        QName[] arrayOfQName = this.fElementChildren;
        int j = this.fElementChildrenOffsetStack[(this.fElementDepth + 1)] + 1;
        int k = this.fElementChildrenLength - j;
        int m = checkContent(i, arrayOfQName, j, k);
        if (m != -1)
        {
          this.fDTDGrammar.getElementDecl(i, this.fTempElementDecl);
          if (this.fTempElementDecl.type == 1)
          {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_CONTENT_INVALID", new Object[] { paramQName.rawname, "EMPTY" }, (short)1);
          }
          else
          {
            String str2 = m != k ? "MSG_CONTENT_INVALID" : "MSG_CONTENT_INCOMPLETE";
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", str2, new Object[] { paramQName.rawname, this.fDTDGrammar.getContentSpecAsString(i) }, (short)1);
          }
        }
      }
      this.fElementChildrenLength = (this.fElementChildrenOffsetStack[(this.fElementDepth + 1)] + 1);
    }
    endNamespaceScope(this.fCurrentElement, paramAugmentations, paramBoolean);
    if (this.fElementDepth < -1) {
      throw new RuntimeException("FWK008 Element stack underflow");
    }
    if (this.fElementDepth < 0)
    {
      this.fCurrentElement.clear();
      this.fCurrentElementIndex = -1;
      this.fCurrentContentSpecType = -1;
      this.fInElementContent = false;
      if (this.fPerformValidation)
      {
        String str1 = this.fValidationState.checkIDRefID();
        if (str1 != null) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_ELEMENT_WITH_ID_REQUIRED", new Object[] { str1 }, (short)1);
        }
      }
      return;
    }
    this.fCurrentElement.setValues(this.fElementQNamePartsStack[this.fElementDepth]);
    this.fCurrentElementIndex = this.fElementIndexStack[this.fElementDepth];
    this.fCurrentContentSpecType = this.fContentSpecTypeStack[this.fElementDepth];
    this.fInElementContent = (this.fCurrentContentSpecType == 3);
  }
  
  protected void endNamespaceScope(QName paramQName, Augmentations paramAugmentations, boolean paramBoolean)
  {
    if ((this.fDocumentHandler != null) && (!paramBoolean)) {
      this.fDocumentHandler.endElement(this.fCurrentElement, paramAugmentations);
    }
  }
  
  protected boolean isSpace(int paramInt)
  {
    return XMLChar.isSpace(paramInt);
  }
  
  public boolean characterData(String paramString, Augmentations paramAugmentations)
  {
    characters(new XMLString(paramString.toCharArray(), 0, paramString.length()), paramAugmentations);
    return true;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dtd.XMLDTDValidator
 * JD-Core Version:    0.7.0.1
 */
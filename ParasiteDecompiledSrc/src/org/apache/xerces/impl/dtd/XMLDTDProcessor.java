package org.apache.xerces.impl.dtd;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLDTDContentModelHandler;
import org.apache.xerces.xni.XMLDTDHandler;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDTDContentModelFilter;
import org.apache.xerces.xni.parser.XMLDTDContentModelSource;
import org.apache.xerces.xni.parser.XMLDTDFilter;
import org.apache.xerces.xni.parser.XMLDTDSource;

public class XMLDTDProcessor
  implements XMLComponent, XMLDTDFilter, XMLDTDContentModelFilter
{
  private static final int TOP_LEVEL_SCOPE = -1;
  protected static final String VALIDATION = "http://xml.org/sax/features/validation";
  protected static final String NOTIFY_CHAR_REFS = "http://apache.org/xml/features/scanner/notify-char-refs";
  protected static final String WARN_ON_DUPLICATE_ATTDEF = "http://apache.org/xml/features/validation/warn-on-duplicate-attdef";
  protected static final String WARN_ON_UNDECLARED_ELEMDEF = "http://apache.org/xml/features/validation/warn-on-undeclared-elemdef";
  protected static final String PARSER_SETTINGS = "http://apache.org/xml/features/internal/parser-settings";
  protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
  protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
  protected static final String GRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
  protected static final String DTD_VALIDATOR = "http://apache.org/xml/properties/internal/validator/dtd";
  private static final String[] RECOGNIZED_FEATURES = { "http://xml.org/sax/features/validation", "http://apache.org/xml/features/validation/warn-on-duplicate-attdef", "http://apache.org/xml/features/validation/warn-on-undeclared-elemdef", "http://apache.org/xml/features/scanner/notify-char-refs" };
  private static final Boolean[] FEATURE_DEFAULTS = { null, Boolean.FALSE, Boolean.FALSE, null };
  private static final String[] RECOGNIZED_PROPERTIES = { "http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/grammar-pool", "http://apache.org/xml/properties/internal/validator/dtd" };
  private static final Object[] PROPERTY_DEFAULTS = { null, null, null, null };
  protected boolean fValidation;
  protected boolean fDTDValidation;
  protected boolean fWarnDuplicateAttdef;
  protected boolean fWarnOnUndeclaredElemdef;
  protected SymbolTable fSymbolTable;
  protected XMLErrorReporter fErrorReporter;
  protected DTDGrammarBucket fGrammarBucket;
  protected XMLDTDValidator fValidator;
  protected XMLGrammarPool fGrammarPool;
  protected Locale fLocale;
  protected XMLDTDHandler fDTDHandler;
  protected XMLDTDSource fDTDSource;
  protected XMLDTDContentModelHandler fDTDContentModelHandler;
  protected XMLDTDContentModelSource fDTDContentModelSource;
  protected DTDGrammar fDTDGrammar;
  private boolean fPerformValidation;
  protected boolean fInDTDIgnore;
  private boolean fMixed;
  private final XMLEntityDecl fEntityDecl = new XMLEntityDecl();
  private final Hashtable fNDataDeclNotations = new Hashtable();
  private String fDTDElementDeclName = null;
  private final Vector fMixedElementTypes = new Vector();
  private final Vector fDTDElementDecls = new Vector();
  private Hashtable fTableOfIDAttributeNames;
  private Hashtable fTableOfNOTATIONAttributeNames;
  private Hashtable fNotationEnumVals;
  
  public void reset(XMLComponentManager paramXMLComponentManager)
    throws XMLConfigurationException
  {
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
      reset();
      return;
    }
    try
    {
      this.fValidation = paramXMLComponentManager.getFeature("http://xml.org/sax/features/validation");
    }
    catch (XMLConfigurationException localXMLConfigurationException2)
    {
      this.fValidation = false;
    }
    try
    {
      this.fDTDValidation = (!paramXMLComponentManager.getFeature("http://apache.org/xml/features/validation/schema"));
    }
    catch (XMLConfigurationException localXMLConfigurationException3)
    {
      this.fDTDValidation = true;
    }
    try
    {
      this.fWarnDuplicateAttdef = paramXMLComponentManager.getFeature("http://apache.org/xml/features/validation/warn-on-duplicate-attdef");
    }
    catch (XMLConfigurationException localXMLConfigurationException4)
    {
      this.fWarnDuplicateAttdef = false;
    }
    try
    {
      this.fWarnOnUndeclaredElemdef = paramXMLComponentManager.getFeature("http://apache.org/xml/features/validation/warn-on-undeclared-elemdef");
    }
    catch (XMLConfigurationException localXMLConfigurationException5)
    {
      this.fWarnOnUndeclaredElemdef = false;
    }
    this.fErrorReporter = ((XMLErrorReporter)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter"));
    this.fSymbolTable = ((SymbolTable)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table"));
    try
    {
      this.fGrammarPool = ((XMLGrammarPool)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/grammar-pool"));
    }
    catch (XMLConfigurationException localXMLConfigurationException6)
    {
      this.fGrammarPool = null;
    }
    try
    {
      this.fValidator = ((XMLDTDValidator)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/validator/dtd"));
    }
    catch (XMLConfigurationException localXMLConfigurationException7)
    {
      this.fValidator = null;
    }
    catch (ClassCastException localClassCastException)
    {
      this.fValidator = null;
    }
    if (this.fValidator != null) {
      this.fGrammarBucket = this.fValidator.getGrammarBucket();
    } else {
      this.fGrammarBucket = null;
    }
    reset();
  }
  
  protected void reset()
  {
    this.fDTDGrammar = null;
    this.fInDTDIgnore = false;
    this.fNDataDeclNotations.clear();
    if (this.fValidation)
    {
      if (this.fNotationEnumVals == null) {
        this.fNotationEnumVals = new Hashtable();
      }
      this.fNotationEnumVals.clear();
      this.fTableOfIDAttributeNames = new Hashtable();
      this.fTableOfNOTATIONAttributeNames = new Hashtable();
    }
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
  
  public void setDTDHandler(XMLDTDHandler paramXMLDTDHandler)
  {
    this.fDTDHandler = paramXMLDTDHandler;
  }
  
  public XMLDTDHandler getDTDHandler()
  {
    return this.fDTDHandler;
  }
  
  public void setDTDContentModelHandler(XMLDTDContentModelHandler paramXMLDTDContentModelHandler)
  {
    this.fDTDContentModelHandler = paramXMLDTDContentModelHandler;
  }
  
  public XMLDTDContentModelHandler getDTDContentModelHandler()
  {
    return this.fDTDContentModelHandler;
  }
  
  public void startExternalSubset(XMLResourceIdentifier paramXMLResourceIdentifier, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.startExternalSubset(paramXMLResourceIdentifier, paramAugmentations);
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.startExternalSubset(paramXMLResourceIdentifier, paramAugmentations);
    }
  }
  
  public void endExternalSubset(Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.endExternalSubset(paramAugmentations);
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.endExternalSubset(paramAugmentations);
    }
  }
  
  protected static void checkStandaloneEntityRef(String paramString, DTDGrammar paramDTDGrammar, XMLEntityDecl paramXMLEntityDecl, XMLErrorReporter paramXMLErrorReporter)
    throws XNIException
  {
    int i = paramDTDGrammar.getEntityDeclIndex(paramString);
    if (i > -1)
    {
      paramDTDGrammar.getEntityDecl(i, paramXMLEntityDecl);
      if (paramXMLEntityDecl.inExternal) {
        paramXMLErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_REFERENCE_TO_EXTERNALLY_DECLARED_ENTITY_WHEN_STANDALONE", new Object[] { paramString }, (short)1);
      }
    }
  }
  
  public void comment(XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.comment(paramXMLString, paramAugmentations);
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.comment(paramXMLString, paramAugmentations);
    }
  }
  
  public void processingInstruction(String paramString, XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.processingInstruction(paramString, paramXMLString, paramAugmentations);
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.processingInstruction(paramString, paramXMLString, paramAugmentations);
    }
  }
  
  public void startDTD(XMLLocator paramXMLLocator, Augmentations paramAugmentations)
    throws XNIException
  {
    this.fNDataDeclNotations.clear();
    this.fDTDElementDecls.removeAllElements();
    if (!this.fGrammarBucket.getActiveGrammar().isImmutable()) {
      this.fDTDGrammar = this.fGrammarBucket.getActiveGrammar();
    }
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.startDTD(paramXMLLocator, paramAugmentations);
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.startDTD(paramXMLLocator, paramAugmentations);
    }
  }
  
  public void ignoredCharacters(XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.ignoredCharacters(paramXMLString, paramAugmentations);
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.ignoredCharacters(paramXMLString, paramAugmentations);
    }
  }
  
  public void textDecl(String paramString1, String paramString2, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.textDecl(paramString1, paramString2, paramAugmentations);
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.textDecl(paramString1, paramString2, paramAugmentations);
    }
  }
  
  public void startParameterEntity(String paramString1, XMLResourceIdentifier paramXMLResourceIdentifier, String paramString2, Augmentations paramAugmentations)
    throws XNIException
  {
    if ((this.fPerformValidation) && (this.fDTDGrammar != null) && (this.fGrammarBucket.getStandalone())) {
      checkStandaloneEntityRef(paramString1, this.fDTDGrammar, this.fEntityDecl, this.fErrorReporter);
    }
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.startParameterEntity(paramString1, paramXMLResourceIdentifier, paramString2, paramAugmentations);
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.startParameterEntity(paramString1, paramXMLResourceIdentifier, paramString2, paramAugmentations);
    }
  }
  
  public void endParameterEntity(String paramString, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.endParameterEntity(paramString, paramAugmentations);
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.endParameterEntity(paramString, paramAugmentations);
    }
  }
  
  public void elementDecl(String paramString1, String paramString2, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fValidation) {
      if (this.fDTDElementDecls.contains(paramString1)) {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_ELEMENT_ALREADY_DECLARED", new Object[] { paramString1 }, (short)1);
      } else {
        this.fDTDElementDecls.addElement(paramString1);
      }
    }
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.elementDecl(paramString1, paramString2, paramAugmentations);
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.elementDecl(paramString1, paramString2, paramAugmentations);
    }
  }
  
  public void startAttlist(String paramString, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.startAttlist(paramString, paramAugmentations);
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.startAttlist(paramString, paramAugmentations);
    }
  }
  
  public void attributeDecl(String paramString1, String paramString2, String paramString3, String[] paramArrayOfString, String paramString4, XMLString paramXMLString1, XMLString paramXMLString2, Augmentations paramAugmentations)
    throws XNIException
  {
    if ((paramString3 != XMLSymbols.fCDATASymbol) && (paramXMLString1 != null)) {
      normalizeDefaultAttrValue(paramXMLString1);
    }
    if (this.fValidation)
    {
      int i = 0;
      DTDGrammar localDTDGrammar = this.fDTDGrammar != null ? this.fDTDGrammar : this.fGrammarBucket.getActiveGrammar();
      int j = localDTDGrammar.getElementDeclIndex(paramString1);
      if (localDTDGrammar.getAttributeDeclIndex(j, paramString2) != -1)
      {
        i = 1;
        if (this.fWarnDuplicateAttdef) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DUPLICATE_ATTRIBUTE_DEFINITION", new Object[] { paramString1, paramString2 }, (short)0);
        }
      }
      if (paramString3 == XMLSymbols.fIDSymbol)
      {
        if ((paramXMLString1 != null) && (paramXMLString1.length != 0) && ((paramString4 == null) || ((paramString4 != XMLSymbols.fIMPLIEDSymbol) && (paramString4 != XMLSymbols.fREQUIREDSymbol)))) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "IDDefaultTypeInvalid", new Object[] { paramString2 }, (short)1);
        }
        if (!this.fTableOfIDAttributeNames.containsKey(paramString1))
        {
          this.fTableOfIDAttributeNames.put(paramString1, paramString2);
        }
        else if (i == 0)
        {
          String str1 = (String)this.fTableOfIDAttributeNames.get(paramString1);
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_MORE_THAN_ONE_ID_ATTRIBUTE", new Object[] { paramString1, str1, paramString2 }, (short)1);
        }
      }
      if (paramString3 == XMLSymbols.fNOTATIONSymbol)
      {
        for (k = 0; k < paramArrayOfString.length; k++) {
          this.fNotationEnumVals.put(paramArrayOfString[k], paramString2);
        }
        if (!this.fTableOfNOTATIONAttributeNames.containsKey(paramString1))
        {
          this.fTableOfNOTATIONAttributeNames.put(paramString1, paramString2);
        }
        else if (i == 0)
        {
          String str2 = (String)this.fTableOfNOTATIONAttributeNames.get(paramString1);
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_MORE_THAN_ONE_NOTATION_ATTRIBUTE", new Object[] { paramString1, str2, paramString2 }, (short)1);
        }
      }
      if ((paramString3 == XMLSymbols.fENUMERATIONSymbol) || (paramString3 == XMLSymbols.fNOTATIONSymbol)) {
        for (k = 0; k < paramArrayOfString.length; k++) {
          for (int m = k + 1; m < paramArrayOfString.length; m++) {
            if (paramArrayOfString[k].equals(paramArrayOfString[m]))
            {
              this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", paramString3 == XMLSymbols.fENUMERATIONSymbol ? "MSG_DISTINCT_TOKENS_IN_ENUMERATION" : "MSG_DISTINCT_NOTATION_IN_ENUMERATION", new Object[] { paramString1, paramArrayOfString[k], paramString2 }, (short)1);
              break;
            }
          }
        }
      }
      int k = 1;
      if ((paramXMLString1 != null) && ((paramString4 == null) || ((paramString4 != null) && (paramString4 == XMLSymbols.fFIXEDSymbol))))
      {
        String str3 = paramXMLString1.toString();
        if ((paramString3 == XMLSymbols.fNMTOKENSSymbol) || (paramString3 == XMLSymbols.fENTITIESSymbol) || (paramString3 == XMLSymbols.fIDREFSSymbol))
        {
          StringTokenizer localStringTokenizer = new StringTokenizer(str3, " ");
          if (localStringTokenizer.hasMoreTokens()) {
            do
            {
              String str4 = localStringTokenizer.nextToken();
              if (paramString3 == XMLSymbols.fNMTOKENSSymbol)
              {
                if (!isValidNmtoken(str4))
                {
                  k = 0;
                  break;
                }
              }
              else if (((paramString3 == XMLSymbols.fENTITIESSymbol) || (paramString3 == XMLSymbols.fIDREFSSymbol)) && (!isValidName(str4)))
              {
                k = 0;
                break;
              }
            } while (localStringTokenizer.hasMoreTokens());
          }
        }
        else
        {
          if ((paramString3 == XMLSymbols.fENTITYSymbol) || (paramString3 == XMLSymbols.fIDSymbol) || (paramString3 == XMLSymbols.fIDREFSymbol) || (paramString3 == XMLSymbols.fNOTATIONSymbol))
          {
            if (!isValidName(str3)) {
              k = 0;
            }
          }
          else if (((paramString3 == XMLSymbols.fNMTOKENSymbol) || (paramString3 == XMLSymbols.fENUMERATIONSymbol)) && (!isValidNmtoken(str3))) {
            k = 0;
          }
          if ((paramString3 == XMLSymbols.fNOTATIONSymbol) || (paramString3 == XMLSymbols.fENUMERATIONSymbol))
          {
            k = 0;
            for (int n = 0; n < paramArrayOfString.length; n++) {
              if (paramXMLString1.equals(paramArrayOfString[n])) {
                k = 1;
              }
            }
          }
        }
        if (k == 0) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_ATT_DEFAULT_INVALID", new Object[] { paramString2, str3 }, (short)1);
        }
      }
    }
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.attributeDecl(paramString1, paramString2, paramString3, paramArrayOfString, paramString4, paramXMLString1, paramXMLString2, paramAugmentations);
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.attributeDecl(paramString1, paramString2, paramString3, paramArrayOfString, paramString4, paramXMLString1, paramXMLString2, paramAugmentations);
    }
  }
  
  public void endAttlist(Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.endAttlist(paramAugmentations);
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.endAttlist(paramAugmentations);
    }
  }
  
  public void internalEntityDecl(String paramString, XMLString paramXMLString1, XMLString paramXMLString2, Augmentations paramAugmentations)
    throws XNIException
  {
    DTDGrammar localDTDGrammar = this.fDTDGrammar != null ? this.fDTDGrammar : this.fGrammarBucket.getActiveGrammar();
    int i = localDTDGrammar.getEntityDeclIndex(paramString);
    if (i == -1)
    {
      if (this.fDTDGrammar != null) {
        this.fDTDGrammar.internalEntityDecl(paramString, paramXMLString1, paramXMLString2, paramAugmentations);
      }
      if (this.fDTDHandler != null) {
        this.fDTDHandler.internalEntityDecl(paramString, paramXMLString1, paramXMLString2, paramAugmentations);
      }
    }
  }
  
  public void externalEntityDecl(String paramString, XMLResourceIdentifier paramXMLResourceIdentifier, Augmentations paramAugmentations)
    throws XNIException
  {
    DTDGrammar localDTDGrammar = this.fDTDGrammar != null ? this.fDTDGrammar : this.fGrammarBucket.getActiveGrammar();
    int i = localDTDGrammar.getEntityDeclIndex(paramString);
    if (i == -1)
    {
      if (this.fDTDGrammar != null) {
        this.fDTDGrammar.externalEntityDecl(paramString, paramXMLResourceIdentifier, paramAugmentations);
      }
      if (this.fDTDHandler != null) {
        this.fDTDHandler.externalEntityDecl(paramString, paramXMLResourceIdentifier, paramAugmentations);
      }
    }
  }
  
  public void unparsedEntityDecl(String paramString1, XMLResourceIdentifier paramXMLResourceIdentifier, String paramString2, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fValidation) {
      this.fNDataDeclNotations.put(paramString1, paramString2);
    }
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.unparsedEntityDecl(paramString1, paramXMLResourceIdentifier, paramString2, paramAugmentations);
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.unparsedEntityDecl(paramString1, paramXMLResourceIdentifier, paramString2, paramAugmentations);
    }
  }
  
  public void notationDecl(String paramString, XMLResourceIdentifier paramXMLResourceIdentifier, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fValidation)
    {
      DTDGrammar localDTDGrammar = this.fDTDGrammar != null ? this.fDTDGrammar : this.fGrammarBucket.getActiveGrammar();
      if (localDTDGrammar.getNotationDeclIndex(paramString) != -1) {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "UniqueNotationName", new Object[] { paramString }, (short)1);
      }
    }
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.notationDecl(paramString, paramXMLResourceIdentifier, paramAugmentations);
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.notationDecl(paramString, paramXMLResourceIdentifier, paramAugmentations);
    }
  }
  
  public void startConditional(short paramShort, Augmentations paramAugmentations)
    throws XNIException
  {
    this.fInDTDIgnore = (paramShort == 1);
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.startConditional(paramShort, paramAugmentations);
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.startConditional(paramShort, paramAugmentations);
    }
  }
  
  public void endConditional(Augmentations paramAugmentations)
    throws XNIException
  {
    this.fInDTDIgnore = false;
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.endConditional(paramAugmentations);
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.endConditional(paramAugmentations);
    }
  }
  
  public void endDTD(Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDTDGrammar != null)
    {
      this.fDTDGrammar.endDTD(paramAugmentations);
      if (this.fGrammarPool != null) {
        this.fGrammarPool.cacheGrammars("http://www.w3.org/TR/REC-xml", new Grammar[] { this.fDTDGrammar });
      }
    }
    if (this.fValidation)
    {
      DTDGrammar localDTDGrammar = this.fDTDGrammar != null ? this.fDTDGrammar : this.fGrammarBucket.getActiveGrammar();
      Enumeration localEnumeration = this.fNDataDeclNotations.keys();
      while (localEnumeration.hasMoreElements())
      {
        localObject1 = (String)localEnumeration.nextElement();
        localObject2 = (String)this.fNDataDeclNotations.get(localObject1);
        if (localDTDGrammar.getNotationDeclIndex((String)localObject2) == -1) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_NOTATION_NOT_DECLARED_FOR_UNPARSED_ENTITYDECL", new Object[] { localObject1, localObject2 }, (short)1);
        }
      }
      Object localObject1 = this.fNotationEnumVals.keys();
      String str1;
      while (((Enumeration)localObject1).hasMoreElements())
      {
        localObject2 = (String)((Enumeration)localObject1).nextElement();
        str1 = (String)this.fNotationEnumVals.get(localObject2);
        if (localDTDGrammar.getNotationDeclIndex((String)localObject2) == -1) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_NOTATION_NOT_DECLARED_FOR_NOTATIONTYPE_ATTRIBUTE", new Object[] { str1, localObject2 }, (short)1);
        }
      }
      Object localObject2 = this.fTableOfNOTATIONAttributeNames.keys();
      while (((Enumeration)localObject2).hasMoreElements())
      {
        str1 = (String)((Enumeration)localObject2).nextElement();
        int i = localDTDGrammar.getElementDeclIndex(str1);
        if (localDTDGrammar.getContentSpecType(i) == 1)
        {
          String str2 = (String)this.fTableOfNOTATIONAttributeNames.get(str1);
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "NoNotationOnEmptyElement", new Object[] { str1, str2 }, (short)1);
        }
      }
      this.fTableOfIDAttributeNames = null;
      this.fTableOfNOTATIONAttributeNames = null;
      if (this.fWarnOnUndeclaredElemdef) {
        checkDeclaredElements(localDTDGrammar);
      }
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.endDTD(paramAugmentations);
    }
  }
  
  public void setDTDSource(XMLDTDSource paramXMLDTDSource)
  {
    this.fDTDSource = paramXMLDTDSource;
  }
  
  public XMLDTDSource getDTDSource()
  {
    return this.fDTDSource;
  }
  
  public void setDTDContentModelSource(XMLDTDContentModelSource paramXMLDTDContentModelSource)
  {
    this.fDTDContentModelSource = paramXMLDTDContentModelSource;
  }
  
  public XMLDTDContentModelSource getDTDContentModelSource()
  {
    return this.fDTDContentModelSource;
  }
  
  public void startContentModel(String paramString, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fValidation)
    {
      this.fDTDElementDeclName = paramString;
      this.fMixedElementTypes.removeAllElements();
    }
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.startContentModel(paramString, paramAugmentations);
    }
    if (this.fDTDContentModelHandler != null) {
      this.fDTDContentModelHandler.startContentModel(paramString, paramAugmentations);
    }
  }
  
  public void any(Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.any(paramAugmentations);
    }
    if (this.fDTDContentModelHandler != null) {
      this.fDTDContentModelHandler.any(paramAugmentations);
    }
  }
  
  public void empty(Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.empty(paramAugmentations);
    }
    if (this.fDTDContentModelHandler != null) {
      this.fDTDContentModelHandler.empty(paramAugmentations);
    }
  }
  
  public void startGroup(Augmentations paramAugmentations)
    throws XNIException
  {
    this.fMixed = false;
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.startGroup(paramAugmentations);
    }
    if (this.fDTDContentModelHandler != null) {
      this.fDTDContentModelHandler.startGroup(paramAugmentations);
    }
  }
  
  public void pcdata(Augmentations paramAugmentations)
  {
    this.fMixed = true;
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.pcdata(paramAugmentations);
    }
    if (this.fDTDContentModelHandler != null) {
      this.fDTDContentModelHandler.pcdata(paramAugmentations);
    }
  }
  
  public void element(String paramString, Augmentations paramAugmentations)
    throws XNIException
  {
    if ((this.fMixed) && (this.fValidation)) {
      if (this.fMixedElementTypes.contains(paramString)) {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "DuplicateTypeInMixedContent", new Object[] { this.fDTDElementDeclName, paramString }, (short)1);
      } else {
        this.fMixedElementTypes.addElement(paramString);
      }
    }
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.element(paramString, paramAugmentations);
    }
    if (this.fDTDContentModelHandler != null) {
      this.fDTDContentModelHandler.element(paramString, paramAugmentations);
    }
  }
  
  public void separator(short paramShort, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.separator(paramShort, paramAugmentations);
    }
    if (this.fDTDContentModelHandler != null) {
      this.fDTDContentModelHandler.separator(paramShort, paramAugmentations);
    }
  }
  
  public void occurrence(short paramShort, Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.occurrence(paramShort, paramAugmentations);
    }
    if (this.fDTDContentModelHandler != null) {
      this.fDTDContentModelHandler.occurrence(paramShort, paramAugmentations);
    }
  }
  
  public void endGroup(Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.endGroup(paramAugmentations);
    }
    if (this.fDTDContentModelHandler != null) {
      this.fDTDContentModelHandler.endGroup(paramAugmentations);
    }
  }
  
  public void endContentModel(Augmentations paramAugmentations)
    throws XNIException
  {
    if (this.fDTDGrammar != null) {
      this.fDTDGrammar.endContentModel(paramAugmentations);
    }
    if (this.fDTDContentModelHandler != null) {
      this.fDTDContentModelHandler.endContentModel(paramAugmentations);
    }
  }
  
  private boolean normalizeDefaultAttrValue(XMLString paramXMLString)
  {
    int i = 1;
    int j = paramXMLString.offset;
    int k = paramXMLString.offset + paramXMLString.length;
    for (int m = paramXMLString.offset; m < k; m++) {
      if (paramXMLString.ch[m] == ' ')
      {
        if (i == 0)
        {
          paramXMLString.ch[(j++)] = ' ';
          i = 1;
        }
      }
      else
      {
        if (j != m) {
          paramXMLString.ch[j] = paramXMLString.ch[m];
        }
        j++;
        i = 0;
      }
    }
    if (j != k)
    {
      if (i != 0) {
        j--;
      }
      paramXMLString.length = (j - paramXMLString.offset);
      return true;
    }
    return false;
  }
  
  protected boolean isValidNmtoken(String paramString)
  {
    return XMLChar.isValidNmtoken(paramString);
  }
  
  protected boolean isValidName(String paramString)
  {
    return XMLChar.isValidName(paramString);
  }
  
  private void checkDeclaredElements(DTDGrammar paramDTDGrammar)
  {
    int i = paramDTDGrammar.getFirstElementDeclIndex();
    XMLContentSpec localXMLContentSpec = new XMLContentSpec();
    while (i >= 0)
    {
      int j = paramDTDGrammar.getContentSpecType(i);
      if ((j == 3) || (j == 2)) {
        checkDeclaredElements(paramDTDGrammar, i, paramDTDGrammar.getContentSpecIndex(i), localXMLContentSpec);
      }
      i = paramDTDGrammar.getNextElementDeclIndex(i);
    }
  }
  
  private void checkDeclaredElements(DTDGrammar paramDTDGrammar, int paramInt1, int paramInt2, XMLContentSpec paramXMLContentSpec)
  {
    paramDTDGrammar.getContentSpec(paramInt2, paramXMLContentSpec);
    if (paramXMLContentSpec.type == 0)
    {
      String str = (String)paramXMLContentSpec.value;
      if ((str != null) && (paramDTDGrammar.getElementDeclIndex(str) == -1)) {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "UndeclaredElementInContentSpec", new Object[] { paramDTDGrammar.getElementDeclName(paramInt1).rawname, str }, (short)0);
      }
    }
    else
    {
      int i;
      if ((paramXMLContentSpec.type == 4) || (paramXMLContentSpec.type == 5))
      {
        i = ((int[])paramXMLContentSpec.value)[0];
        int j = ((int[])paramXMLContentSpec.otherValue)[0];
        checkDeclaredElements(paramDTDGrammar, paramInt1, i, paramXMLContentSpec);
        checkDeclaredElements(paramDTDGrammar, paramInt1, j, paramXMLContentSpec);
      }
      else if ((paramXMLContentSpec.type == 2) || (paramXMLContentSpec.type == 1) || (paramXMLContentSpec.type == 3))
      {
        i = ((int[])paramXMLContentSpec.value)[0];
        checkDeclaredElements(paramDTDGrammar, paramInt1, i, paramXMLContentSpec);
      }
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dtd.XMLDTDProcessor
 * JD-Core Version:    0.7.0.1
 */
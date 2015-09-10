package org.apache.xerces.impl;

import java.io.CharConversionException;
import java.io.EOFException;
import java.io.IOException;
import org.apache.xerces.impl.dtd.XMLDTDDescription;
import org.apache.xerces.impl.io.MalformedByteSequenceException;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.util.NamespaceSupport;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLResourceIdentifierImpl;
import org.apache.xerces.util.XMLStringBuffer;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDTDScanner;
import org.apache.xerces.xni.parser.XMLInputSource;

public class XMLDocumentScannerImpl
  extends XMLDocumentFragmentScannerImpl
{
  protected static final int SCANNER_STATE_XML_DECL = 0;
  protected static final int SCANNER_STATE_PROLOG = 5;
  protected static final int SCANNER_STATE_TRAILING_MISC = 12;
  protected static final int SCANNER_STATE_DTD_INTERNAL_DECLS = 17;
  protected static final int SCANNER_STATE_DTD_EXTERNAL = 18;
  protected static final int SCANNER_STATE_DTD_EXTERNAL_DECLS = 19;
  protected static final String LOAD_EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
  protected static final String DISALLOW_DOCTYPE_DECL_FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
  protected static final String DTD_SCANNER = "http://apache.org/xml/properties/internal/dtd-scanner";
  protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
  protected static final String NAMESPACE_CONTEXT = "http://apache.org/xml/properties/internal/namespace-context";
  private static final String[] RECOGNIZED_FEATURES = { "http://apache.org/xml/features/nonvalidating/load-external-dtd", "http://apache.org/xml/features/disallow-doctype-decl" };
  private static final Boolean[] FEATURE_DEFAULTS = { Boolean.TRUE, Boolean.FALSE };
  private static final String[] RECOGNIZED_PROPERTIES = { "http://apache.org/xml/properties/internal/dtd-scanner", "http://apache.org/xml/properties/internal/validation-manager", "http://apache.org/xml/properties/internal/namespace-context" };
  private static final Object[] PROPERTY_DEFAULTS = { null, null, null };
  protected XMLDTDScanner fDTDScanner;
  protected ValidationManager fValidationManager;
  protected boolean fScanningDTD;
  protected String fDoctypeName;
  protected String fDoctypePublicId;
  protected String fDoctypeSystemId;
  protected NamespaceContext fNamespaceContext = new NamespaceSupport();
  protected boolean fLoadExternalDTD = true;
  protected boolean fDisallowDoctype = false;
  protected boolean fSeenDoctypeDecl;
  protected final XMLDocumentFragmentScannerImpl.Dispatcher fXMLDeclDispatcher = new XMLDeclDispatcher();
  protected final XMLDocumentFragmentScannerImpl.Dispatcher fPrologDispatcher = new PrologDispatcher();
  protected final XMLDocumentFragmentScannerImpl.Dispatcher fDTDDispatcher = new DTDDispatcher();
  protected final XMLDocumentFragmentScannerImpl.Dispatcher fTrailingMiscDispatcher = new TrailingMiscDispatcher();
  private final String[] fStrings = new String[3];
  private final XMLString fString = new XMLString();
  private final XMLStringBuffer fStringBuffer = new XMLStringBuffer();
  private XMLInputSource fExternalSubsetSource = null;
  private final XMLDTDDescription fDTDDescription = new XMLDTDDescription(null, null, null, null, null);
  
  public void setInputSource(XMLInputSource paramXMLInputSource)
    throws IOException
  {
    this.fEntityManager.setEntityHandler(this);
    this.fEntityManager.startDocumentEntity(paramXMLInputSource);
  }
  
  public void reset(XMLComponentManager paramXMLComponentManager)
    throws XMLConfigurationException
  {
    super.reset(paramXMLComponentManager);
    this.fDoctypeName = null;
    this.fDoctypePublicId = null;
    this.fDoctypeSystemId = null;
    this.fSeenDoctypeDecl = false;
    this.fScanningDTD = false;
    this.fExternalSubsetSource = null;
    if (!this.fParserSettings)
    {
      this.fNamespaceContext.reset();
      setScannerState(0);
      setDispatcher(this.fXMLDeclDispatcher);
      return;
    }
    try
    {
      this.fLoadExternalDTD = paramXMLComponentManager.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd");
    }
    catch (XMLConfigurationException localXMLConfigurationException1)
    {
      this.fLoadExternalDTD = true;
    }
    try
    {
      this.fDisallowDoctype = paramXMLComponentManager.getFeature("http://apache.org/xml/features/disallow-doctype-decl");
    }
    catch (XMLConfigurationException localXMLConfigurationException2)
    {
      this.fDisallowDoctype = false;
    }
    this.fDTDScanner = ((XMLDTDScanner)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/dtd-scanner"));
    try
    {
      this.fValidationManager = ((ValidationManager)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/validation-manager"));
    }
    catch (XMLConfigurationException localXMLConfigurationException3)
    {
      this.fValidationManager = null;
    }
    try
    {
      this.fNamespaceContext = ((NamespaceContext)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/namespace-context"));
    }
    catch (XMLConfigurationException localXMLConfigurationException4) {}
    if (this.fNamespaceContext == null) {
      this.fNamespaceContext = new NamespaceSupport();
    }
    this.fNamespaceContext.reset();
    setScannerState(0);
    setDispatcher(this.fXMLDeclDispatcher);
  }
  
  public String[] getRecognizedFeatures()
  {
    String[] arrayOfString1 = super.getRecognizedFeatures();
    int i = arrayOfString1 != null ? arrayOfString1.length : 0;
    String[] arrayOfString2 = new String[i + RECOGNIZED_FEATURES.length];
    if (arrayOfString1 != null) {
      System.arraycopy(arrayOfString1, 0, arrayOfString2, 0, arrayOfString1.length);
    }
    System.arraycopy(RECOGNIZED_FEATURES, 0, arrayOfString2, i, RECOGNIZED_FEATURES.length);
    return arrayOfString2;
  }
  
  public void setFeature(String paramString, boolean paramBoolean)
    throws XMLConfigurationException
  {
    super.setFeature(paramString, paramBoolean);
    if (paramString.startsWith("http://apache.org/xml/features/"))
    {
      int i = paramString.length() - "http://apache.org/xml/features/".length();
      if ((i == "nonvalidating/load-external-dtd".length()) && (paramString.endsWith("nonvalidating/load-external-dtd")))
      {
        this.fLoadExternalDTD = paramBoolean;
        return;
      }
      if ((i == "disallow-doctype-decl".length()) && (paramString.endsWith("disallow-doctype-decl")))
      {
        this.fDisallowDoctype = paramBoolean;
        return;
      }
    }
  }
  
  public String[] getRecognizedProperties()
  {
    String[] arrayOfString1 = super.getRecognizedProperties();
    int i = arrayOfString1 != null ? arrayOfString1.length : 0;
    String[] arrayOfString2 = new String[i + RECOGNIZED_PROPERTIES.length];
    if (arrayOfString1 != null) {
      System.arraycopy(arrayOfString1, 0, arrayOfString2, 0, arrayOfString1.length);
    }
    System.arraycopy(RECOGNIZED_PROPERTIES, 0, arrayOfString2, i, RECOGNIZED_PROPERTIES.length);
    return arrayOfString2;
  }
  
  public void setProperty(String paramString, Object paramObject)
    throws XMLConfigurationException
  {
    super.setProperty(paramString, paramObject);
    if (paramString.startsWith("http://apache.org/xml/properties/"))
    {
      int i = paramString.length() - "http://apache.org/xml/properties/".length();
      if ((i == "internal/dtd-scanner".length()) && (paramString.endsWith("internal/dtd-scanner"))) {
        this.fDTDScanner = ((XMLDTDScanner)paramObject);
      }
      if ((i == "internal/namespace-context".length()) && (paramString.endsWith("internal/namespace-context")) && (paramObject != null)) {
        this.fNamespaceContext = ((NamespaceContext)paramObject);
      }
      return;
    }
  }
  
  public Boolean getFeatureDefault(String paramString)
  {
    for (int i = 0; i < RECOGNIZED_FEATURES.length; i++) {
      if (RECOGNIZED_FEATURES[i].equals(paramString)) {
        return FEATURE_DEFAULTS[i];
      }
    }
    return super.getFeatureDefault(paramString);
  }
  
  public Object getPropertyDefault(String paramString)
  {
    for (int i = 0; i < RECOGNIZED_PROPERTIES.length; i++) {
      if (RECOGNIZED_PROPERTIES[i].equals(paramString)) {
        return PROPERTY_DEFAULTS[i];
      }
    }
    return super.getPropertyDefault(paramString);
  }
  
  public void startEntity(String paramString1, XMLResourceIdentifier paramXMLResourceIdentifier, String paramString2, Augmentations paramAugmentations)
    throws XNIException
  {
    super.startEntity(paramString1, paramXMLResourceIdentifier, paramString2, paramAugmentations);
    if ((!paramString1.equals("[xml]")) && (this.fEntityScanner.isExternal())) {
      setScannerState(16);
    }
    if ((this.fDocumentHandler != null) && (paramString1.equals("[xml]"))) {
      this.fDocumentHandler.startDocument(this.fEntityScanner, paramString2, this.fNamespaceContext, null);
    }
  }
  
  public void endEntity(String paramString, Augmentations paramAugmentations)
    throws XNIException
  {
    super.endEntity(paramString, paramAugmentations);
    if ((this.fDocumentHandler != null) && (paramString.equals("[xml]"))) {
      this.fDocumentHandler.endDocument(null);
    }
  }
  
  protected XMLDocumentFragmentScannerImpl.Dispatcher createContentDispatcher()
  {
    return new ContentDispatcher();
  }
  
  protected boolean scanDoctypeDecl()
    throws IOException, XNIException
  {
    if (!this.fEntityScanner.skipSpaces()) {
      reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ROOT_ELEMENT_TYPE_IN_DOCTYPEDECL", null);
    }
    this.fDoctypeName = this.fEntityScanner.scanName();
    if (this.fDoctypeName == null) {
      reportFatalError("MSG_ROOT_ELEMENT_TYPE_REQUIRED", null);
    }
    if (this.fEntityScanner.skipSpaces())
    {
      scanExternalID(this.fStrings, false);
      this.fDoctypeSystemId = this.fStrings[0];
      this.fDoctypePublicId = this.fStrings[1];
      this.fEntityScanner.skipSpaces();
    }
    this.fHasExternalDTD = (this.fDoctypeSystemId != null);
    if ((!this.fHasExternalDTD) && (this.fExternalSubsetResolver != null))
    {
      this.fDTDDescription.setValues(null, null, this.fEntityManager.getCurrentResourceIdentifier().getExpandedSystemId(), null);
      this.fDTDDescription.setRootName(this.fDoctypeName);
      this.fExternalSubsetSource = this.fExternalSubsetResolver.getExternalSubset(this.fDTDDescription);
      this.fHasExternalDTD = (this.fExternalSubsetSource != null);
    }
    if (this.fDocumentHandler != null) {
      if (this.fExternalSubsetSource == null) {
        this.fDocumentHandler.doctypeDecl(this.fDoctypeName, this.fDoctypePublicId, this.fDoctypeSystemId, null);
      } else {
        this.fDocumentHandler.doctypeDecl(this.fDoctypeName, this.fExternalSubsetSource.getPublicId(), this.fExternalSubsetSource.getSystemId(), null);
      }
    }
    boolean bool = true;
    if (!this.fEntityScanner.skipChar(91))
    {
      bool = false;
      this.fEntityScanner.skipSpaces();
      if (!this.fEntityScanner.skipChar(62)) {
        reportFatalError("DoctypedeclUnterminated", new Object[] { this.fDoctypeName });
      }
      this.fMarkupDepth -= 1;
    }
    return bool;
  }
  
  protected String getScannerStateName(int paramInt)
  {
    switch (paramInt)
    {
    case 0: 
      return "SCANNER_STATE_XML_DECL";
    case 5: 
      return "SCANNER_STATE_PROLOG";
    case 12: 
      return "SCANNER_STATE_TRAILING_MISC";
    case 17: 
      return "SCANNER_STATE_DTD_INTERNAL_DECLS";
    case 18: 
      return "SCANNER_STATE_DTD_EXTERNAL";
    case 19: 
      return "SCANNER_STATE_DTD_EXTERNAL_DECLS";
    }
    return super.getScannerStateName(paramInt);
  }
  
  protected final class TrailingMiscDispatcher
    implements XMLDocumentFragmentScannerImpl.Dispatcher
  {
    protected TrailingMiscDispatcher() {}
    
    public boolean dispatch(boolean paramBoolean)
      throws IOException, XNIException
    {
      try
      {
        int i;
        do
        {
          i = 0;
          switch (XMLDocumentScannerImpl.this.fScannerState)
          {
          case 12: 
            XMLDocumentScannerImpl.this.fEntityScanner.skipSpaces();
            if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(60))
            {
              XMLDocumentScannerImpl.this.setScannerState(1);
              i = 1;
            }
            else
            {
              XMLDocumentScannerImpl.this.setScannerState(7);
              i = 1;
            }
            break;
          case 1: 
            XMLDocumentScannerImpl.this.fMarkupDepth += 1;
            if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(63))
            {
              XMLDocumentScannerImpl.this.setScannerState(3);
              i = 1;
            }
            else if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(33))
            {
              XMLDocumentScannerImpl.this.setScannerState(2);
              i = 1;
            }
            else if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(47))
            {
              XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInMisc", null);
              i = 1;
            }
            else if (XMLDocumentScannerImpl.this.isValidNameStartChar(XMLDocumentScannerImpl.this.fEntityScanner.peekChar()))
            {
              XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInMisc", null);
              XMLDocumentScannerImpl.this.scanStartElement();
              XMLDocumentScannerImpl.this.setScannerState(7);
            }
            else if (XMLDocumentScannerImpl.this.isValidNameStartHighSurrogate(XMLDocumentScannerImpl.this.fEntityScanner.peekChar()))
            {
              XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInMisc", null);
              XMLDocumentScannerImpl.this.scanStartElement();
              XMLDocumentScannerImpl.this.setScannerState(7);
            }
            else
            {
              XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInMisc", null);
            }
            break;
          case 3: 
            XMLDocumentScannerImpl.this.scanPI();
            XMLDocumentScannerImpl.this.setScannerState(12);
            break;
          case 2: 
            if (!XMLDocumentScannerImpl.this.fEntityScanner.skipString("--")) {
              XMLDocumentScannerImpl.this.reportFatalError("InvalidCommentStart", null);
            }
            XMLDocumentScannerImpl.this.scanComment();
            XMLDocumentScannerImpl.this.setScannerState(12);
            break;
          case 7: 
            int j = XMLDocumentScannerImpl.this.fEntityScanner.peekChar();
            if (j == -1)
            {
              XMLDocumentScannerImpl.this.setScannerState(14);
              return false;
            }
            XMLDocumentScannerImpl.this.reportFatalError("ContentIllegalInTrailingMisc", null);
            XMLDocumentScannerImpl.this.fEntityScanner.scanChar();
            XMLDocumentScannerImpl.this.setScannerState(12);
            break;
          case 8: 
            XMLDocumentScannerImpl.this.reportFatalError("ReferenceIllegalInTrailingMisc", null);
            XMLDocumentScannerImpl.this.setScannerState(12);
            break;
          case 14: 
            return false;
          }
        } while ((paramBoolean) || (i != 0));
      }
      catch (MalformedByteSequenceException localMalformedByteSequenceException)
      {
        XMLDocumentScannerImpl.this.fErrorReporter.reportError(localMalformedByteSequenceException.getDomain(), localMalformedByteSequenceException.getKey(), localMalformedByteSequenceException.getArguments(), (short)2, localMalformedByteSequenceException);
        return false;
      }
      catch (CharConversionException localCharConversionException)
      {
        XMLDocumentScannerImpl.this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "CharConversionFailure", null, (short)2, localCharConversionException);
        return false;
      }
      catch (EOFException localEOFException)
      {
        if (XMLDocumentScannerImpl.this.fMarkupDepth != 0)
        {
          XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", null);
          return false;
        }
        XMLDocumentScannerImpl.this.setScannerState(14);
        return false;
      }
      return true;
    }
  }
  
  protected class ContentDispatcher
    extends XMLDocumentFragmentScannerImpl.FragmentContentDispatcher
  {
    protected ContentDispatcher()
    {
      super();
    }
    
    protected boolean scanForDoctypeHook()
      throws IOException, XNIException
    {
      if (XMLDocumentScannerImpl.this.fEntityScanner.skipString("DOCTYPE"))
      {
        XMLDocumentScannerImpl.this.setScannerState(4);
        return true;
      }
      return false;
    }
    
    protected boolean elementDepthIsZeroHook()
      throws IOException, XNIException
    {
      XMLDocumentScannerImpl.this.setScannerState(12);
      XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fTrailingMiscDispatcher);
      return true;
    }
    
    protected boolean scanRootElementHook()
      throws IOException, XNIException
    {
      if ((XMLDocumentScannerImpl.this.fExternalSubsetResolver != null) && (!XMLDocumentScannerImpl.this.fSeenDoctypeDecl) && (!XMLDocumentScannerImpl.this.fDisallowDoctype) && ((XMLDocumentScannerImpl.this.fValidation) || (XMLDocumentScannerImpl.this.fLoadExternalDTD)))
      {
        XMLDocumentScannerImpl.this.scanStartElementName();
        resolveExternalSubsetAndRead();
        if (XMLDocumentScannerImpl.this.scanStartElementAfterName())
        {
          XMLDocumentScannerImpl.this.setScannerState(12);
          XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fTrailingMiscDispatcher);
          return true;
        }
      }
      else if (XMLDocumentScannerImpl.this.scanStartElement())
      {
        XMLDocumentScannerImpl.this.setScannerState(12);
        XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fTrailingMiscDispatcher);
        return true;
      }
      return false;
    }
    
    protected void endOfFileHook(EOFException paramEOFException)
      throws IOException, XNIException
    {
      XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", null);
    }
    
    protected void resolveExternalSubsetAndRead()
      throws IOException, XNIException
    {
      XMLDocumentScannerImpl.this.fDTDDescription.setValues(null, null, XMLDocumentScannerImpl.this.fEntityManager.getCurrentResourceIdentifier().getExpandedSystemId(), null);
      XMLDocumentScannerImpl.this.fDTDDescription.setRootName(XMLDocumentScannerImpl.this.fElementQName.rawname);
      XMLInputSource localXMLInputSource = XMLDocumentScannerImpl.this.fExternalSubsetResolver.getExternalSubset(XMLDocumentScannerImpl.this.fDTDDescription);
      if (localXMLInputSource != null)
      {
        XMLDocumentScannerImpl.this.fDoctypeName = XMLDocumentScannerImpl.this.fElementQName.rawname;
        XMLDocumentScannerImpl.this.fDoctypePublicId = localXMLInputSource.getPublicId();
        XMLDocumentScannerImpl.this.fDoctypeSystemId = localXMLInputSource.getSystemId();
        if (XMLDocumentScannerImpl.this.fDocumentHandler != null) {
          XMLDocumentScannerImpl.this.fDocumentHandler.doctypeDecl(XMLDocumentScannerImpl.this.fDoctypeName, XMLDocumentScannerImpl.this.fDoctypePublicId, XMLDocumentScannerImpl.this.fDoctypeSystemId, null);
        }
        try
        {
          if ((XMLDocumentScannerImpl.this.fValidationManager == null) || (!XMLDocumentScannerImpl.this.fValidationManager.isCachedDTD()))
          {
            XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(localXMLInputSource);
            while (XMLDocumentScannerImpl.this.fDTDScanner.scanDTDExternalSubset(true)) {}
          }
          else
          {
            XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(null);
          }
        }
        finally
        {
          XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(XMLDocumentScannerImpl.this);
        }
      }
    }
  }
  
  protected final class DTDDispatcher
    implements XMLDocumentFragmentScannerImpl.Dispatcher
  {
    protected DTDDispatcher() {}
    
    public boolean dispatch(boolean paramBoolean)
      throws IOException, XNIException
    {
      XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(null);
      try
      {
        int i;
        do
        {
          i = 0;
          switch (XMLDocumentScannerImpl.this.fScannerState)
          {
          case 17: 
            boolean bool1 = true;
            bool3 = ((XMLDocumentScannerImpl.this.fValidation) || (XMLDocumentScannerImpl.this.fLoadExternalDTD)) && ((XMLDocumentScannerImpl.this.fValidationManager == null) || (!XMLDocumentScannerImpl.this.fValidationManager.isCachedDTD()));
            bool4 = XMLDocumentScannerImpl.this.fDTDScanner.scanDTDInternalSubset(bool1, XMLDocumentScannerImpl.this.fStandalone, (XMLDocumentScannerImpl.this.fHasExternalDTD) && (bool3));
            if (!bool4)
            {
              if (!XMLDocumentScannerImpl.this.fEntityScanner.skipChar(93)) {
                XMLDocumentScannerImpl.this.reportFatalError("EXPECTED_SQUARE_BRACKET_TO_CLOSE_INTERNAL_SUBSET", null);
              }
              XMLDocumentScannerImpl.this.fEntityScanner.skipSpaces();
              if (!XMLDocumentScannerImpl.this.fEntityScanner.skipChar(62)) {
                XMLDocumentScannerImpl.this.reportFatalError("DoctypedeclUnterminated", new Object[] { XMLDocumentScannerImpl.this.fDoctypeName });
              }
              XMLDocumentScannerImpl.this.fMarkupDepth -= 1;
              if (XMLDocumentScannerImpl.this.fDoctypeSystemId != null)
              {
                XMLDocumentScannerImpl.this.fIsEntityDeclaredVC = (!XMLDocumentScannerImpl.this.fStandalone);
                if (bool3)
                {
                  XMLDocumentScannerImpl.this.setScannerState(18);
                  continue;
                }
              }
              else if (XMLDocumentScannerImpl.this.fExternalSubsetSource != null)
              {
                XMLDocumentScannerImpl.this.fIsEntityDeclaredVC = (!XMLDocumentScannerImpl.this.fStandalone);
                if (bool3)
                {
                  XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(XMLDocumentScannerImpl.this.fExternalSubsetSource);
                  XMLDocumentScannerImpl.this.fExternalSubsetSource = null;
                  XMLDocumentScannerImpl.this.setScannerState(19);
                  continue;
                }
              }
              else
              {
                XMLDocumentScannerImpl.this.fIsEntityDeclaredVC = ((XMLDocumentScannerImpl.this.fEntityManager.hasPEReferences()) && (!XMLDocumentScannerImpl.this.fStandalone));
              }
              XMLDocumentScannerImpl.this.setScannerState(5);
              XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fPrologDispatcher);
              XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(XMLDocumentScannerImpl.this);
              boolean bool5 = true;
              return bool5;
            }
            break;
          case 18: 
            XMLDocumentScannerImpl.this.fDTDDescription.setValues(XMLDocumentScannerImpl.this.fDoctypePublicId, XMLDocumentScannerImpl.this.fDoctypeSystemId, null, null);
            XMLDocumentScannerImpl.this.fDTDDescription.setRootName(XMLDocumentScannerImpl.this.fDoctypeName);
            XMLInputSource localXMLInputSource = XMLDocumentScannerImpl.this.fEntityManager.resolveEntity(XMLDocumentScannerImpl.this.fDTDDescription);
            XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(localXMLInputSource);
            XMLDocumentScannerImpl.this.setScannerState(19);
            i = 1;
            break;
          case 19: 
            bool2 = true;
            bool3 = XMLDocumentScannerImpl.this.fDTDScanner.scanDTDExternalSubset(bool2);
            if (!bool3)
            {
              XMLDocumentScannerImpl.this.setScannerState(5);
              XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fPrologDispatcher);
              XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(XMLDocumentScannerImpl.this);
              bool4 = true;
              return bool4;
            }
            break;
          default: 
            throw new XNIException("DTDDispatcher#dispatch: scanner state=" + XMLDocumentScannerImpl.this.fScannerState + " (" + XMLDocumentScannerImpl.this.getScannerStateName(XMLDocumentScannerImpl.this.fScannerState) + ')');
          }
        } while ((paramBoolean) || (i != 0));
      }
      catch (MalformedByteSequenceException localMalformedByteSequenceException)
      {
        XMLDocumentScannerImpl.this.fErrorReporter.reportError(localMalformedByteSequenceException.getDomain(), localMalformedByteSequenceException.getKey(), localMalformedByteSequenceException.getArguments(), (short)2, localMalformedByteSequenceException);
        boolean bool2 = false;
        return bool2;
      }
      catch (CharConversionException localCharConversionException)
      {
        XMLDocumentScannerImpl.this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "CharConversionFailure", null, (short)2, localCharConversionException);
        boolean bool3 = false;
        return bool3;
      }
      catch (EOFException localEOFException)
      {
        XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", null);
        boolean bool4 = false;
        return bool4;
      }
      finally
      {
        XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(XMLDocumentScannerImpl.this);
      }
      return true;
    }
  }
  
  protected final class PrologDispatcher
    implements XMLDocumentFragmentScannerImpl.Dispatcher
  {
    protected PrologDispatcher() {}
    
    public boolean dispatch(boolean paramBoolean)
      throws IOException, XNIException
    {
      try
      {
        int i;
        do
        {
          i = 0;
          switch (XMLDocumentScannerImpl.this.fScannerState)
          {
          case 5: 
            XMLDocumentScannerImpl.this.fEntityScanner.skipSpaces();
            if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(60))
            {
              XMLDocumentScannerImpl.this.setScannerState(1);
              i = 1;
            }
            else if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(38))
            {
              XMLDocumentScannerImpl.this.setScannerState(8);
              i = 1;
            }
            else
            {
              XMLDocumentScannerImpl.this.setScannerState(7);
              i = 1;
            }
            break;
          case 1: 
            XMLDocumentScannerImpl.this.fMarkupDepth += 1;
            if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(33))
            {
              if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(45))
              {
                if (!XMLDocumentScannerImpl.this.fEntityScanner.skipChar(45)) {
                  XMLDocumentScannerImpl.this.reportFatalError("InvalidCommentStart", null);
                }
                XMLDocumentScannerImpl.this.setScannerState(2);
                i = 1;
              }
              else if (XMLDocumentScannerImpl.this.fEntityScanner.skipString("DOCTYPE"))
              {
                XMLDocumentScannerImpl.this.setScannerState(4);
                i = 1;
              }
              else
              {
                XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInProlog", null);
              }
            }
            else
            {
              if (XMLDocumentScannerImpl.this.isValidNameStartChar(XMLDocumentScannerImpl.this.fEntityScanner.peekChar()))
              {
                XMLDocumentScannerImpl.this.setScannerState(6);
                XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fContentDispatcher);
                return true;
              }
              if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(63))
              {
                XMLDocumentScannerImpl.this.setScannerState(3);
                i = 1;
              }
              else
              {
                if (XMLDocumentScannerImpl.this.isValidNameStartHighSurrogate(XMLDocumentScannerImpl.this.fEntityScanner.peekChar()))
                {
                  XMLDocumentScannerImpl.this.setScannerState(6);
                  XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fContentDispatcher);
                  return true;
                }
                XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInProlog", null);
              }
            }
            break;
          case 2: 
            XMLDocumentScannerImpl.this.scanComment();
            XMLDocumentScannerImpl.this.setScannerState(5);
            break;
          case 3: 
            XMLDocumentScannerImpl.this.scanPI();
            XMLDocumentScannerImpl.this.setScannerState(5);
            break;
          case 4: 
            if (XMLDocumentScannerImpl.this.fDisallowDoctype) {
              XMLDocumentScannerImpl.this.reportFatalError("DoctypeNotAllowed", null);
            }
            if (XMLDocumentScannerImpl.this.fSeenDoctypeDecl) {
              XMLDocumentScannerImpl.this.reportFatalError("AlreadySeenDoctype", null);
            }
            XMLDocumentScannerImpl.this.fSeenDoctypeDecl = true;
            if (XMLDocumentScannerImpl.this.scanDoctypeDecl())
            {
              XMLDocumentScannerImpl.this.setScannerState(17);
              XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fDTDDispatcher);
              return true;
            }
            if (XMLDocumentScannerImpl.this.fDoctypeSystemId != null)
            {
              XMLDocumentScannerImpl.this.fIsEntityDeclaredVC = (!XMLDocumentScannerImpl.this.fStandalone);
              if (((XMLDocumentScannerImpl.this.fValidation) || (XMLDocumentScannerImpl.this.fLoadExternalDTD)) && ((XMLDocumentScannerImpl.this.fValidationManager == null) || (!XMLDocumentScannerImpl.this.fValidationManager.isCachedDTD())))
              {
                XMLDocumentScannerImpl.this.setScannerState(18);
                XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fDTDDispatcher);
                return true;
              }
            }
            else if (XMLDocumentScannerImpl.this.fExternalSubsetSource != null)
            {
              XMLDocumentScannerImpl.this.fIsEntityDeclaredVC = (!XMLDocumentScannerImpl.this.fStandalone);
              if (((XMLDocumentScannerImpl.this.fValidation) || (XMLDocumentScannerImpl.this.fLoadExternalDTD)) && ((XMLDocumentScannerImpl.this.fValidationManager == null) || (!XMLDocumentScannerImpl.this.fValidationManager.isCachedDTD())))
              {
                XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(XMLDocumentScannerImpl.this.fExternalSubsetSource);
                XMLDocumentScannerImpl.this.fExternalSubsetSource = null;
                XMLDocumentScannerImpl.this.setScannerState(19);
                XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fDTDDispatcher);
                return true;
              }
            }
            XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(null);
            XMLDocumentScannerImpl.this.setScannerState(5);
            break;
          case 7: 
            XMLDocumentScannerImpl.this.reportFatalError("ContentIllegalInProlog", null);
            XMLDocumentScannerImpl.this.fEntityScanner.scanChar();
          case 8: 
            XMLDocumentScannerImpl.this.reportFatalError("ReferenceIllegalInProlog", null);
          }
        } while ((paramBoolean) || (i != 0));
        if (paramBoolean)
        {
          if (XMLDocumentScannerImpl.this.fEntityScanner.scanChar() != 60) {
            XMLDocumentScannerImpl.this.reportFatalError("RootElementRequired", null);
          }
          XMLDocumentScannerImpl.this.setScannerState(6);
          XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fContentDispatcher);
        }
      }
      catch (MalformedByteSequenceException localMalformedByteSequenceException)
      {
        XMLDocumentScannerImpl.this.fErrorReporter.reportError(localMalformedByteSequenceException.getDomain(), localMalformedByteSequenceException.getKey(), localMalformedByteSequenceException.getArguments(), (short)2, localMalformedByteSequenceException);
        return false;
      }
      catch (CharConversionException localCharConversionException)
      {
        XMLDocumentScannerImpl.this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "CharConversionFailure", null, (short)2, localCharConversionException);
        return false;
      }
      catch (EOFException localEOFException)
      {
        XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", null);
        return false;
      }
      return true;
    }
  }
  
  protected final class XMLDeclDispatcher
    implements XMLDocumentFragmentScannerImpl.Dispatcher
  {
    protected XMLDeclDispatcher() {}
    
    public boolean dispatch(boolean paramBoolean)
      throws IOException, XNIException
    {
      XMLDocumentScannerImpl.this.setScannerState(5);
      XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fPrologDispatcher);
      try
      {
        if (XMLDocumentScannerImpl.this.fEntityScanner.skipString("<?xml"))
        {
          XMLDocumentScannerImpl.this.fMarkupDepth += 1;
          if (XMLChar.isName(XMLDocumentScannerImpl.this.fEntityScanner.peekChar()))
          {
            XMLDocumentScannerImpl.this.fStringBuffer.clear();
            XMLDocumentScannerImpl.this.fStringBuffer.append("xml");
            if (XMLDocumentScannerImpl.this.fNamespaces) {
              while (XMLChar.isNCName(XMLDocumentScannerImpl.this.fEntityScanner.peekChar())) {
                XMLDocumentScannerImpl.this.fStringBuffer.append((char)XMLDocumentScannerImpl.this.fEntityScanner.scanChar());
              }
            } else {
              while (XMLChar.isName(XMLDocumentScannerImpl.this.fEntityScanner.peekChar())) {
                XMLDocumentScannerImpl.this.fStringBuffer.append((char)XMLDocumentScannerImpl.this.fEntityScanner.scanChar());
              }
            }
            String str = XMLDocumentScannerImpl.this.fSymbolTable.addSymbol(XMLDocumentScannerImpl.this.fStringBuffer.ch, XMLDocumentScannerImpl.this.fStringBuffer.offset, XMLDocumentScannerImpl.this.fStringBuffer.length);
            XMLDocumentScannerImpl.this.scanPIData(str, XMLDocumentScannerImpl.this.fString);
          }
          else
          {
            XMLDocumentScannerImpl.this.scanXMLDeclOrTextDecl(false);
          }
        }
        XMLDocumentScannerImpl.this.fEntityManager.fCurrentEntity.mayReadChunks = true;
        return true;
      }
      catch (MalformedByteSequenceException localMalformedByteSequenceException)
      {
        XMLDocumentScannerImpl.this.fErrorReporter.reportError(localMalformedByteSequenceException.getDomain(), localMalformedByteSequenceException.getKey(), localMalformedByteSequenceException.getArguments(), (short)2, localMalformedByteSequenceException);
        return false;
      }
      catch (CharConversionException localCharConversionException)
      {
        XMLDocumentScannerImpl.this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "CharConversionFailure", null, (short)2, localCharConversionException);
        return false;
      }
      catch (EOFException localEOFException)
      {
        XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", null);
      }
      return false;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.XMLDocumentScannerImpl
 * JD-Core Version:    0.7.0.1
 */
package org.apache.xerces.impl;

import java.io.IOException;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLResourceIdentifierImpl;
import org.apache.xerces.util.XMLStringBuffer;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.XMLDTDContentModelHandler;
import org.apache.xerces.xni.XMLDTDHandler;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDTDScanner;
import org.apache.xerces.xni.parser.XMLInputSource;

public class XMLDTDScannerImpl
  extends XMLScanner
  implements XMLDTDScanner, XMLComponent, XMLEntityHandler
{
  protected static final int SCANNER_STATE_END_OF_INPUT = 0;
  protected static final int SCANNER_STATE_TEXT_DECL = 1;
  protected static final int SCANNER_STATE_MARKUP_DECL = 2;
  private static final String[] RECOGNIZED_FEATURES = { "http://xml.org/sax/features/validation", "http://apache.org/xml/features/scanner/notify-char-refs" };
  private static final Boolean[] FEATURE_DEFAULTS = { null, Boolean.FALSE };
  private static final String[] RECOGNIZED_PROPERTIES = { "http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-manager" };
  private static final Object[] PROPERTY_DEFAULTS = { null, null, null };
  private static final boolean DEBUG_SCANNER_STATE = false;
  protected XMLDTDHandler fDTDHandler;
  protected XMLDTDContentModelHandler fDTDContentModelHandler;
  protected int fScannerState;
  protected boolean fStandalone;
  protected boolean fSeenExternalDTD;
  protected boolean fSeenPEReferences;
  private boolean fStartDTDCalled;
  private int[] fContentStack = new int[5];
  private int fContentDepth;
  private int[] fPEStack = new int[5];
  private boolean[] fPEReport = new boolean[5];
  private int fPEDepth;
  private int fMarkUpDepth;
  private int fExtEntityDepth;
  private int fIncludeSectDepth;
  private final String[] fStrings = new String[3];
  private final XMLString fString = new XMLString();
  private final XMLStringBuffer fStringBuffer = new XMLStringBuffer();
  private final XMLStringBuffer fStringBuffer2 = new XMLStringBuffer();
  private final XMLString fLiteral = new XMLString();
  private final XMLString fLiteral2 = new XMLString();
  private String[] fEnumeration = new String[5];
  private int fEnumerationCount;
  private final XMLStringBuffer fIgnoreConditionalBuffer = new XMLStringBuffer(128);
  
  public XMLDTDScannerImpl() {}
  
  public XMLDTDScannerImpl(SymbolTable paramSymbolTable, XMLErrorReporter paramXMLErrorReporter, XMLEntityManager paramXMLEntityManager)
  {
    this.fSymbolTable = paramSymbolTable;
    this.fErrorReporter = paramXMLErrorReporter;
    this.fEntityManager = paramXMLEntityManager;
    paramXMLEntityManager.setProperty("http://apache.org/xml/properties/internal/symbol-table", this.fSymbolTable);
  }
  
  public void setInputSource(XMLInputSource paramXMLInputSource)
    throws IOException
  {
    if (paramXMLInputSource == null)
    {
      if (this.fDTDHandler != null)
      {
        this.fDTDHandler.startDTD(null, null);
        this.fDTDHandler.endDTD(null);
      }
      return;
    }
    this.fEntityManager.setEntityHandler(this);
    this.fEntityManager.startDTDEntity(paramXMLInputSource);
  }
  
  public boolean scanDTDExternalSubset(boolean paramBoolean)
    throws IOException, XNIException
  {
    this.fEntityManager.setEntityHandler(this);
    if (this.fScannerState == 1)
    {
      this.fSeenExternalDTD = true;
      boolean bool = scanTextDecl();
      if (this.fScannerState == 0) {
        return false;
      }
      setScannerState(2);
      if ((bool) && (!paramBoolean)) {
        return true;
      }
    }
    do
    {
      if (!scanDecls(paramBoolean)) {
        return false;
      }
    } while (paramBoolean);
    return true;
  }
  
  public boolean scanDTDInternalSubset(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws IOException, XNIException
  {
    this.fEntityScanner = this.fEntityManager.getEntityScanner();
    this.fEntityManager.setEntityHandler(this);
    this.fStandalone = paramBoolean2;
    if (this.fScannerState == 1)
    {
      if (this.fDTDHandler != null)
      {
        this.fDTDHandler.startDTD(this.fEntityScanner, null);
        this.fStartDTDCalled = true;
      }
      setScannerState(2);
    }
    do
    {
      if (!scanDecls(paramBoolean1))
      {
        if ((this.fDTDHandler != null) && (!paramBoolean3)) {
          this.fDTDHandler.endDTD(null);
        }
        setScannerState(1);
        return false;
      }
    } while (paramBoolean1);
    return true;
  }
  
  public void reset(XMLComponentManager paramXMLComponentManager)
    throws XMLConfigurationException
  {
    super.reset(paramXMLComponentManager);
    init();
  }
  
  public void reset()
  {
    super.reset();
    init();
  }
  
  public String[] getRecognizedFeatures()
  {
    return (String[])RECOGNIZED_FEATURES.clone();
  }
  
  public String[] getRecognizedProperties()
  {
    return (String[])RECOGNIZED_PROPERTIES.clone();
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
  
  public void startEntity(String paramString1, XMLResourceIdentifier paramXMLResourceIdentifier, String paramString2, Augmentations paramAugmentations)
    throws XNIException
  {
    super.startEntity(paramString1, paramXMLResourceIdentifier, paramString2, paramAugmentations);
    boolean bool = paramString1.equals("[dtd]");
    if (bool)
    {
      if ((this.fDTDHandler != null) && (!this.fStartDTDCalled)) {
        this.fDTDHandler.startDTD(this.fEntityScanner, null);
      }
      if (this.fDTDHandler != null) {
        this.fDTDHandler.startExternalSubset(paramXMLResourceIdentifier, null);
      }
      this.fEntityManager.startExternalSubset();
      this.fExtEntityDepth += 1;
    }
    else if (paramString1.charAt(0) == '%')
    {
      pushPEStack(this.fMarkUpDepth, this.fReportEntity);
      if (this.fEntityScanner.isExternal()) {
        this.fExtEntityDepth += 1;
      }
    }
    if ((this.fDTDHandler != null) && (!bool) && (this.fReportEntity)) {
      this.fDTDHandler.startParameterEntity(paramString1, paramXMLResourceIdentifier, paramString2, paramAugmentations);
    }
  }
  
  public void endEntity(String paramString, Augmentations paramAugmentations)
    throws XNIException
  {
    super.endEntity(paramString, paramAugmentations);
    if (this.fScannerState == 0) {
      return;
    }
    boolean bool = this.fReportEntity;
    if (paramString.startsWith("%"))
    {
      bool = peekReportEntity();
      int i = popPEStack();
      if ((i == 0) && (i < this.fMarkUpDepth)) {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "ILL_FORMED_PARAMETER_ENTITY_WHEN_USED_IN_DECL", new Object[] { this.fEntityManager.fCurrentEntity.name }, (short)2);
      }
      if (i != this.fMarkUpDepth)
      {
        bool = false;
        if (this.fValidation) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "ImproperDeclarationNesting", new Object[] { paramString }, (short)1);
        }
      }
      if (this.fEntityScanner.isExternal()) {
        this.fExtEntityDepth -= 1;
      }
      if ((this.fDTDHandler != null) && (bool)) {
        this.fDTDHandler.endParameterEntity(paramString, paramAugmentations);
      }
    }
    else if (paramString.equals("[dtd]"))
    {
      if (this.fIncludeSectDepth != 0) {
        reportFatalError("IncludeSectUnterminated", null);
      }
      this.fScannerState = 0;
      this.fEntityManager.endExternalSubset();
      if (this.fDTDHandler != null)
      {
        this.fDTDHandler.endExternalSubset(null);
        this.fDTDHandler.endDTD(null);
      }
      this.fExtEntityDepth -= 1;
    }
  }
  
  protected final void setScannerState(int paramInt)
  {
    this.fScannerState = paramInt;
  }
  
  private static String getScannerStateName(int paramInt)
  {
    return "??? (" + paramInt + ')';
  }
  
  protected final boolean scanningInternalSubset()
  {
    return this.fExtEntityDepth == 0;
  }
  
  protected void startPE(String paramString, boolean paramBoolean)
    throws IOException, XNIException
  {
    int i = this.fPEDepth;
    String str = "%" + paramString;
    if (!this.fSeenPEReferences)
    {
      this.fSeenPEReferences = true;
      this.fEntityManager.notifyHasPEReferences();
    }
    if ((this.fValidation) && (!this.fEntityManager.isDeclaredEntity(str))) {
      this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EntityNotDeclared", new Object[] { paramString }, (short)1);
    }
    this.fEntityManager.startEntity(this.fSymbolTable.addSymbol(str), paramBoolean);
    if ((i != this.fPEDepth) && (this.fEntityScanner.isExternal())) {
      scanTextDecl();
    }
  }
  
  protected final boolean scanTextDecl()
    throws IOException, XNIException
  {
    boolean bool = false;
    if (this.fEntityScanner.skipString("<?xml"))
    {
      this.fMarkUpDepth += 1;
      String str1;
      if (isValidNameChar(this.fEntityScanner.peekChar()))
      {
        this.fStringBuffer.clear();
        this.fStringBuffer.append("xml");
        if (this.fNamespaces) {
          while (isValidNCName(this.fEntityScanner.peekChar())) {
            this.fStringBuffer.append((char)this.fEntityScanner.scanChar());
          }
        } else {
          while (isValidNameChar(this.fEntityScanner.peekChar())) {
            this.fStringBuffer.append((char)this.fEntityScanner.scanChar());
          }
        }
        str1 = this.fSymbolTable.addSymbol(this.fStringBuffer.ch, this.fStringBuffer.offset, this.fStringBuffer.length);
        scanPIData(str1, this.fString);
      }
      else
      {
        str1 = null;
        String str2 = null;
        scanXMLDeclOrTextDecl(true, this.fStrings);
        bool = true;
        this.fMarkUpDepth -= 1;
        str1 = this.fStrings[0];
        str2 = this.fStrings[1];
        this.fEntityScanner.setXMLVersion(str1);
        if (!this.fEntityScanner.fCurrentEntity.isEncodingExternallySpecified()) {
          this.fEntityScanner.setEncoding(str2);
        }
        if (this.fDTDHandler != null) {
          this.fDTDHandler.textDecl(str1, str2, null);
        }
      }
    }
    this.fEntityManager.fCurrentEntity.mayReadChunks = true;
    return bool;
  }
  
  protected final void scanPIData(String paramString, XMLString paramXMLString)
    throws IOException, XNIException
  {
    super.scanPIData(paramString, paramXMLString);
    this.fMarkUpDepth -= 1;
    if (this.fDTDHandler != null) {
      this.fDTDHandler.processingInstruction(paramString, paramXMLString, null);
    }
  }
  
  protected final void scanComment()
    throws IOException, XNIException
  {
    this.fReportEntity = false;
    scanComment(this.fStringBuffer);
    this.fMarkUpDepth -= 1;
    if (this.fDTDHandler != null) {
      this.fDTDHandler.comment(this.fStringBuffer, null);
    }
    this.fReportEntity = true;
  }
  
  protected final void scanElementDecl()
    throws IOException, XNIException
  {
    this.fReportEntity = false;
    if (!skipSeparator(true, !scanningInternalSubset())) {
      reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ELEMENT_TYPE_IN_ELEMENTDECL", null);
    }
    String str1 = this.fEntityScanner.scanName();
    if (str1 == null) {
      reportFatalError("MSG_ELEMENT_TYPE_REQUIRED_IN_ELEMENTDECL", null);
    }
    if (!skipSeparator(true, !scanningInternalSubset())) {
      reportFatalError("MSG_SPACE_REQUIRED_BEFORE_CONTENTSPEC_IN_ELEMENTDECL", new Object[] { str1 });
    }
    if (this.fDTDContentModelHandler != null) {
      this.fDTDContentModelHandler.startContentModel(str1, null);
    }
    String str2 = null;
    this.fReportEntity = true;
    if (this.fEntityScanner.skipString("EMPTY"))
    {
      str2 = "EMPTY";
      if (this.fDTDContentModelHandler != null) {
        this.fDTDContentModelHandler.empty(null);
      }
    }
    else if (this.fEntityScanner.skipString("ANY"))
    {
      str2 = "ANY";
      if (this.fDTDContentModelHandler != null) {
        this.fDTDContentModelHandler.any(null);
      }
    }
    else
    {
      if (!this.fEntityScanner.skipChar(40)) {
        reportFatalError("MSG_OPEN_PAREN_OR_ELEMENT_TYPE_REQUIRED_IN_CHILDREN", new Object[] { str1 });
      }
      if (this.fDTDContentModelHandler != null) {
        this.fDTDContentModelHandler.startGroup(null);
      }
      this.fStringBuffer.clear();
      this.fStringBuffer.append('(');
      this.fMarkUpDepth += 1;
      skipSeparator(false, !scanningInternalSubset());
      if (this.fEntityScanner.skipString("#PCDATA")) {
        scanMixed(str1);
      } else {
        scanChildren(str1);
      }
      str2 = this.fStringBuffer.toString();
    }
    if (this.fDTDContentModelHandler != null) {
      this.fDTDContentModelHandler.endContentModel(null);
    }
    this.fReportEntity = false;
    skipSeparator(false, !scanningInternalSubset());
    if (!this.fEntityScanner.skipChar(62)) {
      reportFatalError("ElementDeclUnterminated", new Object[] { str1 });
    }
    this.fReportEntity = true;
    this.fMarkUpDepth -= 1;
    if (this.fDTDHandler != null) {
      this.fDTDHandler.elementDecl(str1, str2, null);
    }
  }
  
  private final void scanMixed(String paramString)
    throws IOException, XNIException
  {
    String str = null;
    this.fStringBuffer.append("#PCDATA");
    if (this.fDTDContentModelHandler != null) {
      this.fDTDContentModelHandler.pcdata(null);
    }
    skipSeparator(false, !scanningInternalSubset());
    while (this.fEntityScanner.skipChar(124))
    {
      this.fStringBuffer.append('|');
      if (this.fDTDContentModelHandler != null) {
        this.fDTDContentModelHandler.separator((short)0, null);
      }
      skipSeparator(false, !scanningInternalSubset());
      str = this.fEntityScanner.scanName();
      if (str == null) {
        reportFatalError("MSG_ELEMENT_TYPE_REQUIRED_IN_MIXED_CONTENT", new Object[] { paramString });
      }
      this.fStringBuffer.append(str);
      if (this.fDTDContentModelHandler != null) {
        this.fDTDContentModelHandler.element(str, null);
      }
      skipSeparator(false, !scanningInternalSubset());
    }
    if (this.fEntityScanner.skipString(")*"))
    {
      this.fStringBuffer.append(")*");
      if (this.fDTDContentModelHandler != null)
      {
        this.fDTDContentModelHandler.endGroup(null);
        this.fDTDContentModelHandler.occurrence((short)3, null);
      }
    }
    else if (str != null)
    {
      reportFatalError("MixedContentUnterminated", new Object[] { paramString });
    }
    else if (this.fEntityScanner.skipChar(41))
    {
      this.fStringBuffer.append(')');
      if (this.fDTDContentModelHandler != null) {
        this.fDTDContentModelHandler.endGroup(null);
      }
    }
    else
    {
      reportFatalError("MSG_CLOSE_PAREN_REQUIRED_IN_CHILDREN", new Object[] { paramString });
    }
    this.fMarkUpDepth -= 1;
  }
  
  private final void scanChildren(String paramString)
    throws IOException, XNIException
  {
    this.fContentDepth = 0;
    pushContentStack(0);
    int i = 0;
    for (;;)
    {
      if (this.fEntityScanner.skipChar(40))
      {
        this.fMarkUpDepth += 1;
        this.fStringBuffer.append('(');
        if (this.fDTDContentModelHandler != null) {
          this.fDTDContentModelHandler.startGroup(null);
        }
        pushContentStack(i);
        i = 0;
        skipSeparator(false, !scanningInternalSubset());
      }
      else
      {
        skipSeparator(false, !scanningInternalSubset());
        String str = this.fEntityScanner.scanName();
        if (str == null)
        {
          reportFatalError("MSG_OPEN_PAREN_OR_ELEMENT_TYPE_REQUIRED_IN_CHILDREN", new Object[] { paramString });
          return;
        }
        if (this.fDTDContentModelHandler != null) {
          this.fDTDContentModelHandler.element(str, null);
        }
        this.fStringBuffer.append(str);
        int j = this.fEntityScanner.peekChar();
        short s;
        if ((j == 63) || (j == 42) || (j == 43))
        {
          if (this.fDTDContentModelHandler != null)
          {
            if (j == 63) {
              s = 2;
            } else if (j == 42) {
              s = 3;
            } else {
              s = 4;
            }
            this.fDTDContentModelHandler.occurrence(s, null);
          }
          this.fEntityScanner.scanChar();
          this.fStringBuffer.append((char)j);
        }
        do
        {
          skipSeparator(false, !scanningInternalSubset());
          j = this.fEntityScanner.peekChar();
          if ((j == 44) && (i != 124))
          {
            i = j;
            if (this.fDTDContentModelHandler != null) {
              this.fDTDContentModelHandler.separator((short)1, null);
            }
            this.fEntityScanner.scanChar();
            this.fStringBuffer.append(',');
            break;
          }
          if ((j == 124) && (i != 44))
          {
            i = j;
            if (this.fDTDContentModelHandler != null) {
              this.fDTDContentModelHandler.separator((short)0, null);
            }
            this.fEntityScanner.scanChar();
            this.fStringBuffer.append('|');
            break;
          }
          if (j != 41) {
            reportFatalError("MSG_CLOSE_PAREN_REQUIRED_IN_CHILDREN", new Object[] { paramString });
          }
          if (this.fDTDContentModelHandler != null) {
            this.fDTDContentModelHandler.endGroup(null);
          }
          i = popContentStack();
          if (this.fEntityScanner.skipString(")?"))
          {
            this.fStringBuffer.append(")?");
            if (this.fDTDContentModelHandler != null)
            {
              s = 2;
              this.fDTDContentModelHandler.occurrence(s, null);
            }
          }
          else if (this.fEntityScanner.skipString(")+"))
          {
            this.fStringBuffer.append(")+");
            if (this.fDTDContentModelHandler != null)
            {
              s = 4;
              this.fDTDContentModelHandler.occurrence(s, null);
            }
          }
          else if (this.fEntityScanner.skipString(")*"))
          {
            this.fStringBuffer.append(")*");
            if (this.fDTDContentModelHandler != null)
            {
              s = 3;
              this.fDTDContentModelHandler.occurrence(s, null);
            }
          }
          else
          {
            this.fEntityScanner.scanChar();
            this.fStringBuffer.append(')');
          }
          this.fMarkUpDepth -= 1;
        } while (this.fContentDepth != 0);
        return;
        skipSeparator(false, !scanningInternalSubset());
      }
    }
  }
  
  protected final void scanAttlistDecl()
    throws IOException, XNIException
  {
    this.fReportEntity = false;
    if (!skipSeparator(true, !scanningInternalSubset())) {
      reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ELEMENT_TYPE_IN_ATTLISTDECL", null);
    }
    String str1 = this.fEntityScanner.scanName();
    if (str1 == null) {
      reportFatalError("MSG_ELEMENT_TYPE_REQUIRED_IN_ATTLISTDECL", null);
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.startAttlist(str1, null);
    }
    if (!skipSeparator(true, !scanningInternalSubset()))
    {
      if (this.fEntityScanner.skipChar(62))
      {
        if (this.fDTDHandler != null) {
          this.fDTDHandler.endAttlist(null);
        }
        this.fMarkUpDepth -= 1;
        return;
      }
      reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ATTRIBUTE_NAME_IN_ATTDEF", new Object[] { str1 });
    }
    while (!this.fEntityScanner.skipChar(62))
    {
      String str2 = this.fEntityScanner.scanName();
      if (str2 == null) {
        reportFatalError("AttNameRequiredInAttDef", new Object[] { str1 });
      }
      if (!skipSeparator(true, !scanningInternalSubset())) {
        reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ATTTYPE_IN_ATTDEF", new Object[] { str1, str2 });
      }
      String str3 = scanAttType(str1, str2);
      if (!skipSeparator(true, !scanningInternalSubset())) {
        reportFatalError("MSG_SPACE_REQUIRED_BEFORE_DEFAULTDECL_IN_ATTDEF", new Object[] { str1, str2 });
      }
      String str4 = scanAttDefaultDecl(str1, str2, str3, this.fLiteral, this.fLiteral2);
      if (this.fDTDHandler != null)
      {
        String[] arrayOfString = null;
        if (this.fEnumerationCount != 0)
        {
          arrayOfString = new String[this.fEnumerationCount];
          System.arraycopy(this.fEnumeration, 0, arrayOfString, 0, this.fEnumerationCount);
        }
        if ((str4 != null) && ((str4.equals("#REQUIRED")) || (str4.equals("#IMPLIED")))) {
          this.fDTDHandler.attributeDecl(str1, str2, str3, arrayOfString, str4, null, null, null);
        } else {
          this.fDTDHandler.attributeDecl(str1, str2, str3, arrayOfString, str4, this.fLiteral, this.fLiteral2, null);
        }
      }
      skipSeparator(false, !scanningInternalSubset());
    }
    if (this.fDTDHandler != null) {
      this.fDTDHandler.endAttlist(null);
    }
    this.fMarkUpDepth -= 1;
    this.fReportEntity = true;
  }
  
  private final String scanAttType(String paramString1, String paramString2)
    throws IOException, XNIException
  {
    String str1 = null;
    this.fEnumerationCount = 0;
    if (this.fEntityScanner.skipString("CDATA"))
    {
      str1 = "CDATA";
    }
    else if (this.fEntityScanner.skipString("IDREFS"))
    {
      str1 = "IDREFS";
    }
    else if (this.fEntityScanner.skipString("IDREF"))
    {
      str1 = "IDREF";
    }
    else if (this.fEntityScanner.skipString("ID"))
    {
      str1 = "ID";
    }
    else if (this.fEntityScanner.skipString("ENTITY"))
    {
      str1 = "ENTITY";
    }
    else if (this.fEntityScanner.skipString("ENTITIES"))
    {
      str1 = "ENTITIES";
    }
    else if (this.fEntityScanner.skipString("NMTOKENS"))
    {
      str1 = "NMTOKENS";
    }
    else if (this.fEntityScanner.skipString("NMTOKEN"))
    {
      str1 = "NMTOKEN";
    }
    else
    {
      int i;
      String str2;
      if (this.fEntityScanner.skipString("NOTATION"))
      {
        str1 = "NOTATION";
        if (!skipSeparator(true, !scanningInternalSubset())) {
          reportFatalError("MSG_SPACE_REQUIRED_AFTER_NOTATION_IN_NOTATIONTYPE", new Object[] { paramString1, paramString2 });
        }
        i = this.fEntityScanner.scanChar();
        if (i != 40) {
          reportFatalError("MSG_OPEN_PAREN_REQUIRED_IN_NOTATIONTYPE", new Object[] { paramString1, paramString2 });
        }
        this.fMarkUpDepth += 1;
        do
        {
          skipSeparator(false, !scanningInternalSubset());
          str2 = this.fEntityScanner.scanName();
          if (str2 == null)
          {
            reportFatalError("MSG_NAME_REQUIRED_IN_NOTATIONTYPE", new Object[] { paramString1, paramString2 });
            i = skipInvalidEnumerationValue();
            if (i != 124) {
              break;
            }
          }
          else
          {
            ensureEnumerationSize(this.fEnumerationCount + 1);
            this.fEnumeration[(this.fEnumerationCount++)] = str2;
            skipSeparator(false, !scanningInternalSubset());
            i = this.fEntityScanner.scanChar();
          }
        } while (i == 124);
        if (i != 41) {
          reportFatalError("NotationTypeUnterminated", new Object[] { paramString1, paramString2 });
        }
        this.fMarkUpDepth -= 1;
      }
      else
      {
        str1 = "ENUMERATION";
        i = this.fEntityScanner.scanChar();
        if (i != 40) {
          reportFatalError("AttTypeRequiredInAttDef", new Object[] { paramString1, paramString2 });
        }
        this.fMarkUpDepth += 1;
        do
        {
          skipSeparator(false, !scanningInternalSubset());
          str2 = this.fEntityScanner.scanNmtoken();
          if (str2 == null)
          {
            reportFatalError("MSG_NMTOKEN_REQUIRED_IN_ENUMERATION", new Object[] { paramString1, paramString2 });
            i = skipInvalidEnumerationValue();
            if (i != 124) {
              break;
            }
          }
          else
          {
            ensureEnumerationSize(this.fEnumerationCount + 1);
            this.fEnumeration[(this.fEnumerationCount++)] = str2;
            skipSeparator(false, !scanningInternalSubset());
            i = this.fEntityScanner.scanChar();
          }
        } while (i == 124);
        if (i != 41) {
          reportFatalError("EnumerationUnterminated", new Object[] { paramString1, paramString2 });
        }
        this.fMarkUpDepth -= 1;
      }
    }
    return str1;
  }
  
  protected final String scanAttDefaultDecl(String paramString1, String paramString2, String paramString3, XMLString paramXMLString1, XMLString paramXMLString2)
    throws IOException, XNIException
  {
    String str = null;
    this.fString.clear();
    paramXMLString1.clear();
    if (this.fEntityScanner.skipString("#REQUIRED"))
    {
      str = "#REQUIRED";
    }
    else if (this.fEntityScanner.skipString("#IMPLIED"))
    {
      str = "#IMPLIED";
    }
    else
    {
      if (this.fEntityScanner.skipString("#FIXED"))
      {
        str = "#FIXED";
        if (!skipSeparator(true, !scanningInternalSubset())) {
          reportFatalError("MSG_SPACE_REQUIRED_AFTER_FIXED_IN_DEFAULTDECL", new Object[] { paramString1, paramString2 });
        }
      }
      boolean bool = (!this.fStandalone) && ((this.fSeenExternalDTD) || (this.fSeenPEReferences));
      scanAttributeValue(paramXMLString1, paramXMLString2, paramString2, bool, paramString1);
    }
    return str;
  }
  
  private final void scanEntityDecl()
    throws IOException, XNIException
  {
    boolean bool1 = false;
    int i = 0;
    this.fReportEntity = false;
    if (this.fEntityScanner.skipSpaces())
    {
      if (!this.fEntityScanner.skipChar(37))
      {
        bool1 = false;
      }
      else if (skipSeparator(true, !scanningInternalSubset()))
      {
        bool1 = true;
      }
      else if (scanningInternalSubset())
      {
        reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ENTITY_NAME_IN_ENTITYDECL", null);
        bool1 = true;
      }
      else if (this.fEntityScanner.peekChar() == 37)
      {
        skipSeparator(false, !scanningInternalSubset());
        bool1 = true;
      }
      else
      {
        i = 1;
      }
    }
    else if ((scanningInternalSubset()) || (!this.fEntityScanner.skipChar(37)))
    {
      reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ENTITY_NAME_IN_ENTITYDECL", null);
      bool1 = false;
    }
    else if (this.fEntityScanner.skipSpaces())
    {
      reportFatalError("MSG_SPACE_REQUIRED_BEFORE_PERCENT_IN_PEDECL", null);
      bool1 = false;
    }
    else
    {
      i = 1;
    }
    if (i != 0) {
      for (;;)
      {
        str1 = this.fEntityScanner.scanName();
        if (str1 == null) {
          reportFatalError("NameRequiredInPEReference", null);
        } else if (!this.fEntityScanner.skipChar(59)) {
          reportFatalError("SemicolonRequiredInPEReference", new Object[] { str1 });
        } else {
          startPE(str1, false);
        }
        this.fEntityScanner.skipSpaces();
        if (!this.fEntityScanner.skipChar(37)) {
          break;
        }
        if (!bool1)
        {
          if (skipSeparator(true, !scanningInternalSubset()))
          {
            bool1 = true;
            break;
          }
          bool1 = this.fEntityScanner.skipChar(37);
        }
      }
    }
    String str1 = null;
    if (this.fNamespaces) {
      str1 = this.fEntityScanner.scanNCName();
    } else {
      str1 = this.fEntityScanner.scanName();
    }
    if (str1 == null) {
      reportFatalError("MSG_ENTITY_NAME_REQUIRED_IN_ENTITYDECL", null);
    }
    if (!skipSeparator(true, !scanningInternalSubset())) {
      if ((this.fNamespaces) && (this.fEntityScanner.peekChar() == 58))
      {
        this.fEntityScanner.scanChar();
        localObject = new XMLStringBuffer(str1);
        ((XMLStringBuffer)localObject).append(":");
        str2 = this.fEntityScanner.scanName();
        if (str2 != null) {
          ((XMLStringBuffer)localObject).append(str2);
        }
        reportFatalError("ColonNotLegalWithNS", new Object[] { ((XMLString)localObject).toString() });
        if (!skipSeparator(true, !scanningInternalSubset())) {
          reportFatalError("MSG_SPACE_REQUIRED_AFTER_ENTITY_NAME_IN_ENTITYDECL", new Object[] { str1 });
        }
      }
      else
      {
        reportFatalError("MSG_SPACE_REQUIRED_AFTER_ENTITY_NAME_IN_ENTITYDECL", new Object[] { str1 });
      }
    }
    scanExternalID(this.fStrings, false);
    Object localObject = this.fStrings[0];
    String str2 = this.fStrings[1];
    String str3 = null;
    boolean bool2 = skipSeparator(true, !scanningInternalSubset());
    if ((!bool1) && (this.fEntityScanner.skipString("NDATA")))
    {
      if (!bool2) {
        reportFatalError("MSG_SPACE_REQUIRED_BEFORE_NDATA_IN_UNPARSED_ENTITYDECL", new Object[] { str1 });
      }
      if (!skipSeparator(true, !scanningInternalSubset())) {
        reportFatalError("MSG_SPACE_REQUIRED_BEFORE_NOTATION_NAME_IN_UNPARSED_ENTITYDECL", new Object[] { str1 });
      }
      str3 = this.fEntityScanner.scanName();
      if (str3 == null) {
        reportFatalError("MSG_NOTATION_NAME_REQUIRED_FOR_UNPARSED_ENTITYDECL", new Object[] { str1 });
      }
    }
    if (localObject == null)
    {
      scanEntityValue(this.fLiteral, this.fLiteral2);
      this.fStringBuffer.clear();
      this.fStringBuffer2.clear();
      this.fStringBuffer.append(this.fLiteral.ch, this.fLiteral.offset, this.fLiteral.length);
      this.fStringBuffer2.append(this.fLiteral2.ch, this.fLiteral2.offset, this.fLiteral2.length);
    }
    skipSeparator(false, !scanningInternalSubset());
    if (!this.fEntityScanner.skipChar(62)) {
      reportFatalError("EntityDeclUnterminated", new Object[] { str1 });
    }
    this.fMarkUpDepth -= 1;
    if (bool1) {
      str1 = "%" + str1;
    }
    if (localObject != null)
    {
      String str4 = this.fEntityScanner.getBaseSystemId();
      if (str3 != null) {
        this.fEntityManager.addUnparsedEntity(str1, str2, (String)localObject, str4, str3);
      } else {
        this.fEntityManager.addExternalEntity(str1, str2, (String)localObject, str4);
      }
      if (this.fDTDHandler != null)
      {
        this.fResourceIdentifier.setValues(str2, (String)localObject, str4, XMLEntityManager.expandSystemId((String)localObject, str4, false));
        if (str3 != null) {
          this.fDTDHandler.unparsedEntityDecl(str1, this.fResourceIdentifier, str3, null);
        } else {
          this.fDTDHandler.externalEntityDecl(str1, this.fResourceIdentifier, null);
        }
      }
    }
    else
    {
      this.fEntityManager.addInternalEntity(str1, this.fStringBuffer.toString());
      if (this.fDTDHandler != null) {
        this.fDTDHandler.internalEntityDecl(str1, this.fStringBuffer, this.fStringBuffer2, null);
      }
    }
    this.fReportEntity = true;
  }
  
  protected final void scanEntityValue(XMLString paramXMLString1, XMLString paramXMLString2)
    throws IOException, XNIException
  {
    int i = this.fEntityScanner.scanChar();
    if ((i != 39) && (i != 34)) {
      reportFatalError("OpenQuoteMissingInDecl", null);
    }
    int j = this.fEntityDepth;
    Object localObject1 = this.fString;
    Object localObject2 = this.fString;
    if (this.fEntityScanner.scanLiteral(i, this.fString) != i)
    {
      this.fStringBuffer.clear();
      this.fStringBuffer2.clear();
      do
      {
        this.fStringBuffer.append(this.fString);
        this.fStringBuffer2.append(this.fString);
        String str;
        if (this.fEntityScanner.skipChar(38))
        {
          if (this.fEntityScanner.skipChar(35))
          {
            this.fStringBuffer2.append("&#");
            scanCharReferenceValue(this.fStringBuffer, this.fStringBuffer2);
          }
          else
          {
            this.fStringBuffer.append('&');
            this.fStringBuffer2.append('&');
            str = this.fEntityScanner.scanName();
            if (str == null)
            {
              reportFatalError("NameRequiredInReference", null);
            }
            else
            {
              this.fStringBuffer.append(str);
              this.fStringBuffer2.append(str);
            }
            if (!this.fEntityScanner.skipChar(59))
            {
              reportFatalError("SemicolonRequiredInReference", new Object[] { str });
            }
            else
            {
              this.fStringBuffer.append(';');
              this.fStringBuffer2.append(';');
            }
          }
        }
        else if (this.fEntityScanner.skipChar(37))
        {
          do
          {
            this.fStringBuffer2.append('%');
            str = this.fEntityScanner.scanName();
            if (str == null)
            {
              reportFatalError("NameRequiredInPEReference", null);
            }
            else if (!this.fEntityScanner.skipChar(59))
            {
              reportFatalError("SemicolonRequiredInPEReference", new Object[] { str });
            }
            else
            {
              if (scanningInternalSubset()) {
                reportFatalError("PEReferenceWithinMarkup", new Object[] { str });
              }
              this.fStringBuffer2.append(str);
              this.fStringBuffer2.append(';');
            }
            startPE(str, true);
            this.fEntityScanner.skipSpaces();
          } while (this.fEntityScanner.skipChar(37));
        }
        else
        {
          int k = this.fEntityScanner.peekChar();
          if (XMLChar.isHighSurrogate(k))
          {
            scanSurrogates(this.fStringBuffer2);
          }
          else if (isInvalidLiteral(k))
          {
            reportFatalError("InvalidCharInLiteral", new Object[] { Integer.toHexString(k) });
            this.fEntityScanner.scanChar();
          }
          else if ((k != i) || (j != this.fEntityDepth))
          {
            this.fStringBuffer.append((char)k);
            this.fStringBuffer2.append((char)k);
            this.fEntityScanner.scanChar();
          }
        }
      } while (this.fEntityScanner.scanLiteral(i, this.fString) != i);
      this.fStringBuffer.append(this.fString);
      this.fStringBuffer2.append(this.fString);
      localObject1 = this.fStringBuffer;
      localObject2 = this.fStringBuffer2;
    }
    paramXMLString1.setValues((XMLString)localObject1);
    paramXMLString2.setValues((XMLString)localObject2);
    if (!this.fEntityScanner.skipChar(i)) {
      reportFatalError("CloseQuoteMissingInDecl", null);
    }
  }
  
  private final void scanNotationDecl()
    throws IOException, XNIException
  {
    this.fReportEntity = false;
    if (!skipSeparator(true, !scanningInternalSubset())) {
      reportFatalError("MSG_SPACE_REQUIRED_BEFORE_NOTATION_NAME_IN_NOTATIONDECL", null);
    }
    String str1 = null;
    if (this.fNamespaces) {
      str1 = this.fEntityScanner.scanNCName();
    } else {
      str1 = this.fEntityScanner.scanName();
    }
    if (str1 == null) {
      reportFatalError("MSG_NOTATION_NAME_REQUIRED_IN_NOTATIONDECL", null);
    }
    if (!skipSeparator(true, !scanningInternalSubset())) {
      if ((this.fNamespaces) && (this.fEntityScanner.peekChar() == 58))
      {
        this.fEntityScanner.scanChar();
        localObject = new XMLStringBuffer(str1);
        ((XMLStringBuffer)localObject).append(":");
        ((XMLStringBuffer)localObject).append(this.fEntityScanner.scanName());
        reportFatalError("ColonNotLegalWithNS", new Object[] { ((XMLString)localObject).toString() });
        skipSeparator(true, !scanningInternalSubset());
      }
      else
      {
        reportFatalError("MSG_SPACE_REQUIRED_AFTER_NOTATION_NAME_IN_NOTATIONDECL", new Object[] { str1 });
      }
    }
    scanExternalID(this.fStrings, true);
    Object localObject = this.fStrings[0];
    String str2 = this.fStrings[1];
    String str3 = this.fEntityScanner.getBaseSystemId();
    if ((localObject == null) && (str2 == null)) {
      reportFatalError("ExternalIDorPublicIDRequired", new Object[] { str1 });
    }
    skipSeparator(false, !scanningInternalSubset());
    if (!this.fEntityScanner.skipChar(62)) {
      reportFatalError("NotationDeclUnterminated", new Object[] { str1 });
    }
    this.fMarkUpDepth -= 1;
    if (this.fDTDHandler != null)
    {
      this.fResourceIdentifier.setValues(str2, (String)localObject, str3, XMLEntityManager.expandSystemId((String)localObject, str3, false));
      this.fDTDHandler.notationDecl(str1, this.fResourceIdentifier, null);
    }
    this.fReportEntity = true;
  }
  
  private final void scanConditionalSect(int paramInt)
    throws IOException, XNIException
  {
    this.fReportEntity = false;
    skipSeparator(false, !scanningInternalSubset());
    if (this.fEntityScanner.skipString("INCLUDE"))
    {
      skipSeparator(false, !scanningInternalSubset());
      if ((paramInt != this.fPEDepth) && (this.fValidation)) {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "INVALID_PE_IN_CONDITIONAL", new Object[] { this.fEntityManager.fCurrentEntity.name }, (short)1);
      }
      if (!this.fEntityScanner.skipChar(91)) {
        reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
      }
      if (this.fDTDHandler != null) {
        this.fDTDHandler.startConditional((short)0, null);
      }
      this.fIncludeSectDepth += 1;
      this.fReportEntity = true;
    }
    else
    {
      if (this.fEntityScanner.skipString("IGNORE"))
      {
        skipSeparator(false, !scanningInternalSubset());
        if ((paramInt != this.fPEDepth) && (this.fValidation)) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "INVALID_PE_IN_CONDITIONAL", new Object[] { this.fEntityManager.fCurrentEntity.name }, (short)1);
        }
        if (this.fDTDHandler != null) {
          this.fDTDHandler.startConditional((short)1, null);
        }
        if (!this.fEntityScanner.skipChar(91)) {
          reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
        }
        this.fReportEntity = true;
        int i = ++this.fIncludeSectDepth;
        if (this.fDTDHandler != null) {
          this.fIgnoreConditionalBuffer.clear();
        }
        for (;;)
        {
          if (this.fEntityScanner.skipChar(60))
          {
            if (this.fDTDHandler != null) {
              this.fIgnoreConditionalBuffer.append('<');
            }
            if (this.fEntityScanner.skipChar(33)) {
              if (this.fEntityScanner.skipChar(91))
              {
                if (this.fDTDHandler != null) {
                  this.fIgnoreConditionalBuffer.append("![");
                }
                this.fIncludeSectDepth += 1;
              }
              else if (this.fDTDHandler != null)
              {
                this.fIgnoreConditionalBuffer.append("!");
              }
            }
          }
          else if (this.fEntityScanner.skipChar(93))
          {
            if (this.fDTDHandler != null) {
              this.fIgnoreConditionalBuffer.append(']');
            }
            if (this.fEntityScanner.skipChar(93))
            {
              if (this.fDTDHandler != null) {
                this.fIgnoreConditionalBuffer.append(']');
              }
              while (this.fEntityScanner.skipChar(93)) {
                if (this.fDTDHandler != null) {
                  this.fIgnoreConditionalBuffer.append(']');
                }
              }
              if (this.fEntityScanner.skipChar(62))
              {
                if (this.fIncludeSectDepth-- == i)
                {
                  this.fMarkUpDepth -= 1;
                  if (this.fDTDHandler != null)
                  {
                    this.fLiteral.setValues(this.fIgnoreConditionalBuffer.ch, 0, this.fIgnoreConditionalBuffer.length - 2);
                    this.fDTDHandler.ignoredCharacters(this.fLiteral, null);
                    this.fDTDHandler.endConditional(null);
                  }
                  return;
                }
                if (this.fDTDHandler != null) {
                  this.fIgnoreConditionalBuffer.append('>');
                }
              }
            }
          }
          else
          {
            int j = this.fEntityScanner.scanChar();
            if (this.fScannerState == 0)
            {
              reportFatalError("IgnoreSectUnterminated", null);
              return;
            }
            if (this.fDTDHandler != null) {
              this.fIgnoreConditionalBuffer.append((char)j);
            }
          }
        }
      }
      reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
    }
  }
  
  protected final boolean scanDecls(boolean paramBoolean)
    throws IOException, XNIException
  {
    skipSeparator(false, true);
    boolean bool = true;
    while ((bool) && (this.fScannerState == 2))
    {
      bool = paramBoolean;
      if (this.fEntityScanner.skipChar(60))
      {
        this.fMarkUpDepth += 1;
        if (this.fEntityScanner.skipChar(63))
        {
          scanPI();
        }
        else if (this.fEntityScanner.skipChar(33))
        {
          if (this.fEntityScanner.skipChar(45))
          {
            if (!this.fEntityScanner.skipChar(45)) {
              reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
            } else {
              scanComment();
            }
          }
          else if (this.fEntityScanner.skipString("ELEMENT"))
          {
            scanElementDecl();
          }
          else if (this.fEntityScanner.skipString("ATTLIST"))
          {
            scanAttlistDecl();
          }
          else if (this.fEntityScanner.skipString("ENTITY"))
          {
            scanEntityDecl();
          }
          else if (this.fEntityScanner.skipString("NOTATION"))
          {
            scanNotationDecl();
          }
          else if ((this.fEntityScanner.skipChar(91)) && (!scanningInternalSubset()))
          {
            scanConditionalSect(this.fPEDepth);
          }
          else
          {
            this.fMarkUpDepth -= 1;
            reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
          }
        }
        else
        {
          this.fMarkUpDepth -= 1;
          reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
        }
      }
      else if ((this.fIncludeSectDepth > 0) && (this.fEntityScanner.skipChar(93)))
      {
        if ((!this.fEntityScanner.skipChar(93)) || (!this.fEntityScanner.skipChar(62))) {
          reportFatalError("IncludeSectUnterminated", null);
        }
        if (this.fDTDHandler != null) {
          this.fDTDHandler.endConditional(null);
        }
        this.fIncludeSectDepth -= 1;
        this.fMarkUpDepth -= 1;
      }
      else
      {
        if ((scanningInternalSubset()) && (this.fEntityScanner.peekChar() == 93)) {
          return false;
        }
        if (!this.fEntityScanner.skipSpaces())
        {
          reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
          int i;
          do
          {
            this.fEntityScanner.scanChar();
            skipSeparator(false, true);
            i = this.fEntityScanner.peekChar();
          } while ((i != 60) && (i != 93) && (!XMLChar.isSpace(i)));
        }
      }
      skipSeparator(false, true);
    }
    return this.fScannerState != 0;
  }
  
  private boolean skipSeparator(boolean paramBoolean1, boolean paramBoolean2)
    throws IOException, XNIException
  {
    int i = this.fPEDepth;
    boolean bool = this.fEntityScanner.skipSpaces();
    if ((!paramBoolean2) || (!this.fEntityScanner.skipChar(37))) {
      return (!paramBoolean1) || (bool) || (i != this.fPEDepth);
    }
    do
    {
      String str = this.fEntityScanner.scanName();
      if (str == null) {
        reportFatalError("NameRequiredInPEReference", null);
      } else if (!this.fEntityScanner.skipChar(59)) {
        reportFatalError("SemicolonRequiredInPEReference", new Object[] { str });
      }
      startPE(str, false);
      this.fEntityScanner.skipSpaces();
    } while (this.fEntityScanner.skipChar(37));
    return true;
  }
  
  private final void pushContentStack(int paramInt)
  {
    if (this.fContentStack.length == this.fContentDepth)
    {
      int[] arrayOfInt = new int[this.fContentDepth * 2];
      System.arraycopy(this.fContentStack, 0, arrayOfInt, 0, this.fContentDepth);
      this.fContentStack = arrayOfInt;
    }
    this.fContentStack[(this.fContentDepth++)] = paramInt;
  }
  
  private final int popContentStack()
  {
    return this.fContentStack[(--this.fContentDepth)];
  }
  
  private final void pushPEStack(int paramInt, boolean paramBoolean)
  {
    if (this.fPEStack.length == this.fPEDepth)
    {
      int[] arrayOfInt = new int[this.fPEDepth * 2];
      System.arraycopy(this.fPEStack, 0, arrayOfInt, 0, this.fPEDepth);
      this.fPEStack = arrayOfInt;
      boolean[] arrayOfBoolean = new boolean[this.fPEDepth * 2];
      System.arraycopy(this.fPEReport, 0, arrayOfBoolean, 0, this.fPEDepth);
      this.fPEReport = arrayOfBoolean;
    }
    this.fPEReport[this.fPEDepth] = paramBoolean;
    this.fPEStack[(this.fPEDepth++)] = paramInt;
  }
  
  private final int popPEStack()
  {
    return this.fPEStack[(--this.fPEDepth)];
  }
  
  private final boolean peekReportEntity()
  {
    return this.fPEReport[(this.fPEDepth - 1)];
  }
  
  private final void ensureEnumerationSize(int paramInt)
  {
    if (this.fEnumeration.length == paramInt)
    {
      String[] arrayOfString = new String[paramInt * 2];
      System.arraycopy(this.fEnumeration, 0, arrayOfString, 0, paramInt);
      this.fEnumeration = arrayOfString;
    }
  }
  
  private void init()
  {
    this.fStartDTDCalled = false;
    this.fExtEntityDepth = 0;
    this.fIncludeSectDepth = 0;
    this.fMarkUpDepth = 0;
    this.fPEDepth = 0;
    this.fStandalone = false;
    this.fSeenExternalDTD = false;
    this.fSeenPEReferences = false;
    setScannerState(1);
  }
  
  private int skipInvalidEnumerationValue()
    throws IOException
  {
    int i;
    do
    {
      i = this.fEntityScanner.scanChar();
    } while ((i != 124) && (i != 41));
    ensureEnumerationSize(this.fEnumerationCount + 1);
    this.fEnumeration[(this.fEnumerationCount++)] = XMLSymbols.EMPTY_STRING;
    return i;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.XMLDTDScannerImpl
 * JD-Core Version:    0.7.0.1
 */
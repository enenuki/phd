package org.apache.xerces.impl;

import java.io.IOException;
import org.apache.xerces.impl.dtd.XMLDTDValidatorFilter;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLAttributesImpl;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDocumentSource;

public class XML11NSDocumentScannerImpl
  extends XML11DocumentScannerImpl
{
  protected boolean fBindNamespaces;
  protected boolean fPerformValidation;
  private XMLDTDValidatorFilter fDTDValidator;
  private boolean fSawSpace;
  
  public void setDTDValidator(XMLDTDValidatorFilter paramXMLDTDValidatorFilter)
  {
    this.fDTDValidator = paramXMLDTDValidatorFilter;
  }
  
  protected boolean scanStartElement()
    throws IOException, XNIException
  {
    this.fEntityScanner.scanQName(this.fElementQName);
    String str1 = this.fElementQName.rawname;
    if (this.fBindNamespaces)
    {
      this.fNamespaceContext.pushContext();
      if ((this.fScannerState == 6) && (this.fPerformValidation))
      {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_GRAMMAR_NOT_FOUND", new Object[] { str1 }, (short)1);
        if ((this.fDoctypeName == null) || (!this.fDoctypeName.equals(str1))) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "RootElementTypeMustMatchDoctypedecl", new Object[] { this.fDoctypeName, str1 }, (short)1);
        }
      }
    }
    this.fCurrentElement = this.fElementStack.pushElement(this.fElementQName);
    boolean bool1 = false;
    this.fAttributes.removeAllAttributes();
    int i;
    for (;;)
    {
      boolean bool2 = this.fEntityScanner.skipSpaces();
      i = this.fEntityScanner.peekChar();
      if (i == 62)
      {
        this.fEntityScanner.scanChar();
        break;
      }
      if (i == 47)
      {
        this.fEntityScanner.scanChar();
        if (!this.fEntityScanner.skipChar(62)) {
          reportFatalError("ElementUnterminated", new Object[] { str1 });
        }
        bool1 = true;
        break;
      }
      if (((!isValidNameStartChar(i)) || (!bool2)) && ((!isValidNameStartHighSurrogate(i)) || (!bool2))) {
        reportFatalError("ElementUnterminated", new Object[] { str1 });
      }
      scanAttribute(this.fAttributes);
    }
    if (this.fBindNamespaces)
    {
      if (this.fElementQName.prefix == XMLSymbols.PREFIX_XMLNS) {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementXMLNSPrefix", new Object[] { this.fElementQName.rawname }, (short)2);
      }
      String str2 = this.fElementQName.prefix != null ? this.fElementQName.prefix : XMLSymbols.EMPTY_STRING;
      this.fElementQName.uri = this.fNamespaceContext.getURI(str2);
      this.fCurrentElement.uri = this.fElementQName.uri;
      if ((this.fElementQName.prefix == null) && (this.fElementQName.uri != null))
      {
        this.fElementQName.prefix = XMLSymbols.EMPTY_STRING;
        this.fCurrentElement.prefix = XMLSymbols.EMPTY_STRING;
      }
      if ((this.fElementQName.prefix != null) && (this.fElementQName.uri == null)) {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementPrefixUnbound", new Object[] { this.fElementQName.prefix, this.fElementQName.rawname }, (short)2);
      }
      i = this.fAttributes.getLength();
      Object localObject;
      for (int j = 0; j < i; j++)
      {
        this.fAttributes.getName(j, this.fAttributeQName);
        localObject = this.fAttributeQName.prefix != null ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
        String str3 = this.fNamespaceContext.getURI((String)localObject);
        if (((this.fAttributeQName.uri == null) || (this.fAttributeQName.uri != str3)) && (localObject != XMLSymbols.EMPTY_STRING))
        {
          this.fAttributeQName.uri = str3;
          if (str3 == null) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributePrefixUnbound", new Object[] { this.fElementQName.rawname, this.fAttributeQName.rawname, localObject }, (short)2);
          }
          this.fAttributes.setURI(j, str3);
        }
      }
      if (i > 1)
      {
        localObject = this.fAttributes.checkDuplicatesNS();
        if (localObject != null) {
          if (((QName)localObject).uri != null) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributeNSNotUnique", new Object[] { this.fElementQName.rawname, ((QName)localObject).localpart, ((QName)localObject).uri }, (short)2);
          } else {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributeNotUnique", new Object[] { this.fElementQName.rawname, ((QName)localObject).rawname }, (short)2);
          }
        }
      }
    }
    if (this.fDocumentHandler != null) {
      if (bool1)
      {
        this.fMarkupDepth -= 1;
        if (this.fMarkupDepth < this.fEntityStack[(this.fEntityDepth - 1)]) {
          reportFatalError("ElementEntityMismatch", new Object[] { this.fCurrentElement.rawname });
        }
        this.fDocumentHandler.emptyElement(this.fElementQName, this.fAttributes, null);
        if (this.fBindNamespaces) {
          this.fNamespaceContext.popContext();
        }
        this.fElementStack.popElement(this.fElementQName);
      }
      else
      {
        this.fDocumentHandler.startElement(this.fElementQName, this.fAttributes, null);
      }
    }
    return bool1;
  }
  
  protected void scanStartElementName()
    throws IOException, XNIException
  {
    this.fEntityScanner.scanQName(this.fElementQName);
    this.fSawSpace = this.fEntityScanner.skipSpaces();
  }
  
  protected boolean scanStartElementAfterName()
    throws IOException, XNIException
  {
    String str1 = this.fElementQName.rawname;
    if (this.fBindNamespaces)
    {
      this.fNamespaceContext.pushContext();
      if ((this.fScannerState == 6) && (this.fPerformValidation))
      {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_GRAMMAR_NOT_FOUND", new Object[] { str1 }, (short)1);
        if ((this.fDoctypeName == null) || (!this.fDoctypeName.equals(str1))) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "RootElementTypeMustMatchDoctypedecl", new Object[] { this.fDoctypeName, str1 }, (short)1);
        }
      }
    }
    this.fCurrentElement = this.fElementStack.pushElement(this.fElementQName);
    boolean bool = false;
    this.fAttributes.removeAllAttributes();
    for (;;)
    {
      int i = this.fEntityScanner.peekChar();
      if (i == 62)
      {
        this.fEntityScanner.scanChar();
        break;
      }
      if (i == 47)
      {
        this.fEntityScanner.scanChar();
        if (!this.fEntityScanner.skipChar(62)) {
          reportFatalError("ElementUnterminated", new Object[] { str1 });
        }
        bool = true;
        break;
      }
      if (((!isValidNameStartChar(i)) || (!this.fSawSpace)) && ((!isValidNameStartHighSurrogate(i)) || (!this.fSawSpace))) {
        reportFatalError("ElementUnterminated", new Object[] { str1 });
      }
      scanAttribute(this.fAttributes);
      this.fSawSpace = this.fEntityScanner.skipSpaces();
    }
    if (this.fBindNamespaces)
    {
      if (this.fElementQName.prefix == XMLSymbols.PREFIX_XMLNS) {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementXMLNSPrefix", new Object[] { this.fElementQName.rawname }, (short)2);
      }
      String str2 = this.fElementQName.prefix != null ? this.fElementQName.prefix : XMLSymbols.EMPTY_STRING;
      this.fElementQName.uri = this.fNamespaceContext.getURI(str2);
      this.fCurrentElement.uri = this.fElementQName.uri;
      if ((this.fElementQName.prefix == null) && (this.fElementQName.uri != null))
      {
        this.fElementQName.prefix = XMLSymbols.EMPTY_STRING;
        this.fCurrentElement.prefix = XMLSymbols.EMPTY_STRING;
      }
      if ((this.fElementQName.prefix != null) && (this.fElementQName.uri == null)) {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementPrefixUnbound", new Object[] { this.fElementQName.prefix, this.fElementQName.rawname }, (short)2);
      }
      int j = this.fAttributes.getLength();
      Object localObject;
      for (int k = 0; k < j; k++)
      {
        this.fAttributes.getName(k, this.fAttributeQName);
        localObject = this.fAttributeQName.prefix != null ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
        String str3 = this.fNamespaceContext.getURI((String)localObject);
        if (((this.fAttributeQName.uri == null) || (this.fAttributeQName.uri != str3)) && (localObject != XMLSymbols.EMPTY_STRING))
        {
          this.fAttributeQName.uri = str3;
          if (str3 == null) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributePrefixUnbound", new Object[] { this.fElementQName.rawname, this.fAttributeQName.rawname, localObject }, (short)2);
          }
          this.fAttributes.setURI(k, str3);
        }
      }
      if (j > 1)
      {
        localObject = this.fAttributes.checkDuplicatesNS();
        if (localObject != null) {
          if (((QName)localObject).uri != null) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributeNSNotUnique", new Object[] { this.fElementQName.rawname, ((QName)localObject).localpart, ((QName)localObject).uri }, (short)2);
          } else {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributeNotUnique", new Object[] { this.fElementQName.rawname, ((QName)localObject).rawname }, (short)2);
          }
        }
      }
    }
    if (this.fDocumentHandler != null) {
      if (bool)
      {
        this.fMarkupDepth -= 1;
        if (this.fMarkupDepth < this.fEntityStack[(this.fEntityDepth - 1)]) {
          reportFatalError("ElementEntityMismatch", new Object[] { this.fCurrentElement.rawname });
        }
        this.fDocumentHandler.emptyElement(this.fElementQName, this.fAttributes, null);
        if (this.fBindNamespaces) {
          this.fNamespaceContext.popContext();
        }
        this.fElementStack.popElement(this.fElementQName);
      }
      else
      {
        this.fDocumentHandler.startElement(this.fElementQName, this.fAttributes, null);
      }
    }
    return bool;
  }
  
  protected void scanAttribute(XMLAttributesImpl paramXMLAttributesImpl)
    throws IOException, XNIException
  {
    this.fEntityScanner.scanQName(this.fAttributeQName);
    this.fEntityScanner.skipSpaces();
    if (!this.fEntityScanner.skipChar(61)) {
      reportFatalError("EqRequiredInAttribute", new Object[] { this.fCurrentElement.rawname, this.fAttributeQName.rawname });
    }
    this.fEntityScanner.skipSpaces();
    int i;
    if (this.fBindNamespaces)
    {
      i = paramXMLAttributesImpl.getLength();
      paramXMLAttributesImpl.addAttributeNS(this.fAttributeQName, XMLSymbols.fCDATASymbol, null);
    }
    else
    {
      int j = paramXMLAttributesImpl.getLength();
      i = paramXMLAttributesImpl.addAttribute(this.fAttributeQName, XMLSymbols.fCDATASymbol, null);
      if (j == paramXMLAttributesImpl.getLength()) {
        reportFatalError("AttributeNotUnique", new Object[] { this.fCurrentElement.rawname, this.fAttributeQName.rawname });
      }
    }
    boolean bool = scanAttributeValue(this.fTempString, this.fTempString2, this.fAttributeQName.rawname, this.fIsEntityDeclaredVC, this.fCurrentElement.rawname);
    String str1 = this.fTempString.toString();
    paramXMLAttributesImpl.setValue(i, str1);
    if (!bool) {
      paramXMLAttributesImpl.setNonNormalizedValue(i, this.fTempString2.toString());
    }
    paramXMLAttributesImpl.setSpecified(i, true);
    if (this.fBindNamespaces)
    {
      String str2 = this.fAttributeQName.localpart;
      String str3 = this.fAttributeQName.prefix != null ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
      if ((str3 == XMLSymbols.PREFIX_XMLNS) || ((str3 == XMLSymbols.EMPTY_STRING) && (str2 == XMLSymbols.PREFIX_XMLNS)))
      {
        String str4 = this.fSymbolTable.addSymbol(str1);
        if ((str3 == XMLSymbols.PREFIX_XMLNS) && (str2 == XMLSymbols.PREFIX_XMLNS)) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXMLNS", new Object[] { this.fAttributeQName }, (short)2);
        }
        if (str4 == NamespaceContext.XMLNS_URI) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXMLNS", new Object[] { this.fAttributeQName }, (short)2);
        }
        if (str2 == XMLSymbols.PREFIX_XML)
        {
          if (str4 != NamespaceContext.XML_URI) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXML", new Object[] { this.fAttributeQName }, (short)2);
          }
        }
        else if (str4 == NamespaceContext.XML_URI) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXML", new Object[] { this.fAttributeQName }, (short)2);
        }
        str3 = str2 != XMLSymbols.PREFIX_XMLNS ? str2 : XMLSymbols.EMPTY_STRING;
        this.fNamespaceContext.declarePrefix(str3, str4.length() != 0 ? str4 : null);
        paramXMLAttributesImpl.setURI(i, this.fNamespaceContext.getURI(XMLSymbols.PREFIX_XMLNS));
      }
      else if (this.fAttributeQName.prefix != null)
      {
        paramXMLAttributesImpl.setURI(i, this.fNamespaceContext.getURI(this.fAttributeQName.prefix));
      }
    }
  }
  
  protected int scanEndElement()
    throws IOException, XNIException
  {
    this.fElementStack.popElement(this.fElementQName);
    if (!this.fEntityScanner.skipString(this.fElementQName.rawname)) {
      reportFatalError("ETagRequired", new Object[] { this.fElementQName.rawname });
    }
    this.fEntityScanner.skipSpaces();
    if (!this.fEntityScanner.skipChar(62)) {
      reportFatalError("ETagUnterminated", new Object[] { this.fElementQName.rawname });
    }
    this.fMarkupDepth -= 1;
    this.fMarkupDepth -= 1;
    if (this.fMarkupDepth < this.fEntityStack[(this.fEntityDepth - 1)]) {
      reportFatalError("ElementEntityMismatch", new Object[] { this.fCurrentElement.rawname });
    }
    if (this.fDocumentHandler != null)
    {
      this.fDocumentHandler.endElement(this.fElementQName, null);
      if (this.fBindNamespaces) {
        this.fNamespaceContext.popContext();
      }
    }
    return this.fMarkupDepth;
  }
  
  public void reset(XMLComponentManager paramXMLComponentManager)
    throws XMLConfigurationException
  {
    super.reset(paramXMLComponentManager);
    this.fPerformValidation = false;
    this.fBindNamespaces = false;
  }
  
  protected XMLDocumentFragmentScannerImpl.Dispatcher createContentDispatcher()
  {
    return new NS11ContentDispatcher();
  }
  
  protected final class NS11ContentDispatcher
    extends XMLDocumentScannerImpl.ContentDispatcher
  {
    protected NS11ContentDispatcher()
    {
      super();
    }
    
    protected boolean scanRootElementHook()
      throws IOException, XNIException
    {
      if ((XML11NSDocumentScannerImpl.this.fExternalSubsetResolver != null) && (!XML11NSDocumentScannerImpl.this.fSeenDoctypeDecl) && (!XML11NSDocumentScannerImpl.this.fDisallowDoctype) && ((XML11NSDocumentScannerImpl.this.fValidation) || (XML11NSDocumentScannerImpl.this.fLoadExternalDTD)))
      {
        XML11NSDocumentScannerImpl.this.scanStartElementName();
        resolveExternalSubsetAndRead();
        reconfigurePipeline();
        if (XML11NSDocumentScannerImpl.this.scanStartElementAfterName())
        {
          XML11NSDocumentScannerImpl.this.setScannerState(12);
          XML11NSDocumentScannerImpl.this.setDispatcher(XML11NSDocumentScannerImpl.this.fTrailingMiscDispatcher);
          return true;
        }
      }
      else
      {
        reconfigurePipeline();
        if (XML11NSDocumentScannerImpl.this.scanStartElement())
        {
          XML11NSDocumentScannerImpl.this.setScannerState(12);
          XML11NSDocumentScannerImpl.this.setDispatcher(XML11NSDocumentScannerImpl.this.fTrailingMiscDispatcher);
          return true;
        }
      }
      return false;
    }
    
    private void reconfigurePipeline()
    {
      if (XML11NSDocumentScannerImpl.this.fDTDValidator == null)
      {
        XML11NSDocumentScannerImpl.this.fBindNamespaces = true;
      }
      else if (!XML11NSDocumentScannerImpl.this.fDTDValidator.hasGrammar())
      {
        XML11NSDocumentScannerImpl.this.fBindNamespaces = true;
        XML11NSDocumentScannerImpl.this.fPerformValidation = XML11NSDocumentScannerImpl.this.fDTDValidator.validate();
        XMLDocumentSource localXMLDocumentSource = XML11NSDocumentScannerImpl.this.fDTDValidator.getDocumentSource();
        XMLDocumentHandler localXMLDocumentHandler = XML11NSDocumentScannerImpl.this.fDTDValidator.getDocumentHandler();
        localXMLDocumentSource.setDocumentHandler(localXMLDocumentHandler);
        if (localXMLDocumentHandler != null) {
          localXMLDocumentHandler.setDocumentSource(localXMLDocumentSource);
        }
        XML11NSDocumentScannerImpl.this.fDTDValidator.setDocumentSource(null);
        XML11NSDocumentScannerImpl.this.fDTDValidator.setDocumentHandler(null);
      }
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.XML11NSDocumentScannerImpl
 * JD-Core Version:    0.7.0.1
 */
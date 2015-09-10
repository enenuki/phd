package org.apache.xerces.impl;

import java.io.IOException;
import org.apache.xerces.util.XML11Char;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLStringBuffer;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;

public class XML11DocumentScannerImpl
  extends XMLDocumentScannerImpl
{
  private final XMLString fString = new XMLString();
  private final XMLStringBuffer fStringBuffer = new XMLStringBuffer();
  private final XMLStringBuffer fStringBuffer2 = new XMLStringBuffer();
  private final XMLStringBuffer fStringBuffer3 = new XMLStringBuffer();
  
  protected int scanContent()
    throws IOException, XNIException
  {
    Object localObject = this.fString;
    int i = this.fEntityScanner.scanContent((XMLString)localObject);
    if ((i == 13) || (i == 133) || (i == 8232))
    {
      this.fEntityScanner.scanChar();
      this.fStringBuffer.clear();
      this.fStringBuffer.append(this.fString);
      this.fStringBuffer.append((char)i);
      localObject = this.fStringBuffer;
      i = -1;
    }
    if ((this.fDocumentHandler != null) && (((XMLString)localObject).length > 0)) {
      this.fDocumentHandler.characters((XMLString)localObject, null);
    }
    if ((i == 93) && (this.fString.length == 0))
    {
      this.fStringBuffer.clear();
      this.fStringBuffer.append((char)this.fEntityScanner.scanChar());
      this.fInScanContent = true;
      if (this.fEntityScanner.skipChar(93))
      {
        this.fStringBuffer.append(']');
        while (this.fEntityScanner.skipChar(93)) {
          this.fStringBuffer.append(']');
        }
        if (this.fEntityScanner.skipChar(62)) {
          reportFatalError("CDEndInContent", null);
        }
      }
      if ((this.fDocumentHandler != null) && (this.fStringBuffer.length != 0)) {
        this.fDocumentHandler.characters(this.fStringBuffer, null);
      }
      this.fInScanContent = false;
      i = -1;
    }
    return i;
  }
  
  protected boolean scanAttributeValue(XMLString paramXMLString1, XMLString paramXMLString2, String paramString1, boolean paramBoolean, String paramString2)
    throws IOException, XNIException
  {
    int i = this.fEntityScanner.peekChar();
    if ((i != 39) && (i != 34)) {
      reportFatalError("OpenQuoteExpected", new Object[] { paramString2, paramString1 });
    }
    this.fEntityScanner.scanChar();
    int j = this.fEntityDepth;
    int k = this.fEntityScanner.scanLiteral(i, paramXMLString1);
    int m = 0;
    int n;
    if ((k == i) && ((m = isUnchangedByNormalization(paramXMLString1)) == -1))
    {
      paramXMLString2.setValues(paramXMLString1);
      n = this.fEntityScanner.scanChar();
      if (n != i) {
        reportFatalError("CloseQuoteExpected", new Object[] { paramString2, paramString1 });
      }
      return true;
    }
    this.fStringBuffer2.clear();
    this.fStringBuffer2.append(paramXMLString1);
    normalizeWhitespace(paramXMLString1, m);
    if (k != i)
    {
      this.fScanningAttribute = true;
      this.fStringBuffer.clear();
      do
      {
        this.fStringBuffer.append(paramXMLString1);
        if (k == 38)
        {
          this.fEntityScanner.skipChar(38);
          if (j == this.fEntityDepth) {
            this.fStringBuffer2.append('&');
          }
          if (this.fEntityScanner.skipChar(35))
          {
            if (j == this.fEntityDepth) {
              this.fStringBuffer2.append('#');
            }
            n = scanCharReferenceValue(this.fStringBuffer, this.fStringBuffer2);
            if (n == -1) {}
          }
          else
          {
            String str = this.fEntityScanner.scanName();
            if (str == null) {
              reportFatalError("NameRequiredInReference", null);
            } else if (j == this.fEntityDepth) {
              this.fStringBuffer2.append(str);
            }
            if (!this.fEntityScanner.skipChar(59)) {
              reportFatalError("SemicolonRequiredInReference", new Object[] { str });
            } else if (j == this.fEntityDepth) {
              this.fStringBuffer2.append(';');
            }
            if (str == XMLScanner.fAmpSymbol)
            {
              this.fStringBuffer.append('&');
            }
            else if (str == XMLScanner.fAposSymbol)
            {
              this.fStringBuffer.append('\'');
            }
            else if (str == XMLScanner.fLtSymbol)
            {
              this.fStringBuffer.append('<');
            }
            else if (str == XMLScanner.fGtSymbol)
            {
              this.fStringBuffer.append('>');
            }
            else if (str == XMLScanner.fQuotSymbol)
            {
              this.fStringBuffer.append('"');
            }
            else if (this.fEntityManager.isExternalEntity(str))
            {
              reportFatalError("ReferenceToExternalEntity", new Object[] { str });
            }
            else
            {
              if (!this.fEntityManager.isDeclaredEntity(str)) {
                if (paramBoolean)
                {
                  if (this.fValidation) {
                    this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EntityNotDeclared", new Object[] { str }, (short)1);
                  }
                }
                else {
                  reportFatalError("EntityNotDeclared", new Object[] { str });
                }
              }
              this.fEntityManager.startEntity(str, true);
            }
          }
        }
        else if (k == 60)
        {
          reportFatalError("LessthanInAttValue", new Object[] { paramString2, paramString1 });
          this.fEntityScanner.scanChar();
          if (j == this.fEntityDepth) {
            this.fStringBuffer2.append((char)k);
          }
        }
        else if ((k == 37) || (k == 93))
        {
          this.fEntityScanner.scanChar();
          this.fStringBuffer.append((char)k);
          if (j == this.fEntityDepth) {
            this.fStringBuffer2.append((char)k);
          }
        }
        else if ((k == 10) || (k == 13) || (k == 133) || (k == 8232))
        {
          this.fEntityScanner.scanChar();
          this.fStringBuffer.append(' ');
          if (j == this.fEntityDepth) {
            this.fStringBuffer2.append('\n');
          }
        }
        else if ((k != -1) && (XMLChar.isHighSurrogate(k)))
        {
          this.fStringBuffer3.clear();
          if (scanSurrogates(this.fStringBuffer3))
          {
            this.fStringBuffer.append(this.fStringBuffer3);
            if (j == this.fEntityDepth) {
              this.fStringBuffer2.append(this.fStringBuffer3);
            }
          }
        }
        else if ((k != -1) && (isInvalidLiteral(k)))
        {
          reportFatalError("InvalidCharInAttValue", new Object[] { paramString2, paramString1, Integer.toString(k, 16) });
          this.fEntityScanner.scanChar();
          if (j == this.fEntityDepth) {
            this.fStringBuffer2.append((char)k);
          }
        }
        k = this.fEntityScanner.scanLiteral(i, paramXMLString1);
        if (j == this.fEntityDepth) {
          this.fStringBuffer2.append(paramXMLString1);
        }
        normalizeWhitespace(paramXMLString1);
      } while ((k != i) || (j != this.fEntityDepth));
      this.fStringBuffer.append(paramXMLString1);
      paramXMLString1.setValues(this.fStringBuffer);
      this.fScanningAttribute = false;
    }
    paramXMLString2.setValues(this.fStringBuffer2);
    int i1 = this.fEntityScanner.scanChar();
    if (i1 != i) {
      reportFatalError("CloseQuoteExpected", new Object[] { paramString2, paramString1 });
    }
    return paramXMLString2.equals(paramXMLString1.ch, paramXMLString1.offset, paramXMLString1.length);
  }
  
  protected boolean scanPubidLiteral(XMLString paramXMLString)
    throws IOException, XNIException
  {
    int i = this.fEntityScanner.scanChar();
    if ((i != 39) && (i != 34))
    {
      reportFatalError("QuoteRequiredInPublicID", null);
      return false;
    }
    this.fStringBuffer.clear();
    int j = 1;
    boolean bool = true;
    for (;;)
    {
      int k = this.fEntityScanner.scanChar();
      if ((k == 32) || (k == 10) || (k == 13) || (k == 133) || (k == 8232))
      {
        if (j == 0)
        {
          this.fStringBuffer.append(' ');
          j = 1;
        }
      }
      else
      {
        if (k == i)
        {
          if (j != 0) {
            this.fStringBuffer.length -= 1;
          }
          paramXMLString.setValues(this.fStringBuffer);
          break;
        }
        if (XMLChar.isPubid(k))
        {
          this.fStringBuffer.append((char)k);
          j = 0;
        }
        else
        {
          if (k == -1)
          {
            reportFatalError("PublicIDUnterminated", null);
            return false;
          }
          bool = false;
          reportFatalError("InvalidCharInPublicID", new Object[] { Integer.toHexString(k) });
        }
      }
    }
    return bool;
  }
  
  protected void normalizeWhitespace(XMLString paramXMLString)
  {
    int i = paramXMLString.offset + paramXMLString.length;
    for (int j = paramXMLString.offset; j < i; j++)
    {
      int k = paramXMLString.ch[j];
      if (XMLChar.isSpace(k)) {
        paramXMLString.ch[j] = ' ';
      }
    }
  }
  
  protected void normalizeWhitespace(XMLString paramXMLString, int paramInt)
  {
    int i = paramXMLString.offset + paramXMLString.length;
    for (int j = paramXMLString.offset + paramInt; j < i; j++)
    {
      int k = paramXMLString.ch[j];
      if (XMLChar.isSpace(k)) {
        paramXMLString.ch[j] = ' ';
      }
    }
  }
  
  protected int isUnchangedByNormalization(XMLString paramXMLString)
  {
    int i = paramXMLString.offset + paramXMLString.length;
    for (int j = paramXMLString.offset; j < i; j++)
    {
      int k = paramXMLString.ch[j];
      if (XMLChar.isSpace(k)) {
        return j - paramXMLString.offset;
      }
    }
    return -1;
  }
  
  protected boolean isInvalid(int paramInt)
  {
    return XML11Char.isXML11Invalid(paramInt);
  }
  
  protected boolean isInvalidLiteral(int paramInt)
  {
    return !XML11Char.isXML11ValidLiteral(paramInt);
  }
  
  protected boolean isValidNameChar(int paramInt)
  {
    return XML11Char.isXML11Name(paramInt);
  }
  
  protected boolean isValidNameStartChar(int paramInt)
  {
    return XML11Char.isXML11NameStart(paramInt);
  }
  
  protected boolean isValidNCName(int paramInt)
  {
    return XML11Char.isXML11NCName(paramInt);
  }
  
  protected boolean isValidNameStartHighSurrogate(int paramInt)
  {
    return XML11Char.isXML11NameHighSurrogate(paramInt);
  }
  
  protected boolean versionSupported(String paramString)
  {
    return (paramString.equals("1.1")) || (paramString.equals("1.0"));
  }
  
  protected String getVersionNotSupportedKey()
  {
    return "VersionNotSupported11";
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.XML11DocumentScannerImpl
 * JD-Core Version:    0.7.0.1
 */
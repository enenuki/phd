package org.apache.xerces.impl;

import java.io.IOException;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XML11Char;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLStringBuffer;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;

public class XML11DTDScannerImpl
  extends XMLDTDScannerImpl
{
  private final XMLStringBuffer fStringBuffer = new XMLStringBuffer();
  
  public XML11DTDScannerImpl() {}
  
  public XML11DTDScannerImpl(SymbolTable paramSymbolTable, XMLErrorReporter paramXMLErrorReporter, XMLEntityManager paramXMLEntityManager)
  {
    super(paramSymbolTable, paramXMLErrorReporter, paramXMLEntityManager);
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
    return !XML11Char.isXML11Valid(paramInt);
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
 * Qualified Name:     org.apache.xerces.impl.XML11DTDScannerImpl
 * JD-Core Version:    0.7.0.1
 */
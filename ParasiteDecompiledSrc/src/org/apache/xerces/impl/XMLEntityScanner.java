package org.apache.xerces.impl;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.Locale;
import org.apache.xerces.impl.io.UCSReader;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLStringBuffer;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;

public class XMLEntityScanner
  implements XMLLocator
{
  private static final boolean DEBUG_ENCODINGS = false;
  private static final boolean DEBUG_BUFFER = false;
  private static final EOFException END_OF_DOCUMENT_ENTITY = new EOFException()
  {
    private static final long serialVersionUID = 980337771224675268L;
    
    public Throwable fillInStackTrace()
    {
      return this;
    }
  };
  private XMLEntityManager fEntityManager = null;
  protected XMLEntityManager.ScannedEntity fCurrentEntity = null;
  protected SymbolTable fSymbolTable = null;
  protected int fBufferSize = 2048;
  protected XMLErrorReporter fErrorReporter;
  
  public final String getBaseSystemId()
  {
    return (this.fCurrentEntity != null) && (this.fCurrentEntity.entityLocation != null) ? this.fCurrentEntity.entityLocation.getExpandedSystemId() : null;
  }
  
  public final void setEncoding(String paramString)
    throws IOException
  {
    if ((this.fCurrentEntity.stream != null) && ((this.fCurrentEntity.encoding == null) || (!this.fCurrentEntity.encoding.equals(paramString))))
    {
      if ((this.fCurrentEntity.encoding != null) && (this.fCurrentEntity.encoding.startsWith("UTF-16")))
      {
        String str = paramString.toUpperCase(Locale.ENGLISH);
        if (str.equals("UTF-16")) {
          return;
        }
        if (str.equals("ISO-10646-UCS-4"))
        {
          if (this.fCurrentEntity.encoding.equals("UTF-16BE")) {
            this.fCurrentEntity.reader = new UCSReader(this.fCurrentEntity.stream, (short)8);
          } else {
            this.fCurrentEntity.reader = new UCSReader(this.fCurrentEntity.stream, (short)4);
          }
          return;
        }
        if (str.equals("ISO-10646-UCS-2"))
        {
          if (this.fCurrentEntity.encoding.equals("UTF-16BE")) {
            this.fCurrentEntity.reader = new UCSReader(this.fCurrentEntity.stream, (short)2);
          } else {
            this.fCurrentEntity.reader = new UCSReader(this.fCurrentEntity.stream, (short)1);
          }
          return;
        }
      }
      this.fCurrentEntity.setReader(this.fCurrentEntity.stream, paramString, null);
      this.fCurrentEntity.encoding = paramString;
    }
  }
  
  public final void setXMLVersion(String paramString)
  {
    this.fCurrentEntity.xmlVersion = paramString;
  }
  
  public final boolean isExternal()
  {
    return this.fCurrentEntity.isExternal();
  }
  
  public int peekChar()
    throws IOException
  {
    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
      load(0, true);
    }
    int i = this.fCurrentEntity.ch[this.fCurrentEntity.position];
    if (this.fCurrentEntity.isExternal()) {
      return i != 13 ? i : 10;
    }
    return i;
  }
  
  public int scanChar()
    throws IOException
  {
    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
      load(0, true);
    }
    int i = this.fCurrentEntity.ch[(this.fCurrentEntity.position++)];
    boolean bool = false;
    if ((i == 10) || ((i == 13) && ((bool = this.fCurrentEntity.isExternal()))))
    {
      this.fCurrentEntity.lineNumber += 1;
      this.fCurrentEntity.columnNumber = 1;
      if (this.fCurrentEntity.position == this.fCurrentEntity.count)
      {
        this.fCurrentEntity.ch[0] = ((char)i);
        load(1, false);
      }
      if ((i == 13) && (bool))
      {
        if (this.fCurrentEntity.ch[(this.fCurrentEntity.position++)] != '\n') {
          this.fCurrentEntity.position -= 1;
        }
        i = 10;
      }
    }
    this.fCurrentEntity.columnNumber += 1;
    return i;
  }
  
  public String scanNmtoken()
    throws IOException
  {
    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
      load(0, true);
    }
    int i = this.fCurrentEntity.position;
    while (XMLChar.isName(this.fCurrentEntity.ch[this.fCurrentEntity.position])) {
      if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
      {
        j = this.fCurrentEntity.position - i;
        if (j == this.fCurrentEntity.ch.length)
        {
          localObject = new char[this.fCurrentEntity.ch.length << 1];
          System.arraycopy(this.fCurrentEntity.ch, i, localObject, 0, j);
          this.fCurrentEntity.ch = ((char[])localObject);
        }
        else
        {
          System.arraycopy(this.fCurrentEntity.ch, i, this.fCurrentEntity.ch, 0, j);
        }
        i = 0;
        if (load(j, false)) {
          break;
        }
      }
    }
    int j = this.fCurrentEntity.position - i;
    this.fCurrentEntity.columnNumber += j;
    Object localObject = null;
    if (j > 0) {
      localObject = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i, j);
    }
    return localObject;
  }
  
  public String scanName()
    throws IOException
  {
    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
      load(0, true);
    }
    int i = this.fCurrentEntity.position;
    if (XMLChar.isNameStart(this.fCurrentEntity.ch[i]))
    {
      if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
      {
        this.fCurrentEntity.ch[0] = this.fCurrentEntity.ch[i];
        i = 0;
        if (load(1, false))
        {
          this.fCurrentEntity.columnNumber += 1;
          str = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 1);
          return str;
        }
      }
      while (XMLChar.isName(this.fCurrentEntity.ch[this.fCurrentEntity.position]))
      {
        String str;
        if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
        {
          j = this.fCurrentEntity.position - i;
          if (j == this.fCurrentEntity.ch.length)
          {
            localObject = new char[this.fCurrentEntity.ch.length << 1];
            System.arraycopy(this.fCurrentEntity.ch, i, localObject, 0, j);
            this.fCurrentEntity.ch = ((char[])localObject);
          }
          else
          {
            System.arraycopy(this.fCurrentEntity.ch, i, this.fCurrentEntity.ch, 0, j);
          }
          i = 0;
          if (load(j, false)) {
            break;
          }
        }
      }
    }
    int j = this.fCurrentEntity.position - i;
    this.fCurrentEntity.columnNumber += j;
    Object localObject = null;
    if (j > 0) {
      localObject = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i, j);
    }
    return localObject;
  }
  
  public String scanNCName()
    throws IOException
  {
    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
      load(0, true);
    }
    int i = this.fCurrentEntity.position;
    if (XMLChar.isNCNameStart(this.fCurrentEntity.ch[i]))
    {
      if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
      {
        this.fCurrentEntity.ch[0] = this.fCurrentEntity.ch[i];
        i = 0;
        if (load(1, false))
        {
          this.fCurrentEntity.columnNumber += 1;
          str = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 1);
          return str;
        }
      }
      while (XMLChar.isNCName(this.fCurrentEntity.ch[this.fCurrentEntity.position]))
      {
        String str;
        if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
        {
          j = this.fCurrentEntity.position - i;
          if (j == this.fCurrentEntity.ch.length)
          {
            localObject = new char[this.fCurrentEntity.ch.length << 1];
            System.arraycopy(this.fCurrentEntity.ch, i, localObject, 0, j);
            this.fCurrentEntity.ch = ((char[])localObject);
          }
          else
          {
            System.arraycopy(this.fCurrentEntity.ch, i, this.fCurrentEntity.ch, 0, j);
          }
          i = 0;
          if (load(j, false)) {
            break;
          }
        }
      }
    }
    int j = this.fCurrentEntity.position - i;
    this.fCurrentEntity.columnNumber += j;
    Object localObject = null;
    if (j > 0) {
      localObject = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i, j);
    }
    return localObject;
  }
  
  public boolean scanQName(QName paramQName)
    throws IOException
  {
    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
      load(0, true);
    }
    int i = this.fCurrentEntity.position;
    if (XMLChar.isNCNameStart(this.fCurrentEntity.ch[i]))
    {
      if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
      {
        this.fCurrentEntity.ch[0] = this.fCurrentEntity.ch[i];
        i = 0;
        if (load(1, false))
        {
          this.fCurrentEntity.columnNumber += 1;
          String str1 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 1);
          paramQName.setValues(null, str1, str1, null);
          return true;
        }
      }
      int j = -1;
      Object localObject;
      while (XMLChar.isName(this.fCurrentEntity.ch[this.fCurrentEntity.position]))
      {
        k = this.fCurrentEntity.ch[this.fCurrentEntity.position];
        if (k == 58)
        {
          if (j == -1) {
            j = this.fCurrentEntity.position;
          }
        }
        else if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
        {
          int m = this.fCurrentEntity.position - i;
          if (m == this.fCurrentEntity.ch.length)
          {
            localObject = new char[this.fCurrentEntity.ch.length << 1];
            System.arraycopy(this.fCurrentEntity.ch, i, localObject, 0, m);
            this.fCurrentEntity.ch = ((char[])localObject);
          }
          else
          {
            System.arraycopy(this.fCurrentEntity.ch, i, this.fCurrentEntity.ch, 0, m);
          }
          if (j != -1) {
            j -= i;
          }
          i = 0;
          if (load(m, false)) {
            break;
          }
        }
      }
      int k = this.fCurrentEntity.position - i;
      this.fCurrentEntity.columnNumber += k;
      if (k > 0)
      {
        String str2 = null;
        localObject = null;
        String str3 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i, k);
        if (j != -1)
        {
          int n = j - i;
          str2 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i, n);
          int i1 = k - n - 1;
          int i2 = j + 1;
          if (!XMLChar.isNCNameStart(this.fCurrentEntity.ch[i2])) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "IllegalQName", null, (short)2);
          }
          localObject = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i2, i1);
        }
        else
        {
          localObject = str3;
        }
        paramQName.setValues(str2, (String)localObject, str3, null);
        return true;
      }
    }
    return false;
  }
  
  public int scanContent(XMLString paramXMLString)
    throws IOException
  {
    if (this.fCurrentEntity.position == this.fCurrentEntity.count)
    {
      load(0, true);
    }
    else if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1)
    {
      this.fCurrentEntity.ch[0] = this.fCurrentEntity.ch[(this.fCurrentEntity.count - 1)];
      load(1, false);
      this.fCurrentEntity.position = 0;
      this.fCurrentEntity.startPosition = 0;
    }
    int i = this.fCurrentEntity.position;
    int j = this.fCurrentEntity.ch[i];
    int k = 0;
    boolean bool = this.fCurrentEntity.isExternal();
    if ((j == 10) || ((j == 13) && (bool)))
    {
      do
      {
        j = this.fCurrentEntity.ch[(this.fCurrentEntity.position++)];
        if ((j == 13) && (bool))
        {
          k++;
          this.fCurrentEntity.lineNumber += 1;
          this.fCurrentEntity.columnNumber = 1;
          if (this.fCurrentEntity.position == this.fCurrentEntity.count)
          {
            i = 0;
            this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
            this.fCurrentEntity.position = k;
            this.fCurrentEntity.startPosition = k;
            if (load(k, false)) {
              break;
            }
          }
          if (this.fCurrentEntity.ch[this.fCurrentEntity.position] == '\n')
          {
            this.fCurrentEntity.position += 1;
            i++;
          }
          else
          {
            k++;
          }
        }
        else if (j == 10)
        {
          k++;
          this.fCurrentEntity.lineNumber += 1;
          this.fCurrentEntity.columnNumber = 1;
          if (this.fCurrentEntity.position == this.fCurrentEntity.count)
          {
            i = 0;
            this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
            this.fCurrentEntity.position = k;
            this.fCurrentEntity.startPosition = k;
            if (load(k, false)) {
              break;
            }
          }
        }
        else
        {
          this.fCurrentEntity.position -= 1;
          break;
        }
      } while (this.fCurrentEntity.position < this.fCurrentEntity.count - 1);
      for (m = i; m < this.fCurrentEntity.position; m++) {
        this.fCurrentEntity.ch[m] = '\n';
      }
      int n = this.fCurrentEntity.position - i;
      if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1)
      {
        paramXMLString.setValues(this.fCurrentEntity.ch, i, n);
        return -1;
      }
    }
    while (this.fCurrentEntity.position < this.fCurrentEntity.count)
    {
      j = this.fCurrentEntity.ch[(this.fCurrentEntity.position++)];
      if (!XMLChar.isContent(j))
      {
        this.fCurrentEntity.position -= 1;
        break;
      }
    }
    int m = this.fCurrentEntity.position - i;
    this.fCurrentEntity.columnNumber += m - k;
    paramXMLString.setValues(this.fCurrentEntity.ch, i, m);
    if (this.fCurrentEntity.position != this.fCurrentEntity.count)
    {
      j = this.fCurrentEntity.ch[this.fCurrentEntity.position];
      if ((j == 13) && (bool)) {
        j = 10;
      }
    }
    else
    {
      j = -1;
    }
    return j;
  }
  
  public int scanLiteral(int paramInt, XMLString paramXMLString)
    throws IOException
  {
    if (this.fCurrentEntity.position == this.fCurrentEntity.count)
    {
      load(0, true);
    }
    else if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1)
    {
      this.fCurrentEntity.ch[0] = this.fCurrentEntity.ch[(this.fCurrentEntity.count - 1)];
      load(1, false);
      this.fCurrentEntity.position = 0;
      this.fCurrentEntity.startPosition = 0;
    }
    int i = this.fCurrentEntity.position;
    int j = this.fCurrentEntity.ch[i];
    int k = 0;
    boolean bool = this.fCurrentEntity.isExternal();
    if ((j == 10) || ((j == 13) && (bool)))
    {
      do
      {
        j = this.fCurrentEntity.ch[(this.fCurrentEntity.position++)];
        if ((j == 13) && (bool))
        {
          k++;
          this.fCurrentEntity.lineNumber += 1;
          this.fCurrentEntity.columnNumber = 1;
          if (this.fCurrentEntity.position == this.fCurrentEntity.count)
          {
            i = 0;
            this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
            this.fCurrentEntity.position = k;
            this.fCurrentEntity.startPosition = k;
            if (load(k, false)) {
              break;
            }
          }
          if (this.fCurrentEntity.ch[this.fCurrentEntity.position] == '\n')
          {
            this.fCurrentEntity.position += 1;
            i++;
          }
          else
          {
            k++;
          }
        }
        else if (j == 10)
        {
          k++;
          this.fCurrentEntity.lineNumber += 1;
          this.fCurrentEntity.columnNumber = 1;
          if (this.fCurrentEntity.position == this.fCurrentEntity.count)
          {
            i = 0;
            this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
            this.fCurrentEntity.position = k;
            this.fCurrentEntity.startPosition = k;
            if (load(k, false)) {
              break;
            }
          }
        }
        else
        {
          this.fCurrentEntity.position -= 1;
          break;
        }
      } while (this.fCurrentEntity.position < this.fCurrentEntity.count - 1);
      for (m = i; m < this.fCurrentEntity.position; m++) {
        this.fCurrentEntity.ch[m] = '\n';
      }
      int n = this.fCurrentEntity.position - i;
      if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1)
      {
        paramXMLString.setValues(this.fCurrentEntity.ch, i, n);
        return -1;
      }
    }
    while (this.fCurrentEntity.position < this.fCurrentEntity.count)
    {
      j = this.fCurrentEntity.ch[(this.fCurrentEntity.position++)];
      if (((j == paramInt) && ((!this.fCurrentEntity.literal) || (bool))) || (j == 37) || (!XMLChar.isContent(j)))
      {
        this.fCurrentEntity.position -= 1;
        break;
      }
    }
    int m = this.fCurrentEntity.position - i;
    this.fCurrentEntity.columnNumber += m - k;
    paramXMLString.setValues(this.fCurrentEntity.ch, i, m);
    if (this.fCurrentEntity.position != this.fCurrentEntity.count)
    {
      j = this.fCurrentEntity.ch[this.fCurrentEntity.position];
      if ((j == paramInt) && (this.fCurrentEntity.literal)) {
        j = -1;
      }
    }
    else
    {
      j = -1;
    }
    return j;
  }
  
  public boolean scanData(String paramString, XMLStringBuffer paramXMLStringBuffer)
    throws IOException
  {
    int i = 0;
    int j = paramString.length();
    int k = paramString.charAt(0);
    boolean bool1 = this.fCurrentEntity.isExternal();
    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
      load(0, true);
    }
    boolean bool2 = false;
    while ((this.fCurrentEntity.position > this.fCurrentEntity.count - j) && (!bool2))
    {
      System.arraycopy(this.fCurrentEntity.ch, this.fCurrentEntity.position, this.fCurrentEntity.ch, 0, this.fCurrentEntity.count - this.fCurrentEntity.position);
      bool2 = load(this.fCurrentEntity.count - this.fCurrentEntity.position, false);
      this.fCurrentEntity.position = 0;
      this.fCurrentEntity.startPosition = 0;
    }
    if (this.fCurrentEntity.position > this.fCurrentEntity.count - j)
    {
      m = this.fCurrentEntity.count - this.fCurrentEntity.position;
      paramXMLStringBuffer.append(this.fCurrentEntity.ch, this.fCurrentEntity.position, m);
      this.fCurrentEntity.columnNumber += this.fCurrentEntity.count;
      this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
      this.fCurrentEntity.position = this.fCurrentEntity.count;
      this.fCurrentEntity.startPosition = this.fCurrentEntity.count;
      load(0, true);
      return false;
    }
    int m = this.fCurrentEntity.position;
    int n = this.fCurrentEntity.ch[m];
    int i1 = 0;
    int i3;
    if ((n == 10) || ((n == 13) && (bool1)))
    {
      do
      {
        n = this.fCurrentEntity.ch[(this.fCurrentEntity.position++)];
        if ((n == 13) && (bool1))
        {
          i1++;
          this.fCurrentEntity.lineNumber += 1;
          this.fCurrentEntity.columnNumber = 1;
          if (this.fCurrentEntity.position == this.fCurrentEntity.count)
          {
            m = 0;
            this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
            this.fCurrentEntity.position = i1;
            this.fCurrentEntity.startPosition = i1;
            if (load(i1, false)) {
              break;
            }
          }
          if (this.fCurrentEntity.ch[this.fCurrentEntity.position] == '\n')
          {
            this.fCurrentEntity.position += 1;
            m++;
          }
          else
          {
            i1++;
          }
        }
        else if (n == 10)
        {
          i1++;
          this.fCurrentEntity.lineNumber += 1;
          this.fCurrentEntity.columnNumber = 1;
          if (this.fCurrentEntity.position == this.fCurrentEntity.count)
          {
            m = 0;
            this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
            this.fCurrentEntity.position = i1;
            this.fCurrentEntity.startPosition = i1;
            this.fCurrentEntity.count = i1;
            if (load(i1, false)) {
              break;
            }
          }
        }
        else
        {
          this.fCurrentEntity.position -= 1;
          break;
        }
      } while (this.fCurrentEntity.position < this.fCurrentEntity.count - 1);
      for (i2 = m; i2 < this.fCurrentEntity.position; i2++) {
        this.fCurrentEntity.ch[i2] = '\n';
      }
      i3 = this.fCurrentEntity.position - m;
      if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1)
      {
        paramXMLStringBuffer.append(this.fCurrentEntity.ch, m, i3);
        return true;
      }
    }
    while (this.fCurrentEntity.position < this.fCurrentEntity.count)
    {
      n = this.fCurrentEntity.ch[(this.fCurrentEntity.position++)];
      if (n == k)
      {
        i2 = this.fCurrentEntity.position - 1;
        for (i3 = 1; i3 < j; i3++)
        {
          if (this.fCurrentEntity.position == this.fCurrentEntity.count)
          {
            this.fCurrentEntity.position -= i3;
            break label1040;
          }
          n = this.fCurrentEntity.ch[(this.fCurrentEntity.position++)];
          if (paramString.charAt(i3) != n)
          {
            this.fCurrentEntity.position -= 1;
            break;
          }
        }
        if (this.fCurrentEntity.position == i2 + j)
        {
          i = 1;
          break;
        }
      }
      else
      {
        if ((n == 10) || ((bool1) && (n == 13)))
        {
          this.fCurrentEntity.position -= 1;
          break;
        }
        if (XMLChar.isInvalid(n))
        {
          this.fCurrentEntity.position -= 1;
          i2 = this.fCurrentEntity.position - m;
          this.fCurrentEntity.columnNumber += i2 - i1;
          paramXMLStringBuffer.append(this.fCurrentEntity.ch, m, i2);
          return true;
        }
      }
    }
    label1040:
    int i2 = this.fCurrentEntity.position - m;
    this.fCurrentEntity.columnNumber += i2 - i1;
    if (i != 0) {
      i2 -= j;
    }
    paramXMLStringBuffer.append(this.fCurrentEntity.ch, m, i2);
    return i == 0;
  }
  
  public boolean skipChar(int paramInt)
    throws IOException
  {
    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
      load(0, true);
    }
    int i = this.fCurrentEntity.ch[this.fCurrentEntity.position];
    if (i == paramInt)
    {
      this.fCurrentEntity.position += 1;
      if (paramInt == 10)
      {
        this.fCurrentEntity.lineNumber += 1;
        this.fCurrentEntity.columnNumber = 1;
      }
      else
      {
        this.fCurrentEntity.columnNumber += 1;
      }
      return true;
    }
    if ((paramInt == 10) && (i == 13) && (this.fCurrentEntity.isExternal()))
    {
      if (this.fCurrentEntity.position == this.fCurrentEntity.count)
      {
        this.fCurrentEntity.ch[0] = ((char)i);
        load(1, false);
      }
      this.fCurrentEntity.position += 1;
      if (this.fCurrentEntity.ch[this.fCurrentEntity.position] == '\n') {
        this.fCurrentEntity.position += 1;
      }
      this.fCurrentEntity.lineNumber += 1;
      this.fCurrentEntity.columnNumber = 1;
      return true;
    }
    return false;
  }
  
  public boolean skipSpaces()
    throws IOException
  {
    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
      load(0, true);
    }
    int i = this.fCurrentEntity.ch[this.fCurrentEntity.position];
    if (XMLChar.isSpace(i))
    {
      boolean bool1 = this.fCurrentEntity.isExternal();
      do
      {
        boolean bool2 = false;
        if ((i == 10) || ((bool1) && (i == 13)))
        {
          this.fCurrentEntity.lineNumber += 1;
          this.fCurrentEntity.columnNumber = 1;
          if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1)
          {
            this.fCurrentEntity.ch[0] = ((char)i);
            bool2 = load(1, true);
            if (!bool2)
            {
              this.fCurrentEntity.position = 0;
              this.fCurrentEntity.startPosition = 0;
            }
          }
          if ((i == 13) && (bool1)) {
            if (this.fCurrentEntity.ch[(++this.fCurrentEntity.position)] != '\n') {
              this.fCurrentEntity.position -= 1;
            }
          }
        }
        else
        {
          this.fCurrentEntity.columnNumber += 1;
        }
        if (!bool2) {
          this.fCurrentEntity.position += 1;
        }
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
          load(0, true);
        }
      } while (XMLChar.isSpace(i = this.fCurrentEntity.ch[this.fCurrentEntity.position]));
      return true;
    }
    return false;
  }
  
  public final boolean skipDeclSpaces()
    throws IOException
  {
    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
      load(0, true);
    }
    int i = this.fCurrentEntity.ch[this.fCurrentEntity.position];
    if (XMLChar.isSpace(i))
    {
      boolean bool1 = this.fCurrentEntity.isExternal();
      do
      {
        boolean bool2 = false;
        if ((i == 10) || ((bool1) && (i == 13)))
        {
          this.fCurrentEntity.lineNumber += 1;
          this.fCurrentEntity.columnNumber = 1;
          if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1)
          {
            this.fCurrentEntity.ch[0] = ((char)i);
            bool2 = load(1, true);
            if (!bool2)
            {
              this.fCurrentEntity.position = 0;
              this.fCurrentEntity.startPosition = 0;
            }
          }
          if ((i == 13) && (bool1)) {
            if (this.fCurrentEntity.ch[(++this.fCurrentEntity.position)] != '\n') {
              this.fCurrentEntity.position -= 1;
            }
          }
        }
        else
        {
          this.fCurrentEntity.columnNumber += 1;
        }
        if (!bool2) {
          this.fCurrentEntity.position += 1;
        }
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
          load(0, true);
        }
      } while (XMLChar.isSpace(i = this.fCurrentEntity.ch[this.fCurrentEntity.position]));
      return true;
    }
    return false;
  }
  
  public boolean skipString(String paramString)
    throws IOException
  {
    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
      load(0, true);
    }
    int i = paramString.length();
    for (int j = 0; j < i; j++)
    {
      int k = this.fCurrentEntity.ch[(this.fCurrentEntity.position++)];
      if (k != paramString.charAt(j))
      {
        this.fCurrentEntity.position -= j + 1;
        return false;
      }
      if ((j < i - 1) && (this.fCurrentEntity.position == this.fCurrentEntity.count))
      {
        System.arraycopy(this.fCurrentEntity.ch, this.fCurrentEntity.count - j - 1, this.fCurrentEntity.ch, 0, j + 1);
        if (load(j + 1, false))
        {
          this.fCurrentEntity.startPosition -= j + 1;
          this.fCurrentEntity.position -= j + 1;
          return false;
        }
      }
    }
    this.fCurrentEntity.columnNumber += i;
    return true;
  }
  
  public final String getPublicId()
  {
    return (this.fCurrentEntity != null) && (this.fCurrentEntity.entityLocation != null) ? this.fCurrentEntity.entityLocation.getPublicId() : null;
  }
  
  public final String getExpandedSystemId()
  {
    if (this.fCurrentEntity != null)
    {
      if ((this.fCurrentEntity.entityLocation != null) && (this.fCurrentEntity.entityLocation.getExpandedSystemId() != null)) {
        return this.fCurrentEntity.entityLocation.getExpandedSystemId();
      }
      return this.fCurrentEntity.getExpandedSystemId();
    }
    return null;
  }
  
  public final String getLiteralSystemId()
  {
    if (this.fCurrentEntity != null)
    {
      if ((this.fCurrentEntity.entityLocation != null) && (this.fCurrentEntity.entityLocation.getLiteralSystemId() != null)) {
        return this.fCurrentEntity.entityLocation.getLiteralSystemId();
      }
      return this.fCurrentEntity.getLiteralSystemId();
    }
    return null;
  }
  
  public final int getLineNumber()
  {
    if (this.fCurrentEntity != null)
    {
      if (this.fCurrentEntity.isExternal()) {
        return this.fCurrentEntity.lineNumber;
      }
      return this.fCurrentEntity.getLineNumber();
    }
    return -1;
  }
  
  public final int getColumnNumber()
  {
    if (this.fCurrentEntity != null)
    {
      if (this.fCurrentEntity.isExternal()) {
        return this.fCurrentEntity.columnNumber;
      }
      return this.fCurrentEntity.getColumnNumber();
    }
    return -1;
  }
  
  public final int getCharacterOffset()
  {
    if (this.fCurrentEntity != null)
    {
      if (this.fCurrentEntity.isExternal()) {
        return this.fCurrentEntity.baseCharOffset + (this.fCurrentEntity.position - this.fCurrentEntity.startPosition);
      }
      return this.fCurrentEntity.getCharacterOffset();
    }
    return -1;
  }
  
  public final String getEncoding()
  {
    if (this.fCurrentEntity != null)
    {
      if (this.fCurrentEntity.isExternal()) {
        return this.fCurrentEntity.encoding;
      }
      return this.fCurrentEntity.getEncoding();
    }
    return null;
  }
  
  public final String getXMLVersion()
  {
    if (this.fCurrentEntity != null)
    {
      if (this.fCurrentEntity.isExternal()) {
        return this.fCurrentEntity.xmlVersion;
      }
      return this.fCurrentEntity.getXMLVersion();
    }
    return null;
  }
  
  public final void setCurrentEntity(XMLEntityManager.ScannedEntity paramScannedEntity)
  {
    this.fCurrentEntity = paramScannedEntity;
  }
  
  public final void setBufferSize(int paramInt)
  {
    this.fBufferSize = paramInt;
  }
  
  public final void reset(SymbolTable paramSymbolTable, XMLEntityManager paramXMLEntityManager, XMLErrorReporter paramXMLErrorReporter)
  {
    this.fCurrentEntity = null;
    this.fSymbolTable = paramSymbolTable;
    this.fEntityManager = paramXMLEntityManager;
    this.fErrorReporter = paramXMLErrorReporter;
  }
  
  final boolean load(int paramInt, boolean paramBoolean)
    throws IOException
  {
    this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
    int i = this.fCurrentEntity.mayReadChunks ? this.fCurrentEntity.ch.length - paramInt : 64;
    int j = this.fCurrentEntity.reader.read(this.fCurrentEntity.ch, paramInt, i);
    boolean bool = false;
    if (j != -1)
    {
      if (j != 0)
      {
        this.fCurrentEntity.count = (j + paramInt);
        this.fCurrentEntity.position = paramInt;
        this.fCurrentEntity.startPosition = paramInt;
      }
    }
    else
    {
      this.fCurrentEntity.count = paramInt;
      this.fCurrentEntity.position = paramInt;
      this.fCurrentEntity.startPosition = paramInt;
      bool = true;
      if (paramBoolean)
      {
        this.fEntityManager.endEntity();
        if (this.fCurrentEntity == null) {
          throw END_OF_DOCUMENT_ENTITY;
        }
        if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
          load(0, true);
        }
      }
    }
    return bool;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.XMLEntityScanner
 * JD-Core Version:    0.7.0.1
 */
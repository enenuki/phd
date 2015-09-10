package org.apache.xerces.impl;

import java.io.IOException;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XML11Char;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLStringBuffer;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLString;

public class XML11EntityScanner
  extends XMLEntityScanner
{
  public int peekChar()
    throws IOException
  {
    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
      load(0, true);
    }
    int i = this.fCurrentEntity.ch[this.fCurrentEntity.position];
    if (this.fCurrentEntity.isExternal()) {
      return (i != 13) && (i != 133) && (i != 8232) ? i : 10;
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
    if ((i == 10) || (((i == 13) || (i == 133) || (i == 8232)) && ((bool = this.fCurrentEntity.isExternal()))))
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
        int j = this.fCurrentEntity.ch[(this.fCurrentEntity.position++)];
        if ((j != 10) && (j != 133)) {
          this.fCurrentEntity.position -= 1;
        }
      }
      i = 10;
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
    for (;;)
    {
      char c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
      int k;
      char[] arrayOfChar1;
      if (XML11Char.isXML11Name(c))
      {
        if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
        {
          k = this.fCurrentEntity.position - i;
          if (k == this.fCurrentEntity.ch.length)
          {
            arrayOfChar1 = new char[this.fCurrentEntity.ch.length << 1];
            System.arraycopy(this.fCurrentEntity.ch, i, arrayOfChar1, 0, k);
            this.fCurrentEntity.ch = arrayOfChar1;
          }
          else
          {
            System.arraycopy(this.fCurrentEntity.ch, i, this.fCurrentEntity.ch, 0, k);
          }
          i = 0;
          if (load(k, false)) {
            break;
          }
        }
      }
      else if (XML11Char.isXML11NameHighSurrogate(c))
      {
        if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
        {
          k = this.fCurrentEntity.position - i;
          if (k == this.fCurrentEntity.ch.length)
          {
            arrayOfChar1 = new char[this.fCurrentEntity.ch.length << 1];
            System.arraycopy(this.fCurrentEntity.ch, i, arrayOfChar1, 0, k);
            this.fCurrentEntity.ch = arrayOfChar1;
          }
          else
          {
            System.arraycopy(this.fCurrentEntity.ch, i, this.fCurrentEntity.ch, 0, k);
          }
          i = 0;
          if (load(k, false))
          {
            this.fCurrentEntity.startPosition -= 1;
            this.fCurrentEntity.position -= 1;
            break;
          }
        }
        k = this.fCurrentEntity.ch[this.fCurrentEntity.position];
        if ((!XMLChar.isLowSurrogate(k)) || (!XML11Char.isXML11Name(XMLChar.supplemental(c, k))))
        {
          this.fCurrentEntity.position -= 1;
        }
        else if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
        {
          int m = this.fCurrentEntity.position - i;
          if (m == this.fCurrentEntity.ch.length)
          {
            char[] arrayOfChar2 = new char[this.fCurrentEntity.ch.length << 1];
            System.arraycopy(this.fCurrentEntity.ch, i, arrayOfChar2, 0, m);
            this.fCurrentEntity.ch = arrayOfChar2;
          }
          else
          {
            System.arraycopy(this.fCurrentEntity.ch, i, this.fCurrentEntity.ch, 0, m);
          }
          i = 0;
          if (load(m, false)) {
            break;
          }
        }
      }
    }
    int j = this.fCurrentEntity.position - i;
    this.fCurrentEntity.columnNumber += j;
    String str = null;
    if (j > 0) {
      str = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i, j);
    }
    return str;
  }
  
  public String scanName()
    throws IOException
  {
    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
      load(0, true);
    }
    int i = this.fCurrentEntity.position;
    char c1 = this.fCurrentEntity.ch[i];
    Object localObject;
    if (XML11Char.isXML11NameStart(c1))
    {
      if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
      {
        this.fCurrentEntity.ch[0] = c1;
        i = 0;
        if (load(1, false))
        {
          this.fCurrentEntity.columnNumber += 1;
          String str1 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 1);
          return str1;
        }
      }
    }
    else if (XML11Char.isXML11NameHighSurrogate(c1))
    {
      if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
      {
        this.fCurrentEntity.ch[0] = c1;
        i = 0;
        if (load(1, false))
        {
          this.fCurrentEntity.position -= 1;
          this.fCurrentEntity.startPosition -= 1;
          return null;
        }
      }
      char c2 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
      if ((!XMLChar.isLowSurrogate(c2)) || (!XML11Char.isXML11NameStart(XMLChar.supplemental(c1, c2))))
      {
        this.fCurrentEntity.position -= 1;
        return null;
      }
      if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
      {
        this.fCurrentEntity.ch[0] = c1;
        this.fCurrentEntity.ch[1] = c2;
        i = 0;
        if (load(2, false))
        {
          this.fCurrentEntity.columnNumber += 2;
          localObject = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 2);
          return localObject;
        }
      }
    }
    else
    {
      return null;
    }
    for (;;)
    {
      c1 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
      int j;
      if (XML11Char.isXML11Name(c1))
      {
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
      else if (XML11Char.isXML11NameHighSurrogate(c1))
      {
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
          if (load(j, false))
          {
            this.fCurrentEntity.position -= 1;
            this.fCurrentEntity.startPosition -= 1;
            break;
          }
        }
        j = this.fCurrentEntity.ch[this.fCurrentEntity.position];
        if ((!XMLChar.isLowSurrogate(j)) || (!XML11Char.isXML11Name(XMLChar.supplemental(c1, j))))
        {
          this.fCurrentEntity.position -= 1;
        }
        else if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
        {
          int m = this.fCurrentEntity.position - i;
          if (m == this.fCurrentEntity.ch.length)
          {
            char[] arrayOfChar = new char[this.fCurrentEntity.ch.length << 1];
            System.arraycopy(this.fCurrentEntity.ch, i, arrayOfChar, 0, m);
            this.fCurrentEntity.ch = arrayOfChar;
          }
          else
          {
            System.arraycopy(this.fCurrentEntity.ch, i, this.fCurrentEntity.ch, 0, m);
          }
          i = 0;
          if (load(m, false)) {
            break;
          }
        }
      }
    }
    int k = this.fCurrentEntity.position - i;
    this.fCurrentEntity.columnNumber += k;
    String str2 = null;
    if (k > 0) {
      str2 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i, k);
    }
    return str2;
  }
  
  public String scanNCName()
    throws IOException
  {
    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
      load(0, true);
    }
    int i = this.fCurrentEntity.position;
    char c1 = this.fCurrentEntity.ch[i];
    Object localObject;
    if (XML11Char.isXML11NCNameStart(c1))
    {
      if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
      {
        this.fCurrentEntity.ch[0] = c1;
        i = 0;
        if (load(1, false))
        {
          this.fCurrentEntity.columnNumber += 1;
          String str1 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 1);
          return str1;
        }
      }
    }
    else if (XML11Char.isXML11NameHighSurrogate(c1))
    {
      if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
      {
        this.fCurrentEntity.ch[0] = c1;
        i = 0;
        if (load(1, false))
        {
          this.fCurrentEntity.position -= 1;
          this.fCurrentEntity.startPosition -= 1;
          return null;
        }
      }
      char c2 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
      if ((!XMLChar.isLowSurrogate(c2)) || (!XML11Char.isXML11NCNameStart(XMLChar.supplemental(c1, c2))))
      {
        this.fCurrentEntity.position -= 1;
        return null;
      }
      if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
      {
        this.fCurrentEntity.ch[0] = c1;
        this.fCurrentEntity.ch[1] = c2;
        i = 0;
        if (load(2, false))
        {
          this.fCurrentEntity.columnNumber += 2;
          localObject = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 2);
          return localObject;
        }
      }
    }
    else
    {
      return null;
    }
    for (;;)
    {
      c1 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
      int j;
      if (XML11Char.isXML11NCName(c1))
      {
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
      else if (XML11Char.isXML11NameHighSurrogate(c1))
      {
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
          if (load(j, false))
          {
            this.fCurrentEntity.startPosition -= 1;
            this.fCurrentEntity.position -= 1;
            break;
          }
        }
        j = this.fCurrentEntity.ch[this.fCurrentEntity.position];
        if ((!XMLChar.isLowSurrogate(j)) || (!XML11Char.isXML11NCName(XMLChar.supplemental(c1, j))))
        {
          this.fCurrentEntity.position -= 1;
        }
        else if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
        {
          int m = this.fCurrentEntity.position - i;
          if (m == this.fCurrentEntity.ch.length)
          {
            char[] arrayOfChar = new char[this.fCurrentEntity.ch.length << 1];
            System.arraycopy(this.fCurrentEntity.ch, i, arrayOfChar, 0, m);
            this.fCurrentEntity.ch = arrayOfChar;
          }
          else
          {
            System.arraycopy(this.fCurrentEntity.ch, i, this.fCurrentEntity.ch, 0, m);
          }
          i = 0;
          if (load(m, false)) {
            break;
          }
        }
      }
    }
    int k = this.fCurrentEntity.position - i;
    this.fCurrentEntity.columnNumber += k;
    String str2 = null;
    if (k > 0) {
      str2 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i, k);
    }
    return str2;
  }
  
  public boolean scanQName(QName paramQName)
    throws IOException
  {
    if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
      load(0, true);
    }
    int i = this.fCurrentEntity.position;
    char c1 = this.fCurrentEntity.ch[i];
    if (XML11Char.isXML11NCNameStart(c1))
    {
      if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
      {
        this.fCurrentEntity.ch[0] = c1;
        i = 0;
        if (load(1, false))
        {
          this.fCurrentEntity.columnNumber += 1;
          String str1 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 1);
          paramQName.setValues(null, str1, str1, null);
          return true;
        }
      }
    }
    else if (XML11Char.isXML11NameHighSurrogate(c1))
    {
      if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
      {
        this.fCurrentEntity.ch[0] = c1;
        i = 0;
        if (load(1, false))
        {
          this.fCurrentEntity.startPosition -= 1;
          this.fCurrentEntity.position -= 1;
          return false;
        }
      }
      char c2 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
      if ((!XMLChar.isLowSurrogate(c2)) || (!XML11Char.isXML11NCNameStart(XMLChar.supplemental(c1, c2))))
      {
        this.fCurrentEntity.position -= 1;
        return false;
      }
      if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
      {
        this.fCurrentEntity.ch[0] = c1;
        this.fCurrentEntity.ch[1] = c2;
        i = 0;
        if (load(2, false))
        {
          this.fCurrentEntity.columnNumber += 2;
          String str2 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 2);
          paramQName.setValues(null, str2, str2, null);
          return true;
        }
      }
    }
    else
    {
      return false;
    }
    int j = -1;
    int k = 0;
    Object localObject;
    for (;;)
    {
      c1 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
      int m;
      char[] arrayOfChar;
      if (XML11Char.isXML11Name(c1))
      {
        if (c1 == ':')
        {
          if (j == -1) {
            j = this.fCurrentEntity.position;
          }
        }
        else if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
        {
          m = this.fCurrentEntity.position - i;
          if (m == this.fCurrentEntity.ch.length)
          {
            arrayOfChar = new char[this.fCurrentEntity.ch.length << 1];
            System.arraycopy(this.fCurrentEntity.ch, i, arrayOfChar, 0, m);
            this.fCurrentEntity.ch = arrayOfChar;
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
      else if (XML11Char.isXML11NameHighSurrogate(c1))
      {
        if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
        {
          m = this.fCurrentEntity.position - i;
          if (m == this.fCurrentEntity.ch.length)
          {
            arrayOfChar = new char[this.fCurrentEntity.ch.length << 1];
            System.arraycopy(this.fCurrentEntity.ch, i, arrayOfChar, 0, m);
            this.fCurrentEntity.ch = arrayOfChar;
          }
          else
          {
            System.arraycopy(this.fCurrentEntity.ch, i, this.fCurrentEntity.ch, 0, m);
          }
          if (j != -1) {
            j -= i;
          }
          i = 0;
          if (load(m, false))
          {
            k = 1;
            this.fCurrentEntity.startPosition -= 1;
            this.fCurrentEntity.position -= 1;
            break;
          }
        }
        m = this.fCurrentEntity.ch[this.fCurrentEntity.position];
        if ((!XMLChar.isLowSurrogate(m)) || (!XML11Char.isXML11Name(XMLChar.supplemental(c1, m))))
        {
          k = 1;
          this.fCurrentEntity.position -= 1;
        }
        else if (++this.fCurrentEntity.position == this.fCurrentEntity.count)
        {
          int i1 = this.fCurrentEntity.position - i;
          if (i1 == this.fCurrentEntity.ch.length)
          {
            localObject = new char[this.fCurrentEntity.ch.length << 1];
            System.arraycopy(this.fCurrentEntity.ch, i, localObject, 0, i1);
            this.fCurrentEntity.ch = ((char[])localObject);
          }
          else
          {
            System.arraycopy(this.fCurrentEntity.ch, i, this.fCurrentEntity.ch, 0, i1);
          }
          if (j != -1) {
            j -= i;
          }
          i = 0;
          if (load(i1, false)) {
            break;
          }
        }
      }
    }
    int n = this.fCurrentEntity.position - i;
    this.fCurrentEntity.columnNumber += n;
    if (n > 0)
    {
      String str3 = null;
      localObject = null;
      String str4 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i, n);
      if (j != -1)
      {
        int i2 = j - i;
        str3 = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, i, i2);
        int i3 = n - i2 - 1;
        int i4 = j + 1;
        if ((!XML11Char.isXML11NCNameStart(this.fCurrentEntity.ch[i4])) && ((!XML11Char.isXML11NameHighSurrogate(this.fCurrentEntity.ch[i4])) || (k != 0))) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "IllegalQName", null, (short)2);
        }
        localObject = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, j + 1, i3);
      }
      else
      {
        localObject = str4;
      }
      paramQName.setValues(str3, (String)localObject, str4, null);
      return true;
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
    if ((j == 10) || (((j == 13) || (j == 133) || (j == 8232)) && (bool)))
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
          m = this.fCurrentEntity.ch[this.fCurrentEntity.position];
          if ((m == 10) || (m == 133))
          {
            this.fCurrentEntity.position += 1;
            i++;
          }
          else
          {
            k++;
          }
        }
        else if ((j == 10) || (((j == 133) || (j == 8232)) && (bool)))
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
    if (bool) {
      while (this.fCurrentEntity.position < this.fCurrentEntity.count)
      {
        j = this.fCurrentEntity.ch[(this.fCurrentEntity.position++)];
        if ((!XML11Char.isXML11Content(j)) || (j == 133) || (j == 8232))
        {
          this.fCurrentEntity.position -= 1;
          break;
        }
      }
    } else {
      while (this.fCurrentEntity.position < this.fCurrentEntity.count)
      {
        j = this.fCurrentEntity.ch[(this.fCurrentEntity.position++)];
        if (!XML11Char.isXML11InternalEntityContent(j))
        {
          this.fCurrentEntity.position -= 1;
          break;
        }
      }
    }
    int m = this.fCurrentEntity.position - i;
    this.fCurrentEntity.columnNumber += m - k;
    paramXMLString.setValues(this.fCurrentEntity.ch, i, m);
    if (this.fCurrentEntity.position != this.fCurrentEntity.count)
    {
      j = this.fCurrentEntity.ch[this.fCurrentEntity.position];
      if (((j == 13) || (j == 133) || (j == 8232)) && (bool)) {
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
      this.fCurrentEntity.startPosition = 0;
      this.fCurrentEntity.position = 0;
    }
    int i = this.fCurrentEntity.position;
    int j = this.fCurrentEntity.ch[i];
    int k = 0;
    boolean bool = this.fCurrentEntity.isExternal();
    if ((j == 10) || (((j == 13) || (j == 133) || (j == 8232)) && (bool)))
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
          m = this.fCurrentEntity.ch[this.fCurrentEntity.position];
          if ((m == 10) || (m == 133))
          {
            this.fCurrentEntity.position += 1;
            i++;
          }
          else
          {
            k++;
          }
        }
        else if ((j == 10) || (((j == 133) || (j == 8232)) && (bool)))
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
    if (bool) {
      while (this.fCurrentEntity.position < this.fCurrentEntity.count)
      {
        j = this.fCurrentEntity.ch[(this.fCurrentEntity.position++)];
        if ((j == paramInt) || (j == 37) || (!XML11Char.isXML11Content(j)) || (j == 133) || (j == 8232))
        {
          this.fCurrentEntity.position -= 1;
          break;
        }
      }
    } else {
      while (this.fCurrentEntity.position < this.fCurrentEntity.count)
      {
        j = this.fCurrentEntity.ch[(this.fCurrentEntity.position++)];
        if (((j == paramInt) && (!this.fCurrentEntity.literal)) || (j == 37) || (!XML11Char.isXML11InternalEntityContent(j)))
        {
          this.fCurrentEntity.position -= 1;
          break;
        }
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
    label1381:
    do
    {
      if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
        load(0, true);
      }
      boolean bool2 = false;
      while ((this.fCurrentEntity.position >= this.fCurrentEntity.count - j) && (!bool2))
      {
        System.arraycopy(this.fCurrentEntity.ch, this.fCurrentEntity.position, this.fCurrentEntity.ch, 0, this.fCurrentEntity.count - this.fCurrentEntity.position);
        bool2 = load(this.fCurrentEntity.count - this.fCurrentEntity.position, false);
        this.fCurrentEntity.position = 0;
        this.fCurrentEntity.startPosition = 0;
      }
      if (this.fCurrentEntity.position >= this.fCurrentEntity.count - j)
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
      if ((n == 10) || (((n == 13) || (n == 133) || (n == 8232)) && (bool1)))
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
            i2 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
            if ((i2 == 10) || (i2 == 133))
            {
              this.fCurrentEntity.position += 1;
              m++;
            }
            else
            {
              i1++;
            }
          }
          else if ((n == 10) || (((n == 133) || (n == 8232)) && (bool1)))
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
      if (bool1) {
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
                break label1381;
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
            if ((n == 10) || (n == 13) || (n == 133) || (n == 8232))
            {
              this.fCurrentEntity.position -= 1;
              break;
            }
            if (!XML11Char.isXML11ValidLiteral(n))
            {
              this.fCurrentEntity.position -= 1;
              i2 = this.fCurrentEntity.position - m;
              this.fCurrentEntity.columnNumber += i2 - i1;
              paramXMLStringBuffer.append(this.fCurrentEntity.ch, m, i2);
              return true;
            }
          }
        }
      } else {
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
                break label1381;
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
            if (n == 10)
            {
              this.fCurrentEntity.position -= 1;
              break;
            }
            if (!XML11Char.isXML11Valid(n))
            {
              this.fCurrentEntity.position -= 1;
              i2 = this.fCurrentEntity.position - m;
              this.fCurrentEntity.columnNumber += i2 - i1;
              paramXMLStringBuffer.append(this.fCurrentEntity.ch, m, i2);
              return true;
            }
          }
        }
      }
      int i2 = this.fCurrentEntity.position - m;
      this.fCurrentEntity.columnNumber += i2 - i1;
      if (i != 0) {
        i2 -= j;
      }
      paramXMLStringBuffer.append(this.fCurrentEntity.ch, m, i2);
    } while (i == 0);
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
    if ((paramInt == 10) && ((i == 8232) || (i == 133)) && (this.fCurrentEntity.isExternal()))
    {
      this.fCurrentEntity.position += 1;
      this.fCurrentEntity.lineNumber += 1;
      this.fCurrentEntity.columnNumber = 1;
      return true;
    }
    if ((paramInt == 10) && (i == 13) && (this.fCurrentEntity.isExternal()))
    {
      if (this.fCurrentEntity.position == this.fCurrentEntity.count)
      {
        this.fCurrentEntity.ch[0] = ((char)i);
        load(1, false);
      }
      int j = this.fCurrentEntity.ch[(++this.fCurrentEntity.position)];
      if ((j == 10) || (j == 133)) {
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
    boolean bool;
    if (this.fCurrentEntity.isExternal())
    {
      if (XML11Char.isXML11Space(i))
      {
        do
        {
          bool = false;
          if ((i == 10) || (i == 13) || (i == 133) || (i == 8232))
          {
            this.fCurrentEntity.lineNumber += 1;
            this.fCurrentEntity.columnNumber = 1;
            if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1)
            {
              this.fCurrentEntity.ch[0] = ((char)i);
              bool = load(1, true);
              if (!bool)
              {
                this.fCurrentEntity.startPosition = 0;
                this.fCurrentEntity.position = 0;
              }
            }
            if (i == 13)
            {
              int j = this.fCurrentEntity.ch[(++this.fCurrentEntity.position)];
              if ((j != 10) && (j != 133)) {
                this.fCurrentEntity.position -= 1;
              }
            }
          }
          else
          {
            this.fCurrentEntity.columnNumber += 1;
          }
          if (!bool) {
            this.fCurrentEntity.position += 1;
          }
          if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
            load(0, true);
          }
        } while (XML11Char.isXML11Space(i = this.fCurrentEntity.ch[this.fCurrentEntity.position]));
        return true;
      }
    }
    else if (XMLChar.isSpace(i))
    {
      do
      {
        bool = false;
        if (i == 10)
        {
          this.fCurrentEntity.lineNumber += 1;
          this.fCurrentEntity.columnNumber = 1;
          if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1)
          {
            this.fCurrentEntity.ch[0] = ((char)i);
            bool = load(1, true);
            if (!bool)
            {
              this.fCurrentEntity.startPosition = 0;
              this.fCurrentEntity.position = 0;
            }
          }
        }
        else
        {
          this.fCurrentEntity.columnNumber += 1;
        }
        if (!bool) {
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
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.XML11EntityScanner
 * JD-Core Version:    0.7.0.1
 */
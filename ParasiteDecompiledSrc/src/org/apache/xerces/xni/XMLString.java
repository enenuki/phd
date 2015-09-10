package org.apache.xerces.xni;

public class XMLString
{
  public char[] ch;
  public int offset;
  public int length;
  
  public XMLString() {}
  
  public XMLString(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    setValues(paramArrayOfChar, paramInt1, paramInt2);
  }
  
  public XMLString(XMLString paramXMLString)
  {
    setValues(paramXMLString);
  }
  
  public void setValues(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    this.ch = paramArrayOfChar;
    this.offset = paramInt1;
    this.length = paramInt2;
  }
  
  public void setValues(XMLString paramXMLString)
  {
    setValues(paramXMLString.ch, paramXMLString.offset, paramXMLString.length);
  }
  
  public void clear()
  {
    this.ch = null;
    this.offset = 0;
    this.length = -1;
  }
  
  public boolean equals(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    if (paramArrayOfChar == null) {
      return false;
    }
    if (this.length != paramInt2) {
      return false;
    }
    for (int i = 0; i < paramInt2; i++) {
      if (this.ch[(this.offset + i)] != paramArrayOfChar[(paramInt1 + i)]) {
        return false;
      }
    }
    return true;
  }
  
  public boolean equals(String paramString)
  {
    if (paramString == null) {
      return false;
    }
    if (this.length != paramString.length()) {
      return false;
    }
    for (int i = 0; i < this.length; i++) {
      if (this.ch[(this.offset + i)] != paramString.charAt(i)) {
        return false;
      }
    }
    return true;
  }
  
  public String toString()
  {
    return this.length > 0 ? new String(this.ch, this.offset, this.length) : "";
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xni.XMLString
 * JD-Core Version:    0.7.0.1
 */
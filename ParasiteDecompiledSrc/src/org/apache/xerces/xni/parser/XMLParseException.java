package org.apache.xerces.xni.parser;

import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XNIException;

public class XMLParseException
  extends XNIException
{
  static final long serialVersionUID = 1732959359448549967L;
  protected String fPublicId;
  protected String fLiteralSystemId;
  protected String fExpandedSystemId;
  protected String fBaseSystemId;
  protected int fLineNumber = -1;
  protected int fColumnNumber = -1;
  protected int fCharacterOffset = -1;
  
  public XMLParseException(XMLLocator paramXMLLocator, String paramString)
  {
    super(paramString);
    if (paramXMLLocator != null)
    {
      this.fPublicId = paramXMLLocator.getPublicId();
      this.fLiteralSystemId = paramXMLLocator.getLiteralSystemId();
      this.fExpandedSystemId = paramXMLLocator.getExpandedSystemId();
      this.fBaseSystemId = paramXMLLocator.getBaseSystemId();
      this.fLineNumber = paramXMLLocator.getLineNumber();
      this.fColumnNumber = paramXMLLocator.getColumnNumber();
      this.fCharacterOffset = paramXMLLocator.getCharacterOffset();
    }
  }
  
  public XMLParseException(XMLLocator paramXMLLocator, String paramString, Exception paramException)
  {
    super(paramString, paramException);
    if (paramXMLLocator != null)
    {
      this.fPublicId = paramXMLLocator.getPublicId();
      this.fLiteralSystemId = paramXMLLocator.getLiteralSystemId();
      this.fExpandedSystemId = paramXMLLocator.getExpandedSystemId();
      this.fBaseSystemId = paramXMLLocator.getBaseSystemId();
      this.fLineNumber = paramXMLLocator.getLineNumber();
      this.fColumnNumber = paramXMLLocator.getColumnNumber();
      this.fCharacterOffset = paramXMLLocator.getCharacterOffset();
    }
  }
  
  public String getPublicId()
  {
    return this.fPublicId;
  }
  
  public String getExpandedSystemId()
  {
    return this.fExpandedSystemId;
  }
  
  public String getLiteralSystemId()
  {
    return this.fLiteralSystemId;
  }
  
  public String getBaseSystemId()
  {
    return this.fBaseSystemId;
  }
  
  public int getLineNumber()
  {
    return this.fLineNumber;
  }
  
  public int getColumnNumber()
  {
    return this.fColumnNumber;
  }
  
  public int getCharacterOffset()
  {
    return this.fCharacterOffset;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.fPublicId != null) {
      localStringBuffer.append(this.fPublicId);
    }
    localStringBuffer.append(':');
    if (this.fLiteralSystemId != null) {
      localStringBuffer.append(this.fLiteralSystemId);
    }
    localStringBuffer.append(':');
    if (this.fExpandedSystemId != null) {
      localStringBuffer.append(this.fExpandedSystemId);
    }
    localStringBuffer.append(':');
    if (this.fBaseSystemId != null) {
      localStringBuffer.append(this.fBaseSystemId);
    }
    localStringBuffer.append(':');
    localStringBuffer.append(this.fLineNumber);
    localStringBuffer.append(':');
    localStringBuffer.append(this.fColumnNumber);
    localStringBuffer.append(':');
    localStringBuffer.append(this.fCharacterOffset);
    localStringBuffer.append(':');
    String str = getMessage();
    if (str == null)
    {
      Exception localException = getException();
      if (localException != null) {
        str = localException.getMessage();
      }
    }
    if (str != null) {
      localStringBuffer.append(str);
    }
    return localStringBuffer.toString();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xni.parser.XMLParseException
 * JD-Core Version:    0.7.0.1
 */
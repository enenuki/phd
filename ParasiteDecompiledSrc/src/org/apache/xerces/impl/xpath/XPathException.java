package org.apache.xerces.impl.xpath;

public class XPathException
  extends Exception
{
  static final long serialVersionUID = -948482312169512085L;
  private final String fKey;
  
  public XPathException()
  {
    this.fKey = "c-general-xpath";
  }
  
  public XPathException(String paramString)
  {
    this.fKey = paramString;
  }
  
  public String getKey()
  {
    return this.fKey;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xpath.XPathException
 * JD-Core Version:    0.7.0.1
 */
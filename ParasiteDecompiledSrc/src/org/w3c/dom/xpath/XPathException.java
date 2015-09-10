package org.w3c.dom.xpath;

public class XPathException
  extends RuntimeException
{
  public short code;
  public static final short INVALID_EXPRESSION_ERR = 51;
  public static final short TYPE_ERR = 52;
  
  public XPathException(short paramShort, String paramString)
  {
    super(paramString);
    this.code = paramShort;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.xpath.XPathException
 * JD-Core Version:    0.7.0.1
 */
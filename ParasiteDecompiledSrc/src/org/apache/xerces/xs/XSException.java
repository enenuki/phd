package org.apache.xerces.xs;

public class XSException
  extends RuntimeException
{
  static final long serialVersionUID = 3111893084677917742L;
  public short code;
  public static final short NOT_SUPPORTED_ERR = 1;
  public static final short INDEX_SIZE_ERR = 2;
  
  public XSException(short paramShort, String paramString)
  {
    super(paramString);
    this.code = paramShort;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xs.XSException
 * JD-Core Version:    0.7.0.1
 */
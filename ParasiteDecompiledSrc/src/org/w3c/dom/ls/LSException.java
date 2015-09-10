package org.w3c.dom.ls;

public class LSException
  extends RuntimeException
{
  public short code;
  public static final short PARSE_ERR = 81;
  public static final short SERIALIZE_ERR = 82;
  
  public LSException(short paramShort, String paramString)
  {
    super(paramString);
    this.code = paramShort;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.ls.LSException
 * JD-Core Version:    0.7.0.1
 */
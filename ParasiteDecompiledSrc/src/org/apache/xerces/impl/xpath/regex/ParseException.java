package org.apache.xerces.impl.xpath.regex;

public class ParseException
  extends RuntimeException
{
  static final long serialVersionUID = -7012400318097691370L;
  final int location;
  
  public ParseException(String paramString, int paramInt)
  {
    super(paramString);
    this.location = paramInt;
  }
  
  public int getLocation()
  {
    return this.location;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xpath.regex.ParseException
 * JD-Core Version:    0.7.0.1
 */
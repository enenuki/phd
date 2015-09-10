package org.apache.xml.utils;

public abstract class XMLStringFactory
{
  public abstract XMLString newstr(String paramString);
  
  public abstract XMLString newstr(FastStringBuffer paramFastStringBuffer, int paramInt1, int paramInt2);
  
  public abstract XMLString newstr(char[] paramArrayOfChar, int paramInt1, int paramInt2);
  
  public abstract XMLString emptystr();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.XMLStringFactory
 * JD-Core Version:    0.7.0.1
 */
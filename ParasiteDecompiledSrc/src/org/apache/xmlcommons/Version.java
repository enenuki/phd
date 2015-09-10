package org.apache.xmlcommons;

import java.io.PrintStream;

public class Version
{
  public static String getVersion()
  {
    return getProduct() + " " + getVersionNum();
  }
  
  public static String getProduct()
  {
    return "XmlCommonsExternal";
  }
  
  public static String getVersionNum()
  {
    return "1.3.04";
  }
  
  public static void main(String[] paramArrayOfString)
  {
    System.out.println(getVersion());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xmlcommons.Version
 * JD-Core Version:    0.7.0.1
 */
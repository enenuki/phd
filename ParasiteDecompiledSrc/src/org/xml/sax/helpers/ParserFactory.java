package org.xml.sax.helpers;

import org.xml.sax.Parser;

/**
 * @deprecated
 */
public class ParserFactory
{
  public static Parser makeParser()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException, NullPointerException, ClassCastException
  {
    SecuritySupport localSecuritySupport = SecuritySupport.getInstance();
    String str = localSecuritySupport.getSystemProperty("org.xml.sax.parser");
    if (str == null) {
      throw new NullPointerException("No value for sax.parser property");
    }
    return makeParser(str);
  }
  
  public static Parser makeParser(String paramString)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException, ClassCastException
  {
    return (Parser)NewInstance.newInstance(NewInstance.getClassLoader(), paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.xml.sax.helpers.ParserFactory
 * JD-Core Version:    0.7.0.1
 */
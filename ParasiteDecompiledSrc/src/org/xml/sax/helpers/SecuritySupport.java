package org.xml.sax.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

class SecuritySupport
{
  private static final Object securitySupport;
  
  public static SecuritySupport getInstance()
  {
    return (SecuritySupport)securitySupport;
  }
  
  public ClassLoader getContextClassLoader()
  {
    return null;
  }
  
  public String getSystemProperty(String paramString)
  {
    return System.getProperty(paramString);
  }
  
  public FileInputStream getFileInputStream(File paramFile)
    throws FileNotFoundException
  {
    return new FileInputStream(paramFile);
  }
  
  public InputStream getResourceAsStream(ClassLoader paramClassLoader, String paramString)
  {
    InputStream localInputStream;
    if (paramClassLoader == null) {
      localInputStream = ClassLoader.getSystemResourceAsStream(paramString);
    } else {
      localInputStream = paramClassLoader.getResourceAsStream(paramString);
    }
    return localInputStream;
  }
  
  static
  {
    Object localObject1 = null;
    try
    {
      Class localClass = Class.forName("java.security.AccessController");
      localObject1 = new SecuritySupport12();
    }
    catch (Exception localException) {}finally
    {
      if (localObject1 == null) {
        localObject1 = new SecuritySupport();
      }
      securitySupport = localObject1;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.xml.sax.helpers.SecuritySupport
 * JD-Core Version:    0.7.0.1
 */
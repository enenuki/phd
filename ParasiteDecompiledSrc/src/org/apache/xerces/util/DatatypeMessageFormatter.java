package org.apache.xerces.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DatatypeMessageFormatter
{
  private static final String BASE_NAME = "org.apache.xerces.impl.msg.DatatypeMessages";
  
  public static String formatMessage(Locale paramLocale, String paramString, Object[] paramArrayOfObject)
    throws MissingResourceException
  {
    ResourceBundle localResourceBundle = null;
    if (paramLocale != null) {
      localResourceBundle = ResourceBundle.getBundle("org.apache.xerces.impl.msg.DatatypeMessages", paramLocale);
    } else {
      localResourceBundle = ResourceBundle.getBundle("org.apache.xerces.impl.msg.DatatypeMessages");
    }
    String str;
    try
    {
      str = localResourceBundle.getString(paramString);
      if (paramArrayOfObject != null) {
        try
        {
          str = MessageFormat.format(str, paramArrayOfObject);
        }
        catch (Exception localException)
        {
          str = localResourceBundle.getString("FormatFailed");
          str = str + " " + localResourceBundle.getString(paramString);
        }
      }
    }
    catch (MissingResourceException localMissingResourceException)
    {
      str = localResourceBundle.getString("BadMessageKey");
      throw new MissingResourceException(paramString, str, paramString);
    }
    if (str == null)
    {
      str = paramString;
      if (paramArrayOfObject.length > 0)
      {
        StringBuffer localStringBuffer = new StringBuffer(str);
        localStringBuffer.append('?');
        for (int i = 0; i < paramArrayOfObject.length; i++)
        {
          if (i > 0) {
            localStringBuffer.append('&');
          }
          localStringBuffer.append(String.valueOf(paramArrayOfObject[i]));
        }
      }
    }
    return str;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.util.DatatypeMessageFormatter
 * JD-Core Version:    0.7.0.1
 */
package org.apache.xerces.xinclude;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.xerces.util.MessageFormatter;

public class XIncludeMessageFormatter
  implements MessageFormatter
{
  public static final String XINCLUDE_DOMAIN = "http://www.w3.org/TR/xinclude";
  private Locale fLocale = null;
  private ResourceBundle fResourceBundle = null;
  
  public String formatMessage(Locale paramLocale, String paramString, Object[] paramArrayOfObject)
    throws MissingResourceException
  {
    if ((this.fResourceBundle == null) || (paramLocale != this.fLocale))
    {
      if (paramLocale != null)
      {
        this.fResourceBundle = ResourceBundle.getBundle("org.apache.xerces.impl.msg.XIncludeMessages", paramLocale);
        this.fLocale = paramLocale;
      }
      if (this.fResourceBundle == null) {
        this.fResourceBundle = ResourceBundle.getBundle("org.apache.xerces.impl.msg.XIncludeMessages");
      }
    }
    String str = this.fResourceBundle.getString(paramString);
    if (paramArrayOfObject != null) {
      try
      {
        str = MessageFormat.format(str, paramArrayOfObject);
      }
      catch (Exception localException)
      {
        str = this.fResourceBundle.getString("FormatFailed");
        str = str + " " + this.fResourceBundle.getString(paramString);
      }
    }
    if (str == null)
    {
      str = this.fResourceBundle.getString("BadMessageKey");
      throw new MissingResourceException(str, "org.apache.xerces.impl.msg.XIncludeMessages", paramString);
    }
    return str;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xinclude.XIncludeMessageFormatter
 * JD-Core Version:    0.7.0.1
 */
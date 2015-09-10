package org.apache.xerces.impl.xs;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.xerces.util.MessageFormatter;

public class XSMessageFormatter
  implements MessageFormatter
{
  public static final String SCHEMA_DOMAIN = "http://www.w3.org/TR/xml-schema-1";
  private Locale fLocale = null;
  private ResourceBundle fResourceBundle = null;
  
  public String formatMessage(Locale paramLocale, String paramString, Object[] paramArrayOfObject)
    throws MissingResourceException
  {
    if ((this.fResourceBundle == null) || (paramLocale != this.fLocale))
    {
      if (paramLocale != null)
      {
        this.fResourceBundle = ResourceBundle.getBundle("org.apache.xerces.impl.msg.XMLSchemaMessages", paramLocale);
        this.fLocale = paramLocale;
      }
      if (this.fResourceBundle == null) {
        this.fResourceBundle = ResourceBundle.getBundle("org.apache.xerces.impl.msg.XMLSchemaMessages");
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
      throw new MissingResourceException(str, "org.apache.xerces.impl.msg.SchemaMessages", paramString);
    }
    return str;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.XSMessageFormatter
 * JD-Core Version:    0.7.0.1
 */
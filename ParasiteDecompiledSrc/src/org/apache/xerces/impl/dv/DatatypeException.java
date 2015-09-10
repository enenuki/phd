package org.apache.xerces.impl.dv;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DatatypeException
  extends Exception
{
  static final long serialVersionUID = 1940805832730465578L;
  protected final String key;
  protected final Object[] args;
  
  public DatatypeException(String paramString, Object[] paramArrayOfObject)
  {
    super(paramString);
    this.key = paramString;
    this.args = paramArrayOfObject;
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public Object[] getArgs()
  {
    return this.args;
  }
  
  public String getMessage()
  {
    ResourceBundle localResourceBundle = null;
    localResourceBundle = ResourceBundle.getBundle("org.apache.xerces.impl.msg.XMLSchemaMessages");
    if (localResourceBundle == null) {
      throw new MissingResourceException("Property file not found!", "org.apache.xerces.impl.msg.XMLSchemaMessages", this.key);
    }
    String str = localResourceBundle.getString(this.key);
    if (str == null)
    {
      str = localResourceBundle.getString("BadMessageKey");
      throw new MissingResourceException(str, "org.apache.xerces.impl.msg.XMLSchemaMessages", this.key);
    }
    if (this.args != null) {
      try
      {
        str = MessageFormat.format(str, this.args);
      }
      catch (Exception localException)
      {
        str = localResourceBundle.getString("FormatFailed");
        str = str + " " + localResourceBundle.getString(this.key);
      }
    }
    return str;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.DatatypeException
 * JD-Core Version:    0.7.0.1
 */
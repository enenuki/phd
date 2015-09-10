package org.apache.xerces.util;

import java.util.Locale;
import java.util.MissingResourceException;

public abstract interface MessageFormatter
{
  public abstract String formatMessage(Locale paramLocale, String paramString, Object[] paramArrayOfObject)
    throws MissingResourceException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.util.MessageFormatter
 * JD-Core Version:    0.7.0.1
 */
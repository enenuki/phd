package org.apache.xalan.xsltc;

import java.text.Collator;
import java.util.Locale;

public abstract interface CollatorFactory
{
  public abstract Collator getCollator(String paramString1, String paramString2);
  
  public abstract Collator getCollator(Locale paramLocale);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.CollatorFactory
 * JD-Core Version:    0.7.0.1
 */
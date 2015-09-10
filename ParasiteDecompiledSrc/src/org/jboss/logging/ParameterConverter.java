package org.jboss.logging;

import java.util.Locale;

public abstract interface ParameterConverter<I>
{
  public abstract Object convert(Locale paramLocale, I paramI);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.ParameterConverter
 * JD-Core Version:    0.7.0.1
 */
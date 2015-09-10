package org.hibernate.service.config.spi;

import java.util.Map;
import org.hibernate.service.Service;

public abstract interface ConfigurationService
  extends Service
{
  public abstract Map getSettings();
  
  public abstract <T> T getSetting(String paramString, Converter<T> paramConverter);
  
  public abstract <T> T getSetting(String paramString, Converter<T> paramConverter, T paramT);
  
  public static abstract interface Converter<T>
  {
    public abstract T convert(Object paramObject);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.config.spi.ConfigurationService
 * JD-Core Version:    0.7.0.1
 */
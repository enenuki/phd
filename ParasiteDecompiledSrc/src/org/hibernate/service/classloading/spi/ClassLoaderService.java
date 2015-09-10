package org.hibernate.service.classloading.spi;

import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.List;
import org.hibernate.service.Service;

public abstract interface ClassLoaderService
  extends Service
{
  public abstract <T> Class<T> classForName(String paramString);
  
  public abstract URL locateResource(String paramString);
  
  public abstract InputStream locateResourceStream(String paramString);
  
  public abstract List<URL> locateResources(String paramString);
  
  public abstract <S> LinkedHashSet<S> loadJavaServices(Class<S> paramClass);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.classloading.spi.ClassLoaderService
 * JD-Core Version:    0.7.0.1
 */
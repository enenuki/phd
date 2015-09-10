package org.hibernate.service.jndi.spi;

import javax.naming.event.NamespaceChangeListener;
import org.hibernate.service.Service;

public abstract interface JndiService
  extends Service
{
  public abstract Object locate(String paramString);
  
  public abstract void bind(String paramString, Object paramObject);
  
  public abstract void unbind(String paramString);
  
  public abstract void addListener(String paramString, NamespaceChangeListener paramNamespaceChangeListener);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jndi.spi.JndiService
 * JD-Core Version:    0.7.0.1
 */
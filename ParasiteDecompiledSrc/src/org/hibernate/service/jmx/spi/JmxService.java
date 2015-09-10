package org.hibernate.service.jmx.spi;

import javax.management.ObjectName;
import org.hibernate.service.Service;
import org.hibernate.service.spi.Manageable;

public abstract interface JmxService
  extends Service
{
  public abstract void registerService(Manageable paramManageable, Class<? extends Service> paramClass);
  
  public abstract void registerMBean(ObjectName paramObjectName, Object paramObject);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jmx.spi.JmxService
 * JD-Core Version:    0.7.0.1
 */
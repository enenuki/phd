package org.hibernate.service.instrumentation.spi;

import org.hibernate.service.Service;

public abstract interface InstrumentationService
  extends Service
{
  public abstract boolean isInstrumented(Class<?> paramClass);
  
  public abstract boolean isInstrumented(Object paramObject);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.instrumentation.spi.InstrumentationService
 * JD-Core Version:    0.7.0.1
 */
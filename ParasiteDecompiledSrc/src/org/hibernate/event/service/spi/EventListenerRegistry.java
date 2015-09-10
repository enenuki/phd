package org.hibernate.event.service.spi;

import java.io.Serializable;
import org.hibernate.event.spi.EventType;
import org.hibernate.service.Service;

public abstract interface EventListenerRegistry
  extends Service, Serializable
{
  public abstract <T> EventListenerGroup<T> getEventListenerGroup(EventType<T> paramEventType);
  
  public abstract void addDuplicationStrategy(DuplicationStrategy paramDuplicationStrategy);
  
  public abstract <T> void setListeners(EventType<T> paramEventType, Class<T>... paramVarArgs);
  
  public abstract <T> void setListeners(EventType<T> paramEventType, T... paramVarArgs);
  
  public abstract <T> void appendListeners(EventType<T> paramEventType, Class<T>... paramVarArgs);
  
  public abstract <T> void appendListeners(EventType<T> paramEventType, T... paramVarArgs);
  
  public abstract <T> void prependListeners(EventType<T> paramEventType, Class<T>... paramVarArgs);
  
  public abstract <T> void prependListeners(EventType<T> paramEventType, T... paramVarArgs);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.service.spi.EventListenerRegistry
 * JD-Core Version:    0.7.0.1
 */
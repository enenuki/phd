package org.hibernate.event.service.spi;

import java.io.Serializable;
import org.hibernate.event.spi.EventType;

public abstract interface EventListenerGroup<T>
  extends Serializable
{
  public abstract EventType<T> getEventType();
  
  public abstract boolean isEmpty();
  
  public abstract int count();
  
  public abstract Iterable<T> listeners();
  
  public abstract void addDuplicationStrategy(DuplicationStrategy paramDuplicationStrategy);
  
  public abstract void appendListener(T paramT);
  
  public abstract void appendListeners(T... paramVarArgs);
  
  public abstract void prependListener(T paramT);
  
  public abstract void prependListeners(T... paramVarArgs);
  
  public abstract void clear();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.service.spi.EventListenerGroup
 * JD-Core Version:    0.7.0.1
 */
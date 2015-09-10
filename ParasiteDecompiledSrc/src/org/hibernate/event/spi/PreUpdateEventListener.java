package org.hibernate.event.spi;

import java.io.Serializable;

public abstract interface PreUpdateEventListener
  extends Serializable
{
  public abstract boolean onPreUpdate(PreUpdateEvent paramPreUpdateEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PreUpdateEventListener
 * JD-Core Version:    0.7.0.1
 */
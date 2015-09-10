package org.hibernate.event.spi;

import java.io.Serializable;

public abstract interface PreDeleteEventListener
  extends Serializable
{
  public abstract boolean onPreDelete(PreDeleteEvent paramPreDeleteEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PreDeleteEventListener
 * JD-Core Version:    0.7.0.1
 */
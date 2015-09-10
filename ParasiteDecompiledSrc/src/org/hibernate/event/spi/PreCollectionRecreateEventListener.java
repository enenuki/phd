package org.hibernate.event.spi;

import java.io.Serializable;

public abstract interface PreCollectionRecreateEventListener
  extends Serializable
{
  public abstract void onPreRecreateCollection(PreCollectionRecreateEvent paramPreCollectionRecreateEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PreCollectionRecreateEventListener
 * JD-Core Version:    0.7.0.1
 */
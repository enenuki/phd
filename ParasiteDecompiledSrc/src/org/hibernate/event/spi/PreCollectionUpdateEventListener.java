package org.hibernate.event.spi;

import java.io.Serializable;

public abstract interface PreCollectionUpdateEventListener
  extends Serializable
{
  public abstract void onPreUpdateCollection(PreCollectionUpdateEvent paramPreCollectionUpdateEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PreCollectionUpdateEventListener
 * JD-Core Version:    0.7.0.1
 */
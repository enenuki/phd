package org.hibernate.event.spi;

import java.io.Serializable;

public abstract interface PreCollectionRemoveEventListener
  extends Serializable
{
  public abstract void onPreRemoveCollection(PreCollectionRemoveEvent paramPreCollectionRemoveEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PreCollectionRemoveEventListener
 * JD-Core Version:    0.7.0.1
 */
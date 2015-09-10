package org.hibernate.event.spi;

import java.io.Serializable;

public abstract interface PostCollectionRemoveEventListener
  extends Serializable
{
  public abstract void onPostRemoveCollection(PostCollectionRemoveEvent paramPostCollectionRemoveEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PostCollectionRemoveEventListener
 * JD-Core Version:    0.7.0.1
 */
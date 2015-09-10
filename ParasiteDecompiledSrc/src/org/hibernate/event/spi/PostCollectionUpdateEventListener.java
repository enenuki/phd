package org.hibernate.event.spi;

import java.io.Serializable;

public abstract interface PostCollectionUpdateEventListener
  extends Serializable
{
  public abstract void onPostUpdateCollection(PostCollectionUpdateEvent paramPostCollectionUpdateEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PostCollectionUpdateEventListener
 * JD-Core Version:    0.7.0.1
 */
package org.hibernate.event.spi;

import java.io.Serializable;

public abstract interface PostCollectionRecreateEventListener
  extends Serializable
{
  public abstract void onPostRecreateCollection(PostCollectionRecreateEvent paramPostCollectionRecreateEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PostCollectionRecreateEventListener
 * JD-Core Version:    0.7.0.1
 */
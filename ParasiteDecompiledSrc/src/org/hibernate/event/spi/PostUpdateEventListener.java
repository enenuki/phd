package org.hibernate.event.spi;

import java.io.Serializable;

public abstract interface PostUpdateEventListener
  extends Serializable
{
  public abstract void onPostUpdate(PostUpdateEvent paramPostUpdateEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PostUpdateEventListener
 * JD-Core Version:    0.7.0.1
 */
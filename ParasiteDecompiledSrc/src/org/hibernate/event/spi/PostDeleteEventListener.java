package org.hibernate.event.spi;

import java.io.Serializable;

public abstract interface PostDeleteEventListener
  extends Serializable
{
  public abstract void onPostDelete(PostDeleteEvent paramPostDeleteEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PostDeleteEventListener
 * JD-Core Version:    0.7.0.1
 */
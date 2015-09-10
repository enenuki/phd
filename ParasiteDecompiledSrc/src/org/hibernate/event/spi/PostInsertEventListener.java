package org.hibernate.event.spi;

import java.io.Serializable;

public abstract interface PostInsertEventListener
  extends Serializable
{
  public abstract void onPostInsert(PostInsertEvent paramPostInsertEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PostInsertEventListener
 * JD-Core Version:    0.7.0.1
 */
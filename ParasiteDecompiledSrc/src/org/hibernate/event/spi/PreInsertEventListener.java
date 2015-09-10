package org.hibernate.event.spi;

import java.io.Serializable;

public abstract interface PreInsertEventListener
  extends Serializable
{
  public abstract boolean onPreInsert(PreInsertEvent paramPreInsertEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PreInsertEventListener
 * JD-Core Version:    0.7.0.1
 */
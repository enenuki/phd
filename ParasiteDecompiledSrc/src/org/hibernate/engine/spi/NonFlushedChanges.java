package org.hibernate.engine.spi;

import java.io.Serializable;

public abstract interface NonFlushedChanges
  extends Serializable
{
  public abstract void clear();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.NonFlushedChanges
 * JD-Core Version:    0.7.0.1
 */
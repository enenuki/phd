package org.hibernate.event.spi;

import java.io.Serializable;

public abstract interface PreLoadEventListener
  extends Serializable
{
  public abstract void onPreLoad(PreLoadEvent paramPreLoadEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PreLoadEventListener
 * JD-Core Version:    0.7.0.1
 */
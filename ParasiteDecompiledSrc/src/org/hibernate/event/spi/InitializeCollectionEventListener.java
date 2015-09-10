package org.hibernate.event.spi;

import java.io.Serializable;
import org.hibernate.HibernateException;

public abstract interface InitializeCollectionEventListener
  extends Serializable
{
  public abstract void onInitializeCollection(InitializeCollectionEvent paramInitializeCollectionEvent)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.InitializeCollectionEventListener
 * JD-Core Version:    0.7.0.1
 */
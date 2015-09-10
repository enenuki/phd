package org.hibernate.event.spi;

import java.io.Serializable;
import org.hibernate.HibernateException;

public abstract interface ReplicateEventListener
  extends Serializable
{
  public abstract void onReplicate(ReplicateEvent paramReplicateEvent)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.ReplicateEventListener
 * JD-Core Version:    0.7.0.1
 */
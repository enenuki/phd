package org.hibernate.event.spi;

import java.io.Serializable;
import java.util.Set;
import org.hibernate.HibernateException;

public abstract interface DeleteEventListener
  extends Serializable
{
  public abstract void onDelete(DeleteEvent paramDeleteEvent)
    throws HibernateException;
  
  public abstract void onDelete(DeleteEvent paramDeleteEvent, Set paramSet)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.DeleteEventListener
 * JD-Core Version:    0.7.0.1
 */
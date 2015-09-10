package org.hibernate.event.spi;

import java.io.Serializable;
import org.hibernate.HibernateException;

public abstract interface DirtyCheckEventListener
  extends Serializable
{
  public abstract void onDirtyCheck(DirtyCheckEvent paramDirtyCheckEvent)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.DirtyCheckEventListener
 * JD-Core Version:    0.7.0.1
 */
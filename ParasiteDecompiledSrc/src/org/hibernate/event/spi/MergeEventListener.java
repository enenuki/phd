package org.hibernate.event.spi;

import java.io.Serializable;
import java.util.Map;
import org.hibernate.HibernateException;

public abstract interface MergeEventListener
  extends Serializable
{
  public abstract void onMerge(MergeEvent paramMergeEvent)
    throws HibernateException;
  
  public abstract void onMerge(MergeEvent paramMergeEvent, Map paramMap)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.MergeEventListener
 * JD-Core Version:    0.7.0.1
 */
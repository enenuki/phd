package org.hibernate.event.spi;

import java.io.Serializable;
import java.util.Map;
import org.hibernate.HibernateException;

public abstract interface RefreshEventListener
  extends Serializable
{
  public abstract void onRefresh(RefreshEvent paramRefreshEvent)
    throws HibernateException;
  
  public abstract void onRefresh(RefreshEvent paramRefreshEvent, Map paramMap)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.RefreshEventListener
 * JD-Core Version:    0.7.0.1
 */
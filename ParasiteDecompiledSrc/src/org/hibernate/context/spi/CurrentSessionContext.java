package org.hibernate.context.spi;

import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public abstract interface CurrentSessionContext
  extends Serializable
{
  public abstract Session currentSession()
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.context.spi.CurrentSessionContext
 * JD-Core Version:    0.7.0.1
 */
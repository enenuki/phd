package org.hibernate.exception.spi;

import java.util.Properties;
import org.hibernate.HibernateException;

public abstract interface Configurable
{
  public abstract void configure(Properties paramProperties)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.exception.spi.Configurable
 * JD-Core Version:    0.7.0.1
 */
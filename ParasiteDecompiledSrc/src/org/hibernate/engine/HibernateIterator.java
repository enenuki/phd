package org.hibernate.engine;

import java.util.Iterator;
import org.hibernate.JDBCException;

public abstract interface HibernateIterator
  extends Iterator
{
  public abstract void close()
    throws JDBCException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.HibernateIterator
 * JD-Core Version:    0.7.0.1
 */
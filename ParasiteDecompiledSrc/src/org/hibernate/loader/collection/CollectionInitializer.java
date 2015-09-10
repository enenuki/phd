package org.hibernate.loader.collection;

import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

public abstract interface CollectionInitializer
{
  public abstract void initialize(Serializable paramSerializable, SessionImplementor paramSessionImplementor)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.collection.CollectionInitializer
 * JD-Core Version:    0.7.0.1
 */
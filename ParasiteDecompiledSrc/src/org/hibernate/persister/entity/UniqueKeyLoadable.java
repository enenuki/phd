package org.hibernate.persister.entity;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

public abstract interface UniqueKeyLoadable
  extends Loadable
{
  public abstract Object loadByUniqueKey(String paramString, Object paramObject, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract int getPropertyIndex(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.entity.UniqueKeyLoadable
 * JD-Core Version:    0.7.0.1
 */
package org.hibernate.loader.entity;

import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.engine.spi.SessionImplementor;

public abstract interface UniqueEntityLoader
{
  /**
   * @deprecated
   */
  public abstract Object load(Serializable paramSerializable, Object paramObject, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract Object load(Serializable paramSerializable, Object paramObject, SessionImplementor paramSessionImplementor, LockOptions paramLockOptions);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.entity.UniqueEntityLoader
 * JD-Core Version:    0.7.0.1
 */
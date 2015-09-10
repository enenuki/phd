package org.hibernate.event.spi;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.spi.ActionQueue;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.persister.entity.EntityPersister;

public abstract interface EventSource
  extends SessionImplementor, Session
{
  public abstract ActionQueue getActionQueue();
  
  public abstract Object instantiate(EntityPersister paramEntityPersister, Serializable paramSerializable)
    throws HibernateException;
  
  public abstract void forceFlush(EntityEntry paramEntityEntry)
    throws HibernateException;
  
  public abstract void merge(String paramString, Object paramObject, Map paramMap)
    throws HibernateException;
  
  public abstract void persist(String paramString, Object paramObject, Map paramMap)
    throws HibernateException;
  
  public abstract void persistOnFlush(String paramString, Object paramObject, Map paramMap);
  
  public abstract void refresh(Object paramObject, Map paramMap)
    throws HibernateException;
  
  public abstract void delete(String paramString, Object paramObject, boolean paramBoolean, Set paramSet);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.EventSource
 * JD-Core Version:    0.7.0.1
 */
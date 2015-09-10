package org.hibernate.type;

import java.lang.reflect.Method;
import org.hibernate.EntityMode;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.CascadeStyle;
import org.hibernate.engine.spi.SessionImplementor;

public abstract interface CompositeType
  extends Type
{
  public abstract Type[] getSubtypes();
  
  public abstract String[] getPropertyNames();
  
  public abstract boolean[] getPropertyNullability();
  
  public abstract Object[] getPropertyValues(Object paramObject, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract Object[] getPropertyValues(Object paramObject, EntityMode paramEntityMode)
    throws HibernateException;
  
  public abstract Object getPropertyValue(Object paramObject, int paramInt, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract void setPropertyValues(Object paramObject, Object[] paramArrayOfObject, EntityMode paramEntityMode)
    throws HibernateException;
  
  public abstract CascadeStyle getCascadeStyle(int paramInt);
  
  public abstract FetchMode getFetchMode(int paramInt);
  
  public abstract boolean isMethodOf(Method paramMethod);
  
  public abstract boolean isEmbedded();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.CompositeType
 * JD-Core Version:    0.7.0.1
 */
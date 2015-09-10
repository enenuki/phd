package org.hibernate.tuple.entity;

import java.io.Serializable;
import java.util.Map;
import org.hibernate.EntityMode;
import org.hibernate.EntityNameResolver;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.property.Getter;
import org.hibernate.tuple.Tuplizer;

public abstract interface EntityTuplizer
  extends Tuplizer
{
  public abstract EntityMode getEntityMode();
  
  /**
   * @deprecated
   */
  public abstract Object instantiate(Serializable paramSerializable)
    throws HibernateException;
  
  public abstract Object instantiate(Serializable paramSerializable, SessionImplementor paramSessionImplementor);
  
  /**
   * @deprecated
   */
  public abstract Serializable getIdentifier(Object paramObject)
    throws HibernateException;
  
  public abstract Serializable getIdentifier(Object paramObject, SessionImplementor paramSessionImplementor);
  
  /**
   * @deprecated
   */
  public abstract void setIdentifier(Object paramObject, Serializable paramSerializable)
    throws HibernateException;
  
  public abstract void setIdentifier(Object paramObject, Serializable paramSerializable, SessionImplementor paramSessionImplementor);
  
  /**
   * @deprecated
   */
  public abstract void resetIdentifier(Object paramObject1, Serializable paramSerializable, Object paramObject2);
  
  public abstract void resetIdentifier(Object paramObject1, Serializable paramSerializable, Object paramObject2, SessionImplementor paramSessionImplementor);
  
  public abstract Object getVersion(Object paramObject)
    throws HibernateException;
  
  public abstract void setPropertyValue(Object paramObject1, int paramInt, Object paramObject2)
    throws HibernateException;
  
  public abstract void setPropertyValue(Object paramObject1, String paramString, Object paramObject2)
    throws HibernateException;
  
  public abstract Object[] getPropertyValuesToInsert(Object paramObject, Map paramMap, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract Object getPropertyValue(Object paramObject, String paramString)
    throws HibernateException;
  
  public abstract void afterInitialize(Object paramObject, boolean paramBoolean, SessionImplementor paramSessionImplementor);
  
  public abstract boolean hasProxy();
  
  public abstract Object createProxy(Serializable paramSerializable, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract boolean isLifecycleImplementor();
  
  public abstract Class getConcreteProxyClass();
  
  public abstract boolean hasUninitializedLazyProperties(Object paramObject);
  
  public abstract boolean isInstrumented();
  
  public abstract EntityNameResolver[] getEntityNameResolvers();
  
  public abstract String determineConcreteSubclassEntityName(Object paramObject, SessionFactoryImplementor paramSessionFactoryImplementor);
  
  public abstract Getter getIdentifierGetter();
  
  public abstract Getter getVersionGetter();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.entity.EntityTuplizer
 * JD-Core Version:    0.7.0.1
 */
package org.hibernate.metadata;

import java.io.Serializable;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.Type;

public abstract interface ClassMetadata
{
  public abstract String getEntityName();
  
  public abstract String getIdentifierPropertyName();
  
  public abstract String[] getPropertyNames();
  
  public abstract Type getIdentifierType();
  
  public abstract Type[] getPropertyTypes();
  
  public abstract Type getPropertyType(String paramString)
    throws HibernateException;
  
  public abstract boolean hasProxy();
  
  public abstract boolean isMutable();
  
  public abstract boolean isVersioned();
  
  public abstract int getVersionProperty();
  
  public abstract boolean[] getPropertyNullability();
  
  public abstract boolean[] getPropertyLaziness();
  
  public abstract boolean hasIdentifierProperty();
  
  public abstract boolean hasNaturalIdentifier();
  
  public abstract int[] getNaturalIdentifierProperties();
  
  public abstract boolean hasSubclasses();
  
  public abstract boolean isInherited();
  
  public abstract Object[] getPropertyValuesToInsert(Object paramObject, Map paramMap, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract Class getMappedClass();
  
  public abstract Object instantiate(Serializable paramSerializable, SessionImplementor paramSessionImplementor);
  
  public abstract Object getPropertyValue(Object paramObject, String paramString)
    throws HibernateException;
  
  public abstract Object[] getPropertyValues(Object paramObject)
    throws HibernateException;
  
  public abstract void setPropertyValue(Object paramObject1, String paramString, Object paramObject2)
    throws HibernateException;
  
  public abstract void setPropertyValues(Object paramObject, Object[] paramArrayOfObject)
    throws HibernateException;
  
  /**
   * @deprecated
   */
  public abstract Serializable getIdentifier(Object paramObject)
    throws HibernateException;
  
  public abstract Serializable getIdentifier(Object paramObject, SessionImplementor paramSessionImplementor);
  
  public abstract void setIdentifier(Object paramObject, Serializable paramSerializable, SessionImplementor paramSessionImplementor);
  
  public abstract boolean implementsLifecycle();
  
  public abstract Object getVersion(Object paramObject)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metadata.ClassMetadata
 * JD-Core Version:    0.7.0.1
 */
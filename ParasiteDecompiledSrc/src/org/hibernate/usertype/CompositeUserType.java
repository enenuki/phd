package org.hibernate.usertype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.Type;

public abstract interface CompositeUserType
{
  public abstract String[] getPropertyNames();
  
  public abstract Type[] getPropertyTypes();
  
  public abstract Object getPropertyValue(Object paramObject, int paramInt)
    throws HibernateException;
  
  public abstract void setPropertyValue(Object paramObject1, int paramInt, Object paramObject2)
    throws HibernateException;
  
  public abstract Class returnedClass();
  
  public abstract boolean equals(Object paramObject1, Object paramObject2)
    throws HibernateException;
  
  public abstract int hashCode(Object paramObject)
    throws HibernateException;
  
  public abstract Object nullSafeGet(ResultSet paramResultSet, String[] paramArrayOfString, SessionImplementor paramSessionImplementor, Object paramObject)
    throws HibernateException, SQLException;
  
  public abstract void nullSafeSet(PreparedStatement paramPreparedStatement, Object paramObject, int paramInt, SessionImplementor paramSessionImplementor)
    throws HibernateException, SQLException;
  
  public abstract Object deepCopy(Object paramObject)
    throws HibernateException;
  
  public abstract boolean isMutable();
  
  public abstract Serializable disassemble(Object paramObject, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract Object assemble(Serializable paramSerializable, SessionImplementor paramSessionImplementor, Object paramObject)
    throws HibernateException;
  
  public abstract Object replace(Object paramObject1, Object paramObject2, SessionImplementor paramSessionImplementor, Object paramObject3)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.usertype.CompositeUserType
 * JD-Core Version:    0.7.0.1
 */
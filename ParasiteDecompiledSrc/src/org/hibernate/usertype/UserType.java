package org.hibernate.usertype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

public abstract interface UserType
{
  public abstract int[] sqlTypes();
  
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
  
  public abstract Serializable disassemble(Object paramObject)
    throws HibernateException;
  
  public abstract Object assemble(Serializable paramSerializable, Object paramObject)
    throws HibernateException;
  
  public abstract Object replace(Object paramObject1, Object paramObject2, Object paramObject3)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.usertype.UserType
 * JD-Core Version:    0.7.0.1
 */
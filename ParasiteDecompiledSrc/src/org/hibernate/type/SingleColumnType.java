package org.hibernate.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

public abstract interface SingleColumnType<T>
  extends Type
{
  public abstract int sqlType();
  
  public abstract String toString(T paramT)
    throws HibernateException;
  
  public abstract T fromStringValue(String paramString)
    throws HibernateException;
  
  public abstract T nullSafeGet(ResultSet paramResultSet, String paramString, SessionImplementor paramSessionImplementor)
    throws HibernateException, SQLException;
  
  public abstract Object get(ResultSet paramResultSet, String paramString, SessionImplementor paramSessionImplementor)
    throws HibernateException, SQLException;
  
  public abstract void set(PreparedStatement paramPreparedStatement, T paramT, int paramInt, SessionImplementor paramSessionImplementor)
    throws HibernateException, SQLException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.SingleColumnType
 * JD-Core Version:    0.7.0.1
 */
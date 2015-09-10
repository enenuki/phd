package org.hibernate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.hibernate.type.Type;

public abstract interface ScrollableResults
{
  public abstract boolean next()
    throws HibernateException;
  
  public abstract boolean previous()
    throws HibernateException;
  
  public abstract boolean scroll(int paramInt)
    throws HibernateException;
  
  public abstract boolean last()
    throws HibernateException;
  
  public abstract boolean first()
    throws HibernateException;
  
  public abstract void beforeFirst()
    throws HibernateException;
  
  public abstract void afterLast()
    throws HibernateException;
  
  public abstract boolean isFirst()
    throws HibernateException;
  
  public abstract boolean isLast()
    throws HibernateException;
  
  public abstract void close()
    throws HibernateException;
  
  public abstract Object[] get()
    throws HibernateException;
  
  public abstract Object get(int paramInt)
    throws HibernateException;
  
  public abstract Type getType(int paramInt);
  
  public abstract Integer getInteger(int paramInt)
    throws HibernateException;
  
  public abstract Long getLong(int paramInt)
    throws HibernateException;
  
  public abstract Float getFloat(int paramInt)
    throws HibernateException;
  
  public abstract Boolean getBoolean(int paramInt)
    throws HibernateException;
  
  public abstract Double getDouble(int paramInt)
    throws HibernateException;
  
  public abstract Short getShort(int paramInt)
    throws HibernateException;
  
  public abstract Byte getByte(int paramInt)
    throws HibernateException;
  
  public abstract Character getCharacter(int paramInt)
    throws HibernateException;
  
  public abstract byte[] getBinary(int paramInt)
    throws HibernateException;
  
  public abstract String getText(int paramInt)
    throws HibernateException;
  
  public abstract Blob getBlob(int paramInt)
    throws HibernateException;
  
  public abstract Clob getClob(int paramInt)
    throws HibernateException;
  
  public abstract String getString(int paramInt)
    throws HibernateException;
  
  public abstract BigDecimal getBigDecimal(int paramInt)
    throws HibernateException;
  
  public abstract BigInteger getBigInteger(int paramInt)
    throws HibernateException;
  
  public abstract Date getDate(int paramInt)
    throws HibernateException;
  
  public abstract Locale getLocale(int paramInt)
    throws HibernateException;
  
  public abstract Calendar getCalendar(int paramInt)
    throws HibernateException;
  
  public abstract TimeZone getTimeZone(int paramInt)
    throws HibernateException;
  
  public abstract int getRowNumber()
    throws HibernateException;
  
  public abstract boolean setRowNumber(int paramInt)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.ScrollableResults
 * JD-Core Version:    0.7.0.1
 */
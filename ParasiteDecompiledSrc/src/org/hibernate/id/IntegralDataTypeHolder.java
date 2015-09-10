package org.hibernate.id;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract interface IntegralDataTypeHolder
  extends Serializable
{
  public abstract IntegralDataTypeHolder initialize(long paramLong);
  
  public abstract IntegralDataTypeHolder initialize(ResultSet paramResultSet, long paramLong)
    throws SQLException;
  
  public abstract void bind(PreparedStatement paramPreparedStatement, int paramInt)
    throws SQLException;
  
  public abstract IntegralDataTypeHolder increment();
  
  public abstract IntegralDataTypeHolder add(long paramLong);
  
  public abstract IntegralDataTypeHolder decrement();
  
  public abstract IntegralDataTypeHolder subtract(long paramLong);
  
  public abstract IntegralDataTypeHolder multiplyBy(IntegralDataTypeHolder paramIntegralDataTypeHolder);
  
  public abstract IntegralDataTypeHolder multiplyBy(long paramLong);
  
  public abstract boolean eq(IntegralDataTypeHolder paramIntegralDataTypeHolder);
  
  public abstract boolean eq(long paramLong);
  
  public abstract boolean lt(IntegralDataTypeHolder paramIntegralDataTypeHolder);
  
  public abstract boolean lt(long paramLong);
  
  public abstract boolean gt(IntegralDataTypeHolder paramIntegralDataTypeHolder);
  
  public abstract boolean gt(long paramLong);
  
  public abstract IntegralDataTypeHolder copy();
  
  public abstract Number makeValue();
  
  public abstract Number makeValueThenIncrement();
  
  public abstract Number makeValueThenAdd(long paramLong);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.IntegralDataTypeHolder
 * JD-Core Version:    0.7.0.1
 */
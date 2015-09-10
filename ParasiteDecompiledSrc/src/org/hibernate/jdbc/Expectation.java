package org.hibernate.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.hibernate.HibernateException;

public abstract interface Expectation
{
  public abstract void verifyOutcome(int paramInt1, PreparedStatement paramPreparedStatement, int paramInt2)
    throws SQLException, HibernateException;
  
  public abstract int prepare(PreparedStatement paramPreparedStatement)
    throws SQLException, HibernateException;
  
  public abstract boolean canBeBatched();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.jdbc.Expectation
 * JD-Core Version:    0.7.0.1
 */
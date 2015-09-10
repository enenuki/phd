package org.hibernate.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public abstract interface ReturningWork<T>
{
  public abstract T execute(Connection paramConnection)
    throws SQLException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.jdbc.ReturningWork
 * JD-Core Version:    0.7.0.1
 */
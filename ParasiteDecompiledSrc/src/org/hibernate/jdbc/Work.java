package org.hibernate.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public abstract interface Work
{
  public abstract void execute(Connection paramConnection)
    throws SQLException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.jdbc.Work
 * JD-Core Version:    0.7.0.1
 */
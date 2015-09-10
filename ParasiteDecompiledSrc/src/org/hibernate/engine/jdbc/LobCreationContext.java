package org.hibernate.engine.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public abstract interface LobCreationContext
{
  public abstract <T> T execute(Callback<T> paramCallback);
  
  public static abstract interface Callback<T>
  {
    public abstract T executeOnConnection(Connection paramConnection)
      throws SQLException;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.LobCreationContext
 * JD-Core Version:    0.7.0.1
 */
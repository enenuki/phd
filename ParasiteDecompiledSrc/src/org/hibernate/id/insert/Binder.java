package org.hibernate.id.insert;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract interface Binder
{
  public abstract void bindValues(PreparedStatement paramPreparedStatement)
    throws SQLException;
  
  public abstract Object getEntity();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.insert.Binder
 * JD-Core Version:    0.7.0.1
 */
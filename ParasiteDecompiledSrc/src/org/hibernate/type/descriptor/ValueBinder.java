package org.hibernate.type.descriptor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract interface ValueBinder<X>
{
  public abstract void bind(PreparedStatement paramPreparedStatement, X paramX, int paramInt, WrapperOptions paramWrapperOptions)
    throws SQLException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.ValueBinder
 * JD-Core Version:    0.7.0.1
 */
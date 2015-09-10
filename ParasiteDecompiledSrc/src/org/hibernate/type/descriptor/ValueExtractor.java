package org.hibernate.type.descriptor;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract interface ValueExtractor<X>
{
  public abstract X extract(ResultSet paramResultSet, String paramString, WrapperOptions paramWrapperOptions)
    throws SQLException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.ValueExtractor
 * JD-Core Version:    0.7.0.1
 */
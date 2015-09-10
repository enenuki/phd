package org.hibernate.id;

import java.io.Serializable;
import java.sql.ResultSet;

public abstract interface ResultSetIdentifierConsumer
{
  public abstract Serializable consumeIdentifier(ResultSet paramResultSet);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.ResultSetIdentifierConsumer
 * JD-Core Version:    0.7.0.1
 */
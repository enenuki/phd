package org.hibernate.engine.jdbc.spi;

import java.sql.Connection;

public abstract interface SchemaNameResolver
{
  public abstract String resolveSchemaName(Connection paramConnection);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.spi.SchemaNameResolver
 * JD-Core Version:    0.7.0.1
 */
package org.hibernate.engine.jdbc.spi;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.LobCreationContext;
import org.hibernate.engine.jdbc.LobCreator;
import org.hibernate.service.Service;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;

public abstract interface JdbcServices
  extends Service
{
  public abstract ConnectionProvider getConnectionProvider();
  
  public abstract Dialect getDialect();
  
  public abstract SqlStatementLogger getSqlStatementLogger();
  
  public abstract SqlExceptionHelper getSqlExceptionHelper();
  
  public abstract ExtractedDatabaseMetaData getExtractedMetaDataSupport();
  
  public abstract LobCreator getLobCreator(LobCreationContext paramLobCreationContext);
  
  public abstract ResultSetWrapper getResultSetWrapper();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.spi.JdbcServices
 * JD-Core Version:    0.7.0.1
 */
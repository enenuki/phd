package org.apache.http.conn;

import java.util.concurrent.TimeUnit;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;

public abstract interface ClientConnectionManager
{
  public abstract SchemeRegistry getSchemeRegistry();
  
  public abstract ClientConnectionRequest requestConnection(HttpRoute paramHttpRoute, Object paramObject);
  
  public abstract void releaseConnection(ManagedClientConnection paramManagedClientConnection, long paramLong, TimeUnit paramTimeUnit);
  
  public abstract void closeIdleConnections(long paramLong, TimeUnit paramTimeUnit);
  
  public abstract void closeExpiredConnections();
  
  public abstract void shutdown();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.ClientConnectionManager
 * JD-Core Version:    0.7.0.1
 */
package org.apache.http.client;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScheme;

public abstract interface AuthCache
{
  public abstract void put(HttpHost paramHttpHost, AuthScheme paramAuthScheme);
  
  public abstract AuthScheme get(HttpHost paramHttpHost);
  
  public abstract void remove(HttpHost paramHttpHost);
  
  public abstract void clear();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.AuthCache
 * JD-Core Version:    0.7.0.1
 */
package org.apache.http.auth;

import org.apache.http.params.HttpParams;

public abstract interface AuthSchemeFactory
{
  public abstract AuthScheme newInstance(HttpParams paramHttpParams);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.auth.AuthSchemeFactory
 * JD-Core Version:    0.7.0.1
 */
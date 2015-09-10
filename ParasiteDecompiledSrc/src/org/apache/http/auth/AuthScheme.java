package org.apache.http.auth;

import org.apache.http.Header;
import org.apache.http.HttpRequest;

public abstract interface AuthScheme
{
  public abstract void processChallenge(Header paramHeader)
    throws MalformedChallengeException;
  
  public abstract String getSchemeName();
  
  public abstract String getParameter(String paramString);
  
  public abstract String getRealm();
  
  public abstract boolean isConnectionBased();
  
  public abstract boolean isComplete();
  
  @Deprecated
  public abstract Header authenticate(Credentials paramCredentials, HttpRequest paramHttpRequest)
    throws AuthenticationException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.auth.AuthScheme
 * JD-Core Version:    0.7.0.1
 */
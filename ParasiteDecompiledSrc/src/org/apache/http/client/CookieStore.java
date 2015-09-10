package org.apache.http.client;

import java.util.Date;
import java.util.List;
import org.apache.http.cookie.Cookie;

public abstract interface CookieStore
{
  public abstract void addCookie(Cookie paramCookie);
  
  public abstract List<Cookie> getCookies();
  
  public abstract boolean clearExpired(Date paramDate);
  
  public abstract void clear();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.CookieStore
 * JD-Core Version:    0.7.0.1
 */
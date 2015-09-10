package org.apache.http.cookie;

import java.util.Date;

public abstract interface Cookie
{
  public abstract String getName();
  
  public abstract String getValue();
  
  public abstract String getComment();
  
  public abstract String getCommentURL();
  
  public abstract Date getExpiryDate();
  
  public abstract boolean isPersistent();
  
  public abstract String getDomain();
  
  public abstract String getPath();
  
  public abstract int[] getPorts();
  
  public abstract boolean isSecure();
  
  public abstract int getVersion();
  
  public abstract boolean isExpired(Date paramDate);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.cookie.Cookie
 * JD-Core Version:    0.7.0.1
 */
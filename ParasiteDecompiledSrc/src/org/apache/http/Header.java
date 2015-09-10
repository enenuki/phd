package org.apache.http;

public abstract interface Header
{
  public abstract String getName();
  
  public abstract String getValue();
  
  public abstract HeaderElement[] getElements()
    throws ParseException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.Header
 * JD-Core Version:    0.7.0.1
 */
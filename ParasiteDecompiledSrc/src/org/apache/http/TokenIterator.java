package org.apache.http;

import java.util.Iterator;

public abstract interface TokenIterator
  extends Iterator
{
  public abstract boolean hasNext();
  
  public abstract String nextToken();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.TokenIterator
 * JD-Core Version:    0.7.0.1
 */
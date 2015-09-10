package org.apache.http;

import java.util.Iterator;

public abstract interface HeaderElementIterator
  extends Iterator
{
  public abstract boolean hasNext();
  
  public abstract HeaderElement nextElement();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.HeaderElementIterator
 * JD-Core Version:    0.7.0.1
 */
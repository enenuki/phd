package org.apache.http;

import java.util.Iterator;

public abstract interface HeaderIterator
  extends Iterator
{
  public abstract boolean hasNext();
  
  public abstract Header nextHeader();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.HeaderIterator
 * JD-Core Version:    0.7.0.1
 */
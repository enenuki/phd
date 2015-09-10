package org.apache.xerces.util;

public class SymbolHash
{
  protected int fTableSize = 101;
  protected Entry[] fBuckets;
  protected int fNum = 0;
  
  public SymbolHash()
  {
    this.fBuckets = new Entry[this.fTableSize];
  }
  
  public SymbolHash(int paramInt)
  {
    this.fTableSize = paramInt;
    this.fBuckets = new Entry[this.fTableSize];
  }
  
  public void put(Object paramObject1, Object paramObject2)
  {
    int i = (paramObject1.hashCode() & 0x7FFFFFFF) % this.fTableSize;
    Entry localEntry = search(paramObject1, i);
    if (localEntry != null)
    {
      localEntry.value = paramObject2;
    }
    else
    {
      localEntry = new Entry(paramObject1, paramObject2, this.fBuckets[i]);
      this.fBuckets[i] = localEntry;
      this.fNum += 1;
    }
  }
  
  public Object get(Object paramObject)
  {
    int i = (paramObject.hashCode() & 0x7FFFFFFF) % this.fTableSize;
    Entry localEntry = search(paramObject, i);
    if (localEntry != null) {
      return localEntry.value;
    }
    return null;
  }
  
  public int getLength()
  {
    return this.fNum;
  }
  
  public int getValues(Object[] paramArrayOfObject, int paramInt)
  {
    int i = 0;
    int j = 0;
    while ((i < this.fTableSize) && (j < this.fNum))
    {
      for (Entry localEntry = this.fBuckets[i]; localEntry != null; localEntry = localEntry.next)
      {
        paramArrayOfObject[(paramInt + j)] = localEntry.value;
        j++;
      }
      i++;
    }
    return this.fNum;
  }
  
  public SymbolHash makeClone()
  {
    SymbolHash localSymbolHash = new SymbolHash(this.fTableSize);
    localSymbolHash.fNum = this.fNum;
    for (int i = 0; i < this.fTableSize; i++) {
      if (this.fBuckets[i] != null) {
        localSymbolHash.fBuckets[i] = this.fBuckets[i].makeClone();
      }
    }
    return localSymbolHash;
  }
  
  public void clear()
  {
    for (int i = 0; i < this.fTableSize; i++) {
      this.fBuckets[i] = null;
    }
    this.fNum = 0;
  }
  
  protected Entry search(Object paramObject, int paramInt)
  {
    for (Entry localEntry = this.fBuckets[paramInt]; localEntry != null; localEntry = localEntry.next) {
      if (paramObject.equals(localEntry.key)) {
        return localEntry;
      }
    }
    return null;
  }
  
  protected static final class Entry
  {
    public Object key;
    public Object value;
    public Entry next;
    
    public Entry()
    {
      this.key = null;
      this.value = null;
      this.next = null;
    }
    
    public Entry(Object paramObject1, Object paramObject2, Entry paramEntry)
    {
      this.key = paramObject1;
      this.value = paramObject2;
      this.next = paramEntry;
    }
    
    public Entry makeClone()
    {
      Entry localEntry = new Entry();
      localEntry.key = this.key;
      localEntry.value = this.value;
      if (this.next != null) {
        localEntry.next = this.next.makeClone();
      }
      return localEntry;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.util.SymbolHash
 * JD-Core Version:    0.7.0.1
 */
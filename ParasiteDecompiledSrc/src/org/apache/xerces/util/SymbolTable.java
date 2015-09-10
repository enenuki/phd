package org.apache.xerces.util;

public class SymbolTable
{
  protected static final int TABLE_SIZE = 101;
  protected Entry[] fBuckets = null;
  protected int fTableSize;
  protected transient int fCount;
  protected int fThreshold;
  protected float fLoadFactor;
  
  public SymbolTable(int paramInt, float paramFloat)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException("Illegal Capacity: " + paramInt);
    }
    if ((paramFloat <= 0.0F) || (Float.isNaN(paramFloat))) {
      throw new IllegalArgumentException("Illegal Load: " + paramFloat);
    }
    if (paramInt == 0) {
      paramInt = 1;
    }
    this.fLoadFactor = paramFloat;
    this.fTableSize = paramInt;
    this.fBuckets = new Entry[this.fTableSize];
    this.fThreshold = ((int)(this.fTableSize * paramFloat));
    this.fCount = 0;
  }
  
  public SymbolTable(int paramInt)
  {
    this(paramInt, 0.75F);
  }
  
  public SymbolTable()
  {
    this(101, 0.75F);
  }
  
  public String addSymbol(String paramString)
  {
    int i = hash(paramString) % this.fTableSize;
    for (Entry localEntry1 = this.fBuckets[i]; localEntry1 != null; localEntry1 = localEntry1.next) {
      if (localEntry1.symbol.equals(paramString)) {
        return localEntry1.symbol;
      }
    }
    if (this.fCount >= this.fThreshold)
    {
      rehash();
      i = hash(paramString) % this.fTableSize;
    }
    Entry localEntry2 = new Entry(paramString, this.fBuckets[i]);
    this.fBuckets[i] = localEntry2;
    this.fCount += 1;
    return localEntry2.symbol;
  }
  
  public String addSymbol(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    int i = hash(paramArrayOfChar, paramInt1, paramInt2) % this.fTableSize;
    for (Entry localEntry1 = this.fBuckets[i]; localEntry1 != null; localEntry1 = localEntry1.next) {
      if (paramInt2 == localEntry1.characters.length)
      {
        int j = 0;
        while (paramArrayOfChar[(paramInt1 + j)] == localEntry1.characters[j])
        {
          j++;
          if (j >= paramInt2) {
            return localEntry1.symbol;
          }
        }
      }
    }
    if (this.fCount >= this.fThreshold)
    {
      rehash();
      i = hash(paramArrayOfChar, paramInt1, paramInt2) % this.fTableSize;
    }
    Entry localEntry2 = new Entry(paramArrayOfChar, paramInt1, paramInt2, this.fBuckets[i]);
    this.fBuckets[i] = localEntry2;
    this.fCount += 1;
    return localEntry2.symbol;
  }
  
  public int hash(String paramString)
  {
    return paramString.hashCode() & 0x7FFFFFF;
  }
  
  public int hash(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    int i = 0;
    for (int j = 0; j < paramInt2; j++) {
      i = i * 31 + paramArrayOfChar[(paramInt1 + j)];
    }
    return i & 0x7FFFFFF;
  }
  
  protected void rehash()
  {
    int i = this.fBuckets.length;
    Entry[] arrayOfEntry1 = this.fBuckets;
    int j = i * 2 + 1;
    Entry[] arrayOfEntry2 = new Entry[j];
    this.fThreshold = ((int)(j * this.fLoadFactor));
    this.fBuckets = arrayOfEntry2;
    this.fTableSize = this.fBuckets.length;
    int k = i;
    while (k-- > 0)
    {
      Entry localEntry1 = arrayOfEntry1[k];
      while (localEntry1 != null)
      {
        Entry localEntry2 = localEntry1;
        localEntry1 = localEntry1.next;
        int m = hash(localEntry2.characters, 0, localEntry2.characters.length) % j;
        localEntry2.next = arrayOfEntry2[m];
        arrayOfEntry2[m] = localEntry2;
      }
    }
  }
  
  public boolean containsSymbol(String paramString)
  {
    int i = hash(paramString) % this.fTableSize;
    int j = paramString.length();
    for (Entry localEntry = this.fBuckets[i]; localEntry != null; localEntry = localEntry.next) {
      if (j == localEntry.characters.length)
      {
        int k = 0;
        while (paramString.charAt(k) == localEntry.characters[k])
        {
          k++;
          if (k >= j) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  public boolean containsSymbol(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    int i = hash(paramArrayOfChar, paramInt1, paramInt2) % this.fTableSize;
    for (Entry localEntry = this.fBuckets[i]; localEntry != null; localEntry = localEntry.next) {
      if (paramInt2 == localEntry.characters.length)
      {
        int j = 0;
        while (paramArrayOfChar[(paramInt1 + j)] == localEntry.characters[j])
        {
          j++;
          if (j >= paramInt2) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  protected static final class Entry
  {
    public final String symbol;
    public final char[] characters;
    public Entry next;
    
    public Entry(String paramString, Entry paramEntry)
    {
      this.symbol = paramString.intern();
      this.characters = new char[paramString.length()];
      paramString.getChars(0, this.characters.length, this.characters, 0);
      this.next = paramEntry;
    }
    
    public Entry(char[] paramArrayOfChar, int paramInt1, int paramInt2, Entry paramEntry)
    {
      this.characters = new char[paramInt2];
      System.arraycopy(paramArrayOfChar, paramInt1, this.characters, 0, paramInt2);
      this.symbol = new String(this.characters).intern();
      this.next = paramEntry;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.util.SymbolTable
 * JD-Core Version:    0.7.0.1
 */
package org.apache.xerces.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class SoftReferenceSymbolTable
  extends SymbolTable
{
  protected SREntry[] fBuckets = null;
  private final ReferenceQueue fReferenceQueue;
  
  public SoftReferenceSymbolTable(int paramInt, float paramFloat)
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
    this.fBuckets = new SREntry[this.fTableSize];
    this.fThreshold = ((int)(this.fTableSize * paramFloat));
    this.fCount = 0;
    this.fReferenceQueue = new ReferenceQueue();
  }
  
  public SoftReferenceSymbolTable(int paramInt)
  {
    this(paramInt, 0.75F);
  }
  
  public SoftReferenceSymbolTable()
  {
    this(101, 0.75F);
  }
  
  public String addSymbol(String paramString)
  {
    clean();
    int i = hash(paramString) % this.fTableSize;
    for (SREntry localSREntry = this.fBuckets[i]; localSREntry != null; localSREntry = localSREntry.next)
    {
      localObject = (SREntryData)localSREntry.get();
      if ((localObject != null) && (((SREntryData)localObject).symbol.equals(paramString))) {
        return ((SREntryData)localObject).symbol;
      }
    }
    if (this.fCount >= this.fThreshold)
    {
      rehash();
      i = hash(paramString) % this.fTableSize;
    }
    paramString = paramString.intern();
    Object localObject = new SREntry(paramString, this.fBuckets[i], i, this.fReferenceQueue);
    this.fBuckets[i] = localObject;
    this.fCount += 1;
    return paramString;
  }
  
  public String addSymbol(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    clean();
    int i = hash(paramArrayOfChar, paramInt1, paramInt2) % this.fTableSize;
    for (SREntry localSREntry1 = this.fBuckets[i]; localSREntry1 != null; localSREntry1 = localSREntry1.next)
    {
      localObject = (SREntryData)localSREntry1.get();
      if ((localObject != null) && (paramInt2 == ((SREntryData)localObject).characters.length))
      {
        int j = 0;
        while (paramArrayOfChar[(paramInt1 + j)] == localObject.characters[j])
        {
          j++;
          if (j >= paramInt2) {
            return ((SREntryData)localObject).symbol;
          }
        }
      }
    }
    if (this.fCount >= this.fThreshold)
    {
      rehash();
      i = hash(paramArrayOfChar, paramInt1, paramInt2) % this.fTableSize;
    }
    Object localObject = new String(paramArrayOfChar, paramInt1, paramInt2).intern();
    SREntry localSREntry2 = new SREntry((String)localObject, paramArrayOfChar, paramInt1, paramInt2, this.fBuckets[i], i, this.fReferenceQueue);
    this.fBuckets[i] = localSREntry2;
    this.fCount += 1;
    return localObject;
  }
  
  protected void rehash()
  {
    int i = this.fBuckets.length;
    SREntry[] arrayOfSREntry1 = this.fBuckets;
    int j = i * 2 + 1;
    SREntry[] arrayOfSREntry2 = new SREntry[j];
    this.fThreshold = ((int)(j * this.fLoadFactor));
    this.fBuckets = arrayOfSREntry2;
    this.fTableSize = this.fBuckets.length;
    int k = i;
    while (k-- > 0)
    {
      SREntry localSREntry1 = arrayOfSREntry1[k];
      while (localSREntry1 != null)
      {
        SREntry localSREntry2 = localSREntry1;
        localSREntry1 = localSREntry1.next;
        SREntryData localSREntryData = (SREntryData)localSREntry2.get();
        if (localSREntryData != null)
        {
          int m = hash(localSREntryData.characters, 0, localSREntryData.characters.length) % j;
          if (arrayOfSREntry2[m] != null) {
            arrayOfSREntry2[m].prev = localSREntry2;
          }
          localSREntry2.next = arrayOfSREntry2[m];
          arrayOfSREntry2[m] = localSREntry2;
        }
        else
        {
          this.fCount -= 1;
        }
      }
    }
  }
  
  public boolean containsSymbol(String paramString)
  {
    int i = hash(paramString) % this.fTableSize;
    int j = paramString.length();
    for (SREntry localSREntry = this.fBuckets[i]; localSREntry != null; localSREntry = localSREntry.next)
    {
      SREntryData localSREntryData = (SREntryData)localSREntry.get();
      if ((localSREntryData != null) && (j == localSREntryData.characters.length))
      {
        int k = 0;
        while (paramString.charAt(k) == localSREntryData.characters[k])
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
    for (SREntry localSREntry = this.fBuckets[i]; localSREntry != null; localSREntry = localSREntry.next)
    {
      SREntryData localSREntryData = (SREntryData)localSREntry.get();
      if ((localSREntryData != null) && (paramInt2 == localSREntryData.characters.length))
      {
        int j = 0;
        while (paramArrayOfChar[(paramInt1 + j)] == localSREntryData.characters[j])
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
  
  private void removeEntry(SREntry paramSREntry)
  {
    if (paramSREntry.next != null) {
      paramSREntry.next.prev = paramSREntry.prev;
    }
    if (paramSREntry.prev != null) {
      paramSREntry.prev.next = paramSREntry.next;
    } else {
      this.fBuckets[paramSREntry.bucket] = paramSREntry.next;
    }
    this.fCount -= 1;
  }
  
  private void clean()
  {
    for (SREntry localSREntry = (SREntry)this.fReferenceQueue.poll(); localSREntry != null; localSREntry = (SREntry)this.fReferenceQueue.poll()) {
      removeEntry(localSREntry);
    }
  }
  
  protected static final class SREntryData
  {
    public final String symbol;
    public final char[] characters;
    
    public SREntryData(String paramString)
    {
      this.symbol = paramString;
      this.characters = new char[this.symbol.length()];
      this.symbol.getChars(0, this.characters.length, this.characters, 0);
    }
    
    public SREntryData(String paramString, char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      this.symbol = paramString;
      this.characters = new char[paramInt2];
      System.arraycopy(paramArrayOfChar, paramInt1, this.characters, 0, paramInt2);
    }
  }
  
  protected static final class SREntry
    extends SoftReference
  {
    public SREntry next;
    public SREntry prev;
    public int bucket;
    
    public SREntry(String paramString, SREntry paramSREntry, int paramInt, ReferenceQueue paramReferenceQueue)
    {
      super(paramReferenceQueue);
      initialize(paramSREntry, paramInt);
    }
    
    public SREntry(String paramString, char[] paramArrayOfChar, int paramInt1, int paramInt2, SREntry paramSREntry, int paramInt3, ReferenceQueue paramReferenceQueue)
    {
      super(paramReferenceQueue);
      initialize(paramSREntry, paramInt3);
    }
    
    private void initialize(SREntry paramSREntry, int paramInt)
    {
      this.next = paramSREntry;
      if (paramSREntry != null) {
        paramSREntry.prev = this;
      }
      this.prev = null;
      this.bucket = paramInt;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.util.SoftReferenceSymbolTable
 * JD-Core Version:    0.7.0.1
 */
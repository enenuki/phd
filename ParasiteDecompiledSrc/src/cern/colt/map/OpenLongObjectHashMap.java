package cern.colt.map;

import cern.colt.function.LongObjectProcedure;
import cern.colt.function.LongProcedure;
import cern.colt.list.ByteArrayList;
import cern.colt.list.LongArrayList;
import cern.colt.list.ObjectArrayList;

public class OpenLongObjectHashMap
  extends AbstractLongObjectMap
{
  protected long[] table;
  protected Object[] values;
  protected byte[] state;
  protected int freeEntries;
  protected static final byte FREE = 0;
  protected static final byte FULL = 1;
  protected static final byte REMOVED = 2;
  
  public OpenLongObjectHashMap()
  {
    this(277);
  }
  
  public OpenLongObjectHashMap(int paramInt)
  {
    this(paramInt, 0.2D, 0.5D);
  }
  
  public OpenLongObjectHashMap(int paramInt, double paramDouble1, double paramDouble2)
  {
    setUp(paramInt, paramDouble1, paramDouble2);
  }
  
  public void clear()
  {
    new ByteArrayList(this.state).fillFromToWith(0, this.state.length - 1, (byte)0);
    new ObjectArrayList(this.values).fillFromToWith(0, this.state.length - 1, null);
    this.distinct = 0;
    this.freeEntries = this.table.length;
    trimToSize();
  }
  
  public Object clone()
  {
    OpenLongObjectHashMap localOpenLongObjectHashMap = (OpenLongObjectHashMap)super.clone();
    localOpenLongObjectHashMap.table = ((long[])localOpenLongObjectHashMap.table.clone());
    localOpenLongObjectHashMap.values = ((Object[])localOpenLongObjectHashMap.values.clone());
    localOpenLongObjectHashMap.state = ((byte[])localOpenLongObjectHashMap.state.clone());
    return localOpenLongObjectHashMap;
  }
  
  public boolean containsKey(long paramLong)
  {
    return indexOfKey(paramLong) >= 0;
  }
  
  public boolean containsValue(Object paramObject)
  {
    return indexOfValue(paramObject) >= 0;
  }
  
  public void ensureCapacity(int paramInt)
  {
    if (this.table.length < paramInt)
    {
      int i = nextPrime(paramInt);
      rehash(i);
    }
  }
  
  public boolean forEachKey(LongProcedure paramLongProcedure)
  {
    int i = this.table.length;
    while (i-- > 0) {
      if ((this.state[i] == 1) && (!paramLongProcedure.apply(this.table[i]))) {
        return false;
      }
    }
    return true;
  }
  
  public boolean forEachPair(LongObjectProcedure paramLongObjectProcedure)
  {
    int i = this.table.length;
    while (i-- > 0) {
      if ((this.state[i] == 1) && (!paramLongObjectProcedure.apply(this.table[i], this.values[i]))) {
        return false;
      }
    }
    return true;
  }
  
  public Object get(long paramLong)
  {
    int i = indexOfKey(paramLong);
    if (i < 0) {
      return null;
    }
    return this.values[i];
  }
  
  protected int indexOfInsertion(long paramLong)
  {
    long[] arrayOfLong = this.table;
    byte[] arrayOfByte = this.state;
    int i = arrayOfLong.length;
    int j = HashFunctions.hash(paramLong) & 0x7FFFFFFF;
    int k = j % i;
    int m = j % (i - 2);
    if (m == 0) {
      m = 1;
    }
    while ((arrayOfByte[k] == 1) && (arrayOfLong[k] != paramLong))
    {
      k -= m;
      if (k < 0) {
        k += i;
      }
    }
    if (arrayOfByte[k] == 2)
    {
      int n = k;
      while ((arrayOfByte[k] != 0) && ((arrayOfByte[k] == 2) || (arrayOfLong[k] != paramLong)))
      {
        k -= m;
        if (k < 0) {
          k += i;
        }
      }
      if (arrayOfByte[k] == 0) {
        k = n;
      }
    }
    if (arrayOfByte[k] == 1) {
      return -k - 1;
    }
    return k;
  }
  
  protected int indexOfKey(long paramLong)
  {
    long[] arrayOfLong = this.table;
    byte[] arrayOfByte = this.state;
    int i = arrayOfLong.length;
    int j = HashFunctions.hash(paramLong) & 0x7FFFFFFF;
    int k = j % i;
    int m = j % (i - 2);
    if (m == 0) {
      m = 1;
    }
    while ((arrayOfByte[k] != 0) && ((arrayOfByte[k] == 2) || (arrayOfLong[k] != paramLong)))
    {
      k -= m;
      if (k < 0) {
        k += i;
      }
    }
    if (arrayOfByte[k] == 0) {
      return -1;
    }
    return k;
  }
  
  protected int indexOfValue(Object paramObject)
  {
    Object[] arrayOfObject = this.values;
    byte[] arrayOfByte = this.state;
    int i = arrayOfByte.length;
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while ((arrayOfByte[i] != 1) || (arrayOfObject[i] != paramObject));
    return i;
    return -1;
  }
  
  public long keyOf(Object paramObject)
  {
    int i = indexOfValue(paramObject);
    if (i < 0) {
      return -9223372036854775808L;
    }
    return this.table[i];
  }
  
  public void keys(LongArrayList paramLongArrayList)
  {
    paramLongArrayList.setSize(this.distinct);
    long[] arrayOfLong1 = paramLongArrayList.elements();
    long[] arrayOfLong2 = this.table;
    byte[] arrayOfByte = this.state;
    int i = 0;
    int j = arrayOfLong2.length;
    while (j-- > 0) {
      if (arrayOfByte[j] == 1) {
        arrayOfLong1[(i++)] = arrayOfLong2[j];
      }
    }
  }
  
  public void pairsMatching(LongObjectProcedure paramLongObjectProcedure, LongArrayList paramLongArrayList, ObjectArrayList paramObjectArrayList)
  {
    paramLongArrayList.clear();
    paramObjectArrayList.clear();
    int i = this.table.length;
    while (i-- > 0) {
      if ((this.state[i] == 1) && (paramLongObjectProcedure.apply(this.table[i], this.values[i])))
      {
        paramLongArrayList.add(this.table[i]);
        paramObjectArrayList.add(this.values[i]);
      }
    }
  }
  
  public boolean put(long paramLong, Object paramObject)
  {
    int i = indexOfInsertion(paramLong);
    if (i < 0)
    {
      i = -i - 1;
      this.values[i] = paramObject;
      return false;
    }
    int j;
    if (this.distinct > this.highWaterMark)
    {
      j = chooseGrowCapacity(this.distinct + 1, this.minLoadFactor, this.maxLoadFactor);
      rehash(j);
      return put(paramLong, paramObject);
    }
    this.table[i] = paramLong;
    this.values[i] = paramObject;
    if (this.state[i] == 0) {
      this.freeEntries -= 1;
    }
    this.state[i] = 1;
    this.distinct += 1;
    if (this.freeEntries < 1)
    {
      j = chooseGrowCapacity(this.distinct + 1, this.minLoadFactor, this.maxLoadFactor);
      rehash(j);
    }
    return true;
  }
  
  protected void rehash(int paramInt)
  {
    int i = this.table.length;
    long[] arrayOfLong1 = this.table;
    Object[] arrayOfObject1 = this.values;
    byte[] arrayOfByte1 = this.state;
    long[] arrayOfLong2 = new long[paramInt];
    Object[] arrayOfObject2 = new Object[paramInt];
    byte[] arrayOfByte2 = new byte[paramInt];
    this.lowWaterMark = chooseLowWaterMark(paramInt, this.minLoadFactor);
    this.highWaterMark = chooseHighWaterMark(paramInt, this.maxLoadFactor);
    this.table = arrayOfLong2;
    this.values = arrayOfObject2;
    this.state = arrayOfByte2;
    this.freeEntries = (paramInt - this.distinct);
    int j = i;
    while (j-- > 0) {
      if (arrayOfByte1[j] == 1)
      {
        long l = arrayOfLong1[j];
        int k = indexOfInsertion(l);
        arrayOfLong2[k] = l;
        arrayOfObject2[k] = arrayOfObject1[j];
        arrayOfByte2[k] = 1;
      }
    }
  }
  
  public boolean removeKey(long paramLong)
  {
    int i = indexOfKey(paramLong);
    if (i < 0) {
      return false;
    }
    this.state[i] = 2;
    this.values[i] = null;
    this.distinct -= 1;
    if (this.distinct < this.lowWaterMark)
    {
      int j = chooseShrinkCapacity(this.distinct, this.minLoadFactor, this.maxLoadFactor);
      rehash(j);
    }
    return true;
  }
  
  protected void setUp(int paramInt, double paramDouble1, double paramDouble2)
  {
    int i = paramInt;
    super.setUp(i, paramDouble1, paramDouble2);
    i = nextPrime(i);
    if (i == 0) {
      i = 1;
    }
    this.table = new long[i];
    this.values = new Object[i];
    this.state = new byte[i];
    this.minLoadFactor = paramDouble1;
    if (i == 2147483647) {
      this.maxLoadFactor = 1.0D;
    } else {
      this.maxLoadFactor = paramDouble2;
    }
    this.distinct = 0;
    this.freeEntries = i;
    this.lowWaterMark = 0;
    this.highWaterMark = chooseHighWaterMark(i, this.maxLoadFactor);
  }
  
  public void trimToSize()
  {
    int i = nextPrime((int)(1.0D + 1.2D * size()));
    if (this.table.length > i) {
      rehash(i);
    }
  }
  
  public void values(ObjectArrayList paramObjectArrayList)
  {
    paramObjectArrayList.setSize(this.distinct);
    Object[] arrayOfObject1 = paramObjectArrayList.elements();
    Object[] arrayOfObject2 = this.values;
    byte[] arrayOfByte = this.state;
    int i = 0;
    int j = arrayOfByte.length;
    while (j-- > 0) {
      if (arrayOfByte[j] == 1) {
        arrayOfObject1[(i++)] = arrayOfObject2[j];
      }
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.map.OpenLongObjectHashMap
 * JD-Core Version:    0.7.0.1
 */
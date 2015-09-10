package cern.colt.map;

import cern.colt.function.DoubleFunction;
import cern.colt.function.IntDoubleProcedure;
import cern.colt.function.IntProcedure;
import cern.colt.list.ByteArrayList;
import cern.colt.list.DoubleArrayList;
import cern.colt.list.IntArrayList;
import cern.jet.math.Mult;

public class OpenIntDoubleHashMap
  extends AbstractIntDoubleMap
{
  protected int[] table;
  protected double[] values;
  protected byte[] state;
  protected int freeEntries;
  protected static final byte FREE = 0;
  protected static final byte FULL = 1;
  protected static final byte REMOVED = 2;
  
  public OpenIntDoubleHashMap()
  {
    this(277);
  }
  
  public OpenIntDoubleHashMap(int paramInt)
  {
    this(paramInt, 0.2D, 0.5D);
  }
  
  public OpenIntDoubleHashMap(int paramInt, double paramDouble1, double paramDouble2)
  {
    setUp(paramInt, paramDouble1, paramDouble2);
  }
  
  public void assign(DoubleFunction paramDoubleFunction)
  {
    if ((paramDoubleFunction instanceof Mult))
    {
      double d = ((Mult)paramDoubleFunction).multiplicator;
      if (d == 1.0D) {
        return;
      }
      if (d == 0.0D)
      {
        clear();
        return;
      }
      int j = this.table.length;
      while (j-- > 0) {
        if (this.state[j] == 1) {
          this.values[j] *= d;
        }
      }
    }
    int i = this.table.length;
    while (i-- > 0) {
      if (this.state[i] == 1) {
        this.values[i] = paramDoubleFunction.apply(this.values[i]);
      }
    }
  }
  
  public void assign(AbstractIntDoubleMap paramAbstractIntDoubleMap)
  {
    if (!(paramAbstractIntDoubleMap instanceof OpenIntDoubleHashMap))
    {
      super.assign(paramAbstractIntDoubleMap);
      return;
    }
    OpenIntDoubleHashMap localOpenIntDoubleHashMap1 = (OpenIntDoubleHashMap)paramAbstractIntDoubleMap;
    OpenIntDoubleHashMap localOpenIntDoubleHashMap2 = (OpenIntDoubleHashMap)localOpenIntDoubleHashMap1.copy();
    this.values = localOpenIntDoubleHashMap2.values;
    this.table = localOpenIntDoubleHashMap2.table;
    this.state = localOpenIntDoubleHashMap2.state;
    this.freeEntries = localOpenIntDoubleHashMap2.freeEntries;
    this.distinct = localOpenIntDoubleHashMap2.distinct;
    this.lowWaterMark = localOpenIntDoubleHashMap2.lowWaterMark;
    this.highWaterMark = localOpenIntDoubleHashMap2.highWaterMark;
    this.minLoadFactor = localOpenIntDoubleHashMap2.minLoadFactor;
    this.maxLoadFactor = localOpenIntDoubleHashMap2.maxLoadFactor;
  }
  
  public void clear()
  {
    new ByteArrayList(this.state).fillFromToWith(0, this.state.length - 1, (byte)0);
    this.distinct = 0;
    this.freeEntries = this.table.length;
    trimToSize();
  }
  
  public Object clone()
  {
    OpenIntDoubleHashMap localOpenIntDoubleHashMap = (OpenIntDoubleHashMap)super.clone();
    localOpenIntDoubleHashMap.table = ((int[])localOpenIntDoubleHashMap.table.clone());
    localOpenIntDoubleHashMap.values = ((double[])localOpenIntDoubleHashMap.values.clone());
    localOpenIntDoubleHashMap.state = ((byte[])localOpenIntDoubleHashMap.state.clone());
    return localOpenIntDoubleHashMap;
  }
  
  public boolean containsKey(int paramInt)
  {
    return indexOfKey(paramInt) >= 0;
  }
  
  public boolean containsValue(double paramDouble)
  {
    return indexOfValue(paramDouble) >= 0;
  }
  
  public void ensureCapacity(int paramInt)
  {
    if (this.table.length < paramInt)
    {
      int i = nextPrime(paramInt);
      rehash(i);
    }
  }
  
  public boolean forEachKey(IntProcedure paramIntProcedure)
  {
    int i = this.table.length;
    while (i-- > 0) {
      if ((this.state[i] == 1) && (!paramIntProcedure.apply(this.table[i]))) {
        return false;
      }
    }
    return true;
  }
  
  public boolean forEachPair(IntDoubleProcedure paramIntDoubleProcedure)
  {
    int i = this.table.length;
    while (i-- > 0) {
      if ((this.state[i] == 1) && (!paramIntDoubleProcedure.apply(this.table[i], this.values[i]))) {
        return false;
      }
    }
    return true;
  }
  
  public double get(int paramInt)
  {
    int i = indexOfKey(paramInt);
    if (i < 0) {
      return 0.0D;
    }
    return this.values[i];
  }
  
  protected int indexOfInsertion(int paramInt)
  {
    int[] arrayOfInt = this.table;
    byte[] arrayOfByte = this.state;
    int i = arrayOfInt.length;
    int j = HashFunctions.hash(paramInt) & 0x7FFFFFFF;
    int k = j % i;
    int m = j % (i - 2);
    if (m == 0) {
      m = 1;
    }
    while ((arrayOfByte[k] == 1) && (arrayOfInt[k] != paramInt))
    {
      k -= m;
      if (k < 0) {
        k += i;
      }
    }
    if (arrayOfByte[k] == 2)
    {
      int n = k;
      while ((arrayOfByte[k] != 0) && ((arrayOfByte[k] == 2) || (arrayOfInt[k] != paramInt)))
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
  
  protected int indexOfKey(int paramInt)
  {
    int[] arrayOfInt = this.table;
    byte[] arrayOfByte = this.state;
    int i = arrayOfInt.length;
    int j = HashFunctions.hash(paramInt) & 0x7FFFFFFF;
    int k = j % i;
    int m = j % (i - 2);
    if (m == 0) {
      m = 1;
    }
    while ((arrayOfByte[k] != 0) && ((arrayOfByte[k] == 2) || (arrayOfInt[k] != paramInt)))
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
  
  protected int indexOfValue(double paramDouble)
  {
    double[] arrayOfDouble = this.values;
    byte[] arrayOfByte = this.state;
    int i = arrayOfByte.length;
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while ((arrayOfByte[i] != 1) || (arrayOfDouble[i] != paramDouble));
    return i;
    return -1;
  }
  
  public int keyOf(double paramDouble)
  {
    int i = indexOfValue(paramDouble);
    if (i < 0) {
      return -2147483648;
    }
    return this.table[i];
  }
  
  public void keys(IntArrayList paramIntArrayList)
  {
    paramIntArrayList.setSize(this.distinct);
    int[] arrayOfInt1 = paramIntArrayList.elements();
    int[] arrayOfInt2 = this.table;
    byte[] arrayOfByte = this.state;
    int i = 0;
    int j = arrayOfInt2.length;
    while (j-- > 0) {
      if (arrayOfByte[j] == 1) {
        arrayOfInt1[(i++)] = arrayOfInt2[j];
      }
    }
  }
  
  public void pairsMatching(IntDoubleProcedure paramIntDoubleProcedure, IntArrayList paramIntArrayList, DoubleArrayList paramDoubleArrayList)
  {
    paramIntArrayList.clear();
    paramDoubleArrayList.clear();
    int i = this.table.length;
    while (i-- > 0) {
      if ((this.state[i] == 1) && (paramIntDoubleProcedure.apply(this.table[i], this.values[i])))
      {
        paramIntArrayList.add(this.table[i]);
        paramDoubleArrayList.add(this.values[i]);
      }
    }
  }
  
  public boolean put(int paramInt, double paramDouble)
  {
    int i = indexOfInsertion(paramInt);
    if (i < 0)
    {
      i = -i - 1;
      this.values[i] = paramDouble;
      return false;
    }
    int j;
    if (this.distinct > this.highWaterMark)
    {
      j = chooseGrowCapacity(this.distinct + 1, this.minLoadFactor, this.maxLoadFactor);
      rehash(j);
      return put(paramInt, paramDouble);
    }
    this.table[i] = paramInt;
    this.values[i] = paramDouble;
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
    if (paramInt <= this.distinct) {
      throw new InternalError();
    }
    int[] arrayOfInt1 = this.table;
    double[] arrayOfDouble1 = this.values;
    byte[] arrayOfByte1 = this.state;
    int[] arrayOfInt2 = new int[paramInt];
    double[] arrayOfDouble2 = new double[paramInt];
    byte[] arrayOfByte2 = new byte[paramInt];
    this.lowWaterMark = chooseLowWaterMark(paramInt, this.minLoadFactor);
    this.highWaterMark = chooseHighWaterMark(paramInt, this.maxLoadFactor);
    this.table = arrayOfInt2;
    this.values = arrayOfDouble2;
    this.state = arrayOfByte2;
    this.freeEntries = (paramInt - this.distinct);
    int j = i;
    while (j-- > 0) {
      if (arrayOfByte1[j] == 1)
      {
        int k = arrayOfInt1[j];
        int m = indexOfInsertion(k);
        arrayOfInt2[m] = k;
        arrayOfDouble2[m] = arrayOfDouble1[j];
        arrayOfByte2[m] = 1;
      }
    }
  }
  
  public boolean removeKey(int paramInt)
  {
    int i = indexOfKey(paramInt);
    if (i < 0) {
      return false;
    }
    this.state[i] = 2;
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
    this.table = new int[i];
    this.values = new double[i];
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
  
  public void values(DoubleArrayList paramDoubleArrayList)
  {
    paramDoubleArrayList.setSize(this.distinct);
    double[] arrayOfDouble1 = paramDoubleArrayList.elements();
    double[] arrayOfDouble2 = this.values;
    byte[] arrayOfByte = this.state;
    int i = 0;
    int j = arrayOfByte.length;
    while (j-- > 0) {
      if (arrayOfByte[j] == 1) {
        arrayOfDouble1[(i++)] = arrayOfDouble2[j];
      }
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.map.OpenIntDoubleHashMap
 * JD-Core Version:    0.7.0.1
 */
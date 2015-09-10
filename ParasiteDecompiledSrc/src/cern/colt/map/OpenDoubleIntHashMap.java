package cern.colt.map;

import cern.colt.function.DoubleIntProcedure;
import cern.colt.function.DoubleProcedure;
import cern.colt.list.ByteArrayList;
import cern.colt.list.DoubleArrayList;
import cern.colt.list.IntArrayList;

public class OpenDoubleIntHashMap
  extends AbstractDoubleIntMap
{
  protected double[] table;
  protected int[] values;
  protected byte[] state;
  protected int freeEntries;
  protected static final byte FREE = 0;
  protected static final byte FULL = 1;
  protected static final byte REMOVED = 2;
  
  public OpenDoubleIntHashMap()
  {
    this(277);
  }
  
  public OpenDoubleIntHashMap(int paramInt)
  {
    this(paramInt, 0.2D, 0.5D);
  }
  
  public OpenDoubleIntHashMap(int paramInt, double paramDouble1, double paramDouble2)
  {
    setUp(paramInt, paramDouble1, paramDouble2);
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
    OpenDoubleIntHashMap localOpenDoubleIntHashMap = (OpenDoubleIntHashMap)super.clone();
    localOpenDoubleIntHashMap.table = ((double[])localOpenDoubleIntHashMap.table.clone());
    localOpenDoubleIntHashMap.values = ((int[])localOpenDoubleIntHashMap.values.clone());
    localOpenDoubleIntHashMap.state = ((byte[])localOpenDoubleIntHashMap.state.clone());
    return localOpenDoubleIntHashMap;
  }
  
  public boolean containsKey(double paramDouble)
  {
    return indexOfKey(paramDouble) >= 0;
  }
  
  public boolean containsValue(int paramInt)
  {
    return indexOfValue(paramInt) >= 0;
  }
  
  public void ensureCapacity(int paramInt)
  {
    if (this.table.length < paramInt)
    {
      int i = nextPrime(paramInt);
      rehash(i);
    }
  }
  
  public boolean forEachKey(DoubleProcedure paramDoubleProcedure)
  {
    int i = this.table.length;
    while (i-- > 0) {
      if ((this.state[i] == 1) && (!paramDoubleProcedure.apply(this.table[i]))) {
        return false;
      }
    }
    return true;
  }
  
  public boolean forEachPair(DoubleIntProcedure paramDoubleIntProcedure)
  {
    int i = this.table.length;
    while (i-- > 0) {
      if ((this.state[i] == 1) && (!paramDoubleIntProcedure.apply(this.table[i], this.values[i]))) {
        return false;
      }
    }
    return true;
  }
  
  public int get(double paramDouble)
  {
    int i = indexOfKey(paramDouble);
    if (i < 0) {
      return 0;
    }
    return this.values[i];
  }
  
  protected int indexOfInsertion(double paramDouble)
  {
    double[] arrayOfDouble = this.table;
    byte[] arrayOfByte = this.state;
    int i = arrayOfDouble.length;
    int j = HashFunctions.hash(paramDouble) & 0x7FFFFFFF;
    int k = j % i;
    int m = j % (i - 2);
    if (m == 0) {
      m = 1;
    }
    while ((arrayOfByte[k] == 1) && (arrayOfDouble[k] != paramDouble))
    {
      k -= m;
      if (k < 0) {
        k += i;
      }
    }
    if (arrayOfByte[k] == 2)
    {
      int n = k;
      while ((arrayOfByte[k] != 0) && ((arrayOfByte[k] == 2) || (arrayOfDouble[k] != paramDouble)))
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
  
  protected int indexOfKey(double paramDouble)
  {
    double[] arrayOfDouble = this.table;
    byte[] arrayOfByte = this.state;
    int i = arrayOfDouble.length;
    int j = HashFunctions.hash(paramDouble) & 0x7FFFFFFF;
    int k = j % i;
    int m = j % (i - 2);
    if (m == 0) {
      m = 1;
    }
    while ((arrayOfByte[k] != 0) && ((arrayOfByte[k] == 2) || (arrayOfDouble[k] != paramDouble)))
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
  
  protected int indexOfValue(int paramInt)
  {
    int[] arrayOfInt = this.values;
    byte[] arrayOfByte = this.state;
    int i = arrayOfByte.length;
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while ((arrayOfByte[i] != 1) || (arrayOfInt[i] != paramInt));
    return i;
    return -1;
  }
  
  public double keyOf(int paramInt)
  {
    int i = indexOfValue(paramInt);
    if (i < 0) {
      return (0.0D / 0.0D);
    }
    return this.table[i];
  }
  
  public void keys(DoubleArrayList paramDoubleArrayList)
  {
    paramDoubleArrayList.setSize(this.distinct);
    double[] arrayOfDouble1 = paramDoubleArrayList.elements();
    double[] arrayOfDouble2 = this.table;
    byte[] arrayOfByte = this.state;
    int i = 0;
    int j = arrayOfDouble2.length;
    while (j-- > 0) {
      if (arrayOfByte[j] == 1) {
        arrayOfDouble1[(i++)] = arrayOfDouble2[j];
      }
    }
  }
  
  public void pairsMatching(DoubleIntProcedure paramDoubleIntProcedure, DoubleArrayList paramDoubleArrayList, IntArrayList paramIntArrayList)
  {
    paramDoubleArrayList.clear();
    paramIntArrayList.clear();
    int i = this.table.length;
    while (i-- > 0) {
      if ((this.state[i] == 1) && (paramDoubleIntProcedure.apply(this.table[i], this.values[i])))
      {
        paramDoubleArrayList.add(this.table[i]);
        paramIntArrayList.add(this.values[i]);
      }
    }
  }
  
  public boolean put(double paramDouble, int paramInt)
  {
    int i = indexOfInsertion(paramDouble);
    if (i < 0)
    {
      i = -i - 1;
      this.values[i] = paramInt;
      return false;
    }
    int j;
    if (this.distinct > this.highWaterMark)
    {
      j = chooseGrowCapacity(this.distinct + 1, this.minLoadFactor, this.maxLoadFactor);
      rehash(j);
      return put(paramDouble, paramInt);
    }
    this.table[i] = paramDouble;
    this.values[i] = paramInt;
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
    double[] arrayOfDouble1 = this.table;
    int[] arrayOfInt1 = this.values;
    byte[] arrayOfByte1 = this.state;
    double[] arrayOfDouble2 = new double[paramInt];
    int[] arrayOfInt2 = new int[paramInt];
    byte[] arrayOfByte2 = new byte[paramInt];
    this.lowWaterMark = chooseLowWaterMark(paramInt, this.minLoadFactor);
    this.highWaterMark = chooseHighWaterMark(paramInt, this.maxLoadFactor);
    this.table = arrayOfDouble2;
    this.values = arrayOfInt2;
    this.state = arrayOfByte2;
    this.freeEntries = (paramInt - this.distinct);
    int j = i;
    while (j-- > 0) {
      if (arrayOfByte1[j] == 1)
      {
        double d = arrayOfDouble1[j];
        int k = indexOfInsertion(d);
        arrayOfDouble2[k] = d;
        arrayOfInt2[k] = arrayOfInt1[j];
        arrayOfByte2[k] = 1;
      }
    }
  }
  
  public boolean removeKey(double paramDouble)
  {
    int i = indexOfKey(paramDouble);
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
    this.table = new double[i];
    this.values = new int[i];
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
  
  public void values(IntArrayList paramIntArrayList)
  {
    paramIntArrayList.setSize(this.distinct);
    int[] arrayOfInt1 = paramIntArrayList.elements();
    int[] arrayOfInt2 = this.values;
    byte[] arrayOfByte = this.state;
    int i = 0;
    int j = arrayOfByte.length;
    while (j-- > 0) {
      if (arrayOfByte[j] == 1) {
        arrayOfInt1[(i++)] = arrayOfInt2[j];
      }
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.map.OpenDoubleIntHashMap
 * JD-Core Version:    0.7.0.1
 */
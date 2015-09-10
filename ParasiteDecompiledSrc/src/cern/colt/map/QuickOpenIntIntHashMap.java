package cern.colt.map;

class QuickOpenIntIntHashMap
  extends OpenIntIntHashMap
{
  public int totalProbesSaved = 0;
  
  public QuickOpenIntIntHashMap()
  {
    this(277);
  }
  
  public QuickOpenIntIntHashMap(int paramInt)
  {
    this(paramInt, 0.2D, 0.5D);
  }
  
  public QuickOpenIntIntHashMap(int paramInt, double paramDouble1, double paramDouble2)
  {
    setUp(paramInt, paramDouble1, paramDouble2);
  }
  
  public boolean put(int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = this.table;
    byte[] arrayOfByte = this.state;
    int j = arrayOfInt.length;
    int k = HashFunctions.hash(paramInt1) & 0x7FFFFFFF;
    int m = k % j;
    int n = k / j % j;
    if (n == 0) {
      n = 1;
    }
    int i1 = 0;
    int i2 = m;
    while ((arrayOfByte[m] == 1) && (arrayOfInt[m] != paramInt1))
    {
      i1++;
      m -= n;
      if (m < 0) {
        m += j;
      }
    }
    if (arrayOfByte[m] == 1)
    {
      this.values[m] = paramInt2;
      return false;
    }
    int i3;
    if (this.distinct > this.highWaterMark)
    {
      i3 = chooseGrowCapacity(this.distinct + 1, this.minLoadFactor, this.maxLoadFactor);
      rehash(i3);
      return put(paramInt1, paramInt2);
    }
    while (i1 > 1)
    {
      int i = arrayOfInt[i2];
      k = HashFunctions.hash(i) & 0x7FFFFFFF;
      n = k / j % j;
      if (n == 0) {
        n = 1;
      }
      i3 = i2 - n;
      if (i3 < 0) {
        i3 += j;
      }
      if (arrayOfByte[i3] != 0)
      {
        i2 = i3;
        i1--;
      }
      else
      {
        this.totalProbesSaved += i1 - 1;
        arrayOfInt[i3] = i;
        arrayOfByte[i3] = 1;
        this.values[i3] = this.values[i2];
        m = i2;
        i1 = 0;
      }
    }
    this.table[m] = paramInt1;
    this.values[m] = paramInt2;
    if (this.state[m] == 0) {
      this.freeEntries -= 1;
    }
    this.state[m] = 1;
    this.distinct += 1;
    if (this.freeEntries < 1)
    {
      i3 = chooseGrowCapacity(this.distinct + 1, this.minLoadFactor, this.maxLoadFactor);
      rehash(i3);
    }
    return true;
  }
  
  protected void rehash(int paramInt)
  {
    int i = this.table.length;
    int[] arrayOfInt1 = this.table;
    int[] arrayOfInt2 = this.values;
    byte[] arrayOfByte1 = this.state;
    int[] arrayOfInt3 = new int[paramInt];
    int[] arrayOfInt4 = new int[paramInt];
    byte[] arrayOfByte2 = new byte[paramInt];
    this.lowWaterMark = chooseLowWaterMark(paramInt, this.minLoadFactor);
    this.highWaterMark = chooseHighWaterMark(paramInt, this.maxLoadFactor);
    this.table = arrayOfInt3;
    this.values = arrayOfInt4;
    this.state = arrayOfByte2;
    this.freeEntries = (paramInt - this.distinct);
    int j = this.distinct;
    this.distinct = -2147483648;
    int k = i;
    while (k-- > 0) {
      if (arrayOfByte1[k] == 1) {
        put(arrayOfInt1[k], arrayOfInt2[k]);
      }
    }
    this.distinct = j;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.map.QuickOpenIntIntHashMap
 * JD-Core Version:    0.7.0.1
 */
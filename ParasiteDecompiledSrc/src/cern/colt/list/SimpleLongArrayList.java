package cern.colt.list;

import cern.colt.Arrays;

public class SimpleLongArrayList
  extends AbstractLongList
{
  protected long[] elements;
  protected int size;
  
  public SimpleLongArrayList()
  {
    this(10);
  }
  
  public SimpleLongArrayList(long[] paramArrayOfLong)
  {
    elements(paramArrayOfLong);
  }
  
  public SimpleLongArrayList(int paramInt)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException("Illegal Capacity: " + paramInt);
    }
    elements(new long[paramInt]);
    this.size = 0;
  }
  
  public void ensureCapacity(int paramInt)
  {
    this.elements = Arrays.ensureCapacity(this.elements, paramInt);
  }
  
  protected long getQuick(int paramInt)
  {
    return this.elements[paramInt];
  }
  
  protected void setQuick(int paramInt, long paramLong)
  {
    this.elements[paramInt] = paramLong;
  }
  
  public void trimToSize()
  {
    this.elements = Arrays.trimToCapacity(this.elements, size());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.SimpleLongArrayList
 * JD-Core Version:    0.7.0.1
 */
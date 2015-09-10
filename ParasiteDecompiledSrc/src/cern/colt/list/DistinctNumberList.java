package cern.colt.list;

import java.util.Arrays;

public class DistinctNumberList
  extends AbstractLongList
{
  protected long[] distinctValues;
  protected MinMaxNumberList elements;
  
  public DistinctNumberList(long[] paramArrayOfLong, int paramInt)
  {
    setUp(paramArrayOfLong, paramInt);
  }
  
  public void add(long paramLong)
  {
    this.elements.add(codeOf(paramLong));
    this.size += 1;
  }
  
  protected int codeOf(long paramLong)
  {
    int i = Arrays.binarySearch(this.distinctValues, paramLong);
    if (i < 0) {
      throw new IllegalArgumentException("Element=" + paramLong + " not contained in distinct elements.");
    }
    return i;
  }
  
  public void ensureCapacity(int paramInt)
  {
    this.elements.ensureCapacity(paramInt);
  }
  
  public long getQuick(int paramInt)
  {
    return this.distinctValues[((int)this.elements.getQuick(paramInt))];
  }
  
  public void removeFromTo(int paramInt1, int paramInt2)
  {
    this.elements.removeFromTo(paramInt1, paramInt2);
    this.size -= paramInt2 - paramInt1 + 1;
  }
  
  public void setQuick(int paramInt, long paramLong)
  {
    this.elements.setQuick(paramInt, codeOf(paramLong));
  }
  
  protected void setSizeRaw(int paramInt)
  {
    super.setSizeRaw(paramInt);
    this.elements.setSizeRaw(paramInt);
  }
  
  protected void setUp(long[] paramArrayOfLong, int paramInt)
  {
    this.distinctValues = paramArrayOfLong;
    this.elements = new MinMaxNumberList(0L, paramArrayOfLong.length - 1, paramInt);
  }
  
  public void trimToSize()
  {
    this.elements.trimToSize();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.DistinctNumberList
 * JD-Core Version:    0.7.0.1
 */
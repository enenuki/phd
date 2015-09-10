package cern.colt.buffer;

import cern.colt.PersistentObject;
import cern.colt.list.DoubleArrayList;

public class DoubleBuffer2D
  extends PersistentObject
  implements DoubleBuffer2DConsumer
{
  protected DoubleBuffer2DConsumer target;
  protected double[] xElements;
  protected double[] yElements;
  protected DoubleArrayList xList;
  protected DoubleArrayList yList;
  protected int capacity;
  protected int size;
  
  public DoubleBuffer2D(DoubleBuffer2DConsumer paramDoubleBuffer2DConsumer, int paramInt)
  {
    this.target = paramDoubleBuffer2DConsumer;
    this.capacity = paramInt;
    this.xElements = new double[paramInt];
    this.yElements = new double[paramInt];
    this.xList = new DoubleArrayList(this.xElements);
    this.yList = new DoubleArrayList(this.yElements);
    this.size = 0;
  }
  
  public void add(double paramDouble1, double paramDouble2)
  {
    if (this.size == this.capacity) {
      flush();
    }
    this.xElements[this.size] = paramDouble1;
    this.yElements[(this.size++)] = paramDouble2;
  }
  
  public void addAllOf(DoubleArrayList paramDoubleArrayList1, DoubleArrayList paramDoubleArrayList2)
  {
    int i = paramDoubleArrayList1.size();
    if (this.size + i >= this.capacity) {
      flush();
    }
    this.target.addAllOf(paramDoubleArrayList1, paramDoubleArrayList2);
  }
  
  public void clear()
  {
    this.size = 0;
  }
  
  public void flush()
  {
    if (this.size > 0)
    {
      this.xList.setSize(this.size);
      this.yList.setSize(this.size);
      this.target.addAllOf(this.xList, this.yList);
      this.size = 0;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.buffer.DoubleBuffer2D
 * JD-Core Version:    0.7.0.1
 */
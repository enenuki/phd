package cern.colt.buffer;

import cern.colt.PersistentObject;
import cern.colt.list.DoubleArrayList;

public class DoubleBuffer3D
  extends PersistentObject
  implements DoubleBuffer3DConsumer
{
  protected DoubleBuffer3DConsumer target;
  protected double[] xElements;
  protected double[] yElements;
  protected double[] zElements;
  protected DoubleArrayList xList;
  protected DoubleArrayList yList;
  protected DoubleArrayList zList;
  protected int capacity;
  protected int size;
  
  public DoubleBuffer3D(DoubleBuffer3DConsumer paramDoubleBuffer3DConsumer, int paramInt)
  {
    this.target = paramDoubleBuffer3DConsumer;
    this.capacity = paramInt;
    this.xElements = new double[paramInt];
    this.yElements = new double[paramInt];
    this.zElements = new double[paramInt];
    this.xList = new DoubleArrayList(this.xElements);
    this.yList = new DoubleArrayList(this.yElements);
    this.zList = new DoubleArrayList(this.zElements);
    this.size = 0;
  }
  
  public void add(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    if (this.size == this.capacity) {
      flush();
    }
    this.xElements[this.size] = paramDouble1;
    this.yElements[this.size] = paramDouble2;
    this.zElements[(this.size++)] = paramDouble3;
  }
  
  public void addAllOf(DoubleArrayList paramDoubleArrayList1, DoubleArrayList paramDoubleArrayList2, DoubleArrayList paramDoubleArrayList3)
  {
    int i = paramDoubleArrayList1.size();
    if (this.size + i >= this.capacity) {
      flush();
    }
    this.target.addAllOf(paramDoubleArrayList1, paramDoubleArrayList2, paramDoubleArrayList3);
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
      this.zList.setSize(this.size);
      this.target.addAllOf(this.xList, this.yList, this.zList);
      this.size = 0;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.buffer.DoubleBuffer3D
 * JD-Core Version:    0.7.0.1
 */
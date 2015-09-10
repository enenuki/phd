package cern.colt.buffer;

import cern.colt.PersistentObject;
import cern.colt.list.DoubleArrayList;

public class DoubleBuffer
  extends PersistentObject
  implements DoubleBufferConsumer
{
  protected DoubleBufferConsumer target;
  protected double[] elements;
  protected DoubleArrayList list;
  protected int capacity;
  protected int size;
  
  public DoubleBuffer(DoubleBufferConsumer paramDoubleBufferConsumer, int paramInt)
  {
    this.target = paramDoubleBufferConsumer;
    this.capacity = paramInt;
    this.elements = new double[paramInt];
    this.list = new DoubleArrayList(this.elements);
    this.size = 0;
  }
  
  public void add(double paramDouble)
  {
    if (this.size == this.capacity) {
      flush();
    }
    this.elements[(this.size++)] = paramDouble;
  }
  
  public void addAllOf(DoubleArrayList paramDoubleArrayList)
  {
    int i = paramDoubleArrayList.size();
    if (this.size + i >= this.capacity) {
      flush();
    }
    this.target.addAllOf(paramDoubleArrayList);
  }
  
  public void clear()
  {
    this.size = 0;
  }
  
  public void flush()
  {
    if (this.size > 0)
    {
      this.list.setSize(this.size);
      this.target.addAllOf(this.list);
      this.size = 0;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.buffer.DoubleBuffer
 * JD-Core Version:    0.7.0.1
 */
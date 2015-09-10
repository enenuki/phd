package cern.colt.buffer;

import cern.colt.PersistentObject;
import cern.colt.list.IntArrayList;

public class IntBuffer2D
  extends PersistentObject
  implements IntBuffer2DConsumer
{
  protected IntBuffer2DConsumer target;
  protected int[] xElements;
  protected int[] yElements;
  protected IntArrayList xList;
  protected IntArrayList yList;
  protected int capacity;
  protected int size;
  
  public IntBuffer2D(IntBuffer2DConsumer paramIntBuffer2DConsumer, int paramInt)
  {
    this.target = paramIntBuffer2DConsumer;
    this.capacity = paramInt;
    this.xElements = new int[paramInt];
    this.yElements = new int[paramInt];
    this.xList = new IntArrayList(this.xElements);
    this.yList = new IntArrayList(this.yElements);
    this.size = 0;
  }
  
  public void add(int paramInt1, int paramInt2)
  {
    if (this.size == this.capacity) {
      flush();
    }
    this.xElements[this.size] = paramInt1;
    this.yElements[(this.size++)] = paramInt2;
  }
  
  public void addAllOf(IntArrayList paramIntArrayList1, IntArrayList paramIntArrayList2)
  {
    int i = paramIntArrayList1.size();
    if (this.size + i >= this.capacity) {
      flush();
    }
    this.target.addAllOf(paramIntArrayList1, paramIntArrayList2);
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
 * Qualified Name:     cern.colt.buffer.IntBuffer2D
 * JD-Core Version:    0.7.0.1
 */
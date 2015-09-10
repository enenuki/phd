package cern.colt.buffer;

import cern.colt.PersistentObject;
import cern.colt.list.IntArrayList;

public class IntBuffer
  extends PersistentObject
  implements IntBufferConsumer
{
  protected IntBufferConsumer target;
  protected int[] elements;
  protected IntArrayList list;
  protected int capacity;
  protected int size;
  
  public IntBuffer(IntBufferConsumer paramIntBufferConsumer, int paramInt)
  {
    this.target = paramIntBufferConsumer;
    this.capacity = paramInt;
    this.elements = new int[paramInt];
    this.list = new IntArrayList(this.elements);
    this.size = 0;
  }
  
  public void add(int paramInt)
  {
    if (this.size == this.capacity) {
      flush();
    }
    this.elements[(this.size++)] = paramInt;
  }
  
  public void addAllOf(IntArrayList paramIntArrayList)
  {
    int i = paramIntArrayList.size();
    if (this.size + i >= this.capacity) {
      flush();
    }
    this.target.addAllOf(paramIntArrayList);
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
 * Qualified Name:     cern.colt.buffer.IntBuffer
 * JD-Core Version:    0.7.0.1
 */
package cern.colt.buffer;

import cern.colt.PersistentObject;
import cern.colt.list.IntArrayList;

public class IntBuffer3D
  extends PersistentObject
  implements IntBuffer3DConsumer
{
  protected IntBuffer3DConsumer target;
  protected int[] xElements;
  protected int[] yElements;
  protected int[] zElements;
  protected IntArrayList xList;
  protected IntArrayList yList;
  protected IntArrayList zList;
  protected int capacity;
  protected int size;
  
  public IntBuffer3D(IntBuffer3DConsumer paramIntBuffer3DConsumer, int paramInt)
  {
    this.target = paramIntBuffer3DConsumer;
    this.capacity = paramInt;
    this.xElements = new int[paramInt];
    this.yElements = new int[paramInt];
    this.zElements = new int[paramInt];
    this.xList = new IntArrayList(this.xElements);
    this.yList = new IntArrayList(this.yElements);
    this.zList = new IntArrayList(this.zElements);
    this.size = 0;
  }
  
  public void add(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.size == this.capacity) {
      flush();
    }
    this.xElements[this.size] = paramInt1;
    this.yElements[this.size] = paramInt2;
    this.zElements[(this.size++)] = paramInt3;
  }
  
  public void addAllOf(IntArrayList paramIntArrayList1, IntArrayList paramIntArrayList2, IntArrayList paramIntArrayList3)
  {
    int i = paramIntArrayList1.size();
    if (this.size + i >= this.capacity) {
      flush();
    }
    this.target.addAllOf(paramIntArrayList1, paramIntArrayList2, paramIntArrayList3);
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
 * Qualified Name:     cern.colt.buffer.IntBuffer3D
 * JD-Core Version:    0.7.0.1
 */
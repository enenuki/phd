package cern.colt.buffer;

import cern.colt.PersistentObject;
import cern.colt.list.ObjectArrayList;

public class ObjectBuffer
  extends PersistentObject
  implements ObjectBufferConsumer
{
  protected ObjectBufferConsumer target;
  protected Object[] elements;
  protected ObjectArrayList list;
  protected int capacity;
  protected int size;
  
  public ObjectBuffer(ObjectBufferConsumer paramObjectBufferConsumer, int paramInt)
  {
    this.target = paramObjectBufferConsumer;
    this.capacity = paramInt;
    this.elements = new Object[paramInt];
    this.list = new ObjectArrayList(this.elements);
    this.size = 0;
  }
  
  public void add(Object paramObject)
  {
    if (this.size == this.capacity) {
      flush();
    }
    this.elements[(this.size++)] = paramObject;
  }
  
  public void addAllOf(ObjectArrayList paramObjectArrayList)
  {
    int i = paramObjectArrayList.size();
    if (this.size + i >= this.capacity) {
      flush();
    }
    this.target.addAllOf(paramObjectArrayList);
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
 * Qualified Name:     cern.colt.buffer.ObjectBuffer
 * JD-Core Version:    0.7.0.1
 */
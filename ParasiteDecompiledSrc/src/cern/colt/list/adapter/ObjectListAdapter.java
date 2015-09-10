package cern.colt.list.adapter;

import cern.colt.list.ObjectArrayList;
import java.util.AbstractList;
import java.util.List;

public class ObjectListAdapter
  extends AbstractList
  implements List
{
  protected ObjectArrayList content;
  
  public ObjectListAdapter(ObjectArrayList paramObjectArrayList)
  {
    this.content = paramObjectArrayList;
  }
  
  public void add(int paramInt, Object paramObject)
  {
    this.content.beforeInsert(paramInt, paramObject);
    this.modCount += 1;
  }
  
  public Object get(int paramInt)
  {
    return this.content.get(paramInt);
  }
  
  public Object remove(int paramInt)
  {
    Object localObject = get(paramInt);
    this.content.remove(paramInt);
    this.modCount += 1;
    return localObject;
  }
  
  public Object set(int paramInt, Object paramObject)
  {
    Object localObject = get(paramInt);
    this.content.set(paramInt, paramObject);
    return localObject;
  }
  
  public int size()
  {
    return this.content.size();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.adapter.ObjectListAdapter
 * JD-Core Version:    0.7.0.1
 */
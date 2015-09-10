package cern.colt.list.adapter;

import cern.colt.list.AbstractIntList;
import java.util.AbstractList;
import java.util.List;

public class IntListAdapter
  extends AbstractList
  implements List
{
  protected AbstractIntList content;
  
  public IntListAdapter(AbstractIntList paramAbstractIntList)
  {
    this.content = paramAbstractIntList;
  }
  
  public void add(int paramInt, Object paramObject)
  {
    this.content.beforeInsert(paramInt, value(paramObject));
    this.modCount += 1;
  }
  
  public Object get(int paramInt)
  {
    return object(this.content.get(paramInt));
  }
  
  protected static Object object(int paramInt)
  {
    return new Integer(paramInt);
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
    this.content.set(paramInt, value(paramObject));
    return localObject;
  }
  
  public int size()
  {
    return this.content.size();
  }
  
  protected static int value(Object paramObject)
  {
    return ((Number)paramObject).intValue();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.adapter.IntListAdapter
 * JD-Core Version:    0.7.0.1
 */
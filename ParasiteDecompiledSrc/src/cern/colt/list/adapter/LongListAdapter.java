package cern.colt.list.adapter;

import cern.colt.list.AbstractLongList;
import java.util.AbstractList;
import java.util.List;

public class LongListAdapter
  extends AbstractList
  implements List
{
  protected AbstractLongList content;
  
  public LongListAdapter(AbstractLongList paramAbstractLongList)
  {
    this.content = paramAbstractLongList;
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
  
  protected static Object object(long paramLong)
  {
    return new Long(paramLong);
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
  
  protected static long value(Object paramObject)
  {
    return ((Number)paramObject).longValue();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.adapter.LongListAdapter
 * JD-Core Version:    0.7.0.1
 */
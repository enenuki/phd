package cern.colt.list.adapter;

import cern.colt.list.AbstractFloatList;
import java.util.AbstractList;
import java.util.List;

public class FloatListAdapter
  extends AbstractList
  implements List
{
  protected AbstractFloatList content;
  
  public FloatListAdapter(AbstractFloatList paramAbstractFloatList)
  {
    this.content = paramAbstractFloatList;
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
  
  protected static Object object(float paramFloat)
  {
    return new Float(paramFloat);
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
  
  protected static float value(Object paramObject)
  {
    return ((Number)paramObject).floatValue();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.adapter.FloatListAdapter
 * JD-Core Version:    0.7.0.1
 */
package cern.colt.map;

import cern.colt.GenericSorting;
import cern.colt.Swapper;
import cern.colt.function.IntComparator;
import cern.colt.function.LongObjectProcedure;
import cern.colt.function.LongProcedure;
import cern.colt.list.LongArrayList;
import cern.colt.list.ObjectArrayList;

public abstract class AbstractLongObjectMap
  extends AbstractMap
{
  public boolean containsKey(long paramLong)
  {
    !forEachKey(new LongProcedure()
    {
      private final long val$key;
      
      public boolean apply(long paramAnonymousLong)
      {
        return this.val$key != paramAnonymousLong;
      }
    });
  }
  
  public boolean containsValue(Object paramObject)
  {
    !forEachPair(new LongObjectProcedure()
    {
      private final Object val$value;
      
      public boolean apply(long paramAnonymousLong, Object paramAnonymousObject)
      {
        return this.val$value != paramAnonymousObject;
      }
    });
  }
  
  public AbstractLongObjectMap copy()
  {
    return (AbstractLongObjectMap)clone();
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof AbstractLongObjectMap)) {
      return false;
    }
    AbstractLongObjectMap localAbstractLongObjectMap = (AbstractLongObjectMap)paramObject;
    if (localAbstractLongObjectMap.size() != size()) {
      return false;
    }
    (forEachPair(new LongObjectProcedure()
    {
      private final AbstractLongObjectMap val$other;
      
      public boolean apply(long paramAnonymousLong, Object paramAnonymousObject)
      {
        return (this.val$other.containsKey(paramAnonymousLong)) && (this.val$other.get(paramAnonymousLong) == paramAnonymousObject);
      }
    })) && (localAbstractLongObjectMap.forEachPair(new LongObjectProcedure()
    {
      public boolean apply(long paramAnonymousLong, Object paramAnonymousObject)
      {
        return (AbstractLongObjectMap.this.containsKey(paramAnonymousLong)) && (AbstractLongObjectMap.this.get(paramAnonymousLong) == paramAnonymousObject);
      }
    }));
  }
  
  public abstract boolean forEachKey(LongProcedure paramLongProcedure);
  
  public boolean forEachPair(LongObjectProcedure paramLongObjectProcedure)
  {
    forEachKey(new LongProcedure()
    {
      private final LongObjectProcedure val$procedure;
      
      public boolean apply(long paramAnonymousLong)
      {
        return this.val$procedure.apply(paramAnonymousLong, AbstractLongObjectMap.this.get(paramAnonymousLong));
      }
    });
  }
  
  public abstract Object get(long paramLong);
  
  public long keyOf(Object paramObject)
  {
    long[] arrayOfLong = new long[1];
    boolean bool = forEachPair(new LongObjectProcedure()
    {
      private final Object val$value;
      private final long[] val$foundKey;
      
      public boolean apply(long paramAnonymousLong, Object paramAnonymousObject)
      {
        int i = this.val$value == paramAnonymousObject ? 1 : 0;
        if (i != 0) {
          this.val$foundKey[0] = paramAnonymousLong;
        }
        return i == 0;
      }
    });
    if (bool) {
      return -9223372036854775808L;
    }
    return arrayOfLong[0];
  }
  
  public LongArrayList keys()
  {
    LongArrayList localLongArrayList = new LongArrayList(size());
    keys(localLongArrayList);
    return localLongArrayList;
  }
  
  public void keys(LongArrayList paramLongArrayList)
  {
    paramLongArrayList.clear();
    forEachKey(new LongProcedure()
    {
      private final LongArrayList val$list;
      
      public boolean apply(long paramAnonymousLong)
      {
        this.val$list.add(paramAnonymousLong);
        return true;
      }
    });
  }
  
  public void keysSortedByValue(LongArrayList paramLongArrayList)
  {
    pairsSortedByValue(paramLongArrayList, new ObjectArrayList(size()));
  }
  
  public void pairsMatching(LongObjectProcedure paramLongObjectProcedure, LongArrayList paramLongArrayList, ObjectArrayList paramObjectArrayList)
  {
    paramLongArrayList.clear();
    paramObjectArrayList.clear();
    forEachPair(new LongObjectProcedure()
    {
      private final LongObjectProcedure val$condition;
      private final LongArrayList val$keyList;
      private final ObjectArrayList val$valueList;
      
      public boolean apply(long paramAnonymousLong, Object paramAnonymousObject)
      {
        if (this.val$condition.apply(paramAnonymousLong, paramAnonymousObject))
        {
          this.val$keyList.add(paramAnonymousLong);
          this.val$valueList.add(paramAnonymousObject);
        }
        return true;
      }
    });
  }
  
  public void pairsSortedByKey(LongArrayList paramLongArrayList, ObjectArrayList paramObjectArrayList)
  {
    keys(paramLongArrayList);
    paramLongArrayList.sort();
    paramObjectArrayList.setSize(paramLongArrayList.size());
    int i = paramLongArrayList.size();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      paramObjectArrayList.setQuick(i, get(paramLongArrayList.getQuick(i)));
    }
  }
  
  public void pairsSortedByValue(LongArrayList paramLongArrayList, ObjectArrayList paramObjectArrayList)
  {
    keys(paramLongArrayList);
    values(paramObjectArrayList);
    long[] arrayOfLong = paramLongArrayList.elements();
    Object[] arrayOfObject = paramObjectArrayList.elements();
    Swapper local9 = new Swapper()
    {
      private final Object[] val$v;
      private final long[] val$k;
      
      public void swap(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        Object localObject = this.val$v[paramAnonymousInt1];
        this.val$v[paramAnonymousInt1] = this.val$v[paramAnonymousInt2];
        this.val$v[paramAnonymousInt2] = localObject;
        long l = this.val$k[paramAnonymousInt1];
        this.val$k[paramAnonymousInt1] = this.val$k[paramAnonymousInt2];
        this.val$k[paramAnonymousInt2] = l;
      }
    };
    IntComparator local10 = new IntComparator()
    {
      private final Object[] val$v;
      private final long[] val$k;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        int i = ((Comparable)this.val$v[paramAnonymousInt1]).compareTo((Comparable)this.val$v[paramAnonymousInt2]);
        return this.val$k[paramAnonymousInt1] == this.val$k[paramAnonymousInt2] ? 0 : this.val$k[paramAnonymousInt1] < this.val$k[paramAnonymousInt2] ? -1 : i > 0 ? 1 : i < 0 ? -1 : 1;
      }
    };
    GenericSorting.quickSort(0, paramLongArrayList.size(), local10, local9);
  }
  
  public abstract boolean put(long paramLong, Object paramObject);
  
  public abstract boolean removeKey(long paramLong);
  
  public String toString()
  {
    LongArrayList localLongArrayList = keys();
    localLongArrayList.sort();
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[");
    int i = localLongArrayList.size() - 1;
    for (int j = 0; j <= i; j++)
    {
      long l = localLongArrayList.get(j);
      localStringBuffer.append(String.valueOf(l));
      localStringBuffer.append("->");
      localStringBuffer.append(String.valueOf(get(l)));
      if (j < i) {
        localStringBuffer.append(", ");
      }
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
  
  public String toStringByValue()
  {
    LongArrayList localLongArrayList = new LongArrayList();
    keysSortedByValue(localLongArrayList);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[");
    int i = localLongArrayList.size() - 1;
    for (int j = 0; j <= i; j++)
    {
      long l = localLongArrayList.get(j);
      localStringBuffer.append(String.valueOf(l));
      localStringBuffer.append("->");
      localStringBuffer.append(String.valueOf(get(l)));
      if (j < i) {
        localStringBuffer.append(", ");
      }
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
  
  public ObjectArrayList values()
  {
    ObjectArrayList localObjectArrayList = new ObjectArrayList(size());
    values(localObjectArrayList);
    return localObjectArrayList;
  }
  
  public void values(ObjectArrayList paramObjectArrayList)
  {
    paramObjectArrayList.clear();
    forEachKey(new LongProcedure()
    {
      private final ObjectArrayList val$list;
      
      public boolean apply(long paramAnonymousLong)
      {
        this.val$list.add(AbstractLongObjectMap.this.get(paramAnonymousLong));
        return true;
      }
    });
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.map.AbstractLongObjectMap
 * JD-Core Version:    0.7.0.1
 */
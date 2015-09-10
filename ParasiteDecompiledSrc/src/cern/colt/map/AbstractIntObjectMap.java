package cern.colt.map;

import cern.colt.GenericSorting;
import cern.colt.Swapper;
import cern.colt.function.IntComparator;
import cern.colt.function.IntObjectProcedure;
import cern.colt.function.IntProcedure;
import cern.colt.list.IntArrayList;
import cern.colt.list.ObjectArrayList;

public abstract class AbstractIntObjectMap
  extends AbstractMap
{
  public boolean containsKey(int paramInt)
  {
    !forEachKey(new IntProcedure()
    {
      private final int val$key;
      
      public boolean apply(int paramAnonymousInt)
      {
        return this.val$key != paramAnonymousInt;
      }
    });
  }
  
  public boolean containsValue(Object paramObject)
  {
    !forEachPair(new IntObjectProcedure()
    {
      private final Object val$value;
      
      public boolean apply(int paramAnonymousInt, Object paramAnonymousObject)
      {
        return this.val$value != paramAnonymousObject;
      }
    });
  }
  
  public AbstractIntObjectMap copy()
  {
    return (AbstractIntObjectMap)clone();
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof AbstractIntObjectMap)) {
      return false;
    }
    AbstractIntObjectMap localAbstractIntObjectMap = (AbstractIntObjectMap)paramObject;
    if (localAbstractIntObjectMap.size() != size()) {
      return false;
    }
    (forEachPair(new IntObjectProcedure()
    {
      private final AbstractIntObjectMap val$other;
      
      public boolean apply(int paramAnonymousInt, Object paramAnonymousObject)
      {
        return (this.val$other.containsKey(paramAnonymousInt)) && (this.val$other.get(paramAnonymousInt) == paramAnonymousObject);
      }
    })) && (localAbstractIntObjectMap.forEachPair(new IntObjectProcedure()
    {
      public boolean apply(int paramAnonymousInt, Object paramAnonymousObject)
      {
        return (AbstractIntObjectMap.this.containsKey(paramAnonymousInt)) && (AbstractIntObjectMap.this.get(paramAnonymousInt) == paramAnonymousObject);
      }
    }));
  }
  
  public abstract boolean forEachKey(IntProcedure paramIntProcedure);
  
  public boolean forEachPair(IntObjectProcedure paramIntObjectProcedure)
  {
    forEachKey(new IntProcedure()
    {
      private final IntObjectProcedure val$procedure;
      
      public boolean apply(int paramAnonymousInt)
      {
        return this.val$procedure.apply(paramAnonymousInt, AbstractIntObjectMap.this.get(paramAnonymousInt));
      }
    });
  }
  
  public abstract Object get(int paramInt);
  
  public int keyOf(Object paramObject)
  {
    int[] arrayOfInt = new int[1];
    boolean bool = forEachPair(new IntObjectProcedure()
    {
      private final Object val$value;
      private final int[] val$foundKey;
      
      public boolean apply(int paramAnonymousInt, Object paramAnonymousObject)
      {
        int i = this.val$value == paramAnonymousObject ? 1 : 0;
        if (i != 0) {
          this.val$foundKey[0] = paramAnonymousInt;
        }
        return i == 0;
      }
    });
    if (bool) {
      return -2147483648;
    }
    return arrayOfInt[0];
  }
  
  public IntArrayList keys()
  {
    IntArrayList localIntArrayList = new IntArrayList(size());
    keys(localIntArrayList);
    return localIntArrayList;
  }
  
  public void keys(IntArrayList paramIntArrayList)
  {
    paramIntArrayList.clear();
    forEachKey(new IntProcedure()
    {
      private final IntArrayList val$list;
      
      public boolean apply(int paramAnonymousInt)
      {
        this.val$list.add(paramAnonymousInt);
        return true;
      }
    });
  }
  
  public void keysSortedByValue(IntArrayList paramIntArrayList)
  {
    pairsSortedByValue(paramIntArrayList, new ObjectArrayList(size()));
  }
  
  public void pairsMatching(IntObjectProcedure paramIntObjectProcedure, IntArrayList paramIntArrayList, ObjectArrayList paramObjectArrayList)
  {
    paramIntArrayList.clear();
    paramObjectArrayList.clear();
    forEachPair(new IntObjectProcedure()
    {
      private final IntObjectProcedure val$condition;
      private final IntArrayList val$keyList;
      private final ObjectArrayList val$valueList;
      
      public boolean apply(int paramAnonymousInt, Object paramAnonymousObject)
      {
        if (this.val$condition.apply(paramAnonymousInt, paramAnonymousObject))
        {
          this.val$keyList.add(paramAnonymousInt);
          this.val$valueList.add(paramAnonymousObject);
        }
        return true;
      }
    });
  }
  
  public void pairsSortedByKey(IntArrayList paramIntArrayList, ObjectArrayList paramObjectArrayList)
  {
    keys(paramIntArrayList);
    paramIntArrayList.sort();
    paramObjectArrayList.setSize(paramIntArrayList.size());
    int i = paramIntArrayList.size();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      paramObjectArrayList.setQuick(i, get(paramIntArrayList.getQuick(i)));
    }
  }
  
  public void pairsSortedByValue(IntArrayList paramIntArrayList, ObjectArrayList paramObjectArrayList)
  {
    keys(paramIntArrayList);
    values(paramObjectArrayList);
    int[] arrayOfInt = paramIntArrayList.elements();
    Object[] arrayOfObject = paramObjectArrayList.elements();
    Swapper local9 = new Swapper()
    {
      private final Object[] val$v;
      private final int[] val$k;
      
      public void swap(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        Object localObject = this.val$v[paramAnonymousInt1];
        this.val$v[paramAnonymousInt1] = this.val$v[paramAnonymousInt2];
        this.val$v[paramAnonymousInt2] = localObject;
        int i = this.val$k[paramAnonymousInt1];
        this.val$k[paramAnonymousInt1] = this.val$k[paramAnonymousInt2];
        this.val$k[paramAnonymousInt2] = i;
      }
    };
    IntComparator local10 = new IntComparator()
    {
      private final Object[] val$v;
      private final int[] val$k;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        int i = ((Comparable)this.val$v[paramAnonymousInt1]).compareTo((Comparable)this.val$v[paramAnonymousInt2]);
        return this.val$k[paramAnonymousInt1] == this.val$k[paramAnonymousInt2] ? 0 : this.val$k[paramAnonymousInt1] < this.val$k[paramAnonymousInt2] ? -1 : i > 0 ? 1 : i < 0 ? -1 : 1;
      }
    };
    GenericSorting.quickSort(0, paramIntArrayList.size(), local10, local9);
  }
  
  public abstract boolean put(int paramInt, Object paramObject);
  
  public abstract boolean removeKey(int paramInt);
  
  public String toString()
  {
    IntArrayList localIntArrayList = keys();
    localIntArrayList.sort();
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[");
    int i = localIntArrayList.size() - 1;
    for (int j = 0; j <= i; j++)
    {
      int k = localIntArrayList.get(j);
      localStringBuffer.append(String.valueOf(k));
      localStringBuffer.append("->");
      localStringBuffer.append(String.valueOf(get(k)));
      if (j < i) {
        localStringBuffer.append(", ");
      }
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
  
  public String toStringByValue()
  {
    IntArrayList localIntArrayList = new IntArrayList();
    keysSortedByValue(localIntArrayList);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[");
    int i = localIntArrayList.size() - 1;
    for (int j = 0; j <= i; j++)
    {
      int k = localIntArrayList.get(j);
      localStringBuffer.append(String.valueOf(k));
      localStringBuffer.append("->");
      localStringBuffer.append(String.valueOf(get(k)));
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
    forEachKey(new IntProcedure()
    {
      private final ObjectArrayList val$list;
      
      public boolean apply(int paramAnonymousInt)
      {
        this.val$list.add(AbstractIntObjectMap.this.get(paramAnonymousInt));
        return true;
      }
    });
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.map.AbstractIntObjectMap
 * JD-Core Version:    0.7.0.1
 */
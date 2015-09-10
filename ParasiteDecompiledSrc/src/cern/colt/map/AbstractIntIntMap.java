package cern.colt.map;

import cern.colt.GenericSorting;
import cern.colt.Swapper;
import cern.colt.function.IntComparator;
import cern.colt.function.IntIntProcedure;
import cern.colt.function.IntProcedure;
import cern.colt.list.IntArrayList;

public abstract class AbstractIntIntMap
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
  
  public boolean containsValue(int paramInt)
  {
    !forEachPair(new IntIntProcedure()
    {
      private final int val$value;
      
      public boolean apply(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.val$value != paramAnonymousInt2;
      }
    });
  }
  
  public AbstractIntIntMap copy()
  {
    return (AbstractIntIntMap)clone();
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof AbstractIntIntMap)) {
      return false;
    }
    AbstractIntIntMap localAbstractIntIntMap = (AbstractIntIntMap)paramObject;
    if (localAbstractIntIntMap.size() != size()) {
      return false;
    }
    (forEachPair(new IntIntProcedure()
    {
      private final AbstractIntIntMap val$other;
      
      public boolean apply(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return (this.val$other.containsKey(paramAnonymousInt1)) && (this.val$other.get(paramAnonymousInt1) == paramAnonymousInt2);
      }
    })) && (localAbstractIntIntMap.forEachPair(new IntIntProcedure()
    {
      public boolean apply(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return (AbstractIntIntMap.this.containsKey(paramAnonymousInt1)) && (AbstractIntIntMap.this.get(paramAnonymousInt1) == paramAnonymousInt2);
      }
    }));
  }
  
  public abstract boolean forEachKey(IntProcedure paramIntProcedure);
  
  public boolean forEachPair(IntIntProcedure paramIntIntProcedure)
  {
    forEachKey(new IntProcedure()
    {
      private final IntIntProcedure val$procedure;
      
      public boolean apply(int paramAnonymousInt)
      {
        return this.val$procedure.apply(paramAnonymousInt, AbstractIntIntMap.this.get(paramAnonymousInt));
      }
    });
  }
  
  public abstract int get(int paramInt);
  
  public int keyOf(int paramInt)
  {
    int[] arrayOfInt = new int[1];
    boolean bool = forEachPair(new IntIntProcedure()
    {
      private final int val$value;
      private final int[] val$foundKey;
      
      public boolean apply(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        int i = this.val$value == paramAnonymousInt2 ? 1 : 0;
        if (i != 0) {
          this.val$foundKey[0] = paramAnonymousInt1;
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
    pairsSortedByValue(paramIntArrayList, new IntArrayList(size()));
  }
  
  public void pairsMatching(IntIntProcedure paramIntIntProcedure, IntArrayList paramIntArrayList1, IntArrayList paramIntArrayList2)
  {
    paramIntArrayList1.clear();
    paramIntArrayList2.clear();
    forEachPair(new IntIntProcedure()
    {
      private final IntIntProcedure val$condition;
      private final IntArrayList val$keyList;
      private final IntArrayList val$valueList;
      
      public boolean apply(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        if (this.val$condition.apply(paramAnonymousInt1, paramAnonymousInt2))
        {
          this.val$keyList.add(paramAnonymousInt1);
          this.val$valueList.add(paramAnonymousInt2);
        }
        return true;
      }
    });
  }
  
  public void pairsSortedByKey(IntArrayList paramIntArrayList1, IntArrayList paramIntArrayList2)
  {
    keys(paramIntArrayList1);
    paramIntArrayList1.sort();
    paramIntArrayList2.setSize(paramIntArrayList1.size());
    int i = paramIntArrayList1.size();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      paramIntArrayList2.setQuick(i, get(paramIntArrayList1.getQuick(i)));
    }
  }
  
  public void pairsSortedByValue(IntArrayList paramIntArrayList1, IntArrayList paramIntArrayList2)
  {
    keys(paramIntArrayList1);
    values(paramIntArrayList2);
    int[] arrayOfInt1 = paramIntArrayList1.elements();
    int[] arrayOfInt2 = paramIntArrayList2.elements();
    Swapper local9 = new Swapper()
    {
      private final int[] val$v;
      private final int[] val$k;
      
      public void swap(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        int j = this.val$v[paramAnonymousInt1];
        this.val$v[paramAnonymousInt1] = this.val$v[paramAnonymousInt2];
        this.val$v[paramAnonymousInt2] = j;
        int i = this.val$k[paramAnonymousInt1];
        this.val$k[paramAnonymousInt1] = this.val$k[paramAnonymousInt2];
        this.val$k[paramAnonymousInt2] = i;
      }
    };
    IntComparator local10 = new IntComparator()
    {
      private final int[] val$v;
      private final int[] val$k;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.val$k[paramAnonymousInt1] == this.val$k[paramAnonymousInt2] ? 0 : this.val$k[paramAnonymousInt1] < this.val$k[paramAnonymousInt2] ? -1 : this.val$v[paramAnonymousInt1] > this.val$v[paramAnonymousInt2] ? 1 : this.val$v[paramAnonymousInt1] < this.val$v[paramAnonymousInt2] ? -1 : 1;
      }
    };
    GenericSorting.quickSort(0, paramIntArrayList1.size(), local10, local9);
  }
  
  public abstract boolean put(int paramInt1, int paramInt2);
  
  public abstract boolean removeKey(int paramInt);
  
  public String toString()
  {
    IntArrayList localIntArrayList = keys();
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
  
  public IntArrayList values()
  {
    IntArrayList localIntArrayList = new IntArrayList(size());
    values(localIntArrayList);
    return localIntArrayList;
  }
  
  public void values(IntArrayList paramIntArrayList)
  {
    paramIntArrayList.clear();
    forEachKey(new IntProcedure()
    {
      private final IntArrayList val$list;
      
      public boolean apply(int paramAnonymousInt)
      {
        this.val$list.add(AbstractIntIntMap.this.get(paramAnonymousInt));
        return true;
      }
    });
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.map.AbstractIntIntMap
 * JD-Core Version:    0.7.0.1
 */
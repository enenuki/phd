package cern.colt.map;

import cern.colt.GenericSorting;
import cern.colt.Swapper;
import cern.colt.function.DoubleFunction;
import cern.colt.function.IntComparator;
import cern.colt.function.IntDoubleProcedure;
import cern.colt.function.IntProcedure;
import cern.colt.list.DoubleArrayList;
import cern.colt.list.IntArrayList;

public abstract class AbstractIntDoubleMap
  extends AbstractMap
{
  public void assign(DoubleFunction paramDoubleFunction)
  {
    copy().forEachPair(new IntDoubleProcedure()
    {
      private final DoubleFunction val$function;
      
      public boolean apply(int paramAnonymousInt, double paramAnonymousDouble)
      {
        AbstractIntDoubleMap.this.put(paramAnonymousInt, this.val$function.apply(paramAnonymousDouble));
        return true;
      }
    });
  }
  
  public void assign(AbstractIntDoubleMap paramAbstractIntDoubleMap)
  {
    clear();
    paramAbstractIntDoubleMap.forEachPair(new IntDoubleProcedure()
    {
      public boolean apply(int paramAnonymousInt, double paramAnonymousDouble)
      {
        AbstractIntDoubleMap.this.put(paramAnonymousInt, paramAnonymousDouble);
        return true;
      }
    });
  }
  
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
  
  public boolean containsValue(double paramDouble)
  {
    !forEachPair(new IntDoubleProcedure()
    {
      private final double val$value;
      
      public boolean apply(int paramAnonymousInt, double paramAnonymousDouble)
      {
        return this.val$value != paramAnonymousDouble;
      }
    });
  }
  
  public AbstractIntDoubleMap copy()
  {
    return (AbstractIntDoubleMap)clone();
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof AbstractIntDoubleMap)) {
      return false;
    }
    AbstractIntDoubleMap localAbstractIntDoubleMap = (AbstractIntDoubleMap)paramObject;
    if (localAbstractIntDoubleMap.size() != size()) {
      return false;
    }
    (forEachPair(new IntDoubleProcedure()
    {
      private final AbstractIntDoubleMap val$other;
      
      public boolean apply(int paramAnonymousInt, double paramAnonymousDouble)
      {
        return (this.val$other.containsKey(paramAnonymousInt)) && (this.val$other.get(paramAnonymousInt) == paramAnonymousDouble);
      }
    })) && (localAbstractIntDoubleMap.forEachPair(new IntDoubleProcedure()
    {
      public boolean apply(int paramAnonymousInt, double paramAnonymousDouble)
      {
        return (AbstractIntDoubleMap.this.containsKey(paramAnonymousInt)) && (AbstractIntDoubleMap.this.get(paramAnonymousInt) == paramAnonymousDouble);
      }
    }));
  }
  
  public abstract boolean forEachKey(IntProcedure paramIntProcedure);
  
  public boolean forEachPair(IntDoubleProcedure paramIntDoubleProcedure)
  {
    forEachKey(new IntProcedure()
    {
      private final IntDoubleProcedure val$procedure;
      
      public boolean apply(int paramAnonymousInt)
      {
        return this.val$procedure.apply(paramAnonymousInt, AbstractIntDoubleMap.this.get(paramAnonymousInt));
      }
    });
  }
  
  public abstract double get(int paramInt);
  
  public int keyOf(double paramDouble)
  {
    int[] arrayOfInt = new int[1];
    boolean bool = forEachPair(new IntDoubleProcedure()
    {
      private final double val$value;
      private final int[] val$foundKey;
      
      public boolean apply(int paramAnonymousInt, double paramAnonymousDouble)
      {
        int i = this.val$value == paramAnonymousDouble ? 1 : 0;
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
    pairsSortedByValue(paramIntArrayList, new DoubleArrayList(size()));
  }
  
  public void pairsMatching(IntDoubleProcedure paramIntDoubleProcedure, IntArrayList paramIntArrayList, DoubleArrayList paramDoubleArrayList)
  {
    paramIntArrayList.clear();
    paramDoubleArrayList.clear();
    forEachPair(new IntDoubleProcedure()
    {
      private final IntDoubleProcedure val$condition;
      private final IntArrayList val$keyList;
      private final DoubleArrayList val$valueList;
      
      public boolean apply(int paramAnonymousInt, double paramAnonymousDouble)
      {
        if (this.val$condition.apply(paramAnonymousInt, paramAnonymousDouble))
        {
          this.val$keyList.add(paramAnonymousInt);
          this.val$valueList.add(paramAnonymousDouble);
        }
        return true;
      }
    });
  }
  
  public void pairsSortedByKey(IntArrayList paramIntArrayList, DoubleArrayList paramDoubleArrayList)
  {
    keys(paramIntArrayList);
    paramIntArrayList.sort();
    paramDoubleArrayList.setSize(paramIntArrayList.size());
    int i = paramIntArrayList.size();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      paramDoubleArrayList.setQuick(i, get(paramIntArrayList.getQuick(i)));
    }
  }
  
  public void pairsSortedByValue(IntArrayList paramIntArrayList, DoubleArrayList paramDoubleArrayList)
  {
    keys(paramIntArrayList);
    values(paramDoubleArrayList);
    int[] arrayOfInt = paramIntArrayList.elements();
    double[] arrayOfDouble = paramDoubleArrayList.elements();
    Swapper local11 = new Swapper()
    {
      private final double[] val$v;
      private final int[] val$k;
      
      public void swap(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        double d = this.val$v[paramAnonymousInt1];
        this.val$v[paramAnonymousInt1] = this.val$v[paramAnonymousInt2];
        this.val$v[paramAnonymousInt2] = d;
        int i = this.val$k[paramAnonymousInt1];
        this.val$k[paramAnonymousInt1] = this.val$k[paramAnonymousInt2];
        this.val$k[paramAnonymousInt2] = i;
      }
    };
    IntComparator local12 = new IntComparator()
    {
      private final double[] val$v;
      private final int[] val$k;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.val$k[paramAnonymousInt1] == this.val$k[paramAnonymousInt2] ? 0 : this.val$k[paramAnonymousInt1] < this.val$k[paramAnonymousInt2] ? -1 : this.val$v[paramAnonymousInt1] > this.val$v[paramAnonymousInt2] ? 1 : this.val$v[paramAnonymousInt1] < this.val$v[paramAnonymousInt2] ? -1 : 1;
      }
    };
    GenericSorting.quickSort(0, paramIntArrayList.size(), local12, local11);
  }
  
  public abstract boolean put(int paramInt, double paramDouble);
  
  public abstract boolean removeKey(int paramInt);
  
  public String toString()
  {
    IntArrayList localIntArrayList = keys();
    String str = localIntArrayList.toString() + "\n";
    localIntArrayList.sort();
    StringBuffer localStringBuffer = new StringBuffer(str);
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
  
  public DoubleArrayList values()
  {
    DoubleArrayList localDoubleArrayList = new DoubleArrayList(size());
    values(localDoubleArrayList);
    return localDoubleArrayList;
  }
  
  public void values(DoubleArrayList paramDoubleArrayList)
  {
    paramDoubleArrayList.clear();
    forEachKey(new IntProcedure()
    {
      private final DoubleArrayList val$list;
      
      public boolean apply(int paramAnonymousInt)
      {
        this.val$list.add(AbstractIntDoubleMap.this.get(paramAnonymousInt));
        return true;
      }
    });
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.map.AbstractIntDoubleMap
 * JD-Core Version:    0.7.0.1
 */
package cern.colt.map;

import cern.colt.GenericSorting;
import cern.colt.Swapper;
import cern.colt.function.DoubleIntProcedure;
import cern.colt.function.DoubleProcedure;
import cern.colt.function.IntComparator;
import cern.colt.list.DoubleArrayList;
import cern.colt.list.IntArrayList;

public abstract class AbstractDoubleIntMap
  extends AbstractMap
{
  public boolean containsKey(double paramDouble)
  {
    !forEachKey(new DoubleProcedure()
    {
      private final double val$key;
      
      public boolean apply(double paramAnonymousDouble)
      {
        return this.val$key != paramAnonymousDouble;
      }
    });
  }
  
  public boolean containsValue(int paramInt)
  {
    !forEachPair(new DoubleIntProcedure()
    {
      private final int val$value;
      
      public boolean apply(double paramAnonymousDouble, int paramAnonymousInt)
      {
        return this.val$value != paramAnonymousInt;
      }
    });
  }
  
  public AbstractDoubleIntMap copy()
  {
    return (AbstractDoubleIntMap)clone();
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof AbstractDoubleIntMap)) {
      return false;
    }
    AbstractDoubleIntMap localAbstractDoubleIntMap = (AbstractDoubleIntMap)paramObject;
    if (localAbstractDoubleIntMap.size() != size()) {
      return false;
    }
    (forEachPair(new DoubleIntProcedure()
    {
      private final AbstractDoubleIntMap val$other;
      
      public boolean apply(double paramAnonymousDouble, int paramAnonymousInt)
      {
        return (this.val$other.containsKey(paramAnonymousDouble)) && (this.val$other.get(paramAnonymousDouble) == paramAnonymousInt);
      }
    })) && (localAbstractDoubleIntMap.forEachPair(new DoubleIntProcedure()
    {
      public boolean apply(double paramAnonymousDouble, int paramAnonymousInt)
      {
        return (AbstractDoubleIntMap.this.containsKey(paramAnonymousDouble)) && (AbstractDoubleIntMap.this.get(paramAnonymousDouble) == paramAnonymousInt);
      }
    }));
  }
  
  public abstract boolean forEachKey(DoubleProcedure paramDoubleProcedure);
  
  public boolean forEachPair(DoubleIntProcedure paramDoubleIntProcedure)
  {
    forEachKey(new DoubleProcedure()
    {
      private final DoubleIntProcedure val$procedure;
      
      public boolean apply(double paramAnonymousDouble)
      {
        return this.val$procedure.apply(paramAnonymousDouble, AbstractDoubleIntMap.this.get(paramAnonymousDouble));
      }
    });
  }
  
  public abstract int get(double paramDouble);
  
  public double keyOf(int paramInt)
  {
    double[] arrayOfDouble = new double[1];
    boolean bool = forEachPair(new DoubleIntProcedure()
    {
      private final int val$value;
      private final double[] val$foundKey;
      
      public boolean apply(double paramAnonymousDouble, int paramAnonymousInt)
      {
        int i = this.val$value == paramAnonymousInt ? 1 : 0;
        if (i != 0) {
          this.val$foundKey[0] = paramAnonymousDouble;
        }
        return i == 0;
      }
    });
    if (bool) {
      return (0.0D / 0.0D);
    }
    return arrayOfDouble[0];
  }
  
  public DoubleArrayList keys()
  {
    DoubleArrayList localDoubleArrayList = new DoubleArrayList(size());
    keys(localDoubleArrayList);
    return localDoubleArrayList;
  }
  
  public void keys(DoubleArrayList paramDoubleArrayList)
  {
    paramDoubleArrayList.clear();
    forEachKey(new DoubleProcedure()
    {
      private final DoubleArrayList val$list;
      
      public boolean apply(double paramAnonymousDouble)
      {
        this.val$list.add(paramAnonymousDouble);
        return true;
      }
    });
  }
  
  public void keysSortedByValue(DoubleArrayList paramDoubleArrayList)
  {
    pairsSortedByValue(paramDoubleArrayList, new IntArrayList(size()));
  }
  
  public void pairsMatching(DoubleIntProcedure paramDoubleIntProcedure, DoubleArrayList paramDoubleArrayList, IntArrayList paramIntArrayList)
  {
    paramDoubleArrayList.clear();
    paramIntArrayList.clear();
    forEachPair(new DoubleIntProcedure()
    {
      private final DoubleIntProcedure val$condition;
      private final DoubleArrayList val$keyList;
      private final IntArrayList val$valueList;
      
      public boolean apply(double paramAnonymousDouble, int paramAnonymousInt)
      {
        if (this.val$condition.apply(paramAnonymousDouble, paramAnonymousInt))
        {
          this.val$keyList.add(paramAnonymousDouble);
          this.val$valueList.add(paramAnonymousInt);
        }
        return true;
      }
    });
  }
  
  public void pairsSortedByKey(DoubleArrayList paramDoubleArrayList, IntArrayList paramIntArrayList)
  {
    keys(paramDoubleArrayList);
    paramDoubleArrayList.sort();
    paramIntArrayList.setSize(paramDoubleArrayList.size());
    int i = paramDoubleArrayList.size();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      paramIntArrayList.setQuick(i, get(paramDoubleArrayList.getQuick(i)));
    }
  }
  
  public void pairsSortedByValue(DoubleArrayList paramDoubleArrayList, IntArrayList paramIntArrayList)
  {
    keys(paramDoubleArrayList);
    values(paramIntArrayList);
    double[] arrayOfDouble = paramDoubleArrayList.elements();
    int[] arrayOfInt = paramIntArrayList.elements();
    Swapper local9 = new Swapper()
    {
      private final int[] val$v;
      private final double[] val$k;
      
      public void swap(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        int i = this.val$v[paramAnonymousInt1];
        this.val$v[paramAnonymousInt1] = this.val$v[paramAnonymousInt2];
        this.val$v[paramAnonymousInt2] = i;
        double d = this.val$k[paramAnonymousInt1];
        this.val$k[paramAnonymousInt1] = this.val$k[paramAnonymousInt2];
        this.val$k[paramAnonymousInt2] = d;
      }
    };
    IntComparator local10 = new IntComparator()
    {
      private final int[] val$v;
      private final double[] val$k;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.val$k[paramAnonymousInt1] == this.val$k[paramAnonymousInt2] ? 0 : this.val$k[paramAnonymousInt1] < this.val$k[paramAnonymousInt2] ? -1 : this.val$v[paramAnonymousInt1] > this.val$v[paramAnonymousInt2] ? 1 : this.val$v[paramAnonymousInt1] < this.val$v[paramAnonymousInt2] ? -1 : 1;
      }
    };
    GenericSorting.quickSort(0, paramDoubleArrayList.size(), local10, local9);
  }
  
  public abstract boolean put(double paramDouble, int paramInt);
  
  public abstract boolean removeKey(double paramDouble);
  
  public String toString()
  {
    DoubleArrayList localDoubleArrayList = keys();
    localDoubleArrayList.sort();
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[");
    int i = localDoubleArrayList.size() - 1;
    for (int j = 0; j <= i; j++)
    {
      double d = localDoubleArrayList.get(j);
      localStringBuffer.append(String.valueOf(d));
      localStringBuffer.append("->");
      localStringBuffer.append(String.valueOf(get(d)));
      if (j < i) {
        localStringBuffer.append(", ");
      }
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
  
  public String toStringByValue()
  {
    DoubleArrayList localDoubleArrayList = new DoubleArrayList();
    keysSortedByValue(localDoubleArrayList);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[");
    int i = localDoubleArrayList.size() - 1;
    for (int j = 0; j <= i; j++)
    {
      double d = localDoubleArrayList.get(j);
      localStringBuffer.append(String.valueOf(d));
      localStringBuffer.append("->");
      localStringBuffer.append(String.valueOf(get(d)));
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
    forEachKey(new DoubleProcedure()
    {
      private final IntArrayList val$list;
      
      public boolean apply(double paramAnonymousDouble)
      {
        this.val$list.add(AbstractDoubleIntMap.this.get(paramAnonymousDouble));
        return true;
      }
    });
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.map.AbstractDoubleIntMap
 * JD-Core Version:    0.7.0.1
 */
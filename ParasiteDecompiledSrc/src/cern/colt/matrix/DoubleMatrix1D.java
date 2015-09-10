package cern.colt.matrix;

import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;
import cern.colt.function.DoubleProcedure;
import cern.colt.list.DoubleArrayList;
import cern.colt.list.IntArrayList;
import cern.colt.matrix.doublealgo.Formatter;
import cern.colt.matrix.doublealgo.Sorting;
import cern.colt.matrix.impl.AbstractMatrix1D;
import cern.colt.matrix.linalg.Property;
import cern.jet.math.Functions;
import cern.jet.math.PlusMult;

public abstract class DoubleMatrix1D
  extends AbstractMatrix1D
{
  public double aggregate(DoubleDoubleFunction paramDoubleDoubleFunction, DoubleFunction paramDoubleFunction)
  {
    if (this.size == 0) {
      return (0.0D / 0.0D);
    }
    double d = paramDoubleFunction.apply(getQuick(this.size - 1));
    int i = this.size - 1;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      d = paramDoubleDoubleFunction.apply(d, paramDoubleFunction.apply(getQuick(i)));
    }
    return d;
  }
  
  public double aggregate(DoubleMatrix1D paramDoubleMatrix1D, DoubleDoubleFunction paramDoubleDoubleFunction1, DoubleDoubleFunction paramDoubleDoubleFunction2)
  {
    checkSize(paramDoubleMatrix1D);
    if (this.size == 0) {
      return (0.0D / 0.0D);
    }
    double d = paramDoubleDoubleFunction2.apply(getQuick(this.size - 1), paramDoubleMatrix1D.getQuick(this.size - 1));
    int i = this.size - 1;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      d = paramDoubleDoubleFunction1.apply(d, paramDoubleDoubleFunction2.apply(getQuick(i), paramDoubleMatrix1D.getQuick(i)));
    }
    return d;
  }
  
  public DoubleMatrix1D assign(double[] paramArrayOfDouble)
  {
    if (paramArrayOfDouble.length != this.size) {
      throw new IllegalArgumentException("Must have same number of cells: length=" + paramArrayOfDouble.length + "size()=" + size());
    }
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      setQuick(i, paramArrayOfDouble[i]);
    }
    return this;
  }
  
  public DoubleMatrix1D assign(double paramDouble)
  {
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      setQuick(i, paramDouble);
    }
    return this;
  }
  
  public DoubleMatrix1D assign(DoubleFunction paramDoubleFunction)
  {
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      setQuick(i, paramDoubleFunction.apply(getQuick(i)));
    }
    return this;
  }
  
  public DoubleMatrix1D assign(DoubleMatrix1D paramDoubleMatrix1D)
  {
    if (paramDoubleMatrix1D == this) {
      return this;
    }
    checkSize(paramDoubleMatrix1D);
    if (haveSharedCells(paramDoubleMatrix1D)) {
      paramDoubleMatrix1D = paramDoubleMatrix1D.copy();
    }
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      setQuick(i, paramDoubleMatrix1D.getQuick(i));
    }
    return this;
  }
  
  public DoubleMatrix1D assign(DoubleMatrix1D paramDoubleMatrix1D, DoubleDoubleFunction paramDoubleDoubleFunction)
  {
    checkSize(paramDoubleMatrix1D);
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      setQuick(i, paramDoubleDoubleFunction.apply(getQuick(i), paramDoubleMatrix1D.getQuick(i)));
    }
    return this;
  }
  
  public DoubleMatrix1D assign(DoubleMatrix1D paramDoubleMatrix1D, DoubleDoubleFunction paramDoubleDoubleFunction, IntArrayList paramIntArrayList)
  {
    checkSize(paramDoubleMatrix1D);
    int[] arrayOfInt = paramIntArrayList.elements();
    int k;
    if (paramDoubleDoubleFunction == Functions.mult)
    {
      int i = 0;
      int j = paramIntArrayList.size();
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        k = arrayOfInt[j];
        while (i < k)
        {
          setQuick(i, 0.0D);
          i++;
        }
        setQuick(k, getQuick(k) * paramDoubleMatrix1D.getQuick(k));
        i++;
      }
    }
    if ((paramDoubleDoubleFunction instanceof PlusMult))
    {
      double d = ((PlusMult)paramDoubleDoubleFunction).multiplicator;
      if (d == 0.0D) {
        return this;
      }
      int m;
      if (d == 1.0D)
      {
        k = paramIntArrayList.size();
        for (;;)
        {
          k--;
          if (k < 0) {
            break;
          }
          m = arrayOfInt[k];
          setQuick(m, getQuick(m) + paramDoubleMatrix1D.getQuick(m));
        }
      }
      if (d == -1.0D)
      {
        k = paramIntArrayList.size();
        for (;;)
        {
          k--;
          if (k < 0) {
            break;
          }
          m = arrayOfInt[k];
          setQuick(m, getQuick(m) - paramDoubleMatrix1D.getQuick(m));
        }
      }
      k = paramIntArrayList.size();
      for (;;)
      {
        k--;
        if (k < 0) {
          break;
        }
        m = arrayOfInt[k];
        setQuick(m, getQuick(m) + d * paramDoubleMatrix1D.getQuick(m));
      }
    }
    return assign(paramDoubleMatrix1D, paramDoubleDoubleFunction);
    return this;
  }
  
  public int cardinality()
  {
    int i = 0;
    int j = this.size;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      if (getQuick(j) != 0.0D) {
        i++;
      }
    }
    return i;
  }
  
  protected int cardinality(int paramInt)
  {
    int i = 0;
    int j = this.size;
    for (;;)
    {
      j--;
      if ((j < 0) || (i >= paramInt)) {
        break;
      }
      if (getQuick(j) != 0.0D) {
        i++;
      }
    }
    return i;
  }
  
  public DoubleMatrix1D copy()
  {
    DoubleMatrix1D localDoubleMatrix1D = like();
    localDoubleMatrix1D.assign(this);
    return localDoubleMatrix1D;
  }
  
  public boolean equals(double paramDouble)
  {
    return Property.DEFAULT.equals(this, paramDouble);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    if (!(paramObject instanceof DoubleMatrix1D)) {
      return false;
    }
    return Property.DEFAULT.equals(this, (DoubleMatrix1D)paramObject);
  }
  
  public double get(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.size)) {
      checkIndex(paramInt);
    }
    return getQuick(paramInt);
  }
  
  protected DoubleMatrix1D getContent()
  {
    return this;
  }
  
  public void getNonZeros(IntArrayList paramIntArrayList, DoubleArrayList paramDoubleArrayList)
  {
    int i = paramIntArrayList != null ? 1 : 0;
    int j = paramDoubleArrayList != null ? 1 : 0;
    if (i != 0) {
      paramIntArrayList.clear();
    }
    if (j != 0) {
      paramDoubleArrayList.clear();
    }
    int k = this.size;
    for (int m = 0; m < k; m++)
    {
      double d = getQuick(m);
      if (d != 0.0D)
      {
        if (i != 0) {
          paramIntArrayList.add(m);
        }
        if (j != 0) {
          paramDoubleArrayList.add(d);
        }
      }
    }
  }
  
  public void getNonZeros(IntArrayList paramIntArrayList, DoubleArrayList paramDoubleArrayList, int paramInt)
  {
    int i = paramIntArrayList != null ? 1 : 0;
    int j = paramDoubleArrayList != null ? 1 : 0;
    int k = cardinality(paramInt);
    if (i != 0) {
      paramIntArrayList.setSize(k);
    }
    if (j != 0) {
      paramDoubleArrayList.setSize(k);
    }
    if (k >= paramInt) {
      return;
    }
    if (i != 0) {
      paramIntArrayList.setSize(0);
    }
    if (j != 0) {
      paramDoubleArrayList.setSize(0);
    }
    int m = this.size;
    for (int n = 0; n < m; n++)
    {
      double d = getQuick(n);
      if (d != 0.0D)
      {
        if (i != 0) {
          paramIntArrayList.add(n);
        }
        if (j != 0) {
          paramDoubleArrayList.add(d);
        }
      }
    }
  }
  
  public abstract double getQuick(int paramInt);
  
  protected boolean haveSharedCells(DoubleMatrix1D paramDoubleMatrix1D)
  {
    if (paramDoubleMatrix1D == null) {
      return false;
    }
    if (this == paramDoubleMatrix1D) {
      return true;
    }
    return getContent().haveSharedCellsRaw(paramDoubleMatrix1D.getContent());
  }
  
  protected boolean haveSharedCellsRaw(DoubleMatrix1D paramDoubleMatrix1D)
  {
    return false;
  }
  
  public DoubleMatrix1D like()
  {
    return like(this.size);
  }
  
  public abstract DoubleMatrix1D like(int paramInt);
  
  public abstract DoubleMatrix2D like2D(int paramInt1, int paramInt2);
  
  public void set(int paramInt, double paramDouble)
  {
    if ((paramInt < 0) || (paramInt >= this.size)) {
      checkIndex(paramInt);
    }
    setQuick(paramInt, paramDouble);
  }
  
  public abstract void setQuick(int paramInt, double paramDouble);
  
  public void swap(DoubleMatrix1D paramDoubleMatrix1D)
  {
    checkSize(paramDoubleMatrix1D);
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      double d = getQuick(i);
      setQuick(i, paramDoubleMatrix1D.getQuick(i));
      paramDoubleMatrix1D.setQuick(i, d);
    }
  }
  
  public double[] toArray()
  {
    double[] arrayOfDouble = new double[this.size];
    toArray(arrayOfDouble);
    return arrayOfDouble;
  }
  
  public void toArray(double[] paramArrayOfDouble)
  {
    if (paramArrayOfDouble.length < this.size) {
      throw new IllegalArgumentException("values too small");
    }
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      paramArrayOfDouble[i] = getQuick(i);
    }
  }
  
  public String toString()
  {
    return new Formatter().toString(this);
  }
  
  protected DoubleMatrix1D view()
  {
    return (DoubleMatrix1D)clone();
  }
  
  public DoubleMatrix1D viewFlip()
  {
    return (DoubleMatrix1D)view().vFlip();
  }
  
  public DoubleMatrix1D viewPart(int paramInt1, int paramInt2)
  {
    return (DoubleMatrix1D)view().vPart(paramInt1, paramInt2);
  }
  
  public DoubleMatrix1D viewSelection(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null)
    {
      paramArrayOfInt = new int[this.size];
      int i = this.size;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        paramArrayOfInt[i] = i;
      }
    }
    checkIndexes(paramArrayOfInt);
    int[] arrayOfInt = new int[paramArrayOfInt.length];
    int j = paramArrayOfInt.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfInt[j] = index(paramArrayOfInt[j]);
    }
    return viewSelectionLike(arrayOfInt);
  }
  
  public DoubleMatrix1D viewSelection(DoubleProcedure paramDoubleProcedure)
  {
    IntArrayList localIntArrayList = new IntArrayList();
    for (int i = 0; i < this.size; i++) {
      if (paramDoubleProcedure.apply(getQuick(i))) {
        localIntArrayList.add(i);
      }
    }
    localIntArrayList.trimToSize();
    return viewSelection(localIntArrayList.elements());
  }
  
  protected abstract DoubleMatrix1D viewSelectionLike(int[] paramArrayOfInt);
  
  public DoubleMatrix1D viewSorted()
  {
    return Sorting.mergeSort.sort(this);
  }
  
  public DoubleMatrix1D viewStrides(int paramInt)
  {
    return (DoubleMatrix1D)view().vStrides(paramInt);
  }
  
  private boolean xforEach(DoubleProcedure paramDoubleProcedure)
  {
    int i = this.size;
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (paramDoubleProcedure.apply(getQuick(i)));
    return false;
    return true;
  }
  
  public double zDotProduct(DoubleMatrix1D paramDoubleMatrix1D)
  {
    return zDotProduct(paramDoubleMatrix1D, 0, this.size);
  }
  
  public double zDotProduct(DoubleMatrix1D paramDoubleMatrix1D, int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt2 <= 0)) {
      return 0.0D;
    }
    int i = paramInt1 + paramInt2;
    if (this.size < i) {
      i = this.size;
    }
    if (paramDoubleMatrix1D.size < i) {
      i = paramDoubleMatrix1D.size;
    }
    paramInt2 = i - paramInt1;
    double d = 0.0D;
    int j = i - 1;
    int k = paramInt2;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      d += getQuick(j) * paramDoubleMatrix1D.getQuick(j);
      j--;
    }
    return d;
  }
  
  public double zDotProduct(DoubleMatrix1D paramDoubleMatrix1D, int paramInt1, int paramInt2, IntArrayList paramIntArrayList)
  {
    if ((paramInt1 < 0) || (paramInt2 <= 0)) {
      return 0.0D;
    }
    int i = paramInt1 + paramInt2;
    if (this.size < i) {
      i = this.size;
    }
    if (paramDoubleMatrix1D.size < i) {
      i = paramDoubleMatrix1D.size;
    }
    paramInt2 = i - paramInt1;
    if (paramInt2 <= 0) {
      return 0.0D;
    }
    int[] arrayOfInt = paramIntArrayList.elements();
    int j = 0;
    int k = paramIntArrayList.size();
    while ((j < k) && (arrayOfInt[j] < paramInt1)) {
      j++;
    }
    double d = 0.0D;
    for (;;)
    {
      paramInt2--;
      int m;
      if ((paramInt2 < 0) || (j >= k) || ((m = arrayOfInt[j]) >= i)) {
        break;
      }
      d += getQuick(m) * paramDoubleMatrix1D.getQuick(m);
      j++;
    }
    return d;
  }
  
  protected double zDotProduct(DoubleMatrix1D paramDoubleMatrix1D, IntArrayList paramIntArrayList)
  {
    return zDotProduct(paramDoubleMatrix1D, 0, this.size, paramIntArrayList);
  }
  
  public double zSum()
  {
    if (size() == 0) {
      return 0.0D;
    }
    return aggregate(Functions.plus, Functions.identity);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.DoubleMatrix1D
 * JD-Core Version:    0.7.0.1
 */
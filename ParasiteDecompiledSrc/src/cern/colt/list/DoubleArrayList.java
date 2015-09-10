package cern.colt.list;

import cern.colt.Arrays;
import cern.colt.Sorting;
import cern.colt.function.DoubleProcedure;
import cern.jet.math.Arithmetic;
import cern.jet.random.Uniform;
import cern.jet.random.engine.DRand;
import java.util.Date;

public class DoubleArrayList
  extends AbstractDoubleList
{
  protected double[] elements;
  
  public DoubleArrayList()
  {
    this(10);
  }
  
  public DoubleArrayList(double[] paramArrayOfDouble)
  {
    elements(paramArrayOfDouble);
  }
  
  public DoubleArrayList(int paramInt)
  {
    this(new double[paramInt]);
    setSizeRaw(0);
  }
  
  public void add(double paramDouble)
  {
    if (this.size == this.elements.length) {
      ensureCapacity(this.size + 1);
    }
    this.elements[(this.size++)] = paramDouble;
  }
  
  public void beforeInsert(int paramInt, double paramDouble)
  {
    if (this.size == paramInt)
    {
      add(paramDouble);
      return;
    }
    if ((paramInt > this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    ensureCapacity(this.size + 1);
    System.arraycopy(this.elements, paramInt, this.elements, paramInt + 1, this.size - paramInt);
    this.elements[paramInt] = paramDouble;
    this.size += 1;
  }
  
  public int binarySearchFromTo(double paramDouble, int paramInt1, int paramInt2)
  {
    return Sorting.binarySearchFromTo(this.elements, paramDouble, paramInt1, paramInt2);
  }
  
  public Object clone()
  {
    DoubleArrayList localDoubleArrayList = new DoubleArrayList((double[])this.elements.clone());
    localDoubleArrayList.setSizeRaw(this.size);
    return localDoubleArrayList;
  }
  
  public DoubleArrayList copy()
  {
    return (DoubleArrayList)clone();
  }
  
  public double[] elements()
  {
    return this.elements;
  }
  
  public AbstractDoubleList elements(double[] paramArrayOfDouble)
  {
    this.elements = paramArrayOfDouble;
    this.size = paramArrayOfDouble.length;
    return this;
  }
  
  public void ensureCapacity(int paramInt)
  {
    this.elements = Arrays.ensureCapacity(this.elements, paramInt);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof DoubleArrayList)) {
      return super.equals(paramObject);
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    DoubleArrayList localDoubleArrayList = (DoubleArrayList)paramObject;
    if (size() != localDoubleArrayList.size()) {
      return false;
    }
    double[] arrayOfDouble1 = elements();
    double[] arrayOfDouble2 = localDoubleArrayList.elements();
    int i = size();
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (arrayOfDouble1[i] == arrayOfDouble2[i]);
    return false;
    return true;
  }
  
  public boolean forEach(DoubleProcedure paramDoubleProcedure)
  {
    double[] arrayOfDouble = this.elements;
    int i = this.size;
    int j = 0;
    while (j < i) {
      if (!paramDoubleProcedure.apply(arrayOfDouble[(j++)])) {
        return false;
      }
    }
    return true;
  }
  
  public double get(int paramInt)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    return this.elements[paramInt];
  }
  
  public double getQuick(int paramInt)
  {
    return this.elements[paramInt];
  }
  
  public int indexOfFromTo(double paramDouble, int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return -1;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    double[] arrayOfDouble = this.elements;
    for (int i = paramInt1; i <= paramInt2; i++) {
      if (paramDouble == arrayOfDouble[i]) {
        return i;
      }
    }
    return -1;
  }
  
  public int lastIndexOfFromTo(double paramDouble, int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return -1;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    double[] arrayOfDouble = this.elements;
    for (int i = paramInt2; i >= paramInt1; i--) {
      if (paramDouble == arrayOfDouble[i]) {
        return i;
      }
    }
    return -1;
  }
  
  public AbstractDoubleList partFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return new DoubleArrayList(0);
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    double[] arrayOfDouble = new double[paramInt2 - paramInt1 + 1];
    System.arraycopy(this.elements, paramInt1, arrayOfDouble, 0, paramInt2 - paramInt1 + 1);
    return new DoubleArrayList(arrayOfDouble);
  }
  
  public boolean removeAll(AbstractDoubleList paramAbstractDoubleList)
  {
    if (!(paramAbstractDoubleList instanceof DoubleArrayList)) {
      return super.removeAll(paramAbstractDoubleList);
    }
    if (paramAbstractDoubleList.size() == 0) {
      return false;
    }
    int i = paramAbstractDoubleList.size() - 1;
    int j = 0;
    double[] arrayOfDouble = this.elements;
    int k = size();
    double d1 = paramAbstractDoubleList.size();
    double d2 = k;
    if ((d1 + d2) * Arithmetic.log2(d1) < d2 * d1)
    {
      DoubleArrayList localDoubleArrayList = (DoubleArrayList)paramAbstractDoubleList.clone();
      localDoubleArrayList.quickSort();
      for (int n = 0; n < k; n++) {
        if (localDoubleArrayList.binarySearchFromTo(arrayOfDouble[n], 0, i) < 0) {
          arrayOfDouble[(j++)] = arrayOfDouble[n];
        }
      }
    }
    for (int m = 0; m < k; m++) {
      if (paramAbstractDoubleList.indexOfFromTo(arrayOfDouble[m], 0, i) < 0) {
        arrayOfDouble[(j++)] = arrayOfDouble[m];
      }
    }
    m = j != k ? 1 : 0;
    setSize(j);
    return m;
  }
  
  public void replaceFromToWithFrom(int paramInt1, int paramInt2, AbstractDoubleList paramAbstractDoubleList, int paramInt3)
  {
    if (!(paramAbstractDoubleList instanceof DoubleArrayList))
    {
      super.replaceFromToWithFrom(paramInt1, paramInt2, paramAbstractDoubleList, paramInt3);
      return;
    }
    int i = paramInt2 - paramInt1 + 1;
    if (i > 0)
    {
      checkRangeFromTo(paramInt1, paramInt2, size());
      checkRangeFromTo(paramInt3, paramInt3 + i - 1, paramAbstractDoubleList.size());
      System.arraycopy(((DoubleArrayList)paramAbstractDoubleList).elements, paramInt3, this.elements, paramInt1, i);
    }
  }
  
  public boolean retainAll(AbstractDoubleList paramAbstractDoubleList)
  {
    if (!(paramAbstractDoubleList instanceof DoubleArrayList)) {
      return super.retainAll(paramAbstractDoubleList);
    }
    int i = paramAbstractDoubleList.size() - 1;
    int j = 0;
    double[] arrayOfDouble = this.elements;
    int k = size();
    double d1 = paramAbstractDoubleList.size();
    double d2 = k;
    if ((d1 + d2) * Arithmetic.log2(d1) < d2 * d1)
    {
      DoubleArrayList localDoubleArrayList = (DoubleArrayList)paramAbstractDoubleList.clone();
      localDoubleArrayList.quickSort();
      for (int n = 0; n < k; n++) {
        if (localDoubleArrayList.binarySearchFromTo(arrayOfDouble[n], 0, i) >= 0) {
          arrayOfDouble[(j++)] = arrayOfDouble[n];
        }
      }
    }
    for (int m = 0; m < k; m++) {
      if (paramAbstractDoubleList.indexOfFromTo(arrayOfDouble[m], 0, i) >= 0) {
        arrayOfDouble[(j++)] = arrayOfDouble[m];
      }
    }
    m = j != k ? 1 : 0;
    setSize(j);
    return m;
  }
  
  public void reverse()
  {
    int i = this.size / 2;
    int j = this.size - 1;
    double[] arrayOfDouble = this.elements;
    int k = 0;
    while (k < i)
    {
      double d = arrayOfDouble[k];
      arrayOfDouble[(k++)] = arrayOfDouble[j];
      arrayOfDouble[(j--)] = d;
    }
  }
  
  public void set(int paramInt, double paramDouble)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    this.elements[paramInt] = paramDouble;
  }
  
  public void setQuick(int paramInt, double paramDouble)
  {
    this.elements[paramInt] = paramDouble;
  }
  
  public void shuffleFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    Uniform localUniform = new Uniform(new DRand(new Date()));
    double[] arrayOfDouble = this.elements;
    for (int j = paramInt1; j < paramInt2; j++)
    {
      int i = localUniform.nextIntFromTo(j, paramInt2);
      double d = arrayOfDouble[i];
      arrayOfDouble[i] = arrayOfDouble[j];
      arrayOfDouble[j] = d;
    }
  }
  
  public void trimToSize()
  {
    this.elements = Arrays.trimToCapacity(this.elements, size());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.DoubleArrayList
 * JD-Core Version:    0.7.0.1
 */
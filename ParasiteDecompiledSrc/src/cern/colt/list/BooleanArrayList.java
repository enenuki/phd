package cern.colt.list;

import cern.colt.Arrays;
import cern.colt.function.BooleanProcedure;
import cern.jet.math.Arithmetic;
import cern.jet.random.Uniform;
import cern.jet.random.engine.DRand;
import java.util.Date;

public class BooleanArrayList
  extends AbstractBooleanList
{
  protected boolean[] elements;
  
  public BooleanArrayList()
  {
    this(10);
  }
  
  public BooleanArrayList(boolean[] paramArrayOfBoolean)
  {
    elements(paramArrayOfBoolean);
  }
  
  public BooleanArrayList(int paramInt)
  {
    this(new boolean[paramInt]);
    setSizeRaw(0);
  }
  
  public void add(boolean paramBoolean)
  {
    if (this.size == this.elements.length) {
      ensureCapacity(this.size + 1);
    }
    this.elements[(this.size++)] = paramBoolean;
  }
  
  public void beforeInsert(int paramInt, boolean paramBoolean)
  {
    if ((paramInt > this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    ensureCapacity(this.size + 1);
    System.arraycopy(this.elements, paramInt, this.elements, paramInt + 1, this.size - paramInt);
    this.elements[paramInt] = paramBoolean;
    this.size += 1;
  }
  
  public Object clone()
  {
    BooleanArrayList localBooleanArrayList = new BooleanArrayList((boolean[])this.elements.clone());
    localBooleanArrayList.setSizeRaw(this.size);
    return localBooleanArrayList;
  }
  
  public BooleanArrayList copy()
  {
    return (BooleanArrayList)clone();
  }
  
  public void countSortFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    boolean[] arrayOfBoolean = this.elements;
    int i = 0;
    int j = paramInt1;
    while (j <= paramInt2) {
      if (arrayOfBoolean[(j++)] != 0) {
        i++;
      }
    }
    j = paramInt2 - paramInt1 + 1 - i;
    if (j > 0) {
      fillFromToWith(paramInt1, paramInt1 + j - 1, false);
    }
    if (i > 0) {
      fillFromToWith(paramInt1 + j, paramInt1 + j - 1 + i, true);
    }
  }
  
  public boolean[] elements()
  {
    return this.elements;
  }
  
  public AbstractBooleanList elements(boolean[] paramArrayOfBoolean)
  {
    this.elements = paramArrayOfBoolean;
    this.size = paramArrayOfBoolean.length;
    return this;
  }
  
  public void ensureCapacity(int paramInt)
  {
    this.elements = Arrays.ensureCapacity(this.elements, paramInt);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof BooleanArrayList)) {
      return super.equals(paramObject);
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    BooleanArrayList localBooleanArrayList = (BooleanArrayList)paramObject;
    if (size() != localBooleanArrayList.size()) {
      return false;
    }
    boolean[] arrayOfBoolean1 = elements();
    boolean[] arrayOfBoolean2 = localBooleanArrayList.elements();
    int i = size();
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (arrayOfBoolean1[i] == arrayOfBoolean2[i]);
    return false;
    return true;
  }
  
  public boolean forEach(BooleanProcedure paramBooleanProcedure)
  {
    boolean[] arrayOfBoolean = this.elements;
    int i = this.size;
    int j = 0;
    while (j < i) {
      if (!paramBooleanProcedure.apply(arrayOfBoolean[(j++)])) {
        return false;
      }
    }
    return true;
  }
  
  public boolean get(int paramInt)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    return this.elements[paramInt];
  }
  
  public boolean getQuick(int paramInt)
  {
    return this.elements[paramInt];
  }
  
  public int indexOfFromTo(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return -1;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    boolean[] arrayOfBoolean = this.elements;
    for (int i = paramInt1; i <= paramInt2; i++) {
      if (paramBoolean == arrayOfBoolean[i]) {
        return i;
      }
    }
    return -1;
  }
  
  public int lastIndexOfFromTo(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return -1;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    boolean[] arrayOfBoolean = this.elements;
    for (int i = paramInt2; i >= paramInt1; i--) {
      if (paramBoolean == arrayOfBoolean[i]) {
        return i;
      }
    }
    return -1;
  }
  
  public void mergeSortFromTo(int paramInt1, int paramInt2)
  {
    countSortFromTo(paramInt1, paramInt2);
  }
  
  public AbstractBooleanList partFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return new BooleanArrayList(0);
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    boolean[] arrayOfBoolean = new boolean[paramInt2 - paramInt1 + 1];
    System.arraycopy(this.elements, paramInt1, arrayOfBoolean, 0, paramInt2 - paramInt1 + 1);
    return new BooleanArrayList(arrayOfBoolean);
  }
  
  public void quickSortFromTo(int paramInt1, int paramInt2)
  {
    countSortFromTo(paramInt1, paramInt2);
  }
  
  public boolean removeAll(AbstractBooleanList paramAbstractBooleanList)
  {
    if (!(paramAbstractBooleanList instanceof BooleanArrayList)) {
      return super.removeAll(paramAbstractBooleanList);
    }
    if (paramAbstractBooleanList.size() == 0) {
      return false;
    }
    int i = paramAbstractBooleanList.size() - 1;
    int j = 0;
    boolean[] arrayOfBoolean = this.elements;
    int k = size();
    double d1 = paramAbstractBooleanList.size();
    double d2 = k;
    if ((d1 + d2) * Arithmetic.log2(d1) < d2 * d1)
    {
      BooleanArrayList localBooleanArrayList = (BooleanArrayList)paramAbstractBooleanList.clone();
      localBooleanArrayList.quickSort();
      for (int n = 0; n < k; n++) {
        if (localBooleanArrayList.binarySearchFromTo(arrayOfBoolean[n], 0, i) < 0) {
          arrayOfBoolean[(j++)] = arrayOfBoolean[n];
        }
      }
    }
    for (int m = 0; m < k; m++) {
      if (paramAbstractBooleanList.indexOfFromTo(arrayOfBoolean[m], 0, i) < 0) {
        arrayOfBoolean[(j++)] = arrayOfBoolean[m];
      }
    }
    m = j != k ? 1 : 0;
    setSize(j);
    return m;
  }
  
  public void replaceFromToWithFrom(int paramInt1, int paramInt2, AbstractBooleanList paramAbstractBooleanList, int paramInt3)
  {
    if (!(paramAbstractBooleanList instanceof BooleanArrayList))
    {
      super.replaceFromToWithFrom(paramInt1, paramInt2, paramAbstractBooleanList, paramInt3);
      return;
    }
    int i = paramInt2 - paramInt1 + 1;
    if (i > 0)
    {
      checkRangeFromTo(paramInt1, paramInt2, size());
      checkRangeFromTo(paramInt3, paramInt3 + i - 1, paramAbstractBooleanList.size());
      System.arraycopy(((BooleanArrayList)paramAbstractBooleanList).elements, paramInt3, this.elements, paramInt1, i);
    }
  }
  
  public boolean retainAll(AbstractBooleanList paramAbstractBooleanList)
  {
    if (!(paramAbstractBooleanList instanceof BooleanArrayList)) {
      return super.retainAll(paramAbstractBooleanList);
    }
    int i = paramAbstractBooleanList.size() - 1;
    int j = 0;
    boolean[] arrayOfBoolean = this.elements;
    int k = size();
    double d1 = paramAbstractBooleanList.size();
    double d2 = k;
    if ((d1 + d2) * Arithmetic.log2(d1) < d2 * d1)
    {
      BooleanArrayList localBooleanArrayList = (BooleanArrayList)paramAbstractBooleanList.clone();
      localBooleanArrayList.quickSort();
      for (int n = 0; n < k; n++) {
        if (localBooleanArrayList.binarySearchFromTo(arrayOfBoolean[n], 0, i) >= 0) {
          arrayOfBoolean[(j++)] = arrayOfBoolean[n];
        }
      }
    }
    for (int m = 0; m < k; m++) {
      if (paramAbstractBooleanList.indexOfFromTo(arrayOfBoolean[m], 0, i) >= 0) {
        arrayOfBoolean[(j++)] = arrayOfBoolean[m];
      }
    }
    m = j != k ? 1 : 0;
    setSize(j);
    return m;
  }
  
  public void reverse()
  {
    int j = this.size / 2;
    int k = this.size - 1;
    boolean[] arrayOfBoolean = this.elements;
    int m = 0;
    while (m < j)
    {
      int i = arrayOfBoolean[m];
      arrayOfBoolean[(m++)] = arrayOfBoolean[k];
      arrayOfBoolean[(k--)] = i;
    }
  }
  
  public void set(int paramInt, boolean paramBoolean)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    this.elements[paramInt] = paramBoolean;
  }
  
  public void setQuick(int paramInt, boolean paramBoolean)
  {
    this.elements[paramInt] = paramBoolean;
  }
  
  public void shuffleFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    Uniform localUniform = new Uniform(new DRand(new Date()));
    boolean[] arrayOfBoolean = this.elements;
    for (int k = paramInt1; k < paramInt2; k++)
    {
      int j = localUniform.nextIntFromTo(k, paramInt2);
      int i = arrayOfBoolean[j];
      arrayOfBoolean[j] = arrayOfBoolean[k];
      arrayOfBoolean[k] = i;
    }
  }
  
  public void sortFromTo(int paramInt1, int paramInt2)
  {
    countSortFromTo(paramInt1, paramInt2);
  }
  
  public void trimToSize()
  {
    this.elements = Arrays.trimToCapacity(this.elements, size());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.BooleanArrayList
 * JD-Core Version:    0.7.0.1
 */
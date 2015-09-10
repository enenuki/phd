package cern.colt.list;

import cern.colt.Arrays;
import cern.colt.Sorting;
import cern.colt.function.FloatProcedure;
import cern.jet.math.Arithmetic;
import cern.jet.random.Uniform;
import cern.jet.random.engine.DRand;
import java.util.Date;

public class FloatArrayList
  extends AbstractFloatList
{
  protected float[] elements;
  
  public FloatArrayList()
  {
    this(10);
  }
  
  public FloatArrayList(float[] paramArrayOfFloat)
  {
    elements(paramArrayOfFloat);
  }
  
  public FloatArrayList(int paramInt)
  {
    this(new float[paramInt]);
    setSizeRaw(0);
  }
  
  public void add(float paramFloat)
  {
    if (this.size == this.elements.length) {
      ensureCapacity(this.size + 1);
    }
    this.elements[(this.size++)] = paramFloat;
  }
  
  public void beforeInsert(int paramInt, float paramFloat)
  {
    if ((paramInt > this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    ensureCapacity(this.size + 1);
    System.arraycopy(this.elements, paramInt, this.elements, paramInt + 1, this.size - paramInt);
    this.elements[paramInt] = paramFloat;
    this.size += 1;
  }
  
  public int binarySearchFromTo(float paramFloat, int paramInt1, int paramInt2)
  {
    return Sorting.binarySearchFromTo(this.elements, paramFloat, paramInt1, paramInt2);
  }
  
  public Object clone()
  {
    FloatArrayList localFloatArrayList = new FloatArrayList((float[])this.elements.clone());
    localFloatArrayList.setSizeRaw(this.size);
    return localFloatArrayList;
  }
  
  public FloatArrayList copy()
  {
    return (FloatArrayList)clone();
  }
  
  public float[] elements()
  {
    return this.elements;
  }
  
  public AbstractFloatList elements(float[] paramArrayOfFloat)
  {
    this.elements = paramArrayOfFloat;
    this.size = paramArrayOfFloat.length;
    return this;
  }
  
  public void ensureCapacity(int paramInt)
  {
    this.elements = Arrays.ensureCapacity(this.elements, paramInt);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof FloatArrayList)) {
      return super.equals(paramObject);
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    FloatArrayList localFloatArrayList = (FloatArrayList)paramObject;
    if (size() != localFloatArrayList.size()) {
      return false;
    }
    float[] arrayOfFloat1 = elements();
    float[] arrayOfFloat2 = localFloatArrayList.elements();
    int i = size();
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (arrayOfFloat1[i] == arrayOfFloat2[i]);
    return false;
    return true;
  }
  
  public boolean forEach(FloatProcedure paramFloatProcedure)
  {
    float[] arrayOfFloat = this.elements;
    int i = this.size;
    int j = 0;
    while (j < i) {
      if (!paramFloatProcedure.apply(arrayOfFloat[(j++)])) {
        return false;
      }
    }
    return true;
  }
  
  public float get(int paramInt)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    return this.elements[paramInt];
  }
  
  public float getQuick(int paramInt)
  {
    return this.elements[paramInt];
  }
  
  public int indexOfFromTo(float paramFloat, int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return -1;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    float[] arrayOfFloat = this.elements;
    for (int i = paramInt1; i <= paramInt2; i++) {
      if (paramFloat == arrayOfFloat[i]) {
        return i;
      }
    }
    return -1;
  }
  
  public int lastIndexOfFromTo(float paramFloat, int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return -1;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    float[] arrayOfFloat = this.elements;
    for (int i = paramInt2; i >= paramInt1; i--) {
      if (paramFloat == arrayOfFloat[i]) {
        return i;
      }
    }
    return -1;
  }
  
  public AbstractFloatList partFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return new FloatArrayList(0);
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    float[] arrayOfFloat = new float[paramInt2 - paramInt1 + 1];
    System.arraycopy(this.elements, paramInt1, arrayOfFloat, 0, paramInt2 - paramInt1 + 1);
    return new FloatArrayList(arrayOfFloat);
  }
  
  public boolean removeAll(AbstractFloatList paramAbstractFloatList)
  {
    if (!(paramAbstractFloatList instanceof FloatArrayList)) {
      return super.removeAll(paramAbstractFloatList);
    }
    if (paramAbstractFloatList.size() == 0) {
      return false;
    }
    int i = paramAbstractFloatList.size() - 1;
    int j = 0;
    float[] arrayOfFloat = this.elements;
    int k = size();
    double d1 = paramAbstractFloatList.size();
    double d2 = k;
    if ((d1 + d2) * Arithmetic.log2(d1) < d2 * d1)
    {
      FloatArrayList localFloatArrayList = (FloatArrayList)paramAbstractFloatList.clone();
      localFloatArrayList.quickSort();
      for (int n = 0; n < k; n++) {
        if (localFloatArrayList.binarySearchFromTo(arrayOfFloat[n], 0, i) < 0) {
          arrayOfFloat[(j++)] = arrayOfFloat[n];
        }
      }
    }
    for (int m = 0; m < k; m++) {
      if (paramAbstractFloatList.indexOfFromTo(arrayOfFloat[m], 0, i) < 0) {
        arrayOfFloat[(j++)] = arrayOfFloat[m];
      }
    }
    m = j != k ? 1 : 0;
    setSize(j);
    return m;
  }
  
  public void replaceFromToWithFrom(int paramInt1, int paramInt2, AbstractFloatList paramAbstractFloatList, int paramInt3)
  {
    if (!(paramAbstractFloatList instanceof FloatArrayList))
    {
      super.replaceFromToWithFrom(paramInt1, paramInt2, paramAbstractFloatList, paramInt3);
      return;
    }
    int i = paramInt2 - paramInt1 + 1;
    if (i > 0)
    {
      checkRangeFromTo(paramInt1, paramInt2, size());
      checkRangeFromTo(paramInt3, paramInt3 + i - 1, paramAbstractFloatList.size());
      System.arraycopy(((FloatArrayList)paramAbstractFloatList).elements, paramInt3, this.elements, paramInt1, i);
    }
  }
  
  public boolean retainAll(AbstractFloatList paramAbstractFloatList)
  {
    if (!(paramAbstractFloatList instanceof FloatArrayList)) {
      return super.retainAll(paramAbstractFloatList);
    }
    int i = paramAbstractFloatList.size() - 1;
    int j = 0;
    float[] arrayOfFloat = this.elements;
    int k = size();
    double d1 = paramAbstractFloatList.size();
    double d2 = k;
    if ((d1 + d2) * Arithmetic.log2(d1) < d2 * d1)
    {
      FloatArrayList localFloatArrayList = (FloatArrayList)paramAbstractFloatList.clone();
      localFloatArrayList.quickSort();
      for (int n = 0; n < k; n++) {
        if (localFloatArrayList.binarySearchFromTo(arrayOfFloat[n], 0, i) >= 0) {
          arrayOfFloat[(j++)] = arrayOfFloat[n];
        }
      }
    }
    for (int m = 0; m < k; m++) {
      if (paramAbstractFloatList.indexOfFromTo(arrayOfFloat[m], 0, i) >= 0) {
        arrayOfFloat[(j++)] = arrayOfFloat[m];
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
    float[] arrayOfFloat = this.elements;
    int k = 0;
    while (k < i)
    {
      float f = arrayOfFloat[k];
      arrayOfFloat[(k++)] = arrayOfFloat[j];
      arrayOfFloat[(j--)] = f;
    }
  }
  
  public void set(int paramInt, float paramFloat)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    this.elements[paramInt] = paramFloat;
  }
  
  public void setQuick(int paramInt, float paramFloat)
  {
    this.elements[paramInt] = paramFloat;
  }
  
  public void shuffleFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    Uniform localUniform = new Uniform(new DRand(new Date()));
    float[] arrayOfFloat = this.elements;
    for (int j = paramInt1; j < paramInt2; j++)
    {
      int i = localUniform.nextIntFromTo(j, paramInt2);
      float f = arrayOfFloat[i];
      arrayOfFloat[i] = arrayOfFloat[j];
      arrayOfFloat[j] = f;
    }
  }
  
  public void trimToSize()
  {
    this.elements = Arrays.trimToCapacity(this.elements, size());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.FloatArrayList
 * JD-Core Version:    0.7.0.1
 */
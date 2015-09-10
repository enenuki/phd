package cern.colt.list;

import cern.colt.Arrays;
import cern.colt.Sorting;
import cern.colt.function.CharProcedure;
import cern.jet.math.Arithmetic;
import cern.jet.random.Uniform;
import cern.jet.random.engine.DRand;
import java.util.Date;

public class CharArrayList
  extends AbstractCharList
{
  protected char[] elements;
  
  public CharArrayList()
  {
    this(10);
  }
  
  public CharArrayList(char[] paramArrayOfChar)
  {
    elements(paramArrayOfChar);
  }
  
  public CharArrayList(int paramInt)
  {
    this(new char[paramInt]);
    setSizeRaw(0);
  }
  
  public void add(char paramChar)
  {
    if (this.size == this.elements.length) {
      ensureCapacity(this.size + 1);
    }
    this.elements[(this.size++)] = paramChar;
  }
  
  public void beforeInsert(int paramInt, char paramChar)
  {
    if ((paramInt > this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    ensureCapacity(this.size + 1);
    System.arraycopy(this.elements, paramInt, this.elements, paramInt + 1, this.size - paramInt);
    this.elements[paramInt] = paramChar;
    this.size += 1;
  }
  
  public int binarySearchFromTo(char paramChar, int paramInt1, int paramInt2)
  {
    return Sorting.binarySearchFromTo(this.elements, paramChar, paramInt1, paramInt2);
  }
  
  public Object clone()
  {
    CharArrayList localCharArrayList = new CharArrayList((char[])this.elements.clone());
    localCharArrayList.setSizeRaw(this.size);
    return localCharArrayList;
  }
  
  public CharArrayList copy()
  {
    return (CharArrayList)clone();
  }
  
  protected void countSortFromTo(int paramInt1, int paramInt2, char paramChar1, char paramChar2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramChar2 - paramChar1 + 1;
    int[] arrayOfInt = new int[i];
    char[] arrayOfChar = this.elements;
    int j = paramInt1;
    while (j <= paramInt2) {
      arrayOfInt[(arrayOfChar[(j++)] - paramChar1)] += 1;
    }
    j = paramInt1;
    char c = paramChar1;
    int k = 0;
    while (k < i)
    {
      int m = arrayOfInt[k];
      if (m > 0) {
        if (m == 1)
        {
          arrayOfChar[(j++)] = c;
        }
        else
        {
          int n = j + m - 1;
          fillFromToWith(j, n, c);
          j = n + 1;
        }
      }
      k++;
      c = (char)(c + '\001');
    }
  }
  
  public char[] elements()
  {
    return this.elements;
  }
  
  public AbstractCharList elements(char[] paramArrayOfChar)
  {
    this.elements = paramArrayOfChar;
    this.size = paramArrayOfChar.length;
    return this;
  }
  
  public void ensureCapacity(int paramInt)
  {
    this.elements = Arrays.ensureCapacity(this.elements, paramInt);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof CharArrayList)) {
      return super.equals(paramObject);
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    CharArrayList localCharArrayList = (CharArrayList)paramObject;
    if (size() != localCharArrayList.size()) {
      return false;
    }
    char[] arrayOfChar1 = elements();
    char[] arrayOfChar2 = localCharArrayList.elements();
    int i = size();
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (arrayOfChar1[i] == arrayOfChar2[i]);
    return false;
    return true;
  }
  
  public boolean forEach(CharProcedure paramCharProcedure)
  {
    char[] arrayOfChar = this.elements;
    int i = this.size;
    int j = 0;
    while (j < i) {
      if (!paramCharProcedure.apply(arrayOfChar[(j++)])) {
        return false;
      }
    }
    return true;
  }
  
  public char get(int paramInt)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    return this.elements[paramInt];
  }
  
  public char getQuick(int paramInt)
  {
    return this.elements[paramInt];
  }
  
  public int indexOfFromTo(char paramChar, int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return -1;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    char[] arrayOfChar = this.elements;
    for (int i = paramInt1; i <= paramInt2; i++) {
      if (paramChar == arrayOfChar[i]) {
        return i;
      }
    }
    return -1;
  }
  
  public int lastIndexOfFromTo(char paramChar, int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return -1;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    char[] arrayOfChar = this.elements;
    for (int i = paramInt2; i >= paramInt1; i--) {
      if (paramChar == arrayOfChar[i]) {
        return i;
      }
    }
    return -1;
  }
  
  public AbstractCharList partFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return new CharArrayList(0);
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    char[] arrayOfChar = new char[paramInt2 - paramInt1 + 1];
    System.arraycopy(this.elements, paramInt1, arrayOfChar, 0, paramInt2 - paramInt1 + 1);
    return new CharArrayList(arrayOfChar);
  }
  
  public boolean removeAll(AbstractCharList paramAbstractCharList)
  {
    if (!(paramAbstractCharList instanceof CharArrayList)) {
      return super.removeAll(paramAbstractCharList);
    }
    if (paramAbstractCharList.size() == 0) {
      return false;
    }
    int i = paramAbstractCharList.size() - 1;
    int j = 0;
    char[] arrayOfChar = this.elements;
    int k = size();
    double d1 = paramAbstractCharList.size();
    double d2 = k;
    if ((d1 + d2) * Arithmetic.log2(d1) < d2 * d1)
    {
      CharArrayList localCharArrayList = (CharArrayList)paramAbstractCharList.clone();
      localCharArrayList.quickSort();
      for (int n = 0; n < k; n++) {
        if (localCharArrayList.binarySearchFromTo(arrayOfChar[n], 0, i) < 0) {
          arrayOfChar[(j++)] = arrayOfChar[n];
        }
      }
    }
    for (int m = 0; m < k; m++) {
      if (paramAbstractCharList.indexOfFromTo(arrayOfChar[m], 0, i) < 0) {
        arrayOfChar[(j++)] = arrayOfChar[m];
      }
    }
    m = j != k ? 1 : 0;
    setSize(j);
    return m;
  }
  
  public void replaceFromToWithFrom(int paramInt1, int paramInt2, AbstractCharList paramAbstractCharList, int paramInt3)
  {
    if (!(paramAbstractCharList instanceof CharArrayList))
    {
      super.replaceFromToWithFrom(paramInt1, paramInt2, paramAbstractCharList, paramInt3);
      return;
    }
    int i = paramInt2 - paramInt1 + 1;
    if (i > 0)
    {
      checkRangeFromTo(paramInt1, paramInt2, size());
      checkRangeFromTo(paramInt3, paramInt3 + i - 1, paramAbstractCharList.size());
      System.arraycopy(((CharArrayList)paramAbstractCharList).elements, paramInt3, this.elements, paramInt1, i);
    }
  }
  
  public boolean retainAll(AbstractCharList paramAbstractCharList)
  {
    if (!(paramAbstractCharList instanceof CharArrayList)) {
      return super.retainAll(paramAbstractCharList);
    }
    int i = paramAbstractCharList.size() - 1;
    int j = 0;
    char[] arrayOfChar = this.elements;
    int k = size();
    double d1 = paramAbstractCharList.size();
    double d2 = k;
    if ((d1 + d2) * Arithmetic.log2(d1) < d2 * d1)
    {
      CharArrayList localCharArrayList = (CharArrayList)paramAbstractCharList.clone();
      localCharArrayList.quickSort();
      for (int n = 0; n < k; n++) {
        if (localCharArrayList.binarySearchFromTo(arrayOfChar[n], 0, i) >= 0) {
          arrayOfChar[(j++)] = arrayOfChar[n];
        }
      }
    }
    for (int m = 0; m < k; m++) {
      if (paramAbstractCharList.indexOfFromTo(arrayOfChar[m], 0, i) >= 0) {
        arrayOfChar[(j++)] = arrayOfChar[m];
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
    char[] arrayOfChar = this.elements;
    int m = 0;
    while (m < j)
    {
      int i = arrayOfChar[m];
      arrayOfChar[(m++)] = arrayOfChar[k];
      arrayOfChar[(k--)] = i;
    }
  }
  
  public void set(int paramInt, char paramChar)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    this.elements[paramInt] = paramChar;
  }
  
  public void setQuick(int paramInt, char paramChar)
  {
    this.elements[paramInt] = paramChar;
  }
  
  public void shuffleFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    Uniform localUniform = new Uniform(new DRand(new Date()));
    char[] arrayOfChar = this.elements;
    for (int k = paramInt1; k < paramInt2; k++)
    {
      int j = localUniform.nextIntFromTo(k, paramInt2);
      int i = arrayOfChar[j];
      arrayOfChar[j] = arrayOfChar[k];
      arrayOfChar[k] = i;
    }
  }
  
  public void sortFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    char c1 = this.elements[paramInt1];
    char c2 = this.elements[paramInt1];
    char[] arrayOfChar = this.elements;
    int i = paramInt1 + 1;
    while (i <= paramInt2)
    {
      char c3 = arrayOfChar[(i++)];
      if (c3 > c2) {
        c2 = c3;
      } else if (c3 < c1) {
        c1 = c3;
      }
    }
    double d1 = paramInt2 - paramInt1 + 1.0D;
    double d2 = d1 * Math.log(d1) / 0.6931471805599453D;
    double d3 = c2 - c1 + 1.0D;
    double d4 = Math.max(d3, d1);
    if ((d3 < 10000.0D) && (d4 < d2)) {
      countSortFromTo(paramInt1, paramInt2, c1, c2);
    } else {
      quickSortFromTo(paramInt1, paramInt2);
    }
  }
  
  public void trimToSize()
  {
    this.elements = Arrays.trimToCapacity(this.elements, size());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.CharArrayList
 * JD-Core Version:    0.7.0.1
 */
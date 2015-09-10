package cern.colt;

import cern.colt.function.ByteComparator;
import cern.colt.function.CharComparator;
import cern.colt.function.DoubleComparator;
import cern.colt.function.FloatComparator;
import cern.colt.function.IntComparator;
import cern.colt.function.LongComparator;
import cern.colt.function.ShortComparator;
import cern.colt.list.DoubleArrayList;
import cern.colt.list.FloatArrayList;
import java.util.Comparator;

public class Sorting
{
  private static final int SMALL = 7;
  private static final int MEDIUM = 40;
  
  public static int binarySearchFromTo(byte[] paramArrayOfByte, byte paramByte, int paramInt1, int paramInt2)
  {
    while (paramInt1 <= paramInt2)
    {
      int i = (paramInt1 + paramInt2) / 2;
      byte b = paramArrayOfByte[i];
      if (b < paramByte) {
        paramInt1 = i + 1;
      } else if (b > paramByte) {
        paramInt2 = i - 1;
      } else {
        return i;
      }
    }
    return -(paramInt1 + 1);
  }
  
  public static int binarySearchFromTo(char[] paramArrayOfChar, char paramChar, int paramInt1, int paramInt2)
  {
    while (paramInt1 <= paramInt2)
    {
      int i = (paramInt1 + paramInt2) / 2;
      char c = paramArrayOfChar[i];
      if (c < paramChar) {
        paramInt1 = i + 1;
      } else if (c > paramChar) {
        paramInt2 = i - 1;
      } else {
        return i;
      }
    }
    return -(paramInt1 + 1);
  }
  
  public static int binarySearchFromTo(double[] paramArrayOfDouble, double paramDouble, int paramInt1, int paramInt2)
  {
    while (paramInt1 <= paramInt2)
    {
      int i = (paramInt1 + paramInt2) / 2;
      double d = paramArrayOfDouble[i];
      if (d < paramDouble) {
        paramInt1 = i + 1;
      } else if (d > paramDouble) {
        paramInt2 = i - 1;
      } else {
        return i;
      }
    }
    return -(paramInt1 + 1);
  }
  
  public static int binarySearchFromTo(float[] paramArrayOfFloat, float paramFloat, int paramInt1, int paramInt2)
  {
    while (paramInt1 <= paramInt2)
    {
      int i = (paramInt1 + paramInt2) / 2;
      float f = paramArrayOfFloat[i];
      if (f < paramFloat) {
        paramInt1 = i + 1;
      } else if (f > paramFloat) {
        paramInt2 = i - 1;
      } else {
        return i;
      }
    }
    return -(paramInt1 + 1);
  }
  
  public static int binarySearchFromTo(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    while (paramInt2 <= paramInt3)
    {
      int j = (paramInt2 + paramInt3) / 2;
      int i = paramArrayOfInt[j];
      if (i < paramInt1) {
        paramInt2 = j + 1;
      } else if (i > paramInt1) {
        paramInt3 = j - 1;
      } else {
        return j;
      }
    }
    return -(paramInt2 + 1);
  }
  
  public static int binarySearchFromTo(long[] paramArrayOfLong, long paramLong, int paramInt1, int paramInt2)
  {
    while (paramInt1 <= paramInt2)
    {
      int i = (paramInt1 + paramInt2) / 2;
      long l = paramArrayOfLong[i];
      if (l < paramLong) {
        paramInt1 = i + 1;
      } else if (l > paramLong) {
        paramInt2 = i - 1;
      } else {
        return i;
      }
    }
    return -(paramInt1 + 1);
  }
  
  public static int binarySearchFromTo(Object[] paramArrayOfObject, Object paramObject, int paramInt1, int paramInt2, Comparator paramComparator)
  {
    while (paramInt1 <= paramInt2)
    {
      int i = (paramInt1 + paramInt2) / 2;
      Object localObject = paramArrayOfObject[i];
      int j = paramComparator.compare(localObject, paramObject);
      if (j < 0) {
        paramInt1 = i + 1;
      } else if (j > 0) {
        paramInt2 = i - 1;
      } else {
        return i;
      }
    }
    return -(paramInt1 + 1);
  }
  
  public static int binarySearchFromTo(short[] paramArrayOfShort, short paramShort, int paramInt1, int paramInt2)
  {
    while (paramInt1 <= paramInt2)
    {
      int i = (paramInt1 + paramInt2) / 2;
      short s = paramArrayOfShort[i];
      if (s < paramShort) {
        paramInt1 = i + 1;
      } else if (s > paramShort) {
        paramInt2 = i - 1;
      } else {
        return i;
      }
    }
    return -(paramInt1 + 1);
  }
  
  public static int binarySearchFromTo(int paramInt1, int paramInt2, IntComparator paramIntComparator)
  {
    while (paramInt1 <= paramInt2)
    {
      int i = (paramInt1 + paramInt2) / 2;
      int j = paramIntComparator.compare(0, i);
      if (j < 0) {
        paramInt1 = i + 1;
      } else if (j > 0) {
        paramInt2 = i - 1;
      } else {
        return i;
      }
    }
    return -(paramInt1 + 1);
  }
  
  private static int lower_bound(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt2 - paramInt1;
    while (i > 0)
    {
      int j = i / 2;
      int k = paramInt1 + j;
      if (paramArrayOfInt[k] < paramInt3)
      {
        paramInt1 = k + 1;
        i -= j + 1;
      }
      else
      {
        i = j;
      }
    }
    return paramInt1;
  }
  
  private static int upper_bound(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt2 - paramInt1;
    while (i > 0)
    {
      int j = i / 2;
      int k = paramInt1 + j;
      if (paramInt3 < paramArrayOfInt[k])
      {
        i = j;
      }
      else
      {
        paramInt1 = k + 1;
        i -= j + 1;
      }
    }
    return paramInt1;
  }
  
  private static void inplace_merge(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 >= paramInt2) || (paramInt2 >= paramInt3)) {
      return;
    }
    int i;
    if (paramInt3 - paramInt1 == 2)
    {
      if (paramArrayOfInt[paramInt2] < paramArrayOfInt[paramInt1])
      {
        i = paramArrayOfInt[paramInt1];
        paramArrayOfInt[paramInt1] = paramArrayOfInt[paramInt2];
        paramArrayOfInt[paramInt2] = i;
      }
      return;
    }
    int j;
    if (paramInt2 - paramInt1 > paramInt3 - paramInt2)
    {
      i = paramInt1 + (paramInt2 - paramInt1) / 2;
      j = lower_bound(paramArrayOfInt, paramInt2, paramInt3, paramArrayOfInt[i]);
    }
    else
    {
      j = paramInt2 + (paramInt3 - paramInt2) / 2;
      i = upper_bound(paramArrayOfInt, paramInt1, paramInt2, paramArrayOfInt[j]);
    }
    int k = i;
    int m = paramInt2;
    int n = j;
    if ((m != k) && (m != n))
    {
      int i1 = k;
      int i2 = m;
      int i3;
      while (i1 < --i2)
      {
        i3 = paramArrayOfInt[i1];
        paramArrayOfInt[i2] = paramArrayOfInt[i1];
        paramArrayOfInt[(i1++)] = i3;
      }
      i1 = m;
      i2 = n;
      while (i1 < --i2)
      {
        i3 = paramArrayOfInt[i1];
        paramArrayOfInt[i2] = paramArrayOfInt[i1];
        paramArrayOfInt[(i1++)] = i3;
      }
      i1 = k;
      i2 = n;
      while (i1 < --i2)
      {
        i3 = paramArrayOfInt[i1];
        paramArrayOfInt[i2] = paramArrayOfInt[i1];
        paramArrayOfInt[(i1++)] = i3;
      }
    }
    paramInt2 = i + (j - paramInt2);
    inplace_merge(paramArrayOfInt, paramInt1, i, paramInt2);
    inplace_merge(paramArrayOfInt, paramInt2, j, paramInt3);
  }
  
  private static int med3(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, ByteComparator paramByteComparator)
  {
    int i = paramByteComparator.compare(paramArrayOfByte[paramInt1], paramArrayOfByte[paramInt2]);
    int j = paramByteComparator.compare(paramArrayOfByte[paramInt1], paramArrayOfByte[paramInt3]);
    int k = paramByteComparator.compare(paramArrayOfByte[paramInt2], paramArrayOfByte[paramInt3]);
    return j > 0 ? paramInt3 : k > 0 ? paramInt2 : i < 0 ? paramInt1 : j < 0 ? paramInt3 : k < 0 ? paramInt2 : paramInt1;
  }
  
  private static int med3(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3, CharComparator paramCharComparator)
  {
    int i = paramCharComparator.compare(paramArrayOfChar[paramInt1], paramArrayOfChar[paramInt2]);
    int j = paramCharComparator.compare(paramArrayOfChar[paramInt1], paramArrayOfChar[paramInt3]);
    int k = paramCharComparator.compare(paramArrayOfChar[paramInt2], paramArrayOfChar[paramInt3]);
    return j > 0 ? paramInt3 : k > 0 ? paramInt2 : i < 0 ? paramInt1 : j < 0 ? paramInt3 : k < 0 ? paramInt2 : paramInt1;
  }
  
  private static int med3(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3, DoubleComparator paramDoubleComparator)
  {
    int i = paramDoubleComparator.compare(paramArrayOfDouble[paramInt1], paramArrayOfDouble[paramInt2]);
    int j = paramDoubleComparator.compare(paramArrayOfDouble[paramInt1], paramArrayOfDouble[paramInt3]);
    int k = paramDoubleComparator.compare(paramArrayOfDouble[paramInt2], paramArrayOfDouble[paramInt3]);
    return j > 0 ? paramInt3 : k > 0 ? paramInt2 : i < 0 ? paramInt1 : j < 0 ? paramInt3 : k < 0 ? paramInt2 : paramInt1;
  }
  
  private static int med3(float[] paramArrayOfFloat, int paramInt1, int paramInt2, int paramInt3, FloatComparator paramFloatComparator)
  {
    int i = paramFloatComparator.compare(paramArrayOfFloat[paramInt1], paramArrayOfFloat[paramInt2]);
    int j = paramFloatComparator.compare(paramArrayOfFloat[paramInt1], paramArrayOfFloat[paramInt3]);
    int k = paramFloatComparator.compare(paramArrayOfFloat[paramInt2], paramArrayOfFloat[paramInt3]);
    return j > 0 ? paramInt3 : k > 0 ? paramInt2 : i < 0 ? paramInt1 : j < 0 ? paramInt3 : k < 0 ? paramInt2 : paramInt1;
  }
  
  private static int med3(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, IntComparator paramIntComparator)
  {
    int i = paramIntComparator.compare(paramArrayOfInt[paramInt1], paramArrayOfInt[paramInt2]);
    int j = paramIntComparator.compare(paramArrayOfInt[paramInt1], paramArrayOfInt[paramInt3]);
    int k = paramIntComparator.compare(paramArrayOfInt[paramInt2], paramArrayOfInt[paramInt3]);
    return j > 0 ? paramInt3 : k > 0 ? paramInt2 : i < 0 ? paramInt1 : j < 0 ? paramInt3 : k < 0 ? paramInt2 : paramInt1;
  }
  
  private static int med3(long[] paramArrayOfLong, int paramInt1, int paramInt2, int paramInt3, LongComparator paramLongComparator)
  {
    int i = paramLongComparator.compare(paramArrayOfLong[paramInt1], paramArrayOfLong[paramInt2]);
    int j = paramLongComparator.compare(paramArrayOfLong[paramInt1], paramArrayOfLong[paramInt3]);
    int k = paramLongComparator.compare(paramArrayOfLong[paramInt2], paramArrayOfLong[paramInt3]);
    return j > 0 ? paramInt3 : k > 0 ? paramInt2 : i < 0 ? paramInt1 : j < 0 ? paramInt3 : k < 0 ? paramInt2 : paramInt1;
  }
  
  private static int med3(Object[] paramArrayOfObject, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = ((Comparable)paramArrayOfObject[paramInt1]).compareTo((Comparable)paramArrayOfObject[paramInt2]);
    int j = ((Comparable)paramArrayOfObject[paramInt1]).compareTo((Comparable)paramArrayOfObject[paramInt3]);
    int k = ((Comparable)paramArrayOfObject[paramInt2]).compareTo((Comparable)paramArrayOfObject[paramInt3]);
    return j > 0 ? paramInt3 : k > 0 ? paramInt2 : i < 0 ? paramInt1 : j < 0 ? paramInt3 : k < 0 ? paramInt2 : paramInt1;
  }
  
  private static int med3(Object[] paramArrayOfObject, int paramInt1, int paramInt2, int paramInt3, Comparator paramComparator)
  {
    int i = paramComparator.compare(paramArrayOfObject[paramInt1], paramArrayOfObject[paramInt2]);
    int j = paramComparator.compare(paramArrayOfObject[paramInt1], paramArrayOfObject[paramInt3]);
    int k = paramComparator.compare(paramArrayOfObject[paramInt2], paramArrayOfObject[paramInt3]);
    return j > 0 ? paramInt3 : k > 0 ? paramInt2 : i < 0 ? paramInt1 : j < 0 ? paramInt3 : k < 0 ? paramInt2 : paramInt1;
  }
  
  private static int med3(short[] paramArrayOfShort, int paramInt1, int paramInt2, int paramInt3, ShortComparator paramShortComparator)
  {
    int i = paramShortComparator.compare(paramArrayOfShort[paramInt1], paramArrayOfShort[paramInt2]);
    int j = paramShortComparator.compare(paramArrayOfShort[paramInt1], paramArrayOfShort[paramInt3]);
    int k = paramShortComparator.compare(paramArrayOfShort[paramInt2], paramArrayOfShort[paramInt3]);
    return j > 0 ? paramInt3 : k > 0 ? paramInt2 : i < 0 ? paramInt1 : j < 0 ? paramInt3 : k < 0 ? paramInt2 : paramInt1;
  }
  
  public static void mergeSort(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    rangeCheck(paramArrayOfByte.length, paramInt1, paramInt2);
    byte[] arrayOfByte = (byte[])paramArrayOfByte.clone();
    mergeSort1(arrayOfByte, paramArrayOfByte, paramInt1, paramInt2);
  }
  
  public static void mergeSort(byte[] paramArrayOfByte, int paramInt1, int paramInt2, ByteComparator paramByteComparator)
  {
    rangeCheck(paramArrayOfByte.length, paramInt1, paramInt2);
    byte[] arrayOfByte = (byte[])paramArrayOfByte.clone();
    mergeSort1(arrayOfByte, paramArrayOfByte, paramInt1, paramInt2, paramByteComparator);
  }
  
  public static void mergeSort(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    rangeCheck(paramArrayOfChar.length, paramInt1, paramInt2);
    char[] arrayOfChar = (char[])paramArrayOfChar.clone();
    mergeSort1(arrayOfChar, paramArrayOfChar, paramInt1, paramInt2);
  }
  
  public static void mergeSort(char[] paramArrayOfChar, int paramInt1, int paramInt2, CharComparator paramCharComparator)
  {
    rangeCheck(paramArrayOfChar.length, paramInt1, paramInt2);
    char[] arrayOfChar = (char[])paramArrayOfChar.clone();
    mergeSort1(arrayOfChar, paramArrayOfChar, paramInt1, paramInt2, paramCharComparator);
  }
  
  public static void mergeSort(double[] paramArrayOfDouble, int paramInt1, int paramInt2)
  {
    mergeSort2(paramArrayOfDouble, paramInt1, paramInt2);
  }
  
  public static void mergeSort(double[] paramArrayOfDouble, int paramInt1, int paramInt2, DoubleComparator paramDoubleComparator)
  {
    rangeCheck(paramArrayOfDouble.length, paramInt1, paramInt2);
    double[] arrayOfDouble = (double[])paramArrayOfDouble.clone();
    mergeSort1(arrayOfDouble, paramArrayOfDouble, paramInt1, paramInt2, paramDoubleComparator);
  }
  
  public static void mergeSort(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
  {
    mergeSort2(paramArrayOfFloat, paramInt1, paramInt2);
  }
  
  public static void mergeSort(float[] paramArrayOfFloat, int paramInt1, int paramInt2, FloatComparator paramFloatComparator)
  {
    rangeCheck(paramArrayOfFloat.length, paramInt1, paramInt2);
    float[] arrayOfFloat = (float[])paramArrayOfFloat.clone();
    mergeSort1(arrayOfFloat, paramArrayOfFloat, paramInt1, paramInt2, paramFloatComparator);
  }
  
  public static void mergeSort(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    rangeCheck(paramArrayOfInt.length, paramInt1, paramInt2);
    int[] arrayOfInt = (int[])paramArrayOfInt.clone();
    mergeSort1(arrayOfInt, paramArrayOfInt, paramInt1, paramInt2);
  }
  
  public static void mergeSort(int[] paramArrayOfInt, int paramInt1, int paramInt2, IntComparator paramIntComparator)
  {
    rangeCheck(paramArrayOfInt.length, paramInt1, paramInt2);
    int[] arrayOfInt = (int[])paramArrayOfInt.clone();
    mergeSort1(arrayOfInt, paramArrayOfInt, paramInt1, paramInt2, paramIntComparator);
  }
  
  public static void mergeSort(long[] paramArrayOfLong, int paramInt1, int paramInt2)
  {
    rangeCheck(paramArrayOfLong.length, paramInt1, paramInt2);
    long[] arrayOfLong = (long[])paramArrayOfLong.clone();
    mergeSort1(arrayOfLong, paramArrayOfLong, paramInt1, paramInt2);
  }
  
  public static void mergeSort(long[] paramArrayOfLong, int paramInt1, int paramInt2, LongComparator paramLongComparator)
  {
    rangeCheck(paramArrayOfLong.length, paramInt1, paramInt2);
    long[] arrayOfLong = (long[])paramArrayOfLong.clone();
    mergeSort1(arrayOfLong, paramArrayOfLong, paramInt1, paramInt2, paramLongComparator);
  }
  
  public static void mergeSort(short[] paramArrayOfShort, int paramInt1, int paramInt2)
  {
    rangeCheck(paramArrayOfShort.length, paramInt1, paramInt2);
    short[] arrayOfShort = (short[])paramArrayOfShort.clone();
    mergeSort1(arrayOfShort, paramArrayOfShort, paramInt1, paramInt2);
  }
  
  public static void mergeSort(short[] paramArrayOfShort, int paramInt1, int paramInt2, ShortComparator paramShortComparator)
  {
    rangeCheck(paramArrayOfShort.length, paramInt1, paramInt2);
    short[] arrayOfShort = (short[])paramArrayOfShort.clone();
    mergeSort1(arrayOfShort, paramArrayOfShort, paramInt1, paramInt2, paramShortComparator);
  }
  
  private static void mergeSort1(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2)
  {
    int i = paramInt2 - paramInt1;
    if (i < 7)
    {
      for (j = paramInt1; j < paramInt2; j++) {
        for (k = j; (k > paramInt1) && (paramArrayOfByte2[(k - 1)] > paramArrayOfByte2[k]); k--) {
          swap(paramArrayOfByte2, k, k - 1);
        }
      }
      return;
    }
    int j = (paramInt1 + paramInt2) / 2;
    mergeSort1(paramArrayOfByte2, paramArrayOfByte1, paramInt1, j);
    mergeSort1(paramArrayOfByte2, paramArrayOfByte1, j, paramInt2);
    if (paramArrayOfByte1[(j - 1)] <= paramArrayOfByte1[j])
    {
      System.arraycopy(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt1, i);
      return;
    }
    int k = paramInt1;
    int m = paramInt1;
    int n = j;
    while (k < paramInt2)
    {
      if ((n >= paramInt2) || ((m < j) && (paramArrayOfByte1[m] <= paramArrayOfByte1[n]))) {
        paramArrayOfByte2[k] = paramArrayOfByte1[(m++)];
      } else {
        paramArrayOfByte2[k] = paramArrayOfByte1[(n++)];
      }
      k++;
    }
  }
  
  private static void mergeSort1(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, ByteComparator paramByteComparator)
  {
    int i = paramInt2 - paramInt1;
    if (i < 7)
    {
      for (j = paramInt1; j < paramInt2; j++) {
        for (k = j; (k > paramInt1) && (paramByteComparator.compare(paramArrayOfByte2[(k - 1)], paramArrayOfByte2[k]) > 0); k--) {
          swap(paramArrayOfByte2, k, k - 1);
        }
      }
      return;
    }
    int j = (paramInt1 + paramInt2) / 2;
    mergeSort1(paramArrayOfByte2, paramArrayOfByte1, paramInt1, j, paramByteComparator);
    mergeSort1(paramArrayOfByte2, paramArrayOfByte1, j, paramInt2, paramByteComparator);
    if (paramByteComparator.compare(paramArrayOfByte1[(j - 1)], paramArrayOfByte1[j]) <= 0)
    {
      System.arraycopy(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt1, i);
      return;
    }
    int k = paramInt1;
    int m = paramInt1;
    int n = j;
    while (k < paramInt2)
    {
      if ((n >= paramInt2) || ((m < j) && (paramByteComparator.compare(paramArrayOfByte1[m], paramArrayOfByte1[n]) <= 0))) {
        paramArrayOfByte2[k] = paramArrayOfByte1[(m++)];
      } else {
        paramArrayOfByte2[k] = paramArrayOfByte1[(n++)];
      }
      k++;
    }
  }
  
  private static void mergeSort1(char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt1, int paramInt2)
  {
    int i = paramInt2 - paramInt1;
    if (i < 7)
    {
      for (j = paramInt1; j < paramInt2; j++) {
        for (k = j; (k > paramInt1) && (paramArrayOfChar2[(k - 1)] > paramArrayOfChar2[k]); k--) {
          swap(paramArrayOfChar2, k, k - 1);
        }
      }
      return;
    }
    int j = (paramInt1 + paramInt2) / 2;
    mergeSort1(paramArrayOfChar2, paramArrayOfChar1, paramInt1, j);
    mergeSort1(paramArrayOfChar2, paramArrayOfChar1, j, paramInt2);
    if (paramArrayOfChar1[(j - 1)] <= paramArrayOfChar1[j])
    {
      System.arraycopy(paramArrayOfChar1, paramInt1, paramArrayOfChar2, paramInt1, i);
      return;
    }
    int k = paramInt1;
    int m = paramInt1;
    int n = j;
    while (k < paramInt2)
    {
      if ((n >= paramInt2) || ((m < j) && (paramArrayOfChar1[m] <= paramArrayOfChar1[n]))) {
        paramArrayOfChar2[k] = paramArrayOfChar1[(m++)];
      } else {
        paramArrayOfChar2[k] = paramArrayOfChar1[(n++)];
      }
      k++;
    }
  }
  
  private static void mergeSort1(char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt1, int paramInt2, CharComparator paramCharComparator)
  {
    int i = paramInt2 - paramInt1;
    if (i < 7)
    {
      for (j = paramInt1; j < paramInt2; j++) {
        for (k = j; (k > paramInt1) && (paramCharComparator.compare(paramArrayOfChar2[(k - 1)], paramArrayOfChar2[k]) > 0); k--) {
          swap(paramArrayOfChar2, k, k - 1);
        }
      }
      return;
    }
    int j = (paramInt1 + paramInt2) / 2;
    mergeSort1(paramArrayOfChar2, paramArrayOfChar1, paramInt1, j, paramCharComparator);
    mergeSort1(paramArrayOfChar2, paramArrayOfChar1, j, paramInt2, paramCharComparator);
    if (paramCharComparator.compare(paramArrayOfChar1[(j - 1)], paramArrayOfChar1[j]) <= 0)
    {
      System.arraycopy(paramArrayOfChar1, paramInt1, paramArrayOfChar2, paramInt1, i);
      return;
    }
    int k = paramInt1;
    int m = paramInt1;
    int n = j;
    while (k < paramInt2)
    {
      if ((n >= paramInt2) || ((m < j) && (paramCharComparator.compare(paramArrayOfChar1[m], paramArrayOfChar1[n]) <= 0))) {
        paramArrayOfChar2[k] = paramArrayOfChar1[(m++)];
      } else {
        paramArrayOfChar2[k] = paramArrayOfChar1[(n++)];
      }
      k++;
    }
  }
  
  private static void mergeSort1(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, int paramInt1, int paramInt2)
  {
    int i = paramInt2 - paramInt1;
    if (i < 7)
    {
      for (j = paramInt1; j < paramInt2; j++) {
        for (k = j; (k > paramInt1) && (paramArrayOfDouble2[(k - 1)] > paramArrayOfDouble2[k]); k--) {
          swap(paramArrayOfDouble2, k, k - 1);
        }
      }
      return;
    }
    int j = (paramInt1 + paramInt2) / 2;
    mergeSort1(paramArrayOfDouble2, paramArrayOfDouble1, paramInt1, j);
    mergeSort1(paramArrayOfDouble2, paramArrayOfDouble1, j, paramInt2);
    if (paramArrayOfDouble1[(j - 1)] <= paramArrayOfDouble1[j])
    {
      System.arraycopy(paramArrayOfDouble1, paramInt1, paramArrayOfDouble2, paramInt1, i);
      return;
    }
    int k = paramInt1;
    int m = paramInt1;
    int n = j;
    while (k < paramInt2)
    {
      if ((n >= paramInt2) || ((m < j) && (paramArrayOfDouble1[m] <= paramArrayOfDouble1[n]))) {
        paramArrayOfDouble2[k] = paramArrayOfDouble1[(m++)];
      } else {
        paramArrayOfDouble2[k] = paramArrayOfDouble1[(n++)];
      }
      k++;
    }
  }
  
  private static void mergeSort1(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, int paramInt1, int paramInt2, DoubleComparator paramDoubleComparator)
  {
    int i = paramInt2 - paramInt1;
    if (i < 7)
    {
      for (j = paramInt1; j < paramInt2; j++) {
        for (k = j; (k > paramInt1) && (paramDoubleComparator.compare(paramArrayOfDouble2[(k - 1)], paramArrayOfDouble2[k]) > 0); k--) {
          swap(paramArrayOfDouble2, k, k - 1);
        }
      }
      return;
    }
    int j = (paramInt1 + paramInt2) / 2;
    mergeSort1(paramArrayOfDouble2, paramArrayOfDouble1, paramInt1, j, paramDoubleComparator);
    mergeSort1(paramArrayOfDouble2, paramArrayOfDouble1, j, paramInt2, paramDoubleComparator);
    if (paramDoubleComparator.compare(paramArrayOfDouble1[(j - 1)], paramArrayOfDouble1[j]) <= 0)
    {
      System.arraycopy(paramArrayOfDouble1, paramInt1, paramArrayOfDouble2, paramInt1, i);
      return;
    }
    int k = paramInt1;
    int m = paramInt1;
    int n = j;
    while (k < paramInt2)
    {
      if ((n >= paramInt2) || ((m < j) && (paramDoubleComparator.compare(paramArrayOfDouble1[m], paramArrayOfDouble1[n]) <= 0))) {
        paramArrayOfDouble2[k] = paramArrayOfDouble1[(m++)];
      } else {
        paramArrayOfDouble2[k] = paramArrayOfDouble1[(n++)];
      }
      k++;
    }
  }
  
  private static void mergeSort1(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, int paramInt1, int paramInt2)
  {
    int i = paramInt2 - paramInt1;
    if (i < 7)
    {
      for (j = paramInt1; j < paramInt2; j++) {
        for (k = j; (k > paramInt1) && (paramArrayOfFloat2[(k - 1)] > paramArrayOfFloat2[k]); k--) {
          swap(paramArrayOfFloat2, k, k - 1);
        }
      }
      return;
    }
    int j = (paramInt1 + paramInt2) / 2;
    mergeSort1(paramArrayOfFloat2, paramArrayOfFloat1, paramInt1, j);
    mergeSort1(paramArrayOfFloat2, paramArrayOfFloat1, j, paramInt2);
    if (paramArrayOfFloat1[(j - 1)] <= paramArrayOfFloat1[j])
    {
      System.arraycopy(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt1, i);
      return;
    }
    int k = paramInt1;
    int m = paramInt1;
    int n = j;
    while (k < paramInt2)
    {
      if ((n >= paramInt2) || ((m < j) && (paramArrayOfFloat1[m] <= paramArrayOfFloat1[n]))) {
        paramArrayOfFloat2[k] = paramArrayOfFloat1[(m++)];
      } else {
        paramArrayOfFloat2[k] = paramArrayOfFloat1[(n++)];
      }
      k++;
    }
  }
  
  private static void mergeSort1(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, int paramInt1, int paramInt2, FloatComparator paramFloatComparator)
  {
    int i = paramInt2 - paramInt1;
    if (i < 7)
    {
      for (j = paramInt1; j < paramInt2; j++) {
        for (k = j; (k > paramInt1) && (paramFloatComparator.compare(paramArrayOfFloat2[(k - 1)], paramArrayOfFloat2[k]) > 0); k--) {
          swap(paramArrayOfFloat2, k, k - 1);
        }
      }
      return;
    }
    int j = (paramInt1 + paramInt2) / 2;
    mergeSort1(paramArrayOfFloat2, paramArrayOfFloat1, paramInt1, j, paramFloatComparator);
    mergeSort1(paramArrayOfFloat2, paramArrayOfFloat1, j, paramInt2, paramFloatComparator);
    if (paramFloatComparator.compare(paramArrayOfFloat1[(j - 1)], paramArrayOfFloat1[j]) <= 0)
    {
      System.arraycopy(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt1, i);
      return;
    }
    int k = paramInt1;
    int m = paramInt1;
    int n = j;
    while (k < paramInt2)
    {
      if ((n >= paramInt2) || ((m < j) && (paramFloatComparator.compare(paramArrayOfFloat1[m], paramArrayOfFloat1[n]) <= 0))) {
        paramArrayOfFloat2[k] = paramArrayOfFloat1[(m++)];
      } else {
        paramArrayOfFloat2[k] = paramArrayOfFloat1[(n++)];
      }
      k++;
    }
  }
  
  private static void mergeSort1(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2)
  {
    int i = paramInt2 - paramInt1;
    if (i < 7)
    {
      for (j = paramInt1; j < paramInt2; j++) {
        for (k = j; (k > paramInt1) && (paramArrayOfInt2[(k - 1)] > paramArrayOfInt2[k]); k--) {
          swap(paramArrayOfInt2, k, k - 1);
        }
      }
      return;
    }
    int j = (paramInt1 + paramInt2) / 2;
    mergeSort1(paramArrayOfInt2, paramArrayOfInt1, paramInt1, j);
    mergeSort1(paramArrayOfInt2, paramArrayOfInt1, j, paramInt2);
    if (paramArrayOfInt1[(j - 1)] <= paramArrayOfInt1[j])
    {
      System.arraycopy(paramArrayOfInt1, paramInt1, paramArrayOfInt2, paramInt1, i);
      return;
    }
    int k = paramInt1;
    int m = paramInt1;
    int n = j;
    while (k < paramInt2)
    {
      if ((n >= paramInt2) || ((m < j) && (paramArrayOfInt1[m] <= paramArrayOfInt1[n]))) {
        paramArrayOfInt2[k] = paramArrayOfInt1[(m++)];
      } else {
        paramArrayOfInt2[k] = paramArrayOfInt1[(n++)];
      }
      k++;
    }
  }
  
  private static void mergeSort1(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, IntComparator paramIntComparator)
  {
    int i = paramInt2 - paramInt1;
    if (i < 7)
    {
      for (j = paramInt1; j < paramInt2; j++) {
        for (k = j; (k > paramInt1) && (paramIntComparator.compare(paramArrayOfInt2[(k - 1)], paramArrayOfInt2[k]) > 0); k--) {
          swap(paramArrayOfInt2, k, k - 1);
        }
      }
      return;
    }
    int j = (paramInt1 + paramInt2) / 2;
    mergeSort1(paramArrayOfInt2, paramArrayOfInt1, paramInt1, j, paramIntComparator);
    mergeSort1(paramArrayOfInt2, paramArrayOfInt1, j, paramInt2, paramIntComparator);
    if (paramIntComparator.compare(paramArrayOfInt1[(j - 1)], paramArrayOfInt1[j]) <= 0)
    {
      System.arraycopy(paramArrayOfInt1, paramInt1, paramArrayOfInt2, paramInt1, i);
      return;
    }
    int k = paramInt1;
    int m = paramInt1;
    int n = j;
    while (k < paramInt2)
    {
      if ((n >= paramInt2) || ((m < j) && (paramIntComparator.compare(paramArrayOfInt1[m], paramArrayOfInt1[n]) <= 0))) {
        paramArrayOfInt2[k] = paramArrayOfInt1[(m++)];
      } else {
        paramArrayOfInt2[k] = paramArrayOfInt1[(n++)];
      }
      k++;
    }
  }
  
  private static void mergeSort1(long[] paramArrayOfLong1, long[] paramArrayOfLong2, int paramInt1, int paramInt2)
  {
    int i = paramInt2 - paramInt1;
    if (i < 7)
    {
      for (j = paramInt1; j < paramInt2; j++) {
        for (k = j; (k > paramInt1) && (paramArrayOfLong2[(k - 1)] > paramArrayOfLong2[k]); k--) {
          swap(paramArrayOfLong2, k, k - 1);
        }
      }
      return;
    }
    int j = (paramInt1 + paramInt2) / 2;
    mergeSort1(paramArrayOfLong2, paramArrayOfLong1, paramInt1, j);
    mergeSort1(paramArrayOfLong2, paramArrayOfLong1, j, paramInt2);
    if (paramArrayOfLong1[(j - 1)] <= paramArrayOfLong1[j])
    {
      System.arraycopy(paramArrayOfLong1, paramInt1, paramArrayOfLong2, paramInt1, i);
      return;
    }
    int k = paramInt1;
    int m = paramInt1;
    int n = j;
    while (k < paramInt2)
    {
      if ((n >= paramInt2) || ((m < j) && (paramArrayOfLong1[m] <= paramArrayOfLong1[n]))) {
        paramArrayOfLong2[k] = paramArrayOfLong1[(m++)];
      } else {
        paramArrayOfLong2[k] = paramArrayOfLong1[(n++)];
      }
      k++;
    }
  }
  
  private static void mergeSort1(long[] paramArrayOfLong1, long[] paramArrayOfLong2, int paramInt1, int paramInt2, LongComparator paramLongComparator)
  {
    int i = paramInt2 - paramInt1;
    if (i < 7)
    {
      for (j = paramInt1; j < paramInt2; j++) {
        for (k = j; (k > paramInt1) && (paramLongComparator.compare(paramArrayOfLong2[(k - 1)], paramArrayOfLong2[k]) > 0); k--) {
          swap(paramArrayOfLong2, k, k - 1);
        }
      }
      return;
    }
    int j = (paramInt1 + paramInt2) / 2;
    mergeSort1(paramArrayOfLong2, paramArrayOfLong1, paramInt1, j, paramLongComparator);
    mergeSort1(paramArrayOfLong2, paramArrayOfLong1, j, paramInt2, paramLongComparator);
    if (paramLongComparator.compare(paramArrayOfLong1[(j - 1)], paramArrayOfLong1[j]) <= 0)
    {
      System.arraycopy(paramArrayOfLong1, paramInt1, paramArrayOfLong2, paramInt1, i);
      return;
    }
    int k = paramInt1;
    int m = paramInt1;
    int n = j;
    while (k < paramInt2)
    {
      if ((n >= paramInt2) || ((m < j) && (paramLongComparator.compare(paramArrayOfLong1[m], paramArrayOfLong1[n]) <= 0))) {
        paramArrayOfLong2[k] = paramArrayOfLong1[(m++)];
      } else {
        paramArrayOfLong2[k] = paramArrayOfLong1[(n++)];
      }
      k++;
    }
  }
  
  private static void mergeSort1(short[] paramArrayOfShort1, short[] paramArrayOfShort2, int paramInt1, int paramInt2)
  {
    int i = paramInt2 - paramInt1;
    if (i < 7)
    {
      for (j = paramInt1; j < paramInt2; j++) {
        for (k = j; (k > paramInt1) && (paramArrayOfShort2[(k - 1)] > paramArrayOfShort2[k]); k--) {
          swap(paramArrayOfShort2, k, k - 1);
        }
      }
      return;
    }
    int j = (paramInt1 + paramInt2) / 2;
    mergeSort1(paramArrayOfShort2, paramArrayOfShort1, paramInt1, j);
    mergeSort1(paramArrayOfShort2, paramArrayOfShort1, j, paramInt2);
    if (paramArrayOfShort1[(j - 1)] <= paramArrayOfShort1[j])
    {
      System.arraycopy(paramArrayOfShort1, paramInt1, paramArrayOfShort2, paramInt1, i);
      return;
    }
    int k = paramInt1;
    int m = paramInt1;
    int n = j;
    while (k < paramInt2)
    {
      if ((n >= paramInt2) || ((m < j) && (paramArrayOfShort1[m] <= paramArrayOfShort1[n]))) {
        paramArrayOfShort2[k] = paramArrayOfShort1[(m++)];
      } else {
        paramArrayOfShort2[k] = paramArrayOfShort1[(n++)];
      }
      k++;
    }
  }
  
  private static void mergeSort1(short[] paramArrayOfShort1, short[] paramArrayOfShort2, int paramInt1, int paramInt2, ShortComparator paramShortComparator)
  {
    int i = paramInt2 - paramInt1;
    if (i < 7)
    {
      for (j = paramInt1; j < paramInt2; j++) {
        for (k = j; (k > paramInt1) && (paramShortComparator.compare(paramArrayOfShort2[(k - 1)], paramArrayOfShort2[k]) > 0); k--) {
          swap(paramArrayOfShort2, k, k - 1);
        }
      }
      return;
    }
    int j = (paramInt1 + paramInt2) / 2;
    mergeSort1(paramArrayOfShort2, paramArrayOfShort1, paramInt1, j, paramShortComparator);
    mergeSort1(paramArrayOfShort2, paramArrayOfShort1, j, paramInt2, paramShortComparator);
    if (paramShortComparator.compare(paramArrayOfShort1[(j - 1)], paramArrayOfShort1[j]) <= 0)
    {
      System.arraycopy(paramArrayOfShort1, paramInt1, paramArrayOfShort2, paramInt1, i);
      return;
    }
    int k = paramInt1;
    int m = paramInt1;
    int n = j;
    while (k < paramInt2)
    {
      if ((n >= paramInt2) || ((m < j) && (paramShortComparator.compare(paramArrayOfShort1[m], paramArrayOfShort1[n]) <= 0))) {
        paramArrayOfShort2[k] = paramArrayOfShort1[(m++)];
      } else {
        paramArrayOfShort2[k] = paramArrayOfShort1[(n++)];
      }
      k++;
    }
  }
  
  private static void mergeSort2(double[] paramArrayOfDouble, int paramInt1, int paramInt2)
  {
    rangeCheck(paramArrayOfDouble.length, paramInt1, paramInt2);
    long l = Double.doubleToLongBits(-0.0D);
    int i = 0;
    int j = paramInt1;
    int k = paramInt2;
    while (j < k) {
      if (paramArrayOfDouble[j] != paramArrayOfDouble[j])
      {
        paramArrayOfDouble[j] = paramArrayOfDouble[(--k)];
        paramArrayOfDouble[k] = (0.0D / 0.0D);
      }
      else
      {
        if ((paramArrayOfDouble[j] == 0.0D) && (Double.doubleToLongBits(paramArrayOfDouble[j]) == l))
        {
          paramArrayOfDouble[j] = 0.0D;
          i++;
        }
        j++;
      }
    }
    double[] arrayOfDouble = (double[])paramArrayOfDouble.clone();
    mergeSort1(arrayOfDouble, paramArrayOfDouble, paramInt1, k);
    if (i != 0)
    {
      int m = new DoubleArrayList(paramArrayOfDouble).binarySearchFromTo(0.0D, paramInt1, k - 1);
      do
      {
        m--;
      } while ((m >= 0) && (paramArrayOfDouble[m] == 0.0D));
      for (int n = 0; n < i; n++) {
        paramArrayOfDouble[(++m)] = -0.0D;
      }
    }
  }
  
  private static void mergeSort2(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
  {
    rangeCheck(paramArrayOfFloat.length, paramInt1, paramInt2);
    int i = Float.floatToIntBits(-0.0F);
    int j = 0;
    int k = paramInt1;
    int m = paramInt2;
    while (k < m) {
      if (paramArrayOfFloat[k] != paramArrayOfFloat[k])
      {
        paramArrayOfFloat[k] = paramArrayOfFloat[(--m)];
        paramArrayOfFloat[m] = (0.0F / 0.0F);
      }
      else
      {
        if ((paramArrayOfFloat[k] == 0.0F) && (Float.floatToIntBits(paramArrayOfFloat[k]) == i))
        {
          paramArrayOfFloat[k] = 0.0F;
          j++;
        }
        k++;
      }
    }
    float[] arrayOfFloat = (float[])paramArrayOfFloat.clone();
    mergeSort1(arrayOfFloat, paramArrayOfFloat, paramInt1, m);
    if (j != 0)
    {
      int n = new FloatArrayList(paramArrayOfFloat).binarySearchFromTo(0.0F, paramInt1, m - 1);
      do
      {
        n--;
      } while ((n >= 0) && (paramArrayOfFloat[n] == 0.0F));
      for (int i1 = 0; i1 < j; i1++) {
        paramArrayOfFloat[(++n)] = -0.0F;
      }
    }
  }
  
  public static void mergeSortInPlace(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    rangeCheck(paramArrayOfInt.length, paramInt1, paramInt2);
    int i = paramInt2 - paramInt1;
    if (i < 7)
    {
      for (j = paramInt1; j < paramInt2; j++) {
        for (int k = j; (k > paramInt1) && (paramArrayOfInt[(k - 1)] > paramArrayOfInt[k]); k--)
        {
          int m = paramArrayOfInt[k];
          paramArrayOfInt[k] = paramArrayOfInt[(k - 1)];
          paramArrayOfInt[(k - 1)] = m;
        }
      }
      return;
    }
    int j = (paramInt1 + paramInt2) / 2;
    mergeSortInPlace(paramArrayOfInt, paramInt1, j);
    mergeSortInPlace(paramArrayOfInt, j, paramInt2);
    if (paramArrayOfInt[(j - 1)] <= paramArrayOfInt[j]) {
      return;
    }
    inplace_merge(paramArrayOfInt, paramInt1, j, paramInt2);
  }
  
  public static void quickSort(byte[] paramArrayOfByte, int paramInt1, int paramInt2, ByteComparator paramByteComparator)
  {
    rangeCheck(paramArrayOfByte.length, paramInt1, paramInt2);
    quickSort1(paramArrayOfByte, paramInt1, paramInt2 - paramInt1, paramByteComparator);
  }
  
  public static void quickSort(char[] paramArrayOfChar, int paramInt1, int paramInt2, CharComparator paramCharComparator)
  {
    rangeCheck(paramArrayOfChar.length, paramInt1, paramInt2);
    quickSort1(paramArrayOfChar, paramInt1, paramInt2 - paramInt1, paramCharComparator);
  }
  
  public static void quickSort(double[] paramArrayOfDouble, int paramInt1, int paramInt2, DoubleComparator paramDoubleComparator)
  {
    rangeCheck(paramArrayOfDouble.length, paramInt1, paramInt2);
    quickSort1(paramArrayOfDouble, paramInt1, paramInt2 - paramInt1, paramDoubleComparator);
  }
  
  public static void quickSort(float[] paramArrayOfFloat, int paramInt1, int paramInt2, FloatComparator paramFloatComparator)
  {
    rangeCheck(paramArrayOfFloat.length, paramInt1, paramInt2);
    quickSort1(paramArrayOfFloat, paramInt1, paramInt2 - paramInt1, paramFloatComparator);
  }
  
  public static void quickSort(int[] paramArrayOfInt, int paramInt1, int paramInt2, IntComparator paramIntComparator)
  {
    rangeCheck(paramArrayOfInt.length, paramInt1, paramInt2);
    quickSort1(paramArrayOfInt, paramInt1, paramInt2 - paramInt1, paramIntComparator);
  }
  
  public static void quickSort(long[] paramArrayOfLong, int paramInt1, int paramInt2, LongComparator paramLongComparator)
  {
    rangeCheck(paramArrayOfLong.length, paramInt1, paramInt2);
    quickSort1(paramArrayOfLong, paramInt1, paramInt2 - paramInt1, paramLongComparator);
  }
  
  public static void quickSort(Object[] paramArrayOfObject)
  {
    quickSort1(paramArrayOfObject, 0, paramArrayOfObject.length);
  }
  
  public static void quickSort(Object[] paramArrayOfObject, int paramInt1, int paramInt2)
  {
    rangeCheck(paramArrayOfObject.length, paramInt1, paramInt2);
    quickSort1(paramArrayOfObject, paramInt1, paramInt2 - paramInt1);
  }
  
  public static void quickSort(Object[] paramArrayOfObject, int paramInt1, int paramInt2, Comparator paramComparator)
  {
    rangeCheck(paramArrayOfObject.length, paramInt1, paramInt2);
    quickSort1(paramArrayOfObject, paramInt1, paramInt2 - paramInt1, paramComparator);
  }
  
  public static void quickSort(Object[] paramArrayOfObject, Comparator paramComparator)
  {
    quickSort1(paramArrayOfObject, 0, paramArrayOfObject.length, paramComparator);
  }
  
  public static void quickSort(short[] paramArrayOfShort, int paramInt1, int paramInt2, ShortComparator paramShortComparator)
  {
    rangeCheck(paramArrayOfShort.length, paramInt1, paramInt2);
    quickSort1(paramArrayOfShort, paramInt1, paramInt2 - paramInt1, paramShortComparator);
  }
  
  private static void quickSort1(byte[] paramArrayOfByte, int paramInt1, int paramInt2, ByteComparator paramByteComparator)
  {
    if (paramInt2 < 7)
    {
      for (i = paramInt1; i < paramInt2 + paramInt1; i++) {
        for (j = i; (j > paramInt1) && (paramByteComparator.compare(paramArrayOfByte[(j - 1)], paramArrayOfByte[j]) > 0); j--) {
          swap(paramArrayOfByte, j, j - 1);
        }
      }
      return;
    }
    int i = paramInt1 + paramInt2 / 2;
    if (paramInt2 > 7)
    {
      j = paramInt1;
      k = paramInt1 + paramInt2 - 1;
      if (paramInt2 > 40)
      {
        m = paramInt2 / 8;
        j = med3(paramArrayOfByte, j, j + m, j + 2 * m, paramByteComparator);
        i = med3(paramArrayOfByte, i - m, i, i + m, paramByteComparator);
        k = med3(paramArrayOfByte, k - 2 * m, k - m, k, paramByteComparator);
      }
      i = med3(paramArrayOfByte, j, i, k, paramByteComparator);
    }
    int j = paramArrayOfByte[i];
    int k = paramInt1;
    int m = k;
    int n = paramInt1 + paramInt2 - 1;
    int i1 = n;
    for (;;)
    {
      if ((m <= n) && ((i2 = paramByteComparator.compare(paramArrayOfByte[m], j)) <= 0))
      {
        if (i2 == 0) {
          swap(paramArrayOfByte, k++, m);
        }
        m++;
      }
      else
      {
        while ((n >= m) && ((i2 = paramByteComparator.compare(paramArrayOfByte[n], j)) >= 0))
        {
          if (i2 == 0) {
            swap(paramArrayOfByte, n, i1--);
          }
          n--;
        }
        if (m > n) {
          break;
        }
        swap(paramArrayOfByte, m++, n--);
      }
    }
    int i3 = paramInt1 + paramInt2;
    int i2 = Math.min(k - paramInt1, m - k);
    vecswap(paramArrayOfByte, paramInt1, m - i2, i2);
    i2 = Math.min(i1 - n, i3 - i1 - 1);
    vecswap(paramArrayOfByte, m, i3 - i2, i2);
    if ((i2 = m - k) > 1) {
      quickSort1(paramArrayOfByte, paramInt1, i2, paramByteComparator);
    }
    if ((i2 = i1 - n) > 1) {
      quickSort1(paramArrayOfByte, i3 - i2, i2, paramByteComparator);
    }
  }
  
  private static void quickSort1(char[] paramArrayOfChar, int paramInt1, int paramInt2, CharComparator paramCharComparator)
  {
    if (paramInt2 < 7)
    {
      for (i = paramInt1; i < paramInt2 + paramInt1; i++) {
        for (j = i; (j > paramInt1) && (paramCharComparator.compare(paramArrayOfChar[(j - 1)], paramArrayOfChar[j]) > 0); j--) {
          swap(paramArrayOfChar, j, j - 1);
        }
      }
      return;
    }
    int i = paramInt1 + paramInt2 / 2;
    if (paramInt2 > 7)
    {
      j = paramInt1;
      k = paramInt1 + paramInt2 - 1;
      if (paramInt2 > 40)
      {
        m = paramInt2 / 8;
        j = med3(paramArrayOfChar, j, j + m, j + 2 * m, paramCharComparator);
        i = med3(paramArrayOfChar, i - m, i, i + m, paramCharComparator);
        k = med3(paramArrayOfChar, k - 2 * m, k - m, k, paramCharComparator);
      }
      i = med3(paramArrayOfChar, j, i, k, paramCharComparator);
    }
    int j = paramArrayOfChar[i];
    int k = paramInt1;
    int m = k;
    int n = paramInt1 + paramInt2 - 1;
    int i1 = n;
    for (;;)
    {
      if ((m <= n) && ((i2 = paramCharComparator.compare(paramArrayOfChar[m], j)) <= 0))
      {
        if (i2 == 0) {
          swap(paramArrayOfChar, k++, m);
        }
        m++;
      }
      else
      {
        while ((n >= m) && ((i2 = paramCharComparator.compare(paramArrayOfChar[n], j)) >= 0))
        {
          if (i2 == 0) {
            swap(paramArrayOfChar, n, i1--);
          }
          n--;
        }
        if (m > n) {
          break;
        }
        swap(paramArrayOfChar, m++, n--);
      }
    }
    int i3 = paramInt1 + paramInt2;
    int i2 = Math.min(k - paramInt1, m - k);
    vecswap(paramArrayOfChar, paramInt1, m - i2, i2);
    i2 = Math.min(i1 - n, i3 - i1 - 1);
    vecswap(paramArrayOfChar, m, i3 - i2, i2);
    if ((i2 = m - k) > 1) {
      quickSort1(paramArrayOfChar, paramInt1, i2, paramCharComparator);
    }
    if ((i2 = i1 - n) > 1) {
      quickSort1(paramArrayOfChar, i3 - i2, i2, paramCharComparator);
    }
  }
  
  private static void quickSort1(double[] paramArrayOfDouble, int paramInt1, int paramInt2, DoubleComparator paramDoubleComparator)
  {
    int j;
    if (paramInt2 < 7)
    {
      for (i = paramInt1; i < paramInt2 + paramInt1; i++) {
        for (j = i; (j > paramInt1) && (paramDoubleComparator.compare(paramArrayOfDouble[(j - 1)], paramArrayOfDouble[j]) > 0); j--) {
          swap(paramArrayOfDouble, j, j - 1);
        }
      }
      return;
    }
    int i = paramInt1 + paramInt2 / 2;
    if (paramInt2 > 7)
    {
      j = paramInt1;
      int k = paramInt1 + paramInt2 - 1;
      if (paramInt2 > 40)
      {
        m = paramInt2 / 8;
        j = med3(paramArrayOfDouble, j, j + m, j + 2 * m, paramDoubleComparator);
        i = med3(paramArrayOfDouble, i - m, i, i + m, paramDoubleComparator);
        k = med3(paramArrayOfDouble, k - 2 * m, k - m, k, paramDoubleComparator);
      }
      i = med3(paramArrayOfDouble, j, i, k, paramDoubleComparator);
    }
    double d = paramArrayOfDouble[i];
    int m = paramInt1;
    int n = m;
    int i1 = paramInt1 + paramInt2 - 1;
    int i2 = i1;
    for (;;)
    {
      if ((n <= i1) && ((i3 = paramDoubleComparator.compare(paramArrayOfDouble[n], d)) <= 0))
      {
        if (i3 == 0) {
          swap(paramArrayOfDouble, m++, n);
        }
        n++;
      }
      else
      {
        while ((i1 >= n) && ((i3 = paramDoubleComparator.compare(paramArrayOfDouble[i1], d)) >= 0))
        {
          if (i3 == 0) {
            swap(paramArrayOfDouble, i1, i2--);
          }
          i1--;
        }
        if (n > i1) {
          break;
        }
        swap(paramArrayOfDouble, n++, i1--);
      }
    }
    int i4 = paramInt1 + paramInt2;
    int i3 = Math.min(m - paramInt1, n - m);
    vecswap(paramArrayOfDouble, paramInt1, n - i3, i3);
    i3 = Math.min(i2 - i1, i4 - i2 - 1);
    vecswap(paramArrayOfDouble, n, i4 - i3, i3);
    if ((i3 = n - m) > 1) {
      quickSort1(paramArrayOfDouble, paramInt1, i3, paramDoubleComparator);
    }
    if ((i3 = i2 - i1) > 1) {
      quickSort1(paramArrayOfDouble, i4 - i3, i3, paramDoubleComparator);
    }
  }
  
  private static void quickSort1(float[] paramArrayOfFloat, int paramInt1, int paramInt2, FloatComparator paramFloatComparator)
  {
    int j;
    if (paramInt2 < 7)
    {
      for (i = paramInt1; i < paramInt2 + paramInt1; i++) {
        for (j = i; (j > paramInt1) && (paramFloatComparator.compare(paramArrayOfFloat[(j - 1)], paramArrayOfFloat[j]) > 0); j--) {
          swap(paramArrayOfFloat, j, j - 1);
        }
      }
      return;
    }
    int i = paramInt1 + paramInt2 / 2;
    if (paramInt2 > 7)
    {
      j = paramInt1;
      k = paramInt1 + paramInt2 - 1;
      if (paramInt2 > 40)
      {
        m = paramInt2 / 8;
        j = med3(paramArrayOfFloat, j, j + m, j + 2 * m, paramFloatComparator);
        i = med3(paramArrayOfFloat, i - m, i, i + m, paramFloatComparator);
        k = med3(paramArrayOfFloat, k - 2 * m, k - m, k, paramFloatComparator);
      }
      i = med3(paramArrayOfFloat, j, i, k, paramFloatComparator);
    }
    float f = paramArrayOfFloat[i];
    int k = paramInt1;
    int m = k;
    int n = paramInt1 + paramInt2 - 1;
    int i1 = n;
    for (;;)
    {
      if ((m <= n) && ((i2 = paramFloatComparator.compare(paramArrayOfFloat[m], f)) <= 0))
      {
        if (i2 == 0) {
          swap(paramArrayOfFloat, k++, m);
        }
        m++;
      }
      else
      {
        while ((n >= m) && ((i2 = paramFloatComparator.compare(paramArrayOfFloat[n], f)) >= 0))
        {
          if (i2 == 0) {
            swap(paramArrayOfFloat, n, i1--);
          }
          n--;
        }
        if (m > n) {
          break;
        }
        swap(paramArrayOfFloat, m++, n--);
      }
    }
    int i3 = paramInt1 + paramInt2;
    int i2 = Math.min(k - paramInt1, m - k);
    vecswap(paramArrayOfFloat, paramInt1, m - i2, i2);
    i2 = Math.min(i1 - n, i3 - i1 - 1);
    vecswap(paramArrayOfFloat, m, i3 - i2, i2);
    if ((i2 = m - k) > 1) {
      quickSort1(paramArrayOfFloat, paramInt1, i2, paramFloatComparator);
    }
    if ((i2 = i1 - n) > 1) {
      quickSort1(paramArrayOfFloat, i3 - i2, i2, paramFloatComparator);
    }
  }
  
  private static void quickSort1(int[] paramArrayOfInt, int paramInt1, int paramInt2, IntComparator paramIntComparator)
  {
    if (paramInt2 < 7)
    {
      for (i = paramInt1; i < paramInt2 + paramInt1; i++) {
        for (j = i; (j > paramInt1) && (paramIntComparator.compare(paramArrayOfInt[(j - 1)], paramArrayOfInt[j]) > 0); j--) {
          swap(paramArrayOfInt, j, j - 1);
        }
      }
      return;
    }
    int i = paramInt1 + paramInt2 / 2;
    if (paramInt2 > 7)
    {
      j = paramInt1;
      k = paramInt1 + paramInt2 - 1;
      if (paramInt2 > 40)
      {
        m = paramInt2 / 8;
        j = med3(paramArrayOfInt, j, j + m, j + 2 * m, paramIntComparator);
        i = med3(paramArrayOfInt, i - m, i, i + m, paramIntComparator);
        k = med3(paramArrayOfInt, k - 2 * m, k - m, k, paramIntComparator);
      }
      i = med3(paramArrayOfInt, j, i, k, paramIntComparator);
    }
    int j = paramArrayOfInt[i];
    int k = paramInt1;
    int m = k;
    int n = paramInt1 + paramInt2 - 1;
    int i1 = n;
    for (;;)
    {
      if ((m <= n) && ((i2 = paramIntComparator.compare(paramArrayOfInt[m], j)) <= 0))
      {
        if (i2 == 0) {
          swap(paramArrayOfInt, k++, m);
        }
        m++;
      }
      else
      {
        while ((n >= m) && ((i2 = paramIntComparator.compare(paramArrayOfInt[n], j)) >= 0))
        {
          if (i2 == 0) {
            swap(paramArrayOfInt, n, i1--);
          }
          n--;
        }
        if (m > n) {
          break;
        }
        swap(paramArrayOfInt, m++, n--);
      }
    }
    int i3 = paramInt1 + paramInt2;
    int i2 = Math.min(k - paramInt1, m - k);
    vecswap(paramArrayOfInt, paramInt1, m - i2, i2);
    i2 = Math.min(i1 - n, i3 - i1 - 1);
    vecswap(paramArrayOfInt, m, i3 - i2, i2);
    if ((i2 = m - k) > 1) {
      quickSort1(paramArrayOfInt, paramInt1, i2, paramIntComparator);
    }
    if ((i2 = i1 - n) > 1) {
      quickSort1(paramArrayOfInt, i3 - i2, i2, paramIntComparator);
    }
  }
  
  private static void quickSort1(long[] paramArrayOfLong, int paramInt1, int paramInt2, LongComparator paramLongComparator)
  {
    int j;
    if (paramInt2 < 7)
    {
      for (i = paramInt1; i < paramInt2 + paramInt1; i++) {
        for (j = i; (j > paramInt1) && (paramLongComparator.compare(paramArrayOfLong[(j - 1)], paramArrayOfLong[j]) > 0); j--) {
          swap(paramArrayOfLong, j, j - 1);
        }
      }
      return;
    }
    int i = paramInt1 + paramInt2 / 2;
    if (paramInt2 > 7)
    {
      j = paramInt1;
      int k = paramInt1 + paramInt2 - 1;
      if (paramInt2 > 40)
      {
        m = paramInt2 / 8;
        j = med3(paramArrayOfLong, j, j + m, j + 2 * m, paramLongComparator);
        i = med3(paramArrayOfLong, i - m, i, i + m, paramLongComparator);
        k = med3(paramArrayOfLong, k - 2 * m, k - m, k, paramLongComparator);
      }
      i = med3(paramArrayOfLong, j, i, k, paramLongComparator);
    }
    long l = paramArrayOfLong[i];
    int m = paramInt1;
    int n = m;
    int i1 = paramInt1 + paramInt2 - 1;
    int i2 = i1;
    for (;;)
    {
      if ((n <= i1) && ((i3 = paramLongComparator.compare(paramArrayOfLong[n], l)) <= 0))
      {
        if (i3 == 0) {
          swap(paramArrayOfLong, m++, n);
        }
        n++;
      }
      else
      {
        while ((i1 >= n) && ((i3 = paramLongComparator.compare(paramArrayOfLong[i1], l)) >= 0))
        {
          if (i3 == 0) {
            swap(paramArrayOfLong, i1, i2--);
          }
          i1--;
        }
        if (n > i1) {
          break;
        }
        swap(paramArrayOfLong, n++, i1--);
      }
    }
    int i4 = paramInt1 + paramInt2;
    int i3 = Math.min(m - paramInt1, n - m);
    vecswap(paramArrayOfLong, paramInt1, n - i3, i3);
    i3 = Math.min(i2 - i1, i4 - i2 - 1);
    vecswap(paramArrayOfLong, n, i4 - i3, i3);
    if ((i3 = n - m) > 1) {
      quickSort1(paramArrayOfLong, paramInt1, i3, paramLongComparator);
    }
    if ((i3 = i2 - i1) > 1) {
      quickSort1(paramArrayOfLong, i4 - i3, i3, paramLongComparator);
    }
  }
  
  private static void quickSort1(Object[] paramArrayOfObject, int paramInt1, int paramInt2)
  {
    int j;
    if (paramInt2 < 7)
    {
      for (i = paramInt1; i < paramInt2 + paramInt1; i++) {
        for (j = i; (j > paramInt1) && (((Comparable)paramArrayOfObject[(j - 1)]).compareTo((Comparable)paramArrayOfObject[j]) > 0); j--) {
          swap(paramArrayOfObject, j, j - 1);
        }
      }
      return;
    }
    int i = paramInt1 + paramInt2 / 2;
    if (paramInt2 > 7)
    {
      j = paramInt1;
      k = paramInt1 + paramInt2 - 1;
      if (paramInt2 > 40)
      {
        m = paramInt2 / 8;
        j = med3(paramArrayOfObject, j, j + m, j + 2 * m);
        i = med3(paramArrayOfObject, i - m, i, i + m);
        k = med3(paramArrayOfObject, k - 2 * m, k - m, k);
      }
      i = med3(paramArrayOfObject, j, i, k);
    }
    Comparable localComparable = (Comparable)paramArrayOfObject[i];
    int k = paramInt1;
    int m = k;
    int n = paramInt1 + paramInt2 - 1;
    int i1 = n;
    for (;;)
    {
      if ((m <= n) && ((i2 = ((Comparable)paramArrayOfObject[m]).compareTo(localComparable)) <= 0))
      {
        if (i2 == 0) {
          swap(paramArrayOfObject, k++, m);
        }
        m++;
      }
      else
      {
        while ((n >= m) && ((i2 = ((Comparable)paramArrayOfObject[n]).compareTo(localComparable)) >= 0))
        {
          if (i2 == 0) {
            swap(paramArrayOfObject, n, i1--);
          }
          n--;
        }
        if (m > n) {
          break;
        }
        swap(paramArrayOfObject, m++, n--);
      }
    }
    int i3 = paramInt1 + paramInt2;
    int i2 = Math.min(k - paramInt1, m - k);
    vecswap(paramArrayOfObject, paramInt1, m - i2, i2);
    i2 = Math.min(i1 - n, i3 - i1 - 1);
    vecswap(paramArrayOfObject, m, i3 - i2, i2);
    if ((i2 = m - k) > 1) {
      quickSort1(paramArrayOfObject, paramInt1, i2);
    }
    if ((i2 = i1 - n) > 1) {
      quickSort1(paramArrayOfObject, i3 - i2, i2);
    }
  }
  
  private static void quickSort1(Object[] paramArrayOfObject, int paramInt1, int paramInt2, Comparator paramComparator)
  {
    int j;
    if (paramInt2 < 7)
    {
      for (i = paramInt1; i < paramInt2 + paramInt1; i++) {
        for (j = i; (j > paramInt1) && (paramComparator.compare(paramArrayOfObject[(j - 1)], paramArrayOfObject[j]) > 0); j--) {
          swap(paramArrayOfObject, j, j - 1);
        }
      }
      return;
    }
    int i = paramInt1 + paramInt2 / 2;
    if (paramInt2 > 7)
    {
      j = paramInt1;
      k = paramInt1 + paramInt2 - 1;
      if (paramInt2 > 40)
      {
        m = paramInt2 / 8;
        j = med3(paramArrayOfObject, j, j + m, j + 2 * m, paramComparator);
        i = med3(paramArrayOfObject, i - m, i, i + m, paramComparator);
        k = med3(paramArrayOfObject, k - 2 * m, k - m, k, paramComparator);
      }
      i = med3(paramArrayOfObject, j, i, k, paramComparator);
    }
    Object localObject = paramArrayOfObject[i];
    int k = paramInt1;
    int m = k;
    int n = paramInt1 + paramInt2 - 1;
    int i1 = n;
    for (;;)
    {
      if ((m <= n) && ((i2 = paramComparator.compare(paramArrayOfObject[m], localObject)) <= 0))
      {
        if (i2 == 0) {
          swap(paramArrayOfObject, k++, m);
        }
        m++;
      }
      else
      {
        while ((n >= m) && ((i2 = paramComparator.compare(paramArrayOfObject[n], localObject)) >= 0))
        {
          if (i2 == 0) {
            swap(paramArrayOfObject, n, i1--);
          }
          n--;
        }
        if (m > n) {
          break;
        }
        swap(paramArrayOfObject, m++, n--);
      }
    }
    int i3 = paramInt1 + paramInt2;
    int i2 = Math.min(k - paramInt1, m - k);
    vecswap(paramArrayOfObject, paramInt1, m - i2, i2);
    i2 = Math.min(i1 - n, i3 - i1 - 1);
    vecswap(paramArrayOfObject, m, i3 - i2, i2);
    if ((i2 = m - k) > 1) {
      quickSort1(paramArrayOfObject, paramInt1, i2, paramComparator);
    }
    if ((i2 = i1 - n) > 1) {
      quickSort1(paramArrayOfObject, i3 - i2, i2, paramComparator);
    }
  }
  
  private static void quickSort1(short[] paramArrayOfShort, int paramInt1, int paramInt2, ShortComparator paramShortComparator)
  {
    if (paramInt2 < 7)
    {
      for (i = paramInt1; i < paramInt2 + paramInt1; i++) {
        for (j = i; (j > paramInt1) && (paramShortComparator.compare(paramArrayOfShort[(j - 1)], paramArrayOfShort[j]) > 0); j--) {
          swap(paramArrayOfShort, j, j - 1);
        }
      }
      return;
    }
    int i = paramInt1 + paramInt2 / 2;
    if (paramInt2 > 7)
    {
      j = paramInt1;
      k = paramInt1 + paramInt2 - 1;
      if (paramInt2 > 40)
      {
        m = paramInt2 / 8;
        j = med3(paramArrayOfShort, j, j + m, j + 2 * m, paramShortComparator);
        i = med3(paramArrayOfShort, i - m, i, i + m, paramShortComparator);
        k = med3(paramArrayOfShort, k - 2 * m, k - m, k, paramShortComparator);
      }
      i = med3(paramArrayOfShort, j, i, k, paramShortComparator);
    }
    int j = paramArrayOfShort[i];
    int k = paramInt1;
    int m = k;
    int n = paramInt1 + paramInt2 - 1;
    int i1 = n;
    for (;;)
    {
      if ((m <= n) && ((i2 = paramShortComparator.compare(paramArrayOfShort[m], j)) <= 0))
      {
        if (i2 == 0) {
          swap(paramArrayOfShort, k++, m);
        }
        m++;
      }
      else
      {
        while ((n >= m) && ((i2 = paramShortComparator.compare(paramArrayOfShort[n], j)) >= 0))
        {
          if (i2 == 0) {
            swap(paramArrayOfShort, n, i1--);
          }
          n--;
        }
        if (m > n) {
          break;
        }
        swap(paramArrayOfShort, m++, n--);
      }
    }
    int i3 = paramInt1 + paramInt2;
    int i2 = Math.min(k - paramInt1, m - k);
    vecswap(paramArrayOfShort, paramInt1, m - i2, i2);
    i2 = Math.min(i1 - n, i3 - i1 - 1);
    vecswap(paramArrayOfShort, m, i3 - i2, i2);
    if ((i2 = m - k) > 1) {
      quickSort1(paramArrayOfShort, paramInt1, i2, paramShortComparator);
    }
    if ((i2 = i1 - n) > 1) {
      quickSort1(paramArrayOfShort, i3 - i2, i2, paramShortComparator);
    }
  }
  
  private static void rangeCheck(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt2 > paramInt3) {
      throw new IllegalArgumentException("fromIndex(" + paramInt2 + ") > toIndex(" + paramInt3 + ")");
    }
    if (paramInt2 < 0) {
      throw new ArrayIndexOutOfBoundsException(paramInt2);
    }
    if (paramInt3 > paramInt1) {
      throw new ArrayIndexOutOfBoundsException(paramInt3);
    }
  }
  
  private static void swap(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = paramArrayOfByte[paramInt1];
    paramArrayOfByte[paramInt1] = paramArrayOfByte[paramInt2];
    paramArrayOfByte[paramInt2] = i;
  }
  
  private static void swap(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    int i = paramArrayOfChar[paramInt1];
    paramArrayOfChar[paramInt1] = paramArrayOfChar[paramInt2];
    paramArrayOfChar[paramInt2] = i;
  }
  
  private static void swap(double[] paramArrayOfDouble, int paramInt1, int paramInt2)
  {
    double d = paramArrayOfDouble[paramInt1];
    paramArrayOfDouble[paramInt1] = paramArrayOfDouble[paramInt2];
    paramArrayOfDouble[paramInt2] = d;
  }
  
  private static void swap(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
  {
    float f = paramArrayOfFloat[paramInt1];
    paramArrayOfFloat[paramInt1] = paramArrayOfFloat[paramInt2];
    paramArrayOfFloat[paramInt2] = f;
  }
  
  private static void swap(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int i = paramArrayOfInt[paramInt1];
    paramArrayOfInt[paramInt1] = paramArrayOfInt[paramInt2];
    paramArrayOfInt[paramInt2] = i;
  }
  
  private static void swap(long[] paramArrayOfLong, int paramInt1, int paramInt2)
  {
    long l = paramArrayOfLong[paramInt1];
    paramArrayOfLong[paramInt1] = paramArrayOfLong[paramInt2];
    paramArrayOfLong[paramInt2] = l;
  }
  
  private static void swap(Object[] paramArrayOfObject, int paramInt1, int paramInt2)
  {
    Object localObject = paramArrayOfObject[paramInt1];
    paramArrayOfObject[paramInt1] = paramArrayOfObject[paramInt2];
    paramArrayOfObject[paramInt2] = localObject;
  }
  
  private static void swap(short[] paramArrayOfShort, int paramInt1, int paramInt2)
  {
    int i = paramArrayOfShort[paramInt1];
    paramArrayOfShort[paramInt1] = paramArrayOfShort[paramInt2];
    paramArrayOfShort[paramInt2] = i;
  }
  
  private static void vecswap(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    while (i < paramInt3)
    {
      swap(paramArrayOfByte, paramInt1, paramInt2);
      i++;
      paramInt1++;
      paramInt2++;
    }
  }
  
  private static void vecswap(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    while (i < paramInt3)
    {
      swap(paramArrayOfChar, paramInt1, paramInt2);
      i++;
      paramInt1++;
      paramInt2++;
    }
  }
  
  private static void vecswap(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    while (i < paramInt3)
    {
      swap(paramArrayOfDouble, paramInt1, paramInt2);
      i++;
      paramInt1++;
      paramInt2++;
    }
  }
  
  private static void vecswap(float[] paramArrayOfFloat, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    while (i < paramInt3)
    {
      swap(paramArrayOfFloat, paramInt1, paramInt2);
      i++;
      paramInt1++;
      paramInt2++;
    }
  }
  
  private static void vecswap(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    while (i < paramInt3)
    {
      swap(paramArrayOfInt, paramInt1, paramInt2);
      i++;
      paramInt1++;
      paramInt2++;
    }
  }
  
  private static void vecswap(long[] paramArrayOfLong, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    while (i < paramInt3)
    {
      swap(paramArrayOfLong, paramInt1, paramInt2);
      i++;
      paramInt1++;
      paramInt2++;
    }
  }
  
  private static void vecswap(Object[] paramArrayOfObject, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    while (i < paramInt3)
    {
      swap(paramArrayOfObject, paramInt1, paramInt2);
      i++;
      paramInt1++;
      paramInt2++;
    }
  }
  
  private static void vecswap(short[] paramArrayOfShort, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    while (i < paramInt3)
    {
      swap(paramArrayOfShort, paramInt1, paramInt2);
      i++;
      paramInt1++;
      paramInt2++;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.Sorting
 * JD-Core Version:    0.7.0.1
 */
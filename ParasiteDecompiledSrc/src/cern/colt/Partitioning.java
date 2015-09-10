package cern.colt;

import cern.colt.function.IntComparator;
import cern.colt.list.DoubleArrayList;
import cern.colt.list.IntArrayList;
import java.util.Comparator;

public class Partitioning
{
  private static final int SMALL = 7;
  private static final int MEDIUM = 40;
  protected static int steps = 0;
  public static int swappedElements = 0;
  
  private static int binarySearchFromTo(int paramInt1, int paramInt2, int paramInt3, IntComparator paramIntComparator)
  {
    while (paramInt2 <= paramInt3)
    {
      int i = (paramInt2 + paramInt3) / 2;
      int j = paramIntComparator.compare(i, paramInt1);
      if (j < 0) {
        paramInt2 = i + 1;
      } else if (j > 0) {
        paramInt3 = i - 1;
      } else {
        return i;
      }
    }
    return -(paramInt2 + 1);
  }
  
  public static void dualPartition(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, int paramInt1, int paramInt2, double[] paramArrayOfDouble3, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    if (paramInt3 > paramInt4) {
      return;
    }
    int i;
    if (paramInt1 > paramInt2)
    {
      paramInt1--;
      i = paramInt3;
      while (i <= paramInt4) {
        paramArrayOfInt[(i++)] = paramInt1;
      }
      return;
    }
    int k;
    if (paramInt3 == paramInt4)
    {
      i = paramInt3;
    }
    else
    {
      j = (paramInt1 + paramInt2) / 2;
      k = paramInt2 - paramInt1 + 1;
      if (k > 7)
      {
        int m = paramInt1;
        int n = paramInt2;
        if (k > 40)
        {
          int i1 = k / 8;
          m = med3(paramArrayOfDouble1, m, m + i1, m + 2 * i1);
          j = med3(paramArrayOfDouble1, j - i1, j, j + i1);
          n = med3(paramArrayOfDouble1, n - 2 * i1, n - i1, n);
        }
        j = med3(paramArrayOfDouble1, m, j, n);
      }
      i = Sorting.binarySearchFromTo(paramArrayOfDouble3, paramArrayOfDouble1[j], paramInt3, paramInt4);
      if (i < 0) {
        i = -i - 1;
      }
      if (i > paramInt4) {
        i = paramInt4;
      }
    }
    double d = paramArrayOfDouble3[i];
    int j = dualPartition(paramArrayOfDouble1, paramArrayOfDouble2, paramInt1, paramInt2, d);
    paramArrayOfInt[i] = j;
    if (j < paramInt1)
    {
      k = i - 1;
      while ((k >= paramInt3) && (d >= paramArrayOfDouble3[k])) {
        paramArrayOfInt[(k--)] = j;
      }
      paramInt3 = i + 1;
    }
    else if (j >= paramInt2)
    {
      k = i + 1;
      while ((k <= paramInt4) && (d <= paramArrayOfDouble3[k])) {
        paramArrayOfInt[(k++)] = j;
      }
      paramInt4 = i - 1;
    }
    if (paramInt3 <= i - 1) {
      dualPartition(paramArrayOfDouble1, paramArrayOfDouble2, paramInt1, j, paramArrayOfDouble3, paramInt3, i - 1, paramArrayOfInt);
    }
    if (i + 1 <= paramInt4) {
      dualPartition(paramArrayOfDouble1, paramArrayOfDouble2, j + 1, paramInt2, paramArrayOfDouble3, i + 1, paramInt4, paramArrayOfInt);
    }
  }
  
  public static int dualPartition(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, int paramInt1, int paramInt2, double paramDouble)
  {
    int i = paramInt1 - 1;
    for (;;)
    {
      i++;
      if (i > paramInt2) {
        break;
      }
      double d = paramArrayOfDouble1[i];
      if (d < paramDouble)
      {
        paramArrayOfDouble1[i] = paramArrayOfDouble1[paramInt1];
        paramArrayOfDouble1[paramInt1] = d;
        d = paramArrayOfDouble2[i];
        paramArrayOfDouble2[i] = paramArrayOfDouble2[paramInt1];
        paramArrayOfDouble2[(paramInt1++)] = d;
      }
    }
    return paramInt1 - 1;
  }
  
  public static void dualPartition(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int[] paramArrayOfInt3, int paramInt3, int paramInt4, int[] paramArrayOfInt4)
  {
    if (paramInt3 > paramInt4) {
      return;
    }
    int j;
    if (paramInt1 > paramInt2)
    {
      paramInt1--;
      j = paramInt3;
      while (j <= paramInt4) {
        paramArrayOfInt4[(j++)] = paramInt1;
      }
      return;
    }
    int m;
    if (paramInt3 == paramInt4)
    {
      j = paramInt3;
    }
    else
    {
      k = (paramInt1 + paramInt2) / 2;
      m = paramInt2 - paramInt1 + 1;
      if (m > 7)
      {
        int n = paramInt1;
        int i1 = paramInt2;
        if (m > 40)
        {
          int i2 = m / 8;
          n = med3(paramArrayOfInt1, n, n + i2, n + 2 * i2);
          k = med3(paramArrayOfInt1, k - i2, k, k + i2);
          i1 = med3(paramArrayOfInt1, i1 - 2 * i2, i1 - i2, i1);
        }
        k = med3(paramArrayOfInt1, n, k, i1);
      }
      j = Sorting.binarySearchFromTo(paramArrayOfInt3, paramArrayOfInt1[k], paramInt3, paramInt4);
      if (j < 0) {
        j = -j - 1;
      }
      if (j > paramInt4) {
        j = paramInt4;
      }
    }
    int i = paramArrayOfInt3[j];
    int k = dualPartition(paramArrayOfInt1, paramArrayOfInt2, paramInt1, paramInt2, i);
    paramArrayOfInt4[j] = k;
    if (k < paramInt1)
    {
      m = j - 1;
      while ((m >= paramInt3) && (i >= paramArrayOfInt3[m])) {
        paramArrayOfInt4[(m--)] = k;
      }
      paramInt3 = j + 1;
    }
    else if (k >= paramInt2)
    {
      m = j + 1;
      while ((m <= paramInt4) && (i <= paramArrayOfInt3[m])) {
        paramArrayOfInt4[(m++)] = k;
      }
      paramInt4 = j - 1;
    }
    if (paramInt3 <= j - 1) {
      dualPartition(paramArrayOfInt1, paramArrayOfInt2, paramInt1, k, paramArrayOfInt3, paramInt3, j - 1, paramArrayOfInt4);
    }
    if (j + 1 <= paramInt4) {
      dualPartition(paramArrayOfInt1, paramArrayOfInt2, k + 1, paramInt2, paramArrayOfInt3, j + 1, paramInt4, paramArrayOfInt4);
    }
  }
  
  public static int dualPartition(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3)
  {
    int j = paramInt1 - 1;
    for (;;)
    {
      j++;
      if (j > paramInt2) {
        break;
      }
      int i = paramArrayOfInt1[j];
      if (i < paramInt3)
      {
        paramArrayOfInt1[j] = paramArrayOfInt1[paramInt1];
        paramArrayOfInt1[paramInt1] = i;
        i = paramArrayOfInt2[j];
        paramArrayOfInt2[j] = paramArrayOfInt2[paramInt1];
        paramArrayOfInt2[(paramInt1++)] = i;
      }
    }
    return paramInt1 - 1;
  }
  
  public static void genericPartition(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt, IntComparator paramIntComparator1, IntComparator paramIntComparator2, IntComparator paramIntComparator3, Swapper paramSwapper)
  {
    if (paramInt3 > paramInt4) {
      return;
    }
    int j;
    if (paramInt1 > paramInt2)
    {
      paramInt1--;
      j = paramInt3;
      while (j <= paramInt4) {
        paramArrayOfInt[(j++)] = paramInt1;
      }
      return;
    }
    int m;
    if (paramInt3 == paramInt4)
    {
      j = paramInt3;
    }
    else
    {
      k = (paramInt1 + paramInt2) / 2;
      m = paramInt2 - paramInt1 + 1;
      if (m > 7)
      {
        int n = paramInt1;
        int i1 = paramInt2;
        if (m > 40)
        {
          int i2 = m / 8;
          n = med3(n, n + i2, n + 2 * i2, paramIntComparator2);
          k = med3(k - i2, k, k + i2, paramIntComparator2);
          i1 = med3(i1 - 2 * i2, i1 - i2, i1, paramIntComparator2);
        }
        k = med3(n, k, i1, paramIntComparator2);
      }
      j = binarySearchFromTo(k, paramInt3, paramInt4, paramIntComparator1);
      if (j < 0) {
        j = -j - 1;
      }
      if (j > paramInt4) {
        j = paramInt4;
      }
    }
    int i = j;
    int k = genericPartition(paramInt1, paramInt2, i, paramIntComparator1, paramSwapper);
    paramArrayOfInt[j] = k;
    if (k < paramInt1)
    {
      m = j - 1;
      while ((m >= paramInt3) && (paramIntComparator3.compare(i, m) >= 0)) {
        paramArrayOfInt[(m--)] = k;
      }
      paramInt3 = j + 1;
    }
    else if (k >= paramInt2)
    {
      m = j + 1;
      while ((m <= paramInt4) && (paramIntComparator3.compare(i, m) <= 0)) {
        paramArrayOfInt[(m++)] = k;
      }
      paramInt4 = j - 1;
    }
    if (paramInt3 <= j - 1) {
      genericPartition(paramInt1, k, paramInt3, j - 1, paramArrayOfInt, paramIntComparator1, paramIntComparator2, paramIntComparator3, paramSwapper);
    }
    if (j + 1 <= paramInt4) {
      genericPartition(k + 1, paramInt2, j + 1, paramInt4, paramArrayOfInt, paramIntComparator1, paramIntComparator2, paramIntComparator3, paramSwapper);
    }
  }
  
  private static int genericPartition(int paramInt1, int paramInt2, int paramInt3, IntComparator paramIntComparator, Swapper paramSwapper)
  {
    int i = paramInt1 - 1;
    for (;;)
    {
      i++;
      if (i > paramInt2) {
        break;
      }
      if (paramIntComparator.compare(paramInt3, i) > 0)
      {
        paramSwapper.swap(i, paramInt1);
        paramInt1++;
      }
    }
    return paramInt1 - 1;
  }
  
  private static int med3(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3)
  {
    return paramArrayOfDouble[paramInt1] > paramArrayOfDouble[paramInt3] ? paramInt3 : paramArrayOfDouble[paramInt2] > paramArrayOfDouble[paramInt3] ? paramInt2 : paramArrayOfDouble[paramInt1] < paramArrayOfDouble[paramInt2] ? paramInt1 : paramArrayOfDouble[paramInt1] < paramArrayOfDouble[paramInt3] ? paramInt3 : paramArrayOfDouble[paramInt2] < paramArrayOfDouble[paramInt3] ? paramInt2 : paramInt1;
  }
  
  private static int med3(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    return paramArrayOfInt[paramInt1] > paramArrayOfInt[paramInt3] ? paramInt3 : paramArrayOfInt[paramInt2] > paramArrayOfInt[paramInt3] ? paramInt2 : paramArrayOfInt[paramInt1] < paramArrayOfInt[paramInt2] ? paramInt1 : paramArrayOfInt[paramInt1] < paramArrayOfInt[paramInt3] ? paramInt3 : paramArrayOfInt[paramInt2] < paramArrayOfInt[paramInt3] ? paramInt2 : paramInt1;
  }
  
  private static int med3(Object[] paramArrayOfObject, int paramInt1, int paramInt2, int paramInt3, Comparator paramComparator)
  {
    int i = paramComparator.compare(paramArrayOfObject[paramInt1], paramArrayOfObject[paramInt2]);
    int j = paramComparator.compare(paramArrayOfObject[paramInt1], paramArrayOfObject[paramInt3]);
    int k = paramComparator.compare(paramArrayOfObject[paramInt2], paramArrayOfObject[paramInt3]);
    return j > 0 ? paramInt3 : k > 0 ? paramInt2 : i < 0 ? paramInt1 : j < 0 ? paramInt3 : k < 0 ? paramInt2 : paramInt1;
  }
  
  private static int med3(int paramInt1, int paramInt2, int paramInt3, IntComparator paramIntComparator)
  {
    int i = paramIntComparator.compare(paramInt1, paramInt2);
    int j = paramIntComparator.compare(paramInt1, paramInt3);
    int k = paramIntComparator.compare(paramInt2, paramInt3);
    return j > 0 ? paramInt3 : k > 0 ? paramInt2 : i < 0 ? paramInt1 : j < 0 ? paramInt3 : k < 0 ? paramInt2 : paramInt1;
  }
  
  public static void partition(double[] paramArrayOfDouble1, int paramInt1, int paramInt2, double[] paramArrayOfDouble2, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    if (paramInt3 > paramInt4) {
      return;
    }
    int i;
    if (paramInt1 > paramInt2)
    {
      paramInt1--;
      i = paramInt3;
      while (i <= paramInt4) {
        paramArrayOfInt[(i++)] = paramInt1;
      }
      return;
    }
    int k;
    if (paramInt3 == paramInt4)
    {
      i = paramInt3;
    }
    else
    {
      j = (paramInt1 + paramInt2) / 2;
      k = paramInt2 - paramInt1 + 1;
      if (k > 7)
      {
        int m = paramInt1;
        int n = paramInt2;
        if (k > 40)
        {
          int i1 = k / 8;
          m = med3(paramArrayOfDouble1, m, m + i1, m + 2 * i1);
          j = med3(paramArrayOfDouble1, j - i1, j, j + i1);
          n = med3(paramArrayOfDouble1, n - 2 * i1, n - i1, n);
        }
        j = med3(paramArrayOfDouble1, m, j, n);
      }
      i = Sorting.binarySearchFromTo(paramArrayOfDouble2, paramArrayOfDouble1[j], paramInt3, paramInt4);
      if (i < 0) {
        i = -i - 1;
      }
      if (i > paramInt4) {
        i = paramInt4;
      }
    }
    double d = paramArrayOfDouble2[i];
    int j = partition(paramArrayOfDouble1, paramInt1, paramInt2, d);
    paramArrayOfInt[i] = j;
    if (j < paramInt1)
    {
      k = i - 1;
      while ((k >= paramInt3) && (d >= paramArrayOfDouble2[k])) {
        paramArrayOfInt[(k--)] = j;
      }
      paramInt3 = i + 1;
    }
    else if (j >= paramInt2)
    {
      k = i + 1;
      while ((k <= paramInt4) && (d <= paramArrayOfDouble2[k])) {
        paramArrayOfInt[(k++)] = j;
      }
      paramInt4 = i - 1;
    }
    if (paramInt3 <= i - 1) {
      partition(paramArrayOfDouble1, paramInt1, j, paramArrayOfDouble2, paramInt3, i - 1, paramArrayOfInt);
    }
    if (i + 1 <= paramInt4) {
      partition(paramArrayOfDouble1, j + 1, paramInt2, paramArrayOfDouble2, i + 1, paramInt4, paramArrayOfInt);
    }
  }
  
  public static int partition(double[] paramArrayOfDouble, int paramInt1, int paramInt2, double paramDouble)
  {
    int i = paramInt1 - 1;
    for (;;)
    {
      i++;
      if (i > paramInt2) {
        break;
      }
      double d = paramArrayOfDouble[i];
      if (d < paramDouble)
      {
        paramArrayOfDouble[i] = paramArrayOfDouble[paramInt1];
        paramArrayOfDouble[(paramInt1++)] = d;
      }
    }
    return paramInt1 - 1;
  }
  
  public static void partition(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int[] paramArrayOfInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt3)
  {
    if (paramInt3 > paramInt4) {
      return;
    }
    int j;
    if (paramInt1 > paramInt2)
    {
      paramInt1--;
      j = paramInt3;
      while (j <= paramInt4) {
        paramArrayOfInt3[(j++)] = paramInt1;
      }
      return;
    }
    int m;
    if (paramInt3 == paramInt4)
    {
      j = paramInt3;
    }
    else
    {
      k = (paramInt1 + paramInt2) / 2;
      m = paramInt2 - paramInt1 + 1;
      if (m > 7)
      {
        int n = paramInt1;
        int i1 = paramInt2;
        if (m > 40)
        {
          int i2 = m / 8;
          n = med3(paramArrayOfInt1, n, n + i2, n + 2 * i2);
          k = med3(paramArrayOfInt1, k - i2, k, k + i2);
          i1 = med3(paramArrayOfInt1, i1 - 2 * i2, i1 - i2, i1);
        }
        k = med3(paramArrayOfInt1, n, k, i1);
      }
      j = Sorting.binarySearchFromTo(paramArrayOfInt2, paramArrayOfInt1[k], paramInt3, paramInt4);
      if (j < 0) {
        j = -j - 1;
      }
      if (j > paramInt4) {
        j = paramInt4;
      }
    }
    int i = paramArrayOfInt2[j];
    int k = partition(paramArrayOfInt1, paramInt1, paramInt2, i);
    paramArrayOfInt3[j] = k;
    if (k < paramInt1)
    {
      m = j - 1;
      while ((m >= paramInt3) && (i >= paramArrayOfInt2[m])) {
        paramArrayOfInt3[(m--)] = k;
      }
      paramInt3 = j + 1;
    }
    else if (k >= paramInt2)
    {
      m = j + 1;
      while ((m <= paramInt4) && (i <= paramArrayOfInt2[m])) {
        paramArrayOfInt3[(m++)] = k;
      }
      paramInt4 = j - 1;
    }
    if (paramInt3 <= j - 1) {
      partition(paramArrayOfInt1, paramInt1, k, paramArrayOfInt2, paramInt3, j - 1, paramArrayOfInt3);
    }
    if (j + 1 <= paramInt4) {
      partition(paramArrayOfInt1, k + 1, paramInt2, paramArrayOfInt2, j + 1, paramInt4, paramArrayOfInt3);
    }
  }
  
  public static int partition(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    steps += paramInt2 - paramInt1 + 1;
    int j = paramInt1 - 1;
    for (;;)
    {
      j++;
      if (j > paramInt2) {
        break;
      }
      int i = paramArrayOfInt[j];
      if (i < paramInt3)
      {
        paramArrayOfInt[j] = paramArrayOfInt[paramInt1];
        paramArrayOfInt[(paramInt1++)] = i;
      }
    }
    return paramInt1 - 1;
  }
  
  public static void partition(Object[] paramArrayOfObject1, int paramInt1, int paramInt2, Object[] paramArrayOfObject2, int paramInt3, int paramInt4, int[] paramArrayOfInt, Comparator paramComparator)
  {
    if (paramInt3 > paramInt4) {
      return;
    }
    int i;
    if (paramInt1 > paramInt2)
    {
      paramInt1--;
      i = paramInt3;
      while (i <= paramInt4) {
        paramArrayOfInt[(i++)] = paramInt1;
      }
      return;
    }
    int k;
    if (paramInt3 == paramInt4)
    {
      i = paramInt3;
    }
    else
    {
      j = (paramInt1 + paramInt2) / 2;
      k = paramInt2 - paramInt1 + 1;
      if (k > 7)
      {
        int m = paramInt1;
        int n = paramInt2;
        if (k > 40)
        {
          int i1 = k / 8;
          m = med3(paramArrayOfObject1, m, m + i1, m + 2 * i1, paramComparator);
          j = med3(paramArrayOfObject1, j - i1, j, j + i1, paramComparator);
          n = med3(paramArrayOfObject1, n - 2 * i1, n - i1, n, paramComparator);
        }
        j = med3(paramArrayOfObject1, m, j, n, paramComparator);
      }
      i = Sorting.binarySearchFromTo(paramArrayOfObject2, paramArrayOfObject1[j], paramInt3, paramInt4, paramComparator);
      if (i < 0) {
        i = -i - 1;
      }
      if (i > paramInt4) {
        i = paramInt4;
      }
    }
    Object localObject = paramArrayOfObject2[i];
    int j = partition(paramArrayOfObject1, paramInt1, paramInt2, localObject, paramComparator);
    paramArrayOfInt[i] = j;
    if (j < paramInt1)
    {
      k = i - 1;
      while ((k >= paramInt3) && (paramComparator.compare(localObject, paramArrayOfObject2[k]) >= 0)) {
        paramArrayOfInt[(k--)] = j;
      }
      paramInt3 = i + 1;
    }
    else if (j >= paramInt2)
    {
      k = i + 1;
      while ((k <= paramInt4) && (paramComparator.compare(localObject, paramArrayOfObject2[k]) <= 0)) {
        paramArrayOfInt[(k++)] = j;
      }
      paramInt4 = i - 1;
    }
    if (paramInt3 <= i - 1) {
      partition(paramArrayOfObject1, paramInt1, j, paramArrayOfObject2, paramInt3, i - 1, paramArrayOfInt, paramComparator);
    }
    if (i + 1 <= paramInt4) {
      partition(paramArrayOfObject1, j + 1, paramInt2, paramArrayOfObject2, i + 1, paramInt4, paramArrayOfInt, paramComparator);
    }
  }
  
  public static int partition(Object[] paramArrayOfObject, int paramInt1, int paramInt2, Object paramObject, Comparator paramComparator)
  {
    int i = paramInt1 - 1;
    for (;;)
    {
      i++;
      if (i > paramInt2) {
        break;
      }
      Object localObject = paramArrayOfObject[i];
      if (paramComparator.compare(localObject, paramObject) < 0)
      {
        paramArrayOfObject[i] = paramArrayOfObject[paramInt1];
        paramArrayOfObject[paramInt1] = localObject;
        paramInt1++;
      }
    }
    return paramInt1 - 1;
  }
  
  public static void partition(DoubleArrayList paramDoubleArrayList1, int paramInt1, int paramInt2, DoubleArrayList paramDoubleArrayList2, IntArrayList paramIntArrayList)
  {
    partition(paramDoubleArrayList1.elements(), paramInt1, paramInt2, paramDoubleArrayList2.elements(), 0, paramDoubleArrayList2.size() - 1, paramIntArrayList.elements());
  }
  
  public static void partition(IntArrayList paramIntArrayList1, int paramInt1, int paramInt2, IntArrayList paramIntArrayList2, IntArrayList paramIntArrayList3)
  {
    partition(paramIntArrayList1.elements(), paramInt1, paramInt2, paramIntArrayList2.elements(), 0, paramIntArrayList2.size() - 1, paramIntArrayList3.elements());
  }
  
  public static void triplePartition(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, double[] paramArrayOfDouble3, int paramInt1, int paramInt2, double[] paramArrayOfDouble4, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    if (paramInt3 > paramInt4) {
      return;
    }
    int i;
    if (paramInt1 > paramInt2)
    {
      paramInt1--;
      i = paramInt3;
      while (i <= paramInt4) {
        paramArrayOfInt[(i++)] = paramInt1;
      }
      return;
    }
    int k;
    if (paramInt3 == paramInt4)
    {
      i = paramInt3;
    }
    else
    {
      j = (paramInt1 + paramInt2) / 2;
      k = paramInt2 - paramInt1 + 1;
      if (k > 7)
      {
        int m = paramInt1;
        int n = paramInt2;
        if (k > 40)
        {
          int i1 = k / 8;
          m = med3(paramArrayOfDouble1, m, m + i1, m + 2 * i1);
          j = med3(paramArrayOfDouble1, j - i1, j, j + i1);
          n = med3(paramArrayOfDouble1, n - 2 * i1, n - i1, n);
        }
        j = med3(paramArrayOfDouble1, m, j, n);
      }
      i = Sorting.binarySearchFromTo(paramArrayOfDouble4, paramArrayOfDouble1[j], paramInt3, paramInt4);
      if (i < 0) {
        i = -i - 1;
      }
      if (i > paramInt4) {
        i = paramInt4;
      }
    }
    double d = paramArrayOfDouble4[i];
    int j = triplePartition(paramArrayOfDouble1, paramArrayOfDouble2, paramArrayOfDouble3, paramInt1, paramInt2, d);
    paramArrayOfInt[i] = j;
    if (j < paramInt1)
    {
      k = i - 1;
      while ((k >= paramInt3) && (d >= paramArrayOfDouble4[k])) {
        paramArrayOfInt[(k--)] = j;
      }
      paramInt3 = i + 1;
    }
    else if (j >= paramInt2)
    {
      k = i + 1;
      while ((k <= paramInt4) && (d <= paramArrayOfDouble4[k])) {
        paramArrayOfInt[(k++)] = j;
      }
      paramInt4 = i - 1;
    }
    if (paramInt3 <= i - 1) {
      triplePartition(paramArrayOfDouble1, paramArrayOfDouble2, paramArrayOfDouble3, paramInt1, j, paramArrayOfDouble4, paramInt3, i - 1, paramArrayOfInt);
    }
    if (i + 1 <= paramInt4) {
      triplePartition(paramArrayOfDouble1, paramArrayOfDouble2, paramArrayOfDouble3, j + 1, paramInt2, paramArrayOfDouble4, i + 1, paramInt4, paramArrayOfInt);
    }
  }
  
  public static int triplePartition(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, double[] paramArrayOfDouble3, int paramInt1, int paramInt2, double paramDouble)
  {
    int i = paramInt1 - 1;
    for (;;)
    {
      i++;
      if (i > paramInt2) {
        break;
      }
      double d = paramArrayOfDouble1[i];
      if (d < paramDouble)
      {
        paramArrayOfDouble1[i] = paramArrayOfDouble1[paramInt1];
        paramArrayOfDouble1[paramInt1] = d;
        d = paramArrayOfDouble2[i];
        paramArrayOfDouble2[i] = paramArrayOfDouble2[paramInt1];
        paramArrayOfDouble2[paramInt1] = d;
        d = paramArrayOfDouble3[i];
        paramArrayOfDouble3[i] = paramArrayOfDouble3[paramInt1];
        paramArrayOfDouble3[(paramInt1++)] = d;
      }
    }
    return paramInt1 - 1;
  }
  
  public static void triplePartition(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int paramInt1, int paramInt2, int[] paramArrayOfInt4, int paramInt3, int paramInt4, int[] paramArrayOfInt5)
  {
    if (paramInt3 > paramInt4) {
      return;
    }
    int j;
    if (paramInt1 > paramInt2)
    {
      paramInt1--;
      j = paramInt3;
      while (j <= paramInt4) {
        paramArrayOfInt5[(j++)] = paramInt1;
      }
      return;
    }
    int m;
    if (paramInt3 == paramInt4)
    {
      j = paramInt3;
    }
    else
    {
      k = (paramInt1 + paramInt2) / 2;
      m = paramInt2 - paramInt1 + 1;
      if (m > 7)
      {
        int n = paramInt1;
        int i1 = paramInt2;
        if (m > 40)
        {
          int i2 = m / 8;
          n = med3(paramArrayOfInt1, n, n + i2, n + 2 * i2);
          k = med3(paramArrayOfInt1, k - i2, k, k + i2);
          i1 = med3(paramArrayOfInt1, i1 - 2 * i2, i1 - i2, i1);
        }
        k = med3(paramArrayOfInt1, n, k, i1);
      }
      j = Sorting.binarySearchFromTo(paramArrayOfInt4, paramArrayOfInt1[k], paramInt3, paramInt4);
      if (j < 0) {
        j = -j - 1;
      }
      if (j > paramInt4) {
        j = paramInt4;
      }
    }
    int i = paramArrayOfInt4[j];
    int k = triplePartition(paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3, paramInt1, paramInt2, i);
    paramArrayOfInt5[j] = k;
    if (k < paramInt1)
    {
      m = j - 1;
      while ((m >= paramInt3) && (i >= paramArrayOfInt4[m])) {
        paramArrayOfInt5[(m--)] = k;
      }
      paramInt3 = j + 1;
    }
    else if (k >= paramInt2)
    {
      m = j + 1;
      while ((m <= paramInt4) && (i <= paramArrayOfInt4[m])) {
        paramArrayOfInt5[(m++)] = k;
      }
      paramInt4 = j - 1;
    }
    if (paramInt3 <= j - 1) {
      triplePartition(paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3, paramInt1, k, paramArrayOfInt4, paramInt3, j - 1, paramArrayOfInt5);
    }
    if (j + 1 <= paramInt4) {
      triplePartition(paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3, k + 1, paramInt2, paramArrayOfInt4, j + 1, paramInt4, paramArrayOfInt5);
    }
  }
  
  public static int triplePartition(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int paramInt1, int paramInt2, int paramInt3)
  {
    int j = paramInt1 - 1;
    for (;;)
    {
      j++;
      if (j > paramInt2) {
        break;
      }
      int i = paramArrayOfInt1[j];
      if (i < paramInt3)
      {
        paramArrayOfInt1[j] = paramArrayOfInt1[paramInt1];
        paramArrayOfInt1[paramInt1] = i;
        i = paramArrayOfInt2[j];
        paramArrayOfInt2[j] = paramArrayOfInt2[paramInt1];
        paramArrayOfInt2[paramInt1] = i;
        i = paramArrayOfInt3[j];
        paramArrayOfInt3[j] = paramArrayOfInt3[paramInt1];
        paramArrayOfInt3[(paramInt1++)] = i;
      }
    }
    return paramInt1 - 1;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.Partitioning
 * JD-Core Version:    0.7.0.1
 */
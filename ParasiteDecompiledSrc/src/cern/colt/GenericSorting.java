package cern.colt;

import cern.colt.function.IntComparator;

public class GenericSorting
{
  private static final int SMALL = 7;
  private static final int MEDIUM = 40;
  
  private static void inplace_merge(int paramInt1, int paramInt2, int paramInt3, IntComparator paramIntComparator, Swapper paramSwapper)
  {
    if ((paramInt1 >= paramInt2) || (paramInt2 >= paramInt3)) {
      return;
    }
    if (paramInt3 - paramInt1 == 2)
    {
      if (paramIntComparator.compare(paramInt2, paramInt1) < 0) {
        paramSwapper.swap(paramInt1, paramInt2);
      }
      return;
    }
    int i;
    int j;
    if (paramInt2 - paramInt1 > paramInt3 - paramInt2)
    {
      i = paramInt1 + (paramInt2 - paramInt1) / 2;
      j = lower_bound(paramInt2, paramInt3, i, paramIntComparator);
    }
    else
    {
      j = paramInt2 + (paramInt3 - paramInt2) / 2;
      i = upper_bound(paramInt1, paramInt2, j, paramIntComparator);
    }
    int k = i;
    int m = paramInt2;
    int n = j;
    if ((m != k) && (m != n))
    {
      int i1 = k;
      int i2 = m;
      while (i1 < --i2) {
        paramSwapper.swap(i1++, i2);
      }
      i1 = m;
      i2 = n;
      while (i1 < --i2) {
        paramSwapper.swap(i1++, i2);
      }
      i1 = k;
      i2 = n;
      while (i1 < --i2) {
        paramSwapper.swap(i1++, i2);
      }
    }
    paramInt2 = i + (j - paramInt2);
    inplace_merge(paramInt1, i, paramInt2, paramIntComparator, paramSwapper);
    inplace_merge(paramInt2, j, paramInt3, paramIntComparator, paramSwapper);
  }
  
  private static int lower_bound(int paramInt1, int paramInt2, int paramInt3, IntComparator paramIntComparator)
  {
    int i = paramInt2 - paramInt1;
    while (i > 0)
    {
      int j = i / 2;
      int k = paramInt1 + j;
      if (paramIntComparator.compare(k, paramInt3) < 0)
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
  
  private static int med3(int paramInt1, int paramInt2, int paramInt3, IntComparator paramIntComparator)
  {
    int i = paramIntComparator.compare(paramInt1, paramInt2);
    int j = paramIntComparator.compare(paramInt1, paramInt3);
    int k = paramIntComparator.compare(paramInt2, paramInt3);
    return j > 0 ? paramInt3 : k > 0 ? paramInt2 : i < 0 ? paramInt1 : j < 0 ? paramInt3 : k < 0 ? paramInt2 : paramInt1;
  }
  
  public static void mergeSort(int paramInt1, int paramInt2, IntComparator paramIntComparator, Swapper paramSwapper)
  {
    int i = paramInt2 - paramInt1;
    if (i < 7)
    {
      for (j = paramInt1; j < paramInt2; j++) {
        for (int k = j; (k > paramInt1) && (paramIntComparator.compare(k - 1, k) > 0); k--) {
          paramSwapper.swap(k, k - 1);
        }
      }
      return;
    }
    int j = (paramInt1 + paramInt2) / 2;
    mergeSort(paramInt1, j, paramIntComparator, paramSwapper);
    mergeSort(j, paramInt2, paramIntComparator, paramSwapper);
    if (paramIntComparator.compare(j - 1, j) <= 0) {
      return;
    }
    inplace_merge(paramInt1, j, paramInt2, paramIntComparator, paramSwapper);
  }
  
  public static void quickSort(int paramInt1, int paramInt2, IntComparator paramIntComparator, Swapper paramSwapper)
  {
    quickSort1(paramInt1, paramInt2 - paramInt1, paramIntComparator, paramSwapper);
  }
  
  private static void quickSort1(int paramInt1, int paramInt2, IntComparator paramIntComparator, Swapper paramSwapper)
  {
    if (paramInt2 < 7)
    {
      for (i = paramInt1; i < paramInt2 + paramInt1; i++) {
        for (j = i; (j > paramInt1) && (paramIntComparator.compare(j - 1, j) > 0); j--) {
          paramSwapper.swap(j, j - 1);
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
        j = med3(j, j + m, j + 2 * m, paramIntComparator);
        i = med3(i - m, i, i + m, paramIntComparator);
        k = med3(k - 2 * m, k - m, k, paramIntComparator);
      }
      i = med3(j, i, k, paramIntComparator);
    }
    int j = paramInt1;
    int k = j;
    int m = paramInt1 + paramInt2 - 1;
    int n = m;
    for (;;)
    {
      if ((k <= m) && ((i1 = paramIntComparator.compare(k, i)) <= 0))
      {
        if (i1 == 0)
        {
          if (j == i) {
            i = k;
          } else if (k == i) {
            i = j;
          }
          paramSwapper.swap(j++, k);
        }
        k++;
      }
      else
      {
        while ((m >= k) && ((i1 = paramIntComparator.compare(m, i)) >= 0))
        {
          if (i1 == 0)
          {
            if (m == i) {
              i = n;
            } else if (n == i) {
              i = m;
            }
            paramSwapper.swap(m, n--);
          }
          m--;
        }
        if (k > m) {
          break;
        }
        if (k == i) {
          i = n;
        } else if (m == i) {
          i = m;
        }
        paramSwapper.swap(k++, m--);
      }
    }
    int i2 = paramInt1 + paramInt2;
    int i1 = Math.min(j - paramInt1, k - j);
    vecswap(paramSwapper, paramInt1, k - i1, i1);
    i1 = Math.min(n - m, i2 - n - 1);
    vecswap(paramSwapper, k, i2 - i1, i1);
    if ((i1 = k - j) > 1) {
      quickSort1(paramInt1, i1, paramIntComparator, paramSwapper);
    }
    if ((i1 = n - m) > 1) {
      quickSort1(i2 - i1, i1, paramIntComparator, paramSwapper);
    }
  }
  
  private static void reverse(int paramInt1, int paramInt2, Swapper paramSwapper)
  {
    while (paramInt1 < --paramInt2) {
      paramSwapper.swap(paramInt1++, paramInt2);
    }
  }
  
  private static void rotate(int paramInt1, int paramInt2, int paramInt3, Swapper paramSwapper)
  {
    if ((paramInt2 != paramInt1) && (paramInt2 != paramInt3))
    {
      reverse(paramInt1, paramInt2, paramSwapper);
      reverse(paramInt2, paramInt3, paramSwapper);
      reverse(paramInt1, paramInt3, paramSwapper);
    }
  }
  
  private static int upper_bound(int paramInt1, int paramInt2, int paramInt3, IntComparator paramIntComparator)
  {
    int i = paramInt2 - paramInt1;
    while (i > 0)
    {
      int j = i / 2;
      int k = paramInt1 + j;
      if (paramIntComparator.compare(paramInt3, k) < 0)
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
  
  private static void vecswap(Swapper paramSwapper, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    while (i < paramInt3)
    {
      paramSwapper.swap(paramInt1, paramInt2);
      i++;
      paramInt1++;
      paramInt2++;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.GenericSorting
 * JD-Core Version:    0.7.0.1
 */
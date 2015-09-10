package cern.jet.stat.quantile;

import cern.colt.list.DoubleArrayList;
import cern.jet.math.Arithmetic;
import cern.jet.random.engine.RandomEngine;
import java.io.PrintStream;

public class QuantileFinderFactory
{
  public static long[] known_N_compute_B_and_K(long paramLong, double paramDouble1, double paramDouble2, int paramInt, double[] paramArrayOfDouble)
  {
    paramArrayOfDouble[0] = 1.0D;
    long[] arrayOfLong;
    if (paramDouble1 <= 0.0D)
    {
      arrayOfLong = new long[2];
      arrayOfLong[0] = 1L;
      arrayOfLong[1] = paramLong;
      return arrayOfLong;
    }
    if ((paramDouble1 >= 1.0D) || (paramDouble2 >= 1.0D))
    {
      arrayOfLong = new long[2];
      arrayOfLong[0] = 2L;
      arrayOfLong[1] = 1L;
      return arrayOfLong;
    }
    if (paramDouble2 > 0.0D) {
      return known_N_compute_B_and_K_slow(paramLong, paramDouble1, paramDouble2, paramInt, paramArrayOfDouble);
    }
    return known_N_compute_B_and_K_quick(paramLong, paramDouble1);
  }
  
  protected static long[] known_N_compute_B_and_K_quick(long paramLong, double paramDouble)
  {
    double d1 = paramLong;
    double d2 = d1 * paramDouble * 2.0D;
    int[] arrayOfInt = new int[49];
    int k;
    for (int i = 2; i <= 50; i++)
    {
      for (j = 3; (j <= 50) && ((j - 2) * Arithmetic.binomial(i + j - 2, j - 1) - Arithmetic.binomial(i + j - 3, j - 3) + Arithmetic.binomial(i + j - 3, j - 2) - d2 > 0.0D); j++) {}
      while ((j <= 50) && ((j - 2) * Arithmetic.binomial(i + j - 2, j - 1) - Arithmetic.binomial(i + j - 3, j - 3) + Arithmetic.binomial(i + j - 3, j - 2) - d2 <= 0.0D)) {
        j++;
      }
      j--;
      if ((j >= 50) && ((j - 2) * Arithmetic.binomial(i + j - 2, j - 1) - Arithmetic.binomial(i + j - 3, j - 3) + Arithmetic.binomial(i + j - 3, j - 2) - d2 > 0.0D)) {
        k = -2147483648;
      } else {
        k = j;
      }
      arrayOfInt[(i - 2)] = k;
    }
    long[] arrayOfLong1 = new long[49];
    for (int j = 2; j <= 50; j++)
    {
      k = arrayOfInt[(j - 2)];
      long l2 = 9223372036854775807L;
      if (k > -2147483648)
      {
        double d3 = Arithmetic.binomial(j + k - 2, k - 1);
        long l6 = Math.ceil(d1 / d3);
        if (l6 <= 9223372036854775807L) {
          l2 = l6;
        }
      }
      arrayOfLong1[(j - 2)] = l2;
    }
    long l1 = 9223372036854775807L;
    int m = -1;
    for (int n = 2; n <= 50; n++) {
      if (arrayOfLong1[(n - 2)] < 9223372036854775807L)
      {
        long l4 = n * arrayOfLong1[(n - 2)];
        if (l4 < l1)
        {
          l1 = l4;
          m = n;
        }
      }
    }
    long l3;
    long l5;
    if (m != -1)
    {
      l3 = m;
      l5 = arrayOfLong1[(m - 2)];
    }
    else
    {
      l3 = 1L;
      l5 = paramLong;
    }
    long[] arrayOfLong2 = new long[2];
    arrayOfLong2[0] = l3;
    arrayOfLong2[1] = l5;
    return arrayOfLong2;
  }
  
  protected static long[] known_N_compute_B_and_K_slow(long paramLong, double paramDouble1, double paramDouble2, int paramInt, double[] paramArrayOfDouble)
  {
    double d1 = paramLong;
    long l1 = 1L;
    long l2 = paramLong;
    double d2 = 1.0D;
    long l3 = paramLong;
    double d3 = Math.log(2.0D * paramInt / paramDouble2);
    double d4 = 2.0D * paramDouble1 * d1;
    for (long l4 = 2L; l4 < 50L; l4 += 1L) {
      for (long l5 = 3L; l5 < 50L; l5 += 1L)
      {
        double d5 = Arithmetic.binomial(l4 + l5 - 2L, l5 - 1L);
        long l6 = Math.ceil(d1 / d5);
        if ((l4 * l6 < l3) && ((l5 - 2L) * d5 - Arithmetic.binomial(l4 + l5 - 3L, l5 - 3L) + Arithmetic.binomial(l4 + l5 - 3L, l5 - 2L) <= d4))
        {
          l2 = l6;
          l1 = l4;
          l3 = l2 * l4;
          d2 = 1.0D;
        }
        if (paramDouble2 > 0.0D)
        {
          double d6 = (l5 - 2L) * Arithmetic.binomial(l4 + l5 - 2L, l5 - 1L) - Arithmetic.binomial(l4 + l5 - 3L, l5 - 3L) + Arithmetic.binomial(l4 + l5 - 3L, l5 - 2L);
          double d7 = d3 / paramDouble1;
          double d8 = Arithmetic.binomial(l4 + l5 - 2L, l5 - 1L);
          double d9 = d3 / (2.0D * paramDouble1 * paramDouble1);
          double d10 = 0.5D + 0.5D * Math.sqrt(1.0D + 4.0D * d6 / d7);
          long l7 = Math.ceil(d9 * d10 * d10 / d8);
          if (l4 * l7 < l3)
          {
            l2 = l7;
            l1 = l4;
            l3 = l4 * l7;
            d2 = d1 * 2.0D * paramDouble1 * paramDouble1 / d3;
          }
        }
      }
    }
    long[] arrayOfLong = new long[2];
    arrayOfLong[0] = l1;
    arrayOfLong[1] = l2;
    paramArrayOfDouble[0] = d2;
    return arrayOfLong;
  }
  
  public static DoubleQuantileFinder newDoubleQuantileFinder(boolean paramBoolean, long paramLong, double paramDouble1, double paramDouble2, int paramInt, RandomEngine paramRandomEngine)
  {
    if ((paramDouble1 <= 0.0D) || (paramLong < 1000L)) {
      return new ExactDoubleQuantileFinder();
    }
    if (paramDouble1 > 1.0D) {
      paramDouble1 = 1.0D;
    }
    if (paramDouble2 < 0.0D) {
      paramDouble2 = 0.0D;
    }
    if (paramDouble2 > 1.0D) {
      paramDouble2 = 1.0D;
    }
    if (paramInt < 1) {
      paramInt = 1;
    }
    if (paramInt > paramLong) {
      paramLong = paramInt;
    }
    if (paramBoolean)
    {
      localObject = new double[1];
      long[] arrayOfLong = known_N_compute_B_and_K(paramLong, paramDouble1, paramDouble2, paramInt, (double[])localObject);
      long l2 = arrayOfLong[0];
      long l4 = arrayOfLong[1];
      if (l2 == 1L) {
        return new ExactDoubleQuantileFinder();
      }
      return new KnownDoubleQuantileEstimator((int)l2, (int)l4, paramLong, localObject[0], paramRandomEngine);
    }
    Object localObject = unknown_N_compute_B_and_K(paramDouble1, paramDouble2, paramInt);
    long l1 = localObject[0];
    long l3 = localObject[1];
    long l5 = localObject[2];
    double d = -1.0D;
    if (localObject[3] == 1L) {
      d = paramDouble1;
    }
    if (l1 == 1L) {
      return new ExactDoubleQuantileFinder();
    }
    return new UnknownDoubleQuantileEstimator((int)l1, (int)l3, (int)l5, d, paramRandomEngine);
  }
  
  public static DoubleArrayList newEquiDepthPhis(int paramInt)
  {
    DoubleArrayList localDoubleArrayList = new DoubleArrayList(paramInt - 1);
    for (int i = 1; i <= paramInt - 1; i++) {
      localDoubleArrayList.add(i / paramInt);
    }
    return localDoubleArrayList;
  }
  
  public static long[] unknown_N_compute_B_and_K(double paramDouble1, double paramDouble2, int paramInt)
  {
    return unknown_N_compute_B_and_K_raw(paramDouble1, paramDouble2, paramInt);
  }
  
  protected static long[] unknown_N_compute_B_and_K_raw(double paramDouble1, double paramDouble2, int paramInt)
  {
    long[] arrayOfLong1;
    if (paramDouble1 <= 0.0D)
    {
      arrayOfLong1 = new long[4];
      arrayOfLong1[0] = 1L;
      arrayOfLong1[1] = 9223372036854775807L;
      arrayOfLong1[2] = 9223372036854775807L;
      arrayOfLong1[3] = 0L;
      return arrayOfLong1;
    }
    if ((paramDouble1 >= 1.0D) || (paramDouble2 >= 1.0D))
    {
      arrayOfLong1 = new long[4];
      arrayOfLong1[0] = 2L;
      arrayOfLong1[1] = 1L;
      arrayOfLong1[2] = 3L;
      arrayOfLong1[3] = 0L;
      return arrayOfLong1;
    }
    if (paramDouble2 <= 0.0D)
    {
      arrayOfLong1 = new long[4];
      arrayOfLong1[0] = 1L;
      arrayOfLong1[1] = 9223372036854775807L;
      arrayOfLong1[2] = 9223372036854775807L;
      arrayOfLong1[3] = 0L;
      return arrayOfLong1;
    }
    int i = 50;
    int j = 50;
    int k = 50;
    int m = 2;
    long l1 = 9223372036854775807L;
    long l2 = 9223372036854775807L;
    long l3 = 9223372036854775807L;
    long l4 = 9223372036854775807L;
    double d1 = Math.pow(2.0D, k);
    double d2 = Math.log(2.0D / (paramDouble2 / paramInt)) / (2.0D * paramDouble1 * paramDouble1);
    while ((l1 == 9223372036854775807L) && (m-- > 0))
    {
      for (int n = 2; n <= i; n++) {
        for (int i1 = 2; i1 <= j; i1++)
        {
          double d3 = Arithmetic.binomial(n + i1 - 2, i1 - 1);
          double d4 = Arithmetic.binomial(n + i1 - 3, i1 - 1);
          double d5 = d2 / Math.min(d3, 8.0D * d4 / 3.0D);
          double d6 = d3 / d4;
          double d7 = (d6 - 2.0D) * (k - 2.0D) / (d6 + d1 - 2.0D);
          double d8 = (i1 + 3 + d7) / (2.0D * paramDouble1);
          double d9 = d5 * d5 + 4.0D * d5 * d8;
          if (d9 >= 0.0D)
          {
            double d10 = Math.sqrt(d9);
            double d11 = (d5 + 2.0D * d8 + d10) / (2.0D * d8);
            double d12 = (d5 + 2.0D * d8 - d10) / (2.0D * d8);
            int i2 = 0;
            int i3 = 0;
            if ((0.0D < d11) && (d11 < 1.0D)) {
              i2 = 1;
            }
            if ((0.0D < d12) && (d12 < 1.0D)) {
              i3 = 1;
            }
            if ((i2 != 0) || (i3 != 0))
            {
              double d13 = d11;
              if ((i2 != 0) && (i3 != 0)) {
                d13 = Math.max(d11, d12);
              } else if (i3 != 0) {
                d13 = d12;
              }
              long l5 = Math.ceil(Math.max(d8 / d13, (i1 + 1) / (2.0D * paramDouble1)));
              if (l5 > 0L)
              {
                long l6 = n * l5;
                if (l6 < l4)
                {
                  l2 = l5;
                  l1 = n;
                  l3 = i1;
                  l4 = l6;
                }
              }
            }
          }
        }
      }
      if (l1 == 9223372036854775807L)
      {
        System.out.println("Warning: Computing b and k looks like a lot of work!");
        i *= 2;
        j *= 2;
        k *= 2;
      }
    }
    long[] arrayOfLong2 = new long[4];
    arrayOfLong2[3] = 0L;
    if (l1 == 9223372036854775807L)
    {
      arrayOfLong2[0] = 1L;
      arrayOfLong2[1] = 9223372036854775807L;
      arrayOfLong2[2] = 9223372036854775807L;
    }
    else
    {
      arrayOfLong2[0] = l1;
      arrayOfLong2[1] = l2;
      arrayOfLong2[2] = l3;
    }
    return arrayOfLong2;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.stat.quantile.QuantileFinderFactory
 * JD-Core Version:    0.7.0.1
 */
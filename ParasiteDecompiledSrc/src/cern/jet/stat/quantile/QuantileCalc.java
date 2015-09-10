package cern.jet.stat.quantile;

import java.io.PrintStream;

class QuantileCalc
{
  public static double binomial(long paramLong1, long paramLong2)
  {
    if ((paramLong2 == 0L) || (paramLong2 == paramLong1)) {
      return 1.0D;
    }
    if (paramLong2 > paramLong1 / 2.0D) {
      paramLong2 = paramLong1 - paramLong2;
    }
    double d = 1.0D;
    long l1 = paramLong1 - paramLong2 + 1L;
    long l2 = paramLong2;
    while (l2 > 0L) {
      d *= l1++ / l2--;
    }
    return d;
  }
  
  public static long ceiling(double paramDouble)
  {
    return Math.round(Math.ceil(paramDouble));
  }
  
  public static long[] known_N_compute_B_and_K(long paramLong, double paramDouble1, double paramDouble2, int paramInt, double[] paramArrayOfDouble)
  {
    if (paramDouble2 > 0.0D) {
      return known_N_compute_B_and_K_slow(paramLong, paramDouble1, paramDouble2, paramInt, paramArrayOfDouble);
    }
    paramArrayOfDouble[0] = 1.0D;
    return known_N_compute_B_and_K_quick(paramLong, paramDouble1);
  }
  
  protected static long[] known_N_compute_B_and_K_quick(long paramLong, double paramDouble)
  {
    if (paramDouble <= 0.0D)
    {
      long[] arrayOfLong1 = new long[2];
      arrayOfLong1[0] = 1L;
      arrayOfLong1[1] = paramLong;
      return arrayOfLong1;
    }
    double d1 = paramLong;
    double d2 = d1 * paramDouble * 2.0D;
    int[] arrayOfInt = new int[49];
    int k;
    for (int i = 2; i <= 50; i++)
    {
      for (j = 3; (j <= 50) && ((j - 2) * Math.round(binomial(i + j - 2, j - 1)) - Math.round(binomial(i + j - 3, j - 3)) + Math.round(binomial(i + j - 3, j - 2)) - d2 > 0.0D); j++) {}
      while ((j <= 50) && ((j - 2) * Math.round(binomial(i + j - 2, j - 1)) - Math.round(binomial(i + j - 3, j - 3)) + Math.round(binomial(i + j - 3, j - 2)) - d2 <= 0.0D)) {
        j++;
      }
      j--;
      if ((j >= 50) && ((j - 2) * Math.round(binomial(i + j - 2, j - 1)) - Math.round(binomial(i + j - 3, j - 3)) + Math.round(binomial(i + j - 3, j - 2)) - d2 > 0.0D)) {
        k = -2147483648;
      } else {
        k = j;
      }
      arrayOfInt[(i - 2)] = k;
    }
    long[] arrayOfLong2 = new long[49];
    for (int j = 2; j <= 50; j++)
    {
      k = arrayOfInt[(j - 2)];
      long l2 = 9223372036854775807L;
      if (k > -2147483648)
      {
        double d3 = Math.round(binomial(j + k - 2, k - 1));
        long l6 = ceiling(d1 / d3);
        if (l6 <= 9223372036854775807L) {
          l2 = l6;
        }
      }
      arrayOfLong2[(j - 2)] = l2;
    }
    long l1 = 9223372036854775807L;
    int m = -1;
    for (int n = 2; n <= 50; n++) {
      if (arrayOfLong2[(n - 2)] < 9223372036854775807L)
      {
        long l4 = n * arrayOfLong2[(n - 2)];
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
      l5 = arrayOfLong2[(m - 2)];
    }
    else
    {
      l3 = 1L;
      l5 = paramLong;
    }
    long[] arrayOfLong3 = new long[2];
    arrayOfLong3[0] = l3;
    arrayOfLong3[1] = l5;
    return arrayOfLong3;
  }
  
  protected static long[] known_N_compute_B_and_K_slow(long paramLong, double paramDouble1, double paramDouble2, int paramInt, double[] paramArrayOfDouble)
  {
    if (paramDouble1 <= 0.0D)
    {
      long[] arrayOfLong1 = new long[2];
      arrayOfLong1[0] = 1L;
      arrayOfLong1[1] = paramLong;
      paramArrayOfDouble[0] = 1.0D;
      return arrayOfLong1;
    }
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
        double d5 = binomial(l4 + l5 - 2L, l5 - 1L);
        long l6 = ceiling(d1 / d5);
        if ((l4 * l6 < l3) && ((l5 - 2L) * d5 - binomial(l4 + l5 - 3L, l5 - 3L) + binomial(l4 + l5 - 3L, l5 - 2L) <= d4))
        {
          l2 = l6;
          l1 = l4;
          l3 = l2 * l4;
          d2 = 1.0D;
        }
        if (paramDouble2 > 0.0D)
        {
          double d6 = (l5 - 2L) * binomial(l4 + l5 - 2L, l5 - 1L) - binomial(l4 + l5 - 3L, l5 - 3L) + binomial(l4 + l5 - 3L, l5 - 2L);
          double d7 = d3 / paramDouble1;
          double d8 = binomial(l4 + l5 - 2L, l5 - 1L);
          double d9 = d3 / (2.0D * paramDouble1 * paramDouble1);
          double d10 = 0.5D + 0.5D * Math.sqrt(1.0D + 4.0D * d6 / d7);
          long l7 = ceiling(d9 * d10 * d10 / d8);
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
    long[] arrayOfLong2 = new long[2];
    arrayOfLong2[0] = l1;
    arrayOfLong2[1] = l2;
    paramArrayOfDouble[0] = d2;
    return arrayOfLong2;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    test_B_and_K_Calculation(paramArrayOfString);
  }
  
  public static void test_B_and_K_Calculation(String[] paramArrayOfString)
  {
    boolean bool;
    if (paramArrayOfString == null) {
      bool = false;
    } else {
      bool = new Boolean(paramArrayOfString[0]).booleanValue();
    }
    int[] arrayOfInt = { 1, 1000 };
    long[] arrayOfLong1 = { 100000L, 1000000L, 10000000L, 1000000000L };
    double[] arrayOfDouble1 = { 0.0D, 0.001D, 0.0001D, 1.E-005D };
    double[] arrayOfDouble2 = { 0.0D, 0.1D, 0.05D, 0.01D, 0.005D, 0.001D, 1.0E-007D };
    if (!bool) {
      arrayOfLong1 = new long[] { 0L };
    }
    System.out.println("\n\n");
    if (bool) {
      System.out.println("Computing b's and k's for KNOWN N");
    } else {
      System.out.println("Computing b's and k's for UNKNOWN N");
    }
    System.out.println("mem [elements/1024]");
    System.out.println("***********************************");
    for (int i = 0; i < arrayOfInt.length; i++)
    {
      int j = arrayOfInt[i];
      System.out.println("------------------------------");
      System.out.println("computing for p = " + j);
      for (int k = 0; k < arrayOfLong1.length; k++)
      {
        long l1 = arrayOfLong1[k];
        System.out.println("   ------------------------------");
        System.out.println("   computing for N = " + l1);
        for (int m = 0; m < arrayOfDouble1.length; m++)
        {
          double d1 = arrayOfDouble1[m];
          System.out.println("      ------------------------------");
          System.out.println("      computing for delta = " + d1);
          for (int n = 0; n < arrayOfDouble2.length; n++)
          {
            double d2 = arrayOfDouble2[n];
            double[] arrayOfDouble3 = new double[1];
            long[] arrayOfLong2;
            if (bool) {
              arrayOfLong2 = known_N_compute_B_and_K(l1, d2, d1, j, arrayOfDouble3);
            } else {
              arrayOfLong2 = unknown_N_compute_B_and_K(d2, d1, j);
            }
            long l2 = arrayOfLong2[0];
            long l3 = arrayOfLong2[1];
            System.out.print("         (e,d,N,p)=(" + d2 + "," + d1 + "," + l1 + "," + j + ") --> ");
            System.out.print("(b,k,mem");
            if (bool) {
              System.out.print(",sampling");
            }
            System.out.print(")=(" + l2 + "," + l3 + "," + l2 * l3 / 1024L);
            if (bool) {
              System.out.print("," + arrayOfDouble3[0]);
            }
            System.out.println(")");
          }
        }
      }
    }
  }
  
  public static long[] unknown_N_compute_B_and_K(double paramDouble1, double paramDouble2, int paramInt)
  {
    if ((paramDouble1 <= 0.0D) || (paramDouble2 <= 0.0D))
    {
      long[] arrayOfLong1 = new long[3];
      arrayOfLong1[0] = 1L;
      arrayOfLong1[1] = 9223372036854775807L;
      arrayOfLong1[2] = 9223372036854775807L;
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
          double d3 = binomial(n + i1 - 2, i1 - 1);
          double d4 = binomial(n + i1 - 3, i1 - 1);
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
              long l5 = ceiling(Math.max(d8 / d13, (i1 + 1) / (2.0D * paramDouble1)));
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
    long[] arrayOfLong2 = new long[3];
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
 * Qualified Name:     cern.jet.stat.quantile.QuantileCalc
 * JD-Core Version:    0.7.0.1
 */
package cern.jet.stat.quantile;

import cern.colt.Timer;
import cern.colt.list.DoubleArrayList;
import cern.colt.list.IntArrayList;
import cern.jet.stat.Descriptive;
import java.io.PrintStream;

class QuantileFinderTest
{
  protected static IntArrayList binaryMultiSearch(DoubleArrayList paramDoubleArrayList, double paramDouble)
  {
    int i = paramDoubleArrayList.binarySearch(paramDouble);
    if (i < 0) {
      return null;
    }
    for (int j = i - 1; (j >= 0) && (paramDoubleArrayList.get(j) == paramDouble); j--) {}
    j++;
    for (int k = i + 1; (k < paramDoubleArrayList.size()) && (paramDoubleArrayList.get(k) == paramDouble); k++) {}
    k--;
    return new IntArrayList(new int[] { j, k });
  }
  
  public static double epsilon(int paramInt, double paramDouble1, double paramDouble2)
  {
    double d = paramInt;
    return Math.abs(paramDouble2 / d - paramDouble1);
  }
  
  public static double epsilon(DoubleArrayList paramDoubleArrayList, double paramDouble1, double paramDouble2)
  {
    double d = Descriptive.rankInterpolated(paramDoubleArrayList, paramDouble2);
    return epsilon(paramDoubleArrayList.size(), paramDouble1, d);
  }
  
  public static double epsilon(DoubleArrayList paramDoubleArrayList, DoubleQuantileFinder paramDoubleQuantileFinder, double paramDouble)
  {
    double d = paramDoubleQuantileFinder.quantileElements(new DoubleArrayList(new double[] { paramDouble })).get(0);
    return epsilon(paramDoubleArrayList, paramDouble, d);
  }
  
  public static void main(String[] paramArrayOfString)
  {
    testBestBandKCalculation(paramArrayOfString);
  }
  
  public static double observedEpsilonAtPhi(double paramDouble, ExactDoubleQuantileFinder paramExactDoubleQuantileFinder, DoubleQuantileFinder paramDoubleQuantileFinder)
  {
    int i = (int)paramExactDoubleQuantileFinder.size();
    int j = (int)Utils.epsilonCeiling(paramDouble * i) - 1;
    paramExactDoubleQuantileFinder.quantileElements(new DoubleArrayList(new double[] { paramDouble })).get(0);
    double d1 = paramDoubleQuantileFinder.quantileElements(new DoubleArrayList(new double[] { paramDouble })).get(0);
    IntArrayList localIntArrayList = binaryMultiSearch(paramExactDoubleQuantileFinder.buffer, d1);
    int k = localIntArrayList.get(0);
    int m = localIntArrayList.get(1);
    int n;
    if ((k <= j) && (j <= m)) {
      n = 0;
    } else if (k > j) {
      n = Math.abs(k - j);
    } else {
      n = Math.abs(j - m);
    }
    double d2 = n / i;
    return d2;
  }
  
  public static DoubleArrayList observedEpsilonsAtPhis(DoubleArrayList paramDoubleArrayList, ExactDoubleQuantileFinder paramExactDoubleQuantileFinder, DoubleQuantileFinder paramDoubleQuantileFinder, double paramDouble)
  {
    DoubleArrayList localDoubleArrayList = new DoubleArrayList(paramDoubleArrayList.size());
    int i = paramDoubleArrayList.size();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      double d = observedEpsilonAtPhi(paramDoubleArrayList.get(i), paramExactDoubleQuantileFinder, paramDoubleQuantileFinder);
      localDoubleArrayList.add(d);
      if (d > paramDouble) {
        System.out.println("Real epsilon = " + d + " is larger than desired by " + (d - paramDouble));
      }
    }
    return localDoubleArrayList;
  }
  
  public static void test()
  {
    String[] arrayOfString = new String[20];
    String str1 = "10000";
    arrayOfString[0] = str1;
    String str2 = "12";
    arrayOfString[1] = str2;
    String str3 = "2290";
    arrayOfString[2] = str3;
    String str4 = "log";
    arrayOfString[3] = str4;
    String str5 = "10";
    arrayOfString[4] = str5;
    String str6 = "exact";
    arrayOfString[5] = str6;
    String str7 = "shuffle";
    arrayOfString[6] = str7;
    String str8 = "0.001";
    arrayOfString[7] = str8;
    String str9 = "0.0001";
    arrayOfString[8] = str9;
    String str10 = "1";
    arrayOfString[9] = str10;
    String str11 = "-1";
    arrayOfString[10] = str11;
    testQuantileCalculation(arrayOfString);
  }
  
  public static void testBestBandKCalculation(String[] paramArrayOfString)
  {
    int[] arrayOfInt = { 100, 10000 };
    long[] arrayOfLong = { 9223372036854775807L, 1000000L, 10000000L, 100000000L };
    double[] arrayOfDouble1 = { 0.0D, 0.1D, 1.E-005D };
    double[] arrayOfDouble2 = { 0.0D, 0.1D, 0.01D, 0.001D, 0.0001D, 1.E-005D, 1.0E-006D };
    System.out.println("\n\n");
    System.out.println("mem [Math.round(elements/1000.0)]");
    System.out.println("***********************************");
    Timer localTimer = new Timer().start();
    for (int i = 0; i < arrayOfInt.length; i++)
    {
      int j = arrayOfInt[i];
      System.out.println("------------------------------");
      System.out.println("computing for p = " + j);
      for (int k = 0; k < arrayOfLong.length; k++)
      {
        long l1 = arrayOfLong[k];
        System.out.println("   ------------------------------");
        System.out.println("   computing for N = " + l1);
        for (int m = 0; m < arrayOfDouble2.length; m++)
        {
          double d1 = arrayOfDouble2[m];
          System.out.println("      ------------------------------");
          System.out.println("      computing for e = " + d1);
          for (int n = 0; n < arrayOfDouble1.length; n++)
          {
            double d2 = arrayOfDouble1[n];
            for (int i1 = 0; i1 < 2; i1++)
            {
              boolean bool;
              if (i1 == 0) {
                bool = true;
              } else {
                bool = false;
              }
              DoubleQuantileFinder localDoubleQuantileFinder = QuantileFinderFactory.newDoubleQuantileFinder(bool, l1, d1, d2, j, null);
              String str = bool ? "  known" : "unknown";
              long l2 = localDoubleQuantileFinder.totalMemory();
              if (l2 == 0L) {
                l2 = l1;
              }
              System.out.print("         (known, d)=(" + str + ", " + d2 + ") --> ");
              System.out.print("(MB,mem");
              System.out.print(")=(" + l2 * 8.0D / 1024.0D / 1024.0D + ",  " + l2 / 1000.0D + ",  " + Math.round(l2 * 8.0D / 1024.0D / 1024.0D));
              System.out.println(")");
            }
          }
        }
      }
    }
    localTimer.stop().display();
  }
  
  public static void testLocalVarDeclarationSpeed(int paramInt)
  {
    System.out.println("free=" + Runtime.getRuntime().freeMemory());
    System.out.println("total=" + Runtime.getRuntime().totalMemory());
    Timer localTimer = new Timer().start();
    for (int k = 0; k < paramInt; k++) {
      for (int j = 0; j < paramInt; j++)
      {
        Object localObject = null;
        int i = 10;
        double d = 1.0D;
      }
    }
    System.out.println(localTimer.stop());
    System.out.println("free=" + Runtime.getRuntime().freeMemory());
    System.out.println("total=" + Runtime.getRuntime().totalMemory());
  }
  
  public static void testQuantileCalculation(String[] paramArrayOfString)
  {
    int i = Integer.parseInt(paramArrayOfString[0]);
    int j = Integer.parseInt(paramArrayOfString[1]);
    int k = Integer.parseInt(paramArrayOfString[2]);
    int m = Integer.parseInt(paramArrayOfString[4]);
    boolean bool1 = paramArrayOfString[5].equals("exact");
    boolean bool2 = paramArrayOfString[6].equals("shuffle");
    double d1 = new Double(paramArrayOfString[7]).doubleValue();
    double d2 = new Double(paramArrayOfString[8]).doubleValue();
    int n = Integer.parseInt(paramArrayOfString[9]);
    long l = Long.parseLong(paramArrayOfString[10]);
    System.out.println("free=" + Runtime.getRuntime().freeMemory());
    System.out.println("total=" + Runtime.getRuntime().totalMemory());
    double[] arrayOfDouble = { 0.001D, 0.01D, 0.1D, 0.5D, 0.9D, 0.99D, 0.999D, 1.0D };
    Timer localTimer1 = new Timer();
    Timer localTimer2 = new Timer();
    DoubleQuantileFinder localDoubleQuantileFinder1 = QuantileFinderFactory.newDoubleQuantileFinder(false, l, d1, d2, n, null);
    System.out.println(localDoubleQuantileFinder1);
    DoubleQuantileFinder localDoubleQuantileFinder2 = QuantileFinderFactory.newDoubleQuantileFinder(false, -1L, 0.0D, d2, n, null);
    System.out.println(localDoubleQuantileFinder2);
    DoubleArrayList localDoubleArrayList1 = new DoubleArrayList(i);
    Object localObject;
    for (int i1 = 0; i1 < m; i1++)
    {
      localDoubleArrayList1.setSize(0);
      int i2 = i1 * i;
      localTimer2.start();
      for (int i3 = 0; i3 < i; i3++) {
        localDoubleArrayList1.add(i3 + i2);
      }
      localTimer2.stop();
      if (bool2)
      {
        localObject = new Timer().start();
        localDoubleArrayList1.shuffle();
        System.out.println("shuffling took ");
        ((Timer)localObject).stop().display();
      }
      localTimer1.start();
      localDoubleQuantileFinder1.addAllOf(localDoubleArrayList1);
      localTimer1.stop();
      if (bool1) {
        localDoubleQuantileFinder2.addAllOf(localDoubleArrayList1);
      }
    }
    System.out.println("list.add() took" + localTimer2);
    System.out.println("approxFinder.add() took" + localTimer1);
    localTimer1.reset().start();
    DoubleArrayList localDoubleArrayList2 = localDoubleQuantileFinder1.quantileElements(new DoubleArrayList(arrayOfDouble));
    localTimer1.stop().display();
    System.out.println("Phis=" + new DoubleArrayList(arrayOfDouble));
    System.out.println("ApproxQuantiles=" + localDoubleArrayList2);
    if (bool1)
    {
      System.out.println("Comparing with exact quantile computation...");
      localTimer1.reset().start();
      DoubleArrayList localDoubleArrayList3 = localDoubleQuantileFinder2.quantileElements(new DoubleArrayList(arrayOfDouble));
      localTimer1.stop().display();
      System.out.println("ExactQuantiles=" + localDoubleArrayList3);
      localObject = observedEpsilonsAtPhis(new DoubleArrayList(arrayOfDouble), (ExactDoubleQuantileFinder)localDoubleQuantileFinder2, localDoubleQuantileFinder1, d1);
      System.out.println("observedEpsilons=" + localObject);
      double d3 = 1000.0D;
      System.out.println("exact phi(" + d3 + ")=" + localDoubleQuantileFinder2.phi(d3));
      System.out.println("apprx phi(" + d3 + ")=" + localDoubleQuantileFinder1.phi(d3));
      System.out.println("exact elem(phi(" + d3 + "))=" + localDoubleQuantileFinder2.quantileElements(new DoubleArrayList(new double[] { localDoubleQuantileFinder2.phi(d3) })));
      System.out.println("apprx elem(phi(" + d3 + "))=" + localDoubleQuantileFinder1.quantileElements(new DoubleArrayList(new double[] { localDoubleQuantileFinder1.phi(d3) })));
    }
  }
  
  public static void testRank()
  {
    DoubleArrayList localDoubleArrayList = new DoubleArrayList(new double[] { 1.0D, 5.0D, 5.0D, 5.0D, 7.0D, 10.0D });
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.stat.quantile.QuantileFinderTest
 * JD-Core Version:    0.7.0.1
 */
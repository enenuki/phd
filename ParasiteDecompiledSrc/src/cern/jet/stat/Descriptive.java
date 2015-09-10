package cern.jet.stat;

import cern.colt.list.DoubleArrayList;
import cern.colt.list.IntArrayList;
import cern.jet.math.Arithmetic;

public class Descriptive
{
  public static double autoCorrelation(DoubleArrayList paramDoubleArrayList, int paramInt, double paramDouble1, double paramDouble2)
  {
    int i = paramDoubleArrayList.size();
    if (paramInt >= i) {
      throw new IllegalArgumentException("Lag is too large");
    }
    double[] arrayOfDouble = paramDoubleArrayList.elements();
    double d = 0.0D;
    for (int j = paramInt; j < i; j++) {
      d += (arrayOfDouble[j] - paramDouble1) * (arrayOfDouble[(j - paramInt)] - paramDouble1);
    }
    return d / (i - paramInt) / paramDouble2;
  }
  
  protected static void checkRangeFromTo(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt2 == paramInt1 - 1) {
      return;
    }
    if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (paramInt2 >= paramInt3)) {
      throw new IndexOutOfBoundsException("from: " + paramInt1 + ", to: " + paramInt2 + ", size=" + paramInt3);
    }
  }
  
  public static double correlation(DoubleArrayList paramDoubleArrayList1, double paramDouble1, DoubleArrayList paramDoubleArrayList2, double paramDouble2)
  {
    return covariance(paramDoubleArrayList1, paramDoubleArrayList2) / (paramDouble1 * paramDouble2);
  }
  
  public static double covariance(DoubleArrayList paramDoubleArrayList1, DoubleArrayList paramDoubleArrayList2)
  {
    int i = paramDoubleArrayList1.size();
    if ((i != paramDoubleArrayList2.size()) || (i == 0)) {
      throw new IllegalArgumentException();
    }
    double[] arrayOfDouble1 = paramDoubleArrayList1.elements();
    double[] arrayOfDouble2 = paramDoubleArrayList2.elements();
    double d1 = arrayOfDouble1[0];
    double d2 = arrayOfDouble2[0];
    double d3 = 0.0D;
    for (int j = 1; j < i; j++)
    {
      double d4 = arrayOfDouble1[j];
      double d5 = arrayOfDouble2[j];
      d1 += d4;
      d3 += (d4 - d1 / (j + 1)) * (d5 - d2 / j);
      d2 += d5;
    }
    return d3 / (i - 1);
  }
  
  private static double covariance2(DoubleArrayList paramDoubleArrayList1, DoubleArrayList paramDoubleArrayList2)
  {
    int i = paramDoubleArrayList1.size();
    double d1 = mean(paramDoubleArrayList1);
    double d2 = mean(paramDoubleArrayList2);
    double d3 = 0.0D;
    for (int j = 0; j < i; j++)
    {
      double d4 = paramDoubleArrayList1.get(j);
      double d5 = paramDoubleArrayList2.get(j);
      d3 += (d4 - d1) * (d5 - d2);
    }
    return d3 / (i - 1);
  }
  
  public static double durbinWatson(DoubleArrayList paramDoubleArrayList)
  {
    int i = paramDoubleArrayList.size();
    if (i < 2) {
      throw new IllegalArgumentException("data sequence must contain at least two values.");
    }
    double[] arrayOfDouble = paramDoubleArrayList.elements();
    double d1 = 0.0D;
    double d2 = 0.0D;
    d2 = arrayOfDouble[0] * arrayOfDouble[0];
    for (int j = 1; j < i; j++)
    {
      double d3 = arrayOfDouble[j] - arrayOfDouble[(j - 1)];
      d1 += d3 * d3;
      d2 += arrayOfDouble[j] * arrayOfDouble[j];
    }
    return d1 / d2;
  }
  
  public static void frequencies(DoubleArrayList paramDoubleArrayList1, DoubleArrayList paramDoubleArrayList2, IntArrayList paramIntArrayList)
  {
    paramDoubleArrayList2.clear();
    if (paramIntArrayList != null) {
      paramIntArrayList.clear();
    }
    double[] arrayOfDouble = paramDoubleArrayList1.elements();
    int i = paramDoubleArrayList1.size();
    int j = 0;
    while (j < i)
    {
      double d = arrayOfDouble[j];
      int k = j;
      do
      {
        j++;
      } while ((j < i) && (arrayOfDouble[j] == d));
      int m = j - k;
      paramDoubleArrayList2.add(d);
      if (paramIntArrayList != null) {
        paramIntArrayList.add(m);
      }
    }
  }
  
  public static double geometricMean(int paramInt, double paramDouble)
  {
    return Math.exp(paramDouble / paramInt);
  }
  
  public static double geometricMean(DoubleArrayList paramDoubleArrayList)
  {
    return geometricMean(paramDoubleArrayList.size(), sumOfLogarithms(paramDoubleArrayList, 0, paramDoubleArrayList.size() - 1));
  }
  
  public static double harmonicMean(int paramInt, double paramDouble)
  {
    return paramInt / paramDouble;
  }
  
  public static void incrementalUpdate(DoubleArrayList paramDoubleArrayList, int paramInt1, int paramInt2, double[] paramArrayOfDouble)
  {
    checkRangeFromTo(paramInt1, paramInt2, paramDoubleArrayList.size());
    double d1 = paramArrayOfDouble[0];
    double d2 = paramArrayOfDouble[1];
    double d3 = paramArrayOfDouble[2];
    double d4 = paramArrayOfDouble[3];
    double[] arrayOfDouble = paramDoubleArrayList.elements();
    while (paramInt1 <= paramInt2)
    {
      double d5 = arrayOfDouble[paramInt1];
      d3 += d5;
      d4 += d5 * d5;
      if (d5 < d1) {
        d1 = d5;
      }
      if (d5 > d2) {
        d2 = d5;
      }
      paramInt1++;
    }
    paramArrayOfDouble[0] = d1;
    paramArrayOfDouble[1] = d2;
    paramArrayOfDouble[2] = d3;
    paramArrayOfDouble[3] = d4;
  }
  
  public static void incrementalUpdateSumsOfPowers(DoubleArrayList paramDoubleArrayList, int paramInt1, int paramInt2, int paramInt3, int paramInt4, double[] paramArrayOfDouble)
  {
    int i = paramDoubleArrayList.size();
    double d1 = paramInt4 - paramInt3;
    if ((paramInt1 > i) || (d1 + 1 > paramArrayOfDouble.length)) {
      throw new IllegalArgumentException();
    }
    double d8;
    if (paramInt3 == 1)
    {
      double[] arrayOfDouble1;
      double d2;
      double d4;
      if (paramInt4 == 2)
      {
        arrayOfDouble1 = paramDoubleArrayList.elements();
        d2 = paramArrayOfDouble[0];
        d4 = paramArrayOfDouble[1];
        int m = paramInt1 - 1;
        for (;;)
        {
          m++;
          if (m > paramInt2) {
            break;
          }
          double d7 = arrayOfDouble1[m];
          d2 += d7;
          d4 += d7 * d7;
        }
        paramArrayOfDouble[0] += d2;
        paramArrayOfDouble[1] += d4;
        return;
      }
      double d6;
      if (paramInt4 == 3)
      {
        arrayOfDouble1 = paramDoubleArrayList.elements();
        d2 = paramArrayOfDouble[0];
        d4 = paramArrayOfDouble[1];
        d6 = paramArrayOfDouble[2];
        int i1 = paramInt1 - 1;
        for (;;)
        {
          i1++;
          if (i1 > paramInt2) {
            break;
          }
          double d9 = arrayOfDouble1[i1];
          d2 += d9;
          d4 += d9 * d9;
          d6 += d9 * d9 * d9;
        }
        paramArrayOfDouble[0] += d2;
        paramArrayOfDouble[1] += d4;
        paramArrayOfDouble[2] += d6;
        return;
      }
      if (paramInt4 == 4)
      {
        arrayOfDouble1 = paramDoubleArrayList.elements();
        d2 = paramArrayOfDouble[0];
        d4 = paramArrayOfDouble[1];
        d6 = paramArrayOfDouble[2];
        d8 = paramArrayOfDouble[3];
        int i2 = paramInt1 - 1;
        for (;;)
        {
          i2++;
          if (i2 > paramInt2) {
            break;
          }
          double d10 = arrayOfDouble1[i2];
          d2 += d10;
          d4 += d10 * d10;
          d6 += d10 * d10 * d10;
          d8 += d10 * d10 * d10 * d10;
        }
        paramArrayOfDouble[0] += d2;
        paramArrayOfDouble[1] += d4;
        paramArrayOfDouble[2] += d6;
        paramArrayOfDouble[3] += d8;
        return;
      }
    }
    if ((paramInt3 == paramInt4) || ((paramInt3 >= -1) && (paramInt4 <= 5)))
    {
      for (int j = paramInt3; j <= paramInt4; j++) {
        paramArrayOfDouble[(j - paramInt3)] += sumOfPowerDeviations(paramDoubleArrayList, j, 0.0D, paramInt1, paramInt2);
      }
      return;
    }
    double[] arrayOfDouble2 = paramDoubleArrayList.elements();
    int k = paramInt1 - 1;
    for (;;)
    {
      k++;
      if (k > paramInt2) {
        break;
      }
      double d3 = arrayOfDouble2[k];
      double d5 = Math.pow(d3, paramInt3);
      int n = 0;
      d8 = d1;
      for (;;)
      {
        d8--;
        if (d8 < 0) {
          break;
        }
        paramArrayOfDouble[(n++)] += d5;
        d5 *= d3;
      }
      paramArrayOfDouble[n] += d5;
    }
  }
  
  public static void incrementalWeightedUpdate(DoubleArrayList paramDoubleArrayList1, DoubleArrayList paramDoubleArrayList2, int paramInt1, int paramInt2, double[] paramArrayOfDouble)
  {
    int i = paramDoubleArrayList1.size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    if (i != paramDoubleArrayList2.size()) {
      throw new IllegalArgumentException("from=" + paramInt1 + ", to=" + paramInt2 + ", data.size()=" + i + ", weights.size()=" + paramDoubleArrayList2.size());
    }
    double d1 = paramArrayOfDouble[0];
    double d2 = paramArrayOfDouble[1];
    double[] arrayOfDouble1 = paramDoubleArrayList1.elements();
    double[] arrayOfDouble2 = paramDoubleArrayList2.elements();
    int j = paramInt1 - 1;
    for (;;)
    {
      j++;
      if (j > paramInt2) {
        break;
      }
      double d3 = arrayOfDouble1[j];
      double d4 = arrayOfDouble2[j];
      double d5 = d3 * d4;
      d1 += d5;
      d2 += d3 * d5;
    }
    paramArrayOfDouble[0] = d1;
    paramArrayOfDouble[1] = d2;
  }
  
  public static double kurtosis(double paramDouble1, double paramDouble2)
  {
    return -3.0D + paramDouble1 / (paramDouble2 * paramDouble2 * paramDouble2 * paramDouble2);
  }
  
  public static double kurtosis(DoubleArrayList paramDoubleArrayList, double paramDouble1, double paramDouble2)
  {
    return kurtosis(moment(paramDoubleArrayList, 4, paramDouble1), paramDouble2);
  }
  
  public static double lag1(DoubleArrayList paramDoubleArrayList, double paramDouble)
  {
    int i = paramDoubleArrayList.size();
    double[] arrayOfDouble = paramDoubleArrayList.elements();
    double d2 = 0.0D;
    double d3 = (arrayOfDouble[0] - paramDouble) * (arrayOfDouble[0] - paramDouble);
    for (int j = 1; j < i; j++)
    {
      double d4 = arrayOfDouble[(j - 1)] - paramDouble;
      double d5 = arrayOfDouble[j] - paramDouble;
      d2 += (d4 * d5 - d2) / (j + 1);
      d3 += (d5 * d5 - d3) / (j + 1);
    }
    double d1 = d2 / d3;
    return d1;
  }
  
  public static double max(DoubleArrayList paramDoubleArrayList)
  {
    int i = paramDoubleArrayList.size();
    if (i == 0) {
      throw new IllegalArgumentException();
    }
    double[] arrayOfDouble = paramDoubleArrayList.elements();
    double d = arrayOfDouble[(i - 1)];
    int j = i - 1;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      if (arrayOfDouble[j] > d) {
        d = arrayOfDouble[j];
      }
    }
    return d;
  }
  
  public static double mean(DoubleArrayList paramDoubleArrayList)
  {
    return sum(paramDoubleArrayList) / paramDoubleArrayList.size();
  }
  
  public static double meanDeviation(DoubleArrayList paramDoubleArrayList, double paramDouble)
  {
    double[] arrayOfDouble = paramDoubleArrayList.elements();
    int i = paramDoubleArrayList.size();
    double d = 0.0D;
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      d += Math.abs(arrayOfDouble[j] - paramDouble);
    }
    return d / i;
  }
  
  public static double median(DoubleArrayList paramDoubleArrayList)
  {
    return quantile(paramDoubleArrayList, 0.5D);
  }
  
  public static double min(DoubleArrayList paramDoubleArrayList)
  {
    int i = paramDoubleArrayList.size();
    if (i == 0) {
      throw new IllegalArgumentException();
    }
    double[] arrayOfDouble = paramDoubleArrayList.elements();
    double d = arrayOfDouble[(i - 1)];
    int j = i - 1;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      if (arrayOfDouble[j] < d) {
        d = arrayOfDouble[j];
      }
    }
    return d;
  }
  
  public static double moment(int paramInt1, double paramDouble, int paramInt2, double[] paramArrayOfDouble)
  {
    double d1 = 0.0D;
    int i = 1;
    for (int j = 0; j <= paramInt1; j++)
    {
      double d2;
      if (j == 0) {
        d2 = 1.0D;
      } else if (j == 1) {
        d2 = paramDouble;
      } else if (j == 2) {
        d2 = paramDouble * paramDouble;
      } else if (j == 3) {
        d2 = paramDouble * paramDouble * paramDouble;
      } else {
        d2 = Math.pow(paramDouble, j);
      }
      d1 += i * Arithmetic.binomial(paramInt1, j) * d2 * paramArrayOfDouble[(paramInt1 - j)];
      i = -i;
    }
    return d1 / paramInt2;
  }
  
  public static double moment(DoubleArrayList paramDoubleArrayList, int paramInt, double paramDouble)
  {
    return sumOfPowerDeviations(paramDoubleArrayList, paramInt, paramDouble) / paramDoubleArrayList.size();
  }
  
  public static double pooledMean(int paramInt1, double paramDouble1, int paramInt2, double paramDouble2)
  {
    return (paramInt1 * paramDouble1 + paramInt2 * paramDouble2) / (paramInt1 + paramInt2);
  }
  
  public static double pooledVariance(int paramInt1, double paramDouble1, int paramInt2, double paramDouble2)
  {
    return (paramInt1 * paramDouble1 + paramInt2 * paramDouble2) / (paramInt1 + paramInt2);
  }
  
  public static double product(int paramInt, double paramDouble)
  {
    return Math.pow(Math.exp(paramDouble / paramInt), paramInt);
  }
  
  public static double product(DoubleArrayList paramDoubleArrayList)
  {
    int i = paramDoubleArrayList.size();
    double[] arrayOfDouble = paramDoubleArrayList.elements();
    double d = 1.0D;
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      d *= arrayOfDouble[j];
    }
    return d;
  }
  
  public static double quantile(DoubleArrayList paramDoubleArrayList, double paramDouble)
  {
    double[] arrayOfDouble = paramDoubleArrayList.elements();
    int i = paramDoubleArrayList.size();
    double d1 = paramDouble * (i - 1);
    int j = (int)d1;
    double d2 = d1 - j;
    if (i == 0) {
      return 0.0D;
    }
    double d3;
    if (j == i - 1) {
      d3 = arrayOfDouble[j];
    } else {
      d3 = (1.0D - d2) * arrayOfDouble[j] + d2 * arrayOfDouble[(j + 1)];
    }
    return d3;
  }
  
  public static double quantileInverse(DoubleArrayList paramDoubleArrayList, double paramDouble)
  {
    return rankInterpolated(paramDoubleArrayList, paramDouble) / paramDoubleArrayList.size();
  }
  
  public static DoubleArrayList quantiles(DoubleArrayList paramDoubleArrayList1, DoubleArrayList paramDoubleArrayList2)
  {
    int i = paramDoubleArrayList2.size();
    DoubleArrayList localDoubleArrayList = new DoubleArrayList(i);
    for (int j = 0; j < i; j++) {
      localDoubleArrayList.add(quantile(paramDoubleArrayList1, paramDoubleArrayList2.get(j)));
    }
    return localDoubleArrayList;
  }
  
  public static double rankInterpolated(DoubleArrayList paramDoubleArrayList, double paramDouble)
  {
    int i = paramDoubleArrayList.binarySearch(paramDouble);
    if (i >= 0)
    {
      j = i + 1;
      int k = paramDoubleArrayList.size();
      while ((j < k) && (paramDoubleArrayList.get(j) == paramDouble)) {
        j++;
      }
      return j;
    }
    int j = -i - 1;
    if ((j == 0) || (j == paramDoubleArrayList.size())) {
      return j;
    }
    double d1 = paramDoubleArrayList.get(j - 1);
    double d2 = paramDoubleArrayList.get(j);
    double d3 = (paramDouble - d1) / (d2 - d1);
    return j + d3;
  }
  
  public static double rms(int paramInt, double paramDouble)
  {
    return Math.sqrt(paramDouble / paramInt);
  }
  
  public static double sampleKurtosis(int paramInt, double paramDouble1, double paramDouble2)
  {
    int i = paramInt;
    double d1 = paramDouble2;
    double d2 = paramDouble1 * i;
    return d2 * i * (i + 1) / ((i - 1) * (i - 2) * (i - 3) * d1 * d1) - 3.0D * (i - 1) * (i - 1) / ((i - 2) * (i - 3));
  }
  
  public static double sampleKurtosis(DoubleArrayList paramDoubleArrayList, double paramDouble1, double paramDouble2)
  {
    return sampleKurtosis(paramDoubleArrayList.size(), moment(paramDoubleArrayList, 4, paramDouble1), paramDouble2);
  }
  
  public static double sampleKurtosisStandardError(int paramInt)
  {
    int i = paramInt;
    return Math.sqrt(24.0D * i * (i - 1) * (i - 1) / ((i - 3) * (i - 2) * (i + 3) * (i + 5)));
  }
  
  public static double sampleSkew(int paramInt, double paramDouble1, double paramDouble2)
  {
    int i = paramInt;
    double d1 = Math.sqrt(paramDouble2);
    double d2 = paramDouble1 * i;
    return i * d2 / ((i - 1) * (i - 2) * d1 * d1 * d1);
  }
  
  public static double sampleSkew(DoubleArrayList paramDoubleArrayList, double paramDouble1, double paramDouble2)
  {
    return sampleSkew(paramDoubleArrayList.size(), moment(paramDoubleArrayList, 3, paramDouble1), paramDouble2);
  }
  
  public static double sampleSkewStandardError(int paramInt)
  {
    int i = paramInt;
    return Math.sqrt(6.0D * i * (i - 1) / ((i - 2) * (i + 1) * (i + 3)));
  }
  
  public static double sampleStandardDeviation(int paramInt, double paramDouble)
  {
    int i = paramInt;
    double d1 = Math.sqrt(paramDouble);
    double d2;
    if (i > 30) {
      d2 = 1.0D + 1.0D / (4 * (i - 1));
    } else {
      d2 = Math.sqrt((i - 1) * 0.5D) * Gamma.gamma((i - 1) * 0.5D) / Gamma.gamma(i * 0.5D);
    }
    return d2 * d1;
  }
  
  public static double sampleVariance(int paramInt, double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 / paramInt;
    return (paramDouble2 - d * paramDouble1) / (paramInt - 1);
  }
  
  public static double sampleVariance(DoubleArrayList paramDoubleArrayList, double paramDouble)
  {
    double[] arrayOfDouble = paramDoubleArrayList.elements();
    int i = paramDoubleArrayList.size();
    double d1 = 0.0D;
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      double d2 = arrayOfDouble[j] - paramDouble;
      d1 += d2 * d2;
    }
    return d1 / (i - 1);
  }
  
  public static double sampleWeightedVariance(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    return (paramDouble3 - paramDouble2 * paramDouble2 / paramDouble1) / (paramDouble1 - 1.0D);
  }
  
  public static double skew(double paramDouble1, double paramDouble2)
  {
    return paramDouble1 / (paramDouble2 * paramDouble2 * paramDouble2);
  }
  
  public static double skew(DoubleArrayList paramDoubleArrayList, double paramDouble1, double paramDouble2)
  {
    return skew(moment(paramDoubleArrayList, 3, paramDouble1), paramDouble2);
  }
  
  public static DoubleArrayList[] split(DoubleArrayList paramDoubleArrayList1, DoubleArrayList paramDoubleArrayList2)
  {
    int i = paramDoubleArrayList2.size() + 1;
    DoubleArrayList[] arrayOfDoubleArrayList = new DoubleArrayList[i];
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfDoubleArrayList[j] = new DoubleArrayList();
    }
    j = paramDoubleArrayList1.size();
    int k = 0;
    for (int m = 0; (k < j) && (m < i - 1); m++)
    {
      double d = paramDoubleArrayList2.get(m);
      int n = paramDoubleArrayList1.binarySearch(d);
      if (n < 0)
      {
        int i1 = -n - 1;
        arrayOfDoubleArrayList[m].addAllOfFromTo(paramDoubleArrayList1, k, i1 - 1);
        k = i1;
      }
      else
      {
        do
        {
          n--;
        } while ((n >= 0) && (paramDoubleArrayList1.get(n) == d));
        arrayOfDoubleArrayList[m].addAllOfFromTo(paramDoubleArrayList1, k, n);
        k = n + 1;
      }
    }
    arrayOfDoubleArrayList[(i - 1)].addAllOfFromTo(paramDoubleArrayList1, k, paramDoubleArrayList1.size() - 1);
    return arrayOfDoubleArrayList;
  }
  
  public static double standardDeviation(double paramDouble)
  {
    return Math.sqrt(paramDouble);
  }
  
  public static double standardError(int paramInt, double paramDouble)
  {
    return Math.sqrt(paramDouble / paramInt);
  }
  
  public static void standardize(DoubleArrayList paramDoubleArrayList, double paramDouble1, double paramDouble2)
  {
    double[] arrayOfDouble = paramDoubleArrayList.elements();
    int i = paramDoubleArrayList.size();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfDouble[i] = ((arrayOfDouble[i] - paramDouble1) / paramDouble2);
    }
  }
  
  public static double sum(DoubleArrayList paramDoubleArrayList)
  {
    return sumOfPowerDeviations(paramDoubleArrayList, 1, 0.0D);
  }
  
  public static double sumOfInversions(DoubleArrayList paramDoubleArrayList, int paramInt1, int paramInt2)
  {
    return sumOfPowerDeviations(paramDoubleArrayList, -1, 0.0D, paramInt1, paramInt2);
  }
  
  public static double sumOfLogarithms(DoubleArrayList paramDoubleArrayList, int paramInt1, int paramInt2)
  {
    double[] arrayOfDouble = paramDoubleArrayList.elements();
    double d = 0.0D;
    int i = paramInt1 - 1;
    for (;;)
    {
      i++;
      if (i > paramInt2) {
        break;
      }
      d += Math.log(arrayOfDouble[i]);
    }
    return d;
  }
  
  public static double sumOfPowerDeviations(DoubleArrayList paramDoubleArrayList, int paramInt, double paramDouble)
  {
    return sumOfPowerDeviations(paramDoubleArrayList, paramInt, paramDouble, 0, paramDoubleArrayList.size() - 1);
  }
  
  public static double sumOfPowerDeviations(DoubleArrayList paramDoubleArrayList, int paramInt1, double paramDouble, int paramInt2, int paramInt3)
  {
    double[] arrayOfDouble = paramDoubleArrayList.elements();
    double d1 = 0.0D;
    int i;
    double d2;
    switch (paramInt1)
    {
    case -2: 
      if (paramDouble == 0.0D)
      {
        i = paramInt2 - 1;
        for (;;)
        {
          i++;
          if (i > paramInt3) {
            break;
          }
          d2 = arrayOfDouble[i];
          d1 += 1.0D / (d2 * d2);
        }
      }
      i = paramInt2 - 1;
      for (;;)
      {
        i++;
        if (i > paramInt3) {
          break;
        }
        d2 = arrayOfDouble[i] - paramDouble;
        d1 += 1.0D / (d2 * d2);
      }
    case -1: 
      if (paramDouble == 0.0D)
      {
        i = paramInt2 - 1;
        for (;;)
        {
          i++;
          if (i > paramInt3) {
            break;
          }
          d1 += 1.0D / arrayOfDouble[i];
        }
      }
      i = paramInt2 - 1;
      for (;;)
      {
        i++;
        if (i > paramInt3) {
          break;
        }
        d1 += 1.0D / (arrayOfDouble[i] - paramDouble);
      }
    case 0: 
      d1 += paramInt3 - paramInt2 + 1;
      break;
    case 1: 
      if (paramDouble == 0.0D)
      {
        i = paramInt2 - 1;
        for (;;)
        {
          i++;
          if (i > paramInt3) {
            break;
          }
          d1 += arrayOfDouble[i];
        }
      }
      i = paramInt2 - 1;
      for (;;)
      {
        i++;
        if (i > paramInt3) {
          break;
        }
        d1 += arrayOfDouble[i] - paramDouble;
      }
    case 2: 
      if (paramDouble == 0.0D)
      {
        i = paramInt2 - 1;
        for (;;)
        {
          i++;
          if (i > paramInt3) {
            break;
          }
          d2 = arrayOfDouble[i];
          d1 += d2 * d2;
        }
      }
      i = paramInt2 - 1;
      for (;;)
      {
        i++;
        if (i > paramInt3) {
          break;
        }
        d2 = arrayOfDouble[i] - paramDouble;
        d1 += d2 * d2;
      }
    case 3: 
      if (paramDouble == 0.0D)
      {
        i = paramInt2 - 1;
        for (;;)
        {
          i++;
          if (i > paramInt3) {
            break;
          }
          d2 = arrayOfDouble[i];
          d1 += d2 * d2 * d2;
        }
      }
      i = paramInt2 - 1;
      for (;;)
      {
        i++;
        if (i > paramInt3) {
          break;
        }
        d2 = arrayOfDouble[i] - paramDouble;
        d1 += d2 * d2 * d2;
      }
    case 4: 
      if (paramDouble == 0.0D)
      {
        i = paramInt2 - 1;
        for (;;)
        {
          i++;
          if (i > paramInt3) {
            break;
          }
          d2 = arrayOfDouble[i];
          d1 += d2 * d2 * d2 * d2;
        }
      }
      i = paramInt2 - 1;
      for (;;)
      {
        i++;
        if (i > paramInt3) {
          break;
        }
        d2 = arrayOfDouble[i] - paramDouble;
        d1 += d2 * d2 * d2 * d2;
      }
    case 5: 
      if (paramDouble == 0.0D)
      {
        i = paramInt2 - 1;
        for (;;)
        {
          i++;
          if (i > paramInt3) {
            break;
          }
          d2 = arrayOfDouble[i];
          d1 += d2 * d2 * d2 * d2 * d2;
        }
      }
      i = paramInt2 - 1;
      for (;;)
      {
        i++;
        if (i > paramInt3) {
          break;
        }
        d2 = arrayOfDouble[i] - paramDouble;
        d1 += d2 * d2 * d2 * d2 * d2;
      }
    default: 
      i = paramInt2 - 1;
      for (;;)
      {
        i++;
        if (i > paramInt3) {
          break;
        }
        d1 += Math.pow(arrayOfDouble[i] - paramDouble, paramInt1);
      }
    }
    return d1;
  }
  
  public static double sumOfPowers(DoubleArrayList paramDoubleArrayList, int paramInt)
  {
    return sumOfPowerDeviations(paramDoubleArrayList, paramInt, 0.0D);
  }
  
  public static double sumOfSquaredDeviations(int paramInt, double paramDouble)
  {
    return paramDouble * (paramInt - 1);
  }
  
  public static double sumOfSquares(DoubleArrayList paramDoubleArrayList)
  {
    return sumOfPowerDeviations(paramDoubleArrayList, 2, 0.0D);
  }
  
  public static double trimmedMean(DoubleArrayList paramDoubleArrayList, double paramDouble, int paramInt1, int paramInt2)
  {
    int i = paramDoubleArrayList.size();
    if (i == 0) {
      throw new IllegalArgumentException("Empty data.");
    }
    if (paramInt1 + paramInt2 >= i) {
      throw new IllegalArgumentException("Not enough data.");
    }
    double[] arrayOfDouble = paramDoubleArrayList.elements();
    int j = i;
    for (int k = 0; k < paramInt1; k++) {
      paramDouble += (paramDouble - arrayOfDouble[k]) / --i;
    }
    for (k = 0; k < paramInt2; k++) {
      paramDouble += (paramDouble - arrayOfDouble[(j - 1 - k)]) / --i;
    }
    return paramDouble;
  }
  
  public static double variance(double paramDouble)
  {
    return paramDouble * paramDouble;
  }
  
  public static double variance(int paramInt, double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 / paramInt;
    return (paramDouble2 - d * paramDouble1) / paramInt;
  }
  
  public static double weightedMean(DoubleArrayList paramDoubleArrayList1, DoubleArrayList paramDoubleArrayList2)
  {
    int i = paramDoubleArrayList1.size();
    if ((i != paramDoubleArrayList2.size()) || (i == 0)) {
      throw new IllegalArgumentException();
    }
    double[] arrayOfDouble1 = paramDoubleArrayList1.elements();
    double[] arrayOfDouble2 = paramDoubleArrayList2.elements();
    double d1 = 0.0D;
    double d2 = 0.0D;
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      double d3 = arrayOfDouble2[j];
      d1 += arrayOfDouble1[j] * d3;
      d2 += d3;
    }
    return d1 / d2;
  }
  
  public static double weightedRMS(double paramDouble1, double paramDouble2)
  {
    return paramDouble1 / paramDouble2;
  }
  
  public static double winsorizedMean(DoubleArrayList paramDoubleArrayList, double paramDouble, int paramInt1, int paramInt2)
  {
    int i = paramDoubleArrayList.size();
    if (i == 0) {
      throw new IllegalArgumentException("Empty data.");
    }
    if (paramInt1 + paramInt2 >= i) {
      throw new IllegalArgumentException("Not enough data.");
    }
    double[] arrayOfDouble = paramDoubleArrayList.elements();
    double d1 = arrayOfDouble[paramInt1];
    for (int j = 0; j < paramInt1; j++) {
      paramDouble += (d1 - arrayOfDouble[j]) / i;
    }
    double d2 = arrayOfDouble[(i - 1 - paramInt2)];
    for (int k = 0; k < paramInt2; k++) {
      paramDouble += (d2 - arrayOfDouble[(i - 1 - k)]) / i;
    }
    return paramDouble;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.stat.Descriptive
 * JD-Core Version:    0.7.0.1
 */
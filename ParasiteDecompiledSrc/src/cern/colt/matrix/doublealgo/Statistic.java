package cern.colt.matrix.doublealgo;

import cern.colt.Timer;
import cern.colt.function.DoubleDoubleFunction;
import cern.colt.list.DoubleArrayList;
import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.DoubleMatrix3D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.jet.math.Functions;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;
import cern.jet.random.sampling.RandomSampler;
import cern.jet.stat.Descriptive;
import hep.aida.IHistogram1D;
import hep.aida.IHistogram2D;
import hep.aida.IHistogram3D;
import hep.aida.bin.BinFunction1D;
import hep.aida.bin.DynamicBin1D;
import hep.aida.ref.Histogram2D;
import hep.aida.ref.Histogram3D;
import hep.aida.ref.VariableAxis;
import java.io.PrintStream;

public class Statistic
{
  private static final Functions F = Functions.functions;
  public static final VectorVectorFunction EUCLID = new VectorVectorFunction()
  {
    public final double apply(DoubleMatrix1D paramAnonymousDoubleMatrix1D1, DoubleMatrix1D paramAnonymousDoubleMatrix1D2)
    {
      return Math.sqrt(paramAnonymousDoubleMatrix1D1.aggregate(paramAnonymousDoubleMatrix1D2, Functions.plus, Functions.chain(Functions.square, Functions.minus)));
    }
  };
  public static final VectorVectorFunction BRAY_CURTIS = new VectorVectorFunction()
  {
    public final double apply(DoubleMatrix1D paramAnonymousDoubleMatrix1D1, DoubleMatrix1D paramAnonymousDoubleMatrix1D2)
    {
      return paramAnonymousDoubleMatrix1D1.aggregate(paramAnonymousDoubleMatrix1D2, Functions.plus, Functions.chain(Functions.abs, Functions.minus)) / paramAnonymousDoubleMatrix1D1.aggregate(paramAnonymousDoubleMatrix1D2, Functions.plus, Functions.plus);
    }
  };
  public static final VectorVectorFunction CANBERRA = new VectorVectorFunction()
  {
    DoubleDoubleFunction fun = new Statistic.4(this);
    
    public final double apply(DoubleMatrix1D paramAnonymousDoubleMatrix1D1, DoubleMatrix1D paramAnonymousDoubleMatrix1D2)
    {
      return paramAnonymousDoubleMatrix1D1.aggregate(paramAnonymousDoubleMatrix1D2, Functions.plus, this.fun);
    }
  };
  public static final VectorVectorFunction MAXIMUM = new VectorVectorFunction()
  {
    public final double apply(DoubleMatrix1D paramAnonymousDoubleMatrix1D1, DoubleMatrix1D paramAnonymousDoubleMatrix1D2)
    {
      return paramAnonymousDoubleMatrix1D1.aggregate(paramAnonymousDoubleMatrix1D2, Functions.max, Functions.chain(Functions.abs, Functions.minus));
    }
  };
  public static final VectorVectorFunction MANHATTAN = new VectorVectorFunction()
  {
    public final double apply(DoubleMatrix1D paramAnonymousDoubleMatrix1D1, DoubleMatrix1D paramAnonymousDoubleMatrix1D2)
    {
      return paramAnonymousDoubleMatrix1D1.aggregate(paramAnonymousDoubleMatrix1D2, Functions.plus, Functions.chain(Functions.abs, Functions.minus));
    }
  };
  
  public static DoubleMatrix2D aggregate(DoubleMatrix2D paramDoubleMatrix2D1, BinFunction1D[] paramArrayOfBinFunction1D, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    DynamicBin1D localDynamicBin1D = new DynamicBin1D();
    double[] arrayOfDouble = new double[paramDoubleMatrix2D1.rows()];
    DoubleArrayList localDoubleArrayList = new DoubleArrayList(arrayOfDouble);
    int i = paramDoubleMatrix2D1.columns();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      paramDoubleMatrix2D1.viewColumn(i).toArray(arrayOfDouble);
      localDynamicBin1D.clear();
      localDynamicBin1D.addAllOf(localDoubleArrayList);
      int j = paramArrayOfBinFunction1D.length;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        paramDoubleMatrix2D2.set(j, i, paramArrayOfBinFunction1D[j].apply(localDynamicBin1D));
      }
    }
    return paramDoubleMatrix2D2;
  }
  
  public static DynamicBin1D bin(DoubleMatrix1D paramDoubleMatrix1D)
  {
    DynamicBin1D localDynamicBin1D = new DynamicBin1D();
    localDynamicBin1D.addAllOf(DoubleFactory1D.dense.toList(paramDoubleMatrix1D));
    return localDynamicBin1D;
  }
  
  public static DoubleMatrix2D correlation(DoubleMatrix2D paramDoubleMatrix2D)
  {
    int i = paramDoubleMatrix2D.columns();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      int j = i;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        double d1 = Math.sqrt(paramDoubleMatrix2D.getQuick(i, i));
        double d2 = Math.sqrt(paramDoubleMatrix2D.getQuick(j, j));
        double d3 = paramDoubleMatrix2D.getQuick(i, j);
        double d4 = d3 / (d1 * d2);
        paramDoubleMatrix2D.setQuick(i, j, d4);
        paramDoubleMatrix2D.setQuick(j, i, d4);
      }
    }
    i = paramDoubleMatrix2D.columns();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      paramDoubleMatrix2D.setQuick(i, i, 1.0D);
    }
    return paramDoubleMatrix2D;
  }
  
  public static DoubleMatrix2D covariance(DoubleMatrix2D paramDoubleMatrix2D)
  {
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    DenseDoubleMatrix2D localDenseDoubleMatrix2D = new DenseDoubleMatrix2D(j, j);
    double[] arrayOfDouble = new double[j];
    DoubleMatrix1D[] arrayOfDoubleMatrix1D = new DoubleMatrix1D[j];
    int k = j;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      arrayOfDoubleMatrix1D[k] = paramDoubleMatrix2D.viewColumn(k);
      arrayOfDouble[k] = arrayOfDoubleMatrix1D[k].zSum();
    }
    k = j;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      int m = k + 1;
      for (;;)
      {
        m--;
        if (m < 0) {
          break;
        }
        double d1 = arrayOfDoubleMatrix1D[k].zDotProduct(arrayOfDoubleMatrix1D[m]);
        double d2 = (d1 - arrayOfDouble[k] * arrayOfDouble[m] / i) / i;
        localDenseDoubleMatrix2D.setQuick(k, m, d2);
        localDenseDoubleMatrix2D.setQuick(m, k, d2);
      }
    }
    return localDenseDoubleMatrix2D;
  }
  
  public static IHistogram2D cube(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2, DoubleMatrix1D paramDoubleMatrix1D3)
  {
    if ((paramDoubleMatrix1D1.size() != paramDoubleMatrix1D2.size()) || (paramDoubleMatrix1D2.size() != paramDoubleMatrix1D3.size())) {
      throw new IllegalArgumentException("vectors must have same size");
    }
    double d = 1.E-009D;
    DoubleArrayList localDoubleArrayList1 = new DoubleArrayList();
    double[] arrayOfDouble = new double[paramDoubleMatrix1D1.size()];
    DoubleArrayList localDoubleArrayList2 = new DoubleArrayList(arrayOfDouble);
    paramDoubleMatrix1D1.toArray(arrayOfDouble);
    localDoubleArrayList2.sort();
    Descriptive.frequencies(localDoubleArrayList2, localDoubleArrayList1, null);
    if (localDoubleArrayList1.size() > 0) {
      localDoubleArrayList1.add(localDoubleArrayList1.get(localDoubleArrayList1.size() - 1) + d);
    }
    localDoubleArrayList1.trimToSize();
    VariableAxis localVariableAxis1 = new VariableAxis(localDoubleArrayList1.elements());
    paramDoubleMatrix1D2.toArray(arrayOfDouble);
    localDoubleArrayList2.sort();
    Descriptive.frequencies(localDoubleArrayList2, localDoubleArrayList1, null);
    if (localDoubleArrayList1.size() > 0) {
      localDoubleArrayList1.add(localDoubleArrayList1.get(localDoubleArrayList1.size() - 1) + d);
    }
    localDoubleArrayList1.trimToSize();
    VariableAxis localVariableAxis2 = new VariableAxis(localDoubleArrayList1.elements());
    Histogram2D localHistogram2D = new Histogram2D("Cube", localVariableAxis1, localVariableAxis2);
    return histogram(localHistogram2D, paramDoubleMatrix1D1, paramDoubleMatrix1D2, paramDoubleMatrix1D3);
  }
  
  public static IHistogram3D cube(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2, DoubleMatrix1D paramDoubleMatrix1D3, DoubleMatrix1D paramDoubleMatrix1D4)
  {
    if ((paramDoubleMatrix1D1.size() != paramDoubleMatrix1D2.size()) || (paramDoubleMatrix1D1.size() != paramDoubleMatrix1D3.size()) || (paramDoubleMatrix1D1.size() != paramDoubleMatrix1D4.size())) {
      throw new IllegalArgumentException("vectors must have same size");
    }
    double d = 1.E-009D;
    DoubleArrayList localDoubleArrayList1 = new DoubleArrayList();
    double[] arrayOfDouble = new double[paramDoubleMatrix1D1.size()];
    DoubleArrayList localDoubleArrayList2 = new DoubleArrayList(arrayOfDouble);
    paramDoubleMatrix1D1.toArray(arrayOfDouble);
    localDoubleArrayList2.sort();
    Descriptive.frequencies(localDoubleArrayList2, localDoubleArrayList1, null);
    if (localDoubleArrayList1.size() > 0) {
      localDoubleArrayList1.add(localDoubleArrayList1.get(localDoubleArrayList1.size() - 1) + d);
    }
    localDoubleArrayList1.trimToSize();
    VariableAxis localVariableAxis1 = new VariableAxis(localDoubleArrayList1.elements());
    paramDoubleMatrix1D2.toArray(arrayOfDouble);
    localDoubleArrayList2.sort();
    Descriptive.frequencies(localDoubleArrayList2, localDoubleArrayList1, null);
    if (localDoubleArrayList1.size() > 0) {
      localDoubleArrayList1.add(localDoubleArrayList1.get(localDoubleArrayList1.size() - 1) + d);
    }
    localDoubleArrayList1.trimToSize();
    VariableAxis localVariableAxis2 = new VariableAxis(localDoubleArrayList1.elements());
    paramDoubleMatrix1D3.toArray(arrayOfDouble);
    localDoubleArrayList2.sort();
    Descriptive.frequencies(localDoubleArrayList2, localDoubleArrayList1, null);
    if (localDoubleArrayList1.size() > 0) {
      localDoubleArrayList1.add(localDoubleArrayList1.get(localDoubleArrayList1.size() - 1) + d);
    }
    localDoubleArrayList1.trimToSize();
    VariableAxis localVariableAxis3 = new VariableAxis(localDoubleArrayList1.elements());
    Histogram3D localHistogram3D = new Histogram3D("Cube", localVariableAxis1, localVariableAxis2, localVariableAxis3);
    return histogram(localHistogram3D, paramDoubleMatrix1D1, paramDoubleMatrix1D2, paramDoubleMatrix1D3, paramDoubleMatrix1D4);
  }
  
  public static void demo1()
  {
    double[][] arrayOfDouble = { { 1.0D, 2.0D, 3.0D }, { 2.0D, 4.0D, 6.0D }, { 3.0D, 6.0D, 9.0D }, { 4.0D, -8.0D, -10.0D } };
    DoubleFactory2D localDoubleFactory2D = DoubleFactory2D.dense;
    DoubleMatrix2D localDoubleMatrix2D = localDoubleFactory2D.make(arrayOfDouble);
    System.out.println("\n\nmatrix=" + localDoubleMatrix2D);
    System.out.println("\ncovar1=" + covariance(localDoubleMatrix2D));
  }
  
  public static void demo2(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    System.out.println("\n\ninitializing...");
    DoubleFactory2D localDoubleFactory2D = DoubleFactory2D.dense;
    DoubleMatrix2D localDoubleMatrix2D1 = localDoubleFactory2D.ascending(paramInt1, paramInt2);
    System.out.println("benchmarking correlation...");
    Timer localTimer = new Timer().start();
    DoubleMatrix2D localDoubleMatrix2D2 = correlation(covariance(localDoubleMatrix2D1));
    localTimer.stop().display();
    if (paramBoolean)
    {
      System.out.println("printing result...");
      System.out.println(localDoubleMatrix2D2);
    }
    System.out.println("done.");
  }
  
  public static void demo3(VectorVectorFunction paramVectorVectorFunction)
  {
    double[][] arrayOfDouble = { { -0.9611052D, -0.25421095D }, { 0.4308269D, -0.69932648D }, { -1.2071029D, 0.6203059600000001D }, { 1.5345166D, 0.02135884D }, { -1.1341542D, 0.2038843D } };
    System.out.println("\n\ninitializing...");
    DoubleFactory2D localDoubleFactory2D = DoubleFactory2D.dense;
    DoubleMatrix2D localDoubleMatrix2D = localDoubleFactory2D.make(arrayOfDouble).viewDice();
    System.out.println("\nA=" + localDoubleMatrix2D.viewDice());
    System.out.println("\ndist=" + distance(localDoubleMatrix2D, paramVectorVectorFunction).viewDice());
  }
  
  public static DoubleMatrix2D distance(DoubleMatrix2D paramDoubleMatrix2D, VectorVectorFunction paramVectorVectorFunction)
  {
    int i = paramDoubleMatrix2D.columns();
    DenseDoubleMatrix2D localDenseDoubleMatrix2D = new DenseDoubleMatrix2D(i, i);
    DoubleMatrix1D[] arrayOfDoubleMatrix1D = new DoubleMatrix1D[i];
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfDoubleMatrix1D[j] = paramDoubleMatrix2D.viewColumn(j);
    }
    j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      int k = j;
      for (;;)
      {
        k--;
        if (k < 0) {
          break;
        }
        double d = paramVectorVectorFunction.apply(arrayOfDoubleMatrix1D[j], arrayOfDoubleMatrix1D[k]);
        localDenseDoubleMatrix2D.setQuick(j, k, d);
        localDenseDoubleMatrix2D.setQuick(k, j, d);
      }
    }
    return localDenseDoubleMatrix2D;
  }
  
  public static IHistogram1D histogram(IHistogram1D paramIHistogram1D, DoubleMatrix1D paramDoubleMatrix1D)
  {
    int i = paramDoubleMatrix1D.size();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      paramIHistogram1D.fill(paramDoubleMatrix1D.getQuick(i));
    }
    return paramIHistogram1D;
  }
  
  public static IHistogram2D histogram(IHistogram2D paramIHistogram2D, DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    if (paramDoubleMatrix1D1.size() != paramDoubleMatrix1D2.size()) {
      throw new IllegalArgumentException("vectors must have same size");
    }
    int i = paramDoubleMatrix1D1.size();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      paramIHistogram2D.fill(paramDoubleMatrix1D1.getQuick(i), paramDoubleMatrix1D2.getQuick(i));
    }
    return paramIHistogram2D;
  }
  
  public static IHistogram2D histogram(IHistogram2D paramIHistogram2D, DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2, DoubleMatrix1D paramDoubleMatrix1D3)
  {
    if ((paramDoubleMatrix1D1.size() != paramDoubleMatrix1D2.size()) || (paramDoubleMatrix1D2.size() != paramDoubleMatrix1D3.size())) {
      throw new IllegalArgumentException("vectors must have same size");
    }
    int i = paramDoubleMatrix1D1.size();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      paramIHistogram2D.fill(paramDoubleMatrix1D1.getQuick(i), paramDoubleMatrix1D2.getQuick(i), paramDoubleMatrix1D3.getQuick(i));
    }
    return paramIHistogram2D;
  }
  
  public static IHistogram3D histogram(IHistogram3D paramIHistogram3D, DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2, DoubleMatrix1D paramDoubleMatrix1D3, DoubleMatrix1D paramDoubleMatrix1D4)
  {
    if ((paramDoubleMatrix1D1.size() != paramDoubleMatrix1D2.size()) || (paramDoubleMatrix1D1.size() != paramDoubleMatrix1D3.size()) || (paramDoubleMatrix1D1.size() != paramDoubleMatrix1D4.size())) {
      throw new IllegalArgumentException("vectors must have same size");
    }
    int i = paramDoubleMatrix1D1.size();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      paramIHistogram3D.fill(paramDoubleMatrix1D1.getQuick(i), paramDoubleMatrix1D2.getQuick(i), paramDoubleMatrix1D3.getQuick(i), paramDoubleMatrix1D4.getQuick(i));
    }
    return paramIHistogram3D;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    int i = Integer.parseInt(paramArrayOfString[0]);
    int j = Integer.parseInt(paramArrayOfString[1]);
    boolean bool = paramArrayOfString[2].equals("print");
    demo2(i, j, bool);
  }
  
  public static DoubleMatrix1D viewSample(DoubleMatrix1D paramDoubleMatrix1D, double paramDouble, RandomEngine paramRandomEngine)
  {
    double d = 1.E-009D;
    if ((paramDouble < 0.0D - d) || (paramDouble > 1.0D + d)) {
      throw new IllegalArgumentException();
    }
    if (paramDouble < 0.0D) {
      paramDouble = 0.0D;
    }
    if (paramDouble > 1.0D) {
      paramDouble = 1.0D;
    }
    if (paramRandomEngine == null) {
      paramRandomEngine = new MersenneTwister((int)System.currentTimeMillis());
    }
    int i = (int)Math.round(paramDoubleMatrix1D.size() * paramDouble);
    int j = i;
    long[] arrayOfLong = new long[j];
    int k = i;
    int m = paramDoubleMatrix1D.size();
    RandomSampler.sample(k, m, k, 0L, arrayOfLong, 0, paramRandomEngine);
    int[] arrayOfInt = new int[k];
    for (int n = 0; n < k; n++) {
      arrayOfInt[n] = ((int)arrayOfLong[n]);
    }
    return paramDoubleMatrix1D.viewSelection(arrayOfInt);
  }
  
  public static DoubleMatrix2D viewSample(DoubleMatrix2D paramDoubleMatrix2D, double paramDouble1, double paramDouble2, RandomEngine paramRandomEngine)
  {
    double d = 1.E-009D;
    if ((paramDouble1 < 0.0D - d) || (paramDouble1 > 1.0D + d)) {
      throw new IllegalArgumentException();
    }
    if (paramDouble1 < 0.0D) {
      paramDouble1 = 0.0D;
    }
    if (paramDouble1 > 1.0D) {
      paramDouble1 = 1.0D;
    }
    if ((paramDouble2 < 0.0D - d) || (paramDouble2 > 1.0D + d)) {
      throw new IllegalArgumentException();
    }
    if (paramDouble2 < 0.0D) {
      paramDouble2 = 0.0D;
    }
    if (paramDouble2 > 1.0D) {
      paramDouble2 = 1.0D;
    }
    if (paramRandomEngine == null) {
      paramRandomEngine = new MersenneTwister((int)System.currentTimeMillis());
    }
    int i = (int)Math.round(paramDoubleMatrix2D.rows() * paramDouble1);
    int j = (int)Math.round(paramDoubleMatrix2D.columns() * paramDouble2);
    int k = Math.max(i, j);
    long[] arrayOfLong = new long[k];
    int m = i;
    int n = paramDoubleMatrix2D.rows();
    RandomSampler.sample(m, n, m, 0L, arrayOfLong, 0, paramRandomEngine);
    int[] arrayOfInt1 = new int[m];
    for (int i1 = 0; i1 < m; i1++) {
      arrayOfInt1[i1] = ((int)arrayOfLong[i1]);
    }
    m = j;
    n = paramDoubleMatrix2D.columns();
    RandomSampler.sample(m, n, m, 0L, arrayOfLong, 0, paramRandomEngine);
    int[] arrayOfInt2 = new int[m];
    for (int i2 = 0; i2 < m; i2++) {
      arrayOfInt2[i2] = ((int)arrayOfLong[i2]);
    }
    return paramDoubleMatrix2D.viewSelection(arrayOfInt1, arrayOfInt2);
  }
  
  public static DoubleMatrix3D viewSample(DoubleMatrix3D paramDoubleMatrix3D, double paramDouble1, double paramDouble2, double paramDouble3, RandomEngine paramRandomEngine)
  {
    double d = 1.E-009D;
    if ((paramDouble1 < 0.0D - d) || (paramDouble1 > 1.0D + d)) {
      throw new IllegalArgumentException();
    }
    if (paramDouble1 < 0.0D) {
      paramDouble1 = 0.0D;
    }
    if (paramDouble1 > 1.0D) {
      paramDouble1 = 1.0D;
    }
    if ((paramDouble2 < 0.0D - d) || (paramDouble2 > 1.0D + d)) {
      throw new IllegalArgumentException();
    }
    if (paramDouble2 < 0.0D) {
      paramDouble2 = 0.0D;
    }
    if (paramDouble2 > 1.0D) {
      paramDouble2 = 1.0D;
    }
    if ((paramDouble3 < 0.0D - d) || (paramDouble3 > 1.0D + d)) {
      throw new IllegalArgumentException();
    }
    if (paramDouble3 < 0.0D) {
      paramDouble3 = 0.0D;
    }
    if (paramDouble3 > 1.0D) {
      paramDouble3 = 1.0D;
    }
    if (paramRandomEngine == null) {
      paramRandomEngine = new MersenneTwister((int)System.currentTimeMillis());
    }
    int i = (int)Math.round(paramDoubleMatrix3D.slices() * paramDouble1);
    int j = (int)Math.round(paramDoubleMatrix3D.rows() * paramDouble2);
    int k = (int)Math.round(paramDoubleMatrix3D.columns() * paramDouble3);
    int m = Math.max(i, Math.max(j, k));
    long[] arrayOfLong = new long[m];
    int n = i;
    int i1 = paramDoubleMatrix3D.slices();
    RandomSampler.sample(n, i1, n, 0L, arrayOfLong, 0, paramRandomEngine);
    int[] arrayOfInt1 = new int[n];
    for (int i2 = 0; i2 < n; i2++) {
      arrayOfInt1[i2] = ((int)arrayOfLong[i2]);
    }
    n = j;
    i1 = paramDoubleMatrix3D.rows();
    RandomSampler.sample(n, i1, n, 0L, arrayOfLong, 0, paramRandomEngine);
    int[] arrayOfInt2 = new int[n];
    for (int i3 = 0; i3 < n; i3++) {
      arrayOfInt2[i3] = ((int)arrayOfLong[i3]);
    }
    n = k;
    i1 = paramDoubleMatrix3D.columns();
    RandomSampler.sample(n, i1, n, 0L, arrayOfLong, 0, paramRandomEngine);
    int[] arrayOfInt3 = new int[n];
    for (int i4 = 0; i4 < n; i4++) {
      arrayOfInt3[i4] = ((int)arrayOfLong[i4]);
    }
    return paramDoubleMatrix3D.viewSelection(arrayOfInt1, arrayOfInt2, arrayOfInt3);
  }
  
  private static DoubleMatrix2D xdistanceOld(DoubleMatrix2D paramDoubleMatrix2D, int paramInt)
  {
    return null;
  }
  
  private static DoubleMatrix2D xdistanceOld2(DoubleMatrix2D paramDoubleMatrix2D, int paramInt)
  {
    return null;
  }
  
  public static abstract interface VectorVectorFunction
  {
    public abstract double apply(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.doublealgo.Statistic
 * JD-Core Version:    0.7.0.1
 */
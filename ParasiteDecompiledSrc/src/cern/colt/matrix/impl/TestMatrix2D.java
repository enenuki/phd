package cern.colt.matrix.impl;

import cern.colt.Sorting;
import cern.colt.Timer;
import cern.colt.function.Double9Function;
import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;
import cern.colt.function.DoubleProcedure;
import cern.colt.list.IntArrayList;
import cern.colt.map.AbstractIntDoubleMap;
import cern.colt.map.OpenIntDoubleHashMap;
import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleFactory3D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.DoubleMatrix3D;
import cern.colt.matrix.doublealgo.DoubleMatrix2DComparator;
import cern.colt.matrix.doublealgo.Formatter;
import cern.colt.matrix.doublealgo.Statistic;
import cern.colt.matrix.doublealgo.Transform;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.linalg.Blas;
import cern.colt.matrix.linalg.LUDecompositionQuick;
import cern.colt.matrix.linalg.Property;
import cern.colt.matrix.linalg.SeqBlas;
import cern.jet.math.Functions;
import cern.jet.random.Normal;
import cern.jet.random.Poisson;
import cern.jet.random.engine.MersenneTwister;
import hep.aida.bin.DynamicBin1D;
import java.io.PrintStream;

class TestMatrix2D
{
  private static final Functions F = Functions.functions;
  private static final DoubleFactory2D Factory2D = DoubleFactory2D.dense;
  private static final DoubleFactory1D Factory1D = DoubleFactory1D.dense;
  private static final Algebra LinearAlgebra = Algebra.DEFAULT;
  private static final Transform Transform = Transform.transform;
  private static final Property Property = Property.DEFAULT;
  
  protected TestMatrix2D()
  {
    throw new RuntimeException("Non instantiable");
  }
  
  public static void doubleTest()
  {
    int i = 4;
    int j = 5;
    DenseDoubleMatrix2D localDenseDoubleMatrix2D = new DenseDoubleMatrix2D(i, j);
    System.out.println(localDenseDoubleMatrix2D);
    localDenseDoubleMatrix2D.assign(1.0D);
    System.out.println("\n" + localDenseDoubleMatrix2D);
    localDenseDoubleMatrix2D.viewPart(2, 1, 2, 3).assign(2.0D);
    System.out.println("\n" + localDenseDoubleMatrix2D);
    DoubleMatrix2D localDoubleMatrix2D1 = localDenseDoubleMatrix2D.viewPart(2, 1, 2, 3).copy();
    localDoubleMatrix2D1.assign(3.0D);
    localDoubleMatrix2D1.set(0, 0, 4.0D);
    System.out.println("\n" + localDoubleMatrix2D1);
    System.out.println("\n" + localDenseDoubleMatrix2D);
    DoubleMatrix2D localDoubleMatrix2D2 = localDenseDoubleMatrix2D.viewPart(0, 3, 4, 2);
    DoubleMatrix2D localDoubleMatrix2D3 = localDoubleMatrix2D2.viewPart(0, 0, 4, 1);
    System.out.println("\n" + localDoubleMatrix2D2);
    System.out.println("\n" + localDoubleMatrix2D3);
  }
  
  public static void doubleTest(int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2)
  {
    SparseDoubleMatrix2D localSparseDoubleMatrix2D = new SparseDoubleMatrix2D(paramInt1, paramInt2, paramInt3, paramDouble1, paramDouble2);
    System.out.println(localSparseDoubleMatrix2D);
    System.out.println("adding...");
    int i = 0;
    int k;
    for (int j = 0; j < paramInt2; j++) {
      for (k = 0; k < paramInt1; k++)
      {
        localSparseDoubleMatrix2D.set(k, j, i);
        i++;
      }
    }
    System.out.println(localSparseDoubleMatrix2D);
    System.out.println("removing...");
    for (j = 0; j < paramInt2; j++) {
      for (k = 0; k < paramInt1; k++) {
        localSparseDoubleMatrix2D.set(k, j, 0.0D);
      }
    }
    System.out.println(localSparseDoubleMatrix2D);
    System.out.println("bye bye.");
  }
  
  public static void doubleTest10()
  {
    int i = 6;
    int j = 7;
    DoubleMatrix2D localDoubleMatrix2D1 = Factory2D.ascending(i, j);
    Transform.mult(localDoubleMatrix2D1, Math.sin(0.3D));
    System.out.println("\n" + localDoubleMatrix2D1);
    int[] arrayOfInt1 = { 0, 1, 2, 3 };
    int[] arrayOfInt2 = { 0, 1, 2, 3 };
    int[] arrayOfInt3 = { 3, 0, 3 };
    int[] arrayOfInt4 = { 3, 0, 3 };
    DoubleMatrix2D localDoubleMatrix2D2 = localDoubleMatrix2D1.viewPart(1, 1, 4, 5).viewSelection(arrayOfInt1, arrayOfInt2);
    System.out.println("\nview1=" + localDoubleMatrix2D2);
    DoubleMatrix2D localDoubleMatrix2D3 = localDoubleMatrix2D2.viewStrides(2, 2).viewStrides(2, 1);
    System.out.println("\nview9=" + localDoubleMatrix2D3);
    localDoubleMatrix2D2 = localDoubleMatrix2D2.viewSelection(arrayOfInt3, arrayOfInt4);
    System.out.println("\nview1=" + localDoubleMatrix2D2);
    DoubleMatrix2D localDoubleMatrix2D4 = localDoubleMatrix2D2.viewPart(1, 1, 2, 2);
    System.out.println("\nview2=" + localDoubleMatrix2D4);
    DoubleMatrix2D localDoubleMatrix2D5 = localDoubleMatrix2D4.viewRowFlip();
    System.out.println("\nview3=" + localDoubleMatrix2D5);
    localDoubleMatrix2D5.assign(Factory2D.ascending(localDoubleMatrix2D5.rows(), localDoubleMatrix2D5.columns()));
    System.out.println("\nview3=" + localDoubleMatrix2D5);
    System.out.println("\nmaster replaced" + localDoubleMatrix2D1);
    System.out.println("\nview1 replaced" + localDoubleMatrix2D2);
    System.out.println("\nview2 replaced" + localDoubleMatrix2D4);
    System.out.println("\nview3 replaced" + localDoubleMatrix2D5);
  }
  
  public static void doubleTest11()
  {
    int i = 4;
    int j = 5;
    DenseDoubleMatrix2D localDenseDoubleMatrix2D = new DenseDoubleMatrix2D(1, 1);
    localDenseDoubleMatrix2D.assign(2.0D);
    System.out.println("\n" + localDenseDoubleMatrix2D);
    int[] arrayOfInt1 = new int[i];
    int[] arrayOfInt2 = new int[j];
    DoubleMatrix2D localDoubleMatrix2D = localDenseDoubleMatrix2D.viewSelection(arrayOfInt1, arrayOfInt2);
    System.out.println(localDoubleMatrix2D);
    localDenseDoubleMatrix2D.assign(1.0D);
    System.out.println("\n" + localDenseDoubleMatrix2D);
    System.out.println(localDoubleMatrix2D);
  }
  
  public static void doubleTest12()
  {
    DoubleMatrix2D localDoubleMatrix2D1 = Factory2D.make(2, 3, 9.0D);
    DoubleMatrix2D localDoubleMatrix2D2 = Factory2D.make(4, 3, 8.0D);
    DoubleMatrix2D localDoubleMatrix2D3 = Factory2D.appendRows(localDoubleMatrix2D1, localDoubleMatrix2D2);
    System.out.println("\nA=" + localDoubleMatrix2D1);
    System.out.println("\nB=" + localDoubleMatrix2D2);
    System.out.println("\nC=" + localDoubleMatrix2D3);
    DoubleMatrix2D localDoubleMatrix2D4 = Factory2D.make(3, 2, 7.0D);
    DoubleMatrix2D localDoubleMatrix2D5 = Factory2D.make(3, 4, 6.0D);
    DoubleMatrix2D localDoubleMatrix2D6 = Factory2D.appendColumns(localDoubleMatrix2D4, localDoubleMatrix2D5);
    System.out.println("\nD=" + localDoubleMatrix2D4);
    System.out.println("\nE=" + localDoubleMatrix2D5);
    System.out.println("\nF=" + localDoubleMatrix2D6);
    DoubleMatrix2D localDoubleMatrix2D7 = Factory2D.appendRows(localDoubleMatrix2D3, localDoubleMatrix2D6);
    System.out.println("\nG=" + localDoubleMatrix2D7);
    DoubleMatrix2D localDoubleMatrix2D8 = Factory2D.ascending(2, 3);
    System.out.println("\nH=" + localDoubleMatrix2D8);
    DoubleMatrix2D localDoubleMatrix2D9 = Factory2D.repeat(localDoubleMatrix2D8, 2, 3);
    System.out.println("\nI=" + localDoubleMatrix2D9);
  }
  
  public static void doubleTest13()
  {
    double[] arrayOfDouble = { 0.0D, 1.0D, 2.0D, 3.0D };
    DenseDoubleMatrix1D localDenseDoubleMatrix1D = new DenseDoubleMatrix1D(arrayOfDouble);
    System.out.println(localDenseDoubleMatrix1D);
    System.out.println(localDenseDoubleMatrix1D.viewSelection(new DoubleProcedure()
    {
      public final boolean apply(double paramAnonymousDouble)
      {
        return paramAnonymousDouble % 2.0D == 0.0D;
      }
    }));
    System.out.println(localDenseDoubleMatrix1D.aggregate(Functions.plus, Functions.square));
    System.out.println(localDenseDoubleMatrix1D.aggregate(Functions.plus, Functions.pow(3.0D)));
    System.out.println(localDenseDoubleMatrix1D.aggregate(Functions.plus, Functions.identity));
    System.out.println(localDenseDoubleMatrix1D.aggregate(Functions.min, Functions.identity));
    System.out.println(localDenseDoubleMatrix1D.aggregate(Functions.max, Functions.chain(Functions.div(2.0D), Functions.sqrt)));
    System.out.println(localDenseDoubleMatrix1D.aggregate(Functions.plus, Functions.between(0.0D, 2.0D)));
    System.out.println(localDenseDoubleMatrix1D.aggregate(Functions.plus, Functions.chain(Functions.between(0.8D, 1.2D), Functions.log2)));
    System.out.println(localDenseDoubleMatrix1D.aggregate(Functions.mult, Functions.identity));
    DoubleFunction local2 = new DoubleFunction()
    {
      public final double apply(double paramAnonymousDouble)
      {
        return paramAnonymousDouble > 1.0D ? paramAnonymousDouble : 1.0D;
      }
    };
    System.out.println(localDenseDoubleMatrix1D.aggregate(Functions.mult, local2));
    DoubleMatrix1D localDoubleMatrix1D = localDenseDoubleMatrix1D.copy();
    System.out.println(localDenseDoubleMatrix1D.aggregate(localDoubleMatrix1D, Functions.plus, Functions.chain(Functions.square, Functions.plus)));
    localDenseDoubleMatrix1D.assign(Functions.plus(1.0D));
    localDoubleMatrix1D = localDenseDoubleMatrix1D.copy();
    System.out.println(localDenseDoubleMatrix1D);
    System.out.println(localDoubleMatrix1D);
    System.out.println(localDenseDoubleMatrix1D.aggregate(localDoubleMatrix1D, Functions.plus, Functions.chain(Functions.mult(3.141592653589793D), Functions.chain(Functions.log, Functions.swapArgs(Functions.div)))));
    System.out.println(localDenseDoubleMatrix1D.aggregate(localDoubleMatrix1D, Functions.plus, new DoubleDoubleFunction()
    {
      public double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
      {
        return 3.141592653589793D * Math.log(paramAnonymousDouble2 / paramAnonymousDouble1);
      }
    }));
    DoubleMatrix3D localDoubleMatrix3D1 = DoubleFactory3D.dense.ascending(2, 2, 2);
    System.out.println(localDoubleMatrix3D1);
    System.out.println(localDoubleMatrix3D1.aggregate(Functions.plus, Functions.square));
    DoubleMatrix3D localDoubleMatrix3D2 = localDoubleMatrix3D1.copy();
    System.out.println(localDoubleMatrix3D1.aggregate(localDoubleMatrix3D2, Functions.plus, Functions.chain(Functions.square, Functions.plus)));
    System.out.println(localDenseDoubleMatrix1D.assign(Functions.random()));
    System.out.println(localDenseDoubleMatrix1D.assign(new Poisson(5.0D, Poisson.makeDefaultGenerator())));
  }
  
  public static void doubleTest14(int paramInt1, int paramInt2, int paramInt3)
  {
    double[] arrayOfDouble = { 0.0D, 1.0D, 2.0D, 3.0D };
    DoubleMatrix2D localDoubleMatrix2D1 = DoubleFactory2D.dense.ascending(paramInt1, paramInt2);
    DoubleMatrix2D localDoubleMatrix2D2 = Transform.mult(DoubleFactory2D.dense.ascending(paramInt2, paramInt3), -1.0D);
    localDoubleMatrix2D1.assign(0.0D);
    localDoubleMatrix2D2.assign(0.0D);
    Timer localTimer = new Timer().start();
    LinearAlgebra.mult(localDoubleMatrix2D1, localDoubleMatrix2D2);
    localTimer.stop().display();
  }
  
  public static void doubleTest15(int paramInt1, int paramInt2)
  {
    System.out.println("\n\n");
    double[][] arrayOfDouble = { { 0.0D, 5.0D, 9.0D }, { 2.0D, 6.0D, 10.0D }, { 3.0D, 7.0D, 11.0D } };
    DoubleMatrix2D localDoubleMatrix2D1 = Factory2D.make(paramInt1, paramInt1);
    double d = 5.0D;
    int i = paramInt1;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      localDoubleMatrix2D1.setQuick(i, i, d);
    }
    localDoubleMatrix2D1.viewRow(0).assign(d);
    Timer localTimer = new Timer().start();
    DoubleMatrix2D localDoubleMatrix2D2 = null;
    for (int j = 0; j < paramInt2; j++) {
      localDoubleMatrix2D2 = LinearAlgebra.inverse(localDoubleMatrix2D1);
    }
    localTimer.stop().display();
  }
  
  public static void doubleTest17(int paramInt)
  {
    System.out.println("\n\n");
    DoubleMatrix2D localDoubleMatrix2D1 = Factory2D.ascending(3, 4);
    DoubleMatrix2D localDoubleMatrix2D2 = Factory2D.ascending(2, 3);
    DoubleMatrix2D localDoubleMatrix2D3 = Factory2D.ascending(1, 2);
    localDoubleMatrix2D2.assign(Functions.plus(localDoubleMatrix2D1.zSum()));
    localDoubleMatrix2D3.assign(Functions.plus(localDoubleMatrix2D2.zSum()));
  }
  
  public static void doubleTest18(int paramInt)
  {
    System.out.println("\n\n");
    int i = 2;
    DoubleMatrix2D localDoubleMatrix2D9 = Factory2D.make(0, 0);
    DoubleMatrix2D localDoubleMatrix2D1 = Factory2D.ascending(i, i);
    DoubleMatrix2D localDoubleMatrix2D2 = Factory2D.ascending(i, i).assign(Functions.plus(localDoubleMatrix2D1.getQuick(i - 1, i - 1)));
    DoubleMatrix2D localDoubleMatrix2D3 = Factory2D.ascending(i, i).assign(Functions.plus(localDoubleMatrix2D2.getQuick(i - 1, i - 1)));
    DoubleMatrix2D localDoubleMatrix2D4 = Factory2D.ascending(i, i).assign(Functions.plus(localDoubleMatrix2D3.getQuick(i - 1, i - 1)));
    Object localObject = null;
    DoubleMatrix2D localDoubleMatrix2D5 = Factory2D.ascending(i, i).assign(Functions.plus(localDoubleMatrix2D4.getQuick(i - 1, i - 1)));
    DoubleMatrix2D localDoubleMatrix2D6 = Factory2D.ascending(i, i).assign(Functions.plus(localDoubleMatrix2D5.getQuick(i - 1, i - 1)));
    DoubleMatrix2D localDoubleMatrix2D7 = localDoubleMatrix2D9;
    DoubleMatrix2D localDoubleMatrix2D8 = Factory2D.ascending(i, i).assign(Functions.plus(localDoubleMatrix2D6.getQuick(i - 1, i - 1)));
    System.out.println("\n" + localDoubleMatrix2D1);
    System.out.println("\n" + localDoubleMatrix2D2);
    System.out.println("\n" + localDoubleMatrix2D3);
    System.out.println("\n" + localDoubleMatrix2D4);
    System.out.println("\n" + localObject);
    System.out.println("\n" + localDoubleMatrix2D5);
    System.out.println("\n" + localDoubleMatrix2D6);
    System.out.println("\n" + localDoubleMatrix2D7);
    System.out.println("\n" + localDoubleMatrix2D8);
  }
  
  public static void doubleTest19()
  {
    System.out.println("\n\n");
    double[][] arrayOfDouble1 = { { 0.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D } };
    DoubleMatrix2D localDoubleMatrix2D = Factory2D.make(arrayOfDouble1);
    int i = Property.DEFAULT.semiBandwidth(localDoubleMatrix2D);
    int j = Property.DEFAULT.upperBandwidth(localDoubleMatrix2D);
    int k = Property.DEFAULT.lowerBandwidth(localDoubleMatrix2D);
    System.out.println("\n\nupperBandwidth=" + j);
    System.out.println("lowerBandwidth=" + k);
    System.out.println("bandwidth=" + i + " " + localDoubleMatrix2D);
    double[][] arrayOfDouble2 = { { 1.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 1.0D } };
    localDoubleMatrix2D = Factory2D.make(arrayOfDouble2);
    i = Property.DEFAULT.semiBandwidth(localDoubleMatrix2D);
    j = Property.DEFAULT.upperBandwidth(localDoubleMatrix2D);
    k = Property.DEFAULT.lowerBandwidth(localDoubleMatrix2D);
    System.out.println("\n\nupperBandwidth=" + j);
    System.out.println("lowerBandwidth=" + k);
    System.out.println("bandwidth=" + i + " " + localDoubleMatrix2D);
    double[][] arrayOfDouble3 = { { 1.0D, 1.0D, 0.0D, 0.0D }, { 1.0D, 1.0D, 1.0D, 0.0D }, { 0.0D, 1.0D, 1.0D, 1.0D }, { 0.0D, 0.0D, 1.0D, 1.0D } };
    localDoubleMatrix2D = Factory2D.make(arrayOfDouble3);
    i = Property.DEFAULT.semiBandwidth(localDoubleMatrix2D);
    j = Property.DEFAULT.upperBandwidth(localDoubleMatrix2D);
    k = Property.DEFAULT.lowerBandwidth(localDoubleMatrix2D);
    System.out.println("\n\nupperBandwidth=" + j);
    System.out.println("lowerBandwidth=" + k);
    System.out.println("bandwidth=" + i + " " + localDoubleMatrix2D);
    double[][] arrayOfDouble4 = { { 0.0D, 1.0D, 1.0D, 1.0D }, { 0.0D, 1.0D, 1.0D, 1.0D }, { 0.0D, 0.0D, 0.0D, 1.0D }, { 0.0D, 0.0D, 0.0D, 1.0D } };
    localDoubleMatrix2D = Factory2D.make(arrayOfDouble4);
    i = Property.DEFAULT.semiBandwidth(localDoubleMatrix2D);
    j = Property.DEFAULT.upperBandwidth(localDoubleMatrix2D);
    k = Property.DEFAULT.lowerBandwidth(localDoubleMatrix2D);
    System.out.println("\n\nupperBandwidth=" + j);
    System.out.println("lowerBandwidth=" + k);
    System.out.println("bandwidth=" + i + " " + localDoubleMatrix2D);
    double[][] arrayOfDouble5 = { { 0.0D, 0.0D, 0.0D, 0.0D }, { 1.0D, 1.0D, 0.0D, 0.0D }, { 1.0D, 1.0D, 0.0D, 0.0D }, { 1.0D, 1.0D, 1.0D, 1.0D } };
    localDoubleMatrix2D = Factory2D.make(arrayOfDouble5);
    i = Property.DEFAULT.semiBandwidth(localDoubleMatrix2D);
    j = Property.DEFAULT.upperBandwidth(localDoubleMatrix2D);
    k = Property.DEFAULT.lowerBandwidth(localDoubleMatrix2D);
    System.out.println("\n\nupperBandwidth=" + j);
    System.out.println("lowerBandwidth=" + k);
    System.out.println("bandwidth=" + i + " " + localDoubleMatrix2D);
    double[][] arrayOfDouble6 = { { 1.0D, 1.0D, 0.0D, 0.0D }, { 0.0D, 1.0D, 1.0D, 0.0D }, { 0.0D, 1.0D, 0.0D, 1.0D }, { 1.0D, 0.0D, 1.0D, 1.0D } };
    localDoubleMatrix2D = Factory2D.make(arrayOfDouble6);
    i = Property.DEFAULT.semiBandwidth(localDoubleMatrix2D);
    j = Property.DEFAULT.upperBandwidth(localDoubleMatrix2D);
    k = Property.DEFAULT.lowerBandwidth(localDoubleMatrix2D);
    System.out.println("\n\nupperBandwidth=" + j);
    System.out.println("lowerBandwidth=" + k);
    System.out.println("bandwidth=" + i + " " + localDoubleMatrix2D);
    double[][] arrayOfDouble7 = { { 1.0D, 1.0D, 1.0D, 0.0D }, { 0.0D, 1.0D, 0.0D, 0.0D }, { 1.0D, 1.0D, 0.0D, 1.0D }, { 0.0D, 0.0D, 1.0D, 1.0D } };
    localDoubleMatrix2D = Factory2D.make(arrayOfDouble7);
    i = Property.DEFAULT.semiBandwidth(localDoubleMatrix2D);
    j = Property.DEFAULT.upperBandwidth(localDoubleMatrix2D);
    k = Property.DEFAULT.lowerBandwidth(localDoubleMatrix2D);
    System.out.println("\n\nupperBandwidth=" + j);
    System.out.println("lowerBandwidth=" + k);
    System.out.println("bandwidth=" + i + " " + localDoubleMatrix2D);
  }
  
  public static void doubleTest19(int paramInt)
  {
    System.out.println("\n\n");
    int i = 2;
    DoubleMatrix2D localDoubleMatrix2D9 = Factory2D.make(0, 0);
    DoubleMatrix2D localDoubleMatrix2D1 = Factory2D.ascending(i, i);
    DoubleMatrix2D localDoubleMatrix2D2 = Factory2D.ascending(i, i).assign(Functions.plus(localDoubleMatrix2D1.getQuick(i - 1, i - 1)));
    DoubleMatrix2D localDoubleMatrix2D3 = Factory2D.ascending(i, i).assign(Functions.plus(localDoubleMatrix2D2.getQuick(i - 1, i - 1)));
    DoubleMatrix2D localDoubleMatrix2D4 = Factory2D.ascending(i, i).assign(Functions.plus(localDoubleMatrix2D3.getQuick(i - 1, i - 1)));
    Object localObject = null;
    DoubleMatrix2D localDoubleMatrix2D5 = Factory2D.ascending(i, i).assign(Functions.plus(localDoubleMatrix2D4.getQuick(i - 1, i - 1)));
    DoubleMatrix2D localDoubleMatrix2D6 = Factory2D.ascending(i, i).assign(Functions.plus(localDoubleMatrix2D5.getQuick(i - 1, i - 1)));
    DoubleMatrix2D localDoubleMatrix2D7 = localDoubleMatrix2D9;
    DoubleMatrix2D localDoubleMatrix2D8 = Factory2D.ascending(i, i).assign(Functions.plus(localDoubleMatrix2D6.getQuick(i - 1, i - 1)));
    System.out.println("\n" + localDoubleMatrix2D1);
    System.out.println("\n" + localDoubleMatrix2D2);
    System.out.println("\n" + localDoubleMatrix2D3);
    System.out.println("\n" + localDoubleMatrix2D4);
    System.out.println("\n" + localObject);
    System.out.println("\n" + localDoubleMatrix2D5);
    System.out.println("\n" + localDoubleMatrix2D6);
    System.out.println("\n" + localDoubleMatrix2D7);
    System.out.println("\n" + localDoubleMatrix2D8);
  }
  
  public static void doubleTest2()
  {
    int[] arrayOfInt = { 0, 3, 100000, 9 };
    double[] arrayOfDouble = { 100.0D, 1000.0D, 70.0D, 71.0D };
    int i = arrayOfInt.length;
    OpenIntDoubleHashMap localOpenIntDoubleHashMap = new OpenIntDoubleHashMap(i * 2, 0.2D, 0.5D);
    for (int j = 0; j < arrayOfInt.length; j++) {
      localOpenIntDoubleHashMap.put(arrayOfInt[j], (int)arrayOfDouble[j]);
    }
    System.out.println(localOpenIntDoubleHashMap.containsKey(3));
    System.out.println(localOpenIntDoubleHashMap.get(3));
    System.out.println(localOpenIntDoubleHashMap.containsKey(4));
    System.out.println(localOpenIntDoubleHashMap.get(4));
    System.out.println(localOpenIntDoubleHashMap.containsValue(71.0D));
    System.out.println(localOpenIntDoubleHashMap.keyOf(71.0D));
    System.out.println(localOpenIntDoubleHashMap);
  }
  
  public static void doubleTest20()
  {
    System.out.println("\n\n");
    double[][] arrayOfDouble1 = { { 0.0D, 1.0D, 0.0D, 0.0D }, { 3.0D, 0.0D, 2.0D, 0.0D }, { 0.0D, 2.0D, 0.0D, 3.0D }, { 0.0D, 0.0D, 1.0D, 0.0D } };
    DoubleMatrix2D localDoubleMatrix2D = Factory2D.make(arrayOfDouble1);
    System.out.println("\n\n" + LinearAlgebra.toVerboseString(localDoubleMatrix2D));
    double[][] arrayOfDouble2 = { { 1.000000000000017D, -0.3623577544766736D, -0.3623577544766736D }, { 0.0D, 0.9320390859672374D, -0.3377315902755755D }, { 0.0D, 0.0D, 0.8686968577706282D }, { 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D } };
    localDoubleMatrix2D = Factory2D.make(arrayOfDouble2);
    System.out.println("\n\n" + LinearAlgebra.toVerboseString(localDoubleMatrix2D));
    double[][] arrayOfDouble3 = { { 611.0D, 196.0D, -192.0D, 407.0D, -8.0D, -52.0D, -49.0D, 29.0D }, { 196.0D, 899.0D, 113.0D, -192.0D, -71.0D, -43.0D, -8.0D, -44.0D }, { -192.0D, 113.0D, 899.0D, 196.0D, 61.0D, 49.0D, 8.0D, 52.0D }, { 407.0D, -192.0D, 196.0D, 611.0D, 8.0D, 44.0D, 59.0D, -23.0D }, { -8.0D, -71.0D, 61.0D, 8.0D, 411.0D, -599.0D, 208.0D, 208.0D }, { -52.0D, -43.0D, 49.0D, 44.0D, -599.0D, 411.0D, 208.0D, 208.0D }, { -49.0D, -8.0D, 8.0D, 59.0D, 208.0D, 208.0D, 99.0D, -911.0D }, { 29.0D, -44.0D, 52.0D, -23.0D, 208.0D, 208.0D, -911.0D, 99.0D } };
    localDoubleMatrix2D = Factory2D.make(arrayOfDouble3);
    System.out.println("\n\n" + LinearAlgebra.toVerboseString(localDoubleMatrix2D));
    double d1 = Math.sqrt(10405.0D);
    double d2 = Math.sqrt(26.0D);
    double[] arrayOfDouble = { -10.0D * d1, 0.0D, 510.0D - 100.0D * d2, 1000.0D, 1000.0D, 510.0D + 100.0D * d2, 1020.0D, 10.0D * d1 };
    System.out.println(DoubleFactory1D.dense.make(arrayOfDouble));
  }
  
  public static void doubleTest21()
  {
    System.out.println("\n\n");
    double[][] arrayOfDouble = { { 0.0D, 0.0D, 3.141592653589793D, 0.0D }, { 3.0D, 9.0D, 0.0D, 0.0D }, { 0.0D, 2.0D, 7.0D, 0.0D }, { 0.0D, 0.0D, 3.0D, 9.0D } };
    DoubleMatrix2D localDoubleMatrix2D = Factory2D.make(arrayOfDouble);
    System.out.println(localDoubleMatrix2D);
    System.out.println(new Formatter(null).toString(localDoubleMatrix2D));
  }
  
  public static void doubleTest22()
  {
    System.out.println("\n\n");
    double[][] arrayOfDouble = { { 0.0D, 0.0D, 3.141592653589793D, 0.0D }, { 3.0D, 9.0D, 0.0D, 0.0D }, { 0.0D, 2.0D, 7.0D, 0.0D }, { 0.0D, 0.0D, 3.0D, 9.0D } };
    DoubleMatrix2D localDoubleMatrix2D = Factory2D.make(arrayOfDouble);
    System.out.println(localDoubleMatrix2D);
    System.out.println(Property.isDiagonallyDominantByRow(localDoubleMatrix2D));
    System.out.println(Property.isDiagonallyDominantByColumn(localDoubleMatrix2D));
    Property.generateNonSingular(localDoubleMatrix2D);
    System.out.println(localDoubleMatrix2D);
    System.out.println(Property.isDiagonallyDominantByRow(localDoubleMatrix2D));
    System.out.println(Property.isDiagonallyDominantByColumn(localDoubleMatrix2D));
  }
  
  public static void doubleTest23(int paramInt1, int paramInt2, double paramDouble, boolean paramBoolean)
  {
    System.out.println("\n\n");
    System.out.println("initializing...");
    double d1 = 5.0D;
    double d2 = 3.0D;
    Normal localNormal = new Normal(d1, d2, new MersenneTwister());
    System.out.println("sampling...");
    double d3 = 2.0D;
    DoubleMatrix2D localDoubleMatrix2D1;
    if (paramBoolean) {
      localDoubleMatrix2D1 = DoubleFactory2D.dense.sample(paramInt2, paramInt2, d3, paramDouble);
    } else {
      localDoubleMatrix2D1 = DoubleFactory2D.sparse.sample(paramInt2, paramInt2, d3, paramDouble);
    }
    DoubleMatrix1D localDoubleMatrix1D1 = localDoubleMatrix2D1.like1D(paramInt2).assign(1.0D);
    System.out.println("generating invertible matrix...");
    Property.generateNonSingular(localDoubleMatrix2D1);
    DoubleMatrix2D localDoubleMatrix2D2 = localDoubleMatrix2D1.like();
    DoubleMatrix1D localDoubleMatrix1D2 = localDoubleMatrix1D1.like();
    LUDecompositionQuick localLUDecompositionQuick = new LUDecompositionQuick();
    System.out.println("benchmarking assignment...");
    Timer localTimer = new Timer().start();
    localDoubleMatrix2D2.assign(localDoubleMatrix2D1);
    localDoubleMatrix1D2.assign(localDoubleMatrix1D1);
    localTimer.stop().display();
    localDoubleMatrix2D2.assign(localDoubleMatrix2D1);
    localLUDecompositionQuick.decompose(localDoubleMatrix2D2);
    System.out.println("benchmarking LU...");
    localTimer.reset().start();
    int i = paramInt1;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      localDoubleMatrix1D2.assign(localDoubleMatrix1D1);
      localLUDecompositionQuick.solve(localDoubleMatrix1D2);
    }
    localTimer.stop().display();
    System.out.println("done.");
  }
  
  public static void doubleTest24(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    System.out.println("\n\n");
    System.out.println("initializing...");
    DoubleFactory2D localDoubleFactory2D;
    if (paramBoolean) {
      localDoubleFactory2D = DoubleFactory2D.dense;
    } else {
      localDoubleFactory2D = DoubleFactory2D.sparse;
    }
    double d1 = 2.0D;
    double d2 = 1.25D;
    double d3 = d2 * 0.25D;
    double d4 = 1.0D - d2;
    DoubleMatrix2D localDoubleMatrix2D = localDoubleFactory2D.make(paramInt2, paramInt2, d1);
    Double9Function local4 = new Double9Function()
    {
      private final double val$alpha;
      private final double val$beta;
      
      public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2, double paramAnonymousDouble3, double paramAnonymousDouble4, double paramAnonymousDouble5, double paramAnonymousDouble6, double paramAnonymousDouble7, double paramAnonymousDouble8, double paramAnonymousDouble9)
      {
        return this.val$alpha * paramAnonymousDouble5 + this.val$beta * (paramAnonymousDouble2 + paramAnonymousDouble4 + paramAnonymousDouble6 + paramAnonymousDouble8);
      }
    };
    Timer localTimer = new Timer().start();
    System.out.println("benchmarking stencil...");
    for (int i = 0; i < paramInt1; i++) {
      localDoubleMatrix2D.zAssign8Neighbors(localDoubleMatrix2D, local4);
    }
    localTimer.stop().display();
    localDoubleMatrix2D = null;
    double[][] arrayOfDouble = localDoubleFactory2D.make(paramInt2, paramInt2, d1).toArray();
    localTimer.reset().start();
    System.out.println("benchmarking stencil scimark...");
    for (int j = 0; j < paramInt1; j++) {}
    localTimer.stop().display();
    System.out.println("done.");
  }
  
  public static void doubleTest25(int paramInt)
  {
    System.out.println("\n\n");
    System.out.println("initializing...");
    int i = 1;
    DoubleFactory2D localDoubleFactory2D;
    if (i != 0) {
      localDoubleFactory2D = DoubleFactory2D.dense;
    } else {
      localDoubleFactory2D = DoubleFactory2D.sparse;
    }
    double d = 0.5D;
    DoubleMatrix2D localDoubleMatrix2D = localDoubleFactory2D.make(paramInt, paramInt, d);
    Property.generateNonSingular(localDoubleMatrix2D);
    Timer localTimer = new Timer().start();
    System.out.println(localDoubleMatrix2D);
    System.out.println(Algebra.ZERO.inverse(localDoubleMatrix2D));
    localTimer.stop().display();
    System.out.println("done.");
  }
  
  public static void doubleTest26(int paramInt)
  {
    System.out.println("\n\n");
    System.out.println("initializing...");
    int i = 1;
    DoubleFactory2D localDoubleFactory2D;
    if (i != 0) {
      localDoubleFactory2D = DoubleFactory2D.dense;
    } else {
      localDoubleFactory2D = DoubleFactory2D.sparse;
    }
    double d = 0.5D;
    DoubleMatrix2D localDoubleMatrix2D = localDoubleFactory2D.make(paramInt, paramInt, d);
    Property.generateNonSingular(localDoubleMatrix2D);
    Timer localTimer = new Timer().start();
    DoubleMatrix2DComparator local5 = new DoubleMatrix2DComparator()
    {
      public int compare(DoubleMatrix2D paramAnonymousDoubleMatrix2D1, DoubleMatrix2D paramAnonymousDoubleMatrix2D2)
      {
        return paramAnonymousDoubleMatrix2D1.zSum() == paramAnonymousDoubleMatrix2D2.zSum() ? 1 : 0;
      }
    };
    System.out.println(localDoubleMatrix2D);
    System.out.println(Algebra.ZERO.inverse(localDoubleMatrix2D));
    localTimer.stop().display();
    System.out.println("done.");
  }
  
  public static void doubleTest27()
  {
    System.out.println("\n\n");
    System.out.println("initializing...");
    int i = 51;
    int j = 10;
    double[][] arrayOfDouble = new double[j][i];
    int k = j;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      arrayOfDouble[k][k] = 2.0D;
    }
    k = 0;
    int m = 0;
    DoubleMatrix2D localDoubleMatrix2D1 = null;
    DoubleMatrix2D localDoubleMatrix2D2 = null;
    DoubleMatrix2D localDoubleMatrix2D3 = null;
    DoubleMatrix2D localDoubleMatrix2D4 = null;
    DoubleMatrix2D localDoubleMatrix2D5 = null;
    DoubleMatrix2D localDoubleMatrix2D6 = null;
    localDoubleMatrix2D1 = DoubleFactory2D.dense.make(i, j);
    for (k = 0; k < j; k++) {
      for (m = 0; m < i; m++) {
        localDoubleMatrix2D1.setQuick(m, k, arrayOfDouble[k][m]);
      }
    }
    localDoubleMatrix2D2 = Algebra.DEFAULT.transpose(localDoubleMatrix2D1);
    localDoubleMatrix2D3 = Algebra.DEFAULT.mult(localDoubleMatrix2D2, localDoubleMatrix2D1);
    localDoubleMatrix2D4 = Algebra.DEFAULT.inverse(localDoubleMatrix2D3);
    localDoubleMatrix2D5 = Algebra.DEFAULT.mult(localDoubleMatrix2D4, localDoubleMatrix2D2);
    localDoubleMatrix2D6 = Algebra.DEFAULT.mult(localDoubleMatrix2D1, localDoubleMatrix2D5);
    System.out.println("done.");
  }
  
  public static void doubleTest28()
  {
    double[] arrayOfDouble = { 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D };
    double[][] arrayOfDouble1 = { { 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D }, { 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D } };
    DoubleFactory2D localDoubleFactory2D = DoubleFactory2D.dense;
    DenseDoubleMatrix1D localDenseDoubleMatrix1D = new DenseDoubleMatrix1D(arrayOfDouble);
    DoubleMatrix2D localDoubleMatrix2D = localDoubleFactory2D.make(arrayOfDouble1);
    DoubleMatrix1D localDoubleMatrix1D = localDenseDoubleMatrix1D.like(localDoubleMatrix2D.rows());
    localDoubleMatrix2D.zMult(localDenseDoubleMatrix1D, localDoubleMatrix1D);
    System.out.println(localDoubleMatrix1D);
  }
  
  public static void doubleTest28(DoubleFactory2D paramDoubleFactory2D)
  {
    double[] arrayOfDouble = { 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D };
    double[][] arrayOfDouble1 = { { 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D }, { 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D } };
    DenseDoubleMatrix1D localDenseDoubleMatrix1D = new DenseDoubleMatrix1D(arrayOfDouble);
    DoubleMatrix2D localDoubleMatrix2D = paramDoubleFactory2D.make(arrayOfDouble1);
    DoubleMatrix1D localDoubleMatrix1D = localDenseDoubleMatrix1D.like(localDoubleMatrix2D.rows());
    localDoubleMatrix2D.zMult(localDenseDoubleMatrix1D, localDoubleMatrix1D);
    System.out.println(localDoubleMatrix1D);
  }
  
  public static void doubleTest29(int paramInt) {}
  
  public static void doubleTest29(int paramInt, DoubleFactory2D paramDoubleFactory2D)
  {
    DoubleMatrix2D localDoubleMatrix2D1 = new DenseDoubleMatrix2D(paramInt, paramInt).assign(0.5D);
    DoubleMatrix2D localDoubleMatrix2D2 = paramDoubleFactory2D.sample(paramInt, paramInt, 0.5D, 0.001D);
    Timer localTimer = new Timer().start();
    DoubleMatrix2D localDoubleMatrix2D3 = localDoubleMatrix2D2.zMult(localDoubleMatrix2D1, null);
    localTimer.stop().display();
  }
  
  public static void doubleTest29(DoubleFactory2D paramDoubleFactory2D)
  {
    double[][] arrayOfDouble1 = { { 6.0D, 5.0D, 4.0D }, { 7.0D, 6.0D, 3.0D }, { 6.0D, 5.0D, 4.0D }, { 7.0D, 6.0D, 3.0D }, { 6.0D, 5.0D, 4.0D }, { 7.0D, 6.0D, 3.0D } };
    double[][] arrayOfDouble2 = { { 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D }, { 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D } };
    DenseDoubleMatrix2D localDenseDoubleMatrix2D = new DenseDoubleMatrix2D(arrayOfDouble1);
    DoubleMatrix2D localDoubleMatrix2D1 = paramDoubleFactory2D.make(arrayOfDouble2);
    DoubleMatrix2D localDoubleMatrix2D2 = localDoubleMatrix2D1.zMult(localDenseDoubleMatrix2D, null);
    System.out.println(localDoubleMatrix2D2);
  }
  
  public static void doubleTest3()
  {
    int i = 4;
    int j = 5;
    DenseDoubleMatrix2D localDenseDoubleMatrix2D = new DenseDoubleMatrix2D(i, j);
    System.out.println(localDenseDoubleMatrix2D);
    localDenseDoubleMatrix2D.assign(1.0D);
    System.out.println("\n" + localDenseDoubleMatrix2D);
    localDenseDoubleMatrix2D.viewPart(2, 0, 2, 3).assign(2.0D);
    System.out.println("\n" + localDenseDoubleMatrix2D);
    DoubleMatrix2D localDoubleMatrix2D1 = localDenseDoubleMatrix2D.viewColumnFlip();
    System.out.println("flip around columns=" + localDoubleMatrix2D1);
    DoubleMatrix2D localDoubleMatrix2D2 = localDoubleMatrix2D1.viewRowFlip();
    System.out.println("further flip around rows=" + localDoubleMatrix2D2);
    localDoubleMatrix2D2.viewPart(0, 0, 2, 2).assign(3.0D);
    System.out.println("master replaced" + localDenseDoubleMatrix2D);
    System.out.println("flip1 replaced" + localDoubleMatrix2D1);
    System.out.println("flip2 replaced" + localDoubleMatrix2D2);
  }
  
  public static void doubleTest30()
  {
    double[][] arrayOfDouble = { { 6.0D, 5.0D }, { 7.0D, 6.0D } };
    double[] arrayOfDouble1 = { 1.0D, 2.0D };
    double[] arrayOfDouble2 = { 3.0D, 4.0D };
    DenseDoubleMatrix2D localDenseDoubleMatrix2D = new DenseDoubleMatrix2D(arrayOfDouble);
    SeqBlas.seqBlas.dger(1.0D, new DenseDoubleMatrix1D(arrayOfDouble1), new DenseDoubleMatrix1D(arrayOfDouble2), localDenseDoubleMatrix2D);
    System.out.println(localDenseDoubleMatrix2D);
  }
  
  public static void doubleTest30(int paramInt)
  {
    int[] arrayOfInt = { 0, 2, 3, 5, 7 };
    IntArrayList localIntArrayList = new IntArrayList(arrayOfInt);
    int i = 3;
    int j = 0;
    Timer localTimer = new Timer().start();
    int k = paramInt;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      int m = localIntArrayList.binarySearchFromTo(i, 0, arrayOfInt.length - 1);
      System.out.println(localIntArrayList + ", " + i + " --> " + m);
      j += m;
    }
    localTimer.stop().display();
  }
  
  public static void doubleTest30(int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = { 2 };
    IntArrayList localIntArrayList = new IntArrayList(arrayOfInt);
    int i = arrayOfInt.length - 1;
    int j = 0;
    Timer localTimer = new Timer().start();
    int k = paramInt1;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      int m = Sorting.binarySearchFromTo(arrayOfInt, paramInt2, 0, i);
      j += m;
    }
    localTimer.stop().display();
    System.out.println("sum = " + j);
  }
  
  public static void doubleTest31(int paramInt)
  {
    System.out.println("\ninit");
    DoubleMatrix1D localDoubleMatrix1D1 = DoubleFactory1D.dense.descending(paramInt);
    Object localObject = new WrapperDoubleMatrix1D(localDoubleMatrix1D1);
    DoubleMatrix1D localDoubleMatrix1D2 = ((DoubleMatrix1D)localObject).viewPart(2, 3);
    DoubleMatrix1D localDoubleMatrix1D3 = localDoubleMatrix1D2.viewFlip();
    localDoubleMatrix1D3.set(0, 99.0D);
    localObject = ((DoubleMatrix1D)localObject).viewSorted();
    System.out.println("a = " + localDoubleMatrix1D1);
    System.out.println("b = " + localObject);
    System.out.println("c = " + localDoubleMatrix1D2);
    System.out.println("d = " + localDoubleMatrix1D3);
    System.out.println("done");
  }
  
  public static void doubleTest32()
  {
    double[][] arrayOfDouble = { { 1.0D, 4.0D, 0.0D }, { 6.0D, 2.0D, 5.0D }, { 0.0D, 7.0D, 3.0D }, { 0.0D, 0.0D, 8.0D }, { 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D } };
    TridiagonalDoubleMatrix2D localTridiagonalDoubleMatrix2D = new TridiagonalDoubleMatrix2D(arrayOfDouble);
    System.out.println("\n\n\n" + localTridiagonalDoubleMatrix2D);
    System.out.println("\n" + new DenseDoubleMatrix2D(arrayOfDouble));
  }
  
  public static void doubleTest33()
  {
    double d1 = (0.0D / 0.0D);
    double d2 = (1.0D / 0.0D);
    double d3 = (-1.0D / 0.0D);
    double[][] arrayOfDouble = { { d3, d1 } };
    DenseDoubleMatrix2D localDenseDoubleMatrix2D = new DenseDoubleMatrix2D(arrayOfDouble);
    System.out.println("\n\n\n" + localDenseDoubleMatrix2D);
    System.out.println("\n" + localDenseDoubleMatrix2D.equals(d3));
  }
  
  public static void doubleTest34()
  {
    double[][] arrayOfDouble = { { 3.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 4.0D, 2.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D } };
    DenseDoubleMatrix2D localDenseDoubleMatrix2D = new DenseDoubleMatrix2D(arrayOfDouble);
    Property.DEFAULT.generateNonSingular(localDenseDoubleMatrix2D);
    DoubleMatrix2D localDoubleMatrix2D1 = Algebra.DEFAULT.inverse(localDenseDoubleMatrix2D);
    System.out.println("\n\n\n" + localDenseDoubleMatrix2D);
    System.out.println("\n" + localDoubleMatrix2D1);
    DoubleMatrix2D localDoubleMatrix2D2 = localDenseDoubleMatrix2D.zMult(localDoubleMatrix2D1, null);
    System.out.println(localDoubleMatrix2D2);
    if (!localDoubleMatrix2D2.equals(DoubleFactory2D.dense.identity(localDenseDoubleMatrix2D.rows))) {
      throw new InternalError();
    }
  }
  
  public static void doubleTest35() {}
  
  public static void doubleTest36()
  {
    double[] arrayOfDouble = new double[5];
    arrayOfDouble[0] = 5.0D;
    arrayOfDouble[1] = (0.0D / 0.0D);
    arrayOfDouble[2] = 2.0D;
    arrayOfDouble[3] = (0.0D / 0.0D);
    arrayOfDouble[4] = 1.0D;
    Object localObject = new DenseDoubleMatrix1D(arrayOfDouble);
    System.out.println("orig = " + localObject);
    localObject = ((DoubleMatrix1D)localObject).viewSorted();
    ((DoubleMatrix1D)localObject).toArray(arrayOfDouble);
    System.out.println("sort = " + localObject);
    System.out.println("done\n");
  }
  
  public static void doubleTest4()
  {
    int i = 4;
    int j = 5;
    DenseDoubleMatrix2D localDenseDoubleMatrix2D = new DenseDoubleMatrix2D(i, j);
    System.out.println(localDenseDoubleMatrix2D);
    localDenseDoubleMatrix2D.assign(1.0D);
    DoubleMatrix2D localDoubleMatrix2D = localDenseDoubleMatrix2D.viewPart(2, 0, 2, 3).assign(2.0D);
    System.out.println("\n" + localDenseDoubleMatrix2D);
    System.out.println("\n" + localDoubleMatrix2D);
    Transform.mult(localDoubleMatrix2D, 3.0D);
    System.out.println("\n" + localDenseDoubleMatrix2D);
    System.out.println("\n" + localDoubleMatrix2D);
  }
  
  public static void doubleTest5() {}
  
  public static void doubleTest6()
  {
    int i = 4;
    int j = 5;
    DoubleMatrix2D localDoubleMatrix2D = Factory2D.ascending(i, j);
    System.out.println("\n" + localDoubleMatrix2D);
    localDoubleMatrix2D.viewPart(2, 0, 2, 3).assign(2.0D);
    System.out.println("\n" + localDoubleMatrix2D);
    int[] arrayOfInt = { 0, 1, 3, 0, 1, 2 };
    DoubleMatrix1D localDoubleMatrix1D1 = localDoubleMatrix2D.viewRow(0).viewSelection(arrayOfInt);
    System.out.println("view1=" + localDoubleMatrix1D1);
    DoubleMatrix1D localDoubleMatrix1D2 = localDoubleMatrix1D1.viewPart(0, 3);
    System.out.println("view2=" + localDoubleMatrix1D2);
    localDoubleMatrix1D2.viewPart(0, 2).assign(-1.0D);
    System.out.println("master replaced" + localDoubleMatrix2D);
    System.out.println("flip1 replaced" + localDoubleMatrix1D1);
    System.out.println("flip2 replaced" + localDoubleMatrix1D2);
  }
  
  public static void doubleTest7()
  {
    int i = 4;
    int j = 5;
    DoubleMatrix2D localDoubleMatrix2D1 = Factory2D.ascending(i, j);
    System.out.println("\n" + localDoubleMatrix2D1);
    int[] arrayOfInt1 = { 0, 1, 3, 0 };
    int[] arrayOfInt2 = { 0, 2 };
    DoubleMatrix2D localDoubleMatrix2D2 = localDoubleMatrix2D1.viewSelection(arrayOfInt1, arrayOfInt2);
    System.out.println("view1=" + localDoubleMatrix2D2);
    DoubleMatrix2D localDoubleMatrix2D3 = localDoubleMatrix2D2.viewPart(0, 0, 2, 2);
    System.out.println("view2=" + localDoubleMatrix2D3);
    localDoubleMatrix2D3.assign(-1.0D);
    System.out.println("master replaced" + localDoubleMatrix2D1);
    System.out.println("flip1 replaced" + localDoubleMatrix2D2);
    System.out.println("flip2 replaced" + localDoubleMatrix2D3);
  }
  
  public static void doubleTest8()
  {
    int i = 2;
    int j = 3;
    DoubleMatrix2D localDoubleMatrix2D1 = Factory2D.ascending(i, j);
    System.out.println("\n" + localDoubleMatrix2D1);
    DoubleMatrix2D localDoubleMatrix2D2 = localDoubleMatrix2D1.viewDice();
    System.out.println("view1=" + localDoubleMatrix2D2);
    DoubleMatrix2D localDoubleMatrix2D3 = localDoubleMatrix2D2.viewDice();
    System.out.println("view2=" + localDoubleMatrix2D3);
    localDoubleMatrix2D3.assign(-1.0D);
    System.out.println("master replaced" + localDoubleMatrix2D1);
    System.out.println("flip1 replaced" + localDoubleMatrix2D2);
    System.out.println("flip2 replaced" + localDoubleMatrix2D3);
  }
  
  public static void doubleTest9()
  {
    int i = 2;
    int j = 3;
    DoubleMatrix2D localDoubleMatrix2D1 = Factory2D.ascending(i, j);
    System.out.println("\n" + localDoubleMatrix2D1);
    DoubleMatrix2D localDoubleMatrix2D2 = localDoubleMatrix2D1.viewRowFlip();
    System.out.println("view1=" + localDoubleMatrix2D2);
    DoubleMatrix2D localDoubleMatrix2D3 = localDoubleMatrix2D2.viewRowFlip();
    System.out.println("view2=" + localDoubleMatrix2D3);
    localDoubleMatrix2D3.assign(-1.0D);
    System.out.println("master replaced" + localDoubleMatrix2D1);
    System.out.println("flip1 replaced" + localDoubleMatrix2D2);
    System.out.println("flip2 replaced" + localDoubleMatrix2D3);
  }
  
  public static void doubleTestQR()
  {
    double[] arrayOfDouble1 = { -6.221564D, -9.002113D, 2.678001D, 6.483597D, -7.934148D };
    double[] arrayOfDouble2 = { -7.291898D, -7.346928D, 0.520158D, 5.012548D, -8.223725D };
    double[] arrayOfDouble3 = { 1.185925D, -2.523077D, 0.13538D, 0.412556D, -2.98028D };
    double[] arrayOfDouble4 = { 13.561087000000001D, -15.204409999999999D, 16.496829000000002D, 16.470859999999998D, 0.822198D };
    solve(arrayOfDouble3.length, arrayOfDouble3, arrayOfDouble4);
    solve(arrayOfDouble1.length, arrayOfDouble1, arrayOfDouble2);
  }
  
  public static void main(String[] paramArrayOfString)
  {
    int i = Integer.parseInt(paramArrayOfString[0]);
    int j = Integer.parseInt(paramArrayOfString[1]);
    doubleTest30(i, j);
  }
  
  public static double[][] randomMatrix(int paramInt, MersenneTwister paramMersenneTwister)
  {
    double[][] arrayOfDouble = new double[paramInt][paramInt];
    for (int i = 0; i < paramInt; i++) {
      for (int j = 0; j < paramInt; j++) {
        arrayOfDouble[i][j] = 5.0D;
      }
    }
    return arrayOfDouble;
  }
  
  public static void solve(int paramInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2) {}
  
  public static void testLU()
  {
    double[][] arrayOfDouble = { { -0.074683D, 0.321248D, -0.014656D, 0.286586D, 0.0D }, { -0.344852D, -0.16278D, 0.173711D, 0.0006400000000000001D, 0.0D }, { -0.181924D, -0.092926D, 0.184153D, 0.177966D, 1.0D }, { -0.166829D, -0.10321D, 0.582301D, 0.142583D, 0.0D }, { 0.0D, -0.112952D, -0.04932D, -0.700157D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D, 0.0D } };
    DenseDoubleMatrix2D localDenseDoubleMatrix2D = new DenseDoubleMatrix2D(arrayOfDouble);
    System.out.println("\nHplus=" + localDenseDoubleMatrix2D.viewDice().zMult(localDenseDoubleMatrix2D, null));
    DoubleMatrix2D localDoubleMatrix2D = Algebra.DEFAULT.inverse(localDenseDoubleMatrix2D.viewDice().zMult(localDenseDoubleMatrix2D, null)).zMult(localDenseDoubleMatrix2D.viewDice(), null);
    localDoubleMatrix2D.assign(Functions.round(1.0E-010D));
    System.out.println("\nHplus=" + localDoubleMatrix2D);
  }
  
  public static void testMax()
  {
    double[] arrayOfDouble = new double[2];
    arrayOfDouble[0] = 8.9D;
    arrayOfDouble[1] = 1.0D;
    DenseDoubleMatrix1D localDenseDoubleMatrix1D = new DenseDoubleMatrix1D(arrayOfDouble);
    DynamicBin1D localDynamicBin1D = Statistic.bin(localDenseDoubleMatrix1D);
    double d = localDynamicBin1D.max();
    System.out.println("max = " + d);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.TestMatrix2D
 * JD-Core Version:    0.7.0.1
 */